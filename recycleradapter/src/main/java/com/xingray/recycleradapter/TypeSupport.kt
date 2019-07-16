package com.xingray.recycleradapter

class TypeSupport<T : Any>(private val dataClass: Class<T>,
                           private val viewTypeMapper: (T, Int) -> Int) {

    fun getViewType(data: Any, position: Int): Int {
        if (!data.javaClass.isAssignableFrom(dataClass)) {
            throw IllegalArgumentException("unsupported data type, data:$data")
        }
        @Suppress("UNCHECKED_CAST")
        return viewTypeMapper.invoke(data as T, position)
    }
}