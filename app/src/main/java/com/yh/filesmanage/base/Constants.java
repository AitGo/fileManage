package com.yh.filesmanage.base;


import android.os.Environment;

import com.yh.filesmanage.utils.LogUtils;

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
    //    public static String defaultURL = "http://175.6.27.77:8088/lianyservice/service/appUploadDataService?wsdl";
    //    public static String defaultURL = "http://175.6.27.77:8084/lianyservice/service/appUploadDataService?wsdl";

    public static String SOCKET_IP = "192.168.31.185";
    public static  int SOCKET_PORT = 36401;
    public static String SOCKET_Pulse = "7E000200000183399625520001597E";

    public static String SERIALPORT_NO = "/dev/ttyS4";//串口号
    public static int SERIALPORT_BAUDRATE = 115200;//波特率


    public static final boolean Bugly_isDebug = true;
    public static int DEBUGLEVEL = LogUtils.LEVEL_ALL; //打开日志开关
    public static String SP_FILE_NAME = "fileManageSP";
    public static String LOG_NAME = "manage";

    public static final String settingDir = MyApplication.getContext().getExternalFilesDir("Setting").getAbsolutePath();
}
