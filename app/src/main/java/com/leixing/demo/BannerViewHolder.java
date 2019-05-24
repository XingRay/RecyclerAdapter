package com.leixing.demo;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.leixing.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2019/3/7 17:img_04
 */
public class BannerViewHolder extends BaseViewHolder<Banner> {
    private ImageView ivIcon;
    private View vSelect;

    public BannerViewHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    @Override
    protected void onBindItemView(Banner banner, int position) {
        ivIcon.setImageResource(banner.getCoverResId());
        showSelected(banner.isSelect());
    }

    @Override
    protected void onRefreshItemView(@NonNull List<Object> payloads) {
        super.onRefreshItemView(payloads);
        for (Object payload : payloads) {
            if (payload instanceof Boolean) {
                showSelected((Boolean) payload);
            }
        }
    }

    private void showSelected(boolean isSelected) {
        vSelect.setBackgroundColor(isSelected ? 0xffff0000 : 0xff333333);
    }

    private void initView(View itemView) {
        ivIcon = itemView.findViewById(R.id.iv_icon);
        vSelect = itemView.findViewById(R.id.v_select);
    }
}
