package com.xingray.sample.common

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.xingray.recycleradapter.LayoutId
import com.xingray.recycleradapter.ViewHolder
import com.xingray.sample.R

@LayoutId(R.layout.item_data0_layout1)
class Data0Layout1ViewHolder(itemView: View) : ViewHolder<Data0>(itemView) {

    private val tvText: TextView = itemView.findViewById(R.id.tv_text)
    private val vSelect: View = itemView.findViewById(R.id.v_select)

    private var selectOnClickListener: ((Data0, Int) -> Unit)? = null

    override fun onBindItemView(t: Data0, position: Int) {
        itemView.setBackgroundColor(t.color)

        tvText.text = t.name

        showSelected(t.selected)

        vSelect.setOnClickListener {
            selectOnClickListener?.invoke(t, adapterPosition)
        }
    }

    fun selectOnClickListener(listener: ((Data0, Int) -> Unit)?) {
        selectOnClickListener = listener
    }

    private fun showSelected(selected: Boolean) {
        vSelect.setBackgroundColor(if (selected) {
            Color.RED
        } else {
            Color.DKGRAY
        })
    }

    override fun onRefreshItemView(payload: Any) {
        if (payload is Boolean) {
            showSelected(payload)
        }
    }
}