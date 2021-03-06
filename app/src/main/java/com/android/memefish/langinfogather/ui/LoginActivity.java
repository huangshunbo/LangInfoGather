package com.android.memefish.langinfogather.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.presenter.LoginPresenter;
import com.android.memefish.langinfogather.ui.main.RegionMainActivity;
import com.android.memefish.langinfogather.util.UserUtil;
import com.android.minlib.smarttool.permission.PermissionCallback;
import com.android.minlib.smarttool.permission.SmartPermission;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener {

    AppCompatEditText etPhone;
    AppCompatEditText etPassword;
    TextView tvSubmit;
    private String[] PERMISSIONS_STORAGE = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    private void permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SmartPermission.getInstance()
                    .create(this)
                    .addPermission(PERMISSIONS_STORAGE)
                    .requestPermission(new PermissionCallback() {
                        @Override
                        public void onPermissionsResult(@NonNull List<String> allows, @NonNull List<String> refuses, boolean isAllAllow) {
                            if (isAllAllow) {
                                Log.d("hsb", "AllAllow");
                            }
                            if (allows.size() > 0) {
                                Log.d("hsb", "Allows : " + allows.toString());
                            }
                            if (refuses.size() > 0) {
                                Log.d("hsb", "Refuses : " + refuses.toString());
                            }

                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.activity_login_submit) {
//            ProvinceUtil.initJsonData(this);
            MobclickAgent.onProfileSignIn(UserUtil.getInstance().getUserId());
            startActivity(new Intent(this, RegionMainActivity.class));
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        //退出应用
    }
}
