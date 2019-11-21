package com.xingray.sample.common

import android.view.View
import com.xingray.recycleradapter.LayoutId
import com.xingray.recycleradapter.ViewHolder
import com.xingray.sample.R

/**
 * `null`数据的`ViewHolder`
 *
 * @author : leixing
 * @date : 2019/11/21 17:02
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
@LayoutId(R.layout.null_layout)
class NullViewHolder(itemView: View) : ViewHolder<Any>(itemView) {
    override fun onBindItemView(t: Any, position: Int) {

    }
}