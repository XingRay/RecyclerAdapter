package com.xingray.sample.page;

import android.app.Activity;
import android.content.Intent;

import com.xingray.recycleradapter.RecyclerAdapter;
import com.xingray.sample.common.Data0;
import com.xingray.sample.common.Data0Layout0ViewHolder;
import com.xingray.sample.common.Data1;
import com.xingray.sample.common.Data1Layout0ViewHolder;
import com.xingray.sample.common.Data1Layout1ViewHolder;
import com.xingray.sample.common.ListActivity;
import com.xingray.sample.util.UiUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author leixing
 */
public class JavaTestActivity extends ListActivity {

    public static void start(Activity activity) {
        Intent starter = new Intent(activity, JavaTestActivity.class);
        activity.startActivity(starter);
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
                )
                .addViewTypeMapperJ(Data1.class, (data1, position) -> position % 2)
                .addLayoutViewSupport(0, Data1Layout0ViewHolder.class)
                .newLayoutViewSupport(1, Data1Layout1ViewHolder.class)
                .initializerJ(data1Layout1ViewHolder -> {

                })
                .itemClickListenerJ((parent, position, data1) -> {

                })
                .addToAdapter();
    }
}
