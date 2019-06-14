package com.xingray.recycleradapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(context: Context) : RecyclerView.Adapter<BaseViewHolder<out Any>>() {

    private val mContext = context
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

    fun <T : Any> typeSupport(cls: Class<T>): TypeSupport<T> {
        return TypeSupport(mContext, cls, this)
    }

    fun <T : Any> registerType(typeClass: Class<T>, typeSupport: TypeSupport<T>) {
        mTypeSupportMap[typeClass] = typeSupport
    }

    fun <T : Any, VH : BaseViewHolder<T>> registerType(typeClass: Class<T>, mapper: (T, Int) -> Class<out VH>): RecyclerAdapter {
        return this
    }

    fun <T : Any> registerView(viewType: Int, viewSupport: ViewSupport<T>) {
        mViewSupports.put(viewType, viewSupport)
    }

    inline fun <reified T : Any, VH : BaseViewHolder<T>> register(vhCls: Class<VH>, noinline itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null): RecyclerAdapter {
        val annotation = vhCls.getAnnotation(LayoutId::class.java)
                ?: throw IllegalArgumentException("View Holder Class must have @LayoutId Annotation")
        return register(T::class.java, vhCls, annotation.layoutId, itemClickListener)
    }

    fun <T : Any, VH : BaseViewHolder<T>> register(cls: Class<T>, vhCls: Class<VH>, layoutId: Int, itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null): RecyclerAdapter {
        val typeSupport = TypeSupport(mContext, cls, this)
        val layoutViewSupport = LayoutViewSupport(LayoutInflater.from(mContext), layoutId, viewType, typeSupport)
        if (itemClickListener != null) {
            layoutViewSupport.itemClickListener(itemClickListener)
        }
        layoutViewSupport.viewHolder(vhCls)
        layoutViewSupport.registerView().registerType()
        return this
    }

    override fun getItemViewType(position: Int): Int {
        val itemData = mItems[position]
        val typeSupport = mTypeSupportMap[itemData::class.java]
        return typeSupport?.getViewType(itemData, position) ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<out Any> {
        return mViewSupports.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out Any>, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.onRefreshItemView(payloads)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out Any>, position: Int) {
        val t = mItems[position]
        holder.onBindItemView(t, position)
    }
}