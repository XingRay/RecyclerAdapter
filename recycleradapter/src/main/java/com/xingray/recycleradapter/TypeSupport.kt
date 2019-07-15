package com.xingray.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

class TypeSupport<T : Any>(private val context: Context,
                           private val cls: Class<T>,
                           internal val adapter: RecyclerAdapter) {

    private var mViewTypeMapper: ((T, Int) -> Int)? = null
    private var mDefaultViewType = UniqueId.get()

    fun registerType(): RecyclerAdapter {
        adapter.addTypeSupport(cls, this)
        return adapter
    }

    fun getViewType(data: Any, position: Int): Int {
        return if (cls.isAssignableFrom(data::class.java)) {
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
        return LayoutViewSupport(LayoutInflater.from(context), layoutId, viewType, this)
    }

    fun <VH : ViewHolder<T>> viewSupport(layoutId: Int, cls: Class<VH>, viewType: Int = 0, listener: ((ViewGroup, Int, T) -> Unit)): TypeSupport<T> {
        return LayoutViewSupport(LayoutInflater.from(context), layoutId, viewType, this)
                .viewHolderClass(cls)
                .itemClickListener(listener)
                .registerView()
    }

    fun registerView(viewType: Int, viewSupport: ViewSupport<T>) {
        mDefaultViewType = viewType
        adapter.addViewSupport(viewType, viewSupport)
    }
}