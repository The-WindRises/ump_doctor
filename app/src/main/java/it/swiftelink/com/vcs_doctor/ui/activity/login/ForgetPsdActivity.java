package it.swiftelink.com.vcs_doctor.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.TxtUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.login.ForgetPsdParaModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.presenter.login.ForgetPsdContract;
import it.swiftelink.com.factory.presenter.login.ForgetPsdPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * @作者 Arvin
 * @时间 2019/7/26 9:56
 * @一句话描述 忘记密码
 */

@BindEventBus
public class ForgetPsdActivity extends
        BasePresenterActivity<ForgetPsdContract.Presenter>
        implements ForgetPsdContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_phone_type)
    TextView tvPhoneType;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_get_verification_code)
    TextView tvGetVerificationCode;
    @BindView(R.id.tv_phone_send)
    TextView tvPhoneSend;
    @BindView(R.id.et_input_psd)
    EditText et_input_psd;
    @BindView(R.id.stateView)
    StateView stateView;

    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, ForgetPsdActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_psd;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.forget_password));
    }

    @Override
    protected ForgetPsdContract.Presenter initPresenter() {
        return new ForgetPsdPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }


    @OnClick({R.id.btn_back, R.id.ll_phone_type, R.id.tv_phone_type, R.id.btn_next, R.id.tv_get_verification_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_phone_type:
                break;
            case R.id.btn_next:
                forgetPawssword();
                break;
            case R.id.tv_get_verification_code:
                getPhoneCode();

                break;
            case R.id.tv_phone_type:
                toStartActivityForResult(CountryCodeActivity.class, 100);
                break;
        }
    }

    /**
     * 忘记密码操作
     */
    private void forgetPawssword() {

        String phoneNumberPre = "";
        String phoneNumber = etPhone.getText().toString();
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
        String pwd = et_input_psd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            App.showToast(getString(R.string.pwd_not_empty));
            return;
        }

        if (!TxtUtils.chickedPassword(pwd)) {
            App.showToast(R.string.pwd_is_not_format);
            return;
        }
        String smsCode = etVerificationCode.getText().toString();
        if (TextUtils.isEmpty(smsCode)) {
            App.showToast(getString(R.string.verification_code_not_empty));
            return;
        }

        ForgetPsdParaModel forgetPsdParaModel = new ForgetPsdParaModel();
        forgetPsdParaModel.setMobile(phoneNumberPre);
        forgetPsdParaModel.setSmsCode(smsCode);
        forgetPsdParaModel.setPassword(pwd);
        forgetPsdParaModel.setType("Internal");
        mPresenter.forgetPsd(forgetPsdParaModel);
    }

    /**
     * 获取短信验证码
     */
    public void getPhoneCode() {
        String phoneNumberPre = "";
        String phoneNumber = etPhone.getText().toString();
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
        SmsCodeParamModel smsCodeParamModel = new SmsCodeParamModel(phoneNumberPre, "3");
        mPresenter.getPhoneCode(smsCodeParamModel);
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
    public void forgetPsdSuccess() {
        showContent();
        App.showToast(getString(R.string.successful_revision));
        out();
    }

    @Override
    public void getPhoneCodeResModelSuccess() {
        showContent();
        startTimer();
        App.showToast(R.string.cerification_codesuccess);
        String phoneNumber = etPhone.getText().toString();
        tvPhoneSend.setText(phoneNumber);
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
}
