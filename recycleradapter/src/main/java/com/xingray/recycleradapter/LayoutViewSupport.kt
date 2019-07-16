package com.xingray.recycleradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LayoutViewSupport<T : Any, VH : ViewHolder<T>>(layoutInflater: LayoutInflater,
                                                     layoutId: Int,
                                                     viewType: Int,
                                                     typeSupport: TypeSupport<T, VH>)
    : ViewSupport<T, VH>(viewType, typeSupport) {

    private val mLayoutId: Int = layoutId
    private val mLayoutInflater = layoutInflater

    override fun createItemView(parent: ViewGroup): View {
        return mLayoutInflater.inflate(mLayoutId, parent, false)
    }
}