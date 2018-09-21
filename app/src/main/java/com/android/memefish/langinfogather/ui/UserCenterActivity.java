package com.android.memefish.langinfogather.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.thread.ExportRunnable;
import com.android.memefish.langinfogather.ui.main.RegionMainActivity;
import com.android.memefish.langinfogather.util.PictureUtil;

import java.io.File;

public class UserCenterActivity extends BaseActivity implements View.OnClickListener {

    TextView tvArea, tvExport, tvChangePwd, tvLogout;
    ExportRunnable runnable = new ExportRunnable();
    ProgressDialog progressDialog;
    Toolbar mToolBar;

    MediaScannerConnection mediaScannerConnection;
    MediaScannerConnection.MediaScannerConnectionClient client = new MediaScannerConnection.MediaScannerConnectionClient() {
        @Override
        public void onScanCompleted(String path, Uri uri) {  //当client和MediaScaner扫描完成后  进行关闭我们的连接
            // TODO Auto-generated method stub
            mediaScannerConnection.disconnect();
        }

        @Override
        public void onMediaScannerConnected() {   //当client和MediaScanner完成链接后，就开始进行扫描。
            // TODO Auto-generated method stub
            mediaScannerConnection.scanFile(PictureUtil.NEW_PIC_PATH, null);
        }
    };


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);
        tvArea = findViewById(R.id.activity_user_changearea);
        tvExport = findViewById(R.id.activity_user_export);
        tvChangePwd = findViewById(R.id.activity_user_changepwd);
        tvLogout = findViewById(R.id.activity_user_logout);
        mToolBar = findViewById(R.id.activity_user_toolbar);

        tvArea.setOnClickListener(this);
        tvExport.setOnClickListener(this);
        tvChangePwd.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("图片导出中，请稍等......");
        progressDialog.setCancelable(false);

        runnable.setOnFinishListener(new ExportRunnable.OnFinishListener() {
            @Override
            public void onFinish() {
                progressDialog.dismiss();
                folderScan(PictureUtil.NEW_PIC_ROOT_PATH);
                notifyFileSystemChanged(PictureUtil.NEW_PIC_ROOT_PATH);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.activity_user_changearea) {
            startActivity(new Intent(this, RegionMainActivity.class));
        } else if (id == R.id.activity_user_export) {
            if (!runnable.isRunning()) {
                progressDialog.show();
                new Thread(runnable).start();
            }
        } else if (id == R.id.activity_user_changepwd) {

        } else if (id == R.id.activity_user_logout) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public   void  folderScan(String path){
        File file = new  File(path);

        if (file.isDirectory()){
            File[] array = file.listFiles();

            for ( int  i= 0 ;i<array.length;i++){
                File f = array[i];

                if (f.isFile()){ //FILE TYPE
                    fileScan(f.getAbsolutePath());
                }
                else  { //FOLDER TYPE
                    folderScan(f.getAbsolutePath());
                }
            }
        }
    }

    public void  fileScan(String file){
        Uri data = Uri.parse("file://" +file);
        sendBroadcast(new  Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    private void notifyFileSystemChanged(String path) {
        if (path == null)
            return;
        final File f = new File(path);
        if (Build.VERSION.SDK_INT >= 19) { //添加此判断，判断SDK版本是不是4.4或者高于4.4
            String[] paths = new String[]{path};
            MediaScannerConnection.scanFile(this, paths, null, null);
        } else {
            final Intent intent;
            if (f.isDirectory()) {
                intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
                intent.setClassName("com.android.providers.media", "com.android.providers.media.MediaScannerReceiver");
                intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            } else {
                intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(new File(path)));
            }
            sendBroadcast(intent);
        }
    }


}
