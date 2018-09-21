package com.android.memefish.langinfogather.ui.gather;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.bm.library.PhotoView;

import java.util.ArrayList;

public class PhotoViewActivity extends BaseActivity {

    private ViewPager viewPager;
    private TextView textView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private int num = 0;
    private ArrayList<String> pics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        num = getIntent().getIntExtra("num",0);
        pics = getIntent().getStringArrayListExtra("pics");

        setContentView(R.layout.activity_photo_view);
        viewPager = findViewById(R.id.activity_photo_view_vp);
        textView = findViewById(R.id.activity_photo_view_tip);

        viewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pics.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(PhotoViewActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                view.setImageBitmap(BitmapFactory.decodeFile(pics.get(position)));
                container.addView(view);
                return view;
            }



            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        viewPager.setCurrentItem(num);
        textView.setText((num+1)+"/"+pics.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                textView.setText((i+1)+"/"+pics.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
