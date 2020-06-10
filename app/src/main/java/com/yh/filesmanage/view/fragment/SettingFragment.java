package com.yh.filesmanage.view.fragment;

import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment2;
import com.yh.filesmanage.R;
import com.yh.filesmanage.adapter.SettingViewpageAdapter;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.view.MainActivity;
import com.yh.filesmanage.view.fragment.setting.Setting_airFragment;
import com.yh.filesmanage.view.fragment.setting.Setting_animationFragment;
import com.yh.filesmanage.view.fragment.setting.Setting_autoFragment;
import com.yh.filesmanage.view.fragment.setting.Setting_baseFragment;
import com.yh.filesmanage.view.fragment.setting.Setting_dataFragment;
import com.yh.filesmanage.view.fragment.setting.Setting_otherFragment;
import com.yh.filesmanage.view.fragment.setting.Setting_temperatureFragment;
import com.yh.filesmanage.view.fragment.setting.Setting_testFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;

public class SettingFragment extends BaseFragment {


    @BindView(R.id.setting_tab)
    QMUITabSegment2 mTabSegment;
    @BindView(R.id.setting_page)
    ViewPager2 mContentViewPager;

    private String[] titles = new String[]{"基本设置", "数据设置", "动画设置", "通风设置", "温湿度设置", "自动控制设置", "其他设置", "测试"};
    private SettingViewpageAdapter mPageAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;

    }

    @Override
    protected void initView(View inflate) {
        initTab();

    }

    private void initTab() {
        fragments.add(new Setting_baseFragment());
        fragments.add(new Setting_dataFragment());
        fragments.add(new Setting_animationFragment());
        fragments.add(new Setting_airFragment());
        fragments.add(new Setting_temperatureFragment());
        fragments.add(new Setting_autoFragment());
        fragments.add(new Setting_otherFragment());
        fragments.add(new Setting_testFragment());

        mPageAdapter = new SettingViewpageAdapter(getActivity(),fragments);
        mContentViewPager.setAdapter(mPageAdapter);
        mContentViewPager.setCurrentItem(0, false);

        QMUITabBuilder tabBuilder = mTabSegment.tabBuilder();
        for (String s : titles) {
            mTabSegment.addTab(tabBuilder.setText(s).build(getContext()));
        }
        int space = QMUIDisplayHelper.dp2px(getContext(), 14);
        mTabSegment.setIndicator(new QMUITabIndicator(
                QMUIDisplayHelper.dp2px(getContext(), 2), false, true));
        mTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mContentViewPager);
        mTabSegment.setPadding(space, 0, space, 0);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
//                Toast.makeText(getContext(), "select index " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(int index) {
//                Toast.makeText(getContext(), "unSelect index " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(int index) {
//                Toast.makeText(getContext(), "reSelect index " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleTap(int index) {
//                Toast.makeText(getContext(), "double tap index " + index, Toast.LENGTH_SHORT).show();
            }
        });

        mTabSegment.notifyDataChanged();
    }

    @Override
    protected void initData() {

    }
}
