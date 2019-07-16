package com.xingray.sample.page

import android.content.Context
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0
import com.xingray.sample.common.Data0Layout0ViewHolder
import com.xingray.sample.common.Data0Layout1ViewHolder
import com.xingray.sample.common.ListActivity

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/6/5 21:25
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class SimpleAPITestActivity : ListActivity() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SimpleAPITestActivity::class.java)
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
                .addType(Data0::class.java) { _, position ->
                    if (position % 2 == 0) {
                        return@addType Data0Layout0ViewHolder::class.java
                    } else {
                        return@addType Data0Layout1ViewHolder::class.java
                    }
                }
//                .addType(Data0Layout0ViewHolder::class.java, null) { _, position, t ->
//                    showToast("$position ${t.name} clicked layout0")
//                }.addType(Data0Layout1ViewHolder::class.java, null) { _, position, t ->
//                    showToast("$position ${t.name} clicked layout1")
//                }.addType(Data1Layout0ViewHolder::class.java, null) { _, position, t ->
//                    showToast("$position ${t.name} ${t.size} clicked Data1")
//                }
    }
}