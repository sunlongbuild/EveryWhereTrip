package com.jiyun.everywheretrip.presenter.main;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.main.HomeBean;
import com.jiyun.everywheretrip.bean.main.HomeDetailsBean;
import com.jiyun.everywheretrip.model.main.HomeDetailsModel;
import com.jiyun.everywheretrip.model.main.HomeModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.main.HomeDetailsView;
import com.jiyun.everywheretrip.view.main.HomeView;

/**
 * Created by $sl on 2019/5/5 10:05.
 */
public class HomeDetailsPresenter extends BasePresenter<HomeDetailsView> {

    private HomeDetailsModel mHomeParticularsModel;

    @Override
    protected void initModel() {
        mHomeParticularsModel = new HomeDetailsModel();
        mModels.add(mHomeParticularsModel);
    }

    public void getData(int cid){
        mHomeParticularsModel.getData(cid, new ResultCallBack<HomeDetailsBean>() {
            @Override
            public void onSuccess(HomeDetailsBean bean) {
                if (bean!=null){
                    if (mBaseView!=null){
                        mBaseView.setData(bean);
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

}
