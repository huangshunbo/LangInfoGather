package com.android.memefish.langinfogather.db;

import com.android.memefish.langinfogather.bean.ObligeeCountBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class Obligee{

    @Id
    private Long id;
    @NotNull
    private Long region;//行政区id
    @NotNull
    private String user;//用户id
    //预编码
    @NotNull
    private String num;
    //门牌号
    private String houseNumber;
    //状态
    private String status;
    //时间
    private String time;
    //姓名
    @NotNull
    private String name;//主权利人
    @Transient
    private ObligeeCountBean tags;

    @Generated(hash = 987620670)
    public Obligee() {
    }

    @Generated(hash = 1368412511)
    public Obligee(Long id, @NotNull Long region, @NotNull String user,
            @NotNull String num, String houseNumber, String status, String time,
            @NotNull String name) {
        this.id = id;
        this.region = region;
        this.user = user;
        this.num = num;
        this.houseNumber = houseNumber;
        this.status = status;
        this.time = time;
        this.name = name;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObligeeCountBean getTags() {
        return tags;
    }

    public void setTags(ObligeeCountBean tags) {
        this.tags = tags;
    }
}
