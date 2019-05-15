package com.jiyun.everywheretrip.ui.login.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseApp;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.presenter.login.VerifyPresenter;
import com.jiyun.everywheretrip.ui.login.activity.LoginActivity;
import com.jiyun.everywheretrip.view.login.VerifyView;
import com.jungly.gridpasswordview.GridPasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by $sl on 2019/5/4 20:18.
 */
public class VerifyFragment extends BaseFragment<VerifyView, VerifyPresenter> implements VerifyView {
    private static final String TAG = "VerifyFragment----";
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_send_again)
    TextView mTvSendAgain;
    @BindView(R.id.code)
    GridPasswordView mCode;
    @BindView(R.id.tv_wait)
    TextView mTvWait;
    private int mTime;

    /**
     * @param code 验证码,没有传递""
     * @return
     */
    public static VerifyFragment newIntance(String code) {
        VerifyFragment verifyFragment = new VerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.VERIFY_CODE, code);
        verifyFragment.setArguments(bundle);
        return verifyFragment;
    }

    @Override
    protected VerifyPresenter initPresenter() {
        return new VerifyPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getVerifyCode();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify;
    }

    @OnClick({R.id.iv_back, R.id.tv_send_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                //闪屏页销毁时将消息对象从消息队列移除并结束倒计时
                //handler.removeCallbacksAndMessages(null);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack();//弹栈
                break;
            case R.id.tv_send_again:
                //调用它是有条件的
                if (mTime == 0) {
                    mPresenter.getVerifyCode();
                    //重新发起倒计时
                    LoginOrBindFragment fragment = (LoginOrBindFragment) getActivity().getSupportFragmentManager().findFragmentByTag(LoginActivity.TAG);
                    fragment.countDown();
                }
                break;
        }
    }

    @Override
    public void setData(String data) {
        if (!TextUtils.isEmpty(data) && mTvWait != null) {
            mTvWait.setText(BaseApp.getRes().getString(R.string.verify_code) + data);
        }
    }

    @Override
    protected void initListenter() {
        mCode.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length() == 4)
                    mCode.setBackgroundColor(getResources().getColor(R.color.c_C8C8C8));
            }

            @Override
            public void onInputFinish(String psw) {
                showLoading();
                mTvWait.setText("正在登录...");
                mCode.setBackgroundColor(getResources().getColor(R.color.c_C8C8C8));
            }
        });
    }

    public void setCountDownTime(int time) {
        mTime = time;
        if (mTvSendAgain != null) {
            if (time != 0) {
                String format = String.format(getResources().getString(R.string.send_again) + "(%ss)", time);
                mTvSendAgain.setText(format);
                mTvSendAgain.setTextColor(getResources().getColor(R.color.c_999));
            } else {
                mTvSendAgain.setText(getResources().getString(R.string.send_again));
                mTvSendAgain.setTextColor(getResources().getColor(R.color.c_FA6A13));
            }
        }
    }

    @Override
    protected void initView() {
        String code = getArguments().getString(Constants.VERIFY_CODE);
        setData(code);
    }

}
