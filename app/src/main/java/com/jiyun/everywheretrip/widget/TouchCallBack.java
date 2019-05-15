package com.jiyun.everywheretrip.widget;

/**
 * Created by $sl on 2019-4-30 10:32:55.
 */
public interface TouchCallBack {
    //交换条目位置
    void onItemMove(int fromPosition, int toPosition);
    //删除条目
    void onItemDelete(int position);
}
