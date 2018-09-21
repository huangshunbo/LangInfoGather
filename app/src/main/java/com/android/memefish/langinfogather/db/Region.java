package com.android.memefish.langinfogather.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Region {

    @Id
    private Long id;
    private String user;//用户
    @NotNull
    private String province;//省
    @NotNull
    private String city;//市
    @NotNull
    private String area;//区
    @NotNull
    private String addr;//地籍区
    @NotNull
    private String addrDetail;//地籍子区
    @NotNull
    private String village;//村名
    @NotNull
    private String code;//区号

    @Generated(hash = 613215166)
    public Region(Long id, String user, @NotNull String province,
            @NotNull String city, @NotNull String area, @NotNull String addr,
            @NotNull String addrDetail, @NotNull String village,
            @NotNull String code) {
        this.id = id;
        this.user = user;
        this.province = province;
        this.city = city;
        this.area = area;
        this.addr = addr;
        this.addrDetail = addrDetail;
        this.village = village;
        this.code = code;
    }

    @Generated(hash = 600106640)
    public Region() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
