package com.xingray.sample.common

import android.view.View
import android.widget.TextView
import com.xingray.recycleradapter.ViewHolder
import com.xingray.recycleradapter.LayoutId
import com.xingray.sample.R

@LayoutId(R.layout.item_recycler_view_test1_list)
class TestData1ViewHolder(itemView: View) : ViewHolder<TestData1>(itemView) {

    private val tvText: TextView = itemView.findViewById(R.id.tv_text)

    override fun bindItemView(t: TestData1, position: Int) {
        tvText.text = t.name
        tvText.textSize = t.size
    }
}