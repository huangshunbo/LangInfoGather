package com.android.memefish.langinfogather.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ProvinceBean;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.util.ProvinceUtil;
import com.android.memefish.langinfogather.util.ScreenUtil;

import java.util.ArrayList;

public class RegionSelectActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    TextView tvProvince,tvCity,tvArea;
    EditText etAddr,etAddrDetail,etVillage;
    Toolbar mToolBar;
    TextView tvSubmit;

    private static ArrayList<String> options1Items = new ArrayList<>();//省
    private static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区

    private static int options1 = 0;
    private static int options2 = 0;
    private static int options3 = 0;
    //0 省 1市 2区
    private int isNowShowWhat = 0;

    private ListPopupWindow mListPopupWindow;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select);

        tvProvince = findViewById(R.id.activity_region_select_province);
        tvCity = findViewById(R.id.activity_region_select_city);
        tvArea = findViewById(R.id.activity_region_select_area);
        etAddr = findViewById(R.id.activity_region_select_addr);
        etAddrDetail = findViewById(R.id.activity_region_select_addr_detail);
        etVillage = findViewById(R.id.activity_region_select_et_village);
        mToolBar = findViewById(R.id.activity_region_select_toolbar);
        tvSubmit = findViewById(R.id.activity_region_select_submit);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSubmit.setOnClickListener(this);
        tvProvince.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        tvArea.setOnClickListener(this);

        if(options1Items.size() <= 0 || options2Items.size() <= 0 || options3Items.size() <= 0){
            ProvinceUtil.initJsonData(this,options1Items,options2Items,options3Items);
        }

        refreshProvince();

        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setDropDownGravity(Gravity.BOTTOM);
        mListPopupWindow.setAnchorView(getWindow().getDecorView());
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mListPopupWindow.setHeight(ScreenUtil.getScreenHeigh()/2);
        mListPopupWindow.setOnItemClickListener(this);

    }

    private void refreshProvince(){
        tvProvince.setText(options1Items.get(options1));
        tvCity.setText(options2Items.get(options1).get(options2));
        tvArea.setText(options3Items.get(options1).get(options2).get(options3));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.activity_region_select_province){
            isNowShowWhat = 0;
            mListPopupWindow.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, options1Items));
            mListPopupWindow.show();
        }else if(id == R.id.activity_region_select_city){
            isNowShowWhat = 1;
            mListPopupWindow.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, options2Items.get(options1)));
            mListPopupWindow.show();
        }else if(id == R.id.activity_region_select_area){
            isNowShowWhat = 2;
            mListPopupWindow.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, options3Items.get(options1).get(options2)));
            mListPopupWindow.show();
        }else if(id == R.id.activity_region_select_submit){
            String addr = etAddr.getText().toString();
            String addrDetail = etAddrDetail.getText().toString();
            String village = etVillage.getText().toString();
            if(TextUtils.isEmpty(addr) || TextUtils.isEmpty(addrDetail) || TextUtils.isEmpty(village)){
                Toast.makeText(this, "请填写完整后再提交", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            ProvinceBean bean = new ProvinceBean();
            bean.setProvince(options1Items.get(options1));
            bean.setCity(options2Items.get(options1).get(options2));
            bean.setArea(options3Items.get(options1).get(options2).get(options3));
            bean.setAddr(addr);
            bean.setAddrDetail(addrDetail);
            bean.setVillage(village);
            intent.putExtra("province",bean);
            this.setResult(RESULT_OK, intent);
            this.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(isNowShowWhat == 0){
            options1 = position;
            options2 = 0;
            options3 = 0;
        }else if(isNowShowWhat == 1){
            options2 = position;
            options3 = 0;
        }else if(isNowShowWhat == 2){
            options3 = position;
        }
        refreshProvince();
        mListPopupWindow.dismiss();
    }




}
