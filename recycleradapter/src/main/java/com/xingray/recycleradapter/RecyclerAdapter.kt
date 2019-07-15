@file:Suppress("unused")

package com.xingray.recycleradapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(context: Context) : RecyclerView.Adapter<ViewHolder<out Any>>() {

    private val context = context
    internal val mItems: MutableList<Any> = mutableListOf()
    private val mTypeSupportMap = mutableMapOf<Class<*>, TypeSupport<*>>()
    private val mViewSupports = SparseArray<ViewSupport<out Any>>()

    fun add(item: Any?) {
        add(mItems.size, item)
    }

    fun add(index: Int, item: Any?) {
        if (mItems.add(index, item)) {
            notifyItemInserted(index)
        }
    }

    fun addAll(list: List<Any>?) {
        addAll(mItems.size, list)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun addAll(start: Int, items: List<Any>?) {
        if (mItems.addAll(start, items)) {
            notifyItemRangeInserted(start, items?.size ?: 0)
        }
    }

    fun remove(position: Int) {
        if (mItems.remove(position)) {
            notifyItemRemoved(position)
        }
    }

    fun remove(position: Int, count: Int) {
        if (mItems.remove(position, count) != null) {
            notifyItemRangeRemoved(position, count)
        }
    }

    fun update(list: List<Any>?) {
        if (mItems.update(list)) {
            notifyDataSetChanged()
        }
    }

    fun clear() {
        val count = itemCount
        if (mItems.removeAll()) {
            notifyItemRangeRemoved(0, count)
        }
    }

    fun moveItem(from: Int, to: Int) {
        if (mItems.move(from, to)) {
            notifyItemMoved(from, to)
        }
    }

    fun <T : Any> newTypeSupport(cls: Class<T>): TypeSupport<T> {
        return TypeSupport(context, cls, this)
    }

    fun <T : Any> addTypeSupport(cls: Class<T>, typeSupport: TypeSupport<T>) {
        mTypeSupportMap[cls] = typeSupport
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeSupport(cls: Class<T>, mapper: (T, Int) -> Class<out VH>)
            : RecyclerAdapter {
        val typeSupport = TypeSupport(context, cls, this)
        mTypeSupportMap[cls] = typeSupport
        return this
    }

    fun <T : Any> addViewSupport(viewType: Int, viewSupport: ViewSupport<T>) {
        mViewSupports.put(viewType, viewSupport)
    }

    inline fun <reified T : Any, VH : ViewHolder<T>> register(vhCls: Class<VH>, noinline itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null): RecyclerAdapter {
        val annotation = vhCls.getAnnotation(LayoutId::class.java)
                ?: throw IllegalArgumentException("View Holder Class must have @LayoutId Annotation")
        return register(T::class.java, vhCls, annotation.layoutId, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> register(cls: Class<T>, vhCls: Class<VH>, layoutId: Int, itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null): RecyclerAdapter {
        val typeSupport = TypeSupport(context, cls, this)
        val layoutViewSupport = LayoutViewSupport(LayoutInflater.from(context), layoutId, 0, typeSupport)
        if (itemClickListener != null) {
            layoutViewSupport.itemClickListener(itemClickListener)
        }
        layoutViewSupport.viewHolderClass(vhCls)
        layoutViewSupport.registerView().registerType()
        return this
    }

    override fun getItemViewType(position: Int): Int {
        val itemData = mItems[position]
        val typeSupport = mTypeSupportMap[itemData::class.java]
        return typeSupport?.getViewType(itemData, position) ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<out Any> {
        return mViewSupports.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder<out Any>, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.onRefreshItemView(payloads)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<out Any>, position: Int) {
        val t = mItems[position]
        holder.onBindItemView(t, position)
    }
}