package com.xingray.recycleradapter.ext

import android.view.View
import com.xingray.recycleradapter.HolderFactory

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/17 9:16
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ReflectHolderFactory<VH>(private val holderClass: Class<VH>) : HolderFactory<VH> {
    override fun createViewHolder(itemView: View): VH {
        val constructor = holderClass.getConstructor(View::class.java)
        constructor.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        return constructor.newInstance(itemView)
    }
}