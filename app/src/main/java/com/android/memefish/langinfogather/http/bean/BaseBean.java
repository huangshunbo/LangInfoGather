package com.android.memefish.langinfogather.http.bean;

import android.text.TextUtils;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public class BaseBean {

    private String ApiVer;
    private String Sign;
    private String Random;
    private String ResultCode;
    private String ResultErrMsg;
    private String Token;
    private String Data;

    public String getApiVer() {
        return ApiVer;
    }

    public void setApiVer(String apiVer) {
        ApiVer = apiVer;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getRandom() {
        return Random;
    }

    public void setRandom(String random) {
        Random = random;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultErrMsg() {
        return ResultErrMsg;
    }

    public void setResultErrMsg(String resultErrMsg) {
        ResultErrMsg = resultErrMsg;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public boolean isSuccess(){
        return TextUtils.equals(ResultCode,"1000");
    }
}
