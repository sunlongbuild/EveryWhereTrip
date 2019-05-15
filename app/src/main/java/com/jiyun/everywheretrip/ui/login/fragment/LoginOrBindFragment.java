package com.jiyun.everywheretrip.ui.login.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.presenter.login.LoginOrBindPresenter;
import com.jiyun.everywheretrip.ui.guide.activity.GuideActivity;
import com.jiyun.everywheretrip.ui.login.activity.AgreementActivity;
import com.jiyun.everywheretrip.ui.login.activity.LoginActivity;
import com.jiyun.everywheretrip.ui.main.activity.MainActivity;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.login.LoginOrBindView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by $sl on 2019/5/4 16:27.
 */
public class LoginOrBindFragment extends BaseFragment<LoginOrBindView, LoginOrBindPresenter> implements LoginOrBindView {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.btn_send_verify)
    Button mBtnSendVerify;
    @BindView(R.id.iv_wechat)
    ImageView mIvWechat;
    @BindView(R.id.iv_qq)
    ImageView mIvQq;
    @BindView(R.id.iv_sina)
    ImageView mIvSina;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.ll_or)
    LinearLayout mLlOr;
    @BindView(R.id.ll_oauth_login)
    LinearLayout mLlOauthLogin;
    //因为登录和绑定手机号码是用的一个碎片,所以需要使用type隐藏和显示某一些view
    //如果是0:代表登录界面;1:代表要绑定手机
    private int mType;
    private static int COUNT_DOWN_TIME = 10;
    private int mTime = COUNT_DOWN_TIME;
    private Handler mHandler;
    //验证码
    private String mVerifyCode = "";
    private VerifyFragment mVerifyFragment;

    public static LoginOrBindFragment getInstance(int type) {
        LoginOrBindFragment fragment = new LoginOrBindFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected LoginOrBindPresenter initPresenter() {
        return new LoginOrBindPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_bind;
    }

    @Override
    public String getPhone() {
        return mEtPhone.getText().toString().trim();
    }

    @Override
    public Activity getAct() {
        return getActivity();
    }

    @Override
    public void go2MainActivity() {
        MainActivity.startAct(getContext());
    }

    @OnClick({R.id.btn_send_verify, R.id.iv_wechat, R.id.iv_qq, R.id.iv_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_verify:
                getVerifyCode();
                addVerifyFragment();
                time();
                break;
            case R.id.iv_wechat:
                mPresenter.oauthLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_qq:
                mPresenter.oauthLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.iv_sina:
                mPresenter.oauthLogin(SHARE_MEDIA.SINA);
                break;
        }
    }

    private void time() {
        //避免多次执行倒计时
        if (mTime > 0 && mTime < COUNT_DOWN_TIME) {
            return;
        }
        countDown();
    }

    /**
     * 倒计时,如果执行中,不要再调用
     */
    public void countDown() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //避免倒计时变成负的
                if (mTime <= 0) {
                    mTime = COUNT_DOWN_TIME;
                    return;
                }
                mTime--;
                if (mVerifyFragment != null) {
                    mVerifyFragment.setCountDownTime(mTime);
                }
                countDown();
            }
        }, 1000);
    }

    private void getVerifyCode() {
        if (mTime > 0 && mTime < COUNT_DOWN_TIME - 1) {
            //倒计时中
            return;
        }
        mVerifyCode = "";
        mPresenter.getVerifyCode();
    }

    private void addVerifyFragment() {
        if (TextUtils.isEmpty(getPhone())) {
            return;
        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //添加到回退栈
        fragmentTransaction.addToBackStack(null);
        mVerifyFragment = VerifyFragment.newIntance(mVerifyCode);
        fragmentTransaction.add(R.id.fl_container, mVerifyFragment).commit();
    }

    @Override
    protected void initListenter() {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changedBtnState(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    //改变按钮的状态
    private void changedBtnState(CharSequence s) {
        if (TextUtils.isEmpty(s)) {
            mBtnSendVerify.setBackgroundResource(R.drawable.bg_btn_ea_r15);
        } else {
            mBtnSendVerify.setBackgroundResource(R.drawable.bg_btn_fa6a13_r15);
        }
    }

    @Override
    public void setData(String code) {
        this.mVerifyCode = code;
        if (mVerifyFragment != null) {
            mVerifyFragment.setData(code);
        }
    }

    @Override
    protected void initView() {
        setProtocal();//设置协议
        getArgumentsData();
        showOrHideView();

    }

    private void setProtocal() {
        //创建SpannableString类
        SpannableString spanString = new SpannableString(getResources().getString(R.string.agree_protocol));
        //文字颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.c_FA6A13));
        //下划线
        UnderlineSpan underlineSpan = new UnderlineSpan();
        //点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                AgreementActivity.startActivity(getContext());//跳转到用户协议页面
            }
        };
        //设置
        spanString.setSpan(underlineSpan, 11, 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spanString.setSpan(clickableSpan, 11, 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spanString.setSpan(colorSpan, 11, 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mTvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        mTvProtocol.setText(spanString);
    }

    /**
     * 因为登录和绑定手机号码是用的一个碎片,所以需要使用type隐藏和显示某一些view
     */
    private void showOrHideView() {
        if (mType == LoginActivity.TYPE_LOGIN) {
            //登录
            //View.VISIBLE 显示
            //View.INVISIBLE 隐藏,占位置
            //View.GONE 隐藏 不占位置
            mIvBack.setVisibility(View.INVISIBLE);
            mLlOauthLogin.setVisibility(View.VISIBLE);
            mLlOr.setVisibility(View.VISIBLE);
        } else {
            //绑定
            mIvBack.setVisibility(View.VISIBLE);
            mLlOauthLogin.setVisibility(View.GONE);
            mLlOr.setVisibility(View.GONE);
        }
    }

    private void getArgumentsData() {
        Bundle arguments = getArguments();
        mType = arguments.getInt(Constants.TYPE);
    }
}
