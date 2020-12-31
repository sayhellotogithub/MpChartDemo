package com.iblogstreet.mpchartdemo.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Transformer
import com.iblogstreet.mpchartdemo.R
import com.iblogstreet.mpchartdemo.bean.KLineEntity
import com.iblogstreet.mpchartdemo.bean.StockBean
import com.iblogstreet.mpchartdemo.util.ChartsControllerOnTouchUtils
import com.iblogstreet.mpchartdemo.util.DataRequest
import com.iblogstreet.mpchartdemo.util.MockUtil
import com.iblogstreet.mpchartdemo.util.StockUtil
import com.iblogstreet.mpchartdemo.view.*
import com.loro.klinechart.util.XVolFormatter
import java.text.DecimalFormat
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MyCoupleChartGestureListener.OnEdgeListener,
    CoupleChartValueSelectedListener.ValueSelectedListener,
    ChartFingerTouchListener.HighlightListener {

    private lateinit var barChart: BarChart
    private lateinit var stock_chart: CombinedChart
    private lateinit var fl_main_touch: FrameLayout
    private lateinit var cl_klHighlight: ConstraintLayout
    private lateinit var tv_line_info: TextView
    private lateinit var tvOpen: TextView
    private lateinit var tvClose: TextView
    private lateinit var tvHigh: TextView
    private lateinit var tvLow: TextView
    private lateinit var tvVol: TextView
    private lateinit var tvLine: TextView

    private var stockCandleData: CandleData? = null
    private var mMa5Datas = mutableListOf<Entry>()
    private var mMa10Datas = mutableListOf<Entry>()
    private var mMa20Datas = mutableListOf<Entry>()
    private var mMa30Datas = mutableListOf<Entry>()
    private var mMa60Datas = mutableListOf<Entry>()

    private var mVolumeMa5Datas = mutableListOf<Entry>()
    private var mVolumeMa10Datas = mutableListOf<Entry>()

    private var barData: BarData? = null
    private var mMaxVolume = 0f
    private var mXVals = mutableMapOf<Float, String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        initStockChart()
        initChart()
        initEvent()
        initData()

    }

    private fun initView() {
        barChart = findViewById(R.id.bar_chart)
        stock_chart = findViewById(R.id.stock_chart)
        fl_main_touch = findViewById(R.id.fl_main_touch)
        cl_klHighlight = findViewById(R.id.cl_klHighlight)
        tv_line_info = findViewById(R.id.tv_line_info)
        tvOpen = findViewById(R.id.tv_klOpen)
        tvClose = findViewById(R.id.tv_klClose)
        tvHigh = findViewById(R.id.tv_klHigh)
        tvLow = findViewById(R.id.tv_klLow)
        tvVol = findViewById(R.id.tv_klVol)
        tvLine = findViewById(R.id.tv_line_info)
    }

    private var ccGesture: MyCoupleChartGestureListener? = null
    private var bcGesture: MyCoupleChartGestureListener? = null
    private fun initEvent() {

        ccGesture = object : MyCoupleChartGestureListener(this, false, stock_chart, barChart) {
            override fun chartDoubleTapped(me: MotionEvent?) {
                doubleTapped()
            }
        }
        stock_chart.setOnChartGestureListener(ccGesture) //设置手势联动监听

        bcGesture = object : MyCoupleChartGestureListener(this, true, barChart, stock_chart) {
            override fun chartDoubleTapped(me: MotionEvent?) {
                doubleTapped()
            }
        }
        barChart.setOnChartGestureListener(bcGesture)

        stock_chart.setOnChartValueSelectedListener(
            CoupleChartValueSelectedListener(
                this,
                stock_chart,
                barChart
            )
        ) //设置高亮联动监听

        barChart.setOnChartValueSelectedListener(
            CoupleChartValueSelectedListener(
                this,
                barChart,
                stock_chart
            )
        )

        stock_chart.setOnTouchListener(ChartFingerTouchListener(stock_chart, this)) //手指长按滑动高亮

        barChart.setOnTouchListener(ChartFingerTouchListener(barChart, this))

    }

    /**
     * 双击图表
     */
    private fun doubleTapped() {

//        if (isPort()) {
//            highVisX = cc.getHighestVisibleX()
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//        }
    }

    val black: Int = Color.BLACK
    val gray: Int = Color.GRAY
    val red: Int = Color.RED
    val green: Int = Color.GREEN
    val highlightColor: Int = Color.CYAN
    val highlightWidth = 0.5f //高亮线的线宽
    private val barOffset = 0f //BarChart偏移量

    fun sp2px(spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue,
            resources.displayMetrics
        ).toInt()
    }

    private fun initStockChart() {

        var sp8: Float = sp2px(8f).toFloat()
        stock_chart.getDescription().setEnabled(false)
        stock_chart.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        stock_chart.setDrawGridBackground(false)
        stock_chart.setDrawBarShadow(false)
        stock_chart.setHighlightFullBarEnabled(false)


        //        val l = stock_chart.legend
//        l.isWordWrapEnabled = true
//        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.setDrawInside(false)
        //取消图例
        stock_chart.legend.isEnabled = false

        //无数据时
        stock_chart.setNoDataText("No Data")
        stock_chart.setNoDataTextColor(gray)

        //不允许甩动惯性滑动  和moveView方法有冲突 设置为false
        stock_chart.isDragDecelerationEnabled = false

        //外边缘偏移量
        stock_chart.minOffset = 0f
        //设置底部外边缘偏移量 便于显示X轴
//        stock_chart.extraBottomOffset = 6f

        stock_chart.setExtraOffsets(0f, 0f, 0f, 3f)

        stock_chart.setBorderWidth(1f)
        stock_chart.setBorderColor(ContextCompat.getColor(this@MainActivity, R.color.border_color))

        //缩放
        stock_chart.setScaleXEnabled(true) //X轴缩放
        stock_chart.isScaleYEnabled = true
//        stock_chart.isAutoScaleMinMaxEnabled = true//自适应最大最小值

        // draw bars behind lines
        stock_chart.drawOrder = arrayOf(
            CombinedChart.DrawOrder.CANDLE,
            CombinedChart.DrawOrder.LINE
        )

        val trans: Transformer = stock_chart.getTransformer(YAxis.AxisDependency.LEFT)
        stock_chart.rendererLeftYAxis =
            InBoundYAxisRenderer(stock_chart.viewPortHandler, stock_chart.axisLeft, trans)
        stock_chart.setXAxisRenderer(
            InBoundXAxisRenderer(
                stock_chart.viewPortHandler,
                stock_chart.xAxis,
                trans,
                10
            )
        )

        //自定义渲染器 重绘高亮
        stock_chart.renderer = HighlightCombinedRenderer(
            stock_chart,
            stock_chart.animator,
            stock_chart.viewPortHandler,
            sp8
        )

        //Y轴-右
        val rightAxis = stock_chart.axisRight
        rightAxis.isEnabled = false
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        rightAxis?.setDrawLabels(false)
        rightAxis?.setDrawGridLines(false)
        rightAxis?.setDrawAxisLine(false)
        rightAxis?.labelCount = 4

        //Y轴-左
        val leftAxis = stock_chart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)//标签显示在内侧
        leftAxis.setDrawGridLines(true)
//        leftAxis.gridColor = black
//        leftAxis.gridLineWidth

//        leftAxis.isEnabled = false
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        leftAxis.textColor = gray
        leftAxis.textSize = 8f
//        leftAxis.setLabelCount(5, true)
        leftAxis.enableGridDashedLine(5f, 4f, 0f)//横向网格线设置为虚线

        leftAxis?.setDrawGridLines(true)//是否显示Y坐标轴上的刻度横线，默认是true
        leftAxis?.setDrawAxisLine(false)//是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        leftAxis?.setDrawZeroLine(false)//是否绘制0刻度线
        leftAxis?.setDrawLabels(true) //是否显示y轴的刻度
        leftAxis?.enableGridDashedLine(10f, 10f, 0f)
        leftAxis?.textColor = ContextCompat.getColor(this@MainActivity, R.color.text_color_common)
        leftAxis?.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)//设置文字显示在x轴什么位置
        leftAxis?.labelCount = 4
        leftAxis?.spaceTop = 10f //距离顶部留白


        //X轴
        val xAxis = stock_chart.xAxis
        xAxis.setDrawLabels(true)
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f

        xAxis.gridColor = black;//网格线颜色
        xAxis.textColor = gray;//标签颜色
        xAxis.textSize = 8f;//标签字体大小
        xAxis.axisLineColor = black;//轴线颜色
        xAxis.disableAxisLineDashedLine();//取消轴线虚线设置
        xAxis.setAvoidFirstLastClipping(true);//避免首尾端标签被裁剪
        //转换x轴的数字为文字
        xAxis.valueFormatter = XVolFormatter(mXVals)

    }

    private fun initChart() {
        var sp8: Float = sp2px(8f).toFloat()
        barChart.getDescription().setEnabled(false)
        barChart.setBackgroundColor(ContextCompat.getColor(this, R.color.black3B))
        barChart.setDrawGridBackground(false)
        barChart.setDrawBarShadow(false)
        barChart.setHighlightFullBarEnabled(false)

        //        val l = stock_chart.legend
//        l.isWordWrapEnabled = true
//        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.setDrawInside(false)
        //取消图例
        barChart.legend.isEnabled = false

        //无数据时
        barChart.setNoDataText("No Data")
        barChart.setNoDataTextColor(gray)

        //不允许甩动惯性滑动  和moveView方法有冲突 设置为false
        barChart.isDragDecelerationEnabled = false

        //外边缘偏移量
        barChart.minOffset = 0f

        //设置底部外边缘偏移量 便于显示X轴
        barChart.extraBottomOffset = 6f

        //缩放
        barChart.isScaleXEnabled = true //X轴缩放
        barChart.isScaleYEnabled = false
        barChart.isAutoScaleMinMaxEnabled = true//自适应最大最小值

        // draw bars behind lines
//        barChart.drawOrder = arrayOf(
//            CombinedChart.DrawOrder.BAR,
//            CombinedChart.DrawOrder.LINE
//        )

        val trans: Transformer = barChart.getTransformer(YAxis.AxisDependency.LEFT)
        barChart.rendererLeftYAxis =
            InBoundYAxisRenderer(barChart.viewPortHandler, barChart.axisLeft, trans)

        //设置渲染器控制颜色、偏移，以及高亮
        barChart.setRenderer(
            OffsetBarRenderer(
                barChart,
                barChart.getAnimator(),
                barChart.getViewPortHandler(),
                barOffset
            )
                .setHighlightWidthSize(highlightWidth, sp8)
        )


        //Y轴-右
        val rightAxis = barChart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.isEnabled = false
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        //Y轴-左
        val leftAxis = barChart.axisLeft
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)//标签显示在内侧
        leftAxis.setDrawGridLines(false)
//        leftAxis.gridColor = black
//        leftAxis.gridLineWidth

        leftAxis.isEnabled = false
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        leftAxis.textColor = gray
        leftAxis.textSize = 8f
//        leftAxis.setLabelCount(5, true)
//        leftAxis.enableGridDashedLine(5f, 4f, 0f)//横向网格线设置为虚线


        //X轴
        val xAxis = barChart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.isEnabled = false
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f

        xAxis.gridColor = black;//网格线颜色
        xAxis.textColor = gray;//标签颜色
        xAxis.textSize = 8f;//标签字体大小
        xAxis.axisLineColor = black;//轴线颜色
        xAxis.disableAxisLineDashedLine();//取消轴线虚线设置
        xAxis.setAvoidFirstLastClipping(true);//避免首尾端标签被裁剪
//        xAxis.setLabelCount(2, true);//强制显示2个标签
        //转换x轴的数字为文字
//        xAxis.valueFormatter

    }

    private fun generateBarData(entries1: ArrayList<BarEntry>): BarData? {

        val set1 = BarDataSet(entries1, "Bar 1")
        set1.setDrawIcons(false)

        set1.resetColors()

        set1.addColor(Color.RED)
        set1.addColor(Color.GREEN)
        set1.valueTextColor = Color.rgb(60, 220, 78)
        set1.valueTextSize = 10f
        set1.axisDependency = YAxis.AxisDependency.LEFT

        set1.setDrawValues(false)

        set1.highLightColor = highlightColor

        val barWidth = 0.8f // x2 dataset

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        val d = BarData(dataSets)

        d.barWidth = barWidth
        return d
    }


    fun initData() {
        val candleValues = ArrayList<CandleEntry>()

        val barValues = ArrayList<BarEntry>()
        val stockList = DataRequest.getALL(this@MainActivity) {
            mMaxVolume = it
        }

        var count = 0
        for (bean in stockList) {
            candleValues.add(
                CandleEntry(
                    count.toFloat(),
                    bean.High,
                    bean.Low,
                    bean.Open,
                    bean.Close
                )
            )

            barValues.add(
                BarEntry(
                    count.toFloat(),
                    bean.volume,
                    if (bean.Close >= bean.Open) 0 else 1
                )
            )

            mXVals[count.toFloat()] = bean.date ?: ""

            mMa5Datas.add(Entry(count.toFloat(), bean.mA5Price))
            mMa10Datas.add(Entry(count.toFloat(), bean.MA10Price))
            mMa20Datas.add(Entry(count.toFloat(), bean.MA20Price))
            mMa30Datas.add(Entry(count.toFloat(), bean.MA30Price))

            Log.e("data","${bean.mA5Price}:${bean.mA10Price}:${bean.MA20Price}:${bean.MA30Price}:${bean.MA60Price}")
            mMa60Datas.add(Entry(count.toFloat(), bean.MA60Price))

            count++
        }

        val combinedData = CombinedData()
        stockCandleData = generateCandleData(candleValues)

        val sets = java.util.ArrayList<ILineDataSet>()
        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        sets.add(setMaLine(5, mMa5Datas))
        sets.add(setMaLine(10, mMa10Datas))
        sets.add(setMaLine(20, mMa20Datas))
        sets.add(setMaLine(30, mMa30Datas))
        sets.add(setMaLine(60, mMa60Datas))

        combinedData.setData(stockCandleData)
        combinedData.setData(LineData(sets))


        val xMax: Float = candleValues.size - 0.5f //默认X轴最大值是 xValues.size() - 1
        stock_chart.getXAxis().setAxisMaximum(xMax) //使最后一个显示完整

        stock_chart.data = combinedData
        stock_chart.invalidate()

        val data = CombinedData()
        barData = generateBarData(barValues)
        data.setData(barData)

        barChart.data = barData
        barChart.invalidate()

    }

    private fun generateCandleData(candleValues: ArrayList<CandleEntry>): CandleData {
        val set1 = CandleDataSet(candleValues, "Data Set")

        set1.setDrawIcons(false)
        set1.axisDependency = YAxis.AxisDependency.LEFT
        set1.setDrawHorizontalHighlightIndicator(false)
        set1.highlightLineWidth = highlightWidth
        set1.highLightColor = highlightColor

        set1.color = Color.rgb(80, 80, 80)

        set1.shadowColor = Color.BLACK
        set1.shadowWidth = 0.8f

        set1.decreasingColor = green
        set1.decreasingPaintStyle = Paint.Style.FILL

        set1.increasingColor = red
        set1.increasingPaintStyle = Paint.Style.FILL
        set1.neutralColor = red

        set1.shadowColorSameAsCandle = true
        set1.setDrawValues(false)
        set1.isHighlightEnabled = false

        return CandleData(set1)

    }

    private fun setMaLine(ma: Int, lineEntries: MutableList<Entry>): LineDataSet {
        val lineDataSetMa = LineDataSet(lineEntries, "ma$ma")
        if (ma == 5) {
            lineDataSetMa.isHighlightEnabled = true
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false)
            lineDataSetMa.highLightColor =
                ContextCompat.getColor(this@MainActivity, R.color.marker_line_bg)
        } else {/*此处必须得写*/
            lineDataSetMa.isHighlightEnabled = false
        }
        lineDataSetMa.setDrawValues(false)
        when (ma) {
            5 -> lineDataSetMa.color = ContextCompat.getColor(this@MainActivity, R.color.ma5)
            10 -> lineDataSetMa.color = ContextCompat.getColor(this@MainActivity, R.color.ma10)
            20 -> lineDataSetMa.color = ContextCompat.getColor(this@MainActivity, R.color.ma20)
            30 -> lineDataSetMa.color = ContextCompat.getColor(this@MainActivity, R.color.ma30)
            else -> lineDataSetMa.color = ContextCompat.getColor(this@MainActivity, R.color.ma60)
        }
        lineDataSetMa.lineWidth = 1f
        lineDataSetMa.setDrawCircles(false)
        lineDataSetMa.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSetMa.isHighlightEnabled = false
        return lineDataSetMa
    }




    override fun edgeLoad(x: Float, left: Boolean) {
        //todo 边缘检测
        Log.e("edgeLoad", "$x:$left")
        var v = x.toInt()
//        if (!left && !mXVals.containsKey(v) && mXVals.containsKey(v - 1)) {
//            v = v - 1
//        }
//        val time: String? = mXVals.get(v)
//        if (!TextUtils.isEmpty(time)) {
//            try {
//                val loadingDialog = LoadingDialog.newInstance()
//                loadingDialog.show(this)
//
//            } catch (e: ParseException) {
//                e.printStackTrace()
//            }
//        }
    }

    override fun enableHighlight() {
        Log.e("enableHighlight", "enableHighlight")
//        if (!barData!!.isHighlightEnabled()) {
//        lineData!!.isHighlightEnabled = true
        stockCandleData!!.setHighlightEnabled(true)
        barData!!.setHighlightEnabled(true)


    }

    override fun disableHighlight() {
        Log.e("disableHighlight", "disableHighlight")
        stockCandleData!!.setHighlightEnabled(false)

        barData!!.setHighlightEnabled(false)
        if (ccGesture != null) {
            ccGesture!!.setHighlight(true)
        }
        if (bcGesture != null) {
            bcGesture!!.setHighlight(true)
        }
    }

    private val format4p =
        DecimalFormat("0.0000") //格式化数字，保留小数点后4位

    override fun valueSelected(e: Entry) {
        val x = e.x
        cl_klHighlight.visibility = View.VISIBLE
        tv_line_info.visibility = View.VISIBLE

        val candle: CandleEntry = stockCandleData!!.dataSets[0].getEntryForXValue(x, 0f)
        if (candle != null) {
            tvOpen.setText(format4p.format(candle.open.toDouble()))
            tvOpen.setSelected(candle.open < candle.close)
            tvClose.setText(format4p.format(candle.close.toDouble()))
            tvClose.setSelected(candle.open >= candle.close)
            tvHigh.setText(format4p.format(candle.high.toDouble()))
            tvHigh.setSelected(false)
            tvLow.setText(format4p.format(candle.low.toDouble()))
            tvLow.setSelected(true)

        }

        val bar: BarEntry = barData!!.dataSets[0].getEntryForXValue(x, 0f)
        if (bar != null) {
            tvVol.setText(format4p.format(bar.y.toDouble()))
        }
        tvLine.setVisibility(View.VISIBLE)

    }

    override fun nothingSelected() {
        cl_klHighlight.visibility = View.GONE
        tv_line_info.visibility = View.GONE
        tvLine.setVisibility(View.GONE)
    }

}