package com.iblogstreet.mpchartdemo.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Transformer
import com.iblogstreet.mpchartdemo.R
import com.iblogstreet.mpchartdemo.util.DataRequest
import com.iblogstreet.mpchartdemo.view.*
import com.iblogstreet.mpchartdemo.view.chart.MyCombinedChart
import com.iblogstreet.mpchartdemo.view.chart.MyHMarkerView
import com.iblogstreet.mpchartdemo.view.chart.YourMarkerView
import com.loro.klinechart.chart.MyBottomMarkerView
import com.loro.klinechart.chart.MyLeftMarkerView
import com.loro.klinechart.util.XVolFormatter
import java.text.DecimalFormat
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MyCoupleChartGestureListener.OnEdgeListener,
    CoupleChartValueSelectedListener.ValueSelectedListener,
    ChartFingerTouchListener.HighlightListener {

    private lateinit var volume_chart: CombinedChart
    private lateinit var k_line_chart: CombinedChart
    private lateinit var index_chart: CombinedChart

    private lateinit var fl_main_touch: FrameLayout
    private lateinit var cl_klHighlight: ConstraintLayout
    private lateinit var tv_line_info: TextView
    private lateinit var tvOpen: TextView
    private lateinit var tvClose: TextView
    private lateinit var tvHigh: TextView
    private lateinit var tvLow: TextView
    private lateinit var tvVol: TextView
    private lateinit var tvLine: TextView

    private var k_line_data: CandleData? = null
    private var mKlineDatas = mutableListOf<CandleEntry>()
    private var mMa5Datas = mutableListOf<Entry>()
    private var mMa10Datas = mutableListOf<Entry>()
    private var mMa20Datas = mutableListOf<Entry>()
    private var mMa30Datas = mutableListOf<Entry>()
    private var mMa60Datas = mutableListOf<Entry>()

    private var volume_data: BarData? = null
    val mVolumeDatas = ArrayList<BarEntry>()
    private var mVolumeMa5Datas = mutableListOf<Entry>()
    private var mVolumeMa10Datas = mutableListOf<Entry>()

    private var macd_data: BarData? = null

    private var mMacdDatas = mutableListOf<BarEntry>()
    private var mDeaDatas = mutableListOf<Entry>()
    private var mDifDatas = mutableListOf<Entry>()


    private var mMaxVolume = 0f
    private var mXVals = mutableMapOf<Float, String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        setMarkerView()
        setMarkerView(volume_chart)
        setMarkerView(index_chart)

        initStockChart()
        initChart()
        initIndexChart()
        initEvent()
        initData()

    }

    private fun initView() {
        volume_chart = findViewById(R.id.volume_chart)
        k_line_chart = findViewById(R.id.k_line_chart)
        index_chart = findViewById(R.id.index_chart)

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

    private var kLineGesture: MyCoupleChartGestureListener? = null
    private var volumeGesture: MyCoupleChartGestureListener? = null
    private var macdGesture: MyCoupleChartGestureListener? = null
    private fun initEvent() {

        kLineGesture = object : MyCoupleChartGestureListener(
            this, false,
            k_line_chart, volume_chart, index_chart
        ) {
            override fun chartDoubleTapped(me: MotionEvent?) {
                doubleTapped()
            }
        }
        k_line_chart.setOnChartGestureListener(kLineGesture) //设置手势联动监听

        volumeGesture = object : MyCoupleChartGestureListener(
            this, true, volume_chart,
            k_line_chart, index_chart
        ) {
            override fun chartDoubleTapped(me: MotionEvent?) {
                doubleTapped()
            }
        }
        volume_chart.setOnChartGestureListener(volumeGesture)

        macdGesture = object : MyCoupleChartGestureListener(
            this, true, index_chart, volume_chart, k_line_chart
        ) {
            override fun chartDoubleTapped(me: MotionEvent?) {
                doubleTapped()
            }
        }
        index_chart.setOnChartGestureListener(macdGesture)

        k_line_chart.setOnChartValueSelectedListener(
            CoupleChartValueSelectedListener(
                this,
                k_line_chart,
                volume_chart,
                index_chart
            )
        ) //设置高亮联动监听

        volume_chart.setOnChartValueSelectedListener(
            CoupleChartValueSelectedListener(
                this,
                volume_chart,
                k_line_chart,
                index_chart
            )
        )

        index_chart.setOnChartValueSelectedListener(
            CoupleChartValueSelectedListener(
                this,
                index_chart,
                volume_chart,
                k_line_chart
            )
        )

        k_line_chart.setOnTouchListener(ChartFingerTouchListener(k_line_chart, this)) //手指长按滑动高亮

        volume_chart.setOnTouchListener(ChartFingerTouchListener(volume_chart, this))
        index_chart.setOnTouchListener(ChartFingerTouchListener(index_chart, this))

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

    fun sp2px(spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue,
            resources.displayMetrics
        ).toInt()
    }

    private fun initStockChart() {

        var sp8: Float = sp2px(8f).toFloat()
        k_line_chart.description.isEnabled = false
        k_line_chart.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        k_line_chart.setDrawGridBackground(false)
        k_line_chart.setDrawBarShadow(false)
        k_line_chart.isHighlightFullBarEnabled = false

        //取消图例
        k_line_chart.legend.isEnabled = false

        //无数据时
        k_line_chart.setNoDataText("No Data")
        k_line_chart.setNoDataTextColor(gray)

        //不允许甩动惯性滑动  和moveView方法有冲突 设置为false
        k_line_chart.isDragDecelerationEnabled = false


        //外边缘偏移量
        k_line_chart.minOffset = 0f
        //设置底部外边缘偏移量 便于显示X轴
//        stock_chart.extraBottomOffset = 6f

        k_line_chart.setExtraOffsets(0f, 0f, 0f, 3f)

        k_line_chart.setBorderWidth(1f)
        k_line_chart.setBorderColor(ContextCompat.getColor(this@MainActivity, R.color.border_color))

        //缩放
        k_line_chart.isScaleXEnabled = true
        k_line_chart.isAutoScaleMinMaxEnabled = true//自适应最大最小值

        // draw bars behind lines
        k_line_chart.drawOrder = arrayOf(
            CombinedChart.DrawOrder.CANDLE,
            CombinedChart.DrawOrder.LINE
        )

        val trans: Transformer = k_line_chart.getTransformer(YAxis.AxisDependency.LEFT)
        k_line_chart.rendererLeftYAxis =
            InBoundYAxisRenderer(k_line_chart.viewPortHandler, k_line_chart.axisLeft, trans)
        k_line_chart.setXAxisRenderer(
            InBoundXAxisRenderer(
                k_line_chart.viewPortHandler,
                k_line_chart.xAxis,
                trans,
                10
            )
        )

        //自定义渲染器 重绘高亮
        k_line_chart.renderer = HighlightCombinedRenderer(
            k_line_chart,
            k_line_chart.animator,
            k_line_chart.viewPortHandler,
            sp8
        )

        //Y轴-右
        val rightAxis = k_line_chart.axisRight
        rightAxis.isEnabled = false
//        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        rightAxis?.setDrawLabels(false)
        rightAxis?.setDrawGridLines(false)
        rightAxis?.setDrawAxisLine(false)
        rightAxis?.labelCount = 4

        //Y轴-左
        val leftAxis = k_line_chart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)//标签显示在内侧
        leftAxis.setDrawGridLines(false)
//        leftAxis.gridColor = black
//        leftAxis.gridLineWidth

//        leftAxis.isEnabled = false
//        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        leftAxis.textColor = gray
        leftAxis.textSize = 8f
//        leftAxis.setLabelCount(5, true)

        leftAxis?.setDrawGridLines(false)//是否显示Y坐标轴上的刻度横线，默认是true
        leftAxis?.setDrawAxisLine(false)//是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        leftAxis?.setDrawZeroLine(false)//是否绘制0刻度线
        leftAxis?.setDrawLabels(true) //是否显示y轴的刻度
        leftAxis?.enableGridDashedLine(10f, 10f, 0f)
        leftAxis?.textColor = ContextCompat.getColor(this@MainActivity, R.color.text_color_common)
        leftAxis?.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)//设置文字显示在x轴什么位置
        leftAxis?.labelCount = 4
        leftAxis?.spaceTop = 10f //距离顶部留白


        //X轴
        val xAxis = k_line_chart.xAxis
        xAxis.setDrawGridLines(true)
        xAxis.setDrawLabels(true)
//        xAxis.setDrawAxisLine(false)
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis?.enableGridDashedLine(
            10f,
            10f,
            0f
        )
//        xAxis.granularity = 1f

        xAxis.gridColor = black;//网格线颜色
        xAxis.textColor = gray;//标签颜色
        xAxis.textSize = 8f;//标签字体大小
        xAxis.axisLineColor = black;//轴线颜色
        xAxis.disableAxisLineDashedLine();//取消轴线虚线设置
        xAxis.setAvoidFirstLastClipping(true);//避免首尾端标签被裁剪
        //转换x轴的数字为文字
        xAxis.valueFormatter = XVolFormatter(mXVals)

        k_line_chart.animateXY(2000, 2000)
    }

    private fun initChart() {
        var sp8: Float = sp2px(8f).toFloat()
        volume_chart.getDescription().setEnabled(false)
        volume_chart.setBackgroundColor(Color.WHITE)
        volume_chart.setDrawGridBackground(false)
        volume_chart.setDrawBarShadow(false)
        volume_chart.setHighlightFullBarEnabled(false)
        volume_chart.setDrawBorders(false)

        //        val l = stock_chart.legend
//        l.isWordWrapEnabled = true
//        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.setDrawInside(false)
        //取消图例
        volume_chart.legend.isEnabled = false

        //无数据时
        volume_chart.setNoDataText("No Data")
        volume_chart.setNoDataTextColor(gray)

        //不允许甩动惯性滑动  和moveView方法有冲突 设置为false
        volume_chart.isDragDecelerationEnabled = false

        //外边缘偏移量
//        volume_chart.minOffset = 0f

        //设置底部外边缘偏移量 便于显示X轴
//        volume_chart.extraBottomOffset = 6f

        volume_chart.minOffset = 3f
        volume_chart.setExtraOffsets(0f, 0f, 0f, 5f)

        //缩放
        volume_chart.isScaleXEnabled = true //X轴缩放
        volume_chart.isScaleYEnabled = false
        volume_chart.isAutoScaleMinMaxEnabled = true//自适应最大最小值

        // draw bars behind lines
        volume_chart.drawOrder = arrayOf(
            CombinedChart.DrawOrder.BAR,
            CombinedChart.DrawOrder.LINE
        )

        val trans: Transformer = volume_chart.getTransformer(YAxis.AxisDependency.LEFT)
        volume_chart.rendererLeftYAxis =
            InBoundYAxisRenderer(volume_chart.viewPortHandler, volume_chart.axisLeft, trans)

        //自定义渲染器 重绘高亮
        volume_chart.renderer = HighlightCombinedRenderer(
            volume_chart,
            volume_chart.animator,
            volume_chart.viewPortHandler,
            sp8
        )

        //设置渲染器控制颜色、偏移，以及高亮
//        barChart.setRenderer(
//            OffsetBarRenderer(
//                barChart,
//                barChart.getAnimator(),
//                barChart.getViewPortHandler(),
//                barOffset
//            )
//                .setHighlightWidthSize(highlightWidth, sp8)
//        )


        //Y轴-右
        val rightAxis = volume_chart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.isEnabled = false
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        //Y轴-左
        val leftAxis = volume_chart.axisLeft
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
        val xAxis = volume_chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.isEnabled = false
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f

        xAxis.setDrawGridLines(true)
        xAxis.setDrawAxisLine(false)

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


    private fun initIndexChart() {
        index_chart.setScaleEnabled(true)//启用图表缩放事件
        index_chart.setDrawBorders(true)//是否绘制边线
        index_chart.setBorderWidth(1f)//边线宽度，单位dp
        index_chart.isDragEnabled = true//启用图表拖拽事件
        index_chart.isScaleYEnabled = false//启用Y轴上的缩放
        index_chart.setBorderColor(resources.getColor(R.color.border_color))//边线颜色
        index_chart.description.isEnabled = false //禁用图表描述
        index_chart.description.text = ""//图表描述文字
        index_chart.minOffset = 0f
        index_chart.setExtraOffsets(0f, 0f, 0f, 3f)
//        indexChart.isHighlightFullBarEnabled = true
        val lineChartLegend = index_chart.legend
        lineChartLegend.isEnabled = false//是否绘制 Legend 图例

        //bar x y轴
        val xAxisIndex = index_chart.xAxis
        xAxisIndex?.isEnabled = false

        val yAxisLeftIndex = index_chart.axisLeft
        yAxisLeftIndex?.setDrawGridLines(true)
        yAxisLeftIndex?.setDrawAxisLine(false)
        yAxisLeftIndex?.setDrawLabels(true)
        yAxisLeftIndex?.enableGridDashedLine(10f, 10f, 0f)
        yAxisLeftIndex?.textColor =
            ContextCompat.getColor(this@MainActivity, R.color.text_color_common)
        yAxisLeftIndex?.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxisLeftIndex?.setLabelCount(1, false) //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布


        val yAxisRightIndex = index_chart.axisRight
        yAxisRightIndex?.setDrawLabels(false)
        yAxisRightIndex?.setDrawGridLines(false)
        yAxisRightIndex?.setDrawAxisLine(false)

        index_chart.isDragDecelerationEnabled = true
        index_chart.dragDecelerationFrictionCoef = 0.2f

        val trans: Transformer = index_chart.getTransformer(YAxis.AxisDependency.LEFT)
        index_chart.rendererLeftYAxis =
            InBoundYAxisRenderer(index_chart.viewPortHandler, index_chart.axisLeft, trans)

        var sp8: Float = sp2px(8f).toFloat()
        //自定义渲染器 重绘高亮
        index_chart.renderer = HighlightCombinedRenderer(
            index_chart,
            index_chart.animator,
            index_chart.viewPortHandler,
            sp8
        )

        index_chart.animateXY(2000, 2000)
    }

    private fun generateBarData(entries1: ArrayList<BarEntry>): BarData? {

        val set1 = BarDataSet(entries1, "成交量")
        set1.setDrawIcons(false)

        set1.resetColors()

        set1.addColor(Color.RED)
        set1.addColor(Color.GREEN)

        set1.isHighlightEnabled = true
        set1.highLightAlpha = 255
        set1.highLightColor = ContextCompat.getColor(this@MainActivity, R.color.marker_line_bg)

        set1.setDrawValues(false)

//        val barWidth = 1f // x2 dataset

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        val d = BarData(dataSets)

//        d.barWidth = barWidth
        return d
    }


    fun initData() {


        val stockList = DataRequest.getALL(this@MainActivity) {
            mMaxVolume = it
        }

        var count = 0
        for (bean in stockList) {
            mKlineDatas.add(
                CandleEntry(
                    count.toFloat(),
                    bean.High,
                    bean.Low,
                    bean.Open,
                    bean.Close,
                    if (count == 1) 1 else if (count == 50) 2 else 0
                )
            )

            mVolumeDatas.add(
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


            mMa60Datas.add(Entry(count.toFloat(), bean.MA60Price))

            mVolumeMa5Datas.add(Entry(count.toFloat(), bean.mA5Volume))
            mVolumeMa10Datas.add(Entry(count.toFloat(), bean.mA10Volume))

            mMacdDatas.add(BarEntry(count.toFloat(), bean.macd))
            mDeaDatas.add(Entry(count.toFloat(), bean.dea))
            mDifDatas.add(Entry(count.toFloat(), bean.dif))

            count++
        }

        setKlineChart()
        setValumeChart()
        setMACDByChart()

    }

    private fun setValumeChart() {
        val bars = java.util.ArrayList<ILineDataSet>()

        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题 */
        bars.add(setMaLine(5, mVolumeMa5Datas))
        bars.add(setMaLine(10, mVolumeMa10Datas))

        val data = CombinedData()
        volume_data = generateBarData(mVolumeDatas)
        data.setData(volume_data)

        data.setData(LineData(bars))
        volume_chart.data = data
//        volume_chart.invalidate()
        setHandler(volume_chart)
    }

    private fun setKlineChart() {

        k_line_chart.xAxis.valueFormatter = XVolFormatter(mXVals)

        val set = CandleDataSet(mKlineDatas, "")


        set.setDrawHorizontalHighlightIndicator(false)
        set.isHighlightEnabled = true
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.shadowWidth = 1f
        set.valueTextSize = 10f
        set.decreasingColor =
            ContextCompat.getColor(this@MainActivity, R.color.decreasing_color)//设置开盘价高于收盘价的颜色
        set.decreasingPaintStyle = Paint.Style.FILL
        set.increasingColor =
            ContextCompat.getColor(this@MainActivity, R.color.increasing_color)//设置开盘价地狱收盘价的颜色
        set.increasingPaintStyle = Paint.Style.FILL
        set.neutralColor =
            ContextCompat.getColor(this@MainActivity, R.color.increasing_color)//设置开盘价等于收盘价的颜色
        set.shadowColorSameAsCandle = true
//        set.highlightLineWidth = 1f
        set.highLightColor = ContextCompat.getColor(this@MainActivity, R.color.marker_line_bg)
        set.setDrawValues(true)
        set.valueTextColor = ContextCompat.getColor(this@MainActivity, R.color.marker_text_bg)

        val sets = java.util.ArrayList<ILineDataSet>()
        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        sets.add(setMaLine(5, mMa5Datas))
        sets.add(setMaLine(10, mMa10Datas))
        sets.add(setMaLine(20, mMa20Datas))
        sets.add(setMaLine(30, mMa30Datas))
        sets.add(setMaLine(60, mMa60Datas))

        k_line_data = CandleData(set)
        k_line_data!!.setDrawValues(true)
        val data = CombinedData()
        data.setData(k_line_data)
        data.setData(LineData(sets))

        k_line_chart.data = data

        setHandler(k_line_chart)
    }


    private fun setMACDByChart() {
        val set = BarDataSet(mMacdDatas, "BarDataSet")
        set.isHighlightEnabled = true
        set.highLightAlpha = 255
        set.highLightColor = ContextCompat.getColor(this@MainActivity, R.color.marker_line_bg)
        set.setDrawValues(false)
        set.axisDependency = YAxis.AxisDependency.LEFT
        val list = java.util.ArrayList<Int>()
        list.add(ContextCompat.getColor(this@MainActivity, R.color.increasing_color))
        list.add(ContextCompat.getColor(this@MainActivity, R.color.decreasing_color))
        set.colors = list

        macd_data = BarData(set)

        val sets = java.util.ArrayList<ILineDataSet>()
        sets.add(setMACDMaLine(0, mDeaDatas))
        sets.add(setMACDMaLine(1, mDifDatas))
        val lineData = LineData(sets)

        val combinedData = CombinedData()
        combinedData.setData(macd_data)
        combinedData.setData(lineData)
        index_chart.data = combinedData
        setHandler(index_chart)
//        index_chart.invalidate()

    }

    private fun setHandler(combinedChart: CombinedChart) {
        val viewPortHandlerBar = combinedChart.viewPortHandler
        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(mKlineDatas.size.toFloat()))
        val touchMatrix = viewPortHandlerBar.matrixTouch
        val xScale = 3f
        touchMatrix.postScale(xScale, 1f)
    }

    private fun culcMaxscale(count: Float): Float {
        var max = 1f
        max = count / 127 * 5
        return max
    }

    private fun setMACDMaLine(type: Int, lineEntries: MutableList<Entry>): LineDataSet {
        val lineDataSetMa = LineDataSet(lineEntries, "ma$type")
        lineDataSetMa.isHighlightEnabled = false
        lineDataSetMa.setDrawValues(false)

        //DEA
        if (type == 0) {
            lineDataSetMa.color = ContextCompat.getColor(this@MainActivity, R.color.ma5)
        } else {
            lineDataSetMa.color = ContextCompat.getColor(this@MainActivity, R.color.ma10)
        }

        lineDataSetMa.lineWidth = 1f
        lineDataSetMa.setDrawCircles(false)
        lineDataSetMa.axisDependency = YAxis.AxisDependency.LEFT

        return lineDataSetMa
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
        k_line_data!!.isHighlightEnabled = true
        volume_data!!.isHighlightEnabled = true
        macd_data!!.isHighlightEnabled = true

    }

    override fun disableHighlight() {
        Log.e("disableHighlight", "disableHighlight")
        k_line_data!!.isHighlightEnabled = false

        volume_data!!.isHighlightEnabled = false
        macd_data!!.isHighlightEnabled = false
        if (kLineGesture != null) {
            kLineGesture!!.setHighlight(true)
        }
        if (volumeGesture != null) {
            volumeGesture!!.setHighlight(true)
        }
    }

    private val format4p =
        DecimalFormat("0.0000") //格式化数字，保留小数点后4位

    override fun valueSelected(e: Entry) {
        val x = e.x
        cl_klHighlight.visibility = View.VISIBLE
        tv_line_info.visibility = View.VISIBLE

        val candle: CandleEntry = k_line_data!!.dataSets[0].getEntryForXValue(x, 0f)
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

        val bar: BarEntry = volume_data!!.dataSets[0].getEntryForXValue(x, 0f)
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

    private fun setMarkerView() {
//        val leftMarkerView = YourMarkerView(this@MainActivity, R.layout.markerview_kline)
//        val hMarkerView = YourMarkerView(this@MainActivity, R.layout.markerview_kline)
////        k_line_chart.marker = hMarkerView
////        val bottomMarkerView = MyBottomMarkerView(this@MainActivity, R.layout.markerview_kline)
//        k_line_chart.setMarker(hMarkerView)
    }

    private fun setMarkerView(combinedChart: CombinedChart) {
//        val leftMarkerView = MyLeftMarkerView(this@MainActivity, R.layout.markerview_kline)
//        val hMarkerView = YourMarkerView(this@MainActivity, R.layout.markerview_kline)
//        combinedChart.setMarker( hMarkerView)
//        combinedChart.marker = hMarkerView
    }


}