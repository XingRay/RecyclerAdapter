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
import com.xingray.sample.common.*
import com.xingray.sample.util.showToast

class MultiLayoutTestActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MultiLayoutTestActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var mAdapter: RecyclerAdapter? = null
    private var mRepository = DataRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_layout_test_activity)

        val rvList: RecyclerView? = findViewById(R.id.rv_list)
        if (rvList != null) {
            initList(rvList)
        }

        loadData()
    }


    private fun loadData() {
        val list = mutableListOf<Any>()
        list.addAll(mRepository.loadData())
        list.addAll(mRepository.loadData1())
        list.shuffle()

        mAdapter?.addAll(list)
    }

    private fun initList(rvList: RecyclerView) {
        rvList.layoutManager = LinearLayoutManager(applicationContext)

        mAdapter = RecyclerAdapter(applicationContext)
                .newTypeSupport(TestData::class.java)
                .viewSupport(R.layout.item_recycler_view_test_list, TestViewHolder::class.java, 0) { _, position, t ->
                    showToast("$position ${t.name} clicked layout0")
                }.viewSupport(R.layout.item_recycler_view_test_list1, TestViewHolder1::class.java, 1) { _, position, t ->
                    showToast("$position ${t.name} clicked layout1")
                }.viewTypeMapper { _, position ->
                    position % 2
                }.registerType()
                .newTypeSupport(TestData1::class.java)
                .viewSupport(R.layout.item_recycler_view_test1_list, TestData1ViewHolder::class.java, 2) { _, position, t ->
                    showToast("$position ${t.name} ${t.size} clicked TestData1")
                }.registerType()

        rvList.adapter = mAdapter
        rvList.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
    }
}