package com.xingray.recycleradapter

import java.util.*

/**
 * description : 工具类
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:31
 *
 *
 */
private fun <T> Iterable<T>?.getSize(): Int {
    if (this == null) {
        return 0
    }
    if (this is Collection<*>) {
        return (this as Collection<T>).size
    }

    var size = 0
    for (ignored in this) {
        size++
    }
    return size
}

private fun Iterable<*>?.isOutOfIndex(index: Int): Boolean {
    return index < 0 || index >= this.getSize()
}

fun <T> MutableList<T>.move(fromIndex: Int, toIndex: Int): Boolean {
    if (isOutOfIndex(fromIndex)) {
        return false
    }

    if (isOutOfIndex(toIndex)) {
        return false
    }

    if (fromIndex == toIndex) {
        return false
    }

    if (fromIndex > toIndex) {
        for (i in fromIndex downTo toIndex + 1) {
            Collections.swap(this, i, i - 1)
        }
    } else {
        for (i in fromIndex until toIndex) {
            Collections.swap(this, i, i + 1)
        }
    }

    return true
}

fun <T> MutableList<T>.removeAll(): Boolean {
    if (isEmpty()) {
        return false
    }

    clear()
    return true
}

fun <T> MutableList<T>.update(list: List<T>?): Boolean {
    var updated = false
    if (!isEmpty()) {
        clear()
        updated = true
    }

    return if (list != null) {
        addAll(list)
    } else {
        updated
    }
}

fun <T> MutableList<T>.add(index: Int, t: T?): Boolean {
    return if (t == null) {
        false
    } else {
        add(index, t)
        true
    }
}

fun <T> MutableList<T>.addAll(index: Int, items: List<T>?): Boolean {
    return if (items == null) {
        false
    } else {
        addAll(index, items)
    }
}

fun <T> MutableList<T>.remove(position: Int, count: Int): List<T>? {
    if (position < 0 || position >= size) {
        return null
    }
    val removed = mutableListOf<T>()

    val iterator = listIterator(position)
    var countToRemove = count
    while (iterator.hasNext() && countToRemove > 0) {
        val t = iterator.next()
        iterator.remove()
        removed.add(t)
        countToRemove--
    }

    return removed
}

fun <T> MutableList<T>.remove(position: Int): Boolean {
    return if (position in 0 until size) {
        removeAt(position) != null
    } else {
        false
    }
}

//fun <T, VH : ViewHolder<T>> RecyclerAdapter.layoutViewSupport(holderClass: Class<VH>): LayoutViewFactory<T, VH> {
//    return layoutViewSupport(holderClass.layoutId, adapter.getViewType(holderClass))
//}
//
//fun <T, VH : ViewHolder<T>> layoutViewSupport(layoutId: Int, viewType: Int = 0): LayoutViewFactory<T, VH> {
//    return LayoutViewFactory(LayoutInflater.from(context), layoutId, viewType, this)
//}