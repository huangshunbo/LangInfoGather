package com.android.memefish.langinfogather.util;

public class UserUtil {

    private String userId;
    private String region;
    private String obligee;

    private static UserUtil mUserUtil;

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
        return "userid";
    }

    public String getRegion() {
        return "region";
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getObligee() {
        return obligee;
    }

    public void setObligee(String obligee) {
        this.obligee = obligee;
    }
}
