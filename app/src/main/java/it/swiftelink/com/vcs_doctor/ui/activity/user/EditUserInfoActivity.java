package it.swiftelink.com.vcs_doctor.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.GetJsonDataUtil;
import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.auth.DoctorFile;
import it.swiftelink.com.factory.model.auth.UploadResModel;
import it.swiftelink.com.factory.model.user.EditUserParamModel;
import it.swiftelink.com.factory.model.user.UseInfoResModel;
import it.swiftelink.com.factory.presenter.auth.AuthContract;
import it.swiftelink.com.factory.presenter.auth.AuthPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.entity.JsonBean;
import it.swiftelink.com.vcs_doctor.ui.activity.auth.DepartmentActivity;
import it.swiftelink.com.vcs_doctor.util.DateUtils;
import it.swiftelink.com.vcs_doctor.util.RxKeyboardTool;

@BindEventBus
public class EditUserInfoActivity extends BasePresenterActivity<AuthContract.Presenter> implements AuthContract.View {
    @BindView(R.id.iv_user_header)
    ImageView userheaderIv;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.idCardNum_et)
    EditText idCardNumEt;
    @BindView(R.id.origin_et)
    EditText originEt;
    @BindView(R.id.permaResi_et)
    EditText permaResiEt;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.birthday_et)
    EditText birthdayEt;
    @BindView(R.id.age_et)
    EditText ageEt;
    @BindView(R.id.hospital_et)
    EditText hospitalEt;
    @BindView(R.id.departments_et)
    EditText departmentsEt;
    @BindView(R.id.years_et)
    EditText yearsEt;
    @BindView(R.id.departmentTelephone_et)
    EditText departmentTelephoneEt;
    @BindView(R.id.emergencyContact_et)
    EditText emergencyContactEt;
    @BindView(R.id.mandarin_cb)
    CheckBox mandarinCb;
    @BindView(R.id.cantonese_cb)
    CheckBox cantoneseCb;
    @BindView(R.id.english_cb)
    CheckBox englishCb;
    @BindView(R.id.fullJob_rb)
    RadioButton fullJobRb;
    @BindView(R.id.partJob_rb)
    RadioButton partJobRb;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.address_et)
    EditText addressEt;
    @BindView(R.id.jobGroup)
    RadioGroup jobGroup;
    @BindView(R.id.sexGroup)
    RadioGroup sexGroup;
    @BindView(R.id.description_et)
    EditText descriptionEt;
    @BindView(R.id.stateView)
    StateView stateView;

    @BindView(R.id.titlesNs)
    NiceSpinner titlesNs;

    @BindView(R.id.emergency_contactphone_et)
    EditText emergencycontactphoneEt;

    @BindView(R.id.qualification_number)
    EditText qualificationNumberEt;
    @BindView(R.id.doctorstitle_Positive)
    ImageView doctorstitlePositive;
    @BindView(R.id.doctorstitle_Side)
    ImageView doctorstitleSide;
    @BindView(R.id.qualification1_Positive)
    ImageView qualification1Positive;
    @BindView(R.id.qualification1_Side)
    ImageView qualification1Side;
    @BindView(R.id.other_Positive)
    ImageView otherPositive;
    @BindView(R.id.other_Side)
    ImageView otherSide;
    @BindView(R.id.doctorstitlenumber)
    EditText doctorstitleNumberEt;
    @BindView(R.id.qualification1_number)
    EditText qualification1NumberEt;
    @BindView(R.id.qualification_Positive)
    ImageView qualificationPositive;
    @BindView(R.id.qualification_Side)
    ImageView qualificationSide;
    @BindView(R.id.idCard_Positive)
    ImageView idCardPositiveIv;
    @BindView(R.id.idCard_Side)
    ImageView idCardSideIv;

    @BindView(R.id.otherNumberEt)
    EditText otherNumberEt;
    ISListConfig configUserIcon;
    ISListConfig config;
    private String language;
    private String nature;
    private String departmentId;
    private String sex;
    private String userIconPath;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;
    private String titles;
    String qualificationPositivePathId;
    String qualificationSidePathId;

    String doctorstitlePositivePathId;
    String doctorstitleSidePathId;

    String qualification1PositivePathId;
    String qualification1SidePathId;

    String otherPositivePathId;
    String otherSidePathId;


    String qualificationPositivePath;
    String qualificationSidePath;

    String doctorstitlePositivePath;
    String doctorstitleSidePath;

    String qualification1PositivePath;
    String qualification1SidePath;
    String otherPositivePath;
    String otherSidePath;
    String idCardSidePath;
    String idCardSideId;
    String idCardPositivePath;
    String idCardPositiveId;

    String type = "";

    UseInfoResModel useInfoResModel;

    private Map<String, String> languages = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        title.setText(getString(R.string.editing_personal_information));
        dataDisplay();

        // 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        // 自由配置选项
        config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#0694F8"))
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#0694F8"))
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();
        // 自由配置选项
        configUserIcon = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#0694F8"))
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#0694F8"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();

        jobGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.partJob_rb:
                        nature = "PartTime";
                        break;
                    case R.id.fullJob_rb:
                        nature = "FullTime";
                        break;
                }
            }
        });

        mandarinCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    languages.put("zh_CN", Constants.ZH_CN);
                }else{
                    languages.remove("zh_CN");
                }
            }
        });

        cantoneseCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    languages.put("zh_TW", Constants.ZH_TW);
                }else{
                    languages.remove("zh_TW");
                }
            }
        });

        englishCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    languages.put("en_US", Constants.EN_US);
                }else{
                    languages.remove("en_US");
                }
            }
        });


        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        titlesNs.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                if (position == 1) {
                    //主治医生
                    titles = Constants.ATTENDING;
                } else if (position == 2) {
                    //住院医师
                    titles = Constants.RESIDENTS;
                } else if (position == 3) {
                    //副主任医师
                    titles = Constants.DEPUTYCHIEFPHYSICIAN;
                } else if (position == 4) {
                    //主任医师
                    titles = Constants.CHIEFPHYSICIAN;
                }
            }
        });
    }


    /**
     * 数据回显
     */

    void dataDisplay() {
        useInfoResModel = (UseInfoResModel) getIntent().getSerializableExtra("useInfoResModel");
        if (useInfoResModel != null && useInfoResModel.getData() != null) {
            UseInfoResModel.DataBean dataBean = useInfoResModel.getData();
            GlideLoadUtils.getInstance().glideLoad(this, dataBean.getHeadImg(), userheaderIv, R.mipmap.icon_doctor_man);
            userIconPath = dataBean.getHeadImg();

            nameEt.setHint(!TextUtils.isEmpty(dataBean.getName()) ? dataBean.getName() : "");
            if (Constants.MALE.equals(dataBean.getGender())) {
                sexGroup.check(R.id.nanRb);
            } else {
                sexGroup.check(R.id.nvRb);
            }
            if (!TextUtils.isEmpty(dataBean.getBirthday())) {
                birthdayEt.setText(DateUtils.convertToString(Long.parseLong(dataBean.getBirthday()), DateUtils.DATE_FORMAT));
            }
            ageEt.setHint(!TextUtils.isEmpty(dataBean.getAge()) ? dataBean.getAge() : "");
            originEt.setHint(!TextUtils.isEmpty(dataBean.getBirthplace()) ? dataBean.getBirthplace() : "");
            permaResiEt.setHint(!TextUtils.isEmpty(dataBean.getLocation()) ? dataBean.getLocation() : "");
            addressEt.setText(!TextUtils.isEmpty(dataBean.getContactAddr()) ? dataBean.getContactAddr() : "");
            hospitalEt.setText(!TextUtils.isEmpty(dataBean.getHospital()) ? dataBean.getHospital() : "");
            departmentsEt.setText(!TextUtils.isEmpty(dataBean.getSectionOfficeName()) ? dataBean.getSectionOfficeName() : "");
            departmentId = dataBean.getSectionOfficeId();
            yearsEt.setText(!TextUtils.isEmpty(dataBean.getPracticeYear()) ? dataBean.getPracticeYear() : "");
            departmentTelephoneEt.setText(!TextUtils.isEmpty(dataBean.getSectionOfficeTel()) ? dataBean.getSectionOfficeTel() : "");
            emergencyContactEt.setText(!TextUtils.isEmpty(dataBean.getEmergencyContact()) ? dataBean.getEmergencyContact() : "");
            String lanaguge = dataBean.getLanguage();
            StringBuilder stringBuilder = new StringBuilder();
            if (lanaguge.contains(",")) {
                String[] lanaguges = lanaguge.split(",");
                for (String lan : lanaguges) {

                    if (lan.equals(Common.CommonStr.LANGUAGE1)) {
                        languages.put("zh_CN", lan);
                        mandarinCb.setChecked(true);
                        Log.i("lqi","lan");
                    } else if (lan.equals(Common.CommonStr.LANGUAGE2)) {
                        languages.put("zh_TW", lan);
                        cantoneseCb.setChecked(true);
                    } else if (lan.equals(Common.CommonStr.LANGUAGE3)) {
                        languages.put("en_US", lan);
                        englishCb.setChecked(true);
                    }
                }
            }
            if (dataBean.getNature().equals("PartTime")) {
                jobGroup.check(R.id.partJob_rb);
            } else {
                jobGroup.check(R.id.fullJob_rb);
            }
            emergencycontactphoneEt.setText(!TextUtils.isEmpty(dataBean.getEmergencyContactTel()) ? dataBean.getEmergencyContactTel() : "");
            descriptionEt.setText(!TextUtils.isEmpty(dataBean.getDescription()) ? dataBean.getDescription() : "");
            departmentId = dataBean.getSectionOfficeId();
            if (Constants.ATTENDING.equals(dataBean.getJobTitle())) {
                //主治医生
                titlesNs.setText(getString(R.string.attending_doctor));
                titles = Constants.ATTENDING;

            } else if (Constants.RESIDENTS.equals(dataBean.getJobTitle())) {
                //住院医师
                titlesNs.setText(getString(R.string.residents));
                titles = Constants.RESIDENTS;
            } else if (Constants.DEPUTYCHIEFPHYSICIAN.equals(dataBean.getJobTitle())) {
                //副主任医师
                titlesNs.setText(getString(R.string.deputy_chief_physician));
                titles = Constants.DEPUTYCHIEFPHYSICIAN;
            } else if (Constants.CHIEFPHYSICIAN.equals(dataBean.getJobTitle())) {
                //主任医师
                titlesNs.setText(getString(R.string.chief_physician));
                titles = Constants.CHIEFPHYSICIAN;
            }
            List<UseInfoResModel.DataBean.DoctorFilesBean> doctorFilesBeans = dataBean.getDoctorFiles();
            int size = doctorFilesBeans.size();
            for (int i = 0; i < size; i++) {
                String type = doctorFilesBeans.get(i).getType();
                String imagePath = doctorFilesBeans.get(i).getFilePath();
                String no = doctorFilesBeans.get(i).getNo();
                String fileid = doctorFilesBeans.get(i).getFileId();
                if ("IdCard_Front".equals(type)) {
                    //身份证正面
                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, idCardPositiveIv, R.mipmap.img_card02);
                    idCardSidePath = imagePath;
                    idCardSideId = fileid;
                } else if ("IdCard_Reverse".equals(type)) {
                    //身份证反面
                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, idCardSideIv, R.mipmap.img_card01);
                    idCardPositiveId = fileid;
                    idCardPositivePath = imagePath;
                }
                if ("IdCard_Front".equals(type) || "IdCard_Reverse".equals(type)) {
                    idCardNumEt.setText(no);
                }

                if (type.equals("Practice_Reverse")) {
                    //执业证书反面
                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, qualificationPositive, R.mipmap.certificate2);

                    qualificationPositivePathId = fileid;
                    qualificationPositivePath = imagePath;

                } else if (type.equals("Practice_Front")) {
                    //执业证书正面
                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, qualificationSide, R.mipmap.certificate1);

                    qualificationSidePathId = fileid;
                    qualificationSidePath = imagePath;
                }

                if (type.equals("Practice_Front") || type.equals("Practice_Reverse")) {
                    qualificationNumberEt.setText(no);

                }
                if (type.equals("Job_Certificate_Front")) {
                    //职称证书正面

                    doctorstitlePositivePathId = fileid;
                    doctorstitlePositivePath = imagePath;

                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, doctorstitlePositive, R.mipmap.certificate1);
                } else if (type.equals("Job_Certificate_Reverse")) {
                    //职称证书反面
                    doctorstitleSidePathId = fileid;
                    doctorstitleSidePath = imagePath;
                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, doctorstitleSide, R.mipmap.certificate1);

                }

                if (type.equals("Job_Certificate_Reverse") || type.equals("Job_Certificate_Front")) {
                    //职称证书编号
                    doctorstitleNumberEt.setText(no);
                }

                if (type.equals("Qualification_Front")) {
                    //资格证书正面照
                    qualification1PositivePathId = fileid;
                    qualification1PositivePath = imagePath;


                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, qualification1Positive, R.mipmap.certificate1);
                } else if (type.equals("Qualification_Reverse")) {

                    qualification1SidePathId = fileid;
                    qualification1SidePath = imagePath;
                    //资格证书反面照
                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, qualification1Side, R.mipmap.certificate2);
                }

                if (type.equals("Qualification_Reverse") || type.equals("Qualification_Front")) {
                    //资格证书编号
                    qualification1NumberEt.setText(no);
                }

                if (type.equals("Other_Certificate_One")) {

                    otherPositivePathId = fileid;
                    otherPositivePath = imagePath;
                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, otherPositive, R.mipmap.certificate1);

                } else if (type.equals("Other_Certificate_Two")) {
                    GlideLoadUtils.getInstance().glideLoad(this, imagePath, otherSide, R.mipmap.certificate2);
                    otherSidePathId = fileid;
                    otherSidePath = imagePath;

                }


            }

        }
    }


    @OnClick({R.id.next_btn, R.id.btn_back,
            R.id.icon_layout, R.id.birthdayBtn,
            R.id.originBtn, R.id.permaResiBtn,
            R.id.departmentLayout, R.id.qualification_Positive,
            R.id.qualification_Side,
            R.id.doctorstitle_Positive
            , R.id.doctorstitle_Side, R.id.qualification1_Positive,
            R.id.idCard_Positive, R.id.idCard_Side, R.id.qualification1_Side,
            R.id.other_Positive, R.id.other_Side})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.next_btn:
                save();
                break;
            case R.id.originBtn:
//                if (isLoaded) {
//                    showPickerView(100);
//                }
                break;
            case R.id.permaResiBtn:
//                if (isLoaded) {
//                    showPickerView(101);
//                }
                break;
            case R.id.birthdayBtn:
//                showTimePickerView();
                break;
            case R.id.departmentLayout:
                toStartActivityForResult(DepartmentActivity.class, Constants.DEPARTMENTCODE);
                break;
            case R.id.icon_layout:
                // 跳转到图片选择器
                ISNav.getInstance().toListActivity(this, configUserIcon, Constants.USERICON);
                break;
            case R.id.qualification_Positive:
                //医生职业证书 正面
                // 跳转到图片选择器
                ISNav.getInstance().toListActivity(this, config, Constants.QUALIFICATION_POSITIVE);
                break;
            case R.id.qualification_Side:
                //医生职业证书 反面

                ISNav.getInstance().toListActivity(this, config, Constants.QUALIFICATION_SIDE);
                break;
            case R.id.doctorstitle_Positive:
                //医生职程证书 正面

                ISNav.getInstance().toListActivity(this, config, Constants.DOCTORSTITLE_POSITIVE);
                break;
            case R.id.doctorstitle_Side:
                //医生职程证书 反面

                ISNav.getInstance().toListActivity(this, config, Constants.DOCTORSTITLE_SIDE);
                break;
            case R.id.qualification1_Positive:
                //医生资格证书 正面
                ISNav.getInstance().toListActivity(this, config, Constants.QUALIFICATION1_POSITIVE);
                break;
            case R.id.qualification1_Side:
                //医生资格证书 反面
                ISNav.getInstance().toListActivity(this, config, Constants.QUALIFICATION1_SIDE);

                break;
            case R.id.other_Positive:
                //其他证书 正面
                ISNav.getInstance().toListActivity(this, config, Constants.OTHER_POSITIVE);
                break;
            case R.id.other_Side:
                //其他证书 反面
                ISNav.getInstance().toListActivity(this, config, Constants.OTHER_SIDE);
                break;

            case R.id.idCard_Positive:
//                // 跳转到图片选择器
                ISNav.getInstance().toListActivity(this, config, Constants.IDCARD_PIDE);
                break;
            case R.id.idCard_Side:
                // 跳转到图片选择器
                ISNav.getInstance().toListActivity(this, config, Constants.IdCard_SIDE);
                break;
        }
    }

    /**
     * 显示时间选择器
     */
    private void showTimePickerView() {
        RxKeyboardTool.hideSoftInput(this);

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this,
                new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        long dateTime = date.getTime();
                        String dateStr = DateUtils.convertToString(dateTime, DateUtils.DATE_FORMAT);
                        int birthdayYear = Integer.parseInt(DateUtils.convertToString(dateTime, DateUtils.FORMAT_YYYY));
                        int year = Integer.parseInt(DateUtils.getDateFormat(DateUtils.FORMAT_YYYY));
                        ageEt.setText(year - birthdayYear + "");
                        birthdayEt.setText(dateStr);
                        long time = date.getTime();
                        useInfoResModel.getData().setBirthday(String.valueOf(time));
                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                .build();
        pvTime.show();

        if(null!=useInfoResModel && !TextUtils.isEmpty(useInfoResModel.getData().getBirthday())){
            long timeL = Long.parseLong(useInfoResModel.getData().getBirthday());
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(timeL);
            pvTime.setDate(calendar);
        }
    }


    @Override
    protected void initData() {
        super.initData();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    protected AuthContract.Presenter initPresenter() {
        return new AuthPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 保存
     */
    private void save() {

        if (TextUtils.isEmpty(userIconPath)) {
            //头像为空
            App.showToast(getString(R.string.uplod_usericon));

            return;
        }

        String hospital = hospitalEt.getText().toString();
        if (TextUtils.isEmpty(hospital)) {
            App.showToast(getString(R.string.input_hospital_title));
            return;
        }
        if (TextUtils.isEmpty(departmentId)) {
            App.showToast(getString(R.string.departments_not_mpty));
            return;
        }

        String sectionOfficeTel = departmentTelephoneEt.getText().toString();

        String practiceYear = yearsEt.getText().toString();


        if (TextUtils.isEmpty(practiceYear)) {
            App.showToast(getString(R.string.years_is_not_empty));
            return;
        }

        String emergencyContact = emergencyContactEt.getText().toString();


        if (TextUtils.isEmpty(emergencyContact)) {
            App.showToast(getString(R.string.emergencycontact_notempty));
            return;
        }

        String emergencyContactTel = emergencycontactphoneEt.getText().toString();
        if (TextUtils.isEmpty(emergencyContactTel)) {
            App.showToast(R.string.emergencycontactphone_notempty);

            return;
        }


        String contactAddr = addressEt.getText().toString();

        if (TextUtils.isEmpty(contactAddr)) {
            App.showToast(R.string.address_not_empty);
            return;
        }

        if (languages == null || languages.size() <= 0) {
            App.showToast(R.string.choose_language);
            return;
        }
        StringBuilder languageBuilder = new StringBuilder();
        for (String language : languages.keySet()) {
            languageBuilder.append(language + ",");
        }

        String description = descriptionEt.getText().toString();
        if (TextUtils.isEmpty(description)) {
            App.showToast(R.string.introduction_not_empty);
            return;
        }


        String idCardNum = idCardNumEt.getText().toString();


        if (TextUtils.isEmpty(idCardNum)) {
            App.showToast(R.string.input_idcardno);
            return;
        }


        String qualificationNumber = qualificationNumberEt.getText().toString().trim();

        if (TextUtils.isEmpty(qualificationNumber)) {

            App.showToast(R.string.select_qualificationnumber);
            return;
        }


        List<DoctorFile> doctorFiles = new ArrayList<>();

        String doctorstitleNumber = doctorstitleNumberEt.getText().toString().trim();

        String qualification1Number = qualification1NumberEt.getText().toString().trim();

        if (!TextUtils.isEmpty(idCardPositiveId) && !TextUtils.isEmpty(idCardPositivePath)
                && !TextUtils.isEmpty(idCardSideId) && !TextUtils.isEmpty(idCardSidePath)) {
            //身份证
            DoctorFile doctorFileIdCardFront = new DoctorFile();
            doctorFileIdCardFront.setNo(idCardNum);
            doctorFileIdCardFront.setFileId(idCardPositiveId);
            doctorFileIdCardFront.setFilePath(idCardPositivePath);
            doctorFileIdCardFront.setType("IdCard_Front");
            doctorFiles.add(doctorFileIdCardFront);
            DoctorFile doctorFileIdCardReverse = new DoctorFile();
            doctorFileIdCardReverse.setNo(idCardNum);
            doctorFileIdCardReverse.setFileId(idCardSideId);
            doctorFileIdCardReverse.setFilePath(idCardSidePath);
            doctorFileIdCardReverse.setType("IdCard_Reverse");
            doctorFiles.add(doctorFileIdCardReverse);
        }
        if (!TextUtils.isEmpty(qualificationPositivePathId) && !TextUtils.isEmpty(qualificationPositivePath) && !TextUtils.isEmpty(qualificationSidePath) && !TextUtils.isEmpty(qualificationSidePathId)) {
            //执业证书
            DoctorFile practiceReverseFile = new DoctorFile();
            practiceReverseFile.setType("Practice_Front");
            practiceReverseFile.setFileId(qualificationPositivePathId);
            practiceReverseFile.setFilePath(qualificationPositivePath);
            practiceReverseFile.setNo(qualificationNumber);
            doctorFiles.add(practiceReverseFile);
            DoctorFile PracticeFrontFile = new DoctorFile();
            PracticeFrontFile.setType("Practice_Reverse");
            PracticeFrontFile.setFileId(qualificationSidePathId);
            PracticeFrontFile.setFilePath(qualificationSidePath);
            PracticeFrontFile.setNo(qualificationNumber);
            doctorFiles.add(PracticeFrontFile);

        }

        //职称证书
        if (!"".equals(doctorstitlePositivePathId) && !"".equals(doctorstitlePositivePath)) {
            DoctorFile jobDoctorFileFront = new DoctorFile();
            jobDoctorFileFront.setNo(doctorstitleNumber);
            jobDoctorFileFront.setType("Job_Certificate_Front");
            jobDoctorFileFront.setFileId(doctorstitlePositivePathId);
            jobDoctorFileFront.setFilePath(doctorstitlePositivePath);
            doctorFiles.add(jobDoctorFileFront);
            DoctorFile jobDoctorFileReverse = new DoctorFile();
            jobDoctorFileReverse.setNo(doctorstitleNumber);
            jobDoctorFileReverse.setType("Job_Certificate_Reverse");
            jobDoctorFileReverse.setFileId(doctorstitleSidePathId);
            jobDoctorFileReverse.setFilePath(doctorstitleSidePath);
            doctorFiles.add(jobDoctorFileReverse);
        }
        if (qualification1PositivePathId != "" && qualification1PositivePath != "") {
            //资格证书
            DoctorFile qualification1Front = new DoctorFile();
            qualification1Front.setNo(qualification1Number);
            qualification1Front.setFileId(qualification1PositivePathId);
            qualification1Front.setFilePath(qualification1PositivePath);
            qualification1Front.setType("Qualification_Front");
            doctorFiles.add(qualification1Front);
            DoctorFile qualification1Reverse = new DoctorFile();
            qualification1Reverse.setNo(qualification1Number);
            qualification1Reverse.setFileId(qualification1SidePathId);
            qualification1Reverse.setFilePath(qualification1SidePath);
            qualification1Reverse.setType("Qualification_Reverse");
            doctorFiles.add(qualification1Reverse);
        }
        //其他证书
        if (!TextUtils.isEmpty(otherPositivePathId) && !TextUtils.isEmpty(otherSidePathId)) {
            DoctorFile otherDoctorFile1 = new DoctorFile();
            otherDoctorFile1.setType("Other_Certificate_One");
            otherDoctorFile1.setFileId(otherPositivePathId);
            otherDoctorFile1.setFilePath(otherPositivePath);
            doctorFiles.add(otherDoctorFile1);
            DoctorFile otherDoctorFile2 = new DoctorFile();
            otherDoctorFile2.setType("Other_Certificate_Two");
            otherDoctorFile2.setFileId(otherSidePathId);
            otherDoctorFile2.setFilePath(otherSidePath);
            doctorFiles.add(otherDoctorFile2);
        }


        EditUserParamModel editUserParamModel = new EditUserParamModel();
        editUserParamModel.setDoctorFileApprovalVO(doctorFiles);
        editUserParamModel.setHeadImg(userIconPath);
        editUserParamModel.setHospital(hospital);
        editUserParamModel.setSectionOfficeId(departmentId);
        editUserParamModel.setSectionOfficeTel(sectionOfficeTel);
        editUserParamModel.setPracticeYear(practiceYear);
        editUserParamModel.setEmergencyContact(emergencyContact);
        editUserParamModel.setEmergencyContactTel(emergencyContactTel);
        editUserParamModel.setContactAddr(contactAddr);
        editUserParamModel.setLanguage(languageBuilder.toString());
        editUserParamModel.setDescription(description);
        mPresenter.editUserInfo(editUserParamModel);
    }

    private void showPickerView(final int type) {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;

                if (type == 100) {
                    originEt.setText(opt1tx);
                } else {
                    permaResiEt.setText(tx);
                }
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(ContextCompat.getColor(EditUserInfoActivity.this, R.color.lineColor1))
                .setTextColorCenter(ContextCompat.getColor(EditUserInfoActivity.this, R.color.textColor7)) //设置选中项文字颜色
                .setContentTextSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };


    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
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
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.QUALIFICATION_SIDE) {
            departmentsEt.setText(data.getStringExtra("department"));
            departmentId = data.getStringExtra("departmentId");
        }
        // 图片选择结果回调
        if (requestCode == Constants.USERICON && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                type = "usericon";
                GlideLoadUtils.getInstance().glideLoadCircle(this, pathList.get(0), userheaderIv, R.mipmap.img_dc_man);
                mPresenter.uploadImage(pathList.get(0));
            }
        }
        // 图片选择结果回调
        if (requestCode == Constants.QUALIFICATION_POSITIVE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                type = "qualificationPositive";
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), qualificationPositive, R.mipmap.certificate1);
                mPresenter.uploadImage(pathList.get(0));
            }
        } else if (requestCode == Constants.QUALIFICATION_SIDE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {

                type = "qualificationSide";
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), qualificationSide, R.mipmap.certificate2);
                mPresenter.uploadImage(pathList.get(0));
            }

        } else if (requestCode == Constants.DOCTORSTITLE_POSITIVE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                type = "doctorstitlePositive";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), doctorstitlePositive, R.mipmap.certificate1);
            }

        } else if (requestCode == Constants.DOCTORSTITLE_SIDE && resultCode == RESULT_OK && data != null) {

            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {

                type = "doctorstitleSide";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), doctorstitleSide, R.mipmap.certificate2);
            }

        } else if (requestCode == Constants.QUALIFICATION1_POSITIVE && resultCode == RESULT_OK && data != null) {

            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                type = "qualification1Positive";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), qualification1Positive, R.mipmap.certificate1);
            }

        } else if (requestCode == Constants.QUALIFICATION1_SIDE && resultCode == RESULT_OK && data != null) {

            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                type = "qualification1Side";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), qualification1Side, R.mipmap.certificate2);
            }

        } else if (requestCode == Constants.OTHER_POSITIVE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                type = "otherPositive";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), otherPositive, R.mipmap.certificate1);
            }
        } else if (requestCode == Constants.OTHER_SIDE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                type = "otherSide";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), otherSide, R.mipmap.certificate2);
            }
        } else if (requestCode == Constants.IDCARD_PIDE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            type = "IdCard_Front";
            mPresenter.uploadImage(pathList.get(0));
            GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), idCardPositiveIv, R.mipmap.certificate1);

        } else if (requestCode == Constants.IdCard_SIDE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            type = "IdCard_Reverse";
            mPresenter.uploadImage(pathList.get(0));
            GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), idCardSideIv, R.mipmap.certificate2);

        }


    }

    @Override
    public void uploadSuess(UploadResModel uploadResModel) {
        showContent();

        if (uploadResModel != null && uploadResModel.getFiles() != null && uploadResModel.getFiles().size() > 0) {
            if (type.equals("usericon")) {
                userIconPath = uploadResModel.getFiles().get(0).getUrl();
            }
            if (type.equals("qualificationPositive")) {
                qualificationPositivePathId = uploadResModel.getFiles().get(0).getId();
                qualificationPositivePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (type.equals("qualificationSide")) {
                qualificationSidePathId = uploadResModel.getFiles().get(0).getId();
                qualificationSidePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (type.equals("doctorstitlePositive")) {
                doctorstitlePositivePathId = uploadResModel.getFiles().get(0).getId();
                doctorstitlePositivePath = uploadResModel.getFiles().get(0).getUrl();

            } else if (type.equals("doctorstitleSide")) {
                doctorstitleSidePathId = uploadResModel.getFiles().get(0).getId();
                doctorstitleSidePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (type.equals("qualification1Side")) {
                qualification1SidePathId = uploadResModel.getFiles().get(0).getId();
                qualification1SidePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (type.equals("qualification1Positive")) {
                qualification1PositivePathId = uploadResModel.getFiles().get(0).getId();
                qualification1PositivePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (type.equals("otherPositive")) {
                otherPositivePathId = uploadResModel.getFiles().get(0).getId();
                otherPositivePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (type.equals("otherSide")) {
                otherSidePathId = uploadResModel.getFiles().get(0).getId();
                otherSidePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (type.equals("IdCard_Front")) {
                //正面
                idCardPositiveId = uploadResModel.getFiles().get(0).getId();
                idCardPositivePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (type.equals("IdCard_Reverse")) {
                //反面
                idCardSidePath = uploadResModel.getFiles().get(0).getUrl();
                idCardSideId = uploadResModel.getFiles().get(0).getId();
            }
        }
    }

    @Override
    public void authInfoSuess() {

    }

    @Override
    public void authSuess() {

    }

    @Override
    public void editUserInfoSuess() {
        showContent();
        App.showToast(R.string.successful_revision);
        finish();
    }

    @Override
    public void uploadImageProgressDialog(int progress) {

    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
        if (type == Common.UrlApi.POST_AUTHINFO) {
            App.showToast(errorMsg);
        }
    }
}
