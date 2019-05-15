package com.jiyun.everywheretrip.presenter.main;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.main.BanMiDetailsBean;
import com.jiyun.everywheretrip.bean.main.HomeDetailsBean;
import com.jiyun.everywheretrip.model.main.BanMiDetailsModel;
import com.jiyun.everywheretrip.model.main.HomeDetailsModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.main.BanMiDetailsView;
import com.jiyun.everywheretrip.view.main.HomeDetailsView;

/**
 * Created by $sl on 2019/5/5 10:05.
 */
public class BanMiDetailsPresenter extends BasePresenter<BanMiDetailsView> implements ResultCallBack<BanMiDetailsBean> {

    private BanMiDetailsModel mBanMiDetailsModel;

    @Override
    protected void initModel() {
        mBanMiDetailsModel = new BanMiDetailsModel();
        mModels.add(mBanMiDetailsModel);
    }

    public void getData(int banmiId, String token, int page) {
        mBanMiDetailsModel.getData(banmiId, token, page, this);
    }

    @Override
    public void onSuccess(BanMiDetailsBean bean) {
        if (mBaseView != null) {
            mBaseView.setData(bean);
        }
    }

    @Override
    public void onFail(String msg) {

    }
}
