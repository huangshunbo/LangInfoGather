package com.android.memefish.langinfogather.db.manager;

import com.android.memefish.langinfogather.db.Region;

import java.util.List;

public class RegionManager extends BaseManager{

    public static void insertRegion(Region region){
        regionDao.insertOrReplace(region);
    }

    public static List<Region> listRegion(String user){
        return regionDao.queryRaw("where USER=?", user);
    }

    public static void deleteRegion(Long id){
        regionDao.deleteByKey(id);
    }

    public static Region selectRegion(Long id) {
        return regionDao.load(id);
    }
}
