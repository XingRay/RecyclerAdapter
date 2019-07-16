@file:Suppress("unused")

package com.xingray.recycleradapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder<out Any>>() {

    internal val mItems: MutableList<Any> = mutableListOf()
    private val mTypeSupportMap = mutableMapOf<Class<*>, TypeSupport<*, out ViewHolder<out Any>>>()
    private val mViewSupports = SparseArray<ViewSupport<out Any, out ViewHolder<out Any>>>()

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

//    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<out VH>)
//            : RecyclerAdapter {
//        return addType(dataClass, holderClass, holderClass.layoutId, null, null)
//    }
//
//    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<out VH>, itemClickListener: ItemClickListener<T>)
//            : RecyclerAdapter {
//        return addType(dataClass, holderClass, holderClass.layoutId, null, itemClickListener::onItemClick)
//    }
//
//    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<out VH>, itemClickListener: (parent: ViewGroup, position: Int, t: T?) -> Unit)
//            : RecyclerAdapter {
//        return addType(dataClass, holderClass, holderClass.layoutId, null, itemClickListener)
//    }
//
//    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<out VH>, initializer: Initializer<ViewHolder<T>>, itemClickListener: (parent: ViewGroup, position: Int, t: T?) -> Unit)
//            : RecyclerAdapter {
//        return addType(dataClass, holderClass, holderClass.layoutId, initializer::initialize, itemClickListener)
//    }
//
//    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<out VH>, initializer: ((ViewHolder<T>) -> Unit)? = null, itemClickListener: ((parent: ViewGroup, position: Int, t: T?) -> Unit)? = null)
//            : RecyclerAdapter {
//        return addType(dataClass, holderClass, holderClass.layoutId, initializer, itemClickListener)
//    }

    inline fun <reified T : Any, VH : ViewHolder<T>> addType(holderClass: Class<VH>, layoutId: Int, noinline initializer: ((VH) -> Unit)?, noinline itemClickListener: ((ViewGroup, Int, T) -> Unit)?): RecyclerAdapter {
        return addType(T::class.java, holderClass, layoutId, initializer, itemClickListener)
    }

    inline fun <reified T : Any, VH : ViewHolder<T>> addType(holderClass: Class<VH>, noinline initializer: ((VH) -> Unit)? = null, noinline itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null): RecyclerAdapter {
        return addType(T::class.java, holderClass, holderClass.layoutId, initializer, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<VH>, layoutId: Int, initializer: ((VH) -> Unit)?, itemClickListener: ((ViewGroup, Int, T) -> Unit)?): RecyclerAdapter {
        val typeSupport: TypeSupport<T, VH> = newTypeSupport(dataClass)

        val layoutViewSupport = LayoutViewSupport(LayoutInflater.from(context), layoutId, 0, typeSupport)
        layoutViewSupport.itemClickListener(itemClickListener)
        layoutViewSupport.initializer(initializer)
        layoutViewSupport.viewHolderClass(holderClass)

        typeSupport.registerView(0, layoutViewSupport)
        mTypeSupportMap[dataClass] = typeSupport
        return this
    }

    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, mapper: (T, Int) -> Class<out VH>)
            : RecyclerAdapter {
        val typeSupport = TypeSupport(context, dataClass, this)
        mTypeSupportMap[dataClass] = typeSupport
        return this
    }

    fun <T : Any, VH : ViewHolder<T>> newTypeSupport(dataClass: Class<T>): TypeSupport<T, VH> {
        return TypeSupport(context, dataClass, this)
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeSupport(dataClass: Class<T>, typeSupport: TypeSupport<T, VH>) {
        mTypeSupportMap[dataClass] = typeSupport
    }

    fun <T : Any, VH : ViewHolder<T>> addViewSupport(viewType: Int, viewSupport: ViewSupport<T, VH>) {
        mViewSupports.put(viewType, viewSupport)
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