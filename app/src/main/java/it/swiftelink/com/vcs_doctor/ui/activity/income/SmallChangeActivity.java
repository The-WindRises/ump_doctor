package it.swiftelink.com.vcs_doctor.ui.activity.income;


import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.presenter.income.SmallChangeContract;
import it.swiftelink.com.factory.presenter.income.SmallChangePresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;

/**
 * 我的零钱
 */
@BindEventBus
public class SmallChangeActivity extends
        BasePresenterActivity<SmallChangeContract.Presenter>
        implements SmallChangeContract.View {

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.balanceTv)
    TextView balanceTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_small_change;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        title.setText(getString(R.string.smallchange));
    }

    @Override
    protected SmallChangeContract.Presenter initPresenter() {
        return new SmallChangePresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.getBalance();
    }

    @OnClick({R.id.btn_back, R.id.btn_withdrawal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.btn_withdrawal:
                toActivity(CashChangeActivity.class);
                break;
        }
    }

    @Override
    public void getBalance(String balance) {
        stateView.showContent();
        DecimalFormat df = new DecimalFormat("#0.00");
        if (!"".equals(balance) && null != balance) {
            balanceTv.setText("¥\t" + df.format(Double.valueOf(balance)));
        }
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

        if (type == Common.UrlApi.GET_BALANCE_CODE) {
            App.showToast(R.string.queryfailure);
        }

    }
}
