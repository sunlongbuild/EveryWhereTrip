package com.jiyun.everywheretrip.ui.JavaScript;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;

import java.util.ArrayList;

public class SpecialTopicActivity extends BaseActivity<EmptyView,EmptyPresenter> implements EmptyView {

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_special_topic;
    }
}
