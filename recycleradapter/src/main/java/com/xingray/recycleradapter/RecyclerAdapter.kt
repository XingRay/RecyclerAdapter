@file:Suppress("unused")

package com.xingray.recycleradapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xingray.recycleradapter.ext.LayoutViewFactory
import com.xingray.recycleradapter.ext.ReflectHolderFactory

/**
 *用于[RecyclerView]的适配器，可以通过简单的API实现数据的展示等功能
 *
 * @author : leixing
 * @date : 19-7-14
 * email : leixing1012@qq.com
 *
 */
open class RecyclerAdapter(private var context: Context?) : RecyclerView.Adapter<ViewHolder<out Any>>() {

    internal val items by lazy { mutableListOf<Any>() }
    private val viewTypeMappers by lazy { mutableMapOf<Class<out Any>, (Any, Int) -> Int>() }
    private val viewSupports by lazy { SparseArray<ViewSupport<out Any, out ViewHolder<out Any>>>() }
    private val viewTypeMap by lazy { mutableMapOf<Class<*>, Int>() }

    constructor() : this(null)

    fun add(item: Any?) {
        add(items.size, item)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun add(index: Int, item: Any?) {
        if (items.add(index, item)) {
            notifyItemInserted(index)
        }
    }

    fun addAll(list: List<Any>?) {
        addAll(items.size, list)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun addAll(start: Int, items: List<Any>?) {
        if (this.items.addAll(start, items)) {
            notifyItemRangeInserted(start, items?.size ?: 0)
        }
    }

    fun remove(position: Int) {
        if (items.remove(position)) {
            notifyItemRemoved(position)
        }
    }

    fun remove(position: Int, count: Int) {
        if (items.remove(position, count) != null) {
            notifyItemRangeRemoved(position, count)
        }
    }

    fun update(list: List<Any>?) {
        if (items.update(list)) {
            notifyDataSetChanged()
        }
    }

    fun clear() {
        val count = itemCount
        if (items.removeAll()) {
            notifyItemRangeRemoved(0, count)
        }
    }

    fun moveItem(from: Int, to: Int) {
        if (items.move(from, to)) {
            notifyItemMoved(from, to)
        }
    }

    inline fun <reified T : Any, VH : ViewHolder<T>> addType(holderClass: Class<VH>, layoutId: Int, noinline initializer: ((VH) -> Unit)?, noinline itemClickListener: ((ViewGroup, Int, T) -> Unit)?): RecyclerAdapter {
        return addType(T::class.java, holderClass, layoutId, initializer, itemClickListener)
    }

    inline fun <reified T : Any, VH : ViewHolder<T>> addType(holderClass: Class<VH>, noinline initializer: ((VH) -> Unit)? = null, noinline itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null): RecyclerAdapter {
        return addType(T::class.java, holderClass, holderClass.layoutId, initializer, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeJ(dataClass: Class<T>, holderClass: Class<VH>): RecyclerAdapter {
        return addType(dataClass, holderClass, holderClass.layoutId, null, null)
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeJ(dataClass: Class<T>, holderClass: Class<VH>, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        return addTypeJ(dataClass, holderClass, null, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeJ(dataClass: Class<T>, holderClass: Class<VH>, initializer: Initializer<VH>?, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        var init: ((VH) -> Unit)? = null
        if (initializer != null) {
            init = initializer::initialize
        }
        var onItemClick: ((ViewGroup, Int, T) -> Unit)? = null
        if (itemClickListener != null) {
            onItemClick = itemClickListener::onItemClick
        }
        return addType(dataClass, holderClass, holderClass.layoutId, init, onItemClick)
    }

    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<VH>, layoutId: Int, initializer: ((VH) -> Unit)?, itemClickListener: ((ViewGroup, Int, T) -> Unit)?): RecyclerAdapter {
        context ?: throw IllegalStateException("must set context by constructor")

        val viewType = getViewType(holderClass)
        val viewTypeMapper: ((t: T, position: Int) -> Int) = { _, _ -> viewType }
        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), layoutId)::createItemView
        val holderFactory = ReflectHolderFactory(holderClass)::createViewHolder
        val viewSupport = ViewSupport(this, viewFactory, holderFactory)
        viewSupport.itemClickListener(itemClickListener)
        viewSupport.initializer(initializer)

        addViewSupport(viewType, viewSupport)
        addViewTypeMapper(dataClass, viewTypeMapper)
        return this
    }

    private fun <VH> getViewType(holderClass: Class<VH>): Int {
        var viewType: Int? = viewTypeMap[holderClass]
        if (viewType == null) {
            viewType = UniqueId.get()
            viewTypeMap[holderClass] = viewType
        }
        return viewType
    }

    fun <T : Any, VH : ViewHolder<T>> addViewTypeMapper(dataClass: Class<T>, mapper: ViewTypeMapper<T>): RecyclerAdapter {
        return addViewTypeMapper(dataClass, mapper::getViewType)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun <T : Any, VH : ViewHolder<T>> addViewTypeMapper(dataClass: Class<T>, mapper: (T, Int) -> Int): RecyclerAdapter {
        viewTypeMappers[dataClass] = { t, position ->
            if (!t.javaClass.isAssignableFrom(dataClass)) {
                throw IllegalStateException("wrong type of data, " +
                        "${dataClass.canonicalName} required, " +
                        "${t.javaClass.canonicalName} found")
            }
            @Suppress("UNCHECKED_CAST")
            mapper.invoke(t as T, position)
        }

        return this
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun <T : Any, VH : ViewHolder<T>> addViewSupport(viewType: Int, viewSupport: ViewSupport<T, VH>) {
        viewSupports.put(viewType, viewSupport)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun <T : Any, VH : ViewHolder<T>> addLayoutViewSupport(viewType: Int, holderClass: Class<VH>): RecyclerAdapter {
        context ?: throw IllegalStateException("must set context by constructor")

        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), holderClass.layoutId)::createItemView
        val holderFactory = ReflectHolderFactory(holderClass)::createViewHolder
        val viewSupport = ViewSupport(this, viewFactory, holderFactory)
        addViewSupport(viewType, viewSupport)

        return this
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun <T : Any, VH : ViewHolder<T>> newLayoutViewSupport(viewType: Int, holderClass: Class<VH>): ViewSupport<T, VH> {
        context ?: throw IllegalStateException("must set context by constructor")

        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), holderClass.layoutId)::createItemView
        val holderFactory = ReflectHolderFactory(holderClass)::createViewHolder
        return ViewSupport(viewType, this, viewFactory, holderFactory)
    }

    override fun getItemViewType(position: Int): Int {
        val itemData = items[position]
        val viewTypeMapper = viewTypeMappers[itemData::class.java]
        viewTypeMapper ?: throw IllegalStateException("unsupported type, data:$itemData")
        return viewTypeMapper.invoke(itemData, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<out Any> {
        return viewSupports.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder<out Any>, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.onRefreshItemView(payloads)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<out Any>, position: Int) {
        val t = items[position]
        holder.bindItemView(t, position)
    }
}