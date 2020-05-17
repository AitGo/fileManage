package com.yh.filesmanage.view.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yh.filesmanage.R;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.utils.StringUtils;
import com.yh.filesmanage.widget.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {
    @BindView(R.id.tv_main_time)
    TextView tvMainTime;
    @BindView(R.id.tv_main_week)
    TextView tvMainWeek;
    @BindView(R.id.tv_main_date)
    TextView tvMainDate;
    @BindView(R.id.wv_main)
    WheelView wvMain;
    @BindView(R.id.btn_main_confirm)
    Button btnMainConfirm;
    @BindView(R.id.ll_main_setting)
    LinearLayout llMainSetting;
    @BindView(R.id.tv_main_server_address)
    TextView tvMainServerAddress;
    @BindView(R.id.tv_main_files_setting)
    TextView tvMainFilesSetting;
    @BindView(R.id.tv_main_serial_setting)
    TextView tvMainSerialSetting;
    @BindView(R.id.tv_main_order_setting)
    TextView tvMainOrderSetting;
    @BindView(R.id.tv_main_db_address)
    TextView tvMainDbAddress;
    @BindView(R.id.tv_main_param_setting)
    TextView tvMainParamSetting;
    @BindView(R.id.ll_main_open)
    LinearLayout llMainOpen;
    @BindView(R.id.ll_main_check)
    LinearLayout llMainCheck;
    @BindView(R.id.ll_main_select)
    LinearLayout llMainSelect;
    @BindView(R.id.ll_main_statis)
    LinearLayout llMainStatis;
    @BindView(R.id.ll_main_info)
    LinearLayout llMainInfo;
    @BindView(R.id.ll_main_manage)
    LinearLayout llMainManage;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            assert action != null;
            if (action.equals(Intent.ACTION_TIME_TICK)) {
                Date date = new Date();
                tvMainTime.setText(StringUtils.Date2String(date,"HH:mm"));
                tvMainDate.setText(StringUtils.Date2String(date,"yyyy/MM/dd"));
                tvMainWeek.setText(StringUtils.dateToWeek(date));
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View inflate) {

        initTime();
        initWheelView();
    }

    private void initTime() {
        Date date = new Date();
        tvMainTime.setText(StringUtils.Date2String(date,"HH:mm"));
        tvMainDate.setText(StringUtils.Date2String(date,"yyyy/MM/dd"));
        tvMainWeek.setText(StringUtils.dateToWeek(date));

        // 更新时间的广播
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        getActivity().registerReceiver(receiver,filter);
    }

    private void initWheelView() {
        ArrayList localArrayList = new ArrayList();
        int i = 1;
        while (i <= 30) {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(String.valueOf(i));
            localStringBuilder.append("层");
            localArrayList.add(localStringBuilder.toString());
            i += 1;
        }
        wvMain.setItems(localArrayList);
        wvMain.selectIndex(6);

        wvMain.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(WheelView wheelView, int position) {

            }

            @Override
            public void onWheelItemSelected(WheelView wheelView, int position) {
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_main_confirm, R.id.ll_main_setting, R.id.tv_main_server_address, R.id.tv_main_files_setting, R.id.tv_main_serial_setting, R.id.tv_main_order_setting, R.id.tv_main_db_address, R.id.tv_main_param_setting, R.id.ll_main_open, R.id.ll_main_check, R.id.ll_main_select, R.id.ll_main_statis, R.id.ll_main_info, R.id.ll_main_manage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_main_confirm:
                break;
            case R.id.ll_main_setting:
                break;
            case R.id.tv_main_server_address:
                break;
            case R.id.tv_main_files_setting:
                break;
            case R.id.tv_main_serial_setting:
                break;
            case R.id.tv_main_order_setting:
                break;
            case R.id.tv_main_db_address:
                break;
            case R.id.tv_main_param_setting:
                break;
            case R.id.ll_main_open:
                break;
            case R.id.ll_main_check:
                break;
            case R.id.ll_main_select:
                break;
            case R.id.ll_main_statis:
                break;
            case R.id.ll_main_info:
                break;
            case R.id.ll_main_manage:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销广播
        getActivity().unregisterReceiver(receiver);
    }
}
