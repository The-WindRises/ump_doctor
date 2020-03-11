package it.swiftelink.com.vcs_doctor.ui.fragment.main;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import it.swiftelink.com.common.app.BasePresenterFragment;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.common.utils.Utils;
import it.swiftelink.com.common.widget.dialog.ConfirmDialog;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.home.StatisticsResModel;
import it.swiftelink.com.factory.model.message.MessageRemindResModel;
import it.swiftelink.com.factory.model.my.DoctorInforResModel;
import it.swiftelink.com.factory.presenter.my.MyContract;
import it.swiftelink.com.factory.presenter.my.MyPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.UpdateEvent;
import it.swiftelink.com.vcs_doctor.service.MqttService;
import it.swiftelink.com.vcs_doctor.ui.activity.MainActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.auth.AuthenticationActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.consultation.ConsultationActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.evaluation.MyEvaluationActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.help.CustomerServiceActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.help.HelpActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.income.MyIncomeActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.language.LanguageActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.login.LoginActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.login.ModifyPwdActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.message.MessageActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.scheduling.SchedulingActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.user.UserInfoActivity;
import it.swiftelink.com.vcs_doctor.util.DateUtils;
import it.swiftelink.com.vcs_doctor.util.LogUpload;
import it.swiftelink.com.vcs_doctor.util.UserLogin;

/**
 * A simple {@link Fragment} subclass.
 */
@BindEventBus
public class MyFragment extends
        BasePresenterFragment<MyContract.Presenter>
        implements MyContract.View {
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.login_name_tv)
    TextView tvUserName;
    @BindView(R.id.tv_doctor_level)
    TextView tvDoctorLevel;
    @BindView(R.id.tv_online_time)
    TextView tvOnlineTime;
    @BindView(R.id.tv_account_inquiry)
    TextView tvAccountInquiry;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_time_limit)
    TextView tvTimeLimit;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.lv_Tv)
    TextView lv_Tv;
    @BindView(R.id.btn_authentication)
    TextView authenticationTv;
    @BindView(R.id.tv_loginOut)
    TextView tvLoginOut;
    @BindView(R.id.versionTv)
    TextView versionTv;
    @BindView(R.id.my_medical_sr)
    SmartRefreshLayout mSmartRefreshLayout;
    public MyFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected MyContract.Presenter initPresenter() {
        return new MyPresenter(this);
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        tvTitle.setText(getString(R.string.my));
        btn_back.setVisibility(View.GONE);
        ivRight.setVisibility(View.VISIBLE);
        versionTv.setText(Utils.getVersionName());
        initRefreshLayout();
    }

    @Override
    protected void initData() {
        super.initData();
        String doctorId = App.getSPUtils().getString("LoginDoctorId", "");
        String token = App.getSPUtils().getString("accessToken", "");

        if (!TextUtils.isEmpty(token)) {
            tvLoginOut.setVisibility(View.VISIBLE);
            tvDoctorLevel.setVisibility(View.VISIBLE);
            lv_Tv.setVisibility(View.VISIBLE);
            authenticationTv.setVisibility(View.VISIBLE);
            tvUserName.setEnabled(false);
            mPresenter.getDoctorInfo(language);
            mPresenter.diagnosisMonthinfo(doctorId);


        } else {
            tvLoginOut.setVisibility(View.GONE);
            tvDoctorLevel.setVisibility(View.GONE);
            lv_Tv.setVisibility(View.GONE);
            authenticationTv.setVisibility(View.GONE);
            tvUserName.setEnabled(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.remindCount();

    }

    @Override
    protected StateView getStateView() {
        return stateView;
    }

    @OnClick({R.id.iv_header, R.id.login_name_tv, R.id.tv_loginOut, R.id.btn_back, R.id.btn_to_sign_on, R.id.btn_authentication, R.id.iv_my1, R.id.tv_my1,
            R.id.iv_my2, R.id.tv_my2, R.id.iv_my3, R.id.tv_my3, R.id.iv_my4, R.id.tv_my4, R.id.tv_my5,
            R.id.tv_my6, R.id.tv_my7, R.id.tv_my8, R.id.tv_my9, R.id.iv_right, R.id.balance_layout, R.id.versionLy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                if (UserLogin.isLogin()) {
                    UserInfoActivity.show(getActivity());
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
            case R.id.btn_back:
                break;
            case R.id.btn_to_sign_on:
                break;
            case R.id.btn_authentication:
                if (UserLogin.isLogin()) {
                    toActivity(AuthenticationActivity.class);
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
            case R.id.iv_my1:
            case R.id.tv_my1:
                if (UserLogin.isLogin()) {
                    toActivity(SchedulingActivity.class);
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
            case R.id.iv_my2:
            case R.id.tv_my2:
                if (UserLogin.isLogin()) {
                    toActivity(ConsultationActivity.class);
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
            case R.id.iv_my3:
            case R.id.tv_my3:
                if (UserLogin.isLogin()) {
                    toActivity(MyEvaluationActivity.class);
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
            case R.id.iv_my4:
            case R.id.tv_my4:
                if (UserLogin.isLogin()) {
                    toActivity(MyIncomeActivity.class);
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
            case R.id.tv_my5:
                if (UserLogin.isLogin()) {
                    toActivity(HelpActivity.class);
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
            case R.id.tv_my6:
                if (UserLogin.isLogin()) {
                    toActivity(ModifyPwdActivity.class);
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
            case R.id.tv_my7:
                toActivity(CustomerServiceActivity.class);
                break;
            case R.id.tv_my8:
                break;
            case R.id.tv_my9:
                toActivity(LanguageActivity.class);
                break;
            case R.id.iv_right:

                if (UserLogin.isLogin()) {
                    toActivity(MessageActivity.class);
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
            case R.id.balance_layout:
                break;
            case R.id.tv_loginOut:

                showLoginOuTDialog();

                break;
            case R.id.login_name_tv:
                toActivity(LoginActivity.class);
                break;
            case R.id.versionLy:
                LogUpload.upload();
                break;
        }
    }

    @Override
    public void getDoctorInfoSuccess(DoctorInforResModel resModel) {
        showContent();
        DoctorInforResModel.DoctorInfor doctorInfor = resModel.getData();
        if (resModel != null && doctorInfor != null) {
            App.getSPUtils().put("doctorStatus", doctorInfor.getDoctorStatus());
            App.getSPUtils().put("doctorLanguage", doctorInfor.getLanguage());
            authenticationTv.setVisibility(View.VISIBLE);
            authenticationTv.setVisibility(View.VISIBLE);
            GlideLoadUtils.getInstance().glideLoadCircle(getActivity(),
                    doctorInfor.getHeadImg(), ivHeader, R.mipmap.icon_doctor_women);
            tvUserName.setText(doctorInfor.getName());
            DecimalFormat df = new DecimalFormat("#0.00");
            tvAmount.setText("¥ " + df.format(doctorInfor.getAmount()));
            switch (doctorInfor.getDoctorStatus()) {
                case Constants.TOCERTIFIED:
                    //去认证
                    authenticationTv.setText(getString(R.string.ToCertified));
                    authenticationTv.setEnabled(true);
                    break;
                case Constants.CERTIFIEDPENDING:
                    //待认证审批
                    authenticationTv.setText(getString(R.string.CertifiedPending));
                    authenticationTv.setEnabled(false);
                    break;
                case Constants.CERTIFIEDREJECTED:
                    ///认证驳回
                    authenticationTv.setText(getString(R.string.CertifiedRejected));
                    authenticationTv.setEnabled(true);
                    break;
                case Constants.TOASSESS:
                    //去考核
                    authenticationTv.setText(getString(R.string.ToAssess));
                    authenticationTv.setEnabled(false);
                    break;
                case Constants.ASSESSREJECTED:
                    //考核不通过
                    authenticationTv.setText(getString(R.string.AssessRejected));
                    authenticationTv.setEnabled(false);
                    break;
                case Constants.OPENEDVIRTUALCARE:
                    //已开通视频问诊
                    authenticationTv.setText(getString(R.string.open_videoauth));
                    authenticationTv.setEnabled(false);
                    break;
            }

            if (Constants.ATTENDING.equals(doctorInfor.getJobTitle())) {
                //主治医生
                tvDoctorLevel.setText(getString(R.string.attending_doctor));
            } else if (Constants.RESIDENTS.equals(doctorInfor.getJobTitle())) {
                //住院医师
                tvDoctorLevel.setText(getString(R.string.residents));
            } else if (Constants.DEPUTYCHIEFPHYSICIAN.equals(doctorInfor.getJobTitle())) {
                //副主任医师
                tvDoctorLevel.setText(getString(R.string.deputy_chief_physician));

            } else if (Constants.CHIEFPHYSICIAN.equals(doctorInfor.getJobTitle())) {
                //主任医师
                tvDoctorLevel.setText(getString(R.string.chief_physician));
            }

            lv_Tv.setText("LV" + doctorInfor.getLevel());

        } else {

            ivHeader.setImageResource(R.mipmap.icon_doctor_women);
            tvUserName.setText("");
            tvDoctorLevel.setText("");
            tvAmount.setText("0");
            tvAccountInquiry.setText("0");
            tvOnlineTime.setText("0");
        }
    }

    private void showLoginOuTDialog() {
        ConfirmDialog.newInstance(getString(R.string.confirm_exit),
                getString(R.string.cancel), getString(R.string.confirm), true)
                .setConfirmSelect(new ConfirmDialog.ConfirmSelect() {
                    @Override
                    public void onClickCancel() {

                    }

                    @Override
                    public void onClickQuery() {
                        stopService();
                        App.getSPUtils().put(Common.SPApi.TOKEN,"");
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        App.getSPUtils().put("LoginDoctorId","");
                        JPushInterface.deleteAlias(Objects.requireNonNull(getActivity()),0);
                        //退出im
                        loginoutIm();
                    }
                }).setMargin(50)
                .setOutCancel(true)
                .show(getFragmentManager());

    }

    private void stopService() {
        Intent intent = new Intent(getActivity(), MqttService.class);
        getActivity().stopService(intent);
    }


    void loginoutIm() {
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
            }

            @Override
            public void onSuccess() {
                //登出成功
                App.getSPUtils().put("imIsLogin", false);
            }
        });
    }

    @Override
    public void diagnosisMonthinfo(StatisticsResModel resModel) {
        if (resModel != null && resModel.getData() != null) {
            tvAccountInquiry.setText(resModel.getData().getTotalNumberOfOrders());
            String onlineTime = resModel.getData().getTotalOnlineTime();
            if (!"".equals(onlineTime) && null != onlineTime) {
                long time = (Long.parseLong(onlineTime)) * 60 * 1000;
                tvOnlineTime.setText(DateUtils.getDurationInStringHMS2(getActivity(),time));
            }
        }
    }

    @Override
    public void remindCount(MessageRemindResModel messageRemindResModel) {

        if (!"0".equals(messageRemindResModel.getData()) && !"".equals(messageRemindResModel.getData())) {
            ivRight.setImageResource(R.mipmap.icon_msg);
        } else {
            ivRight.setImageResource(R.mipmap.icon_msg_no_read);
        }
    }

    @Override
    public void retry(int type) {
    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
    }

    /**
     * 刷新首页数据
     *
     * @param
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(UpdateEvent updateEvent) {
        if (updateEvent != null) {
            if (updateEvent.getType() == Common.EventbusType.ISLOGIN) {
                tvLoginOut.setVisibility(View.VISIBLE);
                tvDoctorLevel.setVisibility(View.VISIBLE);
                lv_Tv.setVisibility(View.VISIBLE);
                authenticationTv.setVisibility(View.VISIBLE);
                tvUserName.setEnabled(false);
                mPresenter.getDoctorInfo(language);
            }
        }
    }

    //下拉刷新 初始化
    private void initRefreshLayout() {
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(getActivity())));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                String doctorId = App.getSPUtils().getString("LoginDoctorId", "");
                String token = App.getSPUtils().getString("accessToken", "");

                if (!TextUtils.isEmpty(token)) {
                    tvLoginOut.setVisibility(View.VISIBLE);
                    tvDoctorLevel.setVisibility(View.VISIBLE);
                    lv_Tv.setVisibility(View.VISIBLE);
                    authenticationTv.setVisibility(View.VISIBLE);
                    tvUserName.setEnabled(false);
                    mPresenter.getDoctorInfo(language);
                    mPresenter.diagnosisMonthinfo(doctorId);


                } else {
                    tvLoginOut.setVisibility(View.GONE);
                    tvDoctorLevel.setVisibility(View.GONE);
                    lv_Tv.setVisibility(View.GONE);
                    authenticationTv.setVisibility(View.GONE);
                    tvUserName.setEnabled(true);
                }
                refreshLayout.finishRefresh(500);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
