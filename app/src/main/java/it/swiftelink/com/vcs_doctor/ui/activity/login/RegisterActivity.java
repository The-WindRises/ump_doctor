package it.swiftelink.com.vcs_doctor.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.LanguageType;
import it.swiftelink.com.common.utils.MultiLanguageUtil;
import it.swiftelink.com.common.utils.TxtUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.login.RegisterParamModel;
import it.swiftelink.com.factory.model.login.RegisterResModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.model.user.AgrreementResModel;
import it.swiftelink.com.factory.presenter.login.RegisterConract;
import it.swiftelink.com.factory.presenter.login.RegisterPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.UpdateEvent;
import it.swiftelink.com.vcs_doctor.ui.activity.MainActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.user.AgreementActivity;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;


/**
 * @作者 Arvin
 * @时间 2019/8/3 16:56
 * @一句话描述 注册
 */


@BindEventBus
public class RegisterActivity extends
        BasePresenterActivity<RegisterConract.Presenter>
        implements RegisterConract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_phone_type)
    TextView tvPhoneType;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_phone_send)
    TextView tvPhoneSend;
    @BindView(R.id.tv_get_verification_code)
    TextView tvGetVerificationCode;
    @BindView(R.id.et_input_psd)
    EditText etInputPsd;
    @BindView(R.id.et_confirm_psd)
    EditText etConfirmPsd;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.xieyiCheckBox)
    CheckBox xieyiCheckBox;
    @BindView(R.id.agreementLayou)
    LinearLayout agreementLayou;
    private boolean isCheck = false;

    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, RegisterActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.register_title));
        int languageType = MultiLanguageUtil.getInstance().getLanguageType();
        if (languageType == LanguageType.LANGUAGE_EN) {
            agreementLayou.setOrientation(LinearLayout.VERTICAL);
        } else {
            agreementLayou.setOrientation(LinearLayout.HORIZONTAL);
        }
        xieyiCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
            }
        });
    }

    @Override
    protected RegisterConract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick({R.id.btn_back, R.id.ll_phone_type, R.id.view_xieyi, R.id.tv_get_verification_code,
            R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_phone_type:
                toStartActivityForResult(CountryCodeActivity.class, 100);
                break;
            case R.id.tv_get_verification_code:
                getSmsCode();
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.tv_phone_type:
                toStartActivityForResult(CountryCodeActivity.class, 100);
                break;
            case R.id.view_xieyi:
                toActivity(AgreementActivity.class);
                break;
        }
    }

    /**
     * 注册
     */
    public void register() {
        String mobile = "";
        String phoneNum = etPhone.getText().toString();

        if (TextUtils.isEmpty(phoneNum)) {
            App.showToast(getString(R.string.phone_not_empty));
            return;
        }
        if (!TxtUtils.chickedPhone(phoneNum)) {
            App.showToast(getString(R.string.phone_format_Incorrect));
            return;
        }
        String phoneType = tvPhoneType.getText().toString().replace("+", "");
        if (TextUtils.isEmpty(phoneType)) {
            App.showToast(R.string.select_country);
            return;
        }
        String smsCode = etVerificationCode.getText().toString();
        if (TextUtils.isEmpty(smsCode)) {
            App.showToast(getString(R.string.verification_code_not_empty));
            return;
        }
        String pwd = etInputPsd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            App.showToast(R.string.pwd_not_empty);
            return;
        }
        if (!TxtUtils.chickedPassword(pwd)) {
            App.showToast(R.string.pwd_is_not_format);
            return;
        }
        String confirmPwd = etInputPsd.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPwd)) {
            App.showToast(getString(R.string.confirmation_pwd_not_empty));
            return;
        }
        if (!pwd.equals(confirmPwd)) {
            App.showToast(getString(R.string.twopassword_inconsistencies));
            return;
        }
        if (!isCheck) {
            App.showToast(R.string.select_xieyi);
            return;
        }
        String agrreementId = App.getSPUtils().getString("agrreementId", "");
        //处理国家编码
        if (!phoneType.equals("86")) {
            mobile = phoneType + " " + phoneNum;
        } else {
            mobile = phoneNum;
        }
        RegisterParamModel registerParamModel = new RegisterParamModel();
        registerParamModel.setMobile(mobile);
        registerParamModel.setPassword(confirmPwd);
        registerParamModel.setSmsCode(smsCode);
        registerParamModel.setAgrreementId(agrreementId);
        mPresenter.register(registerParamModel);
    }

    /**
     * 获取短信验证码
     */
    private void getSmsCode() {
        String mobile = "";
        String phoneNum = etPhone.getText().toString();

        if (TextUtils.isEmpty(phoneNum)) {
            App.showToast(getString(R.string.phone_not_empty));
            return;
        }

        if (!TxtUtils.chickedPhone(phoneNum)) {

            App.showToast(R.string.phone_geshi_not);
            return;
        }
        String phoneType = tvPhoneType.getText().toString().replace("+", "");
        if (TextUtils.isEmpty(phoneType)) {
            App.showToast(R.string.select_country);
            return;
        }
        //处理国家编码
        if (!phoneType.equals("86")) {
            mobile = phoneType + " " + phoneNum;
        } else {
            mobile = phoneNum;
        }
        SmsCodeParamModel paramModel = new SmsCodeParamModel(mobile, Constants.REGISTER_SMS_TYPE);
        mPresenter.getPhoneCode(paramModel);
    }

    private void startTimer() {
        final int count = 60;//倒计时10秒
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
                tvGetVerificationCode.setText(getString(R.string.surplus) + aLong + getString(R.string.second));
            }
        });
    }

    @Override
    public void registerSuccess(RegisterResModel registerResModel) {
        showContent();
        if (registerResModel != null) {
            String token = registerResModel.getData().getAccessToken();
            String doctorId = registerResModel.getData().getLoginId();
            String doctorStatus = registerResModel.getData().getDoctorStatus();
            App.getSPUtils().put("accessToken", token);
            App.getSPUtils().put("LoginDoctorId", doctorId);
            App.getSPUtils().put("doctorStatus", doctorStatus);
            App.getSPUtils().put("refreshUI", false);
            JPushInterface.setAlias(this,0,registerResModel.getData().getLoginId());
            EventBus.getDefault().postSticky(new UpdateEvent(Common.EventbusType.ISLOGIN));
            toActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    public void getPhoneCodeResModelSuccess() {
        showContent();
        App.showToast(R.string.getsms_suess);
        tvPhoneSend.setText(etPhone.getText().toString());
        startTimer();
    }

    @Override
    public void getAgrreementSuess(AgrreementResModel agrreementResModel) {
        showContent();
        if (agrreementResModel != null) {
            App.getSPUtils().put("agrreementId", agrreementResModel.getData().getId());
        }
    }

    @Override
    public void retry(int type) {
    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
        if (type == Common.UrlApi.Get_SMSCODE) {
            App.showToast(errorMsg);
            App.showToast(errorMsg);
        } else if (type == Common.UrlApi.POST_REGISTER) {
            App.showToast(errorMsg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            tvPhoneType.setText(data.getStringExtra("countryCode"));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
