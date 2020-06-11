package com.yh.filesmanage.view;

import android.Manifest;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aill.androidserialport.SerialPort;
import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.yh.filesmanage.R;
import com.yh.filesmanage.base.BaseFragmentActivity;
import com.yh.filesmanage.base.Constants;
import com.yh.filesmanage.diagnose.RFIDEntity;
import com.yh.filesmanage.socket.FastSocketClient;
import com.yh.filesmanage.socket.interfaces.OnSocketClientCallBackList;
import com.yh.filesmanage.utils.CRC16;
import com.yh.filesmanage.utils.HexUtil;
import com.yh.filesmanage.utils.LogUtils;
import com.yh.filesmanage.utils.SPUtils;
import com.yh.filesmanage.utils.ToastUtils;
import com.yh.filesmanage.view.fragment.StateFragment;
import com.yh.filesmanage.view.fragment.SelectFragment;
import com.yh.filesmanage.view.fragment.SettingFragment;
import com.yh.filesmanage.view.fragment.TaskFragment;
import com.yh.filesmanage.widget.FontIconView;

import java.io.File;
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

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private StateFragment mStateFragment;
    private SelectFragment mSelectFragment;
    private TaskFragment mTaskFragment;
    private SettingFragment mSettingFragment;

    private FontIconView[] tabIvs;
    private TextView[] tabTvs;

    private FastSocketClient fastSocketClient;
    private boolean isPulse;

    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private SerialPort serialPort;
    ExecutorService readES = Executors.newSingleThreadExecutor();

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
        initSerialPort();
    }

    @Override
    protected void initData() {
        getPermission();
        tabIvs = new FontIconView[]{ivMainState,ivMainSelect,ivMainTask,ivMainSetting};
        tabTvs = new TextView[]{tvMainState,tvMainSelect,tvMainTask,tvMainSetting};
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        fastSocketClient = FastSocketClient.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
                showFragment(mSettingFragment);

//                TempTool tempTool = new TempTool(this);
//                tempTool.openGetSSSerial();
//                RFIDEntity entity = new RFIDEntity();
//                entity.setHead(Integer.valueOf(0x11).byteValue());
//                entity.setAddress(new byte[]{Integer.valueOf(0x11).byteValue(),Integer.valueOf(0x32).byteValue()});
//                entity.setControllerCode(new byte[]{(byte)0x00,(byte)0x01});
//                entity.setOrderNo(new byte[]{(byte)0x01});
//                entity.setData(new byte[]{});
//                entity.setLength();
//                entity.setCrcCode();
//                int i = CRC16.CRC16_CCITT(new byte[]{(byte) 0x1B, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01});
//
//                fastSocketClient.send(new byte[]{(byte)0x1B,(byte)0x00,(byte)0x05,(byte)0x00,(byte)0x01,(byte)0x00,(byte)0x01,(byte)0x4a,(byte)0xae});
//                fastSocketClient.setOnSocketClientCallBackList(new OnSocketClientCallBackList() {
//                    @Override
//                    public void onSocketConnectionSuccess(String msg) {
//
//                    }
//
//                    @Override
//                    public void onSocketConnectionFailed(String msg, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onSocketDisconnection(String msg, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onSocketReadResponse(byte[] bytes) {
//                        Log.e("123",bytes.toString());
//                    }
//
//                    @Override
//                    public void onSocketWriteResponse(byte[] bytes) {
//                        Log.e("123",bytes.toString());
//                    }
//                });
                break;
        }
    }



    Thread pulstThread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (isPulse && fastSocketClient.isConnected()){
                fastSocketClient.send(HexUtil.hexStringToByte(Constants.SOCKET_Pulse));
            }
            SystemClock.sleep(5000);
            pulstThread.run();
        }
    });

    private void initSerialPort() {
        try {
            String serialport_no = (String) SPUtils.getParam(this, Constants.SP_SERIALPORT_NO, Constants.SERIALPORT_NO);
            int serialport_baudrate = (int) SPUtils.getParam(this, Constants.SP_SERIALPORT_BAUDRATE, Constants.SERIALPORT_BAUDRATE);
            serialPort = new SerialPort(new File(serialport_no),
                    serialport_baudrate, 0);
            mInputStream = serialPort.getInputStream();
            mOutputStream = serialPort.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("打开串口失败");
            ToastUtils.showShort("打开串口失败");
        }
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
        if(mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
            transaction.add(R.id.fl_view, mSettingFragment);
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
        if (mSettingFragment != null) {
            transaction.hide(mSettingFragment);
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

    public InputStream getmInputStream() {
        return mInputStream;
    }

    public OutputStream getmOutputStream() {
        return mOutputStream;
    }

    @Override
    public ExecutorService getReadEs() {
        return readES;
    }
}
