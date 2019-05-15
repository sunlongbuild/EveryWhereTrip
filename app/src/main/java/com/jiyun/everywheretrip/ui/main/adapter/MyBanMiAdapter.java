package com.jiyun.everywheretrip.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.main.BanMiFollowingBean;
import com.jiyun.everywheretrip.bean.main.BanMiListBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.RxUtils;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by $sl on 2019/5/6 21:16.
 */
public class MyBanMiAdapter extends RecyclerView.Adapter<MyBanMiAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BanMiListBean.ResultBean.BanmiBean> list;
    private int mId;
    private String mToken;
    private boolean mIsFollowed;
    private boolean mMIsFollowed;

    public MyBanMiAdapter(Context context, ArrayList<BanMiListBean.ResultBean.BanmiBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_banmi_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        final BanMiListBean.ResultBean.BanmiBean bean = list.get(i);
        viewHolder.mTvName.setText(bean.getName());
        viewHolder.mTvFollowing.setText(bean.getFollowing() + "");
        viewHolder.mTvLocation.setText(bean.getLocation());
        viewHolder.mTvOccupation.setText(bean.getOccupation());
        Glide.with(context).load(bean.getPhoto()).into(viewHolder.mIvPhoto);

        mId = bean.getId();
        mToken = (String) SpUtil.getParam(Constants.TOKEN, "");

        mMIsFollowed = bean.isIsFollowed();
        if (mMIsFollowed==true) {
            viewHolder.mFollow.setBackground(context.getResources().getDrawable(R.mipmap.follow));
        } else {
            viewHolder.mFollow.setBackground(context.getResources().getDrawable(R.mipmap.follow_unselected));
        }

        //viewHolder.mFollow.setTag(false);
        viewHolder.mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean tag = (boolean) viewHolder.mFollow.getTag();
                if (!mMIsFollowed) {//false没收藏,开始收藏
                    viewHolder.mFollow.setBackground(context.getResources().getDrawable(R.mipmap.follow));
                    //viewHolder.mFollow.setTag(true);
                    mMIsFollowed=true;
                    follow();
                } else {//true已收藏,取消收藏
                    viewHolder.mFollow.setBackground(context.getResources().getDrawable(R.mipmap.follow_unselected));
                    //viewHolder.mFollow.setTag(false);
                    mMIsFollowed=false;
                    unfollowing();
                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(bean);
                }
            }
        });

    }

    private void unfollowing() {
        MainApiService apiserver = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<BanMiFollowingBean> observable = apiserver.getBanMiUnFollowing(mId, mToken);
        observable.compose(RxUtils.<BanMiFollowingBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BanMiFollowingBean>() {
                    @Override
                    public void onNext(BanMiFollowingBean banMiFollowingBean) {
                        ToastUtil.showShort(banMiFollowingBean.getResult().getMessage());
                    }

                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {

                    }
                });
    }

    private void follow() {
        MainApiService apiserver = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<BanMiFollowingBean> observable = apiserver.getBanMiFollowing(mId, mToken);
        observable.compose(RxUtils.<BanMiFollowingBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BanMiFollowingBean>() {
                    @Override
                    public void onNext(BanMiFollowingBean banMiFollowingBean) {
                        ToastUtil.showShort(banMiFollowingBean.getResult().getMessage());
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
    public int getItemCount() {
        return list.size();
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

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(MyBanMiAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onClick(BanMiListBean.ResultBean.BanmiBean banmiBean);

        void onCaring();

        void onCancelCare();
    }
}
