package com.xingray.sample.page.multitypemultilayout

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.*

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/17 10:00
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class MultiTypeMultiLayoutItemSelectActivity : ListActivity() {
    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, MultiTypeMultiLayoutItemSelectActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addViewTypeMapper(Data0::class.java) { _, position -> position % 2 }
                .addLayoutViewSupport(0, Data0Layout0ViewHolder::class.java)
                .newLayoutViewSupport(1, Data0Layout1ViewHolder::class.java)
                .initializer {
                    it.selectOnClickListener { data0, position ->
                        data0.selected = !data0.selected
                        adapter?.notifyItemChanged(position, data0.selected)
                    }
                }
                .addToAdapter()
                .addViewTypeMapper(Data1::class.java) { _, position -> 2 + position % 2 }
                .addLayoutViewSupport(2, Data1Layout0ViewHolder::class.java)
                .newLayoutViewSupport(3, Data1Layout1ViewHolder::class.java)
                .initializer {
                    it.selectOnClickListener { data1, position ->
                        data1.selected = !data1.selected
                        adapter?.notifyItemChanged(position, data1.selected)
                    }
                }
                .addToAdapter()
    }

    override fun loadData(): List<Any> {
        return repository.loadData()
    }
}