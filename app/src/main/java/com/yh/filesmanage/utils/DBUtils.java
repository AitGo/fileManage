package com.yh.filesmanage.utils;

import android.database.Cursor;

import com.yh.filesmanage.base.MyApplication;
import com.yh.filesmanage.database.greenDao.db.DaoSession;
import com.yh.filesmanage.database.greenDao.db.FileInfoDao;
import com.yh.filesmanage.diagnose.FileInfo;
import com.yh.filesmanage.diagnose.LayerEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    public static void insertOrReplaceFileInfo(FileInfo info) {
        MyApplication.getDaoSession().getFileInfoDao().insertOrReplace(info);
    }

    public static List<FileInfo> selectFileInfoByLayerNo(String layerNo) {
        return MyApplication.getDaoSession().getFileInfoDao().queryBuilder()
                .where(FileInfoDao.Properties.LayerNo.eq(layerNo))
                .list();
    }

    public static List<LayerEntity> selectLayerNoList() {
        List<LayerEntity> layerEntities = new ArrayList<>();
        DaoSession daoSession = MyApplication.getDaoSession();
        String SQL_DISTINCT_ENAME = "SELECT DISTINCT "+ FileInfoDao.Properties.LayerNo.columnName
                + " FROM " + FileInfoDao.TABLENAME
                + " order by " + FileInfoDao.Properties.LayerNo.columnName;
        //查询comparePhoto
        ArrayList<String> result = new ArrayList<String>();
        Cursor c = daoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
        try{
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        for(String layerNo : result) {
            LayerEntity entity = new LayerEntity();
            entity.setIndex(layerNo);
            List<FileInfo> list = daoSession.getFileInfoDao().queryBuilder()
                    .where(FileInfoDao.Properties.LayerNo.eq(layerNo))
                    .orderAsc(FileInfoDao.Properties.BoxNo)
                    .list();
            entity.setItems(list);
            layerEntities.add(entity);
        }
        return layerEntities;
    }

    public static List<FileInfo> selectFileInfoByState(String state) {
        return MyApplication.getDaoSession().getFileInfoDao().queryBuilder()
                .where(FileInfoDao.Properties.Status.eq(state))
                .list();
    }
    public static List<FileInfo> selectFileInfoByNotState(String state) {
        return MyApplication.getDaoSession().getFileInfoDao().queryBuilder()
                .where(FileInfoDao.Properties.Status.notEq(state))
                .list();
    }

    public static List<FileInfo> selectFileInfo(FileInfo info,String boxNo) {
        return MyApplication.getDaoSession().getFileInfoDao().queryBuilder()
                .where(FileInfoDao.Properties.HouseSNo.eq(info.getHouseSNo()),
                        FileInfoDao.Properties.AreaNO.eq(info.getAreaNO()),
                        FileInfoDao.Properties.CabinetNo.eq(info.getCabinetNo()),
                        FileInfoDao.Properties.FaceNo.eq(info.getFaceNo()),
                        FileInfoDao.Properties.ClassNo.eq(info.getClassNo()),
                        FileInfoDao.Properties.LayerNo.eq(info.getLayerNo()),
                        FileInfoDao.Properties.BoxNo.eq(boxNo))
                .list();
    }

    public static List<FileInfo> selectFileInfo(FileInfo info,String boxNo,String uid) {
        return MyApplication.getDaoSession().getFileInfoDao().queryBuilder()
                .where(FileInfoDao.Properties.HouseSNo.eq(info.getHouseSNo()),
                        FileInfoDao.Properties.AreaNO.eq(info.getAreaNO()),
                        FileInfoDao.Properties.CabinetNo.eq(info.getCabinetNo()),
                        FileInfoDao.Properties.FaceNo.eq(info.getFaceNo()),
                        FileInfoDao.Properties.ClassNo.eq(info.getClassNo()),
                        FileInfoDao.Properties.LayerNo.eq(info.getLayerNo()),
                        FileInfoDao.Properties.BoxNo.eq(boxNo),
                        FileInfoDao.Properties.Barcode.eq(uid))
                .list();
    }

    public static List<FileInfo> selectFileInfoByBarcode(String barcode) {
        return MyApplication.getDaoSession().getFileInfoDao().queryBuilder()
                .where(FileInfoDao.Properties.Barcode.eq(barcode))
                .list();
    }

    public static List<FileInfo> selectAllFileInfo() {
        return MyApplication.getDaoSession().getFileInfoDao().loadAll();
    }

    public static void insertOrReplaceFileInfoList(List<FileInfo> fileInfoList) {
        MyApplication.getDaoSession().getFileInfoDao().insertOrReplaceInTx(fileInfoList);
    }
}
