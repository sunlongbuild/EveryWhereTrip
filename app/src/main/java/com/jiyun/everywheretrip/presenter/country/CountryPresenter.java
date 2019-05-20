package com.jiyun.everywheretrip.presenter.country;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.country.CountryBean;
import com.jiyun.everywheretrip.model.country.CountryModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.country.CountryView;

/**
 * Created by $sl on 2019/5/17 15:51.
 */
public class CountryPresenter extends BasePresenter<CountryView> implements ResultCallBack<CountryBean> {

    private CountryModel mCountryModel;

    @Override
    protected void initModel() {
        mCountryModel = new CountryModel();
        mModels.add(mCountryModel);
    }
    public void getCouontryData(String token){
        mCountryModel.getCountryData(token,this);
    }

    @Override
    public void onSuccess(CountryBean bean) {
        mBaseView.setData(bean);
    }

    @Override
    public void onFail(String msg) {

    }
}
