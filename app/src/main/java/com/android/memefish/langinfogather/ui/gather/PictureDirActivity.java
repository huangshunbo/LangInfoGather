package com.android.memefish.langinfogather.ui.gather;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.PictureShowBean;
import com.android.memefish.langinfogather.util.PictureUtil;

public class PictureDirActivity extends PictureBaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle(pictureShowBean.getTitle());
        mGridView.setAdapter(new MyAdapter(this));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent2 = new Intent(PictureDirActivity.this,PictureFileActivity.class);
                PictureShowBean bean = (PictureShowBean) pictureShowBean.clone();
                if(pictureShowBean.getTwoLevel() == null){
                    bean.setTwoLevel(pictureShowBean.getDirs().get(i));
                }else if(pictureShowBean.getThreeLevel() == null){
                    bean.setThreeLevel(pictureShowBean.getDirs().get(i));
                }
                bean.setTitle("FW");
                intent2.putExtra("pics",bean);
                if(i != 4){
                    intent2.putExtra("maxPicSize",1);
                }
                intent2.putExtra("baseNum",i);
                intent2.putExtra("sortBase", (PictureUtil.FW_SORT_BASE + i*1000));
                startActivity(intent2);
            }
        });

    }

    class MyAdapter extends BaseAdapter
    {
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return pictureShowBean.getDirs().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = null;
            if(view == null){
                textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setCompoundDrawablePadding(40);
                Drawable top = ContextCompat.getDrawable(context, R.mipmap.ic_floder_big);
                top.setBounds(0,0,top.getMinimumWidth(),top.getMinimumHeight());
                textView.setCompoundDrawables(null,top,null,null);
                textView.setPadding(40,40,40,40);
            }else {
                textView = (TextView) view;
            }
            textView.setText(pictureShowBean.getDirs().get(i));
            return textView;
        }

    }
}
