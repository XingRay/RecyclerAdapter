package com.xingray.recycleradapter

import android.view.View
import android.view.ViewGroup

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/16 19:50
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface ViewFactory {
    fun createItemView(parent: ViewGroup): View
}