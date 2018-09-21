package com.android.memefish.langinfogather.ui.gather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ObligeeCountBean;
import com.android.memefish.langinfogather.bean.PictureShowBean;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.UserUtil;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * @author: huangshunbo
 * @Filename: GatherTypeActivity
 * @Description: 收集首页 权利人、房屋、权属来源、其他、图片调查表
 * @Copyright: Copyright (c) 2018 XXX Inc. All rights reserved.
 * @date: 2018/7/28 0028 22:11 
 */
public class GatherTypeActivity extends BaseActivity implements View.OnClickListener{

    Toolbar mToolbar;
    View llObligee,llHourse,llSource,llPaper,llOther,llQuestionNaire;
    AvatarImageView aivObligee,aivHourse,aivSource,aivPaper,aivOther;
    ObligeeCountBean bean;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gather_type);
        mToolbar = findViewById(R.id.activity_gather_type_toolbar);
        llObligee = findViewById(R.id.activity_gather_type_obligee);
        llHourse = findViewById(R.id.activity_gather_type_hourse);
        llSource = findViewById(R.id.activity_gather_type_source);
        llPaper = findViewById(R.id.activity_gather_type_paper);
        llOther = findViewById(R.id.activity_gather_type_other);
        llQuestionNaire = findViewById(R.id.activity_gather_type_questionnaire);

        aivObligee = findViewById(R.id.activity_gather_type_obligee_num);
        aivHourse = findViewById(R.id.activity_gather_type_hourse_num);
        aivSource = findViewById(R.id.activity_gather_type_source_num);
        aivPaper = findViewById(R.id.activity_gather_type_paper_num);
        aivOther = findViewById(R.id.activity_gather_type_other_num);


        mToolbar.setTitle(UserUtil.getInstance().getObligee());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        llObligee.setOnClickListener(this);
        llHourse.setOnClickListener(this);
        llSource.setOnClickListener(this);
        llPaper.setOnClickListener(this);
        llOther.setOnClickListener(this);
        llQuestionNaire.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bean = UserUtil.getInstance().getTags();
        if(bean != null && bean.getQuanliren() > 0) {
            aivObligee.setVisibility(View.VISIBLE);
            aivObligee.setTextAndColor(""+bean.getQuanliren(), ContextCompat.getColor(this,R.color.color1));
        }else{
            aivObligee.setVisibility(View.GONE);
        }
        if(bean != null && bean.getFangwu() > 0){
            aivHourse.setVisibility(View.VISIBLE);
            aivHourse.setTextAndColor(""+bean.getFangwu(),ContextCompat.getColor(this,R.color.color2));
        }else {
            aivHourse.setVisibility(View.GONE);
        }
        if(bean != null && bean.getQuanshulaiyuan() > 0){
            aivSource.setVisibility(View.VISIBLE);
            aivSource.setTextAndColor(""+bean.getQuanshulaiyuan(),ContextCompat.getColor(this,R.color.color3));
        }else {
            aivSource.setVisibility(View.GONE);
        }
        if(bean != null && bean.getTuzhi() > 0){
            aivPaper.setVisibility(View.VISIBLE);
            aivPaper.setTextAndColor(""+bean.getTuzhi(),ContextCompat.getColor(this,R.color.color4));
        }else {
            aivPaper.setVisibility(View.GONE);
        }
        if(bean != null && bean.getQita() > 0){
            aivOther.setVisibility(View.VISIBLE);
            aivOther.setTextAndColor(""+bean.getQita(),ContextCompat.getColor(this,R.color.color4));
        }else {
            aivOther.setVisibility(View.GONE);
        }
        UserUtil.getInstance().saveDataToSP();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.activity_gather_type_obligee){//权利人
            startActivity(new Intent(this,ObligeeTypeActivity.class));
        }else if(id == R.id.activity_gather_type_hourse){//房屋
            Intent intent2 = new Intent(this,PictureFileActivity.class);
            PictureShowBean bean2 = new PictureShowBean();
            bean2.setTitle("房屋");
            bean2.setOneLevel("房屋");
            bean2.setObligeeId(UserUtil.getInstance().getObligeeChildMainId());
            intent2.putExtra("pics",bean2);
            startActivity(intent2);
        }else if(id == R.id.activity_gather_type_source){//权属来源
            startActivity(new Intent(this,SourceTypeActivity.class));
        }else if(id == R.id.activity_gather_type_paper){//图纸
            // TODO: 2018/7/28 0028 其他
            Intent intent4 = new Intent(this,PictureFileActivity.class);
            PictureShowBean bean = new PictureShowBean();
            bean.setTitle("图纸");
            bean.setOneLevel("图纸");
            bean.setObligeeId(UserUtil.getInstance().getObligeeChildMainId());
            intent4.putExtra("pics",bean);
            intent4.putExtra("sortBase", (PictureUtil.TZ_SORT_BASE));
            startActivity(intent4);
        }else if(id == R.id.activity_gather_type_other){//其他
            // TODO: 2018/7/28 0028 其他
            Intent intent4 = new Intent(this,PictureFileActivity.class);
            PictureShowBean bean = new PictureShowBean();
            bean.setTitle("其他");
            bean.setOneLevel("其他");
            bean.setObligeeId(UserUtil.getInstance().getObligeeChildMainId());
            intent4.putExtra("pics",bean);
            intent4.putExtra("sortBase", (PictureUtil.QT_SORT_BASE));
            startActivity(intent4);
        }else if(id == R.id.activity_gather_type_questionnaire){//图片调查表
            Toast.makeText(GatherTypeActivity.this, "该功能暂时停用", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,QuestionNaireActivity.class));
        }
    }
}
