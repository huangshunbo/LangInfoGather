package com.android.memefish.langinfogather.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Picture {


    @Id
    private Long id;
    //用户名
    @NotNull
    private String user;
    //行政区
    @NotNull
    private Long region;
    //权利人id
    private Long oId;
    //子权利人id
    @NotNull
    private Long obligeeId;
    //层级
    @NotNull
    private String oneLevel;
    private String twoLevel;
    private String threeLevel;
    //存储路径
    @NotNull
    @Unique
    private String path;
    //文件名
    @NotNull
    private String name;
    @NotNull
    private int sort;

    @Generated(hash = 1602548376)
    public Picture() {
    }

    @Generated(hash = 2121320907)
    public Picture(Long id, @NotNull String user, @NotNull Long region, Long oId,
            @NotNull Long obligeeId, @NotNull String oneLevel, String twoLevel,
            String threeLevel, @NotNull String path, @NotNull String name,
            int sort) {
        this.id = id;
        this.user = user;
        this.region = region;
        this.oId = oId;
        this.obligeeId = obligeeId;
        this.oneLevel = oneLevel;
        this.twoLevel = twoLevel;
        this.threeLevel = threeLevel;
        this.path = path;
        this.name = name;
        this.sort = sort;
    }

    public Long getoId() {
        return oId;
    }

    public void setoId(Long oId) {
        this.oId = oId;
    }

    public Long getObligeeId() {
        return obligeeId;
    }

    public void setObligeeId(Long obligeeId) {
        this.obligeeId = obligeeId;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOneLevel() {
        return oneLevel;
    }

    public void setOneLevel(String oneLevel) {
        this.oneLevel = oneLevel;
    }

    public String getTwoLevel() {
        return twoLevel;
    }

    public void setTwoLevel(String twoLevel) {
        this.twoLevel = twoLevel;
    }

    public String getThreeLevel() {
        return threeLevel;
    }

    public void setThreeLevel(String threeLevel) {
        this.threeLevel = threeLevel;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Picture createNew() {
        Picture p = new Picture();
        p.setUser(user);
        p.setOneLevel(oneLevel);
        p.setTwoLevel(twoLevel);
        p.setThreeLevel(threeLevel);
        return p;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Long getOId() {
        return this.oId;
    }

    public void setOId(Long oId) {
        this.oId = oId;
    }
}
