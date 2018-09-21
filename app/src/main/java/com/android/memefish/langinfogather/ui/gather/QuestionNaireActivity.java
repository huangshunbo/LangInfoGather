package com.android.memefish.langinfogather.ui.gather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.memefish.langinfogather.R;
import com.android.memefish.langinfogather.db.Obligee;
import com.android.memefish.langinfogather.db.ObligeeChild;
import com.android.memefish.langinfogather.db.QuestionNaire;
import com.android.memefish.langinfogather.db.manager.ObligeeManager;
import com.android.memefish.langinfogather.db.manager.QuestionNaireManager;
import com.android.memefish.langinfogather.http.Smart;
import com.android.memefish.langinfogather.mvp.BasePresenter;
import com.android.memefish.langinfogather.mvp.base.BaseActivity;
import com.android.memefish.langinfogather.ocr.ORCBean;
import com.android.memefish.langinfogather.ocr.OcrManager;
import com.android.memefish.langinfogather.ocr.OnOcrListener;
import com.android.memefish.langinfogather.util.PictureUtil;
import com.android.memefish.langinfogather.util.ScreenUtil;
import com.android.memefish.langinfogather.util.UserUtil;
import com.android.minlib.smarthttp.okhttp.SmartHttp;
import com.android.minlib.smarttool.permission.PermissionCallback;
import com.android.minlib.smarttool.permission.SmartPermission;
import com.android.minlib.smarttool.tool.PhotoTool;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class QuestionNaireActivity extends BaseActivity implements View.OnClickListener {

    Toolbar mToolbar;
    List<String> names = new ArrayList<>();
    private ListPopupWindow mListPopupWindow;

    private static final String[] QLR_TYPE = new String[]{"个人", "企业", "事业单位", "国家机关", "其他"};
    private static final String[] QLR_SFZ_TYPE = new String[]{"身份证", "港澳台身份证", "护照", "户口簿", "军官证", "组织机构代码", "营业执照", "法人身份证", "其他"};
    private static final String[] TUDI_TYPE = new String[]{"农村宅基地", "工业用地", "城镇住宅用地", "公路用地", "坑塘水面", "沿海滩涂", "港口码头用地", "河流水面", "冰川及永久积雪", "机场用地", "盐碱地", "水库水面"};
    private static final String[] QLSD_TYPE = new String[]{"地上", "地表", "地下"};

    private TextView activityQuestionnaireQlrType;
    private TextView activityQuestionnaireSfzType;
    private EditText activityQuestionnaireSfzNum;
    private ImageView activityQuestionnaireSfzNumScan;
    private EditText activityQuestionnaireName;
    private EditText activityQuestionnaireSfzAddr;
    private EditText activityQuestionnaireFzDw;
    private EditText activityQuestionnaireDzMc;
    private ImageView activityQuestionnaireDzMcScan;
    private EditText activityQuestionnairePhone;
    private TextView activityQuestionnaireTudiType;
    private TextView activityQuestionnaireQlType;
    private EditText activityQuestionnaireJsnf;
    private RadioGroup RadioGroup1;
    private RadioButton RadioButton1;
    private RadioButton RadioButton2;
    private EditText activityQuestionnairePs;
    private TextView activityLoginSubmit;

    private static final int CAMERA_CODE = 10091;
    private static final int REQUEST_CODE = 10092;

    private static File tmpFile = new File(PictureUtil.PICTURE_PATH + "/orc/orc.jpg");
    private List<QuestionNaire> questionNaires = new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questinnaire);

        activityQuestionnaireQlrType = (TextView) findViewById(R.id.activity_questionnaire_qlr_type);
        activityQuestionnaireSfzType = (TextView) findViewById(R.id.activity_questionnaire_sfz_type);
        activityQuestionnaireSfzNum = (EditText) findViewById(R.id.activity_questionnaire_sfz_num);
        activityQuestionnaireSfzNumScan = (ImageView) findViewById(R.id.activity_questionnaire_sfz_num_scan);
        activityQuestionnaireName = (EditText) findViewById(R.id.activity_questionnaire_name);
        activityQuestionnaireSfzAddr = (EditText) findViewById(R.id.activity_questionnaire_sfz_addr);
        activityQuestionnaireFzDw = (EditText) findViewById(R.id.activity_questionnaire_fz_dw);
        activityQuestionnaireDzMc = (EditText) findViewById(R.id.activity_questionnaire_dz_mc);
        activityQuestionnaireDzMcScan = (ImageView) findViewById(R.id.activity_questionnaire_dz_scan);
        activityQuestionnairePhone = (EditText) findViewById(R.id.activity_questionnaire_phone);
        activityQuestionnaireTudiType = (TextView) findViewById(R.id.activity_questionnaire_tudi_type);
        activityQuestionnaireQlType = (TextView) findViewById(R.id.activity_questionnaire_ql_type);
        activityQuestionnaireJsnf = (EditText) findViewById(R.id.activity_questionnaire_jsnf);
        RadioGroup1 = (RadioGroup) findViewById(R.id.RadioGroup1);
        RadioButton1 = (RadioButton) findViewById(R.id.RadioButton1);
        RadioButton2 = (RadioButton) findViewById(R.id.RadioButton2);
        activityQuestionnairePs = (EditText) findViewById(R.id.activity_questionnaire_ps);
        activityLoginSubmit = (TextView) findViewById(R.id.activity_login_submit);

        mToolbar = findViewById(R.id.activity_questionnaire_toolbar);

        activityQuestionnaireQlrType.setOnClickListener(this);
        activityQuestionnaireSfzType.setOnClickListener(this);
        activityQuestionnaireSfzNumScan.setOnClickListener(this);
        activityQuestionnaireDzMcScan.setOnClickListener(this);
        activityQuestionnaireTudiType.setOnClickListener(this);
        activityQuestionnaireQlType.setOnClickListener(this);
        activityLoginSubmit.setOnClickListener(this);

        Obligee obligee = ObligeeManager.getObligee(UserUtil.getInstance().getObligeeId());
        List<ObligeeChild> childList = ObligeeManager.listObligeeChild(obligee.getId());
        names.clear();
        for(ObligeeChild child : childList){
            names.add(child.getName());
        }
        questionNaires = QuestionNaireManager.listQuestionNaire(UserUtil.getInstance().getObligeeId());
        initView(names.get(0));

        mToolbar.setTitle(names.get(0));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mToolbar.setTitle(item.getTitle());
        initView(item.getTitle().toString());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        for (String name : names) {
            menu.add(name);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void showPop(View anchorView, AdapterView.OnItemClickListener listener, String[] items) {
        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setDropDownGravity(Gravity.BOTTOM);
        mListPopupWindow.setAnchorView(anchorView);
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mListPopupWindow.setHeight(ScreenUtil.getScreenHeigh() / 2);
        mListPopupWindow.setOnItemClickListener(listener);
        mListPopupWindow.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items));
        mListPopupWindow.show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.activity_questionnaire_qlr_type) {
            showPop(activityQuestionnaireQlrType, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    activityQuestionnaireQlrType.setText(QLR_TYPE[i]);
                    mListPopupWindow.dismiss();
                }
            }, QLR_TYPE);
        } else if (id == R.id.activity_questionnaire_sfz_type) {
            showPop(activityQuestionnaireSfzType, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    activityQuestionnaireSfzType.setText(QLR_SFZ_TYPE[i]);
                    mListPopupWindow.dismiss();
                }
            }, QLR_SFZ_TYPE);
        } else if (id == R.id.activity_questionnaire_tudi_type) {
            showPop(activityQuestionnaireTudiType, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    activityQuestionnaireTudiType.setText(TUDI_TYPE[i]);
                    mListPopupWindow.dismiss();
                }
            }, TUDI_TYPE);
        } else if (id == R.id.activity_questionnaire_ql_type) {
            showPop(activityQuestionnaireQlType, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    activityQuestionnaireQlType.setText(QLSD_TYPE[i]);
                    mListPopupWindow.dismiss();
                }
            }, QLSD_TYPE);
        } else if (id == R.id.activity_questionnaire_sfz_num_scan) {
            if (TextUtils.equals(activityQuestionnaireSfzType.getText().toString(), "身份证")) {
                if (!tmpFile.getParentFile().exists()) {
                    tmpFile.getParentFile().mkdirs();
                }
                if (!tmpFile.exists()) {
                    try {
                        tmpFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Uri uri = FileProvider.getUriForFile(this, "com.android.memefish.langinfogather", tmpFile);
                PhotoTool.openCamera(this, uri, CAMERA_CODE);
            } else {
                Toast.makeText(this, "当前身份证类型不支持扫描,请手动输入", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.activity_questionnaire_dz_scan) {
            SmartPermission.getInstance().create(this).addPermission(new String[]{"android.permission.CAMERA"}).requestPermissionWithSetting(new PermissionCallback() {
                public void onPermissionsResult(@NonNull List<String> allows, @NonNull List<String> refuses, boolean isAllAllow) {
                    if (isAllAllow) {
                        Intent intent = new Intent(QuestionNaireActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    }

                }
            });
        }else if(id == R.id.activity_login_submit){
            save(mToolbar.getTitle().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CODE) {//从拍照途径获取的照片
                OcrManager.send(this, tmpFile, new OnOcrListener() {
                    @Override
                    public void onFinish(ORCBean bean) {
                        if (activityQuestionnaireSfzNum != null) {
                            if (!TextUtils.isEmpty(bean.getIdNum())) {
                                activityQuestionnaireSfzNum.setText(bean.getIdNum());
                            } else {
                                Toast.makeText(QuestionNaireActivity.this, "错误信息" + bean.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            } else if (requestCode == REQUEST_CODE) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Smart.getHtml(result, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(QuestionNaireActivity.this, "网址信息获取失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String html = response.body().string();
                            final List<String> addrs = getMatcher("(<div class=\"dzksfd1_lz2\">)(.*)(</div>)",html);
                            SmartHttp.runOnUIThread(new Runnable() {
                                public void run() {
                                    if(activityQuestionnaireDzMc != null){
                                        String result = "";
                                        for(String str : addrs){
                                            result += str;
                                        }
                                        activityQuestionnaireDzMc.setText(result);
                                    }
                                }
                            });

                        }
                    });


                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public List<String> getMatcher(String regex, String source) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result.add(matcher.group(2));
        }
        return result;
    }

    public void initView(String obligee){
        activityQuestionnaireQlrType.setText(QLR_TYPE[0]);
        activityQuestionnaireSfzType.setText(QLR_SFZ_TYPE[0]);
        activityQuestionnaireSfzNum.setText("");
        activityQuestionnaireName.setText("");
        activityQuestionnaireSfzAddr.setText("");
        activityQuestionnaireFzDw.setText("");
        activityQuestionnaireDzMc.setText("");
        activityQuestionnairePhone.setText("");
        activityQuestionnaireTudiType.setText(TUDI_TYPE[0]);
        activityQuestionnaireQlType.setText(QLSD_TYPE[0]);
        activityQuestionnaireJsnf.setText("");
        RadioButton1.setChecked(true);
        activityQuestionnairePs.setText("");
        for(QuestionNaire naire : questionNaires){
            if(naire.getObligee().equals(obligee)){
                activityQuestionnaireQlrType.setText(naire.getObligeeType());
                activityQuestionnaireSfzType.setText(naire.getObligeeIdentityType());
                activityQuestionnaireSfzNum.setText(naire.getObligeeIdentity());
                activityQuestionnaireName.setText(naire.getName());
                activityQuestionnaireSfzAddr.setText(naire.getIdentityAddress());
                activityQuestionnaireFzDw.setText(naire.getCertificateUnit());
                activityQuestionnaireDzMc.setText(naire.getAddress());
                activityQuestionnairePhone.setText(naire.getPhone());
                activityQuestionnaireTudiType.setText(naire.getLandType());
                activityQuestionnaireQlType.setText(naire.getRightSettingType());
                activityQuestionnaireJsnf.setText(naire.getBuildYear());
                if(naire.getIsRebuild()){
                    RadioButton1.setChecked(true);
                }else{
                    RadioButton2.setChecked(true);
                }
                activityQuestionnairePs.setText(naire.getRemark());

                return;
            }
        }
    }

    public void save(String obligee){
        QuestionNaire questionNaire = null;
        for(QuestionNaire naire : questionNaires){
            if(naire.getObligee().equals(obligee)){
                questionNaire = naire;
            }
        }
        if(questionNaire == null){
             questionNaire = new QuestionNaire();
             questionNaire.setUserId(UserUtil.getInstance().getUserId());
             questionNaire.setRegion(UserUtil.getInstance().getRegion());
             questionNaire.setObligeeId(UserUtil.getInstance().getObligeeId());
             questionNaire.setObligee(obligee);
        }
        questionNaire.setObligeeType(activityQuestionnaireQlrType.getText().toString());
        questionNaire.setObligeeIdentityType(activityQuestionnaireSfzType.getText().toString());
        questionNaire.setObligeeIdentity(activityQuestionnaireSfzNum.getText().toString());
        questionNaire.setName(activityQuestionnaireName.getText().toString());
        questionNaire.setIdentityAddress(activityQuestionnaireSfzAddr.getText().toString());
        questionNaire.setCertificateUnit(activityQuestionnaireFzDw.getText().toString());
        questionNaire.setAddress(activityQuestionnaireDzMc.getText().toString());
        questionNaire.setPhone(activityQuestionnairePhone.getText().toString());
        questionNaire.setLandType(activityQuestionnaireTudiType.getText().toString());
        questionNaire.setRightSettingType(activityQuestionnaireQlType.getText().toString());
        questionNaire.setBuildYear(activityQuestionnaireJsnf.getText().toString());
        questionNaire.setRebuild(RadioButton1.isChecked());
        questionNaire.setRemark(activityQuestionnairePs.getText().toString());
        Boolean isSuccess = QuestionNaireManager.insert(questionNaire);
        Toast.makeText(this,isSuccess ? "保存成功" : "保存失败,请填写完整",Toast.LENGTH_SHORT).show();
    }
}
