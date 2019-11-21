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

    companion object {
        /**
         * null所对应的`ViewType`
         */
        const val VIEW_TYPE_NULL: Int = 0x0000eeff
    }

    internal val items by lazy { mutableListOf<Any?>() }
    private val viewSupports by lazy { SparseArray<ViewSupport<out Any, out ViewHolder<out Any>>>() }
    private val viewTypeMappers by lazy { mutableMapOf<Class<out Any>, (Any, Int) -> Int>() }
    private val viewTypeMap by lazy { mutableMapOf<Class<out Any>, Int>() }

    private var nullViewFactory: ((ViewGroup) -> View)? = null
    private var nullHolderFactory: ((View) -> ViewHolder<out Any>)? = null
    private var nullItemCLickListener: ((ViewGroup, Int, Any?) -> Unit)? = null

    constructor() : this(null)

    internal val inflater: LayoutInflater
        get() {
            val context = this.context ?: throw NullPointerException("context is required")
            return LayoutInflater.from(context)
        }

    fun add(item: Any?) {
        add(items.size, item)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun add(index: Int, item: Any?) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    fun addAll(list: List<Any?>?) {
        addAll(items.size, list)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun addAll(start: Int, items: List<Any?>?) {
        if (this.items.addAll(start, items)) {
            notifyItemRangeInserted(start, items?.size ?: 0)
        }
    }

    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun remove(position: Int, count: Int) {
        if (items.remove(position, count) != null) {
            notifyItemRangeRemoved(position, count)
        }
    }

    fun update(list: List<Any?>?) {
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

    fun <VH : ViewHolder<out Any>> nullItemSupport(viewFactory: (ViewGroup) -> View, holderFactory: (View) -> VH): RecyclerAdapter {
        nullViewFactory = viewFactory
        nullHolderFactory = holderFactory
        return this
    }

    fun <VH : ViewHolder<out Any>> nullItemSupport(viewFactory: ViewFactory, holderFactory: HolderFactory<VH>): RecyclerAdapter {
        nullItemSupport(viewFactory::createItemView, holderFactory::create)
        return this
    }

    fun <VH : ViewHolder<out Any>> nullItemSupport(holderClass: Class<VH>, initializer: Initializer<VH>?): RecyclerAdapter {
        nullItemSupport(holderClass.layoutId, holderClass, initializer)
        return this
    }

    fun <VH : ViewHolder<out Any>> nullItemSupport(layoutId: Int, holderClass: Class<VH>, initializer: Initializer<VH>?): RecyclerAdapter {
        nullItemSupport(layoutId, ReflectHolderFactory(holderClass).initializerJ(initializer))
        return this
    }

    fun <VH : ViewHolder<out Any>> nullItemSupport(layoutId: Int, reflectHolderFactory: ReflectHolderFactory<VH>): RecyclerAdapter {
        val viewFactory: ViewFactory = LayoutViewFactory(LayoutInflater.from(context), layoutId)
        val holderFactory: HolderFactory<VH> = reflectHolderFactory
        nullItemSupport(viewFactory, holderFactory)
        return this
    }

    fun <VH : ViewHolder<out Any>> nullItemSupport(layoutId: Int, holderFactory: HolderFactory<VH>): RecyclerAdapter {
        val viewFactory: ViewFactory = LayoutViewFactory(LayoutInflater.from(context), layoutId)
        nullItemSupport(viewFactory, holderFactory)
        return this
    }

    fun nullItemClickLister(listener: ItemClickListener<Any?>): RecyclerAdapter {
        nullItemCLickListener = listener::onItemClick
        return this
    }

    fun nullItemClickLister(listener: (parent: ViewGroup, position: Int, t: Any?) -> Unit): RecyclerAdapter {
        nullItemCLickListener = listener
        return this
    }

    override fun getItemViewType(position: Int): Int {
        val itemData = items[position] ?: return VIEW_TYPE_NULL
        val viewTypeMapper = viewTypeMappers[itemData::class.java]
        viewTypeMapper ?: throw IllegalStateException("unsupported type, data:$itemData")
        return viewTypeMapper.invoke(itemData, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<out Any> {
        if (viewType == VIEW_TYPE_NULL) {
            return createNullItemViewHolder(parent)
        }
        return viewSupports.get(viewType).onCreateViewHolder(parent)
    }

    private fun createNullItemViewHolder(parent: ViewGroup): ViewHolder<out Any> {
        val viewFactory = nullViewFactory
                ?: throw java.lang.IllegalStateException("must set nullViewFactory by nullItemSupport()")
        val holderFactory = nullHolderFactory
                ?: throw java.lang.IllegalStateException("must set nullHolderFactory by nullItemSupport()")
        val viewHolder = holderFactory.invoke(viewFactory.invoke(parent))
        if (nullItemCLickListener != null) {
            viewHolder.itemView.setOnClickListener {
                nullItemCLickListener?.invoke(parent, viewHolder.adapterPosition, it)
            }
        }
        return viewHolder
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
        val t = items[position] ?: return
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