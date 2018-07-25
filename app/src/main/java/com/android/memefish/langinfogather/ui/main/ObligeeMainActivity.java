package com.android.memefish.langinfogather.ui.main;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.bean.ObligeeBean;
import com.android.minlib.smartrefreshlayout.recycler.OnSmartFillListener;
import com.android.minlib.smartrefreshlayout.recycler.ViewHolder;

import java.util.ArrayList;
import java.util.List;

//权利人
public class ObligeeMainActivity extends MainBaseActivity {


    @Override
    void onInitList() {
        mSmartRecyclerView.setOnSmartFillListener(new MySmartFillListener());
        fabCreate.setImageResource(R.drawable.ic_group_add_black_24dp);
    }

    class MySmartFillListener implements OnSmartFillListener<ObligeeBean> {

        @Override
        public void onLoadData(final int taskId, int pageIndex) {
            mSmartRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<ObligeeBean> list = new ArrayList<>();
                    for(int i=0;i<5;i++){
                        ObligeeBean bean = new ObligeeBean();
                        bean.setName("张三"+i);
                        bean.setHouseNumber("10086");
                        bean.setStatus("未完成");
                        bean.setTime("2018-07-25 22:13");
                        bean.setTags(new String[]{"A","B"});
                        list.add(bean);
                    }
                    mSmartRecyclerView.showData(taskId,list,20);
                }
            },2000);
        }

        @Override
        public void clickItem(int viewId, ObligeeBean item, int position) {
        }

        @Override
        public int createLayout() {
            return R.layout.item_obligee;
        }

        @Override
        public void createListItem(int viewId, ViewHolder holder, ObligeeBean currentItem, List<ObligeeBean> list, int position) {
            holder.setText(R.id.item_obligee_name,currentItem.getName());
            holder.setText(R.id.item_obligee_status,currentItem.getStatus());
            holder.setText(R.id.item_obligee_time,currentItem.getTime());
            holder.setText(R.id.item_obligee_tags,currentItem.getTags()[0]);
        }

        @Override
        public void onLastPageHint() {

        }
    }
}
