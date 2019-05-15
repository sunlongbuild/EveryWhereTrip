package com.jiyun.everywheretrip.ui.main.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by $sl on 2019/5/5 20:12.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mList;

    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
