package com.xingray.sample.common;

import android.view.View;
import android.widget.TextView;

import com.xingray.recycleradapter.LayoutId;
import com.xingray.recycleradapter.ViewHolder;
import com.xingray.sample.R;

import org.jetbrains.annotations.NotNull;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing@baidu.com
 * @date : 2019/7/23 14:25
 */
@LayoutId(R.layout.item_data0_layout2)
public class Data0Layout2ViewHolder extends ViewHolder<Data0> {

    private TextView tvText;

    public Data0Layout2ViewHolder(@NotNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    @Override
    public void bindItemView(Data0 data0, int position) {
        tvText.setText(data0.getName());
    }

    private void initView(View itemView) {
        tvText = (TextView) itemView.findViewById(R.id.tv_text);
    }
}
