package com.android.memefish.langinfogather.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;

/**
 * @author: huangshunbo
 * @Filename: MainTitleView
 * @Description: 标题栏
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/7/24 17:44 
 */
public class MainTitleView extends FrameLayout implements View.OnClickListener{

    TextView tvTitle;
    ImageView ivCenter,ivSearch,ivClear;
    EditText mEdittext;
    View llSearchContent;
    OnSearchListener onSearchListener;
    OnClearListener onClearListener;

    public MainTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_main_title,this);
        tvTitle = findViewById(R.id.view_main_title);
        ivCenter = findViewById(R.id.view_main_center);
        ivSearch = findViewById(R.id.view_main_search);
        ivClear = findViewById(R.id.view_main_search_content_clear);
        mEdittext = findViewById(R.id.view_main_search_content_et);
        llSearchContent = findViewById(R.id.view_main_search_content);

        ivSearch.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        mEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(onSearchListener != null && !TextUtils.isEmpty(mEdittext.getText().toString())){
                        onSearchListener.onSearch(mEdittext.getText().toString());
                    }
                }
                return false;
            }
        });
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getIvCenter() {
        return ivCenter;
    }

    public ImageView getIvSearch() {
        return ivSearch;
    }

    public ImageView getIvClear() {
        return ivClear;
    }

    public EditText getmEdittext() {
        return mEdittext;
    }

    public View getLlSearchContent() {
        return llSearchContent;
    }

    public void setCenterClickListener(OnClickListener listener){
        if(ivCenter != null){
            ivCenter.setOnClickListener(listener);
        }
    }

    public void setSearchListener(OnSearchListener listener){
        onSearchListener = listener;
    }

    public void setOnClearListener(OnClearListener onClearListener) {
        this.onClearListener = onClearListener;
    }

    public void setTitle(String title){
        if(tvTitle != null){
            tvTitle.setText(title);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.view_main_search){
            showInput();
        }else if(id == R.id.view_main_search_content_clear){
            reset();
            if(onClearListener != null){
                onClearListener.onClear();
            }
        }
    }

    public void showInput() {
        ivSearch.setVisibility(GONE);
        llSearchContent.setVisibility(VISIBLE);
        tvTitle.setVisibility(GONE);
        mEdittext.setFocusable(true);
        mEdittext.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void reset() {
        ivSearch.setVisibility(VISIBLE);
        mEdittext.setText("");
        llSearchContent.setVisibility(GONE);
        tvTitle.setVisibility(VISIBLE);
    }

    public interface OnSearchListener
    {
        void onSearch(String key);
    }

    public interface OnClearListener
    {
        void onClear();
    }
}
