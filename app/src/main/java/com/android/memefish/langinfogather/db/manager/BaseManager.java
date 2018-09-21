package com.android.memefish.langinfogather.db.manager;

import android.database.sqlite.SQLiteDatabase;

import com.android.memefish.langinfogather.BaseApplication;
import com.android.memefish.langinfogather.db.DaoMaster;
import com.android.memefish.langinfogather.db.DaoSession;
import com.android.memefish.langinfogather.db.ObligeeChildDao;
import com.android.memefish.langinfogather.db.ObligeeDao;
import com.android.memefish.langinfogather.db.PictureDao;
import com.android.memefish.langinfogather.db.QuestionNaireDao;
import com.android.memefish.langinfogather.db.RegionDao;

public class BaseManager {

    static DaoSession daoSession;
    static RegionDao regionDao;
    static ObligeeDao obligeeDao;
    static ObligeeChildDao obligeeChildDao;
    static PictureDao pictureDao;
    static QuestionNaireDao questionNaireDao;



    static {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.application, "picture_manager.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        regionDao = daoSession.getRegionDao();
        obligeeDao = daoSession.getObligeeDao();
        obligeeChildDao = daoSession.getObligeeChildDao();
        pictureDao = daoSession.getPictureDao();
        questionNaireDao = daoSession.getQuestionNaireDao();
    }
}
