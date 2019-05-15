package com.jiyun.everywheretrip.presenter.login;

import android.util.Log;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.api.login.LoginApiServer;
import com.jiyun.everywheretrip.base.BaseApp;
import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.login.LoginInfo;
import com.jiyun.everywheretrip.bean.login.VerifyCodeBean;
import com.jiyun.everywheretrip.model.login.LoginModel;
import com.jiyun.everywheretrip.model.login.LoginOrBindModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.utils.ToastUtil;
import com.jiyun.everywheretrip.view.login.LoginOrBindView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by $sl on 2019/5/4 16:28.
 */
public class LoginOrBindPresenter extends BasePresenter<LoginOrBindView> {
    private static final String TAG = "LoginOrBindPresenter";
    private LoginOrBindModel mLoginOrBindModel;
    private LoginModel mLoginModel;

    @Override
    protected void initModel() {
        mLoginOrBindModel = new LoginOrBindModel();
        mModels.add(mLoginOrBindModel);
        mLoginModel = new LoginModel();
        mModels.add(mLoginModel);
    }

    public void oauthLogin(SHARE_MEDIA type) {
        UMShareAPI umShareAPI = UMShareAPI.get(mBaseView.getAct());
        //media,三方平台
        //authListener,登录回调
        umShareAPI.getPlatformInfo(mBaseView.getAct(), type, authListener);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            logMap(data);
            //login();
            //只写微博的,微信的成功不了
            if (platform == SHARE_MEDIA.SINA) {
                loginSina(data.get("uid"));
            }
            if (platform == SHARE_MEDIA.QQ) {
                loginSina(data.get("uid"));
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.showShort(BaseApp.getRes().getString(R.string.oauth_error));
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.showShort(BaseApp.getRes().getString(R.string.oauth_cancel));
        }
    };

    /**
     * 新浪微博登录
     *
     * @param uid
     */
    private void loginSina(String uid) {
        mLoginOrBindModel.loginSina(new ResultCallBack<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo bean) {
                //登录成功了,需要做什么
                //1.跳转主页面
                //2.保存用户信息
                if (bean.getResult() != null) {
                    saveUserInfo(bean.getResult());
                    if (mBaseView != null) {
                        mBaseView.toastShort(BaseApp.getRes().getString(R.string.login_success));
                        mBaseView.go2MainActivity();
                    }
                } else {
                    if (mBaseView != null) {
                        mBaseView.toastShort(BaseApp.getRes().getString(R.string.login_fail));
                    }
                }
            }

            @Override
            public void onFail(String msg) {
                if (mBaseView != null) {
                    mBaseView.toastShort(msg);
                }
            }
        },uid);
    }

    /**
     * 保存用户信息
     *
     * @param result
     */
    private void saveUserInfo(LoginInfo.ResultBean result) {
        SpUtil.setParam(Constants.TOKEN, result.getToken());
        SpUtil.setParam(Constants.DESC, result.getDescription());
        SpUtil.setParam(Constants.USERNAME, result.getUserName());
        SpUtil.setParam(Constants.GENDER, result.getGender());
        SpUtil.setParam(Constants.EMAIL, result.getEmail());
        SpUtil.setParam(Constants.PHOTO, result.getPhoto());
        SpUtil.setParam(Constants.PHONE, result.getPhone());
    }

    private void logMap(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            Log.d(TAG, "logMap: " + entry.getKey() + "," + entry.getValue());
        }
    }

    public void getVerifyCode() {
        mLoginModel.getVerifyCode(new ResultCallBack<VerifyCodeBean>() {
            @Override
            public void onSuccess(VerifyCodeBean bean) {
                if (bean != null && bean.getCode() == LoginApiServer.SUCCESS_CODE){
                    if (mBaseView != null){
                        mBaseView.setData(bean.getData());
                    }
                }else {
                    if (mBaseView != null){
                        mBaseView.toastShort(BaseApp.getRes().getString(R.string.get_verify_fail));
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
