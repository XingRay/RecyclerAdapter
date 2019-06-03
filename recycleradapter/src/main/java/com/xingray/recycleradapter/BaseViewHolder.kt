package com.xingray.recycleradapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:37
 *
 *
 * description : 用于展示`RecyclerView`列表的{@ViewHolder}的基类，
 * 与[RecyclerAdapter]配合使用，支持`Item`的渲染[onBindItemView]
 * 和局部刷新[BaseViewHolder.onRefreshItemView]
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun onRefreshItemView(payloads: List<Any>) {

    }

    fun onBindItemView(t: Any, position: Int) {
        bindItemView(t as T, position)
    }

    abstract fun bindItemView(t: T, position: Int)
}
