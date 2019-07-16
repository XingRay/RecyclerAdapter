package com.xingray.sample.page

import android.content.Context
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0Layout0ViewHolder
import com.xingray.sample.common.Data1Layout0ViewHolder
import com.xingray.sample.common.ListActivity

class MultiSimpleActivity : ListActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MultiSimpleActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun loadData(): List<Any> {
        return mRepository.loadData()
    }

    override fun getAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addType(Data0Layout0ViewHolder::class.java)
                .addType(Data1Layout0ViewHolder::class.java)
    }
}