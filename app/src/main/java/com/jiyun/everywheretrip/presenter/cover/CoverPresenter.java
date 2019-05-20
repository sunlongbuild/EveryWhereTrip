package com.jiyun.everywheretrip.presenter.cover;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.cover.CityTabBean;
import com.jiyun.everywheretrip.model.cover.CoverModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.cover.CoverView;

/**
 * Created by $sl on 2019/5/16 20:46.
 */
public class CoverPresenter extends BasePresenter<CoverView> implements ResultCallBack<CityTabBean> {

    private CoverModel mCoverModel;

    @Override
    protected void initModel() {
        mCoverModel = new CoverModel();
        mModels.add(mCoverModel);
    }

    public void getCityTab(String token, int cityID) {
        mCoverModel.getCityTab(token, cityID, this);
    }

    @Override
    public void onSuccess(CityTabBean bean) {
        if (mBaseView != null) {
            if (bean != null) {
                mBaseView.setData(bean);
            }
        }
    }

    @Override
    public void onFail(String msg) {

    }
}
