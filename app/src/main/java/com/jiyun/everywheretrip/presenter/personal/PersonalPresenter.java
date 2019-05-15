package com.jiyun.everywheretrip.presenter.personal;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.model.personal.PersonalModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.personal.PersonalView;

/**
 * Created by $sl on 2019/5/5 10:05.
 */
public class PersonalPresenter extends BasePresenter<PersonalView> {
    private PersonalModel mPersonalModel;

    @Override
    protected void initModel() {
        mPersonalModel = new PersonalModel();
        mModels.add(mPersonalModel);
    }

    public void upDateInfo(String photo,String name,String gender,String signature ,String token) {
        mPersonalModel.upDateInfo(photo,name,gender,signature,token, new ResultCallBack<String>() {
            @Override
            public void onSuccess(String string) {
                if (mBaseView != null) {
                    mBaseView.onSuccess(string);
                }
            }

            @Override
            public void onFail(String string) {
                if (mBaseView != null) {
                    mBaseView.onFailed(string);
                }
            }
        });
    }
}
