package com.android.memefish.langinfogather.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ObligeeBean;
import com.android.memefish.langinfogather.bean.ObligeeListBean;
import com.android.memefish.langinfogather.ui.gather.GatherTypeActivity;
import com.android.minlib.smartrefreshlayout.recycler.OnSmartFillListener;
import com.android.minlib.smartrefreshlayout.recycler.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

//权利人
public class ObligeeMainActivity extends MainBaseActivity {

    private static final int OBLIGEE_SELECT_CODE = 10002;

    @Override
    void onInitList() {
        mSmartRecyclerView.setOnSmartFillListener(new MySmartFillListener());
        fabCreate.setImageResource(R.drawable.ic_group_add_black_24dp);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ObligeeMainActivity.this,ObligeeSelectActivity.class),OBLIGEE_SELECT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OBLIGEE_SELECT_CODE && resultCode == RESULT_OK){
            List<ObligeeListBean> beans = (List<ObligeeListBean>) data.getSerializableExtra("obligeeList");
        }
    }

    class MySmartFillListener implements OnSmartFillListener<ObligeeBean> {

        @Override
        public void onLoadData(final int taskId, int pageIndex) {
            mSmartRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<ObligeeBean> list = new ArrayList<>();
                    for(int i=0;i<5;i++){
                        ObligeeBean bean = new ObligeeBean();
                        bean.setName("张三"+i);
                        bean.setHouseNumber("10086");
                        bean.setStatus("未完成");
                        bean.setTime("2018-07-25 22:13");
                        bean.setTags(new String[]{"A","B"});
                        list.add(bean);
                    }
                    mSmartRecyclerView.showData(taskId,list,20);
                }
            },200);
        }

        @Override
        public void clickItem(int viewId, ObligeeBean item, int position) {
            startActivity(new Intent(ObligeeMainActivity.this, GatherTypeActivity.class));
        }

        @Override
        public int createLayout() {
            return R.layout.item_obligee;
        }

        @Override
        public void createListItem(int viewId, ViewHolder holder, ObligeeBean currentItem, List<ObligeeBean> list, int position) {
            AvatarImageView circle = holder.getView(R.id.item_obligee_ic);
            circle.setTextAndColor("张", Color.RED);
            holder.setText(R.id.item_obligee_name,currentItem.getName());
            holder.setText(R.id.item_obligee_status,currentItem.getStatus());
            holder.setText(R.id.item_obligee_time,currentItem.getTime());
            LinearLayout ll = holder.getView(R.id.item_obligee_circles);
            if(ll.getChildCount() >= currentItem.getTags().length){
                for(int i=0;i<ll.getChildCount();i++){
                    if(i <= currentItem.getTags().length){
                        ((AvatarImageView)ll.getChildAt(i)).setTextAndColor(currentItem.getTags()[i],Color.GREEN);
                    }else {
                        ll.removeViewAt(i);
                    }
                }
            }else{
                for(int i=0;i<currentItem.getTags().length;i++) {
                    if(i < ll.getChildCount()){
                        ((AvatarImageView)ll.getChildAt(i)).setTextAndColor(currentItem.getTags()[i],Color.GREEN);
                    }else {
                        AvatarImageView c = new AvatarImageView(ObligeeMainActivity.this);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2,-2);
                        lp.gravity = Gravity.CENTER;
                        lp.width = 50;
                        lp.height = 50;
                        lp.leftMargin = 10;
                        c.setLayoutParams(lp);
                        c.setTextAndColor(currentItem.getTags()[i],Color.GREEN);
                        ll.addView(c);
                    }
                }
            }
        }

        @Override
        public void onLastPageHint() {

        }
    }
}
