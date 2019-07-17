package com.xingray.recycleradapter.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xingray.recycleradapter.ViewFactory

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/17 9:13
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class LayoutViewFactory(private val inflater: LayoutInflater, private val layoutId: Int) : ViewFactory {
    override fun createItemView(parent: ViewGroup): View {
        return inflater.inflate(layoutId, parent, false)
    }
}