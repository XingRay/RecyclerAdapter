package com.xingray.sample.page;

import android.content.Context;
import android.content.Intent;

import com.xingray.recycleradapter.RecyclerAdapter;
import com.xingray.sample.common.Data0;
import com.xingray.sample.common.Data0Layout0ViewHolder;
import com.xingray.sample.common.ListActivity;
import com.xingray.sample.util.UiUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author leixing
 */
public class JavaTestActivity extends ListActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, JavaTestActivity.class);
        context.startActivity(starter);
    }

    @NotNull
    @Override
    public List<Object> loadData() {
        List<?> data = getRepository().loadData0();
        // noinspection unchecked
        return (List<Object>) data;
    }

    @NotNull
    @Override
    public RecyclerAdapter createAdapter() {
        return new RecyclerAdapter(getApplicationContext())
                .addTypeJ(Data0.class, Data0Layout0ViewHolder.class, (parent, position, data0) ->
                        UiUtil.showToast(JavaTestActivity.this, position + "" + data0.getName() + " clicked")
                );
    }
}
