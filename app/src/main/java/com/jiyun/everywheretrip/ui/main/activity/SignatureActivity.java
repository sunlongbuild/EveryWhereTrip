package com.jiyun.everywheretrip.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignatureActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.toolBar_title)
    TextView mToolBarTitle;
    @BindView(R.id.tv_finish)
    TextView mTvFinish;
    @BindView(R.id.et_signature)
    EditText mEtSignature;
    @BindView(R.id.tv_font_count)
    TextView mTvFontCount;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signature;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_finish, R.id.et_signature, R.id.tv_font_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                String name = mEtSignature.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("write",name);
                setResult(600,intent);
                finish();
                break;
            case R.id.et_signature:
                break;
            case R.id.tv_font_count:
                break;
        }
    }
    @Override
    protected void initListener() {
        mEtSignature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvFontCount.setText(mEtSignature.length() + "/10");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
