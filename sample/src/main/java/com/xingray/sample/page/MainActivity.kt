package com.xingray.sample.page

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xingray.recycleradapter.LayoutId
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.recycleradapter.ViewHolder
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
                Test("single type, show simple items") {
                    SingleSimpleActivity.start(this)
                },
                Test("single type, listen item click") {
                    SingleItemClickActivity.start(this)
                },
                Test("single type, item is selectable") {
                    SingleItemSelectActivity.start(this)
                },
                Test("multi type, show simple items") {
                    MultiSimpleActivity.start(this)
                },
                Test("multi type, listen item click") {
                    MultiItemClickActivity.start(this)
                },
                Test("multi type, item is selectable") {
                    MultiItemSelectActivity.start(this)
                },
                Test("multi layout, show simple items") {
                    MultiLayoutSimpleActivity.start(this)
                },
                Test("viewpager2 test") {
                    ViewPager2TestActivity.start(this)
                },
                Test("java test") {
                    JavaTestActivity.start(this)
                }
        ))
    }

    private fun initList(rvList: RecyclerView) {
        rvList.layoutManager = LinearLayoutManager(applicationContext)

        mAdapter = RecyclerAdapter(applicationContext)
                .addType(TestViewHolder::class.java, null) { _, _, t ->
                    t.starter.invoke()
                }

        rvList.adapter = mAdapter
        rvList.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
    }

    private data class Test(val name: String, val starter: () -> Unit)

    @LayoutId(R.layout.item_main_list)
    private class TestViewHolder(itemView: View) : ViewHolder<Test>(itemView) {

        private val tvText: TextView = itemView.findViewById(R.id.tv_text)

        override fun bindItemView(t: Test, position: Int) {
            tvText.text = t.name
        }
    }
}
