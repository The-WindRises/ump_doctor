package it.swiftelink.com.vcs_doctor.ui.activity.income;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.DecimalFormat;
import java.util.Date;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.income.MyIncomeRecordResModel;
import it.swiftelink.com.factory.model.income.MyIncomeResModel;
import it.swiftelink.com.factory.presenter.income.MyIncomeContract;
import it.swiftelink.com.factory.presenter.income.MyIncomePresenter;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.income.adapter.MyIncomeAdapter;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

/**
 * @作者 Arvin
 * @时间 2019/7/29 11:38
 * @一句话描述 我的收入
 */
@BindEventBus

public class MyIncomeActivity extends
        BasePresenterActivity<MyIncomeContract.Presenter>
        implements MyIncomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.right_btn)
    TextView right_btn;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.amount_income)
    TextView amountIncome;
    @BindView(R.id.amount_withdrawal)
    TextView amountWithdrawal;
    @BindView(R.id.dateTv)
    TextView dateTv;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    MyIncomeAdapter myIncomeAdapter;

    private int totalPages = 0;
    private int currPage = 1;
    private int pageSize = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_income;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText(getString(R.string.myIncome));
        right_btn.setVisibility(View.VISIBLE);
        right_btn.setText(getString(R.string.discount_record));
        right_btn.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setOnRefreshListener(this);
        String date = DateUtils.getDateYM();
        dateTv.setText(date);
        myIncomeAdapter = new MyIncomeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(myIncomeAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMyIncome();
        mPresenter.getMyIncomeRecord(DateUtils.getDate());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected MyIncomeContract.Presenter initPresenter() {
        return new MyIncomePresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick({R.id.btn_back, R.id.right_btn, R.id.dateTv})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.right_btn:
                Intent intent = new Intent(this, DiscountRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.dateTv:
                showTimePickerView();
                break;
        }
    }

    /**
     * 显示时间选择器
     */
    private void showTimePickerView() {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this,
                new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        long dateTime = date.getTime();
                        String dateStr = DateUtils.convertToStringYM(dateTime);
                        dateTv.setText(dateStr);
                        mPresenter.getMyIncomeRecord(DateUtils.convertToYMString(dateTime));
                    }
                }).setType(new boolean[]{true, true, false, false, false, false})
                .build();
        pvTime.show();
    }

    @Override
    public void getMyIncome(MyIncomeResModel myIncomeResModel) {
        showContent();
        swipeRefreshLayout.setRefreshing(false);
        if (myIncomeResModel.getData() != null) {
            DecimalFormat df = new DecimalFormat("#0.00");
            String accumulatedIncome = myIncomeResModel.getData().getAccumulatedIncome();
            String cashIncome = myIncomeResModel.getData().getCashIncome();
            String amount = myIncomeResModel.getData().getAmount();
            if (!"".equals(accumulatedIncome) && accumulatedIncome != null) {
                amountIncome.setText("¥ " + df.format(Double.valueOf(accumulatedIncome)));
            }
            if (!"".equals(cashIncome) && cashIncome != null) {
                amountWithdrawal.setText("¥ " + df.format(Double.valueOf(cashIncome)));
            }
            if (!"".equals(amount) && amount != null) {
                balance.setText("¥ " + df.format(Double.valueOf(amount)));
            }
        }
    }

    /**
     * 按照日期获取收入记录
     *
     * @param myIncomeRecordResModel
     */
    @Override
    public void getMyIncomeRecord(MyIncomeRecordResModel myIncomeRecordResModel) {
        showContent();
        swipeRefreshLayout.setRefreshing(false);
        myIncomeAdapter.setNewData(myIncomeRecordResModel.getData());

    }


    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

        showContent();
    }

    /**
     * 刷新数据
     */
    @Override
    public void onRefresh() {
        mPresenter.getMyIncomeRecord(DateUtils.getDate());
        mPresenter.getMyIncome();
    }
}
