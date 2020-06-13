package com.yh.filesmanage.base;


import android.os.Environment;

import com.yh.filesmanage.utils.LogUtils;

import java.io.File;

/**
 * @创建者 ly
 * @创建时间 2019/3/15
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class Constants {

    public static String defaultURL = "http://192.168.31.88:8080/lianyservice/service/appUploadDataService";

    public static String SOCKET_IP = "192.168.31.185";
    public static  int SOCKET_PORT = 5000;
    public static String SOCKET_Pulse = "7E000200000183399625520001597E";

    public static final String SERIALPORT_NO = "/dev/ttyS4";//串口号
    public static final int SERIALPORT_BAUDRATE = 115200;//波特率

    public static final boolean Bugly_isDebug = true;
    public static int DEBUGLEVEL = LogUtils.LEVEL_ALL; //打开日志开关
    public static String SP_FILE_NAME = "fileManageSP";
    public static String LOG_NAME = "manage";
    public static String SP_SERIALPORT_NO = "SERIALPORT_NO";
    public static String SP_SERIALPORT_BAUDRATE = "SERIALPORT_BAUDRATE";
    public static String SP_SOCKET_IP = "SOCKET_IP";
    public static String SP_SOCKET_PORT = "SOCKET_PORT";
    public static String SP_SOCKET_Pulse = "SOCKET_Pulse";
    public static String SP_NO_AREA = "area_no";//区号
    public static String SP_NO_LAYER = "layer_no";//层数
    public static String SP_NO_CABINET = "cabinet_no";//柜号
    public static String SP_NO_BOX = "box_no";//盒号
    public static String SP_NO_CABINET_MIN = "cabinet_no_min";
    public static String SP_NO_CABINET_MAX = "cabinet_no_max";


    public static final String settingDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "fileManage";
}
