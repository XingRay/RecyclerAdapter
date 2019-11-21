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
import com.xingray.sample.page.multitype.MultiTypeItemClickActivity
import com.xingray.sample.page.multitype.MultiTypeItemSelectActivity
import com.xingray.sample.page.multitype.MultiTypeSimpleActivity
import com.xingray.sample.page.multitypemultilayout.MultiTypeMultiLayoutItemClickActivity
import com.xingray.sample.page.multitypemultilayout.MultiTypeMultiLayoutItemSelectActivity
import com.xingray.sample.page.multitypemultilayout.MultiTypeMultiLayoutSimpleActivity
import com.xingray.sample.page.nullsupport.NullSupportActivity
import com.xingray.sample.page.onetype.OneTypeItemClickActivity
import com.xingray.sample.page.onetype.OneTypeItemSelectActivity
import com.xingray.sample.page.onetype.OneTypeSimpleActivity
import com.xingray.sample.page.onetypemultilayout.OneTypeMultiLayoutItemClickActivity
import com.xingray.sample.page.onetypemultilayout.OneTypeMultiLayoutItemSelectActivity
import com.xingray.sample.page.onetypemultilayout.OneTypeMultiLayoutSimpleActivity

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
                Test("one type, show simple items") {
                    OneTypeSimpleActivity.start(this)
                },
                Test("one type, listen item click") {
                    OneTypeItemClickActivity.start(this)
                },
                Test("one type, item is selectable") {
                    OneTypeItemSelectActivity.start(this)
                },
                Test("multi type, show simple items") {
                    MultiTypeSimpleActivity.start(this)
                },
                Test("multi type, listen item click") {
                    MultiTypeItemClickActivity.start(this)
                },
                Test("multi type, item is selectable") {
                    MultiTypeItemSelectActivity.start(this)
                },
                Test("one type multi layout, show simple items") {
                    OneTypeMultiLayoutSimpleActivity.start(this)
                },
                Test("one type multi layout, listen item click") {
                    OneTypeMultiLayoutItemClickActivity.start(this)
                },
                Test("one type multi layout, item is selectable") {
                    OneTypeMultiLayoutItemSelectActivity.start(this)
                },
                Test("multi type multi layout, show simple items") {
                    MultiTypeMultiLayoutSimpleActivity.start(this)
                },
                Test("multi type multi layout, listen item click") {
                    MultiTypeMultiLayoutItemClickActivity.start(this)
                },
                Test("multi type multi layout, item is selectable") {
                    MultiTypeMultiLayoutItemSelectActivity.start(this)
                },
                Test("viewpager2 test") {
                    ViewPager2TestActivity.start(this)
                },
                Test("java test") {
                    JavaTestActivity.start(this)
                },
                Test("null support test") {
                    NullSupportActivity.start(this)
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

        override fun onBindItemView(t: Test, position: Int) {
            tvText.text = t.name
        }
    }
}
