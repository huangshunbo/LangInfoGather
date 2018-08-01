package com.android.memefish.langinfogather.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity<T extends BasePresenter> extends AbstractEmotionActivity<T>{

    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getLocalClassName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG); //手动统计页面("SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading(String tips) {

    }

    @Override
    public void dismissLoading() {

    }
}
