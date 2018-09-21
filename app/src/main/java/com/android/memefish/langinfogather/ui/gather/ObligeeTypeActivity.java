package com.android.memefish.langinfogather.ui.gather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.PictureShowBean;
import com.android.memefish.langinfogather.bean.QuanlirenCountBean;
import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.ObligeeChild;
import com.android.memefish.langinfogather.db.manager.ObligeeManager;
import com.android.memefish.langinfogather.db.manager.PictureManager;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * @author: huangshunbo
 * @Filename: ObligeeTypeActivity
 * @Description: 权利人列表 张三_身份证 张三_户口簿
 * @Copyright: Copyright (c) 2018 XXX Inc. All rights reserved.
 * @date: 2018/7/28 0028 22:13
 */
public class ObligeeTypeActivity extends BaseActivity {

    Toolbar mToolbar;
    ListView mListView;

    private List<ObligeeChild> childList = new ArrayList<>();
    private Obligee obligee;
    private MyListAdapter mAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    HashMap<String,QuanlirenCountBean> counts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obligee_type);

        mToolbar = findViewById(R.id.activity_obligee_type_toolbar);
        mListView = findViewById(R.id.activity_obligee_type_list);
        mListView.setAdapter(mAdapter = new MyListAdapter(this));

        mToolbar.setTitle("权利人");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        obligee = ObligeeManager.getObligee(UserUtil.getInstance().getObligeeId());
        childList = ObligeeManager.listObligeeChild(obligee.getId());

    }

    @Override
    protected void onResume() {
        super.onResume();
        counts = PictureManager.countQuanliren(UserUtil.getInstance().getObligeeId());
        mAdapter.notifyDataSetChanged();
    }

    class MyListAdapter extends BaseAdapter {
        private Context mContext;
        public MyListAdapter(Context context) {
            mContext = context;
        }

        public int getCount() {
            return childList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_obligee_type, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.item_obligee_type_name);
                viewHolder.obligee = convertView.findViewById(R.id.item_obligee_type_obligee);
                viewHolder.booklet = convertView.findViewById(R.id.item_obligee_type_booklet);
                viewHolder.obligeeCount = convertView.findViewById(R.id.item_obligee_type_obligee_num);
                viewHolder.bookletCount = convertView.findViewById(R.id.item_obligee_type_booklet_num);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final String nameStr = childList.get(position).getName();
            final Long obligeeId = childList.get(position).getId();
            viewHolder.name.setText(nameStr);
            QuanlirenCountBean bean = counts.get(nameStr);
            if(bean != null && bean.getSfzCount() > 0){
                viewHolder.obligeeCount.setTextAndColor(""+bean.getSfzCount(), ContextCompat.getColor(ObligeeTypeActivity.this,R.color.color1));
            }else{
                viewHolder.obligeeCount.setVisibility(View.GONE);
            }
            if(bean != null && bean.getHkbCount() > 0){
                viewHolder.bookletCount.setTextAndColor(""+bean.getHkbCount(),ContextCompat.getColor(ObligeeTypeActivity.this,R.color.color5));
            }else {
                viewHolder.bookletCount.setVisibility(View.GONE);
            }
            viewHolder.obligee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/28 0028 权利人_张三_身份证
                    Intent intent1 = new Intent(ObligeeTypeActivity.this,PictureFileActivity.class);
                    PictureShowBean bean = new PictureShowBean();
                    bean.setTitle("身份证");
                    bean.setOneLevel("权利人");
                    bean.setTwoLevel(nameStr);
                    bean.setThreeLevel("身份证");
                    bean.setObligeeId(obligeeId);
                    intent1.putExtra("pics",bean);
                    intent1.putExtra("sortBase", (PictureUtil.QLR_SORT_BASE + 1000*position));
                    startActivity(intent1);
                }
            });
            viewHolder.booklet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/28 0028 权利人_张三_户口簿
                    Intent intent1 = new Intent(ObligeeTypeActivity.this,PictureFileActivity.class);
                    PictureShowBean bean = new PictureShowBean();
                    bean.setTitle("户口簿");
                    bean.setOneLevel("权利人");
                    bean.setTwoLevel(nameStr);
                    bean.setThreeLevel("户口簿");
                    bean.setObligeeId(obligeeId);
                    intent1.putExtra("pics",bean);
                    intent1.putExtra("sortBase", (PictureUtil.QLR_SORT_BASE + 1000*position + 500));
                    startActivity(intent1);
                }
            });
            return convertView;
        }

        class ViewHolder
        {
            TextView name;
            TextView obligee;
            TextView booklet;
            AvatarImageView obligeeCount;
            AvatarImageView bookletCount;
        }
    }

}
