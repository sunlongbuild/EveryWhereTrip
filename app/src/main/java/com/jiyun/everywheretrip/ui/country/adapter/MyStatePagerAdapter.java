package com.jiyun.everywheretrip.ui.country.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by $sl on 2019/5/19 17:15.
 */
public class MyStatePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> mList;
    private ArrayList<String> mTitles;

    public MyStatePagerAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> titles) {
        super(fm);
        this.mList = list;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
