package it.swiftelink.com.vcs_doctor.ui.activity.consultation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.consultation.ConsultationResModel;
import it.swiftelink.com.factory.presenter.consultation.ConsultationContract;
import it.swiftelink.com.factory.presenter.consultation.ConsultationPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.consultation.adapter.ConsultationAdapter;
import it.swiftelink.com.vcs_doctor.ui.activity.room.ReportDetailsActivity;
import it.swiftelink.com.vcs_doctor.weight.SpacesItemDecoration;

/**
 * @作者 Arvin
 * @时间 2019/7/29 10:07
 * @一句话描述 问诊记录
 */

@BindEventBus
public class ConsultationActivity extends
        BasePresenterActivity<ConsultationContract.Presenter>
        implements ConsultationContract.View
        , SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        TabLayout.OnTabSelectedListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.stateView)
    StateView stateView;
    ConsultationAdapter consultationAdapter;
    private int totalPages = 0;
    private int currPage = 1;
    private int pageSize = 10;
    private int tabSelect = 0;
    private String appType = "DOCTOR";
    private String status = "FINISH";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_consultation;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText(getString(R.string.record_of_inquiry));
        consultationAdapter = new ConsultationAdapter();
        consultationAdapter.setOnLoadMoreListener(this, recyclerView);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(consultationAdapter);
        mTabLayout.addOnTabSelectedListener(this);

        consultationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ConsultationResModel.ConsultationModel consultationModel = (ConsultationResModel.ConsultationModel) adapter.getData().get(position);
                if (view.getId() == R.id.reportWap) {
                    Bundle bundle = new Bundle();
                    bundle.putString("disgnosisReportId", consultationModel.getDisgnosisReportId());
                    bundle.putString("prescriptionId", consultationModel.getPrescriptionId());
                    toActivity(bundle, ReportDetailsActivity.class);
                }

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }


    private void getData() {
        mPresenter.getConsultationList(currPage, pageSize, status, appType);
    }


    @Override
    protected ConsultationContract.Presenter initPresenter() {
        return new ConsultationPresenter(this);
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
        }
    }

    /**
     * 问诊记录返回
     *
     * @param resModel
     */
    @Override
    public void getConsultationList(ConsultationResModel resModel) {
        swipeRefreshLayout.setRefreshing(false);
        consultationAdapter.loadMoreComplete();
        if (resModel != null) {
            totalPages = resModel.getData().getTotalPages();
            if (resModel.getData().getDataCount() <= 0) {
                stateView.showEmpty();
                consultationAdapter.setNewData(resModel.getData().getDataList());
            } else {
                stateView.showContent();
                if (currPage <= 1) {
                    consultationAdapter.setNewData(resModel.getData().getDataList());
                } else {
                    consultationAdapter.addData(resModel.getData().getDataList());
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
        swipeRefreshLayout.setRefreshing(false);
        App.showToast(R.string.load_fail);
        consultationAdapter.loadMoreFail();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        currPage = 1;
        getData();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        currPage++;
        if (currPage > totalPages) {
            consultationAdapter.loadMoreEnd();
        } else {
            getData();
        }

    }

    /**
     * 选中的tab
     *
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabSelect = tab.getPosition();
        currPage = 1;
        if (tabSelect == 0) {
            status = "FINISH";
        } else {
            status = "SINGULAR";
        }
        getData();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

