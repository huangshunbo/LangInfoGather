package com.android.memefish.langinfogather.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.text.TextUtils;

import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.Picture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Format;

public class PictureUtil {

    public static final String PICTURE_PATH = Environment.getExternalStorageDirectory() + "/langinfogather/" + UserUtil.getInstance().getUserId() +"/";
    public static final String NEW_PIC_ROOT_PATH = Environment.getExternalStorageDirectory() + "/SWBDC/";
    public static final String NEW_PIC_PATH = NEW_PIC_ROOT_PATH + UserUtil.getInstance().getUserId() + "/";

    public static final int QLR_SORT_BASE = 10000;
    public static final int FW_SORT_BASE = 20000;
    public static final int QSLY_SORT_BASE = 30000;
    public static final int QT_SORT_BASE = 40000;
    public static final int TZ_SORT_BASE = 50000;

    static {
        File file = new File(PICTURE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public static void compressPicture(String path){
        try {
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
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static File getPictureFile(Picture picture){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(PictureUtil.PICTURE_PATH);
        stringBuffer.append(picture.getRegion()+"/");
        stringBuffer.append(UserUtil.getInstance().getObligee()+"/");
        stringBuffer.append(picture.getOneLevel()+"/");
        if(!TextUtils.isEmpty(picture.getTwoLevel())) stringBuffer.append(picture.getTwoLevel()+"/");
        if(!TextUtils.isEmpty(picture.getThreeLevel())) stringBuffer.append(picture.getThreeLevel()+"/");
        if(picture.getName() != null) stringBuffer.append(picture.getName());
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

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else{
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File newFile = new File(newPath);
            if(!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs();
            }
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static String formatNum(int num){
        Format f1 = new DecimalFormat("000");
        return f1.format(num);
    }

    /**
     *@Description: 根据权利人删除权利人所属文件夹
     *@Params: [currentItem]
     *@Return: void
     *@Author: huangshunbo
     *@Date: 2018/8/11 0011
     */
    public static void deleteObligeeFile(Obligee currentItem) {
        deleteDirWihtFile(new File(PictureUtil.PICTURE_PATH + currentItem.getRegion() + "/" + currentItem.getName() +"/"));
    }
}
