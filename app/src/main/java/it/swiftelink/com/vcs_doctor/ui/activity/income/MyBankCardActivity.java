package it.swiftelink.com.vcs_doctor.ui.activity.income;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.income.BackResModel;
import it.swiftelink.com.factory.presenter.income.MyBankCardContract;
import it.swiftelink.com.factory.presenter.income.MyBankCardPresenter;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.income.adapter.BackAdapter;

/**
 * 银行卡列表
 */
public class MyBankCardActivity extends
        BasePresenterActivity<MyBankCardContract.Presenter>
        implements MyBankCardContract.View,
        BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.bankRecyclerView)
    RecyclerView bankRecyclerView;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    BackAdapter backAdapter;
    private int totalPages = 0;
    private int currPage = 1;
    private int pageSize = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_bank_card;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText("银行卡");
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.mipmap.img_add);
        swipeRefreshLayout.setOnRefreshListener(this);
        backAdapter = new BackAdapter();
        backAdapter.setOnLoadMoreListener(this, bankRecyclerView);
        bankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bankRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        bankRecyclerView.setAdapter(backAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getBackList(currPage, pageSize);
    }

    @Override
    protected MyBankCardContract.Presenter initPresenter() {
        return new MyBankCardPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }


    @OnClick({R.id.btn_back, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.iv_right:
                toActivity(AddBankActivity.class);
                break;
        }
    }

    @Override
    public void getBackList(BackResModel backResModel) {
        swipeRefreshLayout.setRefreshing(false);
        backAdapter.loadMoreComplete();
        if (backResModel != null) {
            totalPages = backResModel.getData().getTotalPages();
            if (backResModel.getData().getDataCount() <= 0) {
                showEmpty();
            } else {
                showContent();
                if (currPage <= 1) {
                    backAdapter.setNewData(backResModel.getData().getDataList());
                } else {
                    backAdapter.addData(backResModel.getData().getDataList());
                }
            }

        }
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
        if (currPage >= 1) {
            backAdapter.loadMoreFail();
        }
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        currPage++;
        if (currPage > totalPages) {
            backAdapter.loadMoreEnd();
        } else {
            mPresenter.getBackList(currPage, pageSize);
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        currPage = 1;
        mPresenter.getBackList(currPage, pageSize);
    }
}
