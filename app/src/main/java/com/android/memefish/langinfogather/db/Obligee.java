package com.android.memefish.langinfogather.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Obligee{

    @Id
    private Long id;
    @NotNull
    private String region;
    @NotNull
    private String user;
    //预编码
    @NotNull
    @Unique
    private String num;
    //门牌号
    @NotNull
    @Unique
    private String houseNumber;
    //状态
    private String status;
    //时间
    private String time;
    //姓名
    private String names;
    //属性
    private String propertys;

    @Generated(hash = 76991564)
    public Obligee(Long id, @NotNull String region, @NotNull String user,
            @NotNull String num, @NotNull String houseNumber, String status,
            String time, String names, String propertys) {
        this.id = id;
        this.region = region;
        this.user = user;
        this.num = num;
        this.houseNumber = houseNumber;
        this.status = status;
        this.time = time;
        this.names = names;
        this.propertys = propertys;
    }

    @Generated(hash = 987620670)
    public Obligee() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
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

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPropertys() {
        return propertys;
    }

    public void setPropertys(String propertys) {
        this.propertys = propertys;
    }

    public String getNameAndProperty(){
        StringBuffer stringBuffer = new StringBuffer();
        String[] nameArray = names.split(",");
        String[] proArray = propertys.split(",");
        for(int i=0; i<nameArray.length; i++){
            stringBuffer.append(nameArray[i]);
            stringBuffer.append("("+ proArray[i]+"),");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }

}
