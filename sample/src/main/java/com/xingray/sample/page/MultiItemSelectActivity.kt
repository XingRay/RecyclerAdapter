package com.xingray.sample.page

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0Layout1ViewHolder
import com.xingray.sample.common.Data1Layout1ViewHolder
import com.xingray.sample.common.ListActivity

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/16 18:20
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class MultiItemSelectActivity : ListActivity() {

    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, MultiItemSelectActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun createAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addType(Data0Layout1ViewHolder::class.java, {
                    it.selectOnClickListener { data0, position ->
                        data0.selected = !data0.selected
                        adapter?.notifyItemChanged(position, data0.selected)
                    }
                }, null)
                .addType(Data1Layout1ViewHolder::class.java, {
                    it.selectOnClickListener { data1, position ->
                        data1.selected = !data1.selected
                        adapter?.notifyItemChanged(position, data1.selected)
                    }
                }, null)
    }

    override fun loadData(): List<Any> {
        return repository.loadData()
    }
}