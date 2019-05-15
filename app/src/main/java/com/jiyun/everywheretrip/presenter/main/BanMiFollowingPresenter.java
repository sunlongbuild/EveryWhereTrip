package com.jiyun.everywheretrip.presenter.main;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.main.BanMiMyFollowingBean;
import com.jiyun.everywheretrip.model.main.BanMiFollowingModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.main.BanMiFollowingView;

/**
 * Created by $sl on 2019/5/15 8:58.
 */
public class BanMiFollowingPresenter extends BasePresenter<BanMiFollowingView> implements ResultCallBack<BanMiMyFollowingBean> {

    private BanMiFollowingModel mBanMiFollowingModel;

    @Override
    protected void initModel() {
        mBanMiFollowingModel = new BanMiFollowingModel();
        mModels.add(mBanMiFollowingModel);
    }

    public void getMyFollowing(String token,int page){
        mBanMiFollowingModel.getBanMiFollowing(token,page,this);
    }

    @Override
    public void onSuccess(BanMiMyFollowingBean bean) {
        mBaseView.setData(bean);
    }

    @Override
    public void onFail(String msg) {

    }
}
