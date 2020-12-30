package com.iblogstreet.mpchartdemo.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.Transformer
import com.iblogstreet.mpchartdemo.R
import com.iblogstreet.mpchartdemo.bean.StockBean
import com.iblogstreet.mpchartdemo.util.MockUtil
import com.iblogstreet.mpchartdemo.util.StockUtil
import com.iblogstreet.mpchartdemo.view.*
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList


class StockActivity : AppCompatActivity(), CoupleChartGestureListener.OnEdgeListener,
    ChartFingerTouchListener.HighlightListener,
    CoupleChartValueSelectedListener.ValueSelectedListener {

    private lateinit var chart: StockCombinedChart
    private lateinit var stock_chart: StockCombinedChart
//    private lateinit var fl_main_touch: FrameLayout
    private var candleSet: CandleDataSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        chart = findViewById(R.id.chart1)
        stock_chart = findViewById(R.id.stock_chart)
//        fl_main_touch = findViewById(R.id.fl_main_touch)

        initStockChart()

        initChart()

        initData()

        initEvent()
    }

    private var ccGesture: CoupleChartGestureListener? = null
    private var bcGesture: CoupleChartGestureListener? = null
    private fun initEvent() {
//        ChartsControllerOnTouchUtils.bind(fl_main_touch, stock_chart, chart)

//        ccGesture = object : CoupleChartGestureListener(this, stock_chart, chart) {
//            //设置成全局变量，后续要用到
//            override fun chartDoubleTapped(me: MotionEvent?) {
//                doubleTapped()
//            }
//        }
//        stock_chart.setOnChartGestureListener(ccGesture) //设置手势联动监听
//
//        bcGesture = object : CoupleChartGestureListener(this, chart, stock_chart) {
//            override fun chartDoubleTapped(me: MotionEvent?) {
//                doubleTapped()
//            }
//        }
//
//        chart.setOnChartGestureListener(bcGesture)

        stock_chart.setOnChartValueSelectedListener(
            CoupleChartValueSelectedListener(
                this,
                stock_chart,
                chart
            )
        ) //设置高亮联动监听

        stock_chart.setOnChartValueSelectedListener(
            CoupleChartValueSelectedListener(
                this,
                stock_chart,
                chart
            )
        ) //设置高亮联动监听


        chart.setOnChartValueSelectedListener(
            CoupleChartValueSelectedListener(
                this,
                chart,
                stock_chart
            )
        )

        stock_chart.setOnTouchListener(ChartFingerTouchListener(stock_chart, this)) //手指长按滑动高亮

        chart.setOnTouchListener(ChartFingerTouchListener(chart, this))

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

    private fun initStockChart() {
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
        stock_chart.isScaleYEnabled = false
        stock_chart.isAutoScaleMinMaxEnabled = false//自适应最大最小值

        // draw bars behind lines
        stock_chart.drawOrder = arrayOf(
            StockCombinedChart.DrawOrder.CANDLE,
            StockCombinedChart.DrawOrder.LINE
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


    fun sp2px(spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue,
            getResources().getDisplayMetrics()
        ).toInt()
    }

    private fun initChart() {

        chart.getDescription().setEnabled(false)
        chart.setBackgroundColor(Color.WHITE)
        chart.setDrawGridBackground(false)
        chart.setDrawBarShadow(false)
        chart.setHighlightFullBarEnabled(false)


        //        val l = stock_chart.legend
//        l.isWordWrapEnabled = true
//        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.setDrawInside(false)
        //取消图例
        chart.legend.isEnabled = false

        //无数据时
        chart.setNoDataText("No Data")
        chart.setNoDataTextColor(gray)

        //不允许甩动惯性滑动  和moveView方法有冲突 设置为false
        chart.isDragDecelerationEnabled = false

        //外边缘偏移量
        chart.minOffset = 0f
        //设置底部外边缘偏移量 便于显示X轴
        chart.extraBottomOffset = 6f

        //缩放
        chart.isScaleXEnabled = true //X轴缩放
        chart.isScaleYEnabled = false
        chart.isAutoScaleMinMaxEnabled = false//自适应最大最小值

        // draw bars behind lines
        chart.drawOrder = arrayOf(
            StockCombinedChart.DrawOrder.BAR,
            StockCombinedChart.DrawOrder.LINE
        )

        //Y轴-右
        val rightAxis = chart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.isEnabled = false
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        //Y轴-左
        val leftAxis = chart.axisLeft
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
        val xAxis = chart.xAxis
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

    private var barSet: BarDataSet? = null
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


        val barWidth = 0.8f // x2 dataset

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        barSet = set1
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
        combinedData.setData(generateCandleData(candleValues))
        combinedData.setData(generateLineData(lineValues))

        val xMax: Float = candleValues.size - 0.5f //默认X轴最大值是 xValues.size() - 1
        stock_chart.getXAxis().setAxisMaximum(xMax) //使最后一个显示完整

        stock_chart.data = combinedData
        stock_chart.invalidate()

        val data = CombinedData()

        data.setData(generateBarData(barValues))

        chart.data = data
        chart.invalidate()

    }

    private fun generateCandleData(candleValues: ArrayList<CandleEntry>): CandleData {
        val set1 = CandleDataSet(candleValues, "Data Set")

        candleSet=set1

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
        if (!barSet!!.isHighlightEnabled()) {
            candleSet!!.setHighlightEnabled(true)
//            lineSetMin.setHighlightEnabled(true)
            barSet!!.setHighlightEnabled(true)
        }
    }

    override fun disableHighlight() {
        if (barSet!!.isHighlightEnabled()) {
            candleSet!!.setHighlightEnabled(false)
//            lineSetMin.setHighlightEnabled(false)
            barSet!!.setHighlightEnabled(false)
            if (ccGesture != null) {
                ccGesture!!.setHighlight(true)
            }
            if (bcGesture != null) {
                bcGesture!!.setHighlight(true)
            }
        }
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