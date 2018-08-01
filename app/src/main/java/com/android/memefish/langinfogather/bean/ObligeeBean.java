package com.android.memefish.langinfogather.bean;

import java.util.HashMap;

public class ObligeeBean {

    //名字
    private String[] name;
    //属性
    private String[] propertys;
    //门牌号
    private String houseNumber;
    //状态
    private String status;
    //时间
    private String time;
    //预编码
    private String num;

    private HashMap<String,ObligeeCountBean> tags;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String ...name) {
        this.name = name;
    }

    public String getNames(){
        StringBuffer stringBuffer = new StringBuffer();
        for(String s : name){
            stringBuffer.append(s + ",");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }

    public String getNameAndProperty(){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0; i<name.length; i++){
            stringBuffer.append(name[i]);
            stringBuffer.append("("+ propertys[i]+"),");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }

    public String[] getProperty() {
        return propertys;
    }

    public String getPropertys(){
        StringBuffer stringBuffer = new StringBuffer();
        for(String s : propertys){
            stringBuffer.append(s + ",");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }

    public void setPropertys(String ... propertys) {
        this.propertys = propertys;
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

    public HashMap<String, ObligeeCountBean> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, ObligeeCountBean> tags) {
        this.tags = tags;
    }
}
