package com.xingray.sample.page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.R
import com.xingray.sample.common.DataRepository
import com.xingray.sample.common.TestData
import com.xingray.sample.common.TestViewHolder
import com.xingray.sample.util.showToast

class RecyclerViewTestActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, RecyclerViewTestActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var mAdapter: RecyclerAdapter<TestData, TestViewHolder>? = null
    private var mRepository = DataRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_test)

        val rvList: RecyclerView? = findViewById(R.id.rv_list)
        if (rvList != null) {
            initList(rvList)
        }

        loadData()
    }


    private fun loadData() {
        mAdapter?.update(mRepository.loadData())
    }

    private fun initList(rvList: RecyclerView) {
        rvList.layoutManager = LinearLayoutManager(applicationContext)

        mAdapter = RecyclerAdapter<TestData, TestViewHolder>(applicationContext)
                .itemLayoutId(R.layout.item_recycler_view_test_list)
                .viewHolderFactory { itemView, _ -> TestViewHolder(itemView) }
                .itemClickListener { _, position, t ->
                    showToast("$position ${t?.name} clicked")
                }

        rvList.adapter = mAdapter
        rvList.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
    }
}