package com.xingray.sample.page

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
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
class MultiLayoutSimpleActivity : ListActivity() {
    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, MultiLayoutSimpleActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
    }

    override fun loadData(): List<Any> {
        return repository.loadData()
    }
}