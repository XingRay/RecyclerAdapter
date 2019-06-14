package com.xingray.sample.page

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xingray.recycleradapter.BaseViewHolder
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.sample.R

class MainActivity : AppCompatActivity() {

    private var mAdapter: RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvList: RecyclerView? = findViewById(R.id.rv_list)
        if (rvList != null) {
            initList(rvList)
        }

        loadData()
    }

    private fun loadData() {
        mAdapter?.update(listOf(
                Test("recyclerview test") {
                    RecyclerViewTestActivity.start(this)
                },
                Test("viewpager2 test") {
                    ViewPager2TestActivity.start(this)
                },
                Test("java test") {
                    JavaTestActivity.start(this)
                },
                Test("multi layout test") {
                    MultiLayoutTestActivity.start(this)
                },
                Test("simple api test") {
                    SimpleAPITestActivity.start(this)
                }
        ))
    }

    private fun initList(rvList: RecyclerView) {
        rvList.layoutManager = LinearLayoutManager(applicationContext)

        mAdapter = RecyclerAdapter(applicationContext)
                .typeSupport(Test::class.java)
                .viewSupport(R.layout.item_main_list, TestViewHolder::class.java) { _, _, t ->
                    t.starter.invoke()
                }
                .registerType()

        rvList.adapter = mAdapter
        rvList.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
    }

    private data class Test(val name: String, val starter: () -> Unit)

    private class TestViewHolder(itemView: View) : BaseViewHolder<Test>(itemView) {

        private val tvText: TextView = itemView.findViewById(R.id.tv_text)

        override fun bindItemView(t: Test, position: Int) {
            tvText.text = t.name
        }
    }
}
