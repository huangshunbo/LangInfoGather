package com.android.memefish.langinfogather.db.manager;

import android.database.Cursor;
import android.text.TextUtils;

import com.android.memefish.langinfogather.bean.ObligeeCountBean;
import com.android.memefish.langinfogather.bean.QuanlirenCountBean;
import com.android.memefish.langinfogather.db.Picture;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.UserUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class PictureManager extends BaseManager {


    private PictureManager() {
    }

    public static void insetPicture(Picture picture) {
        pictureDao.insertOrReplace(picture);
    }

    public static void deletePicture(String oneLevel,String twoLevel){
        List<Picture> pics = listPicture(oneLevel,twoLevel);
        for(Picture pic : pics){
            deletePicture(pic);
            PictureUtil.deleteDirWihtFile(new File(pic.getPath()));
        }
    }

    public static void deletePicture(Picture picture) {
        pictureDao.delete(picture);
    }

    public static void deletePicture(Long obligeeId){
        List<Picture> pics =  pictureDao.queryRaw("where OBLIGEE_ID=?",""+obligeeId);
        for(Picture pic : pics){
            deletePicture(pic);
        }
    }

    public static List<Picture> listPictureWithObligee(Long obligee,String oneLevel,String twoLevel,String threeLevel){
        String userid = UserUtil.getInstance().getUserId();
        String region = ""+UserUtil.getInstance().getRegion();
        if(!TextUtils.isEmpty(threeLevel)){
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=? AND ONE_LEVEL=? and TWO_LEVEL=? and THREE_LEVEL=?", userid, region, ""+obligee,oneLevel,twoLevel,threeLevel);
        }else if(!TextUtils.isEmpty(twoLevel)){
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=? AND ONE_LEVEL=? and TWO_LEVEL=?", userid, region, ""+obligee,oneLevel,twoLevel);
        }else if(!TextUtils.isEmpty(oneLevel)){
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=? AND ONE_LEVEL=?", userid, region, ""+obligee,oneLevel);
        }else {
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=?", userid, region, ""+obligee);
        }
    }

    public static List<Picture> listPictureWithObligee(Long obligee,String oneLevel,String twoLevel){
        return listPictureWithObligee(obligee,oneLevel,twoLevel,null);
    }

    public static List<Picture> listPictureWithObligee(Long obligee,String oneLevel){
        return listPictureWithObligee(obligee,oneLevel,null,null);
    }

    public static List<Picture> listPictureWithObligee(Long obligee){
        return listPictureWithObligee(obligee,null,null,null);
    }

    public static List<Picture> listPicture(String oneLevel) {
        return listPicture(oneLevel, null);
    }

    public static List<Picture> listPicture(String oneLevel, String twoLevel) {
        return listPicture(oneLevel, twoLevel, null);
    }

    public static List<Picture> listPicture(String oneLevel, String twoLevel, String threeLevel) {
        String userid = UserUtil.getInstance().getUserId();
        String region = ""+UserUtil.getInstance().getRegion();
        String obligeeId = ""+UserUtil.getInstance().getObligeeId();
        if (!TextUtils.isEmpty(threeLevel)) {
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=? AND ONE_LEVEL=? and TWO_LEVEL=? and THREE_LEVEL=?", userid, region, obligeeId, oneLevel, twoLevel, threeLevel);
        } else if (!TextUtils.isEmpty(twoLevel)) {
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=? AND ONE_LEVEL=? and TWO_LEVEL=?", userid, region, obligeeId, oneLevel, twoLevel);
        } else if(!TextUtils.isEmpty(oneLevel)) {
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=? AND ONE_LEVEL=?", userid, region, obligeeId, oneLevel);
        }else{
            return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=?", userid, region, obligeeId);
        }
    }

    public static List<Picture> listPicture(String userid,Long region,String obligee,String oneLevel) {
        return pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=? AND ONE_LEVEL=? GROUP BY SORT,_ID", userid, ""+region, obligee, oneLevel);

    }

    public static HashMap<String, ObligeeCountBean> listCount() {
        String SQL_DISTINCT_ENAME = "SELECT O_ID,ONE_LEVEL,COUNT(*) FROM PICTURE WHERE USER=? AND REGION=? GROUP BY O_ID,ONE_LEVEL";
        Cursor c = daoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, new String[]{UserUtil.getInstance().getUserId(), ""+UserUtil.getInstance().getRegion()});
        HashMap<String, ObligeeCountBean> beans = new HashMap<>();
        ObligeeCountBean bean = new ObligeeCountBean();
        try {
            if (c.moveToFirst()) {
                do {
                    String obligee = c.getString(0);
                    String oneLevel = c.getString(1);
                    int count = c.getInt(2);
                    if (!obligee.equals(bean.getObligee())) {
                        if (bean.getObligee() != null) {
                            beans.put(bean.getObligee(), bean);
                        }
                        bean = new ObligeeCountBean();
                        bean.setObligee(obligee);
                    }
                    if (oneLevel.equals("房屋")) {
                        bean.setFangwu(count);
                    } else if (oneLevel.equals("权利人")) {
                        bean.setQuanliren(count);
                    } else if (oneLevel.equals("权属来源")) {
                        bean.setQuanshulaiyuan(count);
                    } else if(oneLevel.equals("图纸")){
                        bean.setTuzhi(count);
                    }else if (oneLevel.equals("其他")) {
                        bean.setQita(count);
                    }
                } while (c.moveToNext());
                if (bean.getObligee() != null) {
                    beans.put(bean.getObligee(), bean);
                }
            }
        } finally {
            c.close();
        }
        return beans;
    }

    public static HashMap<String,QuanlirenCountBean> countQuanliren(Long oId){
        String SQL_DISTINCT_ENAME = "SELECT TWO_LEVEL,THREE_LEVEL,COUNT(*) FROM PICTURE WHERE USER=? AND REGION=? AND O_ID=? AND ONE_LEVEL=? GROUP BY TWO_LEVEL,THREE_LEVEL";
        Cursor c = daoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, new String[]{UserUtil.getInstance().getUserId(), ""+UserUtil.getInstance().getRegion(),""+oId,"权利人"});
        HashMap<String,QuanlirenCountBean> beans = new HashMap<>();
        try {
            if (c.moveToFirst()) {
                QuanlirenCountBean bean;
                do {
                    String name = c.getString(0);
                    String type = c.getString(1);
                    int count = c.getInt(2);
                    if(beans.get(name) == null){
                        bean = new QuanlirenCountBean();
                        beans.put(name,bean);
                    }else{
                        bean = beans.get(name);
                    }
                    if(type.equals("身份证")){
                        bean.setSfzCount(count);
                    }else{
                        bean.setHkbCount(count);
                    }
                } while (c.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            c.close();
        }
        return beans;
    }

    public static HashMap<String,Integer> countQuanshulaiyuan(){
        String SQL_DISTINCT_ENAME = "SELECT TWO_LEVEL,COUNT(*) FROM PICTURE WHERE USER=? AND REGION=? AND O_ID=? AND ONE_LEVEL='权属来源' GROUP BY TWO_LEVEL";
        Cursor c = daoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, new String[]{UserUtil.getInstance().getUserId(), ""+UserUtil.getInstance().getRegion(),""+UserUtil.getInstance().getObligeeId()});
        HashMap<String,Integer> beans = new HashMap<>();
        try {
            if (c.moveToFirst()) {
                QuanlirenCountBean bean = new QuanlirenCountBean();
                do {
                    String name = c.getString(0);
                    int count = c.getInt(1);
                    beans.put(name,count);
                } while (c.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            c.close();
        }
        return beans;
    }

    public static void updatePictureQLR(String userid,Long region,Long obligeeId,String twoLevel){
        List<Picture> pics = pictureDao.queryRaw("where USER=? AND REGION=? AND OBLIGEE_ID=? AND ONE_LEVEL=?", userid, ""+region, ""+obligeeId, "权利人");
        for(Picture pic : pics){
            pic.setTwoLevel(twoLevel);
            pictureDao.update(pic);
        }
    }


}
