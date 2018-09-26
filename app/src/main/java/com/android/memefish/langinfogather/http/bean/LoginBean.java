package com.android.memefish.langinfogather.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public class LoginBean implements Serializable{

    private int StaffId;
    private String StaffTelephone;
    private String StaffPostName;
    private String Password;
    private boolean IsEnable;
    private String AddTime;
    private String DayOfWeekJson;
    private String WorkingStartTime;
    private String WorkingEndTime;
    private String RoleNumber;

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int staffId) {
        StaffId = staffId;
    }

    public String getStaffTelephone() {
        return StaffTelephone;
    }

    public void setStaffTelephone(String staffTelephone) {
        StaffTelephone = staffTelephone;
    }

    public String getStaffPostName() {
        return StaffPostName;
    }

    public void setStaffPostName(String staffPostName) {
        StaffPostName = staffPostName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isEnable() {
        return IsEnable;
    }

    public void setEnable(boolean enable) {
        IsEnable = enable;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getDayOfWeekJson() {
        return DayOfWeekJson;
    }

    public void setDayOfWeekJson(String dayOfWeekJson) {
        DayOfWeekJson = dayOfWeekJson;
    }

    public String getWorkingStartTime() {
        return WorkingStartTime;
    }

    public void setWorkingStartTime(String workingStartTime) {
        WorkingStartTime = workingStartTime;
    }

    public String getWorkingEndTime() {
        return WorkingEndTime;
    }

    public void setWorkingEndTime(String workingEndTime) {
        WorkingEndTime = workingEndTime;
    }

    public String getRoleNumber() {
        return RoleNumber;
    }

    public void setRoleNumber(String roleNumber) {
        RoleNumber = roleNumber;
    }
}
