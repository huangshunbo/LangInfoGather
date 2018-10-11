package com.android.memefish.langinfogather.http;

import com.android.memefish.langinfogather.http.bean.ObligeeBean;
import com.android.memefish.langinfogather.http.bean.RegionBean;
import com.android.memefish.langinfogather.util.UserUtil;
import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Smart {
    private static OkHttpClient client = new OkHttpClient();

    private static final String DOMAIN = "http://api.tianyanchacha.com/";
    private static final String LOGIN = "API/Staff/LoginVerification";
    private static final String LIST_REGION = "API/XZQINFO/FindXZQINFOInfo";
    private static final String REGION_ADD = "API/XZQINFO/AddXZQINFO";
    private static final String REGION_REPLACE = "API/XZQINFO/UpXZQINFO";
    private static final String REGION_DELETE = "API/XZQINFO/DelXZQINFO";
    private static final String LIST_OBLIGEE = "API/QLRM/FindQLRMInfo";
    private static final String OBLIGEE_ADD = "API/QLRM/AddQLRM";
    private static final String OBLIGEE_REPLACE = "API/QLRM/UpQLRM";
    private static final String OBLIGEE_DELETE = "API/QLRM/DelQLRM";

    public static void getHtml(String url,Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }



    public static void login(String name,String password,Callback callback){
        HashMap<String,String> params = new HashMap<>();
        params.put("LoginName",name);
        params.put("Password",password);
        post(LOGIN,params,callback);
    }

    public static void listRegion(String pageIndex,Callback callback){
        HashMap<String,String> params = new HashMap<>();
        params.put("PageIndex",pageIndex);
        params.put("PageSize","10");
        params.put("StaffId", ""+UserUtil.getInstance().getLoginBean().getStaffId());
        post(LIST_REGION,params,callback);
    }

    public static void addOrReplaceRegion(boolean isAdd,RegionBean regionBean,Callback callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("Province",regionBean.getProvince());
        params.put("City",regionBean.getCity());
        params.put("County",regionBean.getCounty());
        params.put("AreaCode",regionBean.getAreaCode());
        params.put("DJQDM",regionBean.getDJQDM());
        params.put("DJZQDM",regionBean.getDJZQDM());
        params.put("VillageName",regionBean.getVillageName());
        params.put("StaffId",""+UserUtil.getInstance().getLoginBean().getStaffId());
        if(isAdd){
            post(REGION_ADD,params,callback);
        }else{
            params.put("XZQINFOID",regionBean.getXZQINFOID());
            post(REGION_REPLACE,params,callback);
        }
    }

    public static void deleteRegion(RegionBean regionBean,Callback callback){
        HashMap<String,String> params = new HashMap<>();
        params.put("Token","");
        params.put("XZQINFOID", regionBean.getXZQINFOID());
        post(REGION_DELETE,params,callback);
    }

    public static void listObligee(String pageIndex,Callback callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("Token","");
        params.put("PageIndex",pageIndex);
        params.put("PageSize","20");
        params.put("XZQINFOID",""+UserUtil.getInstance().getRegion());
        post(LIST_OBLIGEE,params,callback);
    }

    public static void addOrReplaceObligee(boolean isAdd,ObligeeBean obligeeBean,Callback callback){
        HashMap<String,String> params = new HashMap<>();
        params.put("Token","");
        params.put("XZQINFOID",""+UserUtil.getInstance().getRegion());
        params.put("Prenumbering",obligeeBean.getPrenumbering());
        params.put("DoorNumber",obligeeBean.getDoorNumber());
        params.put("QRLMC",obligeeBean.getQLRMC());
        params.put("QLRItemJson",obligeeBean.getQlrlist() == null ? "" : new Gson().toJson(obligeeBean.getQlrlist()));
        if(isAdd){
            post(OBLIGEE_ADD,params,callback);
        }else {
            params.put("QLRMID",obligeeBean.getQLRMID());
            post(OBLIGEE_REPLACE,params,callback);
        }
    }

    public static void deleteObligee(ObligeeBean obligeeBean,Callback callback){
        HashMap<String,String> params = new HashMap<>();
        params.put("Token","");
        params.put("QLRMID",obligeeBean.getQLRMID());
        post(OBLIGEE_DELETE,params,callback);
    }

    private static void post(String url, HashMap<String,String> params,Callback callback){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("ApiVer","0.0.1");
        builder.add("Token","123");
        builder.add("Data",new Gson().toJson(params));
        builder.add("Random","0123456789");
        builder.add("Sign","123321");
        Request request = new Request.Builder()
                .url(DOMAIN + url)
                .post(builder.build())
                .build();
        client.newCall(request).enqueue(callback);
    }



}
