package com.android.memefish.langinfogather.ui.main;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ProvinceBean;
import com.android.memefish.langinfogather.db.Region;
import com.android.memefish.langinfogather.db.manager.RegionManager;
import com.android.memefish.langinfogather.http.AbstractCallback;
import com.android.memefish.langinfogather.http.Smart;
import com.android.memefish.langinfogather.http.bean.RegionBean;
import com.android.memefish.langinfogather.util.UserUtil;
import com.android.minlib.smartrefreshlayout.recycler.OnSmartFillListener;
import com.android.minlib.smartrefreshlayout.recycler.ViewHolder;

import java.util.List;

//行政区
public class RegionMainActivity extends MainBaseActivity {

    private static final int REGION_SELECT_CODE = 10001;
    private static int BACK_CLICK = 2;

    @Override
    void onInitList() {
        mSmartRecyclerView.setOnSmartFillListener(new MySmartFillListener());
        fabCreate.setImageResource(R.drawable.ic_add_black_24dp);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegionMainActivity.this,RegionSelectActivity.class));
            }
        });
        mMainTitleView.setTitle("行政区");
        mSmartRecyclerView.loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSmartRecyclerView.loadData();
    }

    class MySmartFillListener implements OnSmartFillListener<RegionBean> {

        @Override
        public void onLoadData(final int taskId, int pageIndex) {
            Smart.listRegion(""+pageIndex, new AbstractCallback<List<RegionBean>>() {
                @Override
                public void onSuccess(List<RegionBean> regionBeans) {
                    mSmartRecyclerView.finishLoadMoreOrRefresh();
                    if(regionBeans != null && regionBeans.size() > 0){
                        mSmartRecyclerView.showData(taskId,regionBeans,Integer.MAX_VALUE);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(RegionMainActivity.this, "数据获取失败,请刷新重试", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void clickItem(int viewId, RegionBean item, int position) {
        }

        @Override
        public int createLayout() {
            return R.layout.item_region;
        }

        @Override
        public void createListItem(int viewId, ViewHolder holder, final RegionBean currentItem, List<RegionBean> list, int position) {
            holder.setText(R.id.item_region_title,currentItem.getDJQDM() + " " + currentItem.getDJZQDM() + " " + currentItem.getVillageName() );
            holder.setText(R.id.item_region_time,""+currentItem.getProvince() + " " + currentItem.getCity() + " " + currentItem.getCounty());
            holder.setOnClickListener(R.id.item_region_edit, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/30 0030 编辑行政区
                    Intent intent = new Intent(RegionMainActivity.this,RegionSelectActivity.class);
                    intent.putExtra("id",currentItem);
                    startActivity(intent);
                }
            });
            holder.setOnClickListener(R.id.item_region_delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/30 0030 删除行政区
//                    RegionManager.deleteRegion(currentItem.getId());
                    mSmartRecyclerView.loadData();
                }
            });
            holder.setOnClickListener(R.id.item_region_content, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserUtil.getInstance().setRegion(Long.valueOf(currentItem.getXZQINFOID()));
                    UserUtil.getInstance().setRegionStr(currentItem.getVillageName());
                    startActivity(new Intent(RegionMainActivity.this,ObligeeMainActivity.class));
                }
            });
        }

        @Override
        public void onLastPageHint() {

        }
    }

    @Override
    public void onBackPressed() {
        //退出应用
        BACK_CLICK--;
        if(BACK_CLICK == 0){
            System.exit(0);
            finish();
        }else {
            Toast.makeText(this, "再次点击退出应用", Toast.LENGTH_SHORT).show();
            fabCreate.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BACK_CLICK = 2;
                }
            },2000);
        }
    }
}
