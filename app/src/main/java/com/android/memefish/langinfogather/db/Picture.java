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
    @NotNull
    private String user;
    @NotNull
    private String oneLevel;
    private String twoLevel;
    private String threeLevel;
    @NotNull
    @Unique
    private String path;
    @NotNull
    private String name;

    @Generated(hash = 1250045855)
    public Picture(Long id, @NotNull String user, @NotNull String oneLevel,
            String twoLevel, String threeLevel, @NotNull String path,
            @NotNull String name) {
        this.id = id;
        this.user = user;
        this.oneLevel = oneLevel;
        this.twoLevel = twoLevel;
        this.threeLevel = threeLevel;
        this.path = path;
        this.name = name;
    }

    @Generated(hash = 1602548376)
    public Picture() {
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
}
