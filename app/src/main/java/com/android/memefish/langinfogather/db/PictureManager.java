package com.android.memefish.langinfogather.db;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.android.memefish.langinfogather.BaseApplication;

import java.util.List;

public class PictureManager {

    private static DaoSession daoSession;
    private static PictureDao pictureDao;


    static {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.application, "picture_manager.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        pictureDao = daoSession.getPictureDao();
    }

    private PictureManager() {}

    public static void insetPicture(Picture picture) {
        pictureDao.insert(picture);
    }

    public static void deletePicture(Picture picture) {
        pictureDao.delete(picture);
    }

    public static List<Picture> listPicture(String oneLevel) {
        if (!TextUtils.isEmpty(oneLevel)) {
            return pictureDao.queryRaw("where ONE_LEVEL=?", oneLevel);
        } else {
            throw new RuntimeException("传入参数均不可为空");
        }
    }

    public static List<Picture> listPicture(String oneLevel, String twoLevel) {
        if (TextUtils.isEmpty(twoLevel)) {
            return listPicture(oneLevel);
        }
        return pictureDao.queryRaw("where ONE_LEVEL=? and TWO_LEVEL=?", oneLevel, twoLevel);
    }

    public static List<Picture> listPicture(String oneLevel, String twoLevel, String threeLevel) {
        if (TextUtils.isEmpty(threeLevel)) {
            return listPicture(oneLevel, twoLevel);
        }
        return pictureDao.queryRaw("where ONE_LEVEL=? and TWO_LEVEL=? and THREE_LEVEL=?", oneLevel, twoLevel, threeLevel);
    }


}
