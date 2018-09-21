package com.android.memefish.langinfogather.ui.gather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.PictureShowBean;
import com.android.memefish.langinfogather.db.manager.PictureManager;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.UserUtil;

import java.util.HashMap;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

public class SourceTypeActivity extends BaseActivity implements View.OnClickListener{

    Toolbar mToolbar;
    View llLangcer,llLicence,llNotification,llHourse,llOther;
    AvatarImageView aivLangcer,aivLicence,aivNotification,aivHourse,aivOther;
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

        aivLangcer = findViewById(R.id.activity_source_type_langcer_num);
        aivLicence = findViewById(R.id.activity_source_type_licence_num);
        aivNotification = findViewById(R.id.activity_source_type_notification_num);
        aivHourse = findViewById(R.id.activity_source_type_hourse_num);
        aivOther = findViewById(R.id.activity_source_type_other_num);

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
    protected void onResume() {
        super.onResume();
        HashMap<String,Integer> counts = PictureManager.countQuanshulaiyuan();
        int count = 0;
        if(counts.get("土地证") != null && (count = counts.get("土地证")) != 0){
            aivLangcer.setVisibility(View.VISIBLE);
            aivLangcer.setTextAndColor(""+count, ContextCompat.getColor(this,R.color.color6));
        }else{
            aivLangcer.setVisibility(View.GONE);
        }
        if(counts.get("建设许可证") != null && (count = counts.get("建设许可证")) != 0){
            aivLicence.setVisibility(View.VISIBLE);
            aivLicence.setTextAndColor(""+count,ContextCompat.getColor(this,R.color.color1));
        }else {
            aivLicence.setVisibility(View.GONE);
        }
        if(counts.get("临时用地通知") != null && (count = counts.get("临时用地通知")) != 0){
            aivNotification.setVisibility(View.VISIBLE);
            aivNotification.setTextAndColor(""+count,ContextCompat.getColor(this,R.color.color3));
        }else {
            aivNotification.setVisibility(View.GONE);
        }
        if(counts.get("房产") != null && (count = counts.get("房产")) != 0){
            aivHourse.setVisibility(View.VISIBLE);
            aivHourse.setTextAndColor(""+count,ContextCompat.getColor(this,R.color.color2));
        }else {
            aivHourse.setVisibility(View.GONE);
        }
        if(counts.get("其他") != null && (count = counts.get("其他")) != 0){
            aivOther.setVisibility(View.VISIBLE);
            aivOther.setTextAndColor(""+count,ContextCompat.getColor(this,R.color.color4));
        }else {
            aivOther.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent1 = new Intent(this,PictureFileActivity.class);
        PictureShowBean bean = new PictureShowBean();
        bean.setOneLevel("权属来源");
        bean.setObligeeId(UserUtil.getInstance().getObligeeChildMainId());
        if(id == R.id.activity_source_type_langcer){
            // TODO: 2018/7/28 0028 权属来源_土地证
            bean.setTitle("土地证");
            bean.setTwoLevel("土地证");
            intent1.putExtra("sortBase", (PictureUtil.QSLY_SORT_BASE + 1000));
        }else if(id == R.id.activity_source_type_licence){
            // TODO: 2018/7/28 0028 权属来源_建设许可证
            bean.setTitle("建设许可证");
            bean.setTwoLevel("建设许可证");
            intent1.putExtra("sortBase", (PictureUtil.QSLY_SORT_BASE + 2000));
        }else if(id == R.id.activity_source_type_notification){
            // TODO: 2018/7/28 0028 权属来源_临时用地通知
            bean.setTitle("临时用地通知");
            bean.setTwoLevel("临时用地通知");
            intent1.putExtra("sortBase", (PictureUtil.QSLY_SORT_BASE + 3000));
        }else if(id == R.id.activity_source_type_hourse){
            // TODO: 2018/7/28 0028 权属来源_房产
            bean.setTitle("房产");
            bean.setTwoLevel("房产");
            intent1.putExtra("sortBase", (PictureUtil.QSLY_SORT_BASE + 4000));
        }else if(id == R.id.activity_source_type_other){
            // TODO: 2018/7/28 0028 权属来源_其他
            bean.setTitle("其他");
            bean.setTwoLevel("其他");
            intent1.putExtra("sortBase", (PictureUtil.QSLY_SORT_BASE + 5000));
        }
        intent1.putExtra("pics",bean);
        startActivity(intent1);
    }
}
