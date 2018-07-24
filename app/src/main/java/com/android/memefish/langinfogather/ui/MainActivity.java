package com.android.memefish.langinfogather.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter>{


    FloatingActionButton fabCreate;
    Toolbar mToolbar;
    @Override
    protected MainPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabCreate = findViewById(R.id.activity_main_fab);
        mToolbar = findViewById(R.id.activity_main_toolbar);

        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle("Title");
        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
    }
}
