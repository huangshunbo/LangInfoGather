package com.android.memefish.langinfogather.ocr.face;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.android.memefish.langinfogather.ocr.ORCBean;
import com.android.memefish.langinfogather.ocr.OnOcrListener;

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
    private static final String URL = "https://api-cn.faceplusplus.com/cardpp/v1/ocridcard";

    public static void send(File file, OnOcrListener listener) throws Exception {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("image_base64",base64ForBitmap(BitmapFactory.decodeFile(file.getAbsolutePath())));
//
//        Smart.post(base64ForBitmap(BitmapFactory.decodeFile(file.getAbsolutePath())));

        //获取正面身份证验证信息
        HashMap<String, String> faceMap = new HashMap<String, String>();
        faceMap.put("api_key", API_KEY);
        faceMap.put("api_secret", SECRET_KEY);
        faceMap.put("image_base64", base64ForBitmap(BitmapFactory.decodeFile(file.getAbsolutePath())));
        String frontResult = new String(FaceIdentity.post(URL, faceMap), "UTF-8");
        if(listener != null){
            ORCBean bean = new ORCBean();
            bean.setIdNum(frontResult);
            listener.onFinish(bean);
        }
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
