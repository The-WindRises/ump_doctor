package it.swiftelink.com.vcs_doctor.ui.activity.income;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.vcs_doctor.R;

/**
 * 提现操作
 */
@BindEventBus
public class CashChangeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rb_wexi)
    RadioButton rb_wexi;
    @BindView(R.id.rb_alipay)
    RadioButton rb_alipay;
    @BindView(R.id.rb_unionpay)
    RadioButton rb_unionpay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash_change;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText(getString(R.string.cashwithdrawal));
    }

    @OnClick({R.id.btn_back, R.id.cash_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.cash_out:
                break;
        }
    }

    @OnCheckedChanged({R.id.rb_wexi, R.id.rb_alipay, R.id.rb_unionpay})
    public void onCheckedChanged(CompoundButton view, boolean ischanged) {

        switch (view.getId()) {
            case R.id.rb_wexi:
                if (ischanged) {
                    rb_alipay.setChecked(false);
                    rb_unionpay.setChecked(false);
                }
                break;
            case R.id.rb_alipay:
                if (ischanged) {
                    rb_wexi.setChecked(false);
                    rb_unionpay.setChecked(false);
                }
                break;
            case R.id.rb_unionpay:
                if (ischanged) {
                    rb_alipay.setChecked(false);
                    rb_wexi.setChecked(false);
                }
                break;


        }

    }

}

