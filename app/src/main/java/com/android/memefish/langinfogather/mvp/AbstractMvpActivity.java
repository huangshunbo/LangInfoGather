package com.android.memefish.langinfogather.mvp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * <br> ClassName:   AbstractMvpActivity
 * <br> Description: MVP架构 Activity基类
 * <br>
 * <br> Author:      liaoshengjian
 * <br> Date:        2017/8/1 15:55
 */
public abstract class AbstractMvpActivity<T extends BasePresenter>
        extends AppCompatActivity implements IMvpView {
    protected T mCurrentPresenter;

    /**
     *<br> Description: 创建Presenters
     *<br> Author:      liaoshengjian
     *<br> Date:        2017/5/24 17:10
     *
     * @return List presenterList
     */
    protected abstract T createPresenter();

    public T getCurrentPresenter() {
        return mCurrentPresenter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentPresenter = createPresenter();
        if (mCurrentPresenter != null) {
            mCurrentPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCurrentPresenter != null) {
            mCurrentPresenter.detachView();
        }
    }
}

