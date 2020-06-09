package com.yh.filesmanage.view.fragment;

import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment2;
import com.yh.filesmanage.R;
import com.yh.filesmanage.base.BaseFragment;

import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;

public class SettingFragment extends BaseFragment {


    @BindView(R.id.setting_tab)
    QMUITabSegment2 mTabSegment;
    @BindView(R.id.setting_page)
    ViewPager2 mContentViewPager;

    private String[] titles = new String[]{"基本设置", "数据设置", "动画设置", "通风设置", "温湿度设置", "自动控制设置", "其他设置", "测试"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;

    }

    @Override
    protected void initView(View inflate) {
        initTab();

    }

    private void initTab() {
        mContentViewPager.setAdapter(mPageAdapter);
        mContentViewPager.setCurrentItem(mDestPage.getPosition(), false);

        QMUITabBuilder tabBuilder = mTabSegment.tabBuilder();
        for (String s : titles) {
            mTabSegment.addTab(tabBuilder.setText(s).build(getContext()));
        }
        int space = QMUIDisplayHelper.dp2px(getContext(), 16);
        mTabSegment.setIndicator(new QMUITabIndicator(
                QMUIDisplayHelper.dp2px(getContext(), 2), false, true));
        mTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mContentViewPager);
        mTabSegment.setPadding(space, 0, space, 0);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                Toast.makeText(getContext(), "select index " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(int index) {
                Toast.makeText(getContext(), "unSelect index " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(int index) {
                Toast.makeText(getContext(), "reSelect index " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleTap(int index) {
                Toast.makeText(getContext(), "double tap index " + index, Toast.LENGTH_SHORT).show();
            }
        });

        mTabSegment.notifyDataChanged();
    }

    @Override
    protected void initData() {

    }
}
