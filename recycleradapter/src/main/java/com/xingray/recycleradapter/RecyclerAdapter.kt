@file:Suppress("unused")

package com.xingray.recycleradapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xingray.recycleradapter.ext.LayoutViewFactory
import com.xingray.recycleradapter.ext.LayoutViewSupport
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
    private val viewSupports by lazy { SparseArray<ViewSupport<out Any, out ViewHolder<out Any>>>() }
    private val viewTypeMappers by lazy { mutableMapOf<Class<out Any>, (Any, Int) -> Int>() }
    private val viewTypeMap by lazy { mutableMapOf<Class<out Any>, Int>() }

    constructor() : this(null)

    internal val inflater: LayoutInflater
        get() {
            val context = this.context ?: throw NullPointerException("context is requored")
            return LayoutInflater.from(context)
        }

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
        return addTypeJ(dataClass, holderClass, holderClass.layoutId, null, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeJ(dataClass: Class<T>, holderClass: Class<VH>, initializer: Initializer<VH>?, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        return addTypeJ(dataClass, holderClass, holderClass.layoutId, initializer, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeJ(dataClass: Class<T>, holderClass: Class<VH>, layoutId: Int, initializer: Initializer<VH>?, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        context ?: throw IllegalStateException("must set context by constructor")
        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), layoutId)::createItemView

        var init: ((VH) -> Unit)? = null
        if (initializer != null) {
            init = initializer::initialize
        }
        val holderFactory = ReflectHolderFactory(holderClass).initializer(init)::create

        var onItemClick: ((ViewGroup, Int, T) -> Unit)? = null
        if (itemClickListener != null) {
            onItemClick = itemClickListener::onItemClick
        }
        return addType(dataClass, holderClass, viewFactory, holderFactory, onItemClick)
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeJh(dataClass: Class<T>, holderClass: Class<VH>, factory: HolderFactory<VH>, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        return addTypeJh(dataClass, holderClass, holderClass.layoutId, factory, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeJh(dataClass: Class<T>, holderClass: Class<VH>, layoutId: Int, factory: HolderFactory<VH>, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        context ?: throw IllegalStateException("must set context by constructor")
        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), layoutId)::createItemView

        var onItemClick: ((ViewGroup, Int, T) -> Unit)? = null
        if (itemClickListener != null) {
            onItemClick = itemClickListener::onItemClick
        }
        return addType(dataClass, holderClass, viewFactory, factory::create, onItemClick)
    }

    fun <T : Any, VH : ViewHolder<T>> addTypeJv(dataClass: Class<T>, holderClass: Class<VH>, viewFactory: ViewFactory, initializer: Initializer<VH>, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        val holderFactory = ReflectHolderFactory(holderClass).initializer(initializer::initialize)::create

        var onItemClick: ((ViewGroup, Int, T) -> Unit)? = null
        if (itemClickListener != null) {
            onItemClick = itemClickListener::onItemClick
        }
        return addType(dataClass, holderClass, viewFactory::createItemView, holderFactory, onItemClick)
    }

    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<VH>, layoutId: Int, initializer: ((VH) -> Unit)?, itemClickListener: ((ViewGroup, Int, T) -> Unit)?): RecyclerAdapter {
        context ?: throw IllegalStateException("must set context by constructor")
        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), layoutId)::createItemView
        val holderFactory = ReflectHolderFactory(holderClass).initializer(initializer)::create
        return addType(dataClass, holderClass, viewFactory, holderFactory, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<VH>, holderFactory: (View) -> VH, itemClickListener: ((ViewGroup, Int, T) -> Unit)?): RecyclerAdapter {
        context ?: throw IllegalStateException("must set context by constructor")
        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), holderClass.layoutId)::createItemView
        return addType(dataClass, holderClass, viewFactory, holderFactory, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, holderClass: Class<VH>, viewFactory: (ViewGroup) -> View, holderFactory: (View) -> VH, itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null): RecyclerAdapter {
        val viewType = getOrGenerateViewType(holderClass)
        val viewTypeMapper: ((t: T, position: Int) -> Int) = { _, _ -> viewType }
        val viewSupport = ViewSupport(viewType, this, viewFactory, holderFactory)
        viewSupport.itemClickListener(itemClickListener)

        addViewSupport(viewType, viewSupport)
        addViewTypeMapper(dataClass, viewTypeMapper)
        return this
    }

    fun <T : Any, VH : ViewHolder<T>> addViewTypeMapperJ(dataClass: Class<T>, mapper: ViewTypeMapper<T>): RecyclerAdapter {
        return addViewTypeMapper(dataClass, mapper::getViewType)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun <T : Any, VH : ViewHolder<T>> addViewTypeMapper(dataClass: Class<T>, mapper: (T, Int) -> Int): RecyclerAdapter {
        viewTypeMappers[dataClass] = { t: Any, position: Int ->
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
        val holderFactory = ReflectHolderFactory(holderClass)::create
        val viewSupport = ViewSupport(viewType, this, viewFactory, holderFactory)
        addViewSupport(viewType, viewSupport)

        return this
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun <T : Any, VH : ViewHolder<T>> newLayoutViewSupport(viewType: Int, holderClass: Class<VH>): LayoutViewSupport<T, VH> {
        context ?: throw IllegalStateException("must set context by constructor")

        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), holderClass.layoutId)
        val holderFactory = ReflectHolderFactory(holderClass)
        return LayoutViewSupport(viewType, this, viewFactory, holderFactory)
    }

    fun <T : Any, VH : ViewHolder<T>> addViewSupportJh(viewType: Int, holderClass: Class<VH>, factory: HolderFactory<VH>, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        return addViewSupportJh(viewType, holderClass.layoutId, factory, itemClickListener)
    }

    fun <T : Any, VH : ViewHolder<T>> addViewSupportJh(viewType: Int, layoutId: Int, factory: HolderFactory<VH>, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        context ?: throw IllegalStateException("must set context by constructor")
        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), layoutId)::createItemView

        var onItemClick: ((ViewGroup, Int, T) -> Unit)? = null
        if (itemClickListener != null) {
            onItemClick = itemClickListener::onItemClick
        }
        val viewSupport = ViewSupport(viewType, this, viewFactory, factory::create)
        viewSupport.itemClickListener(onItemClick)
        addViewSupport(viewType, viewSupport)
        return this
    }

    fun <T : Any, VH : ViewHolder<T>> newViewSupportJh(viewType: Int, holderClass: Class<VH>, factory: HolderFactory<VH>): ViewSupport<T, VH> {
        return newViewSupportJh(viewType, holderClass.layoutId, factory)
    }

    fun <T : Any, VH : ViewHolder<T>> newViewSupportJh(viewType: Int, layoutId: Int, factory: HolderFactory<VH>): ViewSupport<T, VH> {
        context ?: throw IllegalStateException("must set context by constructor")
        val viewFactory = LayoutViewFactory(LayoutInflater.from(context), layoutId)::createItemView
        return ViewSupport(viewType, this, viewFactory, factory::create)
    }

    fun <T : Any, VH : ViewHolder<T>> addViewSupportJv(viewType: Int, holderClass: Class<VH>, factory: ViewFactory, initializer: Initializer<VH>?, itemClickListener: ItemClickListener<T>?): RecyclerAdapter {
        val holderFactory = ReflectHolderFactory(holderClass).initializerJ(initializer)
        var onItemClick: ((ViewGroup, Int, T) -> Unit)? = null
        if (itemClickListener != null) {
            onItemClick = itemClickListener::onItemClick
        }
        val viewSupport = ViewSupport(viewType, this, factory::createItemView, holderFactory::create)
        viewSupport.itemClickListener(onItemClick)
        addViewSupport(viewType, viewSupport)
        return this
    }

    fun <T : Any, VH : ViewHolder<T>> newViewSupportJv(viewType: Int, holderClass: Class<VH>, factory: ViewFactory): LayoutViewSupport<T, VH> {
        val holderFactory = ReflectHolderFactory(holderClass)
        return LayoutViewSupport(viewType, this, factory, holderFactory)
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
            holder.refreshItemView(payloads)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<out Any>, position: Int) {
        val t = items[position]
        holder.bindItemView(t, position)
    }

    private fun <T : Any, VH : ViewHolder<T>> getOrGenerateViewType(holderClass: Class<VH>): Int {
        var viewType: Int? = viewTypeMap[holderClass]
        if (viewType == null) {
            viewType = UniqueId.get()
            viewTypeMap[holderClass] = viewType
        }
        return viewType
    }
}