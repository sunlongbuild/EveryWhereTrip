package com.jiyun.everywheretrip.ui.main.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.main.DetailsCollectBean;
import com.jiyun.everywheretrip.bean.main.HomeDetailsBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.RxUtils;
import com.jiyun.everywheretrip.presenter.main.HomeDetailsPresenter;
import com.jiyun.everywheretrip.ui.main.adapter.MyHomeDeTailsAdapter;
import com.jiyun.everywheretrip.utils.ImageLoader;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.main.HomeDetailsView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HomeDetailsActivity extends BaseActivity<HomeDetailsView, HomeDetailsPresenter> implements HomeDetailsView, MyHomeDeTailsAdapter.setOnClickListener {


    @BindView(R.id.lv)
    RecyclerView mLv;
    @BindView(R.id.homedetails_back)
    ImageView mHomedetailsBack;
    @BindView(R.id.homedetails_share)
    ImageView mHomedetailsShare;
    @BindView(R.id.home_details_money)
    Button mHomeDetailsMoney;
    private int mCid;
    private int position;
    private ArrayList<HomeDetailsBean.ResultBean> mList;
    private MyHomeDeTailsAdapter mAdapter;
    private boolean mIsCollected;
    private HomeDetailsBean mHomeDetailsBean;
    private String mToken;
    private ImageView mImageView;
    private TextView mTextView;


    @Override
    protected HomeDetailsPresenter initPresenter() {
        return new HomeDetailsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_details;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);

        Intent intent = getIntent();
        mCid = intent.getIntExtra("id", 0);
        String money = intent.getStringExtra("price");
        mHomeDetailsMoney.setText("￥" + money);

        mList = new ArrayList<>();
        mAdapter = new MyHomeDeTailsAdapter(this, mList);
        mLv.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.scrollToPositionWithOffset(position, 0);//没有滑动，直接置顶
        mLv.setLayoutManager(linearLayoutManager);//线性布局显示方式

    }

    @Override
    protected void initData() {
        showLoading();//显示等待条
        mToken = (String) SpUtil.getParam(Constants.TOKEN, "");
        mPresenter.getData(mCid);
    }

    @Override
    public void setData(HomeDetailsBean bean) {
        mHomeDetailsBean = bean;
        hideLoading();//隐藏等待条
        mList.add(bean.getResult());
        mAdapter.setList(mList);
        mAdapter.setId(bean.getResult().getRoute().getId());
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.homedetails_back, R.id.homedetails_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homedetails_back:
                finish();
                break;
            case R.id.homedetails_share:
                shareBorad();
                break;
        }
    }

    /**
     * 带面板的分享
     */
    private void shareBorad() {
        UMWeb thumb = new UMWeb(mList.get(0).getRoute().getShareURL());
        //thumb.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，
        new ShareAction(HomeDetailsActivity.this).withText("分享")
                .withMedia(thumb)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener)
                .open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(HomeDetailsActivity.this, "分享成功!", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(HomeDetailsActivity.this, "分享失败!" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(HomeDetailsActivity.this, "取消分享!", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void initListener() {
        mAdapter.setSetOnClickListener(this);
    }

    @Override
    public void onClickCollect(int position) {
        this.position = position;
    }

}
