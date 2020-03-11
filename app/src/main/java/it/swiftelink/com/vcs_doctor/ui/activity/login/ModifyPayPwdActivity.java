package it.swiftelink.com.vcs_doctor.ui.activity.login;

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
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.presenter.login.ModifyPayPwdContract;
import it.swiftelink.com.factory.presenter.login.ModifyPayPwdPresenter;
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
 * @时间 2019/7/23 12:21
 * @一句话描述 修改支付密码
 */


@BindEventBus
public class ModifyPayPwdActivity extends
        BasePresenterActivity<ModifyPayPwdContract.Presenter>
        implements ModifyPayPwdContract.View {

    @BindView(R.id.tv_get_verification_code)
    TextView tvGetVerificationCode;
    @BindView(R.id.tv_phone_type)
    TextView tvPhoneType;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_verification_code)
    EditText smsEt;
    @BindView(R.id.et_input_psd)
    EditText payPwdEt;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone_send)
    TextView tv_phone_send;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_pay_pwd;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.modify_payment_password));
    }

    @Override
    protected ModifyPayPwdContract.Presenter initPresenter() {
        return new ModifyPayPwdPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick({R.id.btn_back, R.id.confirm_btn, R.id.tv_get_verification_code, R.id.ll_phone_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.confirm_btn:
                updPayPwd();
                break;
            case R.id.tv_get_verification_code:
                getSmsCode();
                break;
            case R.id.ll_phone_type:
                toStartActivityForResult(CountryCodeActivity.class, 100);
                break;
        }
    }


    private void updPayPwd() {
        String phoneNum = etPhone.getText().toString();
        String phoneTypeCode = tvPhoneType.getText().toString().trim().replace("+", "");
        String mobile = "";
        if (!phoneTypeCode.equals("86")) {
            mobile = phoneTypeCode + " " + phoneNum;
        } else {
            mobile = phoneNum;
        }
        String smsCode = smsEt.getText().toString().trim();
        String payPwd = payPwdEt.getText().toString();
        if (TextUtils.isEmpty(smsCode)) {
            App.showToast(R.string.verification_code_not_empty);
            return;
        }
        if (TextUtils.isEmpty(payPwd)) {
            App.showToast(R.string.payment_pwd_empty);
            return;
        }
        if (payPwd.length() > 6 || payPwd.length() < 6) {
            App.showToast(R.string.input_6paypwd);
            return;
        }

        mPresenter.modifyPayPwd(mobile, smsCode, payPwd);
    }

    /**
     * 获取验证码
     */
    private void getSmsCode() {
        String mobile = "";
        String phoneNum = etPhone.getText().toString();
        String phoneTypeCode = tvPhoneType.getText().toString().replace("+", "");
        if (TextUtils.isEmpty(phoneNum)) {
            App.showToast(getString(R.string.phone_not_empty));
            return;
        }

        if (TextUtils.isEmpty(phoneTypeCode)) {
            App.showToast(getString(R.string.select_country_code));
            return;
        }

        if (!TxtUtils.chickedPhone(phoneNum)) {
            App.showToast(getString(R.string.phone_format_Incorrect));
            return;
        }

        if (!phoneTypeCode.equals("86")) {
            mobile = phoneTypeCode + " " + phoneNum;

        } else {
            mobile = phoneNum;
        }
        SmsCodeParamModel smsCodeParamModel = new SmsCodeParamModel(mobile, "4");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            tvPhoneType.setText(data.getStringExtra("countryCode"));
        }
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {
        stateView.showContent();
        App.showToast(errorMsg);
    }

    @Override
    public void modifyPayPwdSuccess() {
        stateView.showContent();
        App.showToast(R.string.successful_setup);
        out();
    }

    @Override
    public void getPhoneCodeResModelSuccess() {
        stateView.showContent();
        tv_phone_send.setText(etPhone.getText().toString());
        startTimer();
        App.showToast(R.string.cerification_codesuccess);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
