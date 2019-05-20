package com.jiyun.everywheretrip.ui.main.fragment;


import android.support.v4.app.Fragment;


import com.baidu.mapapi.map.MapView;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends BaseFragment<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R.id.mapView)
    MapView mMapView;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
}
