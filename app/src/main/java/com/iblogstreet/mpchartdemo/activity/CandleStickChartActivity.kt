package com.iblogstreet.mpchartdemo.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.iblogstreet.mpchartdemo.R
import java.util.*


class CandleStickChartActivity : AppCompatActivity() {

    private lateinit var candleStickChart: CandleStickChart
    private val count = 12
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candle_stick)
        candleStickChart = findViewById(R.id.candle_stick_chart)


        candleStickChart.setHighlightPerDragEnabled(true)

        candleStickChart.setDrawBorders(true)

        candleStickChart.setBorderColor(Color.GRAY)
        candleStickChart.setScaleEnabled(false)

        val yAxis: YAxis = candleStickChart.getAxisLeft()
        val rightAxis: YAxis = candleStickChart.getAxisRight()
        yAxis.setDrawGridLines(false)
        rightAxis.setDrawGridLines(false)

        candleStickChart.requestDisallowInterceptTouchEvent(true)

        val xAxis: XAxis = candleStickChart.getXAxis()

        xAxis.setDrawGridLines(false) // disable x axis grid lines

        xAxis.setDrawLabels(false)
        rightAxis.textColor = Color.WHITE
        yAxis.setDrawLabels(false)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setAvoidFirstLastClipping(true)

        val l: Legend = candleStickChart.getLegend()
        l.isEnabled = false

        val yValsCandleStick = ArrayList<CandleEntry>()

//
//        yValsCandleStick.add(CandleEntry(0f, 300.0f, 219.84f, 224.94f, 280.07f))
//        yValsCandleStick.add(CandleEntry(1f, 228.35f, 222.57f, 223.52f, 226.41f))
//        yValsCandleStick.add(CandleEntry(2f, 226.84f, 222.52f, 225.75f, 223.84f))
//        yValsCandleStick.add(CandleEntry(3f, 222.95f, 217.27f, 222.15f, 217.88f))

//        yValsCandleStick.add(CandleEntry(4f, 222.95f, 222.95f, 222.95f, 222.95f))

//        val set1 = CandleDataSet(yValsCandleStick, "DataSet 1")
//        set1.color = Color.rgb(80, 80, 80)
//        set1.shadowColor = Color.BLACK
//        set1.shadowWidth = 0.8f
//
//        set1.decreasingColor = Color.RED
//        set1.decreasingPaintStyle = Paint.Style.FILL
//        set1.increasingColor = Color.GREEN
//        set1.increasingPaintStyle = Paint.Style.FILL
//        set1.neutralColor = Color.GRAY
//
//        set1.shadowColorSameAsCandle=true
//        set1.setDrawValues(false)
//
//
//        val data = CandleData(set1)
//
//        candleStickChart.setData(data)
//        candleStickChart.invalidate()

        initData()
    }

    private fun initData() {

        val values = ArrayList<CandleEntry>()

        for (i in 0 until 40) {
            val multi: Float = (40 + 1).toFloat()
            val value = (Math.random() * 40).toFloat() + multi
            val high = (Math.random() * 9).toFloat() + 8f
            val low = (Math.random() * 9).toFloat() + 8f
            val open = (Math.random() * 6).toFloat() + 1f
            val close = (Math.random() * 6).toFloat() + 1f
            val even = i % 2 == 0
            values.add(
                CandleEntry(
                    i.toFloat(), value + high,
                    value - low,
                    if (even) value + open else value - open,
                    if (even) value - close else value + close
                )
            )
        }

        val set1 = CandleDataSet(values, "Data Set")

        set1.setDrawIcons(false)
        set1.axisDependency = AxisDependency.LEFT
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

        val data = CandleData(set1)

        candleStickChart.setData(data)
        candleStickChart.invalidate()
    }

    protected fun getRandom(range: Float, start: Float): Float {
        return (Math.random() * range).toFloat() + start
    }


}