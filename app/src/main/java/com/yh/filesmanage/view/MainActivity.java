package com.yh.filesmanage.view;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aill.androidserialport.SerialPort;
import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.yh.filesmanage.R;
import com.yh.filesmanage.base.BaseEvent;
import com.yh.filesmanage.base.BaseFragmentActivity;
import com.yh.filesmanage.base.Constants;
import com.yh.filesmanage.socket.FastSocketClient;
import com.yh.filesmanage.socket.interfaces.OnSocketClientCallBackList;
import com.yh.filesmanage.utils.HexUtil;
import com.yh.filesmanage.utils.LogUtils;
import com.yh.filesmanage.utils.SPUtils;
import com.yh.filesmanage.utils.StringUtils;
import com.yh.filesmanage.utils.ToastUtils;
import com.yh.filesmanage.view.fragment.StateFragment;
import com.yh.filesmanage.view.fragment.SelectFragment;
import com.yh.filesmanage.view.fragment.SettingFragment;
import com.yh.filesmanage.view.fragment.TaskFragment;
import com.yh.filesmanage.view.fragment.setting.Setting_baseFragment;
import com.yh.filesmanage.widget.FontIconView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseFragmentActivity implements EasyPermissions.PermissionCallbacks,SerialportAnalyze{


    @BindView(R.id.fl_view)
    FrameLayout flView;
    @BindView(R.id.rl_title)
    QMUIRelativeLayout rlTitle;
    @BindView(R.id.tv_main_area)
    TextView tvMainArea;
    @BindView(R.id.tv_main_temperature)
    TextView tvMainTemperature;
    @BindView(R.id.tv_main_humidity)
    TextView tvMainHumidity;
    @BindView(R.id.ll_main_main)
    LinearLayout llMainMain;
    @BindView(R.id.ll_main_select)
    LinearLayout llMainSelect;
    @BindView(R.id.ll_main_task)
    LinearLayout llMainTask;
    @BindView(R.id.ll_main_setting)
    LinearLayout llMainSetting;
    @BindView(R.id.iv_main_state)
    FontIconView ivMainState;
    @BindView(R.id.tv_main_state)
    TextView tvMainState;
    @BindView(R.id.iv_main_select)
    FontIconView ivMainSelect;
    @BindView(R.id.tv_main_select)
    TextView tvMainSelect;
    @BindView(R.id.iv_main_task)
    FontIconView ivMainTask;
    @BindView(R.id.tv_main_task)
    TextView tvMainTask;
    @BindView(R.id.iv_main_setting)
    FontIconView ivMainSetting;
    @BindView(R.id.tv_main_setting)
    TextView tvMainSetting;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_SETTINGS
    };

    private Context mContext;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private StateFragment mStateFragment;
    private SelectFragment mSelectFragment;
    private TaskFragment mTaskFragment;
    private SettingFragment mSettingFragment;
    private Setting_baseFragment mSettingBaseFragment;

    private FontIconView[] tabIvs;
    private TextView[] tabTvs;

    private FastSocketClient fastSocketClient;
    private boolean isPulse;

    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private SerialPort serialPort;
    ExecutorService readES = Executors.newSingleThreadExecutor();
    private int areaNo = 1;
    private int temperatureHeight = 0;
    private int temperatureLow = 0;
    private int temperature = 0;
    private int humidityHeight = 0;
    private int humidityLow = 0;
    private int humidity = 0;
    private int readType = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        rlTitle.setRadiusAndShadow(20, IQMUILayout.HIDE_RADIUS_SIDE_BOTTOM, 0, 0);
        addFragment();
        hideFragment();
        selectButtonBg(0);
        showFragment(mStateFragment);
        areaNo = (int) SPUtils.getParam(this, Constants.SP_NO_AREA, 1);
        tvMainArea.setText(StringUtils.getNumber(areaNo));

        initSerialPort();
    }

    @Override
    protected void initData() {
        getPermission();
        mContext = this;
        tabIvs = new FontIconView[]{ivMainState,ivMainSelect,ivMainTask,ivMainSetting};
        tabTvs = new TextView[]{tvMainState,tvMainSelect,tvMainTask,tvMainSetting};
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        fastSocketClient = FastSocketClient.getInstance();
        fastSocketClient.setOnSocketClientCallBackList(new OnSocketClientCallBackList() {
            @Override
            public void onSocketConnectionSuccess(String msg) {
                LogUtils.e("ConnectionSuccess" + msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort("ConnectionSuccess " + msg);
                    }
                });
            }

            @Override
            public void onSocketConnectionFailed(String msg, Exception e) {
                LogUtils.e("ConnectionFailed" + msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort("ConnectionFailed " + msg);
                    }
                });
            }

            @Override
            public void onSocketDisconnection(String msg, Exception e) {
                LogUtils.e("Disconnection" + msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort("Disconnection " + msg);
                    }
                });
            }

            @Override
            public void onSocketReadResponse(byte[] bytes) {
                //接收命令
                readSocketResponse(bytes);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort("onSocketReadResponse " +  HexUtil.ByteToString(bytes));
                    }
                });
            }

            @Override
            public void onSocketWriteResponse(byte[] bytes) {
                LogUtils.e("Write" + bytes.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort("onSocketWriteResponse " + HexUtil.ByteToString(bytes));
                    }
                });
            }
        });
        fastSocketClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fastSocketClient.close();
        try {
            mInputStream.close();
            mOutputStream.close();
            serialPort.close();
            serialPort = null;
            mInputStream = null;
            mOutputStream = null;
        }catch (Exception e) {

        }
    }

    @OnClick({R.id.ll_main_main, R.id.ll_main_select, R.id.ll_main_task, R.id.ll_main_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_main_main:
                hideFragment();
                selectButtonBg(0);
                showFragment(mStateFragment);
                break;
            case R.id.ll_main_select:
                hideFragment();
                selectButtonBg(1);
                showFragment(mSelectFragment);
                break;
            case R.id.ll_main_task:
                hideFragment();
                selectButtonBg(2);
                showFragment(mTaskFragment);
                break;
            case R.id.ll_main_setting:
                hideFragment();
                selectButtonBg(3);
//                showFragment(mSettingFragment);
                showFragment(mSettingBaseFragment);
                break;
        }
    }

    Thread pulstThread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (isPulse && fastSocketClient.isConnected()){
                byte[] bytes = {(byte) 0x1B,
                        (byte) 0x00,
                        (byte) 0x05,
                        (byte) 0x00,//硬件地址
                        (byte) 0x01,
                        (byte) 0x00,
                        (byte) 0x01,
                        (byte) 0x01};
                fastSocketClient.send(HexUtil.getSocketBytes(bytes));
            }
            SystemClock.sleep(5000);
            pulstThread.run();
        }
    });

    public void initSerialPort() {
        try {
            String serialport_no = (String) SPUtils.getParam(this, Constants.SP_SERIALPORT_NO, Constants.SERIALPORT_NO);
            int serialport_baudrate = (int) SPUtils.getParam(this, Constants.SP_SERIALPORT_BAUDRATE, Constants.SERIALPORT_BAUDRATE);
            serialPort = new SerialPort(new File(serialport_no),
                    serialport_baudrate, 0);
            mInputStream = serialPort.getInputStream();
            mOutputStream = serialPort.getOutputStream();
            getSeriportData();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("打开串口失败");
            ToastUtils.showShort("打开串口失败");
        }
    }

    public void getSeriportData() {
        if(mOutputStream != null) {
            readES.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] send = new byte[]{(byte) 0xAC,
                                (byte) 0x01,
                                (byte) areaNo,//区号
                                (byte) 0x00,
                                (byte) 0x9E};//查询报文
                        mOutputStream.write(send);
                        mOutputStream.flush();
                        Thread.sleep(150);
                        mainloop(mInputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {
            ToastUtils.showShort("串口未打开");
        }
    }

    public void mainloop(InputStream inputStream) throws IOException {
        if (inputStream.available() > 0) {
            byte[] bytes = new byte[inputStream.available()];
            int size = mInputStream.read(bytes);
            LogUtils.e("接收到串口回调w == " + size);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showLong("seriaport read: " + HexUtil.byte2HexStr(bytes));
                }
            });
            if (size > 0) {
                String backString = "";
                for (int i = 0; i < size; i++) {
                    LogUtils.e("接收到串口回调=" + bytes[i]);
                    backString = backString + HexUtil.byteToHexString(bytes[i]);
                }
                //接受到命令后解析
                if(!("AC").equals(HexUtil.byteToHexString(bytes[0])) || !("9E").equals(HexUtil.byteToHexString(bytes[bytes.length - 1]))) {
                    ToastUtils.showShort("命令不完整");
                    return;
                }
                if(bytes.length < 26) {
                    ToastUtils.showShort("命令不完整");
                    return;
                }
                String hightState = HexUtil.byteToHexString(bytes[3]);
                //发送状态码
                BaseEvent.CommonEvent event = BaseEvent.CommonEvent.UPDATE_STATE;
                event.setObject(hightState);
                EventBus.getDefault().post(event);
                String hightAddress = HexUtil.byteToHexString(bytes[4]);
                String lowState = HexUtil.byteToHexString(bytes[5]);
                String lowAddress = HexUtil.byteToHexString(bytes[6]);
                String fixedTemperature = HexUtil.byteToHexString(bytes[7]);
                String fixedHumidity = HexUtil.byteToHexString(bytes[8]);
                String pm25 = HexUtil.byte2HexStrNoSpace(new byte[]{bytes[9],bytes[10]});
                String tvoc = HexUtil.byte2HexStrNoSpace(new byte[]{bytes[11],bytes[12]});
                String co2 = HexUtil.byte2HexStrNoSpace(new byte[]{bytes[13],bytes[14]});
                String hightErrorLayer = HexUtil.byteToHexString(bytes[15]);
                String lowErrorLayer = HexUtil.byteToHexString(bytes[16]);
                String reportCode = HexUtil.byteToHexString(bytes[17]);
                //温度
                temperature = HexUtil.getIntForHexString(HexUtil.byte2HexStrNoSpace(new byte[]{bytes[18],bytes[19]}));
//                temperature = HexUtil.getIntForHexString(backString.substring(37,41));
                LogUtils.e("接受到的命令：temperature " + temperature);
                //湿度
                humidity = HexUtil.getIntForHexString(HexUtil.byte2HexStrNoSpace(new byte[]{bytes[20],bytes[21]}));
//                humidity = HexUtil.getIntForHexString(backString.substring(41,45));
                LogUtils.e("接受到的命令：humidity " + humidity);
                String checkCab = HexUtil.byteToHexString(bytes[22]);
                //甲醛
                String forma = HexUtil.byte2HexStrNoSpace(new byte[]{bytes[23],bytes[24]});

                String layer = HexUtil.byteToHexString(bytes[25]);
                int layerNo = HexUtil.getIntForHexString(layer);
                SPUtils.setParam(mContext,Constants.SP_NO_LAYER,layerNo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMainTemperature.setText(temperature/10 + "");
                        tvMainHumidity.setText(humidity/10 + "");
                    }
                });
            }
        }
    }

    private void readSocketResponse(byte[] bytes) {
        if(bytes.length >= 9) {
            switch (HexUtil.byteToHexString(bytes[6])) {
                case "01":
                    //获取设备信息
                    if("80".equals(HexUtil.byteToHexString(bytes[5]))) {

                    }
                    break;
                case "03":
                    //开始检卡
                    if("80".equals(HexUtil.byteToHexString(bytes[5]))) {
                        if("00".equals(HexUtil.byteToHexString(bytes[8]))) {
                            //检卡成功，读取单层UID
                            int cabinetNo = (int) SPUtils.getParam(mContext,Constants.SP_NO_CABINET,1);
                            byte[] resdUid = {
                                    (byte) 0x00, (byte) 0x06,
                                    (byte) 0x00, (byte) 0x01,//硬件地址
                                    (byte) 0x00, (byte) 0x05,//读取单层命令
                                    (byte) 0x01,
                                    (byte) cabinetNo//ID，与柜号一致
                                    };
                            sendSocketData(HexUtil.getSocketBytes(resdUid));
                        }
                    }
                    break;
                case "05":
                    //读取单层UID
                    if("80".equals(HexUtil.byteToHexString(bytes[5]))) {
                        int index = 8;
                        while(index < bytes.length) {
                            if(!"83".equals(HexUtil.byteToHexString(bytes[index + 1]))) {
                                int id = HexUtil.getIntForHexString(HexUtil.byteToHexString(bytes[index]));
                                byte[] destBytes = new byte[8];
                                System.arraycopy(bytes, index + 1, destBytes, 0, 8);
                                String uid = HexUtil.byte2HexStrNoSpace(destBytes);
                                index += 8;
                                //保存id和uid
                                LogUtils.e("index:" + index + "   id:" + id + "   uid:" + uid);
                            }else {
                                index += 1;
                            }
                        }
                        //判断上架或盘点
                        if(readType == Constants.VALUE_CHECK) {

                        }else if(readType == Constants.VALUE_UP) {

                        }
                    }
                    break;
                case "07":
                    //指示灯闪烁
                    if("80".equals(HexUtil.byteToHexString(bytes[5]))) {
                        String s = HexUtil.byteToHexString(bytes[8]);
                        if(!"00".equals(s)) {
                            //执行失败
                            String id = s;
                            String errorCode = HexUtil.byteToHexString(bytes[9]);
                        }
                    }else {
                        if(bytes.length >= 10) {
                            String id = HexUtil.byteToHexString(bytes[8]);
                            String errorCode = HexUtil.byteToHexString(bytes[9]);
                        }
                    }
                    break;
            }
        }
        String backString = "";
        for (int i = 0; i < bytes.length; i++) {
            backString = backString + HexUtil.byteToHexString(bytes[i]);
        }
        LogUtils.e("Read string " + backString);
    }

    public void showFragment(Fragment fragment) {
        transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    public void addFragment() {
        transaction = fragmentManager.beginTransaction();
        if (mStateFragment == null) {
            mStateFragment = new StateFragment();
            transaction.add(R.id.fl_view, mStateFragment);
        }
        if(mSelectFragment == null) {
            mSelectFragment = new SelectFragment();
            transaction.add(R.id.fl_view, mSelectFragment);
        }
        if(mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            transaction.add(R.id.fl_view, mTaskFragment);
        }
//        if(mSettingFragment == null) {
//            mSettingFragment = new SettingFragment();
//            transaction.add(R.id.fl_view, mSettingFragment);
//        }
        if(mSettingBaseFragment == null) {
            mSettingBaseFragment = new Setting_baseFragment();
            transaction.add(R.id.fl_view, mSettingBaseFragment);
        }

        transaction.commit();
    }

    public void hideFragment() {
        transaction = fragmentManager.beginTransaction();
        if (mStateFragment != null) {
            transaction.hide(mStateFragment);
        }
        if (mSelectFragment != null) {
            transaction.hide(mSelectFragment);
        }
        if (mTaskFragment != null) {
            transaction.hide(mTaskFragment);
        }
//        if (mSettingFragment != null) {
//            transaction.hide(mSettingFragment);
//        }
        if (mSettingBaseFragment != null) {
            transaction.hide(mSettingBaseFragment);
        }
        transaction.commit();
    }

    private void selectButtonBg(int index) {
        for(int i = 0; i < tabIvs.length; i++) {
            if(index == i) {
                tabIvs[i].setTextColor(getResources().getColor(R.color.blue_bg_dark));
            }else {
                tabIvs[i].setTextColor(getResources().getColor(R.color.white));
            }
        }
        for(int i = 0; i < tabTvs.length; i++) {
            if(index == i) {
                tabTvs[i].setTextColor(getResources().getColor(R.color.blue_bg_dark));
            }else {
                tabTvs[i].setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 检查权限
     *
     * @param
     * @since 2.5.0
     */
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, needPermissions)) {
            //已经打开权限
//            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的存储、定位权限", 1, needPermissions);
        }

    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    public void sendSeriportData(byte[] send) {
        if(mOutputStream != null) {
            readES.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        mOutputStream.write(send);
                        mOutputStream.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {
            ToastUtils.showShort("串口未打开");
        }
    }

    @Override
    public void sendSocketData(byte[] send) {
        fastSocketClient.send(send);
    }

    @Override
    public void sendSocketData(byte[] send, int type) {
        readType = type;
        fastSocketClient.send(send);
    }

}
