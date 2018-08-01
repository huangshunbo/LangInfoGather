package com.android.memefish.langinfogather.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import com.android.memefish.langinfogather.db.Picture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureUtil {

    public static final String PICTURE_PATH = Environment.getExternalStorageDirectory() + "/langinfogather/" + UserUtil.getInstance().getUserId() +"/";

    static {
        File file = new File(PICTURE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public static void compressPicture(String path){
        BitmapFactory.Options op = new BitmapFactory.Options();
        Bitmap bitMap = BitmapFactory.decodeFile(path);
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        int newWidth = width;
        int newHeight = height;
        while(newWidth > 2000 || newHeight > 2000){
            newWidth = newWidth / 2;
            newHeight = newHeight / 2;
        }

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,matrix, true);
        FileOutputStream b = null;
        try {
            b = new FileOutputStream(path);
            if (bitMap != null) {
                bitMap.compress(Bitmap.CompressFormat.JPEG, 50, b);
            }
            b.close();
            bitMap.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static File getPictureFile(Picture picture){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(PictureUtil.PICTURE_PATH);
        stringBuffer.append(picture.getRegion()+"/");
        stringBuffer.append(picture.getObligee()+"/");
        stringBuffer.append(picture.getOneLevel()+"/");
        if(picture.getUser() != null) stringBuffer.append(picture.getUser());
        if(picture.getTwoLevel() != null) stringBuffer.append("_" + picture.getTwoLevel());
        if(picture.getThreeLevel() != null) stringBuffer.append("_" + picture.getThreeLevel());
        if(picture.getName() != null) stringBuffer.append("_" + picture.getName());
        stringBuffer.append(".jpg");
        File file = new File(stringBuffer.toString());
        return file;
    }


    public static Bitmap decodeSampledBitmapFromResource(String filePath, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
