package com.yh.filesmanage.utils;

import android.content.Context;
import android.util.Log;

import com.aill.androidserialport.SerialPort;
//import com.common.thermalimage.CalibrationCallBack;
//import com.common.thermalimage.HotImageCallback;
//import com.common.thermalimage.TemperatureBitmapData;
//import com.common.thermalimage.TemperatureData;
//import com.common.thermalimage.ThermalImageUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CREATE BY HAORAN QIN
 * ON 2020/3/12
 * https://github.com/haoran1994
 */
public class TempTool  {
    private File file;
    private int JBSPort = 19200;
    private int SMTPort = 115200;
    private int SSPort = 115200;
//    ThermalImageUtil temperatureUtil;
    private OnGetTempListener onGetTempListener;
    boolean isFixTemp = false;
    ExecutorService readES = Executors.newSingleThreadExecutor();
    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
    public interface OnGetTempListener {
        void CurrentTemp(float temp, String msg);
    }
    public void setCurrentTempListener(OnGetTempListener onGetTempListener){
        this.onGetTempListener  = onGetTempListener;
    }
    private OnFixTempListener onFixTempListener;
    public interface OnFixTempListener {
        void fixTempResult(String msg);
    }
    public void setFixTempListener(OnFixTempListener onFixTempListener){
        this.onFixTempListener  = onFixTempListener;
    }
    public TempTool(Context context) {
        file = new File("/dev/ttyS3");
        openGetSSSerial();
    }

    //盛思达串口
    public void openGetSSSerial() {
        readES.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SerialPort serialPort = new SerialPort(new File("/dev/ttyS0"), 9600, 0);
                    //从串口对象中获取输入流
                    InputStream inputStream = serialPort.getInputStream();
                    OutputStream out = serialPort.getOutputStream();
                    while (true) {
                        byte[] send = new byte[]{(byte)0xFE,(byte)0x32,(byte)0x2A,(byte)0x00};//查询报文
                        out.write(send);
                        out.flush();
                        //System.out.println("串口发送");
                        Thread.sleep(150);
                        mainloop(inputStream);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void mainloop(InputStream inputStream) throws IOException {
        if (inputStream.available() >= 10) {
            byte[] Re_buf = new byte[inputStream.available()];
            int size = inputStream.read(Re_buf);
            System.out.println("时间：" + System.currentTimeMillis() + ",Re_buf:" + size);

            if(Re_buf[0] == (byte)0xFE && Re_buf[1] == (byte)0x32){
                int temp1 = Integer.parseInt(String.format("%02x", new Integer(Re_buf[8] & 0xff)).toUpperCase(),16);
                int temp2 = Integer.parseInt(String.format("%02x", new Integer(Re_buf[9] & 0xff)).toUpperCase(),16);
                float f = Float.valueOf(df.format((temp1 * 256 + temp2)/10F  - 273.15F));
                if(f < 0){
                    return;
                }
                if(f>=35.8f&&f<=42f){
                    onGetTempListener.CurrentTemp(f,"");
                }
            }
        }
    }

}
