package com.iblogstreet.mpchartdemo.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.Transformer
import com.iblogstreet.mpchartdemo.R
import com.iblogstreet.mpchartdemo.bean.StockBean
import com.iblogstreet.mpchartdemo.util.ChartsControllerOnTouchUtils
import com.iblogstreet.mpchartdemo.util.MockUtil
import com.iblogstreet.mpchartdemo.util.StockUtil
import com.iblogstreet.mpchartdemo.view.*
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), CoupleChartGestureListener.OnEdgeListener,
    ChartsControllerOnTouchUtils.HighlightListener,
    CoupleChartValueSelectedListener.ValueSelectedListener {

    private lateinit var barChart: BarChart
    private lateinit var stock_chart: CombinedChart
    private lateinit var fl_main_touch: FrameLayout

    private var stockCandleData: CandleData? = null
    private var lineData: LineData? = null
    private var barData: BarData? = null
    private lateinit var touchUtils: ChartsControllerOnTouchUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barChart = findViewById(R.id.bar_chart)
        stock_chart = findViewById(R.id.stock_chart)
        fl_main_touch = findViewById(R.id.fl_main_touch)
        touchUtils = ChartsControllerOnTouchUtils()

        initStockChart()
        initChart()
        initData()
        initEvent()
    }

    private var ccGesture: CoupleChartGestureListener? = null
    private var bcGesture: CoupleChartGestureListener? = null
    private fun initEvent() {
        touchUtils.bind(this, fl_main_touch, stock_chart, barChart)

//        ccGesture = object : CoupleChartGestureListener(this, stock_chart, barChart) {
//            override fun chartDoubleTapped(me: MotionEvent?) {
//                doubleTapped()
//            }
//        }
//        stock_chart.setOnChartGestureListener(ccGesture) //设置手势联动监听
//
//        bcGesture = object : CoupleChartGestureListener(this, barChart, stock_chart) {
//            override fun chartDoubleTapped(me: MotionEvent?) {
//                doubleTapped()
//            }
//        }
//        barChart.setOnChartGestureListener(bcGesture)

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
    private val barOffset = -0.5f //BarChart偏移量

    fun sp2px(spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue,
            resources.displayMetrics
        ).toInt()
    }

    private fun initStockChart() {
        var sp8: Float = sp2px(8f).toFloat()
        stock_chart.getDescription().setEnabled(false)
        stock_chart.setBackgroundColor(Color.WHITE)
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
        stock_chart.extraBottomOffset = 6f

        //缩放
        stock_chart.setScaleXEnabled(true) //X轴缩放
        stock_chart.isScaleYEnabled = true
        stock_chart.isAutoScaleMinMaxEnabled = true//自适应最大最小值

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
                stock_chart.getViewPortHandler(),
                stock_chart.getXAxis(),
                trans,
                10
            )
        )

        //自定义渲染器 重绘高亮
        stock_chart.setRenderer(
            HighlightCombinedRenderer(
                stock_chart,
                stock_chart.getAnimator(),
                stock_chart.getViewPortHandler(),
                sp8
            )
        )

        //Y轴-右
        val rightAxis = stock_chart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.isEnabled = false
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

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


        //X轴
        val xAxis = stock_chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
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

    private fun initChart() {
        var sp8: Float = sp2px(8f).toFloat()
        barChart.getDescription().setEnabled(false)
        barChart.setBackgroundColor(Color.WHITE)
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
//        barChart.setRenderer(
//            OffsetBarRenderer(barChart, barChart.getAnimator(), bc.getViewPortHandler(), barOffset)
//                .setHighlightWidthSize(highlightWidth, sp8)
//        )
//

//        barChart.setXAxisRenderer(
//            InBoundXAxisRenderer(
//                barChart.getViewPortHandler(),
//                barChart.getXAxis(),
//                trans,
//                10
//            )
//        )
//        //自定义渲染器 重绘高亮
//        barChart.setRenderer(
//            HighlightCombinedRenderer(
//                barChart,
//                barChart.getAnimator(),
//                barChart.getViewPortHandler(),
//                sp8
//            )
//        )

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
        val lineValues = ArrayList<Entry>()
        val barValues = ArrayList<BarEntry>()

        val stockList = MockUtil.getModel(StockUtil.STOKC_DATA, StockBean::class.java)
        var count = 0
        for (bean in stockList.data) {
            candleValues.add(
                CandleEntry(
                    count.toFloat(),
                    bean.high,
                    bean.low,
                    bean.open,
                    bean.close
                )
            )

            lineValues.add(Entry(count.toFloat(), bean.close))

            barValues.add(
                BarEntry(
                    count.toFloat(),
                    bean.volume,
                    if (bean.close >= bean.open) 0 else 1
                )
            )
            xValues.put(count, bean.date)

            count++
        }

        val combinedData = CombinedData()
        stockCandleData = generateCandleData(candleValues)

        lineData = generateLineData(lineValues)

        combinedData.setData(stockCandleData)
        combinedData.setData(lineData)

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

    private fun generateLineData(entries: List<Entry>): LineData {
        val lineData = LineData()
        val set = LineDataSet(entries, "Line DataSet")
        set.color = Color.GRAY
        set.lineWidth = 0.2f

//        set.setCircleColor(Color.rgb(240, 238, 70))
//        set.circleRadius = 5f
//        set.fillColor = Color.rgb(240, 238, 70)
        set.setDrawCircles(false)
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
        set.setDrawValues(true)
        set.valueTextSize = 3f
        set.valueTextColor = Color.rgb(240, 238, 70)
        set.axisDependency = YAxis.AxisDependency.LEFT

        lineData.addDataSet(set)

        return lineData
    }

    private val xValues = HashMap<Int, String?>()
    override fun edgeLoad(x: Float, left: Boolean) {
        Log.e("edgeLoad", "$x:$left")
        var v = x.toInt()
        if (!left && !xValues.containsKey(v) && xValues.containsKey(v - 1)) {
            v = v - 1
        }
        val time: String? = xValues.get(v)
        if (!TextUtils.isEmpty(time)) {
            try {
//                val t: Long = sdf.parse(time).getTime()
//                if (!left) { //向右获取数据时判断时间间隔
//                    val interval: Long =
//                        KL_INTERVAL.get(tabLayout.getSelectedTabPosition()) * M1
//                    if (System.currentTimeMillis() - t < interval) { //不会有新数据
//                        return
//                    }
//                }
                val loadingDialog = LoadingDialog.newInstance()
                loadingDialog.show(this)
//                toLeft = left
//                getData(t * 1000000L.toString() + "")

            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
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
//        stockCandleData!!.setHighlightEnabled(false)
//        lineData?.setHighlightEnabled(false)
//        barData!!.setHighlightEnabled(false)
//        if (ccGesture != null) {
//            ccGesture!!.setHighlight(true)
//        }
//        if (bcGesture != null) {
//            bcGesture!!.setHighlight(true)
//        }
    }

    override fun valueSelected(e: Entry) {
//        val x = e.x
//        clHl.setVisibility(View.VISIBLE)
//        val candle: CandleEntry = candleSet.getEntryForXValue(x, 0f)
//        if (candle != null) {
//            tvOpen.setText(format4p.format(candle.open.toDouble()))
//            tvOpen.setSelected(candle.open < candle.close)
//            tvClose.setText(format4p.format(candle.close.toDouble()))
//            tvClose.setSelected(candle.open >= candle.close)
//            tvHigh.setText(format4p.format(candle.high.toDouble()))
//            tvHigh.setSelected(false)
//            tvLow.setText(format4p.format(candle.low.toDouble()))
//            tvLow.setSelected(true)
//        }
//        val bar: BarEntry = barSet.getEntryForXValue(x, 0f)
//        if (bar != null) {
//            tvVol.setText(format4p.format(bar.y.toDouble()))
//        }
//        if (tabLayout.getSelectedTabPosition() !== 0) {
//            val line5: Entry = lineSet5.getEntryForXValue(x, 0f)
//            val line10: Entry =
//                lineSet10.getEntryForXValue(x, 0f)
//            if (line5 != null && line10 != null) {
//                tvLine.setVisibility(View.VISIBLE)
//                val line: String = kotlin.String.format(
//                    Kline5_10, format4p.format(line5.y.toDouble()),
//                    format4p.format(line10.y.toDouble())
//                )
//                tvLine.setText(MainActivity.fromHtml(line))
//            }
//        }
    }

    override fun nothingSelected() {
//        clHl.setVisibility(View.GONE)
//        tvLine.setVisibility(View.GONE)
    }

}