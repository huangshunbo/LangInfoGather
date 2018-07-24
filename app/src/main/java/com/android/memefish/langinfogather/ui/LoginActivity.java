package com.android.memefish.langinfogather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter> {

    TextInputEditText etPhone;
    TextInputEditText etPassword;

    @Override
    protected LoginPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPhone = findViewById(R.id.activity_login_phone);
        etPassword = findViewById(R.id.activity_login_password);
    }

    public void onClick(View view){
        int id = view.getId();
        if(id == R.id.activity_login_submit){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        //退出应用
    }
}
