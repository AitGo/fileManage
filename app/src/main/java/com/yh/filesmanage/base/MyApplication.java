package com.yh.filesmanage.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tencent.bugly.crashreport.CrashReport;
import com.yh.filesmanage.database.greenDao.db.DBHelper;
import com.yh.filesmanage.database.greenDao.db.DaoMaster;
import com.yh.filesmanage.database.greenDao.db.DaoSession;
import com.yh.filesmanage.utils.CrashHandler;

/**
 * @创建者 liuyang
 * @创建时间 2018/6/25 19:26
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取context
        mContext = getApplicationContext();
        initBugly();
        initGreenDao();
        initCrash();
    }


    //创建一个静态的方法，以便获取context对象
    public static Context getContext(){
        return mContext;
    }


    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "cdac8d34db", Constants.Bugly_isDebug);
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(new GreenDaoContext(mContext), "filesManage.db");
        DBHelper helper = new DBHelper(new GreenDaoContext(mContext),"filesManage.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;

    private void initCrash() {
        //捕获错误日志
        new Thread(){
            @Override
            public void run() {
                //把异常处理的handler设置到主线程里面
                CrashHandler ch = CrashHandler.getInstance();
                ch.init(getApplicationContext());
            }
        }.start();

    }
}