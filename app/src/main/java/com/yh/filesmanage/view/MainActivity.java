package com.yh.filesmanage.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.yh.filesmanage.R;
import com.yh.filesmanage.base.BaseFragmentActivity;
import com.yh.filesmanage.view.fragment.MainFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity {


    @BindView(R.id.fl_view)
    FrameLayout flView;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private MainFragment mMainFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        addFragment();
        hideFragment();
        showFragment(mMainFragment);
    }

    @Override
    protected void initData() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void showFragment(Fragment fragment) {
        transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    public void addFragment() {
        transaction = fragmentManager.beginTransaction();
        if (mMainFragment == null) {
            mMainFragment = new MainFragment();
            transaction.add(R.id.fl_view, mMainFragment);
        }

        transaction.commit();
    }

    public void hideFragment() {
        transaction = fragmentManager.beginTransaction();
        if (mMainFragment != null) {
            transaction.hide(mMainFragment);
        }
        transaction.commit();
    }
}
