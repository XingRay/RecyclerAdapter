package com.leixing.recycleradapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:37
 * <p>
 * description : 用于展示{@code RecyclerView}列表的{@ViewHolder}的基类，
 * 与{@link RecyclerAdapter}配合使用，支持{@code Item}的
 * 渲染({@link #onBindItemView(T, int)})
 * 和局部刷新({@link BaseViewHolder#onRefreshItemView(List<T>)})
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected void onRefreshItemView(@NonNull List<Object> payloads) {

    }

    protected abstract void onBindItemView(@Nullable T t, int position);
}
