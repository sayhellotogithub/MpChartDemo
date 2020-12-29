package com.iblogstreet.mpchartdemo.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.iblogstreet.mpchartdemo.R
import com.iblogstreet.mpchartdemo.util.DayAxisValueFormatter
import com.iblogstreet.mpchartdemo.util.MyAxisValueFormatter
import com.iblogstreet.mpchartdemo.view.StockBarChart
import java.util.*


class BarChartActivity : AppCompatActivity() {

    private lateinit var chart: StockBarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        chart = findViewById(R.id.bar_chart)
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)

        chart.getDescription().setEnabled(false)

        chart.setMaxVisibleValueCount(60)
        chart.setPinchZoom(false)

        chart.setDrawGridBackground(false)

        val xAxisFormatter = DayAxisValueFormatter(chart)

        val xAxis: XAxis = chart.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM

        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day

        xAxis.labelCount = 7
        xAxis.setValueFormatter(xAxisFormatter)

        val custom = MyAxisValueFormatter()

        val leftAxis: YAxis = chart.getAxisLeft()

        leftAxis.setLabelCount(8, false)
        leftAxis.valueFormatter = custom
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


        val rightAxis: YAxis = chart.getAxisRight()
        rightAxis.setDrawGridLines(false)

        rightAxis.setLabelCount(8, false)
        rightAxis.valueFormatter = custom
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


        val l: Legend = chart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = LegendForm.SQUARE
        l.formSize = 9f
        l.textSize = 11f
        l.xEntrySpace = 4f

        setData(10, 40f)
    }

    private fun setData(count: Int, range: Float) {
        val start = 1f
        val values = ArrayList<BarEntry>()
        var i = start.toInt()
        while (i < start + count) {
            val value = (Math.random() * (range + 1)).toFloat()
            values.add(BarEntry(i.toFloat(), value))
            i++
        }
        val set1: BarDataSet
        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "The year 2017")
            set1.setDrawIcons(false)

            set1.resetColors()

            set1.addColor(Color.RED)
            set1.addColor(Color.GREEN)

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = 0.9f
            chart.data = data
        }
    }

}