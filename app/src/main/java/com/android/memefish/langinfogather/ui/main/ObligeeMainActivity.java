package com.android.memefish.langinfogather.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ObligeeListBean;
import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.ObligeeManager;
import com.android.memefish.langinfogather.ui.gather.GatherTypeActivity;
import com.android.memefish.langinfogather.util.UserUtil;
import com.android.minlib.smartrefreshlayout.recycler.OnSmartFillListener;
import com.android.minlib.smartrefreshlayout.recycler.ViewHolder;

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
                startActivity(new Intent(ObligeeMainActivity.this,ObligeeSelectActivity.class));
            }
        });
        mMainTitleView.setTitle(UserUtil.getInstance().getRegion());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OBLIGEE_SELECT_CODE && resultCode == RESULT_OK){
            List<ObligeeListBean> beans = (List<ObligeeListBean>) data.getSerializableExtra("obligeeList");
        }
    }

    class MySmartFillListener implements OnSmartFillListener<Obligee> {

        @Override
        public void onLoadData(final int taskId, int pageIndex) {
            mSmartRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<Obligee> obligees = ObligeeManager.listObligee(UserUtil.getInstance().getUserId(),UserUtil.getInstance().getRegion());
                    mSmartRecyclerView.showData(taskId,obligees,obligees.size());
                }
            },200);
        }

        @Override
        public void clickItem(int viewId, Obligee item, int position) {
        }

        @Override
        public int createLayout() {
            return R.layout.item_obligee;
        }

        @Override
        public void createListItem(int viewId, ViewHolder holder, final Obligee currentItem, List<Obligee> list, int position) {
            AvatarImageView circle = holder.getView(R.id.item_obligee_ic);
            circle.setTextAndColor("张", Color.RED);
            holder.setText(R.id.item_obligee_name,currentItem.getNameAndProperty());
            holder.setText(R.id.item_obligee_status,currentItem.getStatus());
            holder.setText(R.id.item_obligee_time,currentItem.getTime());
            holder.setText(R.id.item_obligee_num,currentItem.getNum());

            ((AvatarImageView)holder.getView(R.id.item_obligee_quanliren)).setTextAndColor("权", ContextCompat.getColor(ObligeeMainActivity.this,R.color.color1));
            ((AvatarImageView)holder.getView(R.id.item_obligee_fangwu)).setTextAndColor("房", ContextCompat.getColor(ObligeeMainActivity.this,R.color.color2));
            ((AvatarImageView)holder.getView(R.id.item_obligee_quanshulaiyuan)).setTextAndColor("源", ContextCompat.getColor(ObligeeMainActivity.this,R.color.color3));
            ((AvatarImageView)holder.getView(R.id.item_obligee_qita)).setTextAndColor("其", ContextCompat.getColor(ObligeeMainActivity.this,R.color.color4));

            holder.setOnClickListener(R.id.item_obligee_edit, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/30 0030 编辑权利人
                    Log.d("hsb","edit obligee");
                    Intent intent = new Intent(ObligeeMainActivity.this,ObligeeSelectActivity.class);
                    intent.putExtra("obligee",currentItem.getId());
                    startActivity(intent);
                }
            });
            holder.setOnClickListener(R.id.item_obligee_delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/30 0030 删除权利人
                    Log.d("hsb","delete obligee");
                    ObligeeManager.deleteObligee(currentItem.getId());
                    mSmartRecyclerView.loadData();
                }
            });
            holder.setOnClickListener(R.id.item_obligee_content, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/31 0031 进入收集首页
                    UserUtil.getInstance().setObligee(currentItem.getNames());
                    startActivity(new Intent(ObligeeMainActivity.this, GatherTypeActivity.class));
                }
            });
        }

        @Override
        public void onLastPageHint() {

        }
    }
}
