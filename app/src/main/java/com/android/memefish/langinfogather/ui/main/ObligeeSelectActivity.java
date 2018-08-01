package com.android.memefish.langinfogather.ui.main;

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
import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.ObligeeManager;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.ui.widget.ObligeeItem;
import com.android.memefish.langinfogather.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class ObligeeSelectActivity extends BaseActivity implements View.OnClickListener {

    EditText etDoorNumber,etNum;
    LinearLayout llContent;
    Toolbar mToolBar;
    TextView tvSubmit;
    FloatingActionButton fab;
    Obligee obligee = new Obligee();
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obligee_select);

        Long id = getIntent().getLongExtra("obligee",-1);


        etDoorNumber = findViewById(R.id.activity_obligee_select_doornumber);
        etNum = findViewById(R.id.activity_obligee_select_num);
        llContent = findViewById(R.id.activity_obligee_select_content);
        mToolBar = findViewById(R.id.activity_obligee_select_toolbar);
        tvSubmit = findViewById(R.id.activity_obligee_select_submit);
        fab = findViewById(R.id.activity_obligee_select_fab);

        if(id != -1){
            obligee = ObligeeManager.getObligee(id);
            etDoorNumber.setText(obligee.getHouseNumber());
            etNum.setText(obligee.getNum());
            String[] names = obligee.getNames().split(",");
            String[] pros = obligee.getPropertys().split(",");
            for(int i=0;i<names.length;i++){
                ObligeeItem item = new ObligeeItem(this,llContent);
                item.setType(pros[i]);
                item.setName(names[i]);
                llContent.addView(item);
            }
        };

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

            StringBuffer name = new StringBuffer();
            StringBuffer pro = new StringBuffer();
            for(ObligeeListBean bean : beans){
                name.append(bean.getName() + ",");
                pro.append(bean.getType() + ",");
            }
            name.deleteCharAt(name.length()-1);
            pro.deleteCharAt(pro.length()-1);

            obligee.setUser(UserUtil.getInstance().getUserId());
            obligee.setRegion(UserUtil.getInstance().getRegion());
            obligee.setNum(etNum.getText().toString());
            obligee.setHouseNumber(etDoorNumber.getText().toString());
            obligee.setNames(name.toString());
            obligee.setPropertys(pro.toString());
            obligee.setStatus("未完成");
            obligee.setTime("2018-08-01 22:00");
            ObligeeManager.insertObligee(obligee);

            this.finish();

        }else if(id == R.id.activity_obligee_select_fab){
            llContent.addView(new ObligeeItem(this,llContent));
        }
    }
}
