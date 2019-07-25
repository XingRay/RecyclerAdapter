package com.xingray.sample.page.multitype

import android.content.Context
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0Layout0ViewHolder
import com.xingray.sample.common.Data1Layout0ViewHolder
import com.xingray.sample.common.ListActivity
import com.xingray.sample.util.showToast

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/6/5 21:25
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class MultiTypeItemClickActivity : ListActivity() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MultiTypeItemClickActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun loadData(): List<Any> {
        return repository.loadData()
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addView(Data0Layout0ViewHolder::class.java, null) { _, position, t ->
                    showToast("position:$position , name:${t.name}")
                }
                .addView(Data1Layout0ViewHolder::class.java, null) { _, position, t ->
                    showToast("position:$position , size:${t.size}")
                }
    }
}