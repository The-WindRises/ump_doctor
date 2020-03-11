package it.swiftelink.com.vcs_doctor.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.LanguageType;
import it.swiftelink.com.common.utils.LogcatHelper;
import it.swiftelink.com.common.utils.MultiLanguageUtil;
import it.swiftelink.com.common.utils.TxtUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.login.LoginResModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.presenter.login.LoginContract;
import it.swiftelink.com.factory.presenter.login.LoginPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.UpdateEvent;
import it.swiftelink.com.vcs_doctor.service.MqttService;
import it.swiftelink.com.vcs_doctor.ui.activity.MainActivity;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

@BindEventBus
public class LoginActivity extends BasePresenterActivity<LoginContract.Presenter> implements LoginContract.View {


    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.ll_phone_type)
    LinearLayout ll_phone_type;
    @BindView(R.id.tv_get_verification_code)
    TextView tvGetVerificationCode;
    @BindView(R.id.tv_phone_type)
    TextView tvPhoneType;
    @BindView(R.id.tipsLayout)
    LinearLayout tipsLayout;
    @BindView(R.id.tv_language1)
    TextView tvLanguage1;
    @BindView(R.id.tv_language2)
    TextView tvLanguage2;
    @BindView(R.id.tv_language3)
    TextView tvLanguage3;
    @BindView(R.id.tipsPhoneTv)
    TextView tipsPhoneTv;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.smsLoginCodeLay)
    LinearLayout smsLoginCodeLay;
    @BindView(R.id.accountLoginPwdLay)
    LinearLayout accountLoginPwdLay;
    @BindView(R.id.tv_sms_login)
    TextView tvSmsLoginTv;

    private boolean isSmsCodeLogin = true;
    private String language;
    private final int LanguageType1 = 0;
    private final int LanguageType2 = 1;
    private final int LanguageType3 = 2;

    private int languageType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        languageType = MultiLanguageUtil.getInstance().getLanguageType();
        if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            setLanguage(LanguageType1);
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
            setLanguage(LanguageType2);
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            setLanguage(LanguageType3);
        }
        if (isSmsCodeLogin) {

        }
    }
    // region foo
    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick({R.id.ll_phone_type, R.id.tv_language1, R.id.tv_language2, R.id.tv_language3, R.id.tv_sms_login,
            R.id.btn_login, R.id.registerTv, R.id.tv_get_verification_code, R.id.tv_phone_type, R.id.tv_forget_psd, R.id.ll_login_weixin,R.id.tv_to_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_phone_type:
                break;
            case R.id.tv_language1:
                MultiLanguageUtil.getInstance().updateLanguage(LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
                setLanguage(LanguageType1);
                recreate();
                break;
            case R.id.tv_language2:
                MultiLanguageUtil.getInstance().updateLanguage(LanguageType.LANGUAGE_CHINESE_TRADITIONAL);
                setLanguage(LanguageType2);
                recreate();
                break;
            case R.id.tv_language3:
                MultiLanguageUtil.getInstance().updateLanguage(LanguageType.LANGUAGE_EN);
                setLanguage(LanguageType3);
                recreate();
                break;
            case R.id.btn_login:
                if (isSmsCodeLogin) {
                    smsLogin();
                } else {
                    login();
                }
                break;
            case R.id.registerTv:
                RegisterActivity.show(this);
                break;
            case R.id.tv_forget_psd:
                ForgetPsdActivity.show(this);
                break;
            case R.id.ll_login_weixin:
                break;
            case R.id.tv_phone_type:
                toStartActivityForResult(CountryCodeActivity.class, 100);
                break;
            case R.id.tv_sms_login:
                switchVieww();
                break;
            case R.id.tv_get_verification_code:
                getPhoneCode();
                break;
            case R.id.tv_to_main:
                MainActivity.show(this);
                finish();
            default:
                break;
        }
    }


    /**
     * 获取短信验证码
     */
    public void getPhoneCode() {
        String phoneNumberPre = "";
        String phoneNumber = etUsername.getText().toString();
        //        String smsCode = etVerificationCode.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            App.showToast(getString(R.string.phone_not_empty));
            return;
        }
        if (!TxtUtils.chickedPhone(phoneNumber)) {
            App.showToast(getString(R.string.phone_format_Incorrect));
            return;
        }
        String countryCode = tvPhoneType.getText().toString().trim().replace("+", "");
        if (TextUtils.isEmpty(countryCode)) {
            App.showToast(R.string.select_country_code);
            return;
        }
        if (!countryCode.equals("86")) {
            phoneNumberPre = countryCode + " " + phoneNumber;
        } else {
            phoneNumberPre = phoneNumber;
        }
        SmsCodeParamModel smsCodeParamModel = new SmsCodeParamModel(phoneNumberPre, "1");
        mPresenter.getPhoneCode(smsCodeParamModel);
    }


    void switchVieww() {
        if (isSmsCodeLogin) {
            isSmsCodeLogin = false;
            etUsername.setHint(getResources().getString(R.string.input_phone_or_account));
            accountLoginPwdLay.setVisibility(View.VISIBLE);
            smsLoginCodeLay.setVisibility(View.GONE);
            tvSmsLoginTv.setText(R.string.login_bysms);
        } else {
            isSmsCodeLogin = true;
            etUsername.setHint(getResources().getString(R.string.input_phone_number));
            smsLoginCodeLay.setVisibility(View.VISIBLE);
            accountLoginPwdLay.setVisibility(View.GONE);
            tipsLayout.setVisibility(View.GONE);
            tvSmsLoginTv.setText(R.string.account_by_login);
        }

    }


    /**
     * 短信验证码登录
     */
    private void smsLogin() {
        String phoneNumberPre = "";
        String phoneNum = etUsername.getText().toString().trim();
        String smsCode = etVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            App.showToast(getString(R.string.phone_not_empty));
            return;
        }
        if (!TxtUtils.chickedPhone(phoneNum)) {
            App.showToast(getString(R.string.phone_format_Incorrect));
            return;
        }
        String countryCode = tvPhoneType.getText().toString().trim().replace("+", "");
        if (TextUtils.isEmpty(countryCode)) {
            App.showToast(R.string.select_country_code);
            return;
        }
        if (!countryCode.equals("86")) {
            phoneNumberPre = countryCode + " " + phoneNum;
        } else {
            phoneNumberPre = phoneNum;
        }
        if (TextUtils.isEmpty(smsCode)) {
            App.showToast(getString(R.string.verification_code_not_empty));
            return;
        }
        mPresenter.smsLogin(phoneNumberPre, smsCode);
    }
    // endregion foo

    /**
     * 账号登录操作
     */
    public void login() {
        String mobile = "";
        String userName = etUsername.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            App.showToast(R.string.phone_not_empty);
            return;
        }
        String countryCode = tvPhoneType.getText().toString().trim().replace("+", "");

        if (TextUtils.isEmpty(countryCode)) {
            App.showToast(R.string.statecode_not_empty);
            return;
        }


        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            App.showToast(R.string.pwd_not_empty);
            return;
        }
        if (!TxtUtils.chickedPassword(password)) {
            App.showToast(R.string.pwd_is_not_format);
            return;
        }
        //处理国家编码
        if (!countryCode.equals("86")) {
            mobile = countryCode + " " + userName;
        } else {
            mobile = userName;
        }
        mPresenter.login(mobile, password);
    }

    @Override
    public void loginSuccess(LoginResModel loginResModel) {
        showContent();
        if (loginResModel != null) {
            String token = loginResModel.getData().getAccessToken();
            String doctorId = loginResModel.getData().getLoginId();
            String doctorStatus = loginResModel.getData().getDoctorStatus();

            App.getSPUtils().put("accessToken", token);
            App.getSPUtils().put("LoginDoctorId", doctorId);
            App.getSPUtils().put("doctorStatus", doctorStatus);
            App.getSPUtils().put("refreshUI", false);
            EventBus.getDefault().post(new UpdateEvent(Common.EventbusType.ISLOGIN));
            JPushInterface.setAlias(this,0,loginResModel.getData().getLoginId());
            startService();
            toActivity(MainActivity.class);
            LogcatHelper logcatHelper = LogcatHelper.getInstance(this,doctorId);
            logcatHelper.stop();
            logcatHelper.start();
            finish();
        }
    }

    private void startService() {
        Intent intent = new Intent(this, MqttService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    private void startTimer() {
        final int count = 60;
        //倒计时60秒
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Func1<Long, Long>() {

                    @Override
                    public Long call(Long aLong) {
                        return count - aLong;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvGetVerificationCode.setEnabled(false);
                        tvGetVerificationCode.setTextColor(Color.GRAY);
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                tvGetVerificationCode.setEnabled(true);
                tvGetVerificationCode.setText(getString(R.string.getauthentication_code));
                tvGetVerificationCode.setTextColor(getResources().getColor(R.color.textColor2));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                tvGetVerificationCode.setText(getString(R.string.surplus) + " " +aLong + " " + getString(R.string.second));
            }
        });
    }


    @Override
    public void getPhoneCodeResModelSuccess() {
        showContent();
        startTimer();
        tipsLayout.setVisibility(View.VISIBLE);
        App.showToast(R.string.cerification_codesuccess);
        tipsPhoneTv.setText(etUsername.getText().toString().trim());
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
        App.showToast(errorMsg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            tvPhoneType.setText(data.getStringExtra("countryCode"));
        }
    }

    private void setLanguage(int type) {
        tvLanguage1.setSelected(false);
        tvLanguage2.setSelected(false);
        tvLanguage3.setSelected(false);
        switch (type) {
            case LanguageType1:
                tvLanguage1.setSelected(true);
                break;
            case LanguageType2:
                tvLanguage2.setSelected(true);
                break;
            case LanguageType3:
                tvLanguage3.setSelected(true);
                break;
        }
    }
}
