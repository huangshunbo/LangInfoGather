package com.android.memefish.langinfogather.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ObligeeCountBean;
import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.ObligeeChild;
import com.android.memefish.langinfogather.db.Picture;
import com.android.memefish.langinfogather.db.manager.ObligeeManager;
import com.android.memefish.langinfogather.db.manager.PictureManager;
import com.android.memefish.langinfogather.db.manager.QuestionNaireManager;
import com.android.memefish.langinfogather.http.AbstractCallback;
import com.android.memefish.langinfogather.http.Smart;
import com.android.memefish.langinfogather.http.bean.ObligeeBean;
import com.android.memefish.langinfogather.ui.gather.GatherTypeActivity;
import com.android.memefish.langinfogather.ui.widget.MainTitleView;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.UserUtil;
import com.android.minlib.smartrefreshlayout.recycler.OnSmartFillListener;
import com.android.minlib.smartrefreshlayout.recycler.ViewHolder;

import java.util.HashMap;
import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

//权利人
public class ObligeeMainActivity extends MainBaseActivity {

    private HashMap<String, ObligeeCountBean> tagMap;

    @Override
    void onInitList() {
        mSmartRecyclerView.setOnSmartFillListener(new MySmartFillListener());
        fabCreate.setImageResource(R.drawable.ic_group_add_black_24dp);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ObligeeMainActivity.this, ObligeeSelectActivity.class));
            }
        });
        mMainTitleView.setTitle(UserUtil.getInstance().getRegionStr());

        mMainTitleView.getmEdittext().setHint("请输入预编号搜索");
        mMainTitleView.setSearchListener(new MainTitleView.OnSearchListener() {
            @Override
            public void onSearch(String key) {
                if (TextUtils.isEmpty(key)) {
                    mSmartRecyclerView.loadData();
                } else {
                    List<Obligee> obligees = ObligeeManager.listObligee(key);
                    mSmartRecyclerView.showData(2, obligees, obligees.size());
                }
            }
        });
        mMainTitleView.setOnClearListener(new MainTitleView.OnClearListener() {
            @Override
            public void onClear() {
                mSmartRecyclerView.loadData();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mSmartRecyclerView.loadData();
    }

    class MySmartFillListener implements OnSmartFillListener<ObligeeBean> {
    @Override
    public void onLoadData(final int taskId, int pageIndex) {
            if (pageIndex == 1) {
                mMainTitleView.reset();
            }
//            List<Obligee> obligees = ObligeeManager.listObligee(UserUtil.getInstance().getUserId(), UserUtil.getInstance().getRegion());
            Smart.listObligee(""+pageIndex, new AbstractCallback<List<ObligeeBean>>() {
                @Override
                public void onSuccess(List<ObligeeBean> obligeeBeans) {
                    mSmartRecyclerView.finishLoadMoreOrRefresh();
                    if(obligeeBeans != null && obligeeBeans.size() > 0){
                        mSmartRecyclerView.showData(taskId, obligeeBeans, Integer.MAX_VALUE);
                    }
                }
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(ObligeeMainActivity.this, "数据获取失败,请刷新重试", Toast.LENGTH_SHORT).show();
                }
            });
            tagMap = PictureManager.listCount();
    }

    @Override
    public void clickItem(int viewId, ObligeeBean item, int position) {
    }

    @Override
    public int createLayout() {
        return R.layout.item_obligee;
    }

    @Override
    public void createListItem(int viewId, ViewHolder holder, final ObligeeBean currentItem, List<ObligeeBean> list, int position) {

        Log.d("hsb","createListItem size " + list.size());
        AvatarImageView circle = holder.getView(R.id.item_obligee_ic);
        if(currentItem.getQLRMC()!=null){
            circle.setTextAndColor(currentItem.getQLRMC().substring(0,1), Color.RED);
        }
        String names = currentItem.getQLRMC();
        if(currentItem.getQlrlist() != null && currentItem.getQlrlist().size() > 0){
            for(ObligeeBean.Obligee obligee : currentItem.getQlrlist()){
                names += "; " + obligee.getQLRMC() + "(" + obligee.getQLRLXMC() +")";
            }
        }
        holder.setText(R.id.item_obligee_name, names);
        holder.setText(R.id.item_obligee_time, currentItem.getAddTime());
        holder.setText(R.id.item_obligee_num, currentItem.getPrenumbering() + "  " + currentItem.getDoorNumber());

//        ObligeeCountBean countBean = tagMap.get("" + currentItem.getId()) == null ? new ObligeeCountBean() : tagMap.get("" + currentItem.getId());
//
//        String statuStr;
//        if (countBean.getQuanliren() > 0 && countBean.getQita() > 0 && countBean.getQuanshulaiyuan() > 0 && countBean.getFangwu() > 0) {
//            statuStr = "已完成";
//        } else {
//            statuStr = "未完成";
//        }
//        holder.setText(R.id.item_obligee_status, statuStr);
//
//        ((AvatarImageView) holder.getView(R.id.item_obligee_quanliren)).setTextAndColor(countBean.getQuanliren() > 0 ? "" + countBean.getQuanliren() : "权", ContextCompat.getColor(ObligeeMainActivity.this, R.color.color1));
//        ((AvatarImageView) holder.getView(R.id.item_obligee_fangwu)).setTextAndColor(countBean.getFangwu() > 0 ? "" + countBean.getFangwu() : "房", ContextCompat.getColor(ObligeeMainActivity.this, R.color.color2));
//        ((AvatarImageView) holder.getView(R.id.item_obligee_quanshulaiyuan)).setTextAndColor(countBean.getQuanshulaiyuan() > 0 ? "" + countBean.getQuanshulaiyuan() : "源", ContextCompat.getColor(ObligeeMainActivity.this, R.color.color3));
//        ((AvatarImageView) holder.getView(R.id.item_obligee_qita)).setTextAndColor(countBean.getQita() > 0 ? "" + countBean.getQita() : "其", ContextCompat.getColor(ObligeeMainActivity.this, R.color.color4));

        holder.setOnClickListener(R.id.item_obligee_edit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/7/30 0030 编辑权利人
                UserUtil.getInstance().setObligee(currentItem.getQLRMC());
                UserUtil.getInstance().setObligeeId(Long.valueOf(currentItem.getQLRMID()));
                Intent intent = new Intent(ObligeeMainActivity.this, ObligeeSelectActivity.class);
                intent.putExtra("obligee", currentItem);
                startActivity(intent);
            }
        });
        holder.setOnClickListener(R.id.item_obligee_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/7/30 0030 删除权利人
                Smart.deleteObligee(currentItem, new AbstractCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ObligeeMainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(ObligeeMainActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                    }
                });
//                Log.d("hsb", "delete obligee");
//                List<ObligeeChild> childList = ObligeeManager.listObligeeChild(currentItem.getId());
//                for (ObligeeChild child : childList) {
//                    List<Picture> pics = PictureManager.listPictureWithObligee(child.getId());
//                    for (Picture pic : pics) {
//                        PictureManager.deletePicture(pic);
//                    }
//                    ObligeeManager.deleteObligeeChild(child.getId());
//                }
//                PictureUtil.deleteObligeeFile(currentItem);
//                ObligeeManager.deleteObligee(currentItem.getId());
//                mSmartRecyclerView.loadData();
//                fileScan(PictureUtil.NEW_PIC_ROOT_PATH+currentItem.getHouseNumber());
//                QuestionNaireManager.delete(currentItem.getId());
            }
        });
        holder.setOnClickListener(R.id.item_obligee_content, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/7/31 0031 进入收集首页
//                UserUtil.getInstance().setObligee(currentItem.getName());
//                UserUtil.getInstance().setObligeeId(currentItem.getId());
//                UserUtil.getInstance().setTags(tagMap.get("" + currentItem.getId()));
//                UserUtil.getInstance().setObligeeChildMainId(ObligeeManager.getObligeeChild(currentItem.getId()).getId());
//                startActivity(new Intent(ObligeeMainActivity.this, GatherTypeActivity.class));
            }
        });
    }

    @Override
    public void onLastPageHint() {

    }
}

    public void  fileScan(String file){
        Uri data = Uri.parse("file://" +file);
        sendBroadcast(new  Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }
}
