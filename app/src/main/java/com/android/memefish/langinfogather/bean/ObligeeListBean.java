package com.android.memefish.langinfogather.bean;

import java.io.Serializable;

public class ObligeeListBean implements Serializable {
    private String type;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
