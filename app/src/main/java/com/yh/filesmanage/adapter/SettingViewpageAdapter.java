package com.yh.filesmanage.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * @创建者 ly
 * @创建时间 2020/6/9
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public class SettingViewpageAdapter extends FragmentPagerAdapter {


    public SettingViewpageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
