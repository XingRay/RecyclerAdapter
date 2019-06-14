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
        var value = -1
        for (i in 0 until 100) {
            var randomInt = r.nextInt(colors.size)
            if (randomInt == value) {
                randomInt = (randomInt + 1) % colors.size
            }
            value = randomInt
            list.add(TestData("test data $i", colors[randomInt]))
        }

        return list
    }

    fun loadData1(): List<TestData1> {
        val list = mutableListOf<TestData1>()
        val r = Random(System.currentTimeMillis())
        val size = arrayOf(
                16.0f,
                18.0f,
                20.0f,
                22.0f,
                24.0f,
                26.0f,
                28.0f,
                30.0f,
                32.0f,
                34.0f,
                36.0f
        )
        var value = -1

        for (i in 0 until 100) {
            var randomInt = r.nextInt(size.size)
            if (randomInt == value) {
                randomInt = (randomInt + 1) % size.size
            }
            value = randomInt
            list.add(TestData1(1, "test data $i", size[randomInt]))
        }

        return list
    }
}