package com.xingray.recycleradapter

import android.view.View
import android.view.ViewGroup

abstract class ViewSupport<T : Any, VH : ViewHolder<T>>(private val viewType: Int,
                                                        private val typeSupport: TypeSupport<T, VH>) {

    private var holderClass: Class<*>? = null
    private var itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null
    private var holderFactory: ((View) -> VH)? = null
    private var initializer: ((VH) -> Unit)? = null

    fun registerView(): TypeSupport<T, VH> {
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
        initializer?.invoke(holder)
        return holder
    }

    fun viewHolderClass(holderClass: Class<out VH>): ViewSupport<T, VH> {
        this.holderClass = holderClass
        return this
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

    fun holderFactory(factory: ViewHolderFactory<VH>) {
        holderFactory = factory::createViewHolder
    }

    fun holderFactory(factory: (View) -> VH) {
        holderFactory = factory
    }

    fun initializer(initializer: Initializer<VH>?) {
        initializer?.let {
            this.initializer = initializer::initialize
        }
    }

    fun initializer(initializer: ((VH) -> Unit)?) {
        this.initializer = initializer
    }

    abstract fun createItemView(parent: ViewGroup): View

    open fun createViewHolder(itemView: View): VH {
        val factory = holderFactory
        if (factory != null) {
            return factory.invoke(itemView)
        }
        val cls = holderClass
                ?: throw NullPointerException("set holderClass if holderFactory is null")
        val constructor = cls.getConstructor(View::class.java)
        constructor.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        return (constructor.newInstance(itemView) as VH)
    }
}