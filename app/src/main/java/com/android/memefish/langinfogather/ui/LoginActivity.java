package com.android.memefish.langinfogather.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.presenter.LoginPresenter;
import com.android.memefish.langinfogather.ui.main.MainBaseActivity;
import com.android.memefish.langinfogather.ui.main.ObligeeMainActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener{

    TextInputEditText etPhone;
    TextInputEditText etPassword;
    TextView tvSubmit;

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
        tvSubmit = findViewById(R.id.activity_login_submit);

        tvSubmit.setOnClickListener(this);
        permission();
    }

    private void permission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},999);
            }
        }
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        if(id == R.id.activity_login_submit){
            startActivity(new Intent(this,ObligeeMainActivity.class));
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        //退出应用
    }
}
