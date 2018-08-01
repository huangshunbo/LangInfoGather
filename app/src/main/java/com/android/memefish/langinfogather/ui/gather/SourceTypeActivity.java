package com.android.memefish.langinfogather.ui.gather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.PictureShowBean;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;

public class SourceTypeActivity extends BaseActivity implements View.OnClickListener{

    Toolbar mToolbar;
    LinearLayout llLangcer,llLicence,llNotification,llHourse,llOther;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_type);

        mToolbar = findViewById(R.id.activity_source_type_toolbar);
        llLangcer = findViewById(R.id.activity_source_type_langcer);
        llLicence = findViewById(R.id.activity_source_type_licence);
        llNotification = findViewById(R.id.activity_source_type_notification);
        llHourse = findViewById(R.id.activity_source_type_hourse);
        llOther = findViewById(R.id.activity_source_type_other);

        mToolbar.setTitle("权属来源");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        llLangcer.setOnClickListener(this);
        llLicence.setOnClickListener(this);
        llNotification.setOnClickListener(this);
        llHourse.setOnClickListener(this);
        llOther.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent1 = new Intent(this,PictureFileActivity.class);
        PictureShowBean bean = new PictureShowBean();
        bean.setOneLevel("权属来源");
        if(id == R.id.activity_source_type_langcer){
            // TODO: 2018/7/28 0028 权属来源_土地证
            bean.setTitle("土地证");
            bean.setTwoLevel("土地证");
        }else if(id == R.id.activity_source_type_licence){
            // TODO: 2018/7/28 0028 权属来源_建设许可证
            bean.setTitle("建设许可证");
            bean.setTwoLevel("建设许可证");
        }else if(id == R.id.activity_source_type_notification){
            // TODO: 2018/7/28 0028 权属来源_临时用地通知
            bean.setTitle("临时用地通知");
            bean.setTwoLevel("临时用地通知");
        }else if(id == R.id.activity_source_type_hourse){
            // TODO: 2018/7/28 0028 权属来源_房产
            bean.setTitle("房产");
            bean.setTwoLevel("房产");
        }else if(id == R.id.activity_source_type_other){
            // TODO: 2018/7/28 0028 权属来源_其他
            bean.setTitle("其他");
            bean.setTwoLevel("其他");
        }
        intent1.putExtra("pics",bean);
        startActivity(intent1);
    }
}
