package com.xingray.recycleradapter.ext

import com.xingray.recycleradapter.*

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/23 20:05
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class LayoutViewSupport<T : Any, VH : ViewHolder<T>>(viewType: Int,
                                                     adapter: RecyclerAdapter,
                                                     viewFactory: ViewFactory,
                                                     private val holderFactory: ReflectHolderFactory<VH>)
    : ViewSupport<T, VH>(viewType, adapter, viewFactory::createItemView, holderFactory::create) {

    fun initializerJ(initializer: Initializer<VH>): LayoutViewSupport<T, VH> {
        holderFactory.initializerJ(initializer)
        return this
    }

    fun initializer(initializer: (VH) -> Unit): LayoutViewSupport<T, VH> {
        holderFactory.initializer(initializer)
        return this
    }
}