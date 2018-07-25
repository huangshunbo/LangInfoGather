package com.android.memefish.langinfogather.util;

import android.content.Context;
import android.view.WindowManager;

import com.android.memefish.langinfogather.BaseApplication;

public class ScreenUtil {

    private static int WIDTH = 0;
    private static int HEIGH = 0;

    public static int getScreenWidth(){
        if(WIDTH <= 0){
            measure();
        }
        return WIDTH;
    }

    public static int getScreenHeigh(){
        if(HEIGH <= 0){
            measure();
        }
        return HEIGH;
    }

    private static void measure(){
        WindowManager wm = (WindowManager) BaseApplication.application
                .getSystemService(Context.WINDOW_SERVICE);
        WIDTH = wm.getDefaultDisplay().getWidth();
        HEIGH = wm.getDefaultDisplay().getHeight();
    }
}
