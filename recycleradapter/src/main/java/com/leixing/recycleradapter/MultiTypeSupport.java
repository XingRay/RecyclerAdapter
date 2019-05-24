package com.leixing.recycleradapter;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * email : leixing1012@qq.com
 *
 * @author : leixing
 * @date : 2018/8/2 14:26
 * <p>
 * description : {@link RecyclerAdapter}的多布局支持接口，接口的定义同
 * {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}类似。
 */
public interface MultiTypeSupport<T> {
    /**
     * 根据数据和位置返回{@code ViewType}
     *
     * @param items    展示的数据
     * @param position 位置
     * @return {@code ViewType} 用具指定布局的类型
     */
    int getItemViewType(@NonNull List<T> items, int position);

    /**
     * 根据{@code ViewType}获取布局的资源id
     *
     * @param viewType 布局类型
     * @return 布局资源ID
     */
    int getLayoutId(int viewType);
}
