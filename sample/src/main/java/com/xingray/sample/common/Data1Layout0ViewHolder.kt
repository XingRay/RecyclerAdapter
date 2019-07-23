package com.xingray.sample.common

import android.view.View
import android.widget.TextView
import com.xingray.recycleradapter.LayoutId
import com.xingray.recycleradapter.ViewHolder
import com.xingray.sample.R

@LayoutId(R.layout.item_data1_layout0)
class Data1Layout0ViewHolder(itemView: View) : ViewHolder<Data1>(itemView) {

    private val tvText: TextView = itemView.findViewById(R.id.tv_text)

    override fun onBindItemView(t: Data1, position: Int) {
        itemView.setBackgroundColor(t.color)

        tvText.text = t.name
        tvText.textSize = t.size
    }
}