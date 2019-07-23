package com.xingray.sample.common

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.xingray.recycleradapter.LayoutId
import com.xingray.recycleradapter.ViewHolder
import com.xingray.sample.R

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/16 18:31
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
@LayoutId(R.layout.item_data1_layout1)
class Data1Layout1ViewHolder(itemView: View) : ViewHolder<Data1>(itemView) {

    private val tvText: TextView = itemView.findViewById(R.id.tv_text)
    private val vSelect: View = itemView.findViewById(R.id.v_select)

    private var selectOnClickListener: ((Data1, Int) -> Unit)? = null

    override fun onBindItemView(t: Data1, position: Int) {
        itemView.setBackgroundColor(t.color)

        tvText.text = t.name

        showSelected(t.selected)

        vSelect.setOnClickListener {
            selectOnClickListener?.invoke(t, adapterPosition)
        }
    }

    override fun onRefreshItemView(payloads: List<Any>) {
        super.onRefreshItemView(payloads)
        for (payload in payloads) {
            if (payload is Boolean) {
                showSelected(payload)
            }
        }
    }

    fun selectOnClickListener(listener: ((Data1, Int) -> Unit)?) {
        selectOnClickListener = listener
    }

    private fun showSelected(selected: Boolean) {
        vSelect.setBackgroundColor(if (selected) {
            Color.RED
        } else {
            Color.DKGRAY
        })
    }


}