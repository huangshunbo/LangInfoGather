package com.android.memefish.langinfogather.ocr;

import android.content.Context;

import com.android.memefish.langinfogather.ocr.baidu.BaiduOrc;
import com.android.memefish.langinfogather.ocr.face.FaceOrc;

import java.io.File;

public class OcrManager {

    private static final int ORC_BAIDU = 1;
    private static final int ORC_FACE = 2;
    private static int ORC_CHANNEL = ORC_BAIDU;

    public static void send(Context context, File file, final OnOcrListener listener){
        if(ORC_CHANNEL == ORC_BAIDU){
            BaiduOrc.send(context, file, new OnOcrListener() {
                @Override
                public void onFinish(ORCBean bean) {
                    if(listener != null){
                        listener.onFinish(bean);
                    }
                }
            });
        }else if(ORC_CHANNEL == ORC_FACE){
            try {
                FaceOrc.send(file, new OnOcrListener() {
                    @Override
                    public void onFinish(ORCBean bean) {
                        if(listener != null){
                            listener.onFinish(bean);
                        }
                    }
                });
            } catch (Exception e) {
                ORC_CHANNEL = ORC_BAIDU;
                if(listener != null){
                    ORCBean bean = new ORCBean();
                    bean.setErrorMessage(e.getMessage());
                    listener.onFinish(bean);
                }
            }
        }

    }
}
