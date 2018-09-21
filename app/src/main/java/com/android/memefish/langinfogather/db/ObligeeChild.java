package com.android.memefish.langinfogather.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ObligeeChild {

    @Id
    private Long id;
    @NotNull
    private Long parentId;
    @NotNull
    private String name;
    @NotNull
    private String property;

    @Generated(hash = 1900992794)
    public ObligeeChild(Long id, @NotNull Long parentId, @NotNull String name,
            @NotNull String property) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.property = property;
    }

    @Generated(hash = 1323172856)
    public ObligeeChild() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
