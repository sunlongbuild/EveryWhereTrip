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
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.bean.main.HomeDetailsBean;
import com.jiyun.everywheretrip.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $sl on 2019/5/9 8:59.
 */
public class MyHomeDetailsCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeDetailsBean.ResultBean.ReviewsBean> mList;


    public MyHomeDetailsCommentAdapter(Context context, List<HomeDetailsBean.ResultBean.ReviewsBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_homedetails_comment, null);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyHolder threeHolder = (MyHolder) viewHolder;
        threeHolder.mTvName.setText(mList.get(i).getUserName());
        threeHolder.mTvTime.setText(mList.get(i).getCreatedAt());
        threeHolder.mTvContent.setText(mList.get(i).getContent());
        ImageLoader.setCircleImage(mContext,mList.get(i).getUserPhoto(),threeHolder.mIvHeadPicture,R.mipmap.zhanweitu_touxiang_mdpi);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headPicture)
        ImageView mIvHeadPicture;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
