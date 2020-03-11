package it.swiftelink.com.vcs_doctor.ui.activity.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.TxtUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.presenter.login.ModifyLoginPwdContract;
import it.swiftelink.com.factory.presenter.login.ModifyLoginPwdPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;

/**
 * @作者 Arvin
 * @时间 2019/7/23 12:05
 * @一句话描述 修改登录密码
 */

@BindEventBus

public class ModifyLoginPwdActivity
        extends BasePresenterActivity<ModifyLoginPwdContract.Presenter>
        implements ModifyLoginPwdContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.oldPwd_Et)
    EditText oldPwdEt;
    @BindView(R.id.newPwdPwd_Et)
    EditText newPwdPwdEt;
    @BindView(R.id.confirmPwd_Et)
    EditText confirmPwdEt;
    @BindView(R.id.stateView)
    StateView stateView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_login_pwd;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.modifyLogin_pwd));
    }

    @Override
    protected ModifyLoginPwdContract.Presenter initPresenter() {
        return new ModifyLoginPwdPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick({R.id.btn_back, R.id.confirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.confirm_btn:
                updPwd();
                break;
        }
    }

    /**
     * 修改密码
     */
    private void updPwd() {

        String oldPwd = oldPwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            App.showToast(R.string.oldpwd_not_empty);
            return;
        }
        if (!TxtUtils.chickedPassword(oldPwd)) {
            App.showToast(R.string.pwd_is_not_format);
            return;
        }
        String newPwd = newPwdPwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(newPwd)) {
            App.showToast(R.string.new_pwd_not_empty);
            return;
        }
        if (!TxtUtils.chickedPassword(oldPwd)) {
            App.showToast(R.string.pwd_is_not_format);
            return;
        }
        String confirmPwd = confirmPwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPwd)) {
            App.showToast(R.string.confirmation_pwd_not_empty);
            return;
        }

        if (!newPwd.equals(confirmPwd)) {
            App.showToast(R.string.twopassword_inconsistencies);
            return;
        }


        mPresenter.modifyLoginPwd(oldPwd, newPwd, "Internal");
    }

    @Override
    public void modifyLoginPwdSuccess() {
        showContent();
        App.showToast(R.string.successful_revision);
        toActivity(LoginActivity.class);
        out();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
