package com.android.memefish.langinfogather.bean;

import java.util.ArrayList;
import java.util.List;

public class DistrictsBean {


    String mainCode;
    List<Area> area = new ArrayList<>();

    public String getMainCode() {
        return mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode;
    }

    public List<Area> getArea() {
        return area;
    }

    public void setArea(List<Area> area) {
        this.area = area;
    }

    public void addArea(Area area){
        this.area.add(area);
    }

    public static class Area {
        String code;
        String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
