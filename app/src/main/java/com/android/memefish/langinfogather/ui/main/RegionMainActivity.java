package com.android.memefish.langinfogather.ui.main;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ProvinceBean;
import com.android.memefish.langinfogather.db.Region;
import com.android.memefish.langinfogather.db.manager.RegionManager;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSmartRecyclerView.loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REGION_SELECT_CODE && resultCode == RESULT_OK){
            ProvinceBean bean = (ProvinceBean) data.getSerializableExtra("province");
        }
    }

    class MySmartFillListener implements OnSmartFillListener<Region> {

        @Override
        public void onLoadData(final int taskId, int pageIndex) {
            List<Region> regions = RegionManager.listRegion(UserUtil.getInstance().getUserId());
            mSmartRecyclerView.showData(taskId,regions,regions.size());
        }

        @Override
        public void clickItem(int viewId, Region item, int position) {
        }

        @Override
        public int createLayout() {
            return R.layout.item_region;
        }

        @Override
        public void createListItem(int viewId, ViewHolder holder, final Region currentItem, List<Region> list, int position) {
            holder.setText(R.id.item_region_title,currentItem.getAddr() + " " + currentItem.getAddrDetail() + " " + currentItem.getVillage() );
            holder.setText(R.id.item_region_time,""+currentItem.getProvince() + " " + currentItem.getCity() + " " + currentItem.getArea());
            holder.setOnClickListener(R.id.item_region_edit, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/30 0030 编辑行政区
                    Intent intent = new Intent(RegionMainActivity.this,RegionSelectActivity.class);
                    intent.putExtra("id",currentItem.getId());
                    startActivity(intent);
                }
            });
            holder.setOnClickListener(R.id.item_region_delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/30 0030 删除行政区
                    RegionManager.deleteRegion(currentItem.getId());
                    mSmartRecyclerView.loadData();
                }
            });
            holder.setOnClickListener(R.id.item_region_content, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserUtil.getInstance().setRegion(currentItem.getId());
                    UserUtil.getInstance().setRegionStr(currentItem.getVillage());
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
