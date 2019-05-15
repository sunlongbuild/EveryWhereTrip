package com.jiyun.everywheretrip.base;

/**
 * Created by $sl on 2019/4/30 9:33.
 */
public interface BaseView {
    //显示加载loading的方法
    void showLoading();
    //隐藏加载loading的方法
    void hideLoading();

    void toastShort(String msg);
}
