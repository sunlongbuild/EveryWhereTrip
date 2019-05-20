package com.jiyun.everywheretrip.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.bean.main.HomeBean;
import com.jiyun.everywheretrip.net.OnItemClickListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $sl on 2019/5/5 21:41.
 */
public class MyHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<HomeBean.ResultBean.BannersBean> mBannersList;
    private ArrayList<HomeBean.ResultBean.RoutesBean> mRoutesList;

    public MyHomeAdapter(Context context, ArrayList<HomeBean.ResultBean.BannersBean> bannersList, ArrayList<HomeBean.ResultBean.RoutesBean> routesList) {
        this.context = context;
        mBannersList = bannersList;
        mRoutesList = routesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        //如果条目的类型等于1,加载轮播图,否则加载文章列表
        if (i == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_main_home_banner, null);
            holder = new BannerViewHolder(view);
        } else if (i == 2) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_main_home_img, null);
            holder = new ImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_main_home_list, null);
            holder = new ArticleViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof BannerViewHolder) {
            BannerViewHolder bannnerViewHolder = (BannerViewHolder) viewHolder;//强转成子类
            bannnerViewHolder.mBanner.setImages(mBannersList);
            bannnerViewHolder.mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    HomeBean.ResultBean.BannersBean dataBean = (HomeBean.ResultBean.BannersBean) path;
                    com.jiyun.everywheretrip.utils.ImageLoader.setImage(context,dataBean.getImageURL(),imageView,R.mipmap.zhanweitu_touxiang_mdpi);
                }
            });
            bannnerViewHolder.mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
            bannnerViewHolder.mBanner.start();
        } else if (viewHolder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;

            final HomeBean.ResultBean.RoutesBean routesBean = mRoutesList.get(i);
            Glide.with(context).load(routesBean.getCardURL()).into(imageViewHolder.img);

            imageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (OnItemClickListener != null) {
                        OnItemClickListener.onClick(routesBean, i);
                    }
                }
            });

        } else {
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) viewHolder;
            final HomeBean.ResultBean.RoutesBean routesBean = mRoutesList.get(i);

            articleViewHolder.mTvIntro.setText(routesBean.getTitle());
            articleViewHolder.mTvCity.setText(routesBean.getCity());
            articleViewHolder.mTvPrice.setText("￥" + routesBean.getPrice());
            articleViewHolder.mTvTitle.setText(routesBean.getIntro());
            articleViewHolder.mTvPriceInCents.setText(routesBean.getPurchasedTimes() + "感兴趣");
            com.jiyun.everywheretrip.utils.ImageLoader.setCircleImage(context,routesBean.getCardURL(),articleViewHolder.mIvCardURL,R.mipmap.zhanweitu_touxiang_mdpi);

            articleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (OnItemClickListener != null) {
                        OnItemClickListener.onClick(routesBean, i);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mRoutesList.size();
    }

    //根据条件返回条目的类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else if (mRoutesList.get(position).getType().equals("bundle")) {
            return 2;
        } else {
            return 3;
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner mBanner;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_intro)
        TextView mTvIntro;
        @BindView(R.id.tv_city)
        TextView mTvCity;
        @BindView(R.id.tv_price)
        Button mTvPrice;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_priceInCents)
        TextView mTvPriceInCents;
        @BindView(R.id.iv_cardURL)
        ImageView mIvCardURL;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener OnItemClickListener;

    public void setOnItemClickListener(MyHomeAdapter.OnItemClickListener onItemClickListener) {
        OnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(HomeBean.ResultBean.RoutesBean bean, int position);
    }

}
