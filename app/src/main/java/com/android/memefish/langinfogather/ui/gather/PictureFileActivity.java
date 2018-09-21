package com.android.memefish.langinfogather.ui.gather;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ObligeeCountBean;
import com.android.memefish.langinfogather.db.Picture;
import com.android.memefish.langinfogather.db.manager.PictureManager;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.UserUtil;
import com.android.minlib.smarttool.tool.PhotoTool;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PictureFileActivity extends PictureBaseActivity {

    private List<Picture> pics;
    private Picture newPicture;
    private FloatingActionButton fab;
    private static final int CAMERA_CODE = 10003;
    File tmpFile;
    private MyAdapter myAdapter;
    private int maxPicSize = -1;
    private int baseNum = 0;
    private int sortBase = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        maxPicSize = getIntent().getIntExtra("maxPicSize",-1);
        baseNum = getIntent().getIntExtra("baseNum",0);
        sortBase = getIntent().getIntExtra("sortBase",0);

        fab = findViewById(R.id.activity_picture_base_fab);
        mToolbar.setTitle(pictureShowBean.getTitle());
//        pics = PictureManager.listPicture(pictureShowBean.getOneLevel(),pictureShowBean.getTwoLevel(),pictureShowBean.getThreeLevel());
        pics = PictureManager.listPictureWithObligee(pictureShowBean.getObligeeId(),pictureShowBean.getOneLevel(),pictureShowBean.getTwoLevel(),pictureShowBean.getThreeLevel());
        myAdapter = new MyAdapter(this);
        mGridView.setAdapter(myAdapter);
        fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maxPicSize != -1 && myAdapter.getCount() >= maxPicSize){
                    Toast.makeText(PictureFileActivity.this,"该目录只能保存"+maxPicSize+"张图片",Toast.LENGTH_SHORT).show();
                    return;
                }
                newPicture = new Picture();
                newPicture.setUser(UserUtil.getInstance().getUserId());
                newPicture.setRegion(UserUtil.getInstance().getRegion());
                newPicture.setoId(UserUtil.getInstance().getObligeeId());
                newPicture.setObligeeId(pictureShowBean.getObligeeId());
                newPicture.setOneLevel(pictureShowBean.getOneLevel());
                newPicture.setTwoLevel(pictureShowBean.getTwoLevel());
                newPicture.setThreeLevel(pictureShowBean.getThreeLevel());
                newPicture.setName(pictureShowBean.getTitle() + System.currentTimeMillis());
                newPicture.setSort(sortBase+pics.size()+1);
                tmpFile = PictureUtil.getPictureFile(newPicture);
                if(!tmpFile.getParentFile().exists()){
                    tmpFile.getParentFile().mkdirs();
                }
                if(!tmpFile.exists()){
                    try {
                        tmpFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Uri uri = FileProvider.getUriForFile(PictureFileActivity.this,"com.android.memefish.langinfogather",tmpFile);
                PhotoTool.openCamera(PictureFileActivity.this,uri ,CAMERA_CODE);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                Uri uri;
//                if (Build.VERSION.SDK_INT >= 24) {
//                    File file = new File(pics.get(i).getPath());
//                    uri = FileProvider.getUriForFile(PictureFileActivity.this, "com.android.memefish.langinfogather", file);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                } else {
//                    uri = Uri.fromFile(new File(pics.get(i).getPath()));
//                }
//                intent.setDataAndType(uri, "image/*");
//                startActivity(intent);
                ArrayList<String> picList = new ArrayList<>();
                for(Picture pic : pics){
                    picList.add(pic.getPath());
                }
                Intent intent = new Intent(PictureFileActivity.this,PhotoViewActivity.class);
                intent.putExtra("num",i);
                intent.putStringArrayListExtra("pics",picList);
                startActivity(intent);
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Picture picture = pics.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(PictureFileActivity.this);
                builder.setTitle("温馨提示")
                        .setMessage("确定删除文件<"+picture.getName()+".jpg>?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                File file = new File(picture.getPath());
                                if(file.exists()){
                                    file.delete();
                                }
                                pics.remove(picture);
                                myAdapter.notifyDataSetChanged();
                                PictureManager.deletePicture(picture);
                                changeCount(-1);
                                fileScan(file.getAbsolutePath());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                return true;
            }
        });
    }

    private void changeCount(int count) {
        String type = pictureShowBean.getOneLevel();
        ObligeeCountBean countBean = UserUtil.getInstance().getTags();
        if(type.equals("权利人")){
            countBean.setQuanliren(countBean.getQuanliren() + count);
        }else if(type.equals("房屋")){
            countBean.setFangwu(countBean.getFangwu() + count);
        }else if(type.equals("权属来源")){
            countBean.setQuanshulaiyuan(countBean.getQuanshulaiyuan() + count);
        }else if(type.equals("图纸")){
            countBean.setTuzhi(countBean.getTuzhi() + count);
        }else if(type.equals("其他")){
            countBean.setQita(countBean.getQita() + count);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_CODE){
                if(tmpFile != null && tmpFile.exists()){
                    PictureUtil.compressPicture(tmpFile.getAbsolutePath());
                    newPicture.setPath(tmpFile.getAbsolutePath());
                    pics.add(newPicture);
                    myAdapter.notifyDataSetChanged();
                    PictureManager.insetPicture(newPicture);
                    changeCount(1);
                }
            }
        }
    }

    class MyAdapter extends BaseAdapter
    {
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            if(pics == null){
                return 0;
            }
            if(maxPicSize == -1 || pics.size() <= maxPicSize){
                return pics.size();
            }else {
                return maxPicSize;
            }
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
            ViewHolder viewHolder = null;
            if(view == null){
                view = View.inflate(context,R.layout.item_picture_file,null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = view.findViewById(R.id.item_picture_file_img);
                viewHolder.textView = view.findViewById(R.id.item_picture_file_txt);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.textView.setText(pictureShowBean.getTitle() + PictureUtil.formatNum(i+1));

            Glide.with(context)
                    .load(new File(pics.get(i).getPath()))
                    .into(viewHolder.imageView);
//            viewHolder.imageView.setImageBitmap(PictureUtil.decodeSampledBitmapFromResource(pics.get(i).getPath(),400,400));
            return view;
        }

        class ViewHolder
        {
            ImageView imageView;
            TextView textView;
        }
    }

    public void  fileScan(String file){
        Uri data = Uri.parse("file://" +file);
        sendBroadcast(new  Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }
}
