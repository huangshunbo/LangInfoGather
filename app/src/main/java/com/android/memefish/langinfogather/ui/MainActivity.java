package com.android.memefish.langinfogather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.presenter.MainPresenter;
import com.android.memefish.langinfogather.ui.widget.MainTitleView;


public class MainActivity extends BaseActivity<MainPresenter>{

    private static final String TAG = "MainActivity";

    FloatingActionButton fabCreate;
    MainTitleView mMainTitleView;
    @Override
    protected MainPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabCreate = findViewById(R.id.activity_main_fab);
        mMainTitleView = findViewById(R.id.activity_main_titleview);
        initTitleView();
    }

    private void initTitleView() {
        mMainTitleView.setTitle("标题");
        mMainTitleView.setCenterClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UserCenterActivity.class));
            }
        });
        mMainTitleView.setSearchListener(new MainTitleView.OnSearchListener() {
            @Override
            public void onSearch(String key) {
                Log.d(TAG,"onSearch key = " + key);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //退出应用

    }
}
