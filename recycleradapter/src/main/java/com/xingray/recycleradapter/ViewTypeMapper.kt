package com.xingray.recycleradapter

interface ViewTypeMapper<T> {
    fun getViewType(t: T, position: Int): Int
}