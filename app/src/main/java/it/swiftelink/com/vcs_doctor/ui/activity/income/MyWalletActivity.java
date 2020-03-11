
package it.swiftelink.com.vcs_doctor.ui.activity.income;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.vcs_doctor.R;

/**
 * 我的钱包
 */
@BindEventBus
public class MyWalletActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView title;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        title.setText(getString(R.string.wallet));
    }

    @OnClick({R.id.balance_lay, R.id.bankcard_lay, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.balance_lay:
                toActivity(SmallChangeActivity.class);
                break;
            case R.id.bankcard_lay:
                toActivity(MyBankCardActivity.class);
                break;
        }
    }
}

