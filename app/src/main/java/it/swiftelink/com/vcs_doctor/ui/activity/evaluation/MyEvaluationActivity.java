package it.swiftelink.com.vcs_doctor.ui.activity.evaluation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.common.widget.dialog.CustomDialog;
import it.swiftelink.com.common.widget.nicedialog.BaseNiceDialog;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.evaluation.EvaluationParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationUpdateParmModel;
import it.swiftelink.com.factory.model.evaluation.MyEvaluationResModel;
import it.swiftelink.com.factory.presenter.evaluation.MyEvaluationContract;
import it.swiftelink.com.factory.presenter.evaluation.MyEvaluationPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.evaluation.adapter.MyEvaluationAdapter;
import it.swiftelink.com.vcs_doctor.weight.SpacesItemDecoration;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * @作者 Arvin
 * @时间 2019/7/26 19:38
 * @一句话描述 我的评价
 */
@BindEventBus
public class MyEvaluationActivity extends
        BasePresenterActivity<MyEvaluationContract.Presenter>
        implements MyEvaluationContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        BaseSectionQuickAdapter.RequestLoadMoreListener,
        TabLayout.OnTabSelectedListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int totalPages = 0;
    private int currPage = 1;
    private int pageSize = 10;
    private String type = "All";
    private int tabSelect = 0;
    private String appType = "";
    private int patientScore = 5;
    MyEvaluationAdapter myEvaluationAdapter;
    TagFlowLayout tabLayout;
    List<String> tags = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_evaluation;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshEvent() {
        mPresenter.evaluationList(currPage, pageSize, type, appType);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText(getString(R.string.full_evaluation));
        mTabLayout.addOnTabSelectedListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        myEvaluationAdapter = new MyEvaluationAdapter();
        myEvaluationAdapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(myEvaluationAdapter);
        myEvaluationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyEvaluationResModel.MyEvaluation myEvaluation = (MyEvaluationResModel.MyEvaluation) adapter.getData().get(position);
                String userName = myEvaluation.getPatientName();
                String userIcon = myEvaluation.getPatientHeadImg();
                String id = myEvaluation.getId();
                EvaluationParmModel parmModel = new EvaluationParmModel();
                parmModel.setLanguage(language);
                parmModel.setScore(5);
                parmModel.setType("Doctor");
                mPresenter.getEvaluationListSuccess(parmModel);
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(id)) {
                    shoDialog(userName, userIcon, id);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.evaluationList(currPage, pageSize, type, appType);
    }

    @Override
    protected MyEvaluationContract.Presenter initPresenter() {
        return new MyEvaluationPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick(R.id.btn_back)
    public void onClicky(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
        }
    }

    /**
     * 评价列表
     *
     * @param myEvaluationResModel
     */
    @Override
    public void evaluationList(MyEvaluationResModel myEvaluationResModel) {
        showContent();
        swipeRefreshLayout.setRefreshing(false);
        myEvaluationAdapter.loadMoreComplete();
        if (myEvaluationResModel != null) {
            if (myEvaluationResModel.getData().getDataCount() <= 0) {
                showEmpty();
                myEvaluationAdapter.setNewData(myEvaluationResModel.getData().getDataList());
            } else {
                totalPages = myEvaluationResModel.getData().getTotalPages();
                if (currPage <= 1) {
                    myEvaluationAdapter.setNewData(myEvaluationResModel.getData().getDataList());
                } else {
                    myEvaluationAdapter.addData(myEvaluationResModel.getData().getDataList());
                }
            }
        }
    }

    @Override
    public void getEvaluationListSuccess(EvaluationResModel evaluationResModel) {
        showContent();
        if (evaluationResModel != null && evaluationResModel.getData() != null && evaluationResModel.getData().getDataList() != null) {
            List<EvaluationResModel.DataBean.DataListBean> listBeans
                    = evaluationResModel.getData().getDataList();

            List<String> dataList = new ArrayList<>();
            for (EvaluationResModel.DataBean.DataListBean dataListBean : listBeans) {
                dataList.add(dataListBean.getName());
            }
            tags.clear();
            tags.addAll(dataList);
            if (tabLayout != null) {
                tabLayout.getAdapter().notifyDataChanged();
            }
        }
    }

    @Override
    public void evaluationUpdate() {
        showContent();
        currPage = 1;
        App.showToast(R.string.evaluation_success);
        mPresenter.evaluationList(currPage, pageSize, type, appType);
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {
        myEvaluationAdapter.loadMoreFail();
    }

    /**
     * 刷新
     */

    @Override
    public void onRefresh() {
        currPage = 1;
        mPresenter.evaluationList(currPage, pageSize, type, appType);
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {

        currPage++;
        if (currPage >= totalPages) {
            myEvaluationAdapter.loadMoreEnd();
        } else {
            mPresenter.evaluationList(currPage, pageSize, type, appType);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabSelect = tab.getPosition();
        currPage = 1;
        if (tabSelect == 0) {
            type = "All";
        } else if (tabSelect == 1) {
            type = "Not_Evaluation";
        } else if (tabSelect == 2) {
            type = "Evaluation";
        }
        mPresenter.evaluationList(currPage, pageSize, type, appType);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    public void shoDialog(final String name, final String icon, final String id) {
        CustomDialog.
                newInstance(R.layout.dialog_evaluate)
                .setViewCreateListner(new CustomDialog.ViewCreateListener() {
                    @Override
                    public void onViewCreate(ViewGroup viewGroup, final BaseNiceDialog dialog) {
                        StringBuilder patientEvaluation = new StringBuilder();
                        LinearLayout closeIv = viewGroup.findViewById(R.id.closeIv);
                        ImageView doctorIconIv = viewGroup.findViewById(R.id.doctorIconIv);
                        GlideLoadUtils.getInstance().glideLoadCircle(MyEvaluationActivity.this, icon, doctorIconIv, R.mipmap.icon_dc_man);
                        TextView doctorNameTv = viewGroup.findViewById(R.id.doctorNameTv);
                        doctorNameTv.setText(name);
                        final MaterialRatingBar rb_grade = viewGroup.findViewById(R.id.rb_grade);
                        rb_grade.setElevation((float) 5.0);
                        Button submit = viewGroup.findViewById(R.id.submit);
                        tabLayout = viewGroup.findViewById(R.id.tabLayout);
                        rb_grade.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                EvaluationParmModel parmModel = new EvaluationParmModel();
                                parmModel.setLanguage(language);
                                parmModel.setScore((int) rating);
                                parmModel.setType("Doctor");
                                patientScore = (int) rating;
                                mPresenter.getEvaluationListSuccess(parmModel);
                                tabLayout.getAdapter().notifyDataChanged();
                            }
                        });
                        closeIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StringBuilder stringBuilder = new StringBuilder();
                                EvaluationUpdateParmModel updateParmModel = new EvaluationUpdateParmModel();
                                Set<Integer> integers = tabLayout.getSelectedList();
                                for (Integer integer : integers) {
                                    stringBuilder.append(tags.get(integer));
                                    stringBuilder.append(",");
                                }
                                if (stringBuilder.toString().equals("")) {
                                    App.showToast(R.string.plase_sel_tag);
                                    return;
                                }
                                updateParmModel.setId(id);
                                updateParmModel.setDoctorEvaluation(stringBuilder.toString());
                                updateParmModel.setDoctorStatus("Evaluation");
                                updateParmModel.setDoctorScore(patientScore);
                                mPresenter.evaluationUpdate(updateParmModel);
                                dialog.dismiss();
                            }
                        });

                        final LayoutInflater mInflater = LayoutInflater.from(MyEvaluationActivity.this);
                        tabLayout.setAdapter(new TagAdapter(tags) {
                            @Override
                            public View getView(FlowLayout parent, int position, Object o) {
                                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_tv,
                                        tabLayout, false);
                                tv.setText(o.toString());
                                return tv;
                            }
                        });

                    }
                })
                .setMargin(30)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }


}

