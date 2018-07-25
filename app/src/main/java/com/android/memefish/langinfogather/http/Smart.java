package com.android.memefish.langinfogather.http;

import com.android.memefish.langinfogather.BaseApplication;
import com.android.memefish.langinfogather.ui.MainActivity;
import com.android.minlib.smarthttp.callback.AbstractCallBack;
import com.android.minlib.smarthttp.okhttp.SmartHttp;
import com.android.minlib.smarthttp.strategy.IURLStrategy;
import com.google.gson.Gson;

import java.util.HashMap;

public class Smart {
    private static final SmartHttp smartHttp = new SmartHttp(BaseApplication.application);

    static  {
        smartHttp.setCacheTime(1000 * 60 * 60);
        smartHttp.setHeaders(new HashMap());
        smartHttp.setURLStrategy(new IURLStrategy() {
            @Override
            public boolean isHttps() {
                return true;
            }

            @Override
            public String getDomains() {
                return "api-cn.faceplusplus.com/cardpp/v1/";
//                return "imtt.dd.qq.com/16891/5F46E5DD09F7478B7C1B96EEA48CA221.apk";
//                return "imtt.dd.qq.com/16891/77FCEF50B7D08DC3322DDC2F1DF54BAF.apk";
            }

        });

    }

    public static void post(String methodName, HashMap<String, String> params, AbstractCallBack abstractCallBack) {
        String paramStr = params != null ? new Gson().toJson(params) : "";
        smartHttp.post(methodName)
                .setRequestJson(paramStr)
                .buildRequest()
                .execute(abstractCallBack);

    }
}
