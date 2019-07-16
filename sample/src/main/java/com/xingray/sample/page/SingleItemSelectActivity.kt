package com.xingray.sample.page

import android.app.Activity
import android.content.Intent
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.common.Data0Layout1ViewHolder
import com.xingray.sample.common.ListActivity

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/16 13:22
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class SingleItemSelectActivity : ListActivity() {

    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, SingleItemSelectActivity::class.java)
            activity.startActivity(starter)
        }
    }

    override fun getAdapter(): RecyclerAdapter {
        return RecyclerAdapter(applicationContext)
                .addType(Data0Layout1ViewHolder::class.java, {
                    it.selectOnClickListener { t, position ->
                        t.selected = !t.selected
                        mAdapter?.notifyItemChanged(position, t.selected)
                    }
                }, null)
    }

    override fun loadData(): List<Any> {
        return mRepository.loadData0()
    }
}