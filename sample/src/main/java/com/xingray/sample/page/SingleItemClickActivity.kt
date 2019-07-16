package com.xingray.sample.page

import android.content.Context
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0Layout0ViewHolder
import com.xingray.sample.common.ListActivity
import com.xingray.sample.util.showToast

class SingleItemClickActivity : ListActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SingleItemClickActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun loadData(): List<Any> {
        return repository.loadData0()
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addType(Data0Layout0ViewHolder::class.java, null) { _, position, t ->
                    showToast("position:$position , name:${t.name}")
                }
    }
}