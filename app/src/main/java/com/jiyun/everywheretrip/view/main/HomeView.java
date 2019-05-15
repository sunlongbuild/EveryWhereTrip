package com.jiyun.everywheretrip.view.main;

import com.jiyun.everywheretrip.base.BaseView;
import com.jiyun.everywheretrip.bean.main.HomeBean;

import java.util.HashMap;

/**
 * Created by $sl on 2019/5/5 10:04.
 */
public interface HomeView extends BaseView {
    void onSuccess(HomeBean bean);
    void onFailed(String string);
}
