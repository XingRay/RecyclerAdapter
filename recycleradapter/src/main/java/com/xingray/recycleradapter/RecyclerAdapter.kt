package com.xingray.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * 用于[RecyclerView]实现列表展示功能
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:15
 *
 *
 * description : 用于{@RecyclerView}实现列表展示功能
 * 如果列表中的item只有一种布局，通过[RecyclerAdapter.itemLayoutId]传入布局文件资源ID即可
 * 然后再通过[RecyclerAdapter.viewHolderFactory]方法传入`ViewHolder`
 * 的工厂类即可。
 * 同时也支持多布局类型列表，通过调用[RecyclerAdapter.multiTypeSupport]方法
 * 传入[MultiTypeSupport]的实现类即可
 */
class RecyclerAdapter<T, VH : BaseViewHolder<T>>(context: Context) : RecyclerView.Adapter<VH>() {

    private val mItems: MutableList<T> = mutableListOf()
    private var mOnItemClickListener: ((ViewGroup, Int, T?) -> Unit)? = null
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mItemLayoutId: Int = 0
    private var mFactory: ((View, Int) -> VH)? = null

    private var mTypeProvider: ((items: List<T>, position: Int) -> Int)? = null
    private var mLayoutIdProvider: ((viewType: Int) -> Int)? = null

    fun itemLayoutId(itemLayoutId: Int): RecyclerAdapter<T, VH> {
        mItemLayoutId = itemLayoutId
        return this
    }

    fun viewHolderFactory(factory: ViewHolderFactory<VH>): RecyclerAdapter<T, VH> {
        mFactory = factory::build
        return this
    }

    fun viewHolderFactory(factory: ((itemView: View, viewType: Int) -> VH)): RecyclerAdapter<T, VH> {
        mFactory = factory
        return this
    }

    fun multiTypeSupport(support: MultiTypeSupport<T>?): RecyclerAdapter<T, VH> {
        if (support != null) {
            mTypeProvider = support::getItemViewType
            mLayoutIdProvider = support::getLayoutId
        }
        return this
    }

    fun multiTypeSupport(typeProvider: ((items: List<T>, position: Int) -> Int)?,
                         layoutIdProvider: ((viewType: Int) -> Int)?): RecyclerAdapter<T, VH> {
        mTypeProvider = typeProvider
        mLayoutIdProvider = layoutIdProvider
        return this
    }

    fun itemClickListener(listener: OnItemClickListener<T>?): RecyclerAdapter<T, VH> {
        if (listener != null) {
            mOnItemClickListener = listener::onItemClick
        }
        return this
    }

    fun itemClickListener(listener: ((ViewGroup, Int, T?) -> Unit)?): RecyclerAdapter<T, VH> {
        if (listener != null) {
            mOnItemClickListener = listener
        }
        return this
    }

    fun update(list: List<T>?) {
        if (mItems.update(list)) {
            notifyDataSetChanged()
        }
    }

    fun clear() {
        if (mItems.isEmpty()) {
            return
        }

        val size = mItems.size
        mItems.clear()
        notifyItemMoved(0, size)
    }

    fun add(item: T?) {
        add(mItems.size, item)
    }

    fun add(index: Int, item: T?) {
        if (mItems.add(index, item)) {
            notifyItemInserted(index)
        }
    }

    fun addAll(list: List<T>?) {
        addAll(mItems.size, list)
    }

    fun addAll(start: Int, items: List<T>?) {
        if (mItems.addAll(start, items)) {
            notifyItemRangeInserted(start, items?.size ?: 0)
        }
    }

    fun remove(position: Int) {
        val removed = mItems.removeAt(position)
        if (removed != null) {
            notifyItemRemoved(position)
        }
    }

    fun remove(position: Int, count: Int) {
        if (mItems.remove(position, count) != null) {
            notifyItemRangeRemoved(position, count)
        }
    }

    fun moveItem(from: Int, to: Int) {
        if (mItems.move(from, to)) {
            notifyItemMoved(from, to)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (multiTypeSupportEnable()) {
            mTypeProvider?.invoke(mItems, position) ?: 0
        } else super.getItemViewType(position)
    }

    private fun multiTypeSupportEnable(): Boolean {
        return mTypeProvider != null && mLayoutIdProvider != null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutId: Int = if (multiTypeSupportEnable()) {
            mLayoutIdProvider?.invoke(viewType) ?: mItemLayoutId
        } else {
            mItemLayoutId
        }

        val itemView = mInflater.inflate(layoutId, parent, false)
        val viewHolder = mFactory!!.invoke(itemView, viewType)

        itemView.setOnClickListener {
            val listener = mOnItemClickListener
            if (listener != null) {
                val position = viewHolder.adapterPosition
                listener.invoke(parent, position, mItems[position])
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.onRefreshItemView(payloads)
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = mItems[position]
        holder.onBindItemView(t, position)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}
