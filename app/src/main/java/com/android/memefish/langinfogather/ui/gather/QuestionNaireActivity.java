package com.android.memefish.langinfogather.ui.gather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.util.UserUtil;

import java.util.Arrays;
import java.util.List;

public class QuestionNaireActivity extends BaseActivity {

    Toolbar mToolbar;
    List<String> names = Arrays.asList(UserUtil.getInstance().getObligee().split(","));

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questinnaire);

        mToolbar = findViewById(R.id.activity_questionnaire_toolbar);
        mToolbar.setTitle(names.get(0));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mToolbar.setTitle(item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        for(String name : names){
            menu.add(name);
        }
        return super.onPrepareOptionsMenu(menu);
    }

}
