package com.xingray.sample.page.onetypemultilayout

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0
import com.xingray.sample.common.Data0Layout0ViewHolder
import com.xingray.sample.common.Data0Layout1ViewHolder
import com.xingray.sample.common.ListActivity
import com.xingray.sample.util.showToast

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/17 9:31
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class OneTypeMultiLayoutItemClickActivity : ListActivity() {

    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, OneTypeMultiLayoutItemClickActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addViewTypeMapper(Data0::class.java) { _, position -> position % 2 }
                .newLayoutViewSupport(0, Data0Layout0ViewHolder::class.java)
                .itemClickListener { _, position, data0 ->
                    showToast("layout:0, position:$position, name:${data0.name}")
                }.addToAdapter()
                .newLayoutViewSupport(1, Data0Layout1ViewHolder::class.java)
                .itemClickListener { _, position, data0 ->
                    showToast("layout:1, position:$position, name:${data0.name}")
                }.addToAdapter()
    }

    override fun loadData(): List<Any> {
        return repository.loadData0()
    }
}