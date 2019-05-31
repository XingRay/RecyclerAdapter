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

    var mAdapter: RecyclerAdapter<Test, TestViewHolder>? = null

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
                }
        ))
    }

    private fun initList(rvList: RecyclerView) {
        rvList.layoutManager = LinearLayoutManager(applicationContext)

        mAdapter = RecyclerAdapter<Test, TestViewHolder>(applicationContext)
                .itemLayoutId(R.layout.item_main_list)
                .viewHolderFactory { itemView, _ -> TestViewHolder(itemView) }
                .itemClickListener { _, _, t -> t?.starter?.invoke() }

        rvList.adapter = mAdapter
        rvList.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
    }

    data class Test(val name: String, val starter: () -> Unit)

    class TestViewHolder(itemView: View) : BaseViewHolder<Test>(itemView) {

        private val tvText: TextView = itemView.findViewById(R.id.tv_text)

        override fun onBindItemView(t: Test?, position: Int) {
            tvText.text = t?.name
        }
    }
}
