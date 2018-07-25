package com.android.memefish.langinfogather.bean;

import java.io.Serializable;

public class ProvinceBean implements Serializable {

    private String province;
    private String city;
    private String area;
    private String addr;
    private String addrDetail;
    private String village;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    @Override
    public String toString() {
        return "ProvinceBean{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", addr='" + addr + '\'' +
                ", addrDetail='" + addrDetail + '\'' +
                ", village='" + village + '\'' +
                '}';
    }
}
