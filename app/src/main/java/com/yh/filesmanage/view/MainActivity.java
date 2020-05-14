package com.yh.filesmanage.view;

import android.os.Bundle;

import com.yh.filesmanage.R;
import com.yh.filesmanage.base.BaseFragmentActivity;
import com.yh.filesmanage.utils.ToastUtils;
import com.yh.filesmanage.widget.HorizontalSelectedView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.hsv)
    HorizontalSelectedView hsv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ArrayList<String> strings = new ArrayList<>();
        for(int i = 0;i < 10;i++) {
            strings.add(i + 1 + "");
        }
        hsv.setData(strings);
        hsv.setOnSelectListener(new HorizontalSelectedView.OnSelectListener() {
            @Override
            public void onSelect(String selectStr, int index) {
                ToastUtils.showLong(selectStr);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
