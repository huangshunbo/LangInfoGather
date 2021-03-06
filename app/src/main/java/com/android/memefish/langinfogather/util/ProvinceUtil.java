package com.android.memefish.langinfogather.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.android.memefish.langinfogather.bean.DistrictsBean;
import com.android.memefish.langinfogather.bean.JsonBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ProvinceUtil {


    public static ArrayList<DistrictsBean> initJsonData(Context context){
        ArrayList<DistrictsBean> list = new ArrayList<>();
        String jsonData = getJson(context,"districts.json");
        JsonParser parser1 = new JsonParser();
        JsonObject jsonObj1 = parser1.parse(jsonData).getAsJsonObject();
        Iterator iterator1 = jsonObj1.entrySet().iterator();
        while(iterator1.hasNext()) {
            DistrictsBean districtsBean = new DistrictsBean();
            ArrayList<DistrictsBean.Area> areas = new ArrayList<>();
            Map.Entry entry1 = (Map.Entry)iterator1.next();
            JsonObject jsonObj2 = parser1.parse(entry1.getValue().toString()).getAsJsonObject();
            Iterator iterator2 = jsonObj2.entrySet().iterator();
            while(iterator2.hasNext()) {
                DistrictsBean.Area bean = new DistrictsBean.Area();
                Map.Entry entry2 = (Map.Entry)iterator2.next();
                bean.setCode(entry2.getKey().toString());
                bean.setName(entry2.getValue().toString());
                areas.add(bean);
            }
            districtsBean.setMainCode(entry1.getKey().toString());
            districtsBean.setArea(areas);
            list.add(districtsBean);
        }
        return list;
    }

    public static void initJsonData(Context context,ArrayList<String> options1Items,ArrayList<ArrayList<String>> options2Items,ArrayList<ArrayList<ArrayList<String>>> options3Items) {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(context,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            options1Items.add(jsonBean.get(i).getName());
            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

    }

    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return detail;
    }
}
