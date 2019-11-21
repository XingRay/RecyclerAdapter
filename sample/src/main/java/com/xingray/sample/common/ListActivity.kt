package com.xingray.sample.common

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.R

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/16 10:49
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
abstract class ListActivity : Activity() {

    var adapter: RecyclerAdapter? = null
    var repository = DataRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_activity)

        val rvList: RecyclerView? = findViewById(R.id.rv_list)
        if (rvList != null) {
            rvList.layoutManager = LinearLayoutManager(applicationContext)
            rvList.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))

            adapter = createAdapter()
            rvList.adapter = adapter
        }

        adapter?.update(loadData())
    }

    abstract fun loadData(): List<Any?>

    abstract fun createAdapter(): RecyclerAdapter
}