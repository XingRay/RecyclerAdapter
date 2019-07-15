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

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/6/5 21:25
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class SimpleAPITestActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SimpleAPITestActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var mAdapter: RecyclerAdapter? = null
    private var mRepository = DataRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_api_test_activity)

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
                .addTypeSupport(TestData::class.java) { _, position ->
                    if (position % 2 == 0) {
                        return@addTypeSupport TestViewHolder::class.java
                    } else {
                        return@addTypeSupport TestViewHolder1::class.java
                    }
                }
                .register(TestViewHolder::class.java) { _, position, t ->
                    showToast("$position ${t.name} clicked layout0")
                }.register(TestViewHolder1::class.java) { _, position, t ->
                    showToast("$position ${t.name} clicked layout1")
                }.register(TestData1ViewHolder::class.java) { _, position, t ->
                    showToast("$position ${t.name} ${t.size} clicked TestData1")
                }
        rvList.adapter = mAdapter
        rvList.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
    }
}