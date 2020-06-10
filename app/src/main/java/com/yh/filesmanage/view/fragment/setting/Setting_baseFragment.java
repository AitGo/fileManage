package com.yh.filesmanage.view.fragment.setting;

import android.view.View;

import com.yh.filesmanage.R;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.widget.ChooseView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @创建者 ly
 * @创建时间 2020/6/10
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public class Setting_baseFragment extends BaseFragment {

    @BindView(R.id.setting_choose_first)
    ChooseView settingChooseFirst;
    @BindView(R.id.setting_choose_fixed)
    ChooseView settingChooseFixed;
    @BindView(R.id.setting_choose_end)
    ChooseView settingChooseEnd;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_base;
    }

    @Override
    protected void initView(View inflate) {
    }

    @Override
    protected void initData() {

    }

}
