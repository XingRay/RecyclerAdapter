package com.xingray.recycleradapter

import android.view.View
import android.view.ViewGroup

abstract class ViewSupport<T : Any>(private val viewType: Int,
                                    private val typeSupport: TypeSupport<T>) {

    var viewHolderClass: Class<*>? = null
    private var itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null
    private var viewHolderFactory: ((View) -> ViewHolder<T>)? = null

    fun registerView(): TypeSupport<T> {
        typeSupport.registerView(viewType, this)
        return typeSupport
    }

    fun onCreateViewHolder(parent: ViewGroup): ViewHolder<T> {
        val itemView = createItemView(parent)
        val holder = createViewHolder(itemView)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            @Suppress("UNCHECKED_CAST")
            itemClickListener?.invoke(parent, position, typeSupport.adapter.mItems[position] as T)
        }
        return holder
    }

    fun <VH : ViewHolder<T>> viewHolderClass(cls: Class<VH>): ViewSupport<T> {
        viewHolderClass = cls
        return this
    }

    fun itemClickListener(listener: ItemClickListener<T>): ViewSupport<T> {
        itemClickListener = listener::onItemClick
        return this
    }

    fun itemClickListener(listener: ((ViewGroup, Int, T) -> Unit)): ViewSupport<T> {
        itemClickListener = listener
        return this
    }

    fun viewHolderFactory(factory: ViewHolderFactory<T>) {
        viewHolderFactory = factory::createViewHolder
    }

    fun viewHolderFactory(factory: (View) -> ViewHolder<T>) {
        viewHolderFactory = factory
    }

    abstract fun createItemView(parent: ViewGroup): View

    open fun createViewHolder(itemView: View): ViewHolder<T> {
        val factory = viewHolderFactory
        if (factory != null) {
            return factory.invoke(itemView)
        }
        val cls = viewHolderClass
                ?: throw NullPointerException("set viewHolderClass if viewHolderFactory is null")
        val constructor = cls.getConstructor(View::class.java)
        constructor.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        return (constructor.newInstance(itemView) as ViewHolder<T>)
    }
}