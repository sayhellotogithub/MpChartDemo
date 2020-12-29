package com.iblogstreet.mpchartdemo

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

//        val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//        val string1 = "2001-07-04T12:08:56.235-0700"
//        val result1: Date = df1.parse(string1)
//
//        print(result1)
//        val df2: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
//        val string2 = "2001-07-04T12:08:56.235-07:00"
//        val result2: Date = df2.parse(string2)

//        print(result2)
        val f = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        println(f.format(Date()))
    }
}