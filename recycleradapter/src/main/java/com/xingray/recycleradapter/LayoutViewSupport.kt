package com.xingray.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LayoutViewSupport<T : Any>(context: Context, viewType: Int, typeSupport: TypeSupport<T>) : ViewSupport<T>(viewType, typeSupport) {

    private var mLayoutId: Int = 0
    private val mLayoutInflater = LayoutInflater.from(context)

    fun layoutId(layoutId: Int): LayoutViewSupport<T> {
        mLayoutId = layoutId
        return this
    }

    override fun createItemView(parent: ViewGroup): View {
        return mLayoutInflater.inflate(mLayoutId, parent, false)
    }
}