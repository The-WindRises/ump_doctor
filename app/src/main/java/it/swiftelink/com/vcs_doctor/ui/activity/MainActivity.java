package it.swiftelink.com.vcs_doctor.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hacknife.onpermission.OnPermission;
import com.hacknife.onpermission.Permission;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.update.Updater;
import it.swiftelink.com.common.update.UpdaterConfig;
import it.swiftelink.com.common.utils.ACache;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.LogcatHelper;
import it.swiftelink.com.common.utils.OnChangeLanguageEvent;
import it.swiftelink.com.common.utils.Utils;
import it.swiftelink.com.common.widget.dialog.ConfirmDialog;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.login.LoginResModel;
import it.swiftelink.com.factory.model.main.VersionResModel;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;
import it.swiftelink.com.factory.presenter.main.MainContract;
import it.swiftelink.com.factory.presenter.main.MainPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.UpdateEvent;
import it.swiftelink.com.vcs_doctor.service.MqttService;
import it.swiftelink.com.vcs_doctor.ui.activity.auth.AuthenticationActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.login.LoginActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.room.ConsultationRoomActivity;
import it.swiftelink.com.vcs_doctor.ui.fragment.ViewPagerAdapter;
import it.swiftelink.com.vcs_doctor.ui.fragment.main.ClinicFragment;
import it.swiftelink.com.vcs_doctor.ui.fragment.main.FindFragment;
import it.swiftelink.com.vcs_doctor.ui.fragment.main.HomeFragment;
import it.swiftelink.com.vcs_doctor.ui.fragment.main.MyFragment;
import it.swiftelink.com.vcs_doctor.util.UserLogin;
import it.swiftelink.com.vcs_doctor.weight.CustomViewPager;
import it.swiftelink.com.vcs_doctor.weight.UpdateDialog;

@BindEventBus
public class MainActivity extends BasePresenterActivity<MainContract.Presenter> implements MainContract.View {
    @BindView(R.id.vp_main)
    CustomViewPager vpMain;
    @BindView(R.id.tv_tab_home)
    TextView tvTabHome;
    @BindView(R.id.tv_tab_hospital)
    TextView tvTabHospital;
    @BindView(R.id.tv_tab_find)
    TextView tvTabFind;
    @BindView(R.id.tv_tab_my)
    TextView tvTabMy;
    @BindView(R.id.tv_video_chat)
    TextView tvVideoChat;
    @BindView(R.id.ll_to_video_chat)
    LinearLayout llToVideoChat;
    UpdateDialog updateDialog;
    private long mPressedTime = 0;
    private final int TYPE_HOME = 0;
    private final int TYPE_TAB_HOSPITAL = 1;
    private final int TYPE_FIND = 2;
    private final int TYPE_MY = 3;
    private List<Fragment> frags;

    private final static int EXTERNAL_STORAGE_CODE = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPresenter.loginTest();
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_CODE);
            } else {
                String loginId = App.getSPUtils().getString("LoginDoctorId");
                LogcatHelper.getInstance(this, loginId).start();
            }
        } catch (Exception e) {
            Log.e("MainActivity", "", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            if (requestCode == EXTERNAL_STORAGE_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String loginId = App.getSPUtils().getString("LoginDoctorId");
                LogcatHelper.getInstance(this, loginId).start();
            } else {
                App.showToast("授权失败，日志记录无法运行");
            }
        } catch (Exception e) {
            Log.e("MainActivity", "", e);
        }
    }

    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initFragments();
        FragmentManager mFragmentManager = getSupportFragmentManager();
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(mFragmentManager, frags);
        vpMain.setAdapter(mViewPagerAdapter);
        if (App.getSPUtils().getBoolean("refreshUI")) {
            setTab(TYPE_MY);
        } else {
            setTab(TYPE_HOME);
        }
    }

    @Override
    protected void onResume() {
        mPresenter.checkVersion("android", "doctor");
        if (UserLogin.isLogin()) {
            startService();
            String loginId = App.getSPUtils().getString("LoginDoctorId");
            if (!TextUtils.isEmpty(loginId)) {
                JPushInterface.setAlias(this, 0, loginId);
            }
            mPresenter.getTrtcConfig(App.getSPUtils().getString("LoginDoctorId", ""));

        }
        super.onResume();
    }

    private void startService() {
        Intent intent = new Intent(this, MqttService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        String isIgnore = ACache.get(this).getAsString("isIgnore");
        if (TextUtils.isEmpty(isIgnore)) {
            mPresenter.checkVersion("android", "doctor");
        }
    }

    @Override
    protected MainContract.Presenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return null;
    }


    private void initFragments() {
        if (frags == null) {
            frags = new ArrayList<>();
            frags.add(new HomeFragment());
            frags.add(new ClinicFragment());
            frags.add(new FindFragment());
            frags.add(new MyFragment());
        }
    }

    @OnClick({R.id.tv_tab_home, R.id.tv_tab_hospital, R.id.tv_tab_find,
            R.id.tv_tab_my, R.id.ll_to_video_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tab_home:
                App.getSPUtils().put("refreshUI", false);
                setTab(TYPE_HOME);
                break;
            case R.id.tv_tab_hospital:
                App.getSPUtils().put("refreshUI", false);
                setTab(TYPE_TAB_HOSPITAL);
                break;
            case R.id.tv_tab_find:
                App.getSPUtils().put("refreshUI", false);
                setTab(TYPE_FIND);
                break;
            case R.id.tv_tab_my:
                App.getSPUtils().put("refreshUI", false);
                setTab(TYPE_MY);
                break;
            case R.id.ll_to_video_chat:
                App.getSPUtils().put("refreshUI", false);
                if (UserLogin.isLogin()) {
                    String doctorStatus = App.getSPUtils().getString("doctorStatus", "");
                    if (Constants.TOASSESS.equals(doctorStatus)
                            || Constants.ASSESSREJECTED.equals(doctorStatus)
                            || Constants.OPENEDVIRTUALCARE.equals(doctorStatus)) {
                        toActivity(ConsultationRoomActivity.class);
                    } else if (Constants.CERTIFIEDPENDING.equals(doctorStatus)) {
                        toAuthInfo(getString(R.string.to_be_auditedtips), doctorStatus);
                    } else if (Constants.CERTIFIEDREJECTED.equals(doctorStatus)) {
                        toAuthInfo(getString(R.string.authentication_rejection_resubmitted), doctorStatus);
                    } else if (Constants.TOCERTIFIED.equals(doctorStatus)) {
                        toAuthInfo(getString(R.string.decertification), doctorStatus);
                    }
                } else {
                    toActivity(LoginActivity.class);
                }
                break;
        }
    }

    /**
     * 去认证
     */
    private void toAuthInfo(String content, final String doctorStatus) {
        ConfirmDialog.newInstance(content,
                getString(R.string.cancel), getString(R.string.confirm), true)
                .setConfirmSelect(new ConfirmDialog.ConfirmSelect() {
                    @Override
                    public void onClickCancel() {

                    }

                    @Override
                    public void onClickQuery() {
                        if (Constants.CERTIFIEDPENDING.equals(doctorStatus)) {

                        } else {
                            toActivity(AuthenticationActivity.class);
                        }
                    }
                }).setMargin(50)
                .setOutCancel(true)
                .show(getSupportFragmentManager());
    }


    private void setTab(int tabNum) {
        tvTabHome.setSelected(false);
        tvTabHospital.setSelected(false);
        tvTabFind.setSelected(false);
        tvTabMy.setSelected(false);
        switch (tabNum) {
            case TYPE_HOME:
                tvTabHome.setSelected(true);
                vpMain.setCurrentItem(0);
                break;
            case TYPE_TAB_HOSPITAL:
                tvTabHospital.setSelected(true);
                vpMain.setCurrentItem(1);
                break;
            case TYPE_FIND:
                tvTabFind.setSelected(true);
                vpMain.setCurrentItem(2);
                break;
            case TYPE_MY:
                tvTabMy.setSelected(true);
                vpMain.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();
        //获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, getString(R.string.press_the_exit_procedure_again), Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }

    @Override
    public void getTrtcConfigSuccess(TrtcConfigResModel model) {
        if (model != null && model.getUsers() != null && model.getUsers().size() > 0) {
            TrtcConfigResModel.UsersBean usersBean = model.getUsers().get(0);
            imLogin(usersBean);
        }
    }

    /**
     * 初始化im
     *
     * @param usersBean
     */
    public void imLogin(final TrtcConfigResModel.UsersBean usersBean) {

        new OnPermission(this).grant(new Permission() {
            @Override
            public String[] permissions() {
                return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            }

            @Override
            public void onGranted(String[] permission) {
                TIMManager.getInstance().autoLogin(usersBean.getUserId(), new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        TUIKit.login(usersBean.getUserId(), usersBean.getUserToken(), new IUIKitCallBack() {
                            @Override
                            public void onSuccess(Object data) {
                                App.getSPUtils().put("imIsLogin", true);
                            }

                            @Override
                            public void onError(String module, int errCode, String errMsg) {
                                App.getSPUtils().put("imIsLogin", false);
                                imLogin(usersBean);
                            }
                        });
                    }

                    @Override
                    public void onSuccess() {
                        App.getSPUtils().put("imIsLogin", true);
                    }
                });
            }

            @Override
            public void onDenied(String[] permission) {
                App.showToast("拒绝存储权限Im聊天将不可用");
            }
        });

    }


    //检测新的版本
    @Override
    public void checkVersion(VersionResModel versionResModel) {
        if (versionResModel.getData() != null) {
            int versionNumber = versionResModel.getData().getAppVersionNumber();
            int locVersionCode = Utils.getVersionCode();
            if (versionNumber > locVersionCode && !TextUtils.isEmpty(versionResModel.getData().getAppDownloadUrl())) {
                showDialog(versionResModel);
            }
        }
    }

    @Override
    public void loginTestSuccess(LoginResModel loginResModel) {
        if (loginResModel != null) {
            String token = loginResModel.getData().getAccessToken();
            String doctorId = loginResModel.getData().getLoginId();
            String doctorStatus = loginResModel.getData().getDoctorStatus();

            App.getSPUtils().put("accessToken", token);
            App.getSPUtils().put("LoginDoctorId", doctorId);
            App.getSPUtils().put("doctorStatus", doctorStatus);
            App.getSPUtils().put("refreshUI", false);
            EventBus.getDefault().post(new UpdateEvent(Common.EventbusType.ISLOGIN));
            JPushInterface.setAlias(this,0,loginResModel.getData().getLoginId());
            startService();

            LogcatHelper logcatHelper = LogcatHelper.getInstance(this,doctorId);
            logcatHelper.stop();
            logcatHelper.start();
        }
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

    }

    private void showDialog(final VersionResModel versionResModel) {
        updateDialog = UpdateDialog.newInstance(versionResModel.getData().getIsForce(), versionResModel.getData().getUpdateLog(), versionResModel.getData().getAppVersionName());
        //不强制更新
        if (updateDialog != null && versionResModel != null) {

            updateDialog.setCallback(new UpdateDialog.Callback() {
                @Override
                public void onUpgradeNow() {
                    new OnPermission(MainActivity.this).grant(new Permission() {
                        @Override
                        public String[] permissions() {
                            return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        }

                        @Override
                        public void onGranted(String[] permission) {
                            if (versionResModel.getData().getIsForce() == Constants.ISFORCENO) {

                                updateDialog.dismiss();
                            }
                            download(versionResModel.getData().getAppDownloadUrl());
                        }

                        @Override
                        public void onDenied(String[] permission) {
                        }
                    });
                }

                @Override
                public void onIgnore() {
                    //两天后重新检查新版本
                    ACache.get(MainActivity.this).put("isIgnore", versionResModel.getData().getIsForce() + "", 172800);
                    updateDialog.dismiss();
                }
            });
            updateDialog.show(getSupportFragmentManager(), "updateDialog");
            updateDialog.setCancelable(false);
        }
    }

    public void download(String url) {
        UpdaterConfig config = new UpdaterConfig.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setDescription(getString(R.string.system_download_description))
                .setFileUrl(url)
                .setCanMediaScanner(true)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                        | DownloadManager.Request.NETWORK_WIFI)
                .build();
        Updater.get().showLog(true).download(config);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event) {
        if (event != null) {
            this.recreate();
        }
    }
}
