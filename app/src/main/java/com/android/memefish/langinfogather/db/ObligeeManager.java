package com.android.memefish.langinfogather.db;

import java.util.ArrayList;
import java.util.List;

public class ObligeeManager extends BaseManager{

    public static void insertObligee(Obligee obligee){
        obligeeDao.insertOrReplace(obligee);
    }

    public static void updateObligee(Obligee obligee){
        obligeeDao.update(obligee);
    }

    public static void deleteObligee(Long id){
        obligeeDao.deleteByKey(id);
    }

    public static List<Obligee> listObligee(String user,String region){
        return obligeeDao.queryRaw("WHERE USER=? AND REGION=?", user,region);
    }

    public static Obligee getObligee(Long id){
        return obligeeDao.loadByRowId(id);
    }

}
