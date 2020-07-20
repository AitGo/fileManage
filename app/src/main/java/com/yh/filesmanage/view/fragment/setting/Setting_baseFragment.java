package com.yh.filesmanage.view.fragment.setting;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.aill.androidserialport.SerialPortFinder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.yh.filesmanage.R;
import com.yh.filesmanage.adapter.ChooseViewAdapter;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.base.Constants;
import com.yh.filesmanage.utils.SPUtils;
import com.yh.filesmanage.utils.StringUtils;
import com.yh.filesmanage.utils.ToastUtils;
import com.yh.filesmanage.view.MainActivity;
import com.yh.filesmanage.widget.ChooseView;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.et_setting_min)
    EditText etSettingMin;
    @BindView(R.id.et_setting_fixed)
    EditText etSettingFixed;
    @BindView(R.id.et_setting_max)
    EditText etSettingMax;
    @BindView(R.id.et_setting_area)
    EditText etSettingArea;
    @BindView(R.id.setting_choose_seriaport)
    ChooseView settingChooseSeriaport;
    @BindView(R.id.setting_choose_bt)
    ChooseView settingChooseBt;
    @BindView(R.id.et_setting_house_no)
    EditText etSettingHouseNo;
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
    @BindView(R.id.et_setting_class_size)
    EditText etSettingClassSize;
    @BindView(R.id.et_setting_layer_size)
    EditText etSettingLayerSize;
    @BindView(R.id.et_setting_box_size)
    EditText etSettingBoxSize;
    @BindView(R.id.btn_setting_update)
    Button btnSettingUpdate;
    @BindView(R.id.btn_setting_use)
    Button btnSettingUse;
    @BindView(R.id.btn_setting_seriaport)
    Button btnSettingSeriaport;
    @BindView(R.id.et_setting_speed)
    EditText etSettingSpeed;

    private MainActivity activity;
    private QMUIPopup popup;
    private List<String> devices = new ArrayList<>();
    private List<String> bts = new ArrayList<>();
    private String[] btStrings = new String[]{"9600", "19200", "38400", "57600", "115200"};
    private ChooseViewAdapter seriaportAdapter, btAdapter;
    private AdapterView.OnItemClickListener seriaportOnItemClickListener, btOnItemClickListener;

    private int cabinet_min;
    private int cabinet_max;
    private int cabinet_fixed;
    private int class_size;
    private int layer_size;
    private int box_size;
    private int area_no;
    private int house_no;
    private String serialport;
    private int seriaport_bt;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_base;
    }

    @Override
    protected void initView(View inflate) {
        serialport = (String) SPUtils.getParam(getContext(), Constants.SP_SERIALPORT_NO, Constants.SERIALPORT_NO);
        settingChooseSeriaport.setTextValue(serialport.replace("/dev/", ""));
        settingChooseBt.setTextValue((int) SPUtils.getParam(getContext(), Constants.SP_SERIALPORT_BAUDRATE, Constants.SERIALPORT_BAUDRATE) + "");

        seriaportAdapter = new ChooseViewAdapter(getContext(), devices);
        seriaportOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                settingChooseSeriaport.setTextValue(devices.get(position));
                serialport = "/dev/" + devices.get(position);
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };

        btAdapter = new ChooseViewAdapter(getContext(), bts);
        btOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                settingChooseBt.setTextValue(bts.get(position));
                seriaport_bt = Integer.valueOf(bts.get(position));
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };

        int areaNo = (int) SPUtils.getParam(getContext(), Constants.SP_NO_AREA, 1);
        etSettingArea.setText(areaNo + "");
        int speed = (int) SPUtils.getParam(getContext(), Constants.SP_SPEED, 1);
        etSettingSpeed.setText(speed + "");
    }

    @Override
    protected void initData() {
        activity = (MainActivity) getActivity();
        SerialPortFinder finder = new SerialPortFinder();
        String[] allDevices = new String[]{};
        try {
            allDevices = finder.getAllDevices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String device : allDevices) {
            if (device.contains(" (serial)")) {
                device = device.replace(" (serial)", "");
            }
            devices.add(device);
        }
        for (String bt : btStrings) {
            bts.add(bt);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            etSettingArea.setText((int) SPUtils.getParam(getContext(), Constants.SP_NO_AREA, 1) + "");
            etSettingSpeed.setText((int) SPUtils.getParam(getContext(), Constants.SP_SPEED, 1) + "");
            etSettingMin.setText((int) SPUtils.getParam(getContext(), Constants.SP_NO_CABINET_MIN, 1) + "");
            etSettingMax.setText((int) SPUtils.getParam(getContext(), Constants.SP_NO_CABINET_MAX, 1) + "");
            etSettingFixed.setText((int) SPUtils.getParam(getContext(), Constants.SP_NO_CABINET_FIXED, 1) + "");
            etSettingClassSize.setText((int) SPUtils.getParam(getContext(), Constants.SP_SIZE_CLASS, 1) + "");
            etSettingLayerSize.setText((int) SPUtils.getParam(getContext(), Constants.SP_SIZE_LAYER, 1) + "");
            etSettingBoxSize.setText((int) SPUtils.getParam(getContext(), Constants.SP_SIZE_BOX, 1) + "");
            etSettingHouseNo.setText(SPUtils.getParam(getContext(), Constants.SP_NO_HOUSE, 1) + "");
        }
    }

    @OnClick({R.id.setting_choose_seriaport, R.id.setting_choose_bt, R.id.btn_setting_update, R.id.btn_setting_use, R.id.btn_setting_seriaport})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_choose_seriaport:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        seriaportAdapter, seriaportOnItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
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
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(settingChooseBt);
                break;
            case R.id.btn_setting_seriaport:
                SPUtils.setParam(getContext(), Constants.SP_SERIALPORT_NO, serialport);
                SPUtils.setParam(getContext(), Constants.SP_SERIALPORT_BAUDRATE, seriaport_bt);
                activity.closeSerialPort();
                activity.initSerialPort();
                break;
            case R.id.btn_setting_update:

                break;
            case R.id.btn_setting_use:
                String cabinetMinString = etSettingMin.getText().toString().trim();
                String cabinetMaxString = etSettingMax.getText().toString().trim();
                String cabinetFixedString = etSettingFixed.getText().toString().trim();
                String classSizeString = etSettingClassSize.getText().toString().trim();
                String layerSizeString = etSettingLayerSize.getText().toString().trim();
                String boxSizeString = etSettingBoxSize.getText().toString().trim();
                String areaNoString = etSettingArea.getText().toString().trim();
                String speedString = etSettingSpeed.getText().toString().trim();
                String houseNoString = etSettingHouseNo.getText().toString().trim();

                if (!StringUtils.checkString(cabinetMinString)) {
                    ToastUtils.showShort("请填写首柜");
                    return;
                }
                if (!StringUtils.checkString(cabinetMaxString)) {
                    ToastUtils.showShort("请填写末柜");
                    return;
                }
                if (!StringUtils.checkString(cabinetFixedString)) {
                    ToastUtils.showShort("请填写固定柜");
                    return;
                }
                if (!StringUtils.checkString(classSizeString)) {
                    ToastUtils.showShort("请填写节数");
                    return;
                }
                if (!StringUtils.checkString(layerSizeString)) {
                    ToastUtils.showShort("请填写层数");
                    return;
                }
                if (!StringUtils.checkString(boxSizeString)) {
                    ToastUtils.showShort("请填写盒数");
                    return;
                }
                if (!StringUtils.checkString(areaNoString)) {
                    ToastUtils.showShort("请填写区号");
                    return;
                }
                if (!StringUtils.checkString(houseNoString)) {
                    ToastUtils.showShort("请填写库房号");
                    return;
                }
                cabinet_min = Integer.valueOf(cabinetMinString);
                cabinet_max = Integer.valueOf(cabinetMaxString);
                if (cabinet_max < cabinet_min) {
                    ToastUtils.showShort("首柜不能大于末柜");
                    return;
                }
                cabinet_fixed = Integer.valueOf(cabinetFixedString);
                if (cabinet_fixed < cabinet_min) {
                    ToastUtils.showShort("固定柜不能小于首柜");
                    return;
                }
                if (cabinet_fixed > cabinet_max) {
                    ToastUtils.showShort("固定柜不能大于末柜");
                    return;
                }
                class_size = Integer.valueOf(classSizeString);
                layer_size = Integer.valueOf(layerSizeString);
                box_size = Integer.valueOf(boxSizeString);
                area_no = Integer.valueOf(areaNoString);
                int speed = Integer.valueOf(speedString);
                house_no = Integer.valueOf(houseNoString);
                //设置命令
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) area_no,//区号
                        (byte) 0x24,
                        (byte) area_no,//设区号
                        (byte) cabinet_fixed,//设固定列
                        (byte) cabinet_max,//设高区最大列
                        (byte) (cabinet_min - 1),//设屏蔽列
                        (byte) class_size,//设节数
                        (byte) layer_size,//设层数
                        (byte) box_size,//设盒数
                        (byte) speed,//设速度
                        (byte) 0x9E});
                //设置sp
                SPUtils.setParam(getContext(), Constants.SP_NO_AREA, area_no);
                SPUtils.setParam(getContext(), Constants.SP_SIZE_LAYER, layer_size);
                SPUtils.setParam(getContext(), Constants.SP_SIZE_CLASS, class_size);
                SPUtils.setParam(getContext(), Constants.SP_SIZE_BOX, box_size);
                SPUtils.setParam(getContext(), Constants.SP_SIZE_CABINET, cabinet_max - cabinet_min + 1);
                SPUtils.setParam(getContext(), Constants.SP_NO_HOUSE, house_no);
                SPUtils.setParam(getContext(), Constants.SP_SPEED, speed);
                break;
        }
    }

    public void setEditState(boolean isEdit) {
        etSettingHttpPort.setEnabled(isEdit);
        etSettingHttpConnectNo.setEnabled(isEdit);
        etSettingTcpPort.setEnabled(isEdit);
        etSettingTcpConnectNo.setEnabled(isEdit);
        etSettingUsername.setEnabled(isEdit);
        etSettingPassword.setEnabled(isEdit);
    }

}
