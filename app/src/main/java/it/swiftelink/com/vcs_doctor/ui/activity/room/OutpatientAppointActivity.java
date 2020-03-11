package it.swiftelink.com.vcs_doctor.ui.activity.room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.vcs_doctor.R;

/**
 * @作者 Arvin
 * @时间 2019/7/24 9:39
 * @一句话描述 门诊预约
 */

@BindEventBus
public class OutpatientAppointActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_outpatient_appoint;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(R.string.outpatient_appointment);
    }

    @OnClick({R.id.btn_back, R.id.callPhoneBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.callPhoneBtn:
                callPhone("4006808069");
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
