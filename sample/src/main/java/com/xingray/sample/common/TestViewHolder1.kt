package com.xingray.sample.common

import android.view.View
import android.widget.Button
import com.xingray.recycleradapter.BaseViewHolder
import com.xingray.recycleradapter.LayoutId
import com.xingray.sample.R

@LayoutId(R.layout.item_recycler_view_test_list1)
class TestViewHolder1(itemView: View) : BaseViewHolder<TestData>(itemView) {

    private val btText: Button = itemView.findViewById(R.id.bt_text)

    override fun bindItemView(t: TestData, position: Int) {
        btText.text = t.name
    }
}