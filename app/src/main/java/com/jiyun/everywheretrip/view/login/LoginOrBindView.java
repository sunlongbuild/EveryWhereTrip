package com.jiyun.everywheretrip.view.login;

import android.app.Activity;

import com.jiyun.everywheretrip.base.BaseView;
import com.jiyun.everywheretrip.bean.login.LoginInfo;

/**
 * Created by $sl on 2019/5/4 16:28.
 */
public interface LoginOrBindView extends BaseView {
    String getPhone();

    Activity getAct();
    void go2MainActivity();

    void setData(String code);
}
