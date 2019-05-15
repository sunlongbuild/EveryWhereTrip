package com.jiyun.everywheretrip.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserNameActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_finish)
    TextView mTvFinish;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.tv_font_count)
    TextView mTvFontCount;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_name;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_finish, R.id.et_name, R.id.tv_font_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                String name = mEtName.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("name", name);
                setResult(400, intent);
                finish();
                break;
            case R.id.et_name:
                break;
            case R.id.tv_font_count:

                break;
        }
    }

    private int type = 1;

    @Override
    protected void initListener() {
        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvFontCount.setText(mEtName.length() + "/10");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
