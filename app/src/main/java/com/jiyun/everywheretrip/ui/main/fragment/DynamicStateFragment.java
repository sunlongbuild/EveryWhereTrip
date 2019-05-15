package com.jiyun.everywheretrip.ui.main.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicStateFragment extends BaseFragment<EmptyView, EmptyPresenter> implements EmptyView {

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic_state;
    }

}
