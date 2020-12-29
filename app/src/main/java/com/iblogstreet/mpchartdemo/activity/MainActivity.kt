package com.iblogstreet.mpchartdemo.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.iblogstreet.mpchartdemo.R
import com.iblogstreet.mpchartdemo.bean.StockBean
import com.iblogstreet.mpchartdemo.util.ChartsControllerOnTouchUtils
import com.iblogstreet.mpchartdemo.util.MockUtil
import com.iblogstreet.mpchartdemo.util.StockUtil
import com.iblogstreet.mpchartdemo.view.OffsetBarRenderer
import com.iblogstreet.mpchartdemo.view.StockCombinedChart

class MainActivity : AppCompatActivity() {

    private lateinit var chart: StockCombinedChart
    private lateinit var stock_chart: StockCombinedChart
    private lateinit var fl_main_touch: FrameLayout
    private val count = 40
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chart = findViewById(R.id.chart1)
        stock_chart = findViewById(R.id.stock_chart)
        fl_main_touch = findViewById(R.id.fl_main_touch)

        initStockChart()

        initChart()

        initData()

        initEvent()
    }

    private fun initEvent() {
        chart.onChartGestureListener = object : OnChartGestureListener {
            override fun onChartGestureEnd(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {
            }

            override fun onChartFling(
                me1: MotionEvent?,
                me2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ) {

            }

            override fun onChartSingleTapped(me: MotionEvent?) {
            }

            override fun onChartGestureStart(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {
            }

            override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
            }

            override fun onChartLongPressed(me: MotionEvent?) {
            }

            override fun onChartDoubleTapped(me: MotionEvent?) {
            }

            override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
            }

        }

        ChartsControllerOnTouchUtils.bind(fl_main_touch, stock_chart, chart)
    }

    private fun initStockChart() {
        stock_chart.getDescription().setEnabled(false)
        stock_chart.setBackgroundColor(Color.WHITE)
        stock_chart.setDrawGridBackground(false)
        stock_chart.setDrawBarShadow(false)
        stock_chart.setHighlightFullBarEnabled(false)
        stock_chart.setScaleXEnabled(true)
        stock_chart.isScaleYEnabled=false


        // draw bars behind lines
        stock_chart.drawOrder = arrayOf(
            StockCombinedChart.DrawOrder.BAR,
            StockCombinedChart.DrawOrder.BUBBLE,
            StockCombinedChart.DrawOrder.CANDLE,
            StockCombinedChart.DrawOrder.LINE,
            StockCombinedChart.DrawOrder.SCATTER
        )

        val l = stock_chart.legend
        l.isWordWrapEnabled = true
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)

        val rightAxis = stock_chart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.isEnabled = false
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


        val leftAxis = stock_chart.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.isEnabled = false
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


        val xAxis = stock_chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f


    }

    private val barOffset = -0.5f //BarChart偏移量
    private var highlightWidth = 0.5f
    fun sp2px(spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue,
            getResources().getDisplayMetrics()
        ).toInt()
    }

    private fun initChart() {
        val sp8: Float = sp2px(8f).toFloat()

        chart.getDescription().setEnabled(false)
        chart.setBackgroundColor(Color.WHITE)
        chart.setDrawGridBackground(false)
        chart.setScaleXEnabled(true)
        chart.isScaleYEnabled=false
        chart.setDrawBarShadow(false)
        chart.setHighlightFullBarEnabled(false)

        //设置渲染器控制颜色、偏移，以及高亮
//        chart.setRenderer(
//            OffsetBarRenderer(chart, chart.getAnimator(), chart.getViewPortHandler(), barOffset)
//                .setHighlightWidthSize(highlightWidth, sp8)
//        )

        // draw bars behind lines
        chart.drawOrder = arrayOf(
            StockCombinedChart.DrawOrder.BAR,
            StockCombinedChart.DrawOrder.BUBBLE,
            StockCombinedChart.DrawOrder.CANDLE,
            StockCombinedChart.DrawOrder.LINE,
            StockCombinedChart.DrawOrder.SCATTER
        )

        val l = chart.legend
        l.isWordWrapEnabled = true
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)

        val rightAxis = chart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        rightAxis.isEnabled = false


        val leftAxis = chart.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.isEnabled = false
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


        val xAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f


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


        val barWidth = 0.9f // x2 dataset

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

            barValues.add(BarEntry(count.toFloat(), bean.volume, if (bean.close >= bean.open) 0 else 1))
            count++
        }

        val combinedData = CombinedData()
        combinedData.setData(generateCandleData(candleValues))
        combinedData.setData(generateLineData(lineValues))

        stock_chart.data = combinedData
        stock_chart.invalidate()

        val data = CombinedData()

        data.setData(generateBarData(barValues))

        chart.data = data
        chart.invalidate()

    }

    private fun generateCandleData(candleValues: ArrayList<CandleEntry>): CandleData {
        val set1 = CandleDataSet(candleValues, "Data Set")

        set1.setDrawIcons(false)
        set1.axisDependency = YAxis.AxisDependency.LEFT
//        set1.setColor(Color.rgb(80, 80, 80));
        set1.color = Color.rgb(80, 80, 80)
        set1.shadowColor = Color.BLACK
        set1.shadowWidth = 0.8f

        set1.decreasingColor = Color.RED
        set1.decreasingPaintStyle = Paint.Style.FILL
        set1.increasingColor = Color.GREEN
        set1.increasingPaintStyle = Paint.Style.FILL
        set1.neutralColor = Color.GRAY

        set1.shadowColorSameAsCandle = true
        set1.setDrawValues(false)

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
}