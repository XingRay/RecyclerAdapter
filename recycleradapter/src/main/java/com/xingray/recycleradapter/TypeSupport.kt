package com.xingray.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

class TypeSupport<T : Any, VH : ViewHolder<T>>(private val context: Context,
                                               private val dataClass: Class<T>,
                                               internal val adapter: RecyclerAdapter) {

    private var mViewTypeMapper: ((T, Int) -> Int)? = null
    private var mDefaultViewType = UniqueId.get()

    fun registerType(): RecyclerAdapter {
        adapter.addTypeSupport(dataClass, this)
        return adapter
    }

    fun getViewType(data: Any, position: Int): Int {
        return if (dataClass.isAssignableFrom(data::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val t: T = data as T
            mViewTypeMapper?.invoke(t, position) ?: mDefaultViewType
        } else {
            mDefaultViewType
        }
    }

    fun viewTypeMapper(mapper: (T, Int) -> Int): TypeSupport<T, VH> {
        mViewTypeMapper = mapper
        return this
    }

    fun viewTypeMapper(mapper: ViewTypeMapper<T>): TypeSupport<T, VH> {
        mViewTypeMapper = mapper::getViewType
        return this
    }

    fun /*<VH : ViewHolder<T>>*/ layoutViewSupport(layoutId: Int): LayoutViewSupport<T, VH> {
        return layoutViewSupport(layoutId, 0)
    }

    fun /*<VH : ViewHolder<T>>*/ layoutViewSupport(layoutId: Int, viewType: Int = 0): LayoutViewSupport<T, VH> {
        return LayoutViewSupport(LayoutInflater.from(context), layoutId, viewType, this)
    }

    fun /*<VH : ViewHolder<T>> */viewSupport(layoutId: Int, holderClass: Class<out VH>, viewType: Int = 0, listener: ((ViewGroup, Int, T) -> Unit)): TypeSupport<T, VH> {
        return LayoutViewSupport(LayoutInflater.from(context), layoutId, viewType, this)
                .viewHolderClass(holderClass)
                .itemClickListener(listener)
                .registerView()
    }

    fun <T : Any, VH : ViewHolder<T>> registerView(viewType: Int, viewSupport: ViewSupport<T, VH>) {
        mDefaultViewType = viewType
        adapter.addViewSupport(viewType, viewSupport)
    }
}