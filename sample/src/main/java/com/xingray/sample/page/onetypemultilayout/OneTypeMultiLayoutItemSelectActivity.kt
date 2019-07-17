package com.xingray.sample.page.onetypemultilayout

import android.app.Activity
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
 * @date : 2019/7/17 9:49
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class OneTypeMultiLayoutItemSelectActivity : ListActivity() {
    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, OneTypeMultiLayoutItemSelectActivity::class.java)
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
                }.addToAdapter()
    }

    override fun loadData(): List<Any> {
        return repository.loadData0()
    }
}