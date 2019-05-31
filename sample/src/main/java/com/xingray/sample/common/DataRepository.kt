package com.xingray.sample.common

import android.graphics.Color
import kotlin.random.Random

class DataRepository {
    fun loadData(): List<TestData> {
        val list = mutableListOf<TestData>()
        val r = Random(System.currentTimeMillis())
        val colors = arrayOf(
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
                Color.MAGENTA)
        for (i in 0 until 100) {
            list.add(TestData("test data $i", colors[r.nextInt(colors.size)]))
        }

        return list
    }
}