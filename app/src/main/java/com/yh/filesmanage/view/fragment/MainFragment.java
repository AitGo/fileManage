package com.yh.filesmanage.view.fragment;

import android.view.View;

import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.yh.filesmanage.R;
import com.yh.filesmanage.base.BaseFragment;

import butterknife.BindView;

public class MainFragment extends BaseFragment {


    @BindView(R.id.ll_state_save_right)
    QMUILinearLayout llStateSaveRight;
    @BindView(R.id.ll_state_save_left)
    QMUILinearLayout llStateSaveLeft;
    @BindView(R.id.ll_state_error_right)
    QMUILinearLayout llStateErrorRight;
    @BindView(R.id.ll_state_error_left)
    QMUILinearLayout llStateErrorLeft;
    @BindView(R.id.ll_state_time_right)
    QMUILinearLayout llStateTimeRight;
    @BindView(R.id.ll_state_time_left)
    QMUILinearLayout llStateTimeLeft;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View inflate) {
        llStateSaveLeft.setRadiusAndShadow(40, 0, 10);
        llStateSaveRight.setRadiusAndShadow(40, IQMUILayout.HIDE_RADIUS_SIDE_LEFT, 0, 10);
        llStateErrorLeft.setRadiusAndShadow(40, 0, 10);
        llStateErrorRight.setRadiusAndShadow(40, IQMUILayout.HIDE_RADIUS_SIDE_LEFT, 0, 10);
        llStateTimeLeft.setRadiusAndShadow(40, 0, 10);
        llStateTimeRight.setRadiusAndShadow(40, IQMUILayout.HIDE_RADIUS_SIDE_LEFT, 0, 10);
    }

    @Override
    protected void initData() {

    }

}
