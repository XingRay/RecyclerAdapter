package com.xingray.sample.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xingray.recycleradapter.ItemClickListener;
import com.xingray.recycleradapter.RecyclerAdapter;
import com.xingray.sample.R;
import com.xingray.sample.common.DataRepository;
import com.xingray.sample.common.TestData;
import com.xingray.sample.common.TestViewHolder;
import com.xingray.sample.util.UiUtil;

import org.jetbrains.annotations.NotNull;

/**
 * @author leixing
 */
public class JavaTestActivity extends AppCompatActivity {

    private RecyclerAdapter mAdapter;

    private DataRepository mRepository = new DataRepository();

    public static void start(Context context) {
        Intent starter = new Intent(context, JavaTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_test);

        RecyclerView rvList = findViewById(R.id.rv_list);
        if (rvList != null) {
            initList(rvList);
        }

        loadData();
    }

    private void loadData() {
        mAdapter.update(mRepository.loadData());
    }

    private void initList(RecyclerView rvList) {
        rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mAdapter = new RecyclerAdapter(getApplicationContext())
                .typeSupport(TestData.class)
                .layoutViewSupport(R.layout.item_recycler_view_test_list)
                .viewHolder(TestViewHolder.class)
                .itemClickListener(new ItemClickListener<TestData>() {
                    @Override
                    public void onItemClick(@NotNull ViewGroup parent, int position, @org.jetbrains.annotations.Nullable TestData testData) {
                        UiUtil.showToast(JavaTestActivity.this, position + "" + testData.getName() + " clicked");
                    }
                }).registerView().registerType();

        rvList.setAdapter(mAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    }
}
