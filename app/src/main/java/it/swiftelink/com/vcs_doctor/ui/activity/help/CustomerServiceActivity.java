package it.swiftelink.com.vcs_doctor.ui.activity.help;

import android.content.Intent;
import android.net.Uri;
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
 * @时间 2019/7/23 14:32
 * @一句话描述 联系客服
 */
@BindEventBus
public class CustomerServiceActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.phone)
    TextView phone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.contact_customer_service));
    }

    @OnClick({R.id.btn_back, R.id.customerServiceLay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.customerServiceLay:
                callPhone(phone.getText().toString());
                break;
        }
    }

    private void callPhone(String phoneNumber) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }


}
