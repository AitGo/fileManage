package com.yh.filesmanage.view.fragment.setting;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aill.androidserialport.SerialPortFinder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.yh.filesmanage.R;
import com.yh.filesmanage.adapter.ChooseViewAdapter;
import com.yh.filesmanage.adapter.LayerChooseAdapter;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.base.Constants;
import com.yh.filesmanage.utils.SPUtils;
import com.yh.filesmanage.widget.ChooseView;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

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

    @BindView(R.id.et_setting_first)
    EditText etSettingFirst;
    @BindView(R.id.et_setting_fixed)
    EditText etSettingFixed;
    @BindView(R.id.et_setting_end)
    EditText etSettingEnd;
    @BindView(R.id.et_setting_area)
    EditText etSettingArea;
    @BindView(R.id.setting_choose_seriaport)
    ChooseView settingChooseSeriaport;
    @BindView(R.id.btn_setting_set)
    Button btnSettingSet;
    @BindView(R.id.setting_choose_bt)
    ChooseView settingChooseBt;
    @BindView(R.id.et_setting_room_no)
    EditText etSettingRoomNo;
    @BindView(R.id.et_setting_tcp_port)
    EditText etSettingTcpPort;
    @BindView(R.id.et_setting_tcp_connect_no)
    EditText etSettingTcpConnectNo;
    @BindView(R.id.et_setting_username)
    EditText etSettingUsername;
    @BindView(R.id.et_setting_http_port)
    EditText etSettingHttpPort;
    @BindView(R.id.et_setting_http_connect_no)
    EditText etSettingHttpConnectNo;
    @BindView(R.id.et_setting_password)
    EditText etSettingPassword;

    private QMUIPopup popup;
    private List<String> devices = new ArrayList<>();
    private List<String> bts = new ArrayList<>();
    private String[] btStrings = new String[]{"9600","19200","38400","57600","115200"};
    private ChooseViewAdapter seriaportAdapter,btAdapter;
    private AdapterView.OnItemClickListener seriaportOnItemClickListener,btOnItemClickListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_base;
    }

    @Override
    protected void initView(View inflate) {
        String serialport = (String) SPUtils.getParam(getContext(), Constants.SP_SERIALPORT_NO, Constants.SERIALPORT_NO);
        serialport = serialport.replace("/dev/","");
        settingChooseSeriaport.setTextValue(serialport);
        settingChooseBt.setTextValue((int) SPUtils.getParam(getContext(), Constants.SP_SERIALPORT_BAUDRATE, Constants.SERIALPORT_BAUDRATE) + "");

        seriaportAdapter = new ChooseViewAdapter(getContext(), devices);
        seriaportOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), devices.get(position), Toast.LENGTH_SHORT).show();
                settingChooseSeriaport.setTextValue(devices.get(position));
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };

        btAdapter = new ChooseViewAdapter(getContext(), bts);
        btOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), bts.get(position), Toast.LENGTH_SHORT).show();
                settingChooseBt.setTextValue(bts.get(position));
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };

        int areaNo = (int) SPUtils.getParam(getContext(), Constants.SP_NO_AREA, 1);
        etSettingArea.setText(areaNo + "");
    }

    @Override
    protected void initData() {
        SerialPortFinder finder = new SerialPortFinder();
        String[] allDevices = finder.getAllDevices();
        for(String device : allDevices) {
            if(device.contains(" (serial)")) {
                device = device.replace(" (serial)","");
            }
            devices.add(device);
        }
        for(String bt : btStrings) {
            bts.add(bt);
        }
    }

    @OnClick({R.id.setting_choose_seriaport, R.id.setting_choose_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_choose_seriaport:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        seriaportAdapter, seriaportOnItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(settingChooseSeriaport);
                break;
            case R.id.setting_choose_bt:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        btAdapter, btOnItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(settingChooseBt);
                break;
        }
    }
}
