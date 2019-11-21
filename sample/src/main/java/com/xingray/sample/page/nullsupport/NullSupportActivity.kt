package com.xingray.sample.page.nullsupport

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.*
import com.xingray.sample.util.showToast

/**
 * 对为`null`的item的支持的测试
 *
 * @author : leixing
 * @date : 2019/11/21 16:54
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class NullSupportActivity : ListActivity() {

    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, NullSupportActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun loadData(): List<Any?> {
        val loadData = repository.loadData()
        val list = mutableListOf<Any?>()
        list.add(null)
        list.add(null)
        list.add(null)
        list.addAll(loadData)
        list.add(null)
        list.add(null)
        list.add(null)
        return list
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addViewTypeMapper(Data0::class.java) { _, position -> position % 2 }
                .newLayoutViewSupport(0, Data0Layout0ViewHolder::class.java)
                .itemClickListener { _, position, data0 ->
                    showToast("position:$position, layout:0, name:${data0.name}")
                }.addToAdapter()
                .newLayoutViewSupport(1, Data0Layout1ViewHolder::class.java)
                .itemClickListener { _, position, data0 ->
                    showToast("position:$position,layout:1, name:${data0.name}")
                }
                .addToAdapter()
                .addViewTypeMapper(Data1::class.java) { _, position -> 2 + position % 2 }
                .newLayoutViewSupport(2, Data1Layout0ViewHolder::class.java)
                .itemClickListener { _, position, data1 ->
                    showToast("position:$position, layout:2, size:${data1.size}")
                }
                .addToAdapter()
                .newLayoutViewSupport(3, Data1Layout1ViewHolder::class.java)
                .itemClickListener { _, position, data1 ->
                    showToast("position:$position, layout:3, size:${data1.size}")
                }
                .addToAdapter()
                .nullItemSupport(NullViewHolder::class.java, null)
                .nullItemClickLister { _, position, _ ->
                    showToast("NULL item clicked, position:$position")
                }
    }
}