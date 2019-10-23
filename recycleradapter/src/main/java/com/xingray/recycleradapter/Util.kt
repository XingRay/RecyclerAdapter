package com.xingray.recycleradapter

import android.util.SparseArray
import java.util.*

/**
 * description : 工具类，主要用于集合数据处理
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:31
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

internal fun <T> MutableList<T>.move(fromIndex: Int, toIndex: Int): Boolean {
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

internal fun <T> MutableList<T>.removeAll(): Boolean {
    if (isEmpty()) {
        return false
    }

    clear()
    return true
}

internal fun <T> MutableList<T>.update(list: List<T>?): Boolean {
    var updated = false
    if (!isEmpty()) {
        clear()
        updated = true
    }

    return if (list?.isNotEmpty() == true) {
        addAll(list)
    } else {
        updated
    }
}

internal fun <T> MutableList<T>.add(index: Int, t: T?): Boolean {
    return if (t == null) {
        false
    } else {
        add(index, t)
        true
    }
}

internal fun <T> MutableList<T>.addAll(index: Int, items: List<T>?): Boolean {
    return if (items == null) {
        false
    } else {
        addAll(index, items)
    }
}

internal fun <T> MutableList<T>.remove(position: Int, count: Int): List<T>? {
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

internal operator fun <E> SparseArray<E>.set(key: Int, value: E) {
    put(key, value)
}