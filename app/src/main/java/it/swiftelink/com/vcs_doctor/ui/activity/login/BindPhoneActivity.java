package it.swiftelink.com.vcs_doctor.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.vcs_doctor.R;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

@BindEventBus
public class BindPhoneActivity extends BaseActivity {

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

    public static void show(Activity activity){
        activity.startActivity(new Intent(activity,BindPhoneActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        tvTitle.setText(getString(R.string.binding_phone));
    }

    @OnClick({R.id.btn_back, R.id.ll_phone_type, R.id.btn_bind,R.id.tv_get_verification_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_phone_type:
                break;
            case R.id.btn_bind:
                break;
            case R.id.tv_get_verification_code:
                startTimer();
                break;
        }
    }

    private void startTimer() {

        final int count = 60;//倒计时10秒


        Observable.interval(0,1,TimeUnit.SECONDS)
                .take(count+1)
                .map(new Func1<Long, Long>() {

                    @Override
                    public Long call(Long aLong) {
                        return count-aLong;
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
                tvGetVerificationCode.setText(getString(R.string.surplus)+aLong+getString(R.string.second));
            }
        });
    }
}
