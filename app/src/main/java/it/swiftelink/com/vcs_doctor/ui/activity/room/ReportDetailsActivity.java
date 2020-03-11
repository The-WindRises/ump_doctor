package it.swiftelink.com.vcs_doctor.ui.activity.room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.DateTimeUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.room.InquiryReportResModel;
import it.swiftelink.com.factory.model.room.RecipeInfoResModel;
import it.swiftelink.com.factory.presenter.room.ReportDetailsContract;
import it.swiftelink.com.factory.presenter.room.ReportDetailsPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.room.adapter.ReportDetailsAdapter;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

/**
 * @作者 Arvin
 * @时间 2019/7/23 18:49
 * @一句话描述 报告详情
 */
@BindEventBus
public class ReportDetailsActivity extends
        BasePresenterActivity<ReportDetailsContract.Presenter>
        implements ReportDetailsContract.View {
    @BindView(R.id.prescriptionTv)
    TextView prescriptionTv;
    @BindView(R.id.weightTv)
    TextView weightTv;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sexIv)
    ImageView sexIv;
    @BindView(R.id.ageTv)
    TextView ageTv;
    @BindView(R.id.maritalStatusTv)
    TextView maritalStatusTv;
    @BindView(R.id.birthdayTv)
    TextView birthdayTv;
    @BindView(R.id.diagnosisNoTv)
    TextView diagnosisNoTv;
    @BindView(R.id.diagnosisIdTv)
    TextView diagnosisIdTv;
    @BindView(R.id.sectionOfficeNameTv)
    TextView sectionOfficeNameTv;
    @BindView(R.id.doctorNameTv)
    TextView doctorNameTv;
    @BindView(R.id.diagnosisStartTimeTv)
    TextView diagnosisStartTimeTv;
    @BindView(R.id.mainAppealTv)
    TextView mainAppealTv;
    @BindView(R.id.currentMedicalHistorylTv)
    TextView currentMedicalHistorylTv;
    @BindView(R.id.pastHistorylTv)
    TextView pastHistorylTv;
    @BindView(R.id.allergiesTv)
    TextView allergiesTv;
    @BindView(R.id.familyHistoryTv)
    TextView familyHistoryTv;
    @BindView(R.id.preliminaryDiagnosisTv)
    TextView preliminaryDiagnosisTv;
    @BindView(R.id.diagnosisSuggestTitleTv)
    TextView diagnosisSuggestTitleTv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.prescriptionWap)
    CardView prescriptionWap;
    ReportDetailsAdapter reportDetailsAdapter;
    String disgnosisReportId;
    String prescriptionId;
    String prescription;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.report_details));
        Bundle bundle = getIntent().getExtras();
        disgnosisReportId = bundle.getString("disgnosisReportId");
        prescriptionId = bundle.getString("prescriptionId");
        reportDetailsAdapter = new ReportDetailsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(reportDetailsAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getRecipeInfo(disgnosisReportId);
    }

    @Override
    protected ReportDetailsContract.Presenter initPresenter() {
        return new ReportDetailsPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        out();
    }

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @Override
    public void getInquiryReportInfo(InquiryReportResModel reportResModel) {
        showContent();
        if (reportResModel != null && reportResModel.getData() != null && reportResModel.getData().getPrescriptionDrugs() != null) {
            reportDetailsAdapter.setNewData(reportResModel.getData().getPrescriptionDrugs());
            View view = LayoutInflater.from(this).inflate(R.layout.prescription_foot_layout, null, false);
            TextView textView = view.findViewById(R.id.descriptionTv);
            textView.setText(reportResModel.getData().getDescription());
            prescriptionTv.setText(String.format(getResources().getString(R.string.prescription_tips), reportResModel.getData().getValidity()));
            reportDetailsAdapter.addFooterView(view);
        }
    }

    @Override
    public void getRecipeInfo(RecipeInfoResModel recipeInfoResModel) {
        showContent();
        if (recipeInfoResModel != null) {
            RecipeInfoResModel.DataBean dataBean = recipeInfoResModel.getData();
            if (dataBean != null) {
                mainAppealTv.setText(dataBean.getMainAppeal());
                currentMedicalHistorylTv.setText(dataBean.getCurrentMedicalHistory());
                pastHistorylTv.setText(dataBean.getPastHistory());
                allergiesTv.setText(dataBean.getAllergies());
                familyHistoryTv.setText(dataBean.getFamilyHistory());
                diagnosisSuggestTitleTv.setText(dataBean.getDiagnosisSuggest());
                RecipeInfoResModel.DataBean.DiagnosisBean diagnosisBean = dataBean.getDiagnosis();
                if (diagnosisBean != null) {
                    preliminaryDiagnosisTv.setText(diagnosisBean.getPreliminaryDiagnosis());
                    diagnosisNoTv.setText(diagnosisBean.getNo());
                    diagnosisIdTv.setText(diagnosisBean.getMedicalRecord());
                    sectionOfficeNameTv.setText(diagnosisBean.getSectionOfficeName());
                    doctorNameTv.setText(diagnosisBean.getDoctorName());
                    name.setText(diagnosisBean.getPatientName());
                    try {
                        String birthday = diagnosisBean.getBirthday();
                        if (!TextUtils.isEmpty(birthday)) {
                            String birthdayStr = DateUtils.convertToString(Long.parseLong(birthday), DateUtils.DATE_FORMAT);
                            ageTv.setText(DateTimeUtils.getAge(birthdayStr, this));
                            birthdayTv.setText(birthdayStr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if ("Y".equals(diagnosisBean.getMaritalStatus())) {
                        maritalStatusTv.setText(getString(R.string.married));
                    } else {
                        maritalStatusTv.setText(getString(R.string.unmarried));
                    }
                    if ("".equals(diagnosisBean.getGender())) {
                        sexIv.setImageResource(R.mipmap.icon_man);
                    } else {
                        sexIv.setImageResource(R.mipmap.nv);
                    }
                    if (!TextUtils.isEmpty(dataBean.getWeight())) {
                        DecimalFormat df = new DecimalFormat("0.00");
                        weightTv.setText(df.format(Double.valueOf(dataBean.getWeight())));//体重
                    }
                    String diagnosisStartTime = DateUtils.convertToString(diagnosisBean.getDiagnosisStartTime(), DateUtils.TIME_FORMAT);
                    diagnosisStartTimeTv.setText(diagnosisStartTime);
                }
                //加载处方
                if (dataBean.getNeedPrescription() == 1) {
                    mPresenter.getInquiryReportInfo(diagnosisBean.getId());
                    prescriptionWap.setVisibility(View.VISIBLE);
                } else {
                    prescriptionWap.setVisibility(View.GONE);
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
        App.showToast(errorMsg);
    }
}
