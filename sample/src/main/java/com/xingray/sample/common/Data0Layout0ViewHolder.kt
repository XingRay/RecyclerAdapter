package com.xingray.sample.common

import android.view.View
import android.widget.TextView
import com.xingray.recycleradapter.LayoutId
import com.xingray.recycleradapter.ViewHolder
import com.xingray.sample.R

@LayoutId(R.layout.item_data0_layout0)
class Data0Layout0ViewHolder(itemView: View) : ViewHolder<Data0>(itemView) {

    private val tvText: TextView = itemView.findViewById(R.id.tv_text)

    override fun bindItemView(t: Data0, position: Int) {
        itemView.setBackgroundColor(t.color)

        tvText.text = t.name
    }
}