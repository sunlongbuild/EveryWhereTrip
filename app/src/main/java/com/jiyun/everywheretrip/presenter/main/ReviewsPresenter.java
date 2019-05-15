package com.jiyun.everywheretrip.presenter.main;

import android.util.Log;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.main.ReviewsBean;
import com.jiyun.everywheretrip.model.main.ReviewsModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.main.ReviewsView;

/**
 * Created by $sl on 2019/5/14 9:49.
 */
public class ReviewsPresenter extends BasePresenter<ReviewsView> implements ResultCallBack<ReviewsBean> {

    private ReviewsModel mReviewsModel;
    private static final String TAG = "ReviewsPresenter----";

    @Override
    protected void initModel() {
        mReviewsModel = new ReviewsModel();
        mModels.add(mReviewsModel);
    }
    public void getReviews(int routeId, int page, String token){
        mReviewsModel.getData(routeId,page,token,this);
    }

    @Override
    public void onSuccess(ReviewsBean bean) {
        if (bean!=null&&bean.getResult().getReview().size()>0){
            if (mBaseView!=null){
                mBaseView.setData(bean);
            }
        }
    }

    @Override
    public void onFail(String msg) {
        if (mBaseView!=null){

        }
    }
}
