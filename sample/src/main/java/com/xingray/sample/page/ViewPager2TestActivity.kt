package com.xingray.sample.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.xingray.recycleradapter.LayoutId
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.recycleradapter.ViewHolder
import com.xingray.sample.R
import com.xingray.sample.common.Data0
import com.xingray.sample.common.DataRepository
import com.xingray.sample.util.showToast

class ViewPager2TestActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ViewPager2TestActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var mAdapter: RecyclerAdapter? = null
    private val mRepository = DataRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2_test)

        val vpPager = findViewById<ViewPager2>(R.id.vp_pager)
        if (vpPager != null) {
            initPager(vpPager)
        }

        loadData()
    }

    private fun loadData() {
        mAdapter?.update(mRepository.loadData0())
    }

    private fun initPager(vpPager: ViewPager2) {
        vpPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        mAdapter = RecyclerAdapter(applicationContext)
                .addType(PagerViewHolder::class.java, null) { _, position, t ->
                    showToast("$position ${t.name} clicked")
                }

        vpPager.adapter = mAdapter
    }

    @LayoutId(R.layout.item_pager)
    class PagerViewHolder(itemView: View) : ViewHolder<Data0>(itemView) {
        private val tvText: TextView = itemView.findViewById(R.id.tv_text)

        override fun bindItemView(t: Data0, position: Int) {
            tvText.text = t.name
        }
    }
}