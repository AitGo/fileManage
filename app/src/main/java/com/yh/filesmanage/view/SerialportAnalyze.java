package com.yh.filesmanage.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;

/**
 * @创建者 ly
 * @创建时间 2020/6/6
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public interface SerialportAnalyze {

    void sendSeriportData(byte[] send);

    void sendSocketData(byte[] send);

    /**
     *
     * @param send
     * @param type 1:上架 2:盘点
     */
    void sendSocketData(byte[] send,int type);

    void initSerialPort();

    void setSocektType(int type);
}
