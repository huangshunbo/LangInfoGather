package com.android.memefish.langinfogather.http;

import android.os.Handler;
import android.os.Looper;

import com.android.memefish.langinfogather.http.bean.BaseBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public abstract class AbstractCallback<T> implements Callback {

    Gson gson = new Gson();
    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    public abstract void onSuccess(T t);

    public abstract void onFailure(Exception e);

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String responseString = response.body().string();
        final BaseBean bean = gson.fromJson(responseString, BaseBean.class);
        if (bean.isSuccess()) {
            JsonObject jsonObject = new JsonParser().parse(bean.getData()).getAsJsonObject();
//            Class clz = (Class<T>)(((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            if (jsonObject.get("BizSuccess").getAsBoolean()) {
                final T t = (T) gson.fromJson(jsonObject.get("BizData").getAsString(), (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(t);
                    }
                });
                return;
            }
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(new IllegalAccessException(bean.getResultCode() + "   " + bean.getResultErrMsg()));
            }
        });

    }

}
