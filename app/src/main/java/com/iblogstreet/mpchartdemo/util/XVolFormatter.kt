package com.iblogstreet.mpchartdemo.util


import com.github.mikephil.charting.formatter.ValueFormatter

class XVolFormatter(private val xVols: MutableMap<Float, String>) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return xVols[value]!!
    }

}
