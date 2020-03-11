package it.swiftelink.com.vcs_doctor.ui.activity.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.GetJsonDataUtil;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.user.UseInfoResModel;
import it.swiftelink.com.factory.presenter.user.UserInfoContract;
import it.swiftelink.com.factory.presenter.user.UserInfoPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.entity.JsonBean;
import it.swiftelink.com.vcs_doctor.util.DateUtils;
import it.swiftelink.com.vcs_doctor.util.RxKeyboardTool;

/**
 * 填写认证信息
 */
@BindEventBus
public class AuthenticationActivity extends BasePresenterActivity<UserInfoContract.Presenter> implements UserInfoContract.View {
    @BindView(R.id.tv_title)
    TextView title;
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
    @BindView(R.id.titlesNs)
    NiceSpinner titlesNs;
    @BindView(R.id.departmentTelephone_et)
    EditText departmentTelephoneEt;
    @BindView(R.id.emergencyContact_et)
    EditText emergencyContactEt;
    @BindView(R.id.emergency_contactphone_et)
    EditText emergencyContactphoneEt;
    @BindView(R.id.mandarin_rb)
    CheckBox mandarinRb;
    @BindView(R.id.cantonese_rb)
    CheckBox cantoneseRb;
    @BindView(R.id.english_rb)
    CheckBox englishRb;
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
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private Map<String, String> languages = new HashMap<>();
    private String nature;
    private String departmentId;
    private String sex;
    private String titles;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private String yearString;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;
    UseInfoResModel.DataBean dataBean;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        title.setText(getString(R.string.tianxie_renzheng));
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
        mandarinRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    languages.put("zh_CN", Constants.ZH_CN);
                } else {
                    languages.remove("zh_CN");
                }
            }
        });

        cantoneseRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    languages.put("zh_TW", Constants.ZH_TW);
                } else {
                    languages.remove("zh_TW");
                }
            }
        });

        englishRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    languages.put("en_US", Constants.EN_US);
                } else {
                    languages.remove("en_US");
                }
            }
        });
        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.nanRb:
                        sex = Constants.MALE;
                        break;
                    case R.id.nvRb:
                        sex = Constants.FEMALE;
                        break;
                }
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

    @OnClick({R.id.next_btn, R.id.btn_back, R.id.birthdayBtn, R.id.originBtn, R.id.permaResiBtn, R.id.departmentLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.next_btn:
                if (next()) {
                    toActivity(IntroductionActivity.class);
                }
                break;
            case R.id.originBtn:
                if (isLoaded) {
                    showPickerView(100);
                }
                break;
            case R.id.permaResiBtn:
                if (isLoaded) {
                    showPickerView(101);
                }
                break;
            case R.id.birthdayBtn:
                showTimePickerView();
                break;
            case R.id.departmentLayout:
                toStartActivityForResult(DepartmentActivity.class, 100);
                break;
        }
    }

    /**
     * 显示时间选择器
     */
    private void showTimePickerView() {

        try {
            RxKeyboardTool.hideSoftInput(this);

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(new Date());
            //时间选择器
            TimePickerView pvTime = new TimePickerBuilder(this,
                    new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            long dateTime = date.getTime();
                            String dateStr = DateUtils.convertToString(dateTime, DateUtils.DATE_FORMAT);
                            int birthdayYear = Integer.parseInt(DateUtils.convertToString(dateTime, DateUtils.FORMAT_YYYY));
                            int year = Integer.parseInt(DateUtils.getDateFormat(DateUtils.FORMAT_YYYY));
                            ageEt.setText(year - birthdayYear + getString(R.string.yearold));
                            yearString = year - birthdayYear + "";
                            birthdayEt.setText(dateStr);
                            long time = date.getTime();
                            dataBean.setBirthday(String.valueOf(time));
                        }
                    }).setType(new boolean[]{true, true, true, false, false, false})
                    .setRangDate(null, endCalendar)
                    .build();
            pvTime.show();

            if(null!=dataBean && !TextUtils.isEmpty(dataBean.getBirthday())){
                long timeL = Long.parseLong(dataBean.getBirthday());
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(timeL);
                pvTime.setDate(calendar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter.getUserInfo();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    protected UserInfoContract.Presenter initPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean next() {
        //姓名
        String name = nameEt.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            App.showToast(R.string.name_is_not_empty);
            return false;
        }
        App.getSPUtils().put("authName", name);
        //生日
        String birthday = birthdayEt.getText().toString().trim();

        if (TextUtils.isEmpty(birthday)) {
            App.showToast(R.string.birthdays_not_empty);
            return false;
        }
        App.getSPUtils().put("authBirthday", birthday);
        //年龄
        String age = yearString.trim();
        if (TextUtils.isEmpty(age)) {
            App.showToast(R.string.age_not_empty);
            return false;
        }
        App.getSPUtils().put("authAge", age);
        //籍贯
        String origin = originEt.getText().toString();
        if (TextUtils.isEmpty(origin)) {
            App.showToast(R.string.origin_not_empty);
            return false;
        }
        App.getSPUtils().put("authOrigin", origin);
        //常住地
        String permaResi = permaResiEt.getText().toString();
        if (TextUtils.isEmpty(permaResi)) {
            App.showToast(R.string.residencenot_empty);
            return false;
        }
        App.getSPUtils().put("authPermaResi", permaResi);
        //地址
        String address = addressEt.getText().toString();

        if (TextUtils.isEmpty(address)) {
            App.showToast(R.string.address_not_empty);
            return false;
        }
        App.getSPUtils().put("authAddress", address);
        //医院
        String hospital = hospitalEt.getText().toString();

        if (TextUtils.isEmpty(hospital)) {
            App.showToast(R.string.hospita_not_emptyl);
            return false;
        }
        App.getSPUtils().put("authHospital", hospital);
        //科室
        String departments = departmentsEt.getText().toString();

        if (TextUtils.isEmpty(departments)) {
            App.showToast(R.string.departments_not_mpty);
            return false;
        }
        App.getSPUtils().put("authDepartmentId", departmentId);
        //从业年限
        String years = yearsEt.getText().toString();

        if (TextUtils.isEmpty(years)) {
            App.showToast(R.string.years_is_not_empty);
            return false;
        }
        App.getSPUtils().put("authYears", years);
        //    职称
        if (TextUtils.isEmpty(titles)) {
            App.showToast(R.string.title_not_empty);
            return false;
        }
        App.getSPUtils().put("authTitles", titles);
        //科室电话
        String departmentTelephone = departmentTelephoneEt.getText().toString();

        App.getSPUtils().put("authDepartmentTelephone", departmentTelephone);
        //紧急联系人
        String emergencyContact = emergencyContactEt.getText().toString();

        if (TextUtils.isEmpty(emergencyContact)) {
            App.showToast(R.string.emergencycontact_notempty);
            return false;
        }
        App.getSPUtils().put("authEmergencyContact", emergencyContact);

        String emergencyContactphone = emergencyContactphoneEt.getText().toString().trim();

        if (TextUtils.isEmpty(emergencyContactphone)) {
            App.showToast(R.string.emergencycontactphone_notempty);
            return false;
        }
//        if (!TxtUtils.chickedPhone(emergencyContactphone)) {
//            App.showToast(getString(R.string.phone_geshi_not));
//            return false;
//        }
        App.getSPUtils().put("authEmergencyContactphone", emergencyContactphone);
        if (languages == null || languages.size() <= 0) {
            App.showToast(R.string.choose_language);
            return false;
        }
        StringBuilder languageBuilder = new StringBuilder();
        for (String language : languages.keySet()) {
            languageBuilder.append(language + ",");
        }
        App.getSPUtils().put("authLanguage", languageBuilder.toString());
        if (TextUtils.isEmpty(nature)) {
            App.showToast(R.string.select_attributes);
            return false;
        }
        App.getSPUtils().put("authNature", nature);
        if (TextUtils.isEmpty(sex)) {
            App.showToast(R.string.sex_sel);
            return false;
        }
        App.getSPUtils().put("authSex", sex);
        return true;
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
                String tx = opt1tx + opt2tx;
                if (type == 100) {
                    originEt.setText(opt1tx + opt2tx);
                } else {
                    permaResiEt.setText(tx);
                }
            }
        })
                .setTitleText(getString(R.string.city_sel))
                .setDividerColor(ContextCompat.getColor(AuthenticationActivity.this, R.color.lineColor1))
                .setTextColorCenter(ContextCompat.getColor(AuthenticationActivity.this, R.color.textColor7)) //设置选中项文字颜色
                .setContentTextSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
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
        if (requestCode == 100 && resultCode == 101) {
            departmentsEt.setText(data.getStringExtra("department"));
            departmentId = data.getStringExtra("departmentId");
        }
    }

    @Override
    public void getUserInfoSuess(UseInfoResModel useInfoResModel) {
        showContent();
        if (useInfoResModel != null && useInfoResModel.getData() != null) {
            Gson gson = new Gson();
            App.getSPUtils().put("useInfoResModel", gson.toJson(useInfoResModel));
            dataBean = useInfoResModel.getData();
            nameEt.setText(!TextUtils.isEmpty(dataBean.getName()) ? dataBean.getName() : "");
            if (Constants.MALE.equals(dataBean.getGender())) {
                sexGroup.check(R.id.nanRb);
            } else {
                sexGroup.check(R.id.nvRb);
            }
            if (!TextUtils.isEmpty(dataBean.getBirthday())) {
                birthdayEt.setText(DateUtils.convertToString(Long.parseLong(dataBean.getBirthday()), DateUtils.DATE_FORMAT));
            }
            ageEt.setText(!TextUtils.isEmpty(dataBean.getAge()) ? dataBean.getAge() : "");
            originEt.setText(!TextUtils.isEmpty(dataBean.getBirthplace()) ? dataBean.getBirthplace() : "");
            permaResiEt.setText(!TextUtils.isEmpty(dataBean.getLocation()) ? dataBean.getLocation() : "");
            addressEt.setText(!TextUtils.isEmpty(dataBean.getContactAddr()) ? dataBean.getContactAddr() : "");
            hospitalEt.setText(!TextUtils.isEmpty(dataBean.getHospital()) ? dataBean.getHospital() : "");
            departmentsEt.setText(!TextUtils.isEmpty(dataBean.getSectionOfficeName()) ? dataBean.getSectionOfficeName() : "");
            departmentId = dataBean.getSectionOfficeId();
            yearsEt.setText(!TextUtils.isEmpty(dataBean.getPracticeYear()) ? dataBean.getPracticeYear() : "");
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
            departmentTelephoneEt.setText(!TextUtils.isEmpty(dataBean.getSectionOfficeTel()) ? dataBean.getSectionOfficeTel() : "");
            emergencyContactEt.setText(!TextUtils.isEmpty(dataBean.getEmergencyContact()) ? dataBean.getEmergencyContact() : "");
            emergencyContactphoneEt.setText(!TextUtils.isEmpty(dataBean.getEmergencyContactTel()) ? dataBean.getEmergencyContactTel() : "");
            String lanaguge = dataBean.getLanguage();
            if (lanaguge.contains(",")) {
                String[] lanaguges = lanaguge.split(",");
                for (String lan : lanaguges) {
                    if (lan.equals(Common.CommonStr.LANGUAGE1)) {
                        Log.i("lqi",lan);
                        languages.put("zh_CN", lan);
                        mandarinRb.setChecked(true);
                    } else if (lan.equals(Common.CommonStr.LANGUAGE2)) {
                        languages.put("zh_TW", lan);
                        cantoneseRb.setChecked(true);
                    } else if (lan.equals(Common.CommonStr.LANGUAGE3)) {
                        languages.put("en_US", lan);
                        englishRb.setChecked(true);
                    }
                }
            }
            if (dataBean.getNature().equals("PartTime")) {
                jobGroup.check(R.id.partJob_rb);
            } else {
                jobGroup.check(R.id.fullJob_rb);
            }
        }

    }

    @Override
    public void checkIsEdit(BaseResModel baseResModel) {

    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

        showContent();
        App.showToast(errorMsg);


    }
}
