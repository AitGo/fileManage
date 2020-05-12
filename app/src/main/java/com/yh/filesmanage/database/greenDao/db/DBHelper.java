package com.yh.filesmanage.database.greenDao.db;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

/**
 * @创建者 ly
 * @创建时间 2019/4/19
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class DBHelper extends DaoMaster.DevOpenHelper {
    public DBHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        DBMigrationHelper.getInstance().migrate(db, TestEntityDao.class);
    }
}
