package com.android.memefish.langinfogather.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.ObligeeChild;
import com.android.memefish.langinfogather.db.Picture;
import com.android.memefish.langinfogather.db.manager.ObligeeManager;
import com.android.memefish.langinfogather.db.manager.PictureManager;
import com.android.memefish.langinfogather.http.AbstractCallback;
import com.android.memefish.langinfogather.http.Smart;
import com.android.memefish.langinfogather.http.bean.ObligeeBean;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.ui.widget.ObligeeItem;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.UserUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ObligeeSelectActivity extends BaseActivity implements View.OnClickListener {

    EditText etDoorNumber, etNum, etObligee;
    LinearLayout llContent;
    Toolbar mToolBar;
    TextView tvSubmit;
    FloatingActionButton fab;
    ObligeeBean obligee = null;
    List<ObligeeBean.Obligee> obligeeChildren = new ArrayList<>();

    boolean isAdd = false;
    int maxNum = 2;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obligee_select);

        obligee = (ObligeeBean) getIntent().getSerializableExtra("obligee");

        etDoorNumber = findViewById(R.id.activity_obligee_select_doornumber);
        etNum = findViewById(R.id.activity_obligee_select_num);
        etObligee = findViewById(R.id.activity_obligee_select_obligee);
        llContent = findViewById(R.id.activity_obligee_select_content);
        mToolBar = findViewById(R.id.activity_obligee_select_toolbar);
        tvSubmit = findViewById(R.id.activity_obligee_select_submit);
        fab = findViewById(R.id.activity_obligee_select_fab);

        if (obligee != null) {
            isAdd = false;
            etDoorNumber.setText(obligee.getDoorNumber());
            etNum.setText(obligee.getPrenumbering());
            etObligee.setText(obligee.getQLRMC());
            if (obligee.getQlrlist() != null && obligee.getQlrlist().size() > 0) {
                for (final ObligeeBean.Obligee child : obligee.getQlrlist()) {
                    maxNum = maxNum > Integer.parseInt(child.getQLRLX()) ? maxNum : Integer.parseInt(child.getQLRLX());
                    ObligeeItem item = new ObligeeItem(this, llContent);
                    item.setType(child.getQLRLXMC());
                    item.setName(child.getQLRMC());
                    llContent.addView(item);
                    item.setTag(child.getQLRLX());
                    item.setOnDelListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            List<Picture> pictures = PictureManager.listPictureWithObligee(child.getQLRID(), "权利人");
//                            if (pictures.size() > 0) {
//                                File file = null;
//                                for (Picture picture : pictures) {
//                                    file = new File(picture.getPath());
//                                    if (file.isFile() && file.exists()) {
//                                        file.delete();
//                                    }
//                                }
//                                PictureUtil.deleteDirWihtFile(file.getParentFile().getParentFile());
//                            }
//
//                            ObligeeManager.deleteObligeeChild(child.getId());
//                            PictureManager.deletePicture(child.getId());
                        }
                    });
                }
            }
        } else {
            isAdd = true;
            obligee = new ObligeeBean();
        }
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
        if (id == R.id.activity_obligee_select_submit) {
            if (TextUtils.isEmpty(etObligee.getText().toString())) {
                Toast.makeText(ObligeeSelectActivity.this, "请填写主权利人", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(etNum.getText().toString())) {
                Toast.makeText(this, "请填写完整后再保存", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < llContent.getChildCount(); i++) {
                ObligeeItem item = (ObligeeItem) llContent.getChildAt(i);
                if (TextUtils.isEmpty(item.getName())) {
                    Toast.makeText(this, "请将子权利人填写完整后再保存", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            for (int i = 0; i < llContent.getChildCount(); i++) {
                ObligeeItem item = (ObligeeItem) llContent.getChildAt(i);
                if (item.getTag() != null) {
                    String QLRLX = (String) item.getTag();
                    for(ObligeeBean.Obligee ob : obligee.getQlrlist()){
                        if(TextUtils.equals(ob.getQLRLX(),QLRLX)){
                            ob.setQLRMC(item.getName());
                            ob.setQLRLXMC(item.getType());
                            ob.setQLRType("2");
                            continue;
                        }
                    }
                }
            }

            ObligeeBean.Obligee ob = new ObligeeBean.Obligee();
            ob.setQLRMC(etObligee.getText().toString());
            ob.setQLRLX("1");
            ob.setQLRLXMC("主权利人");
            ob.setQLRType("1");

            obligee.setDoorNumber(etDoorNumber.getText().toString());
            obligee.setPrenumbering(etNum.getText().toString());
            obligee.setQLRMC(etObligee.getText().toString());
            if (isAdd) {
                obligee.setAddTime(DateFormat.format("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()).toString());
            }
            Smart.addOrReplaceObligee(isAdd, obligee, new AbstractCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(ObligeeSelectActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(ObligeeSelectActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                }
            });
//            //将主权利人插入数据库
//            ObligeeChild mainChild = new ObligeeChild();
//            if (etObligee.getTag() != null && etObligee.getTag() instanceof ObligeeChild) {
//                mainChild = (ObligeeChild) etObligee.getTag();
//            }
//            mainChild.setParentId(ID);
//            mainChild.setName(etObligee.getText().toString());
//            mainChild.setProperty("主权利人");
//            ObligeeManager.insertObligeeChild(mainChild);
//            PictureManager.updatePictureQLR(UserUtil.getInstance().getUserId(), UserUtil.getInstance().getRegion(), mainChild.getId(), mainChild.getName());
//
//            //将子权利人插入数据库
//            for (int i = 0; i < llContent.getChildCount(); i++) {
//                ObligeeChild bean = new ObligeeChild();
//                ObligeeItem item = (ObligeeItem) llContent.getChildAt(i);
//                if (item.getTag() != null && item.getTag() instanceof ObligeeChild) {
//                    bean = (ObligeeChild) item.getTag();
//                }
//                if (!TextUtils.isEmpty(item.getName()) && !TextUtils.isEmpty(item.getType())) {
//                    bean.setProperty(item.getType());
//                    bean.setName(item.getName());
//                    bean.setParentId(ID);
//                    ObligeeManager.insertObligeeChild(bean);
//                    PictureManager.updatePictureQLR(UserUtil.getInstance().getUserId(), UserUtil.getInstance().getRegion(), bean.getId(), bean.getName());
//                }
//            }

            this.finish();

        } else if (id == R.id.activity_obligee_select_fab) {
            ObligeeItem item = new ObligeeItem(this, llContent);
            ObligeeBean.Obligee ob = new ObligeeBean.Obligee();
            ob.setQLRLX(""+(++maxNum));
            item.setTag(ob.getQLRLX());
            if(obligee.getQlrlist() == null){
                obligee.setQlrlist(new ArrayList<ObligeeBean.Obligee>());
            }
            obligee.getQlrlist().add(ob);
            llContent.addView(item);
        }
    }
}
