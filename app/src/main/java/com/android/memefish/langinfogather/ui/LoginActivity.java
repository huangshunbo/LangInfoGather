package com.android.memefish.langinfogather.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.memefish.langinfogather.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.activity_login_phone)
    TextInputEditText etPhone;
    @BindView(R.id.activity_login_password)
    TextInputEditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.activity_login_submit)
    public void onClick(View view){
        int id = view.getId();
        if(id == R.id.activity_login_submit){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

}
