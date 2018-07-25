package com.android.memefish.langinfogather.http;

import com.android.memefish.langinfogather.BaseApplication;
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
