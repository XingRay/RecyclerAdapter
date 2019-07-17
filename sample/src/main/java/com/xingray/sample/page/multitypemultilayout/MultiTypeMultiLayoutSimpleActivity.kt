package com.xingray.sample.page.multitypemultilayout

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.*

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/17 9:59
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class MultiTypeMultiLayoutSimpleActivity : ListActivity() {
    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, MultiTypeMultiLayoutSimpleActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addViewTypeMapper(Data0::class.java) { _, position -> position % 2 }
                .addLayoutViewSupport(0, Data0Layout0ViewHolder::class.java)
                .addLayoutViewSupport(1, Data0Layout1ViewHolder::class.java)
                .addViewTypeMapper(Data1::class.java) { _, position -> 2 + position % 2 }
                .addLayoutViewSupport(2, Data1Layout0ViewHolder::class.java)
                .addLayoutViewSupport(3, Data1Layout1ViewHolder::class.java)
    }

    override fun loadData(): List<Any> {
        return repository.loadData()
    }
}