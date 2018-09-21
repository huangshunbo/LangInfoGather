package com.android.memefish.langinfogather.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.DistrictsBean;
import com.android.memefish.langinfogather.db.Region;
import com.android.memefish.langinfogather.db.manager.RegionManager;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.util.ProvinceUtil;
import com.android.memefish.langinfogather.util.ScreenUtil;
import com.android.memefish.langinfogather.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class RegionSelectActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    TextView tvProvince,tvCity,tvArea;
    EditText etAddr,etAddrDetail,etVillage;
    Toolbar mToolBar;
    TextView tvSubmit;

//    private static ArrayList<String> options1Items = new ArrayList<>();//省
//    private static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
//    private static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区

    private static ArrayList<DistrictsBean> options = new ArrayList<>();

    private static String options1 = "110000";
    private static String options2 = "110100";
    private static String options3 = "110101";
    //0 省 1市 2区
    private int isNowShowWhat = 0;

    private ListPopupWindow mListPopupWindow;

    private Long id = -1L;
    private Region region;

    private static List<DistrictsBean.Area> list;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select);

        id = getIntent().getLongExtra("id",-1);

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

//        if(options1Items.size() <= 0 || options2Items.size() <= 0 || options3Items.size() <= 0){
//            ProvinceUtil.initJsonData(this,options1Items,options2Items,options3Items);
//        }

        if(options.size() <= 0){
            options = ProvinceUtil.initJsonData(this);
        }

        refreshProvince();

        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setDropDownGravity(Gravity.BOTTOM);
        mListPopupWindow.setAnchorView(getWindow().getDecorView());
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mListPopupWindow.setHeight(ScreenUtil.getScreenHeigh()/2);
        mListPopupWindow.setOnItemClickListener(this);


        if(id != -1){
            region = RegionManager.selectRegion(id);
            tvProvince.setText(region.getProvince());
            tvCity.setText(region.getCity());
            tvArea.setText(region.getArea());
            etAddr.setText(region.getAddr());
            etAddrDetail.setText(region.getAddrDetail());
            etVillage.setText(region.getVillage());
        }else{
            region = new Region();
        }
    }

    private void refreshProvince(){
        tvProvince.setText(findArea("100000",options1).getName().replace("\"", ""));
        tvCity.setText(findArea(options1,options2).getName().replace("\"", ""));
        tvArea.setText(findArea(options2,options3).getName().replace("\"", ""));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.activity_region_select_province){
            isNowShowWhat = 0;
            mListPopupWindow.setAdapter(new MyAdapter(findDistricsBean("100000").getArea()));
            mListPopupWindow.show();
        }else if(id == R.id.activity_region_select_city){
            isNowShowWhat = 1;
            mListPopupWindow.setAdapter(new MyAdapter(findDistricsBean(options1).getArea()));
            mListPopupWindow.show();
        }else if(id == R.id.activity_region_select_area){
            isNowShowWhat = 2;
            mListPopupWindow.setAdapter(new MyAdapter(findDistricsBean(options2).getArea()));
            mListPopupWindow.show();
        }else if(id == R.id.activity_region_select_submit){
            String addr = etAddr.getText().toString();
            String addrDetail = etAddrDetail.getText().toString();
            String village = etVillage.getText().toString();
            if(TextUtils.isEmpty(addr) || TextUtils.isEmpty(addrDetail) || TextUtils.isEmpty(village)){
                Toast.makeText(this, "请填写完整后再提交", Toast.LENGTH_SHORT).show();
                return;
            }
            region.setUser(UserUtil.getInstance().getUserId());
            region.setProvince(findArea("100000",options1).getName().replace("\"", ""));
            region.setCity(findArea(options1,options2).getName().replace("\"", ""));
            region.setArea(findArea(options2,options3).getName().replace("\"", ""));
            region.setCode(findArea(options2,options3).getCode().replace("\"", ""));
            region.setAddr(addr);
            region.setAddrDetail(addrDetail);
            region.setVillage(village);
            RegionManager.insertRegion(region);
            this.finish();
        }
    }

    private DistrictsBean findDistricsBean(String str){
        for(DistrictsBean bean : options){
            if(TextUtils.equals(str,bean.getMainCode())){
                return bean;
            }
        }
        return null;
    }

    private DistrictsBean.Area findArea(String mainCode,String str){
        List<DistrictsBean.Area> list = findDistricsBean(mainCode).getArea();
        for(DistrictsBean.Area area : list){
            if(TextUtils.equals(str,area.getCode())){
                return area;
            }
        }
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(isNowShowWhat == 0){
            options1 = list.get(position).getCode();
            options2 = findDistricsBean(options1).getArea().get(0).getCode();
            options3 = findDistricsBean(options2).getArea().get(0).getCode();
        }else if(isNowShowWhat == 1){
            options2 = list.get(position).getCode();
            options3 = findDistricsBean(options2).getArea().get(0).getCode();
        }else if(isNowShowWhat == 2){
            options3 = list.get(position).getCode();
        }
        refreshProvince();
        mListPopupWindow.dismiss();
    }


    class MyAdapter extends BaseAdapter
    {

        public MyAdapter(List<DistrictsBean.Area> list) {
            RegionSelectActivity.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            if(view == null){
                view = View.inflate(RegionSelectActivity.this,android.R.layout.simple_list_item_1,null);
                textView = view.findViewById(android.R.id.text1);
                view.setTag(textView);
            }else{
                textView = (TextView) view.getTag();
            }
            textView.setText(list.get(i).getName().replace("\"", ""));
            return view;
        }
    }


}
