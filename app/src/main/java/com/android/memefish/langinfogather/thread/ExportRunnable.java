package com.android.memefish.langinfogather.thread;

import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.ObligeeChild;
import com.android.memefish.langinfogather.db.Region;
import com.android.memefish.langinfogather.db.manager.ObligeeManager;
import com.android.memefish.langinfogather.db.Picture;
import com.android.memefish.langinfogather.db.manager.PictureManager;
import com.android.memefish.langinfogather.db.manager.RegionManager;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.UserUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExportRunnable implements Runnable {

    private static boolean isRunning = false;
    private OnFinishListener onFinishListener;



    public void setOnFinishListener(OnFinishListener listener){
        onFinishListener = listener;
    }

    public boolean isRunning(){
        return isRunning;
    }

    @Override
    public void run() {
        if(!isRunning){
            try {
                isRunning = true;
                File file = new File(PictureUtil.NEW_PIC_ROOT_PATH);
                if(file.exists()){
                    PictureUtil.deleteDirWihtFile(file);
                }
                List<Region> regions = RegionManager.listRegion(UserUtil.getInstance().getUserId());
                for(Region region : regions){
                    List<Obligee> obligees = ObligeeManager.listObligee(UserUtil.getInstance().getUserId(),region.getId());
                    for(Obligee obligee : obligees){
                        List<ObligeeChild> childList = ObligeeManager.listObligeeChild(obligee.getId());
                        ObligeeChild main = null;
                        List<Picture> qlrs = new ArrayList<>();
                        for(ObligeeChild child : childList){
                            if(child.getProperty().equals("主权利人")){
                                main = child;
                            }
                            List<Picture> pics = PictureManager.listPicture(obligee.getUser(),obligee.getRegion(),""+child.getId(),"权利人");
                            if(pics.size() > 0){
                                qlrs.addAll(pics);
                            }
                        }
                        List<Picture> fws = PictureManager.listPicture(obligee.getUser(),obligee.getRegion(),""+main.getId(),"房屋");
                        List<Picture> qslys = PictureManager.listPicture(obligee.getUser(),obligee.getRegion(),""+main.getId(),"权属来源");
                        List<Picture> qts = PictureManager.listPicture(obligee.getUser(),obligee.getRegion(),""+main.getId(),"其他");
                        for(int i=0;i<qlrs.size();i++){
                            Picture qlr = qlrs.get(i);
                            PictureUtil.copyFile(qlr.getPath(),PictureUtil.NEW_PIC_PATH + "/" + region.getCode()+region.getAddr()+region.getAddrDetail()+region.getVillage() + "/" + obligee.getNum() + "/QLR/QLR_"+PictureUtil.formatNum(i+1)+".jpg");
                        }
                        for(int i=0;i<fws.size();i++){
                            Picture fw = fws.get(i);
                            PictureUtil.copyFile(fw.getPath(),PictureUtil.NEW_PIC_PATH + "/" + region.getCode()+region.getAddr()+region.getAddrDetail()+region.getVillage() + "/" + obligee.getNum() + "/FW/FW_"+PictureUtil.formatNum(i+1)+".jpg");
                        }
                        for(int i=0;i<qslys.size();i++){
                            Picture qsly = qslys.get(i);
                            PictureUtil.copyFile(qsly.getPath(),PictureUtil.NEW_PIC_PATH + "/" + region.getCode()+region.getAddr()+region.getAddrDetail()+region.getVillage() + "/" + obligee.getNum() +  "/QSLY/QSLY_"+PictureUtil.formatNum(i+1)+".jpg");
                        }
                        for(int i=0;i<qts.size();i++){
                            Picture qt = qts.get(i);
                            PictureUtil.copyFile(qt.getPath(),PictureUtil.NEW_PIC_PATH + "/" + region.getCode()+region.getAddr()+region.getAddrDetail()+region.getVillage() + "/" + obligee.getNum() + "/QT/QT_"+PictureUtil.formatNum(i+1)+".jpg");
                        }
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                isRunning = false;
                if(onFinishListener != null){
                    onFinishListener.onFinish();
                }
            }
        }
    }



    public interface OnFinishListener
    {
        void onFinish();
    }





}
