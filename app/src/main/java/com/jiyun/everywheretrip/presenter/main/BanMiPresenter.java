package com.jiyun.everywheretrip.presenter.main;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.main.BanMiListBean;
import com.jiyun.everywheretrip.model.main.BanMiModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.main.BanMiView;

/**
 * Created by $sl on 2019/5/5 20:09.
 */
public class BanMiPresenter extends BasePresenter<BanMiView> implements ResultCallBack<BanMiListBean> {

    private BanMiModel mBanMiModel;

    @Override
    protected void initModel() {
        mBanMiModel = new BanMiModel();
    }
    public void getBanMiData(int page,String string){
        mBanMiModel.getData(this,1,string);
    }

    @Override
    public void onSuccess(BanMiListBean bean) {
        if(bean!=null){
            if(mBaseView!=null){
                mBaseView.setData(bean);
            }
        }
    }

    @Override
    public void onFail(String msg) {

    }
}
