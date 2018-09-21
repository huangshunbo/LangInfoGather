package com.android.memefish.langinfogather.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.WindowManager;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.presenter.MainPresenter;
import com.android.memefish.langinfogather.ui.UserCenterActivity;
import com.android.memefish.langinfogather.ui.widget.MainTitleView;
import com.android.minlib.smartrefreshlayout.recycler.SmartRecyclerView;


public abstract class MainBaseActivity extends BaseActivity<MainPresenter>{

    private static final String TAG = "MainBaseActivity";

    FloatingActionButton fabCreate;
    MainTitleView mMainTitleView;
    SmartRecyclerView mSmartRecyclerView;
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
        mSmartRecyclerView = findViewById(R.id.activity_main_list);
        initTitleView();

        mSmartRecyclerView.setDiver(5, R.drawable.line_left_margin);
        mSmartRecyclerView.setMode(SmartRecyclerView.STATE_MODE.REFRESH);
        onInitList();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSmartRecyclerView.loadData();
    }

    abstract void onInitList();

    private void initTitleView() {
        mMainTitleView.setCenterClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainBaseActivity.this,UserCenterActivity.class));
            }
        });

    }

}
