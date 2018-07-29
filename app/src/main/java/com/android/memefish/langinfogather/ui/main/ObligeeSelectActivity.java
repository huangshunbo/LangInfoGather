package com.android.memefish.langinfogather.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ObligeeListBean;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.ui.widget.ObligeeItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObligeeSelectActivity extends BaseActivity implements View.OnClickListener {

    EditText etDoorNumber;
    LinearLayout llContent;
    Toolbar mToolBar;
    TextView tvSubmit;
    FloatingActionButton fab;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obligee_select);
        etDoorNumber = findViewById(R.id.activity_obligee_select_doornumber);
        llContent = findViewById(R.id.activity_obligee_select_content);
        mToolBar = findViewById(R.id.activity_obligee_select_toolbar);
        tvSubmit = findViewById(R.id.activity_obligee_select_submit);
        fab = findViewById(R.id.activity_obligee_select_fab);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSubmit.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.activity_obligee_select_submit){

            List<ObligeeListBean> beans = new ArrayList<>();
            for(int i=0;i<llContent.getChildCount();i++){
                ObligeeListBean bean = new ObligeeListBean();
                ObligeeItem item = (ObligeeItem) llContent.getChildAt(i);
                if(!TextUtils.isEmpty(item.getName()) && !TextUtils.isEmpty(item.getType())){
                    bean.setType(item.getType());
                    bean.setName(item.getName());
                    beans.add(bean);
                }
            }
            if(TextUtils.isEmpty(etDoorNumber.getText().toString()) || beans.size()<=0){
                Toast.makeText(this, "请填写完整后再保存", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("obligeeList", (Serializable) beans);
            intent.putExtra("doorNum",etDoorNumber.getText().toString());
            this.setResult(RESULT_OK, intent);
            this.finish();

        }else if(id == R.id.activity_obligee_select_fab){
            llContent.addView(new ObligeeItem(this,llContent));
        }
    }
}
