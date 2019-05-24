package com.leixing.recycleradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * 用于{@link RecyclerView}实现列表展示功能
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:15
 * <p>
 * description : 用于{@RecyclerView}实现列表展示功能
 * 如果列表中的item只有一种布局，通过{@link RecyclerAdapter#itemLayoutId(int)}传入布局文件资源ID即可
 * 然后再通过{@link RecyclerAdapter#viewHolderFactory(ViewHolderFactory)}方法传入{@code ViewHolder}
 * 的工厂类即可。
 * 同时也支持多布局类型列表，通过调用{@link RecyclerAdapter#multiTypeSupport(MultiTypeSupport)}方法
 * 传入{@link MultiTypeSupport}的实现类即可
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class RecyclerAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {
    private static final int CLICK_TIME = 3000;

    private final ArrayList<T> mItems;
    private OnItemClickListener<T> mOnItemClickListener;
    private final LayoutInflater mInflater;
    private int mItemLayoutId;
    private ViewHolderFactory<VH> mFactory;
    private MultiTypeSupport<T> mMultiTypeSupport;

    public RecyclerAdapter(@NonNull Context context) {
        mItems = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    final public RecyclerAdapter<T, VH> itemLayoutId(int itemLayoutId) {
        mItemLayoutId = itemLayoutId;
        return this;
    }

    final public RecyclerAdapter<T, VH> viewHolderFactory(@Nullable ViewHolderFactory<VH> factory) {
        mFactory = factory;
        return this;
    }

    final public RecyclerAdapter<T, VH> multiTypeSupport(@Nullable MultiTypeSupport<T> support) {
        mMultiTypeSupport = support;
        return this;
    }

    public RecyclerAdapter<T, VH> itemClickListener(@Nullable OnItemClickListener<T> listener) {
        mOnItemClickListener = listener;
        return this;
    }


    final public void update(@Nullable List<T> list) {
        mItems.clear();
        if (list != null) {
            mItems.addAll(list);
        }
        notifyDataSetChanged();
    }

    final public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    final public void add(@Nullable T item) {
        add(mItems.size(), item);
    }

    public void add(int index, @Nullable T item) {
        if (item == null) {
            return;
        }
        mItems.add(index, item);
        notifyItemInserted(index);
    }

    public void addAll(@Nullable List<T> list) {
        addAll(mItems.size(), list);
    }

    public void addAll(int start, @Nullable List<T> items) {
        if (items == null) {
            return;
        }

        mItems.addAll(start, items);
        notifyItemRangeInserted(start, items.size());
    }

    public void remove(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mItems.size() - position);
    }

    public void remove(int position, int count) {
        if (position < 0 || position >= mItems.size()) {
            return;
        }

        ListIterator<T> iterator = mItems.listIterator(position);
        int countToRemove = count;
        while (iterator.hasNext() && countToRemove > 0) {
            iterator.next();
            iterator.remove();
            countToRemove--;
        }

        notifyItemRangeRemoved(position, count);
    }

    public void moveItem(int from, int to) {
        Util.move(mItems, from, to);
        notifyItemMoved(from, to);
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getItemViewType(mItems, position);
        }
        return super.getItemViewType(position);
    }

    @Override
    @NonNull
    public VH onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        int layoutId;
        if (mMultiTypeSupport != null) {
            layoutId = mMultiTypeSupport.getLayoutId(viewType);
        } else {
            layoutId = mItemLayoutId;
        }
        View itemView = mInflater.inflate(layoutId, parent, false);
        final VH viewHolder = mFactory.build(itemView, viewType);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(parent, position, mItems.get(position));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            holder.onRefreshItemView(payloads);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T t = mItems.get(position);
        holder.onBindItemView(t, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
