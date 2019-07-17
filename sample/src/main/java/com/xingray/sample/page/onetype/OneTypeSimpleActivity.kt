package com.xingray.sample.page.onetype

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0Layout0ViewHolder
import com.xingray.sample.common.ListActivity


/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/16 12:35
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class OneTypeSimpleActivity : ListActivity() {

    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, OneTypeSimpleActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addType(Data0Layout0ViewHolder::class.java)
    }

    override fun loadData(): List<Any> {
        return repository.loadData0()
    }
}