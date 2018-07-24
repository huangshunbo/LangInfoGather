package com.android.memefish.langinfogather.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.memefish.langinfogather.R;

/**
 * @author: huangshunbo
 * @Filename: MainTitleView
 * @Description: 标题栏
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/7/24 17:44 
 */
public class MainTitleView extends FrameLayout {


    public MainTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_main_title,this);
    }

}
