package com.xingray.recycleradapter

import android.view.View

/**
 *{@code ViewHolder}工厂
 *
 * @author : leixing
 * @date : 19-7-14
 * email : leixing1012@qq.com
 *
 */
interface ViewHolderFactory<VH> {
    fun createViewHolder(itemView: View): VH
}