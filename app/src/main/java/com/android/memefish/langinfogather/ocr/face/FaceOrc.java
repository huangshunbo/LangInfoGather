package com.android.memefish.langinfogather.ocr.face;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Base64;
import android.util.Log;

import com.android.memefish.langinfogather.http.Smart;
import com.android.minlib.smarthttp.callback.StringCallback;
import com.android.minlib.smarthttp.exception.ApiException;
import com.android.minlib.smarthttp.okhttp.SmartHttp;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.sdk.utils.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author: huangshunbo
 * @Filename: FaceOrc
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/7/25 12:46
 * https://console.faceplusplus.com.cn/documents/5671702
 */
public class FaceOrc {
    public static final String API_KEY = "e_gkao_4z59UJ_ELCPfZJf2ThBD1NBdT";
    public static final String SECRET_KEY = "ZPGIraKKqnf-qlmnP_3XVIj1ygW1jIVS";

    public static void send(File file){
        //https://api-cn.faceplusplus.com/cardpp/v1/ocridcard
        HashMap<String,String> params = new HashMap<>();
//        params.put("api_key",API_KEY);
//        params.put("api_secret",SECRET_KEY);
        params.put("image_base64",base64ForBitmap(BitmapFactory.decodeFile(file.getAbsolutePath())));

        Smart.post("ocridcard", params, new StringCallback() {
            @Override
            public void onSuccess(Object o) {
                Log.d("hsb","result " + o.toString());
            }

            @Override
            public void onFailure(ApiException e) {
                Log.d("hsb","result "+e.getMessage());
            }
        });
    }

    private static String base64ForBitmap(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Bitmap.CompressFormat cf = Bitmap.CompressFormat.JPEG;
        bitmap.compress(cf, 90, out);
        try {
            out.flush();
            out.close();
        } catch (IOException var11) {
            var11.printStackTrace();
        }
        return Base64.encodeToString(out.toByteArray(), 0);
    }
}
