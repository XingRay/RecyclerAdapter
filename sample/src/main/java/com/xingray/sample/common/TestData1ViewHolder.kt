package com.xingray.sample.common

import android.view.View
import android.widget.TextView
import com.xingray.recycleradapter.BaseViewHolder
import com.xingray.sample.R

class TestData1ViewHolder(itemView: View) : BaseViewHolder<TestData1>(itemView) {

    private val tvText: TextView = itemView.findViewById(R.id.tv_text)

    override fun bindItemView(t: TestData1, position: Int) {
        tvText.text = t.name
        tvText.textSize = t.size
    }
}