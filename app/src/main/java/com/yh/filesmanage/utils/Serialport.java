package com.yh.filesmanage.utils;

import java.io.FileDescriptor;

/**
 * @创建者 ly
 * @创建时间 2020/5/12
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public class Serialport {

    // JNI(调用java本地接口，实现串口的打开和关闭)
/**串口有五个重要的参数：串口设备名，波特率，检验位，数据位，停止位
 其中检验位一般默认位NONE,数据位一般默认为8，停止位默认为1*/
    /**
     * @param path 串口设备的据对路径
     * @param baudrate 波特率
     * @param dataBits 数据位
     * @param stopBits 停止位
     * @param parity 校验位
     */
    private native static FileDescriptor open(String path, int baudrate,
                                              int dataBits, int stopBits, char parity);
    public static native void close();


}
