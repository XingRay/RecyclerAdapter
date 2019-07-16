package com.xingray.recycleradapter

import android.view.View
import android.view.ViewGroup

class ViewSupport<T : Any, VH : ViewHolder<T>>(private val adapter: RecyclerAdapter,
                                               private val viewFactory: (ViewGroup) -> View,
                                               private val holderFactory: (View) -> VH) {
    private var itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null
    private var initializer: ((VH) -> Unit)? = null

    fun onCreateViewHolder(parent: ViewGroup): ViewHolder<T> {
        val itemView = viewFactory.invoke(parent)
        val holder = holderFactory.invoke(itemView)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            @Suppress("UNCHECKED_CAST")
            itemClickListener?.invoke(parent, position, adapter.items[position] as T)
        }
        initializer?.invoke(holder)
        return holder
    }

    fun itemClickListener(listener: ItemClickListener<T>?): ViewSupport<T, VH> {
        listener?.let {
            itemClickListener = listener::onItemClick
        }
        return this
    }

    fun itemClickListener(listener: ((ViewGroup, Int, T) -> Unit)?): ViewSupport<T, VH> {
        itemClickListener = listener
        return this
    }

    fun initializer(initializer: Initializer<VH>?) {
        initializer?.let {
            this.initializer = initializer::initialize
        }
    }

    fun initializer(initializer: ((VH) -> Unit)?) {
        this.initializer = initializer
    }
}