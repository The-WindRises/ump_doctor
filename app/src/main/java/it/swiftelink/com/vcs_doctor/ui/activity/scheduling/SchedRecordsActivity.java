package it.swiftelink.com.vcs_doctor.ui.activity.scheduling;

/**
 * @作者 Arvin
 * @时间 2019/7/25 21:25
 * @一句话描述 排版记录
 */


import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.scheduling.SchedulingdRecordsResModel;
import it.swiftelink.com.factory.presenter.scheduling.SchedRecordsContract;
import it.swiftelink.com.factory.presenter.scheduling.SchedRecordsPresenter;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.scheduling.adapter.SchedRecordsAdapter;

@BindEventBus
public class SchedRecordsActivity extends
        BasePresenterActivity<SchedRecordsContract.Presenter>
        implements SchedRecordsContract.View,
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    SchedRecordsAdapter schedRecordsAdapter = null;
    private int totalPages = 0;
    private int currPage = 1;
    private int pageSize = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sched_records;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText(getString(R.string.scheduling_application_record));
        swipeRefreshLayout.setOnRefreshListener(this);
        schedRecordsAdapter = new SchedRecordsAdapter();
        schedRecordsAdapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(schedRecordsAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.chedulingdRecords(currPage, pageSize);
    }

    @Override
    protected SchedRecordsContract.Presenter initPresenter() {
        return new SchedRecordsPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick(R.id.btn_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            default:
                break;
        }
    }

    @Override
    public void schedulingdRecords(SchedulingdRecordsResModel resModel) {
        showContent();
        schedRecordsAdapter.loadMoreComplete();
        swipeRefreshLayout.setRefreshing(false);
        if (resModel != null) {
            totalPages = resModel.getData().getTotalPages();
            if (resModel.getData().getDataCount() <= 0) {
                showEmpty();
            } else {
                if (currPage <= 1) {
                    schedRecordsAdapter.setNewData(resModel.getData().getDataList());
                } else {
                    schedRecordsAdapter.addData(resModel.getData().getDataList());
                }
            }


        }
    }

    @Override
    public void retry(int type) {
    }

    @Override
    public void showError(int type, String errorMsg) {
        if (currPage >= 1) {
            schedRecordsAdapter.loadMoreFail();
        }
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        currPage++;
        if (currPage >= totalPages) {
            schedRecordsAdapter.loadMoreEnd();
        } else {
            mPresenter.chedulingdRecords(currPage, pageSize);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        currPage = 1;
        mPresenter.chedulingdRecords(currPage, pageSize);
    }
}

