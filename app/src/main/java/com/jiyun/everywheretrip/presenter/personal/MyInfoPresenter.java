package com.jiyun.everywheretrip.presenter.personal;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.main.MyInfoBean;
import com.jiyun.everywheretrip.model.personal.MyInfoModel;
import com.jiyun.everywheretrip.model.personal.PersonalModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.personal.MyInfoView;
import com.jiyun.everywheretrip.view.personal.PersonalView;

/**
 * Created by $sl on 2019/5/5 10:05.
 */
public class MyInfoPresenter extends BasePresenter<MyInfoView> implements ResultCallBack<MyInfoBean> {

    private MyInfoModel mMyInfoModel;

    @Override
    protected void initModel() {
        mMyInfoModel = new MyInfoModel();
        mModels.add(mMyInfoModel);
    }

    public void getMyInfo(String token) {
        mMyInfoModel.getMyInfo(token, this);
    }

    @Override
    public void onSuccess(MyInfoBean bean) {
        mBaseView.setData(bean);
    }

    @Override
    public void onFail(String msg) {

    }
}
