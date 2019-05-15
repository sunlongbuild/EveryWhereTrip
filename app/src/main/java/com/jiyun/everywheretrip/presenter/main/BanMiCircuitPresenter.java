package com.jiyun.everywheretrip.presenter.main;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.main.BanMiCirCuitBean;
import com.jiyun.everywheretrip.bean.main.BanMiDetailsBean;
import com.jiyun.everywheretrip.model.main.BanMiCircuitModel;
import com.jiyun.everywheretrip.model.main.BanMiDetailsModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.main.BanMiCircuitView;
import com.jiyun.everywheretrip.view.main.BanMiDetailsView;

/**
 * Created by $sl on 2019/5/5 10:05.
 */
public class BanMiCircuitPresenter extends BasePresenter<BanMiCircuitView> implements ResultCallBack<BanMiCirCuitBean> {

    private BanMiCircuitModel mBanMiCircuitModel;

    @Override
    protected void initModel() {
        mBanMiCircuitModel = new BanMiCircuitModel();
        mModels.add(mBanMiCircuitModel);
    }

    public void getData(int banmiId,int page,String token){
        mBanMiCircuitModel.getData(banmiId,page,token,this);
    }

    @Override
    public void onSuccess(BanMiCirCuitBean bean) {
        mBaseView.setData(bean);
    }

    @Override
    public void onFail(String msg) {

    }
}
