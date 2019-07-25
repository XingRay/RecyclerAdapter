package com.xingray.sample.page.multitype

import android.content.Context
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0Layout0ViewHolder
import com.xingray.sample.common.Data1Layout0ViewHolder
import com.xingray.sample.common.ListActivity

class MultiTypeSimpleActivity : ListActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MultiTypeSimpleActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun loadData(): List<Any> {
        return repository.loadData()
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addType(Data0Layout0ViewHolder::class.java)
                .addType(Data1Layout0ViewHolder::class.java)
    }
}