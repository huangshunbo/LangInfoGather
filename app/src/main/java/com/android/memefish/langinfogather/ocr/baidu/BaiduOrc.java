package com.android.memefish.langinfogather.ocr.baidu;

import android.content.Context;
import android.util.Log;

import com.android.memefish.langinfogather.http.Smart;
import com.android.minlib.smarthttp.callback.StringCallback;
import com.android.minlib.smarthttp.exception.ApiException;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * @author: huangshunbo
 * @Filename: BaiduOrc
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/7/25 12:46
 * https://ai.baidu.com/docs#/OCR-Android-SDK/top
 *
 */
public class BaiduOrc {
    //设置APPID/AK/SK
    public static final String APP_ID = "11583101";
    public static final String API_KEY = "SIaKK2GZNZzR0sHPH4WrFZ6G";
    public static final String SECRET_KEY = "A56thVzOWioV7QEZ7FnvgxDlbl0BuYs9";

    public static void send(Context context,File file){
        IDCardParams param = new IDCardParams();
        param.setImageFile(file);
        param.setIdCardSide("front");
        OCR.getInstance(context).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                // 调用成功，返回IDCardResult对象
                Log.d("hsb","result" + result.getIdNumber());
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
                Log.d("hsb","error"+error.getLocalizedMessage());
            }
        });
    }
}
