package com.xingray.sample.common

import android.view.View
import android.widget.TextView
import com.xingray.recycleradapter.BaseViewHolder
import com.xingray.recycleradapter.LayoutId
import com.xingray.sample.R

@LayoutId(R.layout.item_recycler_view_test_list)
class TestViewHolder(itemView: View) : BaseViewHolder<TestData>(itemView) {

    private val tvText: TextView = itemView.findViewById(R.id.tv_text)

    override fun bindItemView(t: TestData, position: Int) {
        tvText.text = t.name
        tvText.setBackgroundColor(t.color)
    }
}