package com.xingray.recycleradapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 用于展示`RecyclerView`列表的{@ViewHolder}的基类，
 * 与[RecyclerAdapter]配合使用，支持`Item`的渲染[onBindItemView]
 * 和局部刷新[ViewHolder.onRefreshItemView]
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:37
 */
abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal fun bindItemView(t: Any, position: Int) {
        @Suppress("UNCHECKED_CAST")
        (onBindItemView(t as T, position))
    }

    internal fun refreshItemView(payloads: List<Any>) {
        payloads.forEach {
            onRefreshItemView(it)
        }
    }

    protected abstract fun onBindItemView(t: T, position: Int)

    protected open fun onRefreshItemView(payload: Any) {

    }
}
