package com.xingray.sample.common

import android.view.View
import android.widget.TextView
import com.xingray.recycleradapter.BaseViewHolder
import com.xingray.sample.R

class TestViewHolder(itemView: View) : BaseViewHolder<TestData>(itemView) {

    private val tvText: TextView = itemView.findViewById(R.id.tv_text)

    override fun onBindItemView(t: TestData?, position: Int) {
        if (t != null) {
            tvText.text = t.name
            tvText.setBackgroundColor(t.color)
        }
    }
}