package com.android.memefish.langinfogather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.ocr.baidu.BaiduOrc;
import com.android.memefish.langinfogather.ocr.face.FaceOrc;
import com.android.memefish.langinfogather.presenter.LoginPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public void baiduSend(){
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/idcard.jpg");
        BaiduOrc.send(this,file);
    }

    public void faceSend(){
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/idcard.jpg");
        FaceOrc.send(file);
    }

    public void onClick(View view){
        int id = view.getId();
        if(id == R.id.activity_login_submit){
//            baiduSend();
            faceSend();
//            startActivity(new Intent(this,MainActivity.class));
//            finish();
        }
    }


    @Override
    public void onBackPressed() {
        //退出应用
    }
}
