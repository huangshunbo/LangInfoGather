package com.android.memefish.langinfogather.http;

import android.util.Log;

import com.android.memefish.langinfogather.BaseApplication;
import com.android.minlib.smarthttp.callback.AbstractCallBack;
import com.android.minlib.smarthttp.okhttp.SmartHttp;
import com.android.minlib.smarthttp.strategy.IURLStrategy;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Smart {
    private static OkHttpClient client = new OkHttpClient();


    public static void post(String fileBase64) {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("api_key","e_gkao_4z59UJ_ELCPfZJf2ThBD1NBdT");
        formBody.add("api_secret","ZPGIraKKqnf-qlmnP_3XVIj1ygW1jIVS");
        formBody.add("image_base64",fileBase64);
        Request request = new Request.Builder()
                .url("https://api-cn.faceplusplus.com/cardpp/v1/ocridcard")
                .post(formBody.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("hsb","failure " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("hsb","success "+response.body().toString());
            }
        });

    }
}
