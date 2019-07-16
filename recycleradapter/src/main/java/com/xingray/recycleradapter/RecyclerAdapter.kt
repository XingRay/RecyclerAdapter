@file:Suppress("unused")

package com.xingray.recycleradapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder<out Any>>() {

    internal val items by lazy { mutableListOf<Any>() }
    private val viewTypeMappers by lazy { mutableMapOf<Class<out Any>, (Any, Int) -> Int>() }
    private val viewSupports by lazy { SparseArray<ViewSupport<out Any, out ViewHolder<out Any>>>() }
    private val viewTypeMap by lazy { mutableMapOf<Class<*>, Int>() }

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
        val viewType = getViewType(holderClass)
        val viewTypeMapper: ((t: T, position: Int) -> Int) = { _, _ -> viewType }

        val viewSupport = ViewSupport(this, {
            LayoutInflater.from(context).inflate(layoutId, it, false)
        }, {
            val constructor = holderClass.getConstructor(View::class.java)
            constructor.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            return@ViewSupport constructor.newInstance(it)
        })
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

    fun <T : Any, VH : ViewHolder<T>> addType(dataClass: Class<T>, mapper: (T, Int) -> Class<out VH>)
            : RecyclerAdapter {
        val typeSupport = TypeSupport(context, dataClass, this)
        viewTypeMappers[dataClass] = typeSupport
        return this
    }
//
//    fun <T : Any, VH : ViewHolder<T>> newTypeSupport(dataClass: Class<T>): TypeSupport<T> {
//        return TypeSupport(context, dataClass, this)
//    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun <T : Any, VH : ViewHolder<T>> addViewTypeMapper(dataClass: Class<T>, viewTypeMapper: (T, Int) -> Int) {
        viewTypeMappers[dataClass] = { t, position ->
            if (!t.javaClass.isAssignableFrom(dataClass)) {
                throw IllegalStateException("wrong type of data, " +
                        "${dataClass.canonicalName} required, " +
                        "${t.javaClass.canonicalName} found")
            }
            @Suppress("UNCHECKED_CAST")
            viewTypeMapper.invoke(t as T, position)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun <T : Any, VH : ViewHolder<T>> addViewSupport(viewType: Int, viewSupport: ViewSupport<T, VH>) {
        viewSupports.put(viewType, viewSupport)
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
        holder.onBindItemView(t, position)
    }
}