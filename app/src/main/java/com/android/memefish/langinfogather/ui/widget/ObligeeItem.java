package com.android.memefish.langinfogather.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.ListPopupWindow;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.util.ScreenUtil;

public class ObligeeItem extends FrameLayout implements AdapterView.OnItemClickListener{

    private TextView tvType;
    private EditText etName;
    private ImageView ivDel;

    private ListPopupWindow mListPopupWindow;
    private String[] items = new String[]{"继承人","买卖","其他"};
    private OnClickListener onClickListener;

    public ObligeeItem(@NonNull Context context,View anchorView) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_obligee_item,this);
        tvType = findViewById(R.id.view_obligee_item_type);
        etName = findViewById(R.id.view_obligee_item_name);
        ivDel = findViewById(R.id.view_obligee_item_del);

        tvType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListPopupWindow.show();
            }
        });
        ivDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ViewGroup)getParent()).removeView(ObligeeItem.this);
                if(onClickListener != null){
                    onClickListener.onClick(view);
                }
            }
        });

        mListPopupWindow = new ListPopupWindow(getContext());
        mListPopupWindow.setDropDownGravity(Gravity.BOTTOM);
        mListPopupWindow.setAnchorView(anchorView);
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mListPopupWindow.setHeight(ScreenUtil.getScreenHeigh()/2);
        mListPopupWindow.setOnItemClickListener(this);
        mListPopupWindow.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, items));
        tvType.setText(items[0]);
    }

    public void setOnDelListener(OnClickListener listener){
        onClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        tvType.setText(items[i]);
        mListPopupWindow.dismiss();
    }
    public String getType(){
        if(tvType == null){
            return null;
        }
        return tvType.getText().toString();
    }
    public String getName(){
        if(etName == null){
            return null;
        }
        return etName.getText().toString();
    }

    public void setType(String type){
        tvType.setText(type);
    }

    public void setName(String name){
        etName.setText(name);
    }
}
