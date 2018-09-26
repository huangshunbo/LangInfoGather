package com.android.memefish.langinfogather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.android.memefish.langinfogather.BaseApplication;
import com.android.memefish.langinfogather.bean.ObligeeCountBean;
import com.android.memefish.langinfogather.http.bean.LoginBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserUtil {

    private String userId = "userid";//用戶id
    private Long region;//行政区id
    private String regionStr;//行政区名称
    private Long obligeeId = 0L;//权利人id
    private String obligee;//权利人名称
    private Long obligeeChildMainId;//主权利人id
    private ObligeeCountBean tags;//计数


    private LoginBean loginBean;
    private static final String USER_PATH = Environment.getExternalStorageDirectory() + File.separator + "user.info";

    private static UserUtil mUserUtil;
    private static SharedPreferences share = BaseApplication.application.getSharedPreferences("User", Context.MODE_PRIVATE);

    private UserUtil() {}

    public static UserUtil getInstance() {
        if(mUserUtil == null){
            synchronized (UserUtil.class){
                if(mUserUtil == null){
                    mUserUtil = new UserUtil();
                }
            }
        }
        return mUserUtil;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        if(TextUtils.isEmpty(userId)){
            loadDataFromSP();
        }
        return userId;
    }

    public Long getRegion() {
        if(region == null || region == 0){
            loadDataFromSP();
        }
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public String getRegionStr() {
        if(TextUtils.isEmpty(regionStr)){
            loadDataFromSP();
        }
        return regionStr;
    }

    public void setRegionStr(String regionStr) {
        this.regionStr = regionStr;
    }

    public String getObligee() {
        if(TextUtils.isEmpty(obligee)){
            loadDataFromSP();
        }
        return obligee;
    }

    public void setObligee(String obligee) {
        this.obligee = obligee;
    }

    public Long getObligeeChildMainId() {
        if(obligeeChildMainId == null || obligeeChildMainId == 0){
            loadDataFromSP();
        }
        return obligeeChildMainId;
    }

    public void setObligeeChildMainId(Long obligeeChildMainId) {
        this.obligeeChildMainId = obligeeChildMainId;
    }

    public ObligeeCountBean getTags() {
        if(tags == null || TextUtils.isEmpty(tags.getObligee())){
            loadDataFromSP();
        }
        return this.tags;
    }

    public void setTags(ObligeeCountBean tags) {
        if(tags == null){
            tags = new ObligeeCountBean();
            tags.setObligee(obligee);
        }
        this.tags = tags;
    }

    public static UserUtil getmUserUtil() {
        return mUserUtil;
    }

    public static void setmUserUtil(UserUtil mUserUtil) {
        UserUtil.mUserUtil = mUserUtil;
    }

    public Long getObligeeId() {
        if(obligeeId == 0){
            loadDataFromSP();
        }
        return obligeeId;
    }

    public void setObligeeId(Long obligeeId) {
        this.obligeeId = obligeeId;
    }

    public LoginBean getLoginBean() {
        if(loginBean == null){
            File file = new File(USER_PATH);
            if(file.exists()){
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    loginBean= (LoginBean)in.readObject();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
        try {
            File file = new File(USER_PATH);
            file.deleteOnExit();
            file.getParentFile().mkdirs();
            file.createNewFile();
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(loginBean);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SharedPreferences getShare() {
        return share;
    }

    public static void setShare(SharedPreferences share) {
        UserUtil.share = share;
    }

    private void loadDataFromSP(){
        userId = share.getString("userId",userId);
        region = share.getLong("region",region);
        regionStr = share.getString("regionStr",regionStr);
        obligee = share.getString("obligee",obligee);
        obligeeId = share.getLong("obligeeId",obligeeId != null ? obligeeId : 0);
        obligeeChildMainId = share.getLong("obligeeChildMainId",obligeeChildMainId != null ? obligeeChildMainId : 0);
        tags = new ObligeeCountBean();
        tags.setQuanliren(share.getInt("QUANLIREN",tags.getQuanliren()));
        tags.setFangwu(share.getInt("FANGWU",tags.getFangwu()));
        tags.setQuanshulaiyuan(share.getInt("QUANSHULAIYUAN",tags.getQuanshulaiyuan()));
        tags.setTuzhi(share.getInt("TUZHI",tags.getTuzhi()));
        tags.setQita(share.getInt("QITA",tags.getQita()));
        tags.setObligee(obligee);

    }

    public void saveDataToSP(){
        SharedPreferences.Editor edit = share.edit();
        edit.putString("userId",this.userId);
        edit.putLong("region",region);
        edit.putString("regionStr",regionStr);
        edit.putString("obligee",obligee);
        edit.putLong("obligeeId",obligeeId);
        edit.putLong("obligeeChildMainId",obligeeChildMainId);
        edit.putInt("QUANLIREN",tags.getQuanliren());
        edit.putInt("FANGWU",tags.getFangwu());
        edit.putInt("QUANSHULAIYUAN",tags.getQuanshulaiyuan());
        edit.putInt("TUZHI",tags.getTuzhi());
        edit.putInt("QITA",tags.getQita());
        edit.commit();
    }
}
