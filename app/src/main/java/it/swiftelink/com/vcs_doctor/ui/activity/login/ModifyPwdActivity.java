package it.swiftelink.com.vcs_doctor.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.vcs_doctor.R;

/**
 * @作者 Arvin
 * @时间 2019/7/23 11:46
 * @一句话描述 修改密码
 */
@BindEventBus

public class ModifyPwdActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.modify_pwd));
    }

    @OnClick({R.id.btn_back, R.id.modifylogin_wap, R.id.modifypay_wap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.modifylogin_wap:
                toActivity(ModifyLoginPwdActivity.class);
                break;
            case R.id.modifypay_wap:
                toActivity(ModifyPayPwdActivity.class);
                break;
        }
    }
}
