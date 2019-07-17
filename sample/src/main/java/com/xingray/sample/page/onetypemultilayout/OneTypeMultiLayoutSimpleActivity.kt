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
 * @date : 2019/7/16 18:43
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class OneTypeMultiLayoutSimpleActivity : ListActivity() {
    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, OneTypeMultiLayoutSimpleActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addViewTypeMapper(Data0::class.java) { _, position -> position % 2 }
                .addLayoutViewSupport(0, Data0Layout0ViewHolder::class.java)
                .addLayoutViewSupport(1, Data0Layout1ViewHolder::class.java)
    }

    override fun loadData(): List<Any> {
        return repository.loadData0()
    }
}