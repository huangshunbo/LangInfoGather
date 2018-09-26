package com.android.memefish.langinfogather.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/26 0026.
 */

public class RegionBean implements Serializable{
    private String Province;//省
    private String City;//市
    private String County;//区
    private String AreaCode;//区号
    private String DJQDM;//地籍区
    private String DJZQDM;//地籍子区
    private String VillageName;//村名
    private String AddTime;//添加时间
    private String StaffId;
    private String XZQINFOID;

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getDJQDM() {
        return DJQDM;
    }

    public void setDJQDM(String DJQDM) {
        this.DJQDM = DJQDM;
    }

    public String getDJZQDM() {
        return DJZQDM;
    }

    public void setDJZQDM(String DJZQDM) {
        this.DJZQDM = DJZQDM;
    }

    public String getVillageName() {
        return VillageName;
    }

    public void setVillageName(String villageName) {
        VillageName = villageName;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    public String getXZQINFOID() {
        return XZQINFOID;
    }

    public void setXZQINFOID(String XZQINFOID) {
        this.XZQINFOID = XZQINFOID;
    }
}
