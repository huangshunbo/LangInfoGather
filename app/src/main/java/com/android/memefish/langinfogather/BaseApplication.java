package com.android.memefish.langinfogather;

import android.app.Application;

import com.android.memefish.langinfogather.ocr.baidu.BaiduOrc;
import com.android.memefish.langinfogather.util.UserUtil;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class BaseApplication extends Application {

    public static Application application;
    public static String token ;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initBaiduOcr();
        initBugly();
        initYoumeng();
    }

    private void initBaiduOcr() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                token = result.getAccessToken();
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
            }
        }, getApplicationContext(), BaiduOrc.API_KEY,BaiduOrc.SECRET_KEY);

    }

    private void initBugly(){
        CrashReport.initCrashReport(getApplicationContext(), "0beb3c5b6f", false);
    }

    private void initYoumeng(){
        /*
注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
*/
        UMConfigure.init(this,"5b61bc73a40fa362790003d1","self",1, null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);


    }
}
