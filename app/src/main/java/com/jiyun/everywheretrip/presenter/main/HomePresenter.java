package com.jiyun.everywheretrip.presenter.main;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.base.BaseView;
import com.jiyun.everywheretrip.bean.main.HomeBean;
import com.jiyun.everywheretrip.model.main.HomeModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.main.HomeView;

import java.util.HashMap;
import java.util.Observable;

/**
 * Created by $sl on 2019/5/5 10:05.
 */
public class HomePresenter extends BasePresenter<HomeView> implements ResultCallBack<HomeBean> {

    private HomeModel mHomeModel;

    @Override
    protected void initModel() {
        mHomeModel = new HomeModel();
        mModels.add(mHomeModel);
    }

    public void getData(int page,String string) {
        mHomeModel.getData(this, page,string);
    }

    @Override
    public void onSuccess(HomeBean bean) {
        if (mBaseView != null) {
            mBaseView.onSuccess(bean);
        }
    }

    @Override
    public void onFail(String msg) {
        if (mBaseView != null) {
            mBaseView.onFailed(msg);
        }
    }
}
