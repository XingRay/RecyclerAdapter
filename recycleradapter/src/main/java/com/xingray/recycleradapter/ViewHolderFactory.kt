package com.xingray.recycleradapter

import android.view.View

/**
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:26
 *
 *
 * description : `ViewHolder`的工厂类，用于生成`ViewHolder`
 */
interface ViewHolderFactory<VH> {
    /**
     * 构造`ViewHolder`
     *
     * @param itemView 条目的视图
     * @param viewType 条目的布局类型
     * @return `ViewHolder`
     */
    fun build(itemView: View, viewType: Int): VH
}
