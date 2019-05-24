package com.leixing.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leixing.recycleradapter.OnItemClickListener;
import com.leixing.recycleradapter.RecyclerAdapter;
import com.leixing.recycleradapter.ViewHolderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2019/3/7 16:58
 */
public class RecyclerAdapterActivity extends Activity {

    public static final int SIZE = 20;
    private Activity mActivity;
    private Context mContext;
    private RecyclerView rvList;
    private RecyclerAdapter<Banner, BannerViewHolder> mAdapter;

    private static int[] IMG_RES_ID = new int[]{
            R.mipmap.img_01,
            R.mipmap.img_02,
            R.mipmap.img_03,
            R.mipmap.img_04,
            R.mipmap.img_05
    };

    public static void start(Context context) {
        Intent intent = new Intent(context, RecyclerAdapterActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = getApplicationContext();

        setContentView(R.layout.activity_recycler_adapter);

        initView();
        loadData();
    }

    private void initView() {
        rvList = findViewById(R.id.rv_list);

        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new RecyclerAdapter<Banner, BannerViewHolder>(mContext)
                .itemLayoutId(R.layout.view_banner_list_item)
                .viewHolderFactory(new ViewHolderFactory<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder build(@NonNull View itemView, int viewType) {
                        return new BannerViewHolder(itemView);
                    }
                })
                .itemClickListener(new OnItemClickListener<Banner>() {
                    @Override
                    public void onItemClick(@NonNull ViewGroup parent, int position, Banner banner) {
                        onBannerClick(position, banner);
                    }
                });

        rvList.setAdapter(mAdapter);
    }

    private void onBannerClick(int position, Banner banner) {
        banner.setSelect(!banner.isSelect());
        mAdapter.notifyItemChanged(position, banner.isSelect());
    }

    private void loadData() {
        TaskExecutor.io(new Runnable() {
            @Override
            public void run() {
                final List<Banner> banners = loadBanners();
                TaskExecutor.ui(new Runnable() {
                    @Override
                    public void run() {
                        showBanners(banners);
                    }
                });
            }
        });
    }

    private List<Banner> loadBanners() {
        List<Banner> banners = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            banners.add(new Banner(IMG_RES_ID[i % IMG_RES_ID.length]));
        }
        return banners;
    }

    private void showBanners(List<Banner> banners) {
        mAdapter.update(banners);
    }
}
