package com.jiyun.everywheretrip.ui.main.fragment;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;

/**
 * Created by $sl on 2019/5/7 20:11.
 */
public class CollectFragment extends BaseFragment<EmptyView,EmptyPresenter> implements EmptyView {
    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
    }
}
