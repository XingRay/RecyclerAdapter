package com.xingray.recycleradapter

import android.view.ViewGroup

/**
 * 条目点击事件监听器
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:19
 *
 */
interface ItemClickListener<T> {
    /**
     * 列表条目的点击事件
     *
     * @param parent   条目所在的父布局
     * @param position 点击的Item的位置
     * @param t        点击的item对应的数据
     */
    fun onItemClick(parent: ViewGroup, position: Int, t: T?)
}
