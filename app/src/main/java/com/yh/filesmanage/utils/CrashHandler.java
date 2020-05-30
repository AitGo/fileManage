package com.yh.filesmanage.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.yh.filesmanage.base.Constants;
import com.yh.filesmanage.base.MyApplication;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * @创建者 ly
 * @创建时间 2020/1/10
 * @描述 崩溃日志handler
 * @更新者 $
 * @更新时间 $
 * @更新描述 崩溃日志handler
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler myCrashHandler;

    private CrashHandler() {
    };

    private Context context;

    private Thread.UncaughtExceptionHandler defaultExceptionHandler;
    public synchronized static CrashHandler getInstance() {
        if (myCrashHandler == null) {
            myCrashHandler = new CrashHandler();
        }
        return myCrashHandler;
    }

    public void init(Context context) {
        this.context = context;
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if (sdCardIsAvailable()) {
            try {
                // 在throwable的参数里面保存的有程序的异常信息
                StringBuffer sb = new StringBuffer();
                sb.append("Time:" + StringUtils.Date2String(new Date(),"yyyy年MM月dd日HH时mm分"));
                sb.append("\n");
                // 1.得到手机的版本信息 硬件信息
                Field[] fields = Build.class.getDeclaredFields();
                for (Field filed : fields) {
                    filed.setAccessible(true); // 暴力反射
                    String name = filed.getName();
                    String value = filed.get(null).toString();
                    sb.append(name);
                    sb.append("=");
                    sb.append(value);
                    sb.append("\n");
                }
                // 2.得到当前程序的版本号
                PackageInfo info = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), 0);
                sb.append(info.versionName);
                sb.append("\n");
                // 3.得到当前程序的异常信息
                Writer writer = new StringWriter();
                PrintWriter printwriter = new PrintWriter(writer);

                ex.printStackTrace(printwriter);
                printwriter.flush();
                printwriter.close();

                sb.append(writer.toString());

                FileUtils.writeTxtToFile(sb.toString(), Constants.settingDir + "/", "crash_" +StringUtils.Date2String(new Date(),"yyyy_MM_dd_HH_mm_ss") + ".txt");
//                File ff = new File(Constants.filePath + "/crash.txt");
//                ff.createNewFile();
//                FileWriter fw = new FileWriter(new File(Constants.filePath + "/crash.txt"));
//                fw.write(sb.toString());
//                fw.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(context, "异常退出", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }.start();
            return;
        }
    }

    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }
}
