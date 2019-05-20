package com.jiyun.everywheretrip.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.bean.main.ReviewsBean;
import com.jiyun.everywheretrip.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $sl on 2019/5/14 10:03.
 */
public class MyReviewsAdapter extends RecyclerView.Adapter<MyReviewsAdapter.ViewHolder> {
    private Context mContext;
    private List<ReviewsBean.ResultBean.ReviewBean> mList;

    public MyReviewsAdapter(Context context, List<ReviewsBean.ResultBean.ReviewBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setList(List<ReviewsBean.ResultBean.ReviewBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_homedetails_reviews, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ReviewsBean.ResultBean.ReviewBean bean = mList.get(i);
        ReviewsBean.ResultBean resultBean = new ReviewsBean.ResultBean();
        viewHolder.mTvName.setText(bean.getUserName());
        viewHolder.mTvTime.setText(bean.getCreatedAt());
        viewHolder.mTvContent.setText(bean.getContent());
        //viewHolder.mReviewsCount.setText(resultBean.getReviewsCount()+"");
        ImageLoader.setCircleImage(mContext,bean.getUserPhoto(),viewHolder.mIvHead,R.mipmap.zhanweitu_touxiang_mdpi);

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (OnItemClickListener != null) {
                    OnItemClickListener.OnClickListener(bean);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.reviews_count)
        TextView mReviewsCount;
        @BindView(R.id.praise_count)
        TextView mPraiseCount;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener OnItemClickListener;

    public void setOnItemClickListener(MyReviewsAdapter.OnItemClickListener onItemClickListener) {
        OnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnClickListener(ReviewsBean.ResultBean.ReviewBean listBean);
    }
}
