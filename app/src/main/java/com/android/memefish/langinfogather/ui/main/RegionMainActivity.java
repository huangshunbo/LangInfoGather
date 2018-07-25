package com.android.memefish.langinfogather.ui.main;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ProvinceBean;
import com.android.memefish.langinfogather.bean.RegionBean;
import com.android.minlib.smartrefreshlayout.recycler.OnSmartFillListener;
import com.android.minlib.smartrefreshlayout.recycler.ViewHolder;

import java.util.ArrayList;
import java.util.List;

//行政区
public class RegionMainActivity extends MainBaseActivity {

    private static final int REGION_SELECT_CODE = 10001;
    @Override
    void onInitList() {
        mSmartRecyclerView.setOnSmartFillListener(new MySmartFillListener());
        fabCreate.setImageResource(R.drawable.ic_add_black_24dp);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(RegionMainActivity.this,RegionSelectActivity.class),REGION_SELECT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REGION_SELECT_CODE && resultCode == RESULT_OK){
            ProvinceBean bean = (ProvinceBean) data.getSerializableExtra("province");
            Log.d("hsb","result " + bean.toString());
        }
    }

    class MySmartFillListener implements OnSmartFillListener<RegionBean> {

        @Override
        public void onLoadData(final int taskId, int pageIndex) {
            mSmartRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<RegionBean> list = new ArrayList<>();
                    list.add(new RegionBean("A村","2018-07-25 23:50"));
                    list.add(new RegionBean("B村","2018-07-25 23:50"));
                    list.add(new RegionBean("C村","2018-07-25 23:50"));
                    list.add(new RegionBean("D村","2018-07-25 23:50"));
                    list.add(new RegionBean("E村","2018-07-25 23:50"));
                    mSmartRecyclerView.showData(taskId,list,20);
                }
            },2000);
        }

        @Override
        public void clickItem(int viewId, RegionBean item, int position) {
            startActivity(new Intent(RegionMainActivity.this,ObligeeMainActivity.class));
        }

        @Override
        public int createLayout() {
            return R.layout.item_region;
        }

        @Override
        public void createListItem(int viewId, ViewHolder holder, RegionBean currentItem, List<RegionBean> list, int position) {
            holder.setText(R.id.item_region_title,currentItem.getTime());
            holder.setText(R.id.item_region_time,""+currentItem.getTime());
        }

        @Override
        public void onLastPageHint() {

        }
    }
}