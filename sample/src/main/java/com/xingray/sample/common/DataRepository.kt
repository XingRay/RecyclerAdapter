package com.xingray.sample.common

import android.graphics.Color
import kotlin.random.Random

class DataRepository {
    companion object {
        private const val DATA0_COUNT = 100
        private const val DATA1_COUNT = 100
    }


    fun loadData0(): List<Data0> {
        val list = mutableListOf<Data0>()
        for (i in 0 until DATA0_COUNT) {
            list.add(Data0("test data $i", Color.WHITE))
        }

        return list
    }

    fun loadData1(): List<Data1> {
        val list = mutableListOf<Data1>()
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

        for (i in 0 until DATA1_COUNT) {
            var randomInt = r.nextInt(size.size)
            if (randomInt == value) {
                randomInt = (randomInt + 1) % size.size
            }
            value = randomInt
            list.add(Data1("test data $i", size[randomInt], Color.GRAY))
        }

        return list
    }
}