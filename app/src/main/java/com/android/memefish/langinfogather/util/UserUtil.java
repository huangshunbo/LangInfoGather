package com.android.memefish.langinfogather.util;

public class UserUtil {

    private String userId;

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
        return "1";
    }

}
