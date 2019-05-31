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

    private var mAdapter: RecyclerAdapter<TestData, TestViewHolder>? = null
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

        mAdapter = RecyclerAdapter<TestData, TestViewHolder>(applicationContext)
                .itemLayoutId(R.layout.item_view_pager2_test_list)
                .viewHolderFactory { itemView, _ -> TestViewHolder(itemView) }
                .itemClickListener { _, position, t ->
                    showToast("$position ${t?.name} clicked")
                }

        vpPager.adapter = mAdapter
    }
}