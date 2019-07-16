package com.xingray.sample.page

import android.content.Context
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.R
import com.xingray.sample.common.*
import com.xingray.sample.util.showToast

class MultiLayoutTestActivity : ListActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MultiLayoutTestActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun loadData(): List<Any> {
        val list = mutableListOf<Any>()
        list.addAll(mRepository.loadData0())
        list.addAll(mRepository.loadData1())
        list.shuffle()

        return list
    }

    override fun getAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .newTypeSupport(Data0::class.java)
                .viewSupport(R.layout.item_data0_layout0, Data0Layout0ViewHolder::class.java, 0) { _, position, t ->
                    showToast("$position ${t.name} clicked layout0")
                }.viewSupport(R.layout.item_data0_layout1, Data0Layout1ViewHolder::class.java, 1) { _, position, t ->
                    showToast("$position ${t.name} clicked layout1")
                }.viewTypeMapper { _, position ->
                    position % 2
                }.registerType()
                .newTypeSupport(Data1::class.java)
                .viewSupport(R.layout.item_data1_layout0, Data1Layout0ViewHolder::class.java, 2) { _, position, t ->
                    showToast("$position ${t.name} ${t.size} clicked Data1")
                }.registerType()
    }
}