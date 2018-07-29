package com.android.memefish.langinfogather.ui.gather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.PictureShowBean;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PictureBaseActivity extends BaseActivity {
    List<String> files = new ArrayList<>();

    PictureShowBean pictureShowBean;
    Toolbar mToolbar;
    GridView mGridView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getSerializableExtra("pics") == null){
            throw new NullPointerException("必须传入pics数据");
        }
        pictureShowBean = (PictureShowBean) getIntent().getSerializableExtra("pics");
        setContentView(R.layout.activity_picture_base);
        mToolbar = findViewById(R.id.activity_picture_base_toolbar);
        mGridView = findViewById(R.id.activity_picture_base_gridview);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}