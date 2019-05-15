package com.jiyun.everywheretrip.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.main.BanMiFollowingBean;
import com.jiyun.everywheretrip.bean.main.BanMiListBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.RxUtils;
import com.jiyun.everywheretrip.presenter.main.BanMiPresenter;
import com.jiyun.everywheretrip.ui.main.activity.BanMiDetailsActivity;
import com.jiyun.everywheretrip.ui.main.adapter.MyBanMiAdapter;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.main.BanMiView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;

/**
 * Created by $sl on 2019/5/5 20:08.
 */
public class BanMiFragment extends BaseFragment<BanMiView, BanMiPresenter> implements BanMiView {
    @BindView(R.id.lv)
    RecyclerView mLv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private ArrayList<BanMiListBean.ResultBean.BanmiBean> mList;
    private MyBanMiAdapter mAdapter;
    private int mId;
    private boolean mIsFollowed;
    private String mToken;

    @Override
    protected BanMiPresenter initPresenter() {
        return new BanMiPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_banmi;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mAdapter = new MyBanMiAdapter(getContext(), mList);
        mLv.setAdapter(mAdapter);
        mLv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        mToken = (String) SpUtil.getParam(Constants.TOKEN, "");
        mPresenter.getBanMiData(1, mToken);
    }

    @Override
    public void setData(BanMiListBean banmiBean) {
        mList.addAll(banmiBean.getResult().getBanmi());
        mAdapter.notifyDataSetChanged();

        List<BanMiListBean.ResultBean.BanmiBean> banmi = banmiBean.getResult().getBanmi();
        mIsFollowed = banmi.get(0).isIsFollowed();
    }

    @Override
    protected void initListenter() {
        mAdapter.setOnItemClickListener(new MyBanMiAdapter.onItemClickListener() {
            @Override
            public void onClick(BanMiListBean.ResultBean.BanmiBean banmiBean) {
                Intent intent = new Intent(getContext(), BanMiDetailsActivity.class);
                intent.putExtra("banmiId", banmiBean.getId());
                startActivity(intent);
            }

            @Override
            public void onCaring() {

            }

            @Override
            public void onCancelCare() {

            }
        });
    }
}
