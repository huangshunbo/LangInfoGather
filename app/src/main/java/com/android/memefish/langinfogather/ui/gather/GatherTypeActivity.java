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
import com.android.memefish.langinfogather.util.UserUtil;

import java.util.Arrays;

/**
 * @author: huangshunbo
 * @Filename: GatherTypeActivity
 * @Description: 收集首页 权利人、房屋、权属来源、其他、图片调查表
 * @Copyright: Copyright (c) 2018 XXX Inc. All rights reserved.
 * @date: 2018/7/28 0028 22:11 
 */
public class GatherTypeActivity extends BaseActivity implements View.OnClickListener{

    Toolbar mToolbar;
    LinearLayout llObligee,llHourse,llSource,llOther,llQuestionNaire;

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
        llOther = findViewById(R.id.activity_gather_type_other);
        llQuestionNaire = findViewById(R.id.activity_gather_type_questionnaire);

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
        llOther.setOnClickListener(this);
        llQuestionNaire.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.activity_gather_type_obligee){//权利人
            startActivity(new Intent(this,ObligeeTypeActivity.class));
        }else if(id == R.id.activity_gather_type_hourse){//房屋
            Intent intent2 = new Intent(this,PictureDirActivity.class);
            PictureShowBean bean2 = new PictureShowBean();
            bean2.setTitle("房屋");
            bean2.setOneLevel("房屋");
            bean2.setDirs(Arrays.asList("FW001","FW002","FW003","FW004","其他照片"));
            intent2.putExtra("pics",bean2);
            startActivity(intent2);
        }else if(id == R.id.activity_gather_type_source){//权属来源
            startActivity(new Intent(this,SourceTypeActivity.class));
        }else if(id == R.id.activity_gather_type_other){//其他
            // TODO: 2018/7/28 0028 其他
            Intent intent4 = new Intent(this,PictureFileActivity.class);
            PictureShowBean bean = new PictureShowBean();
            bean.setTitle("其他");
            bean.setOneLevel("其他");
            intent4.putExtra("pics",bean);
            startActivity(intent4);
        }else if(id == R.id.activity_gather_type_questionnaire){//图片调查表
            startActivity(new Intent(this,QuestionNaireActivity.class));
        }
    }
}
