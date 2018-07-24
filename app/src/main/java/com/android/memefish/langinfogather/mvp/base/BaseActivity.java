package com.android.memefish.langinfogather.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.memefish.langinfogather.mvp.AbstractMvpActivity;
import com.android.memefish.langinfogather.mvp.BasePresenter;

public abstract class BaseActivity<T extends BasePresenter> extends AbstractEmotionActivity<T>{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
