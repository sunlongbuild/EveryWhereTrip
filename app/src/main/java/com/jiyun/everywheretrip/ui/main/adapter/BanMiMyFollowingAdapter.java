package com.jiyun.everywheretrip.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.bean.main.BanMiMyFollowingBean;
import com.jiyun.everywheretrip.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $sl on 2019/5/15 9:11.
 */
public class BanMiMyFollowingAdapter extends RecyclerView.Adapter<BanMiMyFollowingAdapter.ViewHolder> {
    private Context mContext;
    private List<BanMiMyFollowingBean.ResultBean.BanmiBean> mList;

    public BanMiMyFollowingAdapter(Context context, List<BanMiMyFollowingBean.ResultBean.BanmiBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_banmi_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BanMiMyFollowingBean.ResultBean.BanmiBean banmiBean = mList.get(i);
        viewHolder.mTvName.setText(banmiBean.getName());
        viewHolder.mTvLocation.setText(banmiBean.getLocation());
        viewHolder.mTvOccupation.setText(banmiBean.getOccupation());
        viewHolder.mTvFollowing.setText(banmiBean.getFollowing() + "");
        ImageLoader.setImage(mContext, banmiBean.getPhoto(), viewHolder.mIvPhoto, R.mipmap.zhanweitu_touxiang_mdpi);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView mIvPhoto;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_following)
        TextView mTvFollowing;
        @BindView(R.id.tv_location)
        TextView mTvLocation;
        @BindView(R.id.tv_occupation)
        TextView mTvOccupation;
        @BindView(R.id.follow)
        ImageView mFollow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
