package com.jiyun.everywheretrip.net;

/**
 * Created by $sl on 2019/4/30/ 10:20.
 */

public interface ResultCallBack<T> {
    void onSuccess(T bean);
    void onFail(String msg);
}
