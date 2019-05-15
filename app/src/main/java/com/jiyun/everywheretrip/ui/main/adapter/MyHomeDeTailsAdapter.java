package com.jiyun.everywheretrip.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.main.DetailsCollectBean;
import com.jiyun.everywheretrip.bean.main.HomeDetailsBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.RxUtils;
import com.jiyun.everywheretrip.ui.main.activity.HomeDetailsActivity;
import com.jiyun.everywheretrip.ui.main.activity.ReviewsActivity;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class MyHomeDeTailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<HomeDetailsBean.ResultBean> mList;
    private int mId;
    private String mToken;
    private boolean isCollected;

    public MyHomeDeTailsAdapter(Context context, List<HomeDetailsBean.ResultBean> list) {
        mContext = context;
        mList = list;
    }

    public void setList(List<HomeDetailsBean.ResultBean> list) {
        mList = list;
    }

    public void setId(int id) {
        mId = id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        if (i == 1) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_homedetails_largermap, null);
            holder = new LargerMapViewHolder(inflate);
        } else if (i == 2) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_homedetails_introduce, null);
            holder = new IntroduceViewHolder(inflate);
        } else if (i == 3) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_homeparti_three, null);
            holder = new CommentViewHolder(inflate);
        } else if (i == 4) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_homedetails_collect, null);
            holder = new CollectViewHolder(inflate);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        if (mList != null && mList.size() > 0) {
            if (viewHolder instanceof LargerMapViewHolder) {
                LargerMapViewHolder largerMapViewHolder = (LargerMapViewHolder) viewHolder;

                HomeDetailsBean.ResultBean.RouteBean route = mList.get(0).getRoute();
                mId = route.getId();
                Glide.with(mContext).load(route.getCardURL()).into(largerMapViewHolder.mIvBigImg);

            } else if (viewHolder instanceof IntroduceViewHolder) {
                IntroduceViewHolder introduceViewHolder = (IntroduceViewHolder) viewHolder;

                HomeDetailsBean.ResultBean.BanmiBean banmi = mList.get(0).getBanmi();

                introduceViewHolder.mTvName.setText(banmi.getName());
                introduceViewHolder.mTvIdentity.setText(banmi.getOccupation());
                introduceViewHolder.mTvAddress.setText(banmi.getLocation());
                introduceViewHolder.mTvInfo.setText(banmi.getIntroduction());
                Glide.with(mContext).load(banmi.getPhoto()).apply(requestOptions).into(introduceViewHolder.mIvHeadPicture);

            } else if (viewHolder instanceof CommentViewHolder) {
                CommentViewHolder commentViewHolder = (CommentViewHolder) viewHolder;
                //创建适配器
                MyHomeDetailsCommentAdapter adapter = new MyHomeDetailsCommentAdapter(mContext, mList.get(0).getReviews());
                commentViewHolder.mLv.setAdapter(adapter);
                //布局管理方式
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                commentViewHolder.mLv.setLayoutManager(linearLayoutManager);
            } else {
                final CollectViewHolder collectViewHolder = (CollectViewHolder) viewHolder;
                mToken = (String) SpUtil.getParam(Constants.TOKEN, "");
                int reviewsCount = mList.get(0).getReviewsCount();
                collectViewHolder.mTvWatchAppraise.setText("查看" + reviewsCount + "评论");
                //跳转评论页面
                collectViewHolder.mTvWatchAppraise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ReviewsActivity.class);
                        intent.putExtra("id", mId);
                        mContext.startActivity(intent);
                    }
                });

                //收藏部分
                isCollected = mList.get(0).getRoute().isIsCollected();//false未收藏
                if (isCollected) {//true  显示收藏状态
                    collectViewHolder.mIvCollect.setImageResource(R.drawable.collect_highlight);
                    collectViewHolder.mTvCollect.setText("已收藏");

                } else {
                    collectViewHolder.mIvCollect.setImageResource(R.drawable.collect_default);
                    collectViewHolder.mTvCollect.setText("未收藏");

                }
                //collectViewHolder.mIvCollect.setTag(isCollected);
                collectViewHolder.mLlCollect.setOnClickListener(new View.OnClickListener() {//对图片和收藏按钮进行监听
                    @Override
                    public void onClick(View v) {
                        if (isCollected) {//没点击过
                            collectViewHolder.mIvCollect.setImageResource(R.drawable.collect_default);
                            collectViewHolder.mTvCollect.setText("未收藏");
                            mList.get(0).getRoute().setIsCollected(false);
                            //collectViewHolder.mIvCollect.setTag(false);
                            collectFailed();
                        } else {
                            collectViewHolder.mIvCollect.setImageResource(R.drawable.collect_highlight);
                            collectViewHolder.mTvCollect.setText("已收藏");
                            mList.get(0).getRoute().setIsCollected(true);
                            //collectViewHolder.mIvCollect.setTag(true);

                            collectSuccess();

                        }
                        notifyDataSetChanged();


                    }
                });
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (setOnClickListener != null) {
                        setOnClickListener.onClickCollect(i);
                    }
                }
            });
        }
    }

    //取消收藏
    private void collectFailed() {
        MainApiService apiserver = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<DetailsCollectBean> observable = apiserver.getDetailsCancelCollect(mId, mToken);
        observable.compose(RxUtils.<DetailsCollectBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<DetailsCollectBean>() {
                    @Override
                    public void onNext(DetailsCollectBean detailsCollectBean) {
                        ToastUtil.showShort(detailsCollectBean.getDesc());
                    }

                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {

                    }
                });
    }

    //收藏
    private void collectSuccess() {
        MainApiService apiserver = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<DetailsCollectBean> observable = apiserver.getDetailsCollect(mId, mToken);
        observable.compose(RxUtils.<DetailsCollectBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<DetailsCollectBean>() {
                    @Override
                    public void onNext(DetailsCollectBean detailsCollectBean) {
                        ToastUtil.showShort(detailsCollectBean.getDesc());
                    }

                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {

                    }
                });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else if (position == 1) {
            return 2;
        } else if (position == 2) {
            return 3;
        } else {
            return 4;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class LargerMapViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_bigImg)
        ImageView mIvBigImg;

        public LargerMapViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class IntroduceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headPicture)
        ImageView mIvHeadPicture;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_identity)
        TextView mTvIdentity;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_info)
        TextView mTvInfo;

        public IntroduceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lv)
        RecyclerView mLv;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_watchAppraise)
        TextView mTvWatchAppraise;
        @BindView(R.id.iv_collect)
        ImageView mIvCollect;
        @BindView(R.id.tv_collect)
        TextView mTvCollect;
        @BindView(R.id.ll_collect)
        LinearLayout mLlCollect;

        public CollectViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private setOnClickListener setOnClickListener;

    public void setSetOnClickListener(MyHomeDeTailsAdapter.setOnClickListener setOnClickListener) {
        this.setOnClickListener = setOnClickListener;
    }

    public interface setOnClickListener {
        void onClickCollect(int position);
    }
}
