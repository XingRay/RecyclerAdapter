package com.xingray.recycleradapter

import android.view.View
import android.view.ViewGroup

/**
 * 用于记录列表中一个`ViewType`所对应的视图的信息，包括视图构造工厂，`Holder`构造工厂，
 * 用于创建`ViewHolder`及当绑定点击事件等
 *
 * @author : leixing
 * @date : 19-7-14
 * email : leixing1012@qq.com
 *
 */
open class ViewSupport<T : Any, VH : ViewHolder<T>>(private val viewType: Int,
                                                    private val adapter: RecyclerAdapter,
                                                    private val viewFactory: (ViewGroup) -> View,
                                                    private val holderFactory: (View) -> VH) {

    private var itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null

    fun onCreateViewHolder(parent: ViewGroup): ViewHolder<T> {
        val itemView = viewFactory.invoke(parent)
        val holder = holderFactory.invoke(itemView)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            @Suppress("UNCHECKED_CAST")
            itemClickListener?.invoke(parent, position, adapter.items[position] as T)
        }
        return holder
    }

    fun addToAdapter(): RecyclerAdapter {
        adapter.addViewSupport(viewType, this)
        return adapter
    }

    fun itemClickListenerJ(listener: ItemClickListener<T>?): ViewSupport<T, VH> {
        if (listener == null) {
            itemClickListener = null
        } else {
            itemClickListener = listener::onItemClick
        }
        return this
    }

    fun itemClickListener(listener: ((ViewGroup, Int, T) -> Unit)?): ViewSupport<T, VH> {
        itemClickListener = listener
        return this
    }
}