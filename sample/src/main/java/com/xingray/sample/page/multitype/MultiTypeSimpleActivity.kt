package com.xingray.sample.page.multitype

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0Layout0ViewHolder
import com.xingray.sample.common.Data1Layout0ViewHolder
import com.xingray.sample.common.ListActivity

class MultiTypeSimpleActivity : ListActivity() {

    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity, MultiTypeSimpleActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun loadData(): List<Any?> {
        return repository.loadData()
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addType(Data0Layout0ViewHolder::class.java)
                .addType(Data1Layout0ViewHolder::class.java)
    }
}