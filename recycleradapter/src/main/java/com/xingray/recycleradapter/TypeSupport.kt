package com.xingray.recycleradapter

import android.content.Context
import android.view.ViewGroup

class TypeSupport<T : Any>(context: Context, cls: Class<T>, adapter: RecyclerAdapter) {

    private val mContext = context
    private val mClass = cls
    internal val mAdapter = adapter
    private var mViewTypeMapper: ((T, Int) -> Int)? = null
    private var mDefaultViewType = UniqueId.get()

    fun registerType(): RecyclerAdapter {
        mAdapter.registerType(mClass, this)
        return mAdapter
    }

    fun getViewType(data: Any, position: Int): Int {
        return if (mClass.isAssignableFrom(data::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val t: T = data as T
            mViewTypeMapper?.invoke(t, position) ?: mDefaultViewType
        } else {
            mDefaultViewType
        }
    }

    fun viewTypeMapper(mapper: (T, Int) -> Int): TypeSupport<T> {
        mViewTypeMapper = mapper
        return this
    }

    fun viewTypeMapper(mapper: ViewTypeMapper<T>): TypeSupport<T> {
        mViewTypeMapper = mapper::getViewType
        return this
    }

    fun layoutViewSupport(layoutId: Int): LayoutViewSupport<T> {
        return layoutViewSupport(layoutId, 0)
    }

    fun layoutViewSupport(layoutId: Int, viewType: Int = 0): LayoutViewSupport<T> {
        return LayoutViewSupport(mContext, viewType, this).layoutId(layoutId)
    }

    fun <VH : BaseViewHolder<T>> viewSupport(layoutId: Int, cls: Class<VH>, viewType: Int = 0, listener: ((ViewGroup, Int, T) -> Unit)): TypeSupport<T> {
        return LayoutViewSupport(mContext, viewType, this).layoutId(layoutId).viewHolder(cls).itemClickListener(listener).registerView()
    }

    fun registerView(viewType: Int, viewSupport: ViewSupport<T>) {
        mDefaultViewType = viewType
        mAdapter.registerView(viewType, viewSupport)
    }
}