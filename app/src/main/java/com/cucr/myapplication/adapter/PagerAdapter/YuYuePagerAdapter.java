package com.cucr.myapplication.adapter.PagerAdapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 911 on 2017/5/19.
 *
 * 粉团页面的pagerAdapter
 */

public class YuYuePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public YuYuePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "推荐";

            case 1:
                return "全部";
        }
        return null;
    }
}
