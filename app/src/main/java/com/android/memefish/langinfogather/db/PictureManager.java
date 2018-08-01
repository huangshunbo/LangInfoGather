package com.android.memefish.langinfogather.db;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.android.memefish.langinfogather.bean.ObligeeCountBean;
import com.android.memefish.langinfogather.util.UserUtil;

import java.util.HashMap;
import java.util.List;

public class PictureManager extends BaseManager{



    private PictureManager() {
    }

    public static void insetPicture(Picture picture) {
        pictureDao.insert(picture);
    }

    public static void deletePicture(Picture picture) {
        pictureDao.delete(picture);
    }

    public static List<Picture> listPicture(String oneLevel) {
        return listPicture(oneLevel,null);
    }

    public static List<Picture> listPicture(String oneLevel, String twoLevel) {
        return listPicture(oneLevel, twoLevel,null);
    }

    public static List<Picture> listPicture(String oneLevel, String twoLevel, String threeLevel) {
        String userid = UserUtil.getInstance().getUserId();
        String region = UserUtil.getInstance().getRegion();
        String obligee = UserUtil.getInstance().getObligee();
        if(TextUtils.isEmpty(twoLevel)){
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE=? AND ONE_LEVEL=?", userid,region,obligee,oneLevel);
        }else if(TextUtils.isEmpty(threeLevel)){
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE=? AND ONE_LEVEL=? and TWO_LEVEL=?", userid,region,obligee,oneLevel, twoLevel);
        }else{
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE=? AND ONE_LEVEL=? and TWO_LEVEL=? and THREE_LEVEL=?", userid,region,obligee,oneLevel, twoLevel, threeLevel);
        }
    }

    public static HashMap<String,ObligeeCountBean> listCount() {
        String SQL_DISTINCT_ENAME = "SELECT OBLIGEE,ONE_LEVEL,COUNT(*) FROM PICTURE WHERE USER=? AND REGION=? GROUP BY OBLIGEE,ONE_LEVEL";
        Cursor c = daoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, new String[]{"userid","region"});
        HashMap<String,ObligeeCountBean> beans = new HashMap<>();
        ObligeeCountBean bean = new ObligeeCountBean();
        try {
            if (c.moveToFirst()) {
                do {
                    String obligee = c.getString(0);
                    String oneLevel = c.getString(1);
                    int count = c.getInt(2);
                    if(!bean.getObligee().equals(obligee)){
                        beans.put(bean.getObligee(),bean);
                        bean = new ObligeeCountBean();
                    }
                    if(oneLevel.equals("房屋")){
                        bean.setFangwu(count);
                    }else if(oneLevel.equals("权利人")){
                        bean.setQuanliren(count);
                    }else if(oneLevel.equals("权属来源")){
                        bean.setQuanshulaiyuan(count);
                    }else if(oneLevel.equals("其他")){
                        bean.setQita(count);
                    }
                    Log.d("hsb","result " + c.getString(0) + "  " + c.getString(1) + "  " + c.getString(2));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return beans;
    }


}
