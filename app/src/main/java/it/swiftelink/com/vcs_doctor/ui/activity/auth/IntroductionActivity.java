package it.swiftelink.com.vcs_doctor.ui.activity.auth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.auth.AuthInfoPramModel;
import it.swiftelink.com.factory.model.auth.UploadResModel;
import it.swiftelink.com.factory.model.user.UseInfoResModel;
import it.swiftelink.com.factory.presenter.auth.AuthContract;
import it.swiftelink.com.factory.presenter.auth.AuthPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

/**
 * 个人简介编辑页面
 */
@BindEventBus
public class IntroductionActivity extends BasePresenterActivity<AuthContract.Presenter> implements AuthContract.View {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.set_one)
    TextView set_one;
    @BindView(R.id.userIcon)
    ImageView userIcon;
    ISListConfig config;
    @BindView(R.id.introduction_et)
    EditText introductionEt;
    @BindView(R.id.stateView)
    StateView stateView;
    String imonPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduction;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tv_title.setText(getString(R.string.authentication));
        dataDisplay();
        set_one.setBackgroundResource(R.mipmap.set_yes);
        // 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        // 自由配置选项
        config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#0694F8"))
                // 标题
                .title(getString(R.string.tupian))
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#0694F8"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();
    }


    @Override
    protected AuthContract.Presenter initPresenter() {
        return new AuthPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick({R.id.btn_back, R.id.next_btn, R.id.iconLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.next_btn:
                next();

                break;
            case R.id.iconLayout:
                // 跳转到图片选择器
                ISNav.getInstance().toListActivity(this, config, 200);
                break;
        }
    }


    /**
     * 数据回显
     */

    void dataDisplay() {
        String useInfoResModelStr = App.getSPUtils().getString("useInfoResModel", "");

        if (!TextUtils.isEmpty(useInfoResModelStr)) {

            UseInfoResModel useInfoResModel = new Gson().fromJson(useInfoResModelStr, UseInfoResModel.class);
            if (useInfoResModel != null && useInfoResModel.getData() != null) {
                UseInfoResModel.DataBean dataBean = useInfoResModel.getData();
                imonPath = dataBean.getHeadImg();
                GlideLoadUtils.getInstance().glideLoadCircle(this, dataBean.getHeadImg(), userIcon, R.mipmap.img_dc_man);
                introductionEt.setText(dataBean.getDescription());
            }
        }
    }


    void next() {

        if (TextUtils.isEmpty(imonPath)) {
            App.showToast(R.string.select_icon);
            return;
        }
        String introduction = introductionEt.getText().toString();

        if (TextUtils.isEmpty(introduction)) {
            App.showToast(R.string.introduction_not_empty);
            return;
        }
        String authName = App.getSPUtils().getString("authName");
        String authBirthday = App.getSPUtils().getString("authBirthday");
        String authAge = App.getSPUtils().getString("authAge");
        String authOrigin = App.getSPUtils().getString("authOrigin");
        String authPermaResi = App.getSPUtils().getString("authPermaResi");
        String authAddress = App.getSPUtils().getString("authAddress");
        String authHospital = App.getSPUtils().getString("authHospital");
        String authDepartmentId = App.getSPUtils().getString("authDepartmentId");
        String authYears = App.getSPUtils().getString("authYears");
        String authTitles = App.getSPUtils().getString("authTitles");
        String authDepartmentTelephone = App.getSPUtils().getString("authDepartmentTelephone");
        String authEmergencyContact = App.getSPUtils().getString("authEmergencyContact");
        String authLanguage = App.getSPUtils().getString("authLanguage");
        String authNature = App.getSPUtils().getString("authNature");
        String authSex = App.getSPUtils().getString("authSex");
        String authEmergencyContactphone = App.getSPUtils().getString("authEmergencyContactphone");
        AuthInfoPramModel authInfoPramModel = new AuthInfoPramModel();
        authInfoPramModel.setName(authName);
        Long birthdaydate = DateUtils.convertToLong(authBirthday, DateUtils.DATE_FORMAT);
        if (birthdaydate != null) {
            authInfoPramModel.setBirthday(birthdaydate);
        }
        authInfoPramModel.setAge(Integer.parseInt(authAge));
        authInfoPramModel.setHeadImg(imonPath);
        authInfoPramModel.setBirthplace(authOrigin);
        authInfoPramModel.setLocation(authPermaResi);
        authInfoPramModel.setContactAddr(authAddress);
        authInfoPramModel.setDescription(introduction);
        authInfoPramModel.setHospital(authHospital);
        authInfoPramModel.setSectionOfficeId(authDepartmentId);
        authInfoPramModel.setPracticeYear(authYears);
        authInfoPramModel.setJobTitle(authTitles);
        authInfoPramModel.setSectionOfficeTel(authDepartmentTelephone);
        authInfoPramModel.setEmergencyContact(authEmergencyContact);
        authInfoPramModel.setLanguage(authLanguage);
        authInfoPramModel.setNature(authNature);
        authInfoPramModel.setGender(authSex);
        authInfoPramModel.setEmergencyContactTel(authEmergencyContactphone);
//        mPresenter.authInfo(authInfoPramModel);

        Bundle bundle=new Bundle();
        bundle.putSerializable("AUTH_INFO",authInfoPramModel);
        toActivity(bundle,IdAuthActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                GlideLoadUtils.getInstance().glideLoadCircle(this, pathList.get(0), userIcon, R.mipmap.img_dc_man);
                mPresenter.uploadImage(pathList.get(0));
            }
        }
    }

    @Override
    public void retry(int type) {
    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
    }

    @Override
    public void uploadSuess(UploadResModel uploadResModel) {
        showContent();
        imonPath = uploadResModel.getFiles().get(0).getUrl();
    }

    @Override
    public void authInfoSuess() {

    }

    @Override
    public void authSuess() {

    }

    @Override
    public void editUserInfoSuess() {

    }

    @Override
    public void uploadImageProgressDialog(int progress) {

    }
}
