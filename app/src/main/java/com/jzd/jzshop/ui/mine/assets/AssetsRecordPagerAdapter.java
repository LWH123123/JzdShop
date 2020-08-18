package com.jzd.jzshop.ui.mine.assets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP： tablayou  +viewpager  fragment  公用适配器
 */
public class AssetsRecordPagerAdapter extends FragmentPagerAdapter {
    public final List<Fragment> mFragments = new ArrayList<>();
    public final List<String> mFragmentTitles = new ArrayList<>();


    public AssetsRecordPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        return fragment;
    }

    /**
     * PagerAdapter.POSITION_NONE保证调用notifyDataSetChanged刷新Fragment
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return AssetsRecordPagerAdapter.POSITION_NONE;
    }
}
