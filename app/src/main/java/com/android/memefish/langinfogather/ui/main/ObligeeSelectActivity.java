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
import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.ObligeeChild;
import com.android.memefish.langinfogather.db.Picture;
import com.android.memefish.langinfogather.db.manager.ObligeeManager;
import com.android.memefish.langinfogather.db.manager.PictureManager;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.ui.widget.ObligeeItem;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.UserUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ObligeeSelectActivity extends BaseActivity implements View.OnClickListener {

    EditText etDoorNumber,etNum,etObligee;
    LinearLayout llContent;
    Toolbar mToolBar;
    TextView tvSubmit;
    FloatingActionButton fab;
    Obligee obligee = new Obligee();
    List<ObligeeChild> obligeeChildren = new ArrayList<>();

    Long ID = -1L;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obligee_select);

        ID = getIntent().getLongExtra("obligee",-1);

        etDoorNumber = findViewById(R.id.activity_obligee_select_doornumber);
        etNum = findViewById(R.id.activity_obligee_select_num);
        etObligee = findViewById(R.id.activity_obligee_select_obligee);
        llContent = findViewById(R.id.activity_obligee_select_content);
        mToolBar = findViewById(R.id.activity_obligee_select_toolbar);
        tvSubmit = findViewById(R.id.activity_obligee_select_submit);
        fab = findViewById(R.id.activity_obligee_select_fab);

        if(ID != -1){
            obligee = ObligeeManager.getObligee(ID);
            etDoorNumber.setText(obligee.getHouseNumber());
            etNum.setText(obligee.getNum());
            etObligee.setText(obligee.getName());
            obligeeChildren = ObligeeManager.listObligeeChild(ID);
            for(final ObligeeChild child : obligeeChildren){
                if(child.getProperty().equals("主权利人")){
                    etObligee.setText(child.getName());
                    etObligee.setTag(child);
                }else{
                    ObligeeItem item = new ObligeeItem(this,llContent);
                    item.setType(child.getProperty());
                    item.setName(child.getName());
                    llContent.addView(item);
                    item.setTag(child);
                    item.setOnDelListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<Picture> pictures = PictureManager.listPictureWithObligee(child.getId(),"权利人");
                            if(pictures.size() > 0){
                                File file = null;
                                for(Picture picture : pictures){
                                    file = new File(picture.getPath());
                                    if(file.isFile() && file.exists()){
                                        file.delete();
                                    }
                                }
                                PictureUtil.deleteDirWihtFile(file.getParentFile().getParentFile());
                            }

                            ObligeeManager.deleteObligeeChild(child.getId());
                            PictureManager.deletePicture(child.getId());
                        }
                    });
                }

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
            if(this.ID == -1){
                String num = etNum.getText().toString();
                List<Obligee> obligees = ObligeeManager.findObligee(num);
                if(obligees != null && obligees.size() > 0){
                    Toast.makeText(ObligeeSelectActivity.this,"已存在相同的预编码",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(TextUtils.isEmpty(etObligee.getText().toString())){
                Toast.makeText(ObligeeSelectActivity.this,"请填写主权利人",Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(etNum.getText().toString())){
                Toast.makeText(this, "请填写完整后再保存", Toast.LENGTH_SHORT).show();
                return;
            }

            obligee.setUser(UserUtil.getInstance().getUserId());
            obligee.setRegion(UserUtil.getInstance().getRegion());
            obligee.setNum(etNum.getText().toString());
            obligee.setHouseNumber(etDoorNumber.getText().toString());
            obligee.setName(etObligee.getText().toString());
            obligee.setStatus("未完成");
            obligee.setTime("2018-08-01 22:00");
            if(ID != -1){
                ObligeeManager.updateObligee(obligee);

                for(ObligeeChild child : obligeeChildren){
                    List<Picture> pics = PictureManager.listPictureWithObligee(child.getId());
                    for(Picture pic : pics){
                        if(pic.getOneLevel().equals("权利人")){
                            pic.setTwoLevel(child.getName());
                        }
                        PictureManager.insetPicture(pic);
                    }
                }
            }else {
                ID = ObligeeManager.insertObligee(obligee);
            }
            //将主权利人插入数据库
            ObligeeChild mainChild = new ObligeeChild();
            if(etObligee.getTag() != null && etObligee.getTag() instanceof ObligeeChild){
                mainChild = (ObligeeChild) etObligee.getTag();
            }
            mainChild.setParentId(ID);
            mainChild.setName(etObligee.getText().toString());
            mainChild.setProperty("主权利人");
            ObligeeManager.insertObligeeChild(mainChild);
            PictureManager.updatePictureQLR(UserUtil.getInstance().getUserId(),UserUtil.getInstance().getRegion(),mainChild.getId(),mainChild.getName());

            //将子权利人插入数据库
            for(int i=0;i<llContent.getChildCount();i++){
                ObligeeChild bean = new ObligeeChild();
                ObligeeItem item = (ObligeeItem) llContent.getChildAt(i);
                if(item.getTag() != null && item.getTag() instanceof ObligeeChild){
                    bean = (ObligeeChild) item.getTag();
                }
                if(!TextUtils.isEmpty(item.getName()) && !TextUtils.isEmpty(item.getType())){
                    bean.setProperty(item.getType());
                    bean.setName(item.getName());
                    bean.setParentId(ID);
                    ObligeeManager.insertObligeeChild(bean);
                    PictureManager.updatePictureQLR(UserUtil.getInstance().getUserId(),UserUtil.getInstance().getRegion(),bean.getId(),bean.getName());
                }
            }

            this.finish();

        }else if(id == R.id.activity_obligee_select_fab){
            llContent.addView(new ObligeeItem(this,llContent));
        }
    }
}
