package com.xingray.recycleradapter

import android.view.View
import android.view.ViewGroup

abstract class ViewSupport<T : Any>(viewType: Int, typeSupport: TypeSupport<T>) {
    private val mTypeSupport = typeSupport

    private val mViewType = viewType
    var mClass: Class<*>? = null
    private var mItemClickListener: ((ViewGroup, Int, T) -> Unit)? = null

    fun registerView(): TypeSupport<T> {
        mTypeSupport.registerView(mViewType, this)
        return mTypeSupport
    }

    fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder<T> {
        val itemView = createItemView(parent)
        val holder = createViewHolder(itemView)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            @Suppress("UNCHECKED_CAST")
            mItemClickListener?.invoke(parent, position, mTypeSupport.mAdapter.mItems[position] as T)
        }
        return holder
    }

    fun <VH : BaseViewHolder<T>> viewHolder(cls: Class<VH>): ViewSupport<T> {
        mClass = cls
        return this
    }

    fun itemClickListener(listener: ItemClickListener<T>): ViewSupport<T> {
        mItemClickListener = listener::onItemClick
        return this
    }

    fun itemClickListener(listener: ((ViewGroup, Int, T) -> Unit)): ViewSupport<T> {
        mItemClickListener = listener
        return this
    }

    abstract fun createItemView(parent: ViewGroup): View

    open fun createViewHolder(itemView: View): BaseViewHolder<T> {
        val constructor = mClass?.getConstructor(View::class.java)
        constructor?.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        return (constructor?.newInstance(itemView) as BaseViewHolder<T>)
    }
}