package com.android.memefish.langinfogather.db.manager;

import android.database.Cursor;

import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.ObligeeChild;
import com.android.memefish.langinfogather.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class ObligeeManager extends BaseManager{

    public static Long insertObligee(Obligee obligee){
        return obligeeDao.insert(obligee);
    }

    public static void updateObligee(Obligee obligee){
        obligeeDao.update(obligee);
    }

    public static void deleteObligee(Long id){
        obligeeDao.deleteByKey(id);
    }

    public static List<Obligee> findObligee(String num){
        return obligeeDao.queryRaw("WHERE NUM=? AND REGION=?",num,""+UserUtil.getInstance().getRegion());
    }


    public static List<Obligee> listObligee(String user,Long region){
        List<Obligee> obligees = new ArrayList<>();
        try {
            obligees = obligeeDao.queryRaw("WHERE USER=? AND REGION=?", user,""+region);
        }catch (Exception e){
            e.printStackTrace();
        }
        return obligees;
    }

    public static List<Obligee> listObligee(String keyword){
//        List<Obligee> obligees = new ArrayList<>();
//        try {
//            obligees = obligeeDao.queryRaw("WHERE NUM LIKE ? OR NAME LIKE ? OR HOUSE_NUMBER=?", "%"+keyword+"%","%"+keyword+"%","%"+keyword+"%");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return obligees;
        String SQL_SEARCH = "SELECT OBLIGEE._id,OBLIGEE.REGION,OBLIGEE.USER,OBLIGEE.NUM,OBLIGEE.HOUSE_NUMBER,OBLIGEE.STATUS,OBLIGEE.TIME,OBLIGEE.NAME FROM OBLIGEE JOIN OBLIGEE_CHILD ON OBLIGEE._ID=OBLIGEE_CHILD.PARENT_ID WHERE OBLIGEE.NUM LIKE ? OR OBLIGEE.HOUSE_NUMBER=? OR OBLIGEE_CHILD.NAME LIKE ?";
        Cursor c = daoSession.getDatabase().rawQuery(SQL_SEARCH, new String[]{keyword,keyword,keyword});
        List<Obligee> obligees = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                do {
                    Obligee obligee = new Obligee();
                    obligee.setId(c.getLong(0));
                    obligee.setRegion(c.getLong(1));
                    obligee.setUser(c.getString(2));
                    obligee.setNum(c.getString(3));
                    obligee.setHouseNumber(c.getString(4));
                    obligee.setStatus(c.getString(5));
                    obligee.setTime(c.getString(6));
                    obligee.setName(c.getString(7));
                    obligees.add(obligee);
                } while (c.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            c.close();
        }
        return obligees;
    }

    public static Obligee getObligee(Long id){
        return obligeeDao.loadByRowId(id);
    }

    public static List<ObligeeChild> listObligeeChild(Long parentId){
        return obligeeChildDao.queryRaw("WHERE PARENT_ID=? GROUP BY _ID",""+parentId);
    }

    public static void insertObligeeChild(ObligeeChild child) {
        obligeeChildDao.insertOrReplace(child);
    }

    public static void deleteObligeeChild(Long id){
        obligeeChildDao.deleteByKey(id);
    }

    public static ObligeeChild getObligeeChild(Long parentId){
        List<ObligeeChild> childList = listObligeeChild(parentId);
        for(ObligeeChild child : childList){
            if(child.getProperty().equals("主权利人")){
                return child;
            }
        }
        return null;
    }
}
