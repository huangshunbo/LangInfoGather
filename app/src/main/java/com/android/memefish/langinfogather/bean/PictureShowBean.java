package com.android.memefish.langinfogather.bean;

import java.io.Serializable;
import java.util.List;

public class PictureShowBean implements Serializable,Cloneable {

    private String title;
    private String oneLevel;
    private String twoLevel;
    private String threeLevel;
    private List<String> dirs;
    private List<Integer> maxPicSizes;

    public List<Integer> getMaxPicSizes() {
        return maxPicSizes;
    }

    public void setMaxPicSizes(List<Integer> maxPicSizes) {
        this.maxPicSizes = maxPicSizes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
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

    @Override
    public Object clone(){
        PictureShowBean bean = null;
        try {
            bean = (PictureShowBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if(bean == null){
            bean = new PictureShowBean();
            bean.setTitle(getTitle());
            bean.setDirs(getDirs());
            bean.setOneLevel(getOneLevel());
            bean.setTwoLevel(getTwoLevel());
            bean.setThreeLevel(getThreeLevel());
        }
        return bean;
    }
}
