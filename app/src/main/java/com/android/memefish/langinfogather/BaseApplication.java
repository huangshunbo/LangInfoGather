package com.android.memefish.langinfogather;

import android.app.Application;
import android.util.Log;

import com.android.memefish.langinfogather.ocr.baidu.BaiduOrc;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;

public class BaseApplication extends Application {

    public static Application application;
    public static String token ;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initBaiduOcr();
    }

    private void initBaiduOcr() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                token = result.getAccessToken();
                Log.d("hsb","token " + token);
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                Log.d("hsb","token " + error.getMessage());
            }
        }, getApplicationContext(), BaiduOrc.API_KEY,BaiduOrc.SECRET_KEY);

    }
}
