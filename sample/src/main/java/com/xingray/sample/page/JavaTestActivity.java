package com.xingray.sample.page;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import com.xingray.recycleradapter.ItemClickListener;
import com.xingray.recycleradapter.RecyclerAdapter;
import com.xingray.sample.R;
import com.xingray.sample.common.Data0;
import com.xingray.sample.common.Data0Layout0ViewHolder;
import com.xingray.sample.common.DataRepository;
import com.xingray.sample.common.ListActivity;
import com.xingray.sample.util.UiUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author leixing
 */
public class JavaTestActivity extends ListActivity {

    private RecyclerAdapter mAdapter;

    private DataRepository mRepository = new DataRepository();

    public static void start(Context context) {
        Intent starter = new Intent(context, JavaTestActivity.class);
        context.startActivity(starter);
    }

    @NotNull
    @Override
    public List<Object> loadData() {
        List<?> data = mRepository.loadData0();
        return (List<Object>) data;
    }

    @NotNull
    @Override
    public RecyclerAdapter getAdapter() {
        return new RecyclerAdapter(getApplicationContext())
                .newTypeSupport(Data0.class)
                .layoutViewSupport(R.layout.item_data0_layout0)
                .viewHolderClass(Data0Layout0ViewHolder.class)
                .itemClickListener(new ItemClickListener<Data0>() {
                    @Override
                    public void onItemClick(@NotNull ViewGroup parent, int position, @org.jetbrains.annotations.Nullable Data0 data0) {
                        UiUtil.showToast(JavaTestActivity.this, position + "" + data0.getName() + " clicked");
                    }
                }).registerView().registerType();
    }
}
