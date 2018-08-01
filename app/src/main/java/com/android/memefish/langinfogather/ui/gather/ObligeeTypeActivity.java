package com.android.memefish.langinfogather.ui.gather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.PictureShowBean;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.util.UserUtil;

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

    private String[] names = UserUtil.getInstance().getObligee().split(",");

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obligee_type);

        mToolbar = findViewById(R.id.activity_obligee_type_toolbar);
        mListView = findViewById(R.id.activity_obligee_type_list);
        mListView.setAdapter(new MyListAdapter(this));

        mToolbar.setTitle("权利人");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class MyListAdapter extends BaseAdapter {
        private Context mContext;
        public MyListAdapter(Context context) {
            mContext = context;
        }

        public int getCount() {
            return names.length;
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
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(names[position]);
            viewHolder.obligee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/28 0028 权利人_张三_身份证
                    Intent intent1 = new Intent(ObligeeTypeActivity.this,PictureFileActivity.class);
                    PictureShowBean bean = new PictureShowBean();
                    bean.setTitle("身份证");
                    bean.setOneLevel("权利人");
                    bean.setTwoLevel(names[position]);
                    bean.setThreeLevel("身份证");
                    intent1.putExtra("pics",bean);
                    startActivity(intent1);
                }
            });
            viewHolder.booklet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/7/28 0028 权利人_张三_户口簿
                    Intent intent1 = new Intent(ObligeeTypeActivity.this,PictureFileActivity.class);
                    PictureShowBean bean = new PictureShowBean();
                    bean.setTitle("身份证");
                    bean.setOneLevel("权利人");
                    bean.setTwoLevel(names[position]);
                    bean.setThreeLevel("户口簿");
                    intent1.putExtra("pics",bean);
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
        }
    }

}
