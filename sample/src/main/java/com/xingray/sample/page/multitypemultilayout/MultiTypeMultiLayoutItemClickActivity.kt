package com.xingray.sample.page.multitypemultilayout

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.*
import com.xingray.sample.util.showToast

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/17 9:59
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class MultiTypeMultiLayoutItemClickActivity : ListActivity() {
    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, MultiTypeMultiLayoutItemClickActivity::class.java)
            activity.startActivity(starter)
        }
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
    }

    override fun loadData(): List<Any> {
        return repository.loadData()
    }
}