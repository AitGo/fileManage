package com.yh.filesmanage.adapter;

import java.util.ArrayList;
import java.util.List;

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
public class SettingViewpageAdapter extends FragmentStateAdapter {

    private List<Fragment> list;

    public SettingViewpageAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> list) {
        super(fragmentActivity);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    public SettingViewpageAdapter(@NonNull FragmentManager fm, List<Fragment> list) {
//        super(fm);
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
}
