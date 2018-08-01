package com.android.memefish.langinfogather.db;

import android.database.sqlite.SQLiteDatabase;

import com.android.memefish.langinfogather.BaseApplication;

public class BaseManager {

    static DaoSession daoSession;
    static PictureDao pictureDao;
    static ObligeeDao obligeeDao;


    static {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.application, "picture_manager.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        pictureDao = daoSession.getPictureDao();
        obligeeDao = daoSession.getObligeeDao();
    }
}
