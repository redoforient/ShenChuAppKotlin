package com.shenchu.kotlin.app

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun arrayTest(){
        val asList = asList("张三", "李四", "王五");
        println("列表结果>>>$asList")
    }


    private fun <T> asList(vararg ts: T): List<T> {
        val arrayList = ArrayList<T>();
        for (t in ts) {
            arrayList.add(t)
        }
        return arrayList
    }
}