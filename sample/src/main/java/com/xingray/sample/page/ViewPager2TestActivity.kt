package com.xingray.sample.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.R
import com.xingray.sample.common.DataRepository
import com.xingray.sample.common.TestData
import com.xingray.sample.common.TestViewHolder
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
        mAdapter?.update(mRepository.loadData())
    }

    private fun initPager(vpPager: ViewPager2) {
        vpPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        mAdapter = RecyclerAdapter(applicationContext)
                .typeSupport(TestData::class.java)
                .layoutViewSupport(R.layout.item_view_pager2_test_list)
                .viewHolder(TestViewHolder::class.java)
                .itemClickListener { _, position, t ->
                    showToast("$position ${t.name} clicked")
                }.registerView().registerType()

        vpPager.adapter = mAdapter
    }
}