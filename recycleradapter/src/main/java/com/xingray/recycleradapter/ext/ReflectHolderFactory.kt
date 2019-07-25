package com.xingray.recycleradapter.ext

import android.view.View
import com.xingray.recycleradapter.HolderFactory
import com.xingray.recycleradapter.Initializer

/**
 * Holder工厂实现：反射构造Holder
 *
 * @author : leixing
 * @date : 2019/7/17 9:16
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ReflectHolderFactory<VH>(private val holderClass: Class<VH>) : HolderFactory<VH> {

    private var initializer: ((VH) -> Unit)? = null

    fun initializerJ(initializer: Initializer<VH>?): ReflectHolderFactory<VH> {
        if (initializer == null) {
            this.initializer = null
        } else {
            this.initializer = initializer::initialize
        }
        return this
    }

    fun initializer(initializer: ((VH) -> Unit)?): ReflectHolderFactory<VH> {
        this.initializer = initializer
        return this
    }

    override fun create(itemView: View): VH {
        val constructor = holderClass.getConstructor(View::class.java)
        constructor.isAccessible = true
        val holder: VH = constructor.newInstance(itemView)
        initializer?.invoke(holder)
        return holder
    }
}