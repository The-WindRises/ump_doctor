package it.swiftelink.com.vcs_doctor.ui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.user.UseInfoResModel;
import it.swiftelink.com.factory.presenter.user.UserInfoContract;
import it.swiftelink.com.factory.presenter.user.UserInfoPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.UpdateEvent;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

@BindEventBus
public class UserInfoActivity extends
        BasePresenterActivity<UserInfoContract.Presenter>
        implements UserInfoContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.iv_user_header)
    ImageView ivUserHeader;
    @BindView(R.id.sexTv)
    TextView sexTv;
    @BindView(R.id.emergencyContactTv)
    TextView emergencyContactTv;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.iv_id_card1)
    ImageView ivIdCard1;
    @BindView(R.id.iv_id_card2)
    ImageView ivIdCard2;
    @BindView(R.id.iv_title_certificates1)
    ImageView ivTitleCertificates1;
    @BindView(R.id.iv_title_certificates2)
    ImageView ivTitleCertificates2;
    @BindView(R.id.tv_certificate_number1)
    TextView tvCertificateNumber1;
    @BindView(R.id.iv_qualification_certificate1)
    ImageView ivQualificationCertificate1;
    @BindView(R.id.iv_qualification_certificate2)
    ImageView ivQualificationCertificate2;
    @BindView(R.id.tv_certificate_number2)
    TextView tvCertificateNumber2;
    @BindView(R.id.iv_another_certificate1)
    ImageView ivAnotherCertificate1;
    @BindView(R.id.iv_another_certificate2)
    ImageView ivAnotherCertificate2;
    @BindView(R.id.tv_certificate_number3)
    TextView tvCertificateNumber3;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.emergencyContactTelTv)
    TextView emergencyContactTelTv;
    @BindView(R.id.practiceReverseIv)
    ImageView practiceReverseIv;
    @BindView(R.id.practiceFrontIV)
    ImageView practiceFrontIV;

    @BindView(R.id.descriptionTv)
    TextView descriptionTv;
    @BindView(R.id.practiceNotV)
    TextView practiceNotV;

    @BindView(R.id.sectionOfficeTelTv)

    TextView sectionOfficeTelTv;

    @BindView(R.id.languageTv)
    TextView languageTv;

    @BindView(R.id.natureTv)
    TextView natureTv;

    @BindView(R.id.idCardNumTv)
    TextView idCardNumTv;


    @BindView(R.id.jobTitleTv)
    TextView jobTitleTv;
    @BindView(R.id.birthday)
    TextView birthdayTv;
    @BindView(R.id.ageTv)
    TextView ageTv;
    @BindView(R.id.locationTv)
    TextView locationTv;
    @BindView(R.id.addressTV)
    TextView addressTv;
    @BindView(R.id.hospitalv)
    TextView hospitalTv;
    @BindView(R.id.sectionOfficeNameTv)
    TextView sectionOfficeNameTv;
    @BindView(R.id.practiceYearTV)
    TextView practiceYearTV;
    @BindView(R.id.originTv)
    TextView originTv;
    UseInfoResModel useInfoResModel;


    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, UserInfoActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.personal_information));
        tvRight.setText(getString(R.string.edit));
        tvRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getUserInfo();
    }

    @Override
    protected UserInfoContract.Presenter initPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick({R.id.tv_right, R.id.iv_user_header, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                mPresenter.checkIsEdit();
                break;
            case R.id.iv_user_header:
                break;
            case R.id.btn_back:
                out();
                break;
        }
    }

    public void editUserinfo() {
        if (useInfoResModel != null && useInfoResModel.getData() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("useInfoResModel", useInfoResModel);
            toActivity(bundle, EditUserInfoActivity.class);
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

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void getUserInfoSuess(UseInfoResModel useInfoResModel) {
        showContent();
        this.useInfoResModel = useInfoResModel;
        UseInfoResModel.DataBean dataBean = useInfoResModel.getData();
        if (dataBean != null) {
            GlideLoadUtils.getInstance().glideLoadCircle(this, dataBean.getHeadImg(), ivUserHeader, R.mipmap.icon_doctor_women);
            tvUserName.setText(dataBean.getName());
            locationTv.setText(dataBean.getLocation());
            addressTv.setText(dataBean.getContactAddr());
            hospitalTv.setText(dataBean.getHospital());
            sectionOfficeNameTv.setText(dataBean.getSectionOfficeName());
            if (null != dataBean.getPracticeYear() && !"".equals(dataBean.getPracticeYear()))
                practiceYearTV.setText(dataBean.getPracticeYear() + getString(R.string.year));
            sectionOfficeTelTv.setText(dataBean.getSectionOfficeTel());
            emergencyContactTelTv.setText(dataBean.getEmergencyContactTel());
            originTv.setText(dataBean.getBirthplace());
            if (dataBean.getAge() != null) {
                ageTv.setText(dataBean.getAge() + getString(R.string.yearold));
            }
            if (Constants.MALE.equals(dataBean.getGender())) {
                sexTv.setText(getString(R.string.male));
            } else if (Constants.FEMALE.equals(dataBean.getGender())) {
                sexTv.setText(getString(R.string.woman));
            }
            if (null != dataBean.getBirthday() && !"".equals(dataBean.getBirthday())) {
                String bir = DateUtils.convertToString(Long.parseLong(dataBean.getBirthday()), DateUtils.DATE_FORMAT);
                if (bir != null) {
                    birthdayTv.setText(bir);
                }
            }
            if (Constants.ATTENDING.equals(dataBean.getJobTitle())) {
                //主治医生
                jobTitleTv.setText(getString(R.string.attending_doctor));
            } else if (Constants.RESIDENTS.equals(dataBean.getJobTitle())) {
                //住院医师
                jobTitleTv.setText(getString(R.string.residents));
            } else if (Constants.DEPUTYCHIEFPHYSICIAN.equals(dataBean.getJobTitle())) {
                //副主任医师
                jobTitleTv.setText(getString(R.string.deputy_chief_physician));
            } else if (Constants.CHIEFPHYSICIAN.equals(dataBean.getJobTitle())) {
                //主任医师
                jobTitleTv.setText(getString(R.string.chief_physician));
            }
            String lanaguge = dataBean.getLanguage();
            StringBuilder stringBuilder = new StringBuilder();
            if (lanaguge.contains(",")) {
                String[] lanaguges = lanaguge.split(",");
                for (String lan : lanaguges) {
                    if (lan.equals(Constants.ZH_CN)) {
                        stringBuilder.append(getString(R.string.mandarin));
                        stringBuilder.append(",");
                    } else if (lan.equals(Constants.ZH_TW)) {
                        stringBuilder.append(getString(R.string.Cantonese));
                        stringBuilder.append(",");
                    } else if (lan.equals(Constants.EN_US)) {
                        stringBuilder.append("English");
                    }
                }
                languageTv.setText(stringBuilder.toString());
            } else {
                if (lanaguge.equals(Constants.ZH_CN)) {
                    languageTv.setText(getString(R.string.mandarin));
                } else if (lanaguge.equals(Constants.ZH_TW)) {
                    languageTv.setText(getString(R.string.Cantonese));
                } else if (lanaguge.equals(Constants.EN_US)) {
                    languageTv.setText("English");
                }
            }
        }
        emergencyContactTv.setText(dataBean.getEmergencyContact());
        if (dataBean.getNature().equals("PartTime")) {
            natureTv.setText(getString(R.string.partJob_rb));
        } else {
            natureTv.setText(getString(R.string.fullJob_rb));
        }
        descriptionTv.setText(dataBean.getDescription());
        List<UseInfoResModel.DataBean.DoctorFilesBean> doctorFilesBeans = dataBean.getDoctorFiles();
        if (doctorFilesBeans != null && doctorFilesBeans.size() > 0) {
            for (UseInfoResModel.DataBean.DoctorFilesBean doctorFilesBean :
                    doctorFilesBeans) {
                if (doctorFilesBean.getType().equals("IdCard_Front")) {
                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), ivIdCard1, R.mipmap.img_card01);
                    idCardNumTv.setText(getString(R.string.document_number) + doctorFilesBean.getNo());
                } else if (doctorFilesBean.getType().equals("IdCard_Reverse")) {
                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), ivIdCard2, R.mipmap.img_card02);
                } else if (doctorFilesBean.getType().equals("Job_Certificate_Front")) {
                    tvCertificateNumber1.setText(getString(R.string.certificateProfessional_title) + doctorFilesBean.getNo());
                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), ivTitleCertificates1, R.mipmap.certificate1);
                } else if (doctorFilesBean.getType().equals("Job_Certificate_Reverse")) {
                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), ivTitleCertificates2, R.mipmap.certificate2);
                } else if (doctorFilesBean.getType().equals("Practice_Front")) {
                    //执业证书
                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), practiceFrontIV, R.mipmap.certificate1);
                    practiceNotV.setText(getString(R.string.certificateProfessional_title) + doctorFilesBean.getNo());
                } else if (doctorFilesBean.getType().equals("Practice_Reverse")) {
                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), practiceReverseIv, R.mipmap.certificate2);
                    //执业证书
                } else if (doctorFilesBean.getType().equals("Qualification_Front")) {
                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), ivQualificationCertificate1, R.mipmap.certificate1);
                    tvCertificateNumber2.setText(getString(R.string.certificateProfessional_title) + doctorFilesBean.getNo());
                } else if (doctorFilesBean.getType().equals("Qualification_Reverse")) {

                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), ivQualificationCertificate2, R.mipmap.certificate2);

                } else if (doctorFilesBean.getType().equals("Other_Certificate_One")) {
                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), ivAnotherCertificate1, R.mipmap.certificate1);
                } else if (doctorFilesBean.getType().equals("Other_Certificate_Two")) {
                    GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBean.getFilePath(), ivAnotherCertificate2, R.mipmap.certificate2);
                }
            }
        }
    }

    @Override
    public void checkIsEdit(BaseResModel baseResModel) {
        showContent();
        editUserinfo();
    }

    /**
     * 刷新首页数据
     *
     * @param
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(UpdateEvent updateEvent) {
        mPresenter.getUserInfo();
    }

}
