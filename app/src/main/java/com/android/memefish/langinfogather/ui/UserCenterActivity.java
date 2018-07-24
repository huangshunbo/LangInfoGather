package com.android.memefish.langinfogather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;

public class UserCenterActivity extends BaseActivity implements View.OnClickListener{

    TextView tvPhone,tvAreaCount;
    TextView tvArea,tvChangePwd,tvLogout;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);
        tvPhone = findViewById(R.id.activity_user_phone);
        tvAreaCount = findViewById(R.id.activity_user_areacount);
        tvArea = findViewById(R.id.activity_user_changearea);
        tvChangePwd = findViewById(R.id.activity_user_changepwd);
        tvLogout = findViewById(R.id.activity_user_logout);

        tvArea.setOnClickListener(this);
        tvChangePwd.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.activity_user_changearea){
            startActivity(new Intent(this,MainActivity.class));
        }else if(id == R.id.activity_user_changepwd){

        }else if(id == R.id.activity_user_logout){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
