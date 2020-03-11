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


/**
 * 身份认证
 */
@BindEventBus
public class IdAuthActivity extends BasePresenterActivity<AuthContract.Presenter> implements AuthContract.View {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.set_tow)
    TextView set_tow;
    ISListConfig config;
    @BindView(R.id.idCard_Positive)
    ImageView idCardPositive;
    @BindView(R.id.idCard_Side)
    ImageView idCardSide;
    @BindView(R.id.idCardNum_et)
    EditText idCardNumEt;
    @BindView(R.id.stateView)
    StateView stateView;

    String cardSideIconId;
    String cardPositiveIconId;
    String type = "cardPositive";
    private static final String TAG = IdAuthActivity.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_auth;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tv_title.setText(getString(R.string.authentication));
        set_tow.setBackgroundResource(R.mipmap.set_yes);
        dataDisplay();
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
                .title("图片")
                .needCamera(true)
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#0694F8"))
                // 第一个是否显示相机，默认true
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

    void dataDisplay() {
        String useInfoResModelStr = App.getSPUtils().getString("useInfoResModel", "");
        if (!TextUtils.isEmpty(useInfoResModelStr)) {
            UseInfoResModel useInfoResModel = new Gson().fromJson(useInfoResModelStr, UseInfoResModel.class);
            if (useInfoResModel != null && useInfoResModel.getData() != null) {
                UseInfoResModel.DataBean dataBean = useInfoResModel.getData();
                List<UseInfoResModel.DataBean.DoctorFilesBean> doctorFilesBeans = dataBean.getDoctorFiles();
                int size = doctorFilesBeans.size();
                for (int i = 0; i < size; i++) {

                    String tyep = doctorFilesBeans.get(i).getType();
                    if (!TextUtils.isEmpty(tyep)) {
                        if (tyep.equals("IdCard_Front")) {
                            type = "cardPositive";
                            cardPositiveIconId = doctorFilesBeans.get(i).getFileId();
                            App.getSPUtils().put("cardPositiveIconId", cardSideIconId);
                            App.getSPUtils().put("cardPositiveIconPath", doctorFilesBeans.get(i).getFilePath());
                            //正面
                            GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBeans.get(i).getFilePath(), idCardPositive, R.mipmap.img_card01);
                        } else if (tyep.equals("IdCard_Reverse")) {
                            //反面
                            type = "cardSide";
                            cardSideIconId = doctorFilesBeans.get(i).getFileId();
                            App.getSPUtils().put("cardSideIconId", cardSideIconId);
                            App.getSPUtils().put("cardSideIconPath", doctorFilesBeans.get(i).getFilePath());
                            GlideLoadUtils.getInstance().glideLoad(this, doctorFilesBeans.get(i).getFilePath(), idCardSide, R.mipmap.img_card01);
                        }
                        if (tyep.equals("IdCard_Reverse") || tyep.equals("IdCard_Front")) {
                            idCardNumEt.setText(doctorFilesBeans.get(i).getNo());
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), idCardPositive, R.mipmap.img_card01);
                type = "cardPositive";
                mPresenter.uploadImage(pathList.get(0));
            }
        } else if (requestCode == 201 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), idCardSide, R.mipmap.img_card02);
                type = "cardSide";
                mPresenter.uploadImage(pathList.get(0));
            }
        }

    }


    @OnClick({R.id.btn_back, R.id.idCard_Positive, R.id.idCard_Side, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.idCard_Positive:
//                // 跳转到图片选择器
                ISNav.getInstance().toListActivity(this, config, 200);
                break;
            case R.id.idCard_Side:
                // 跳转到图片选择器
                ISNav.getInstance().toListActivity(this, config, 201);
                break;
            case R.id.next_btn:
                next();
                break;
        }
    }

    void next() {
        if (TextUtils.isEmpty(cardPositiveIconId)) {
            App.showToast(R.string.select_idcard);
            return;
        }
        if (TextUtils.isEmpty(cardSideIconId)) {
            App.showToast(R.string.select_idcard);
            return;
        }

        String authIdCardNum = idCardNumEt.getText().toString();
        if (TextUtils.isEmpty(authIdCardNum)) {
            App.showToast(R.string.input_idcardno);
            return;
        }
//        if (!TxtUtils.chickedIdCard(authIdCardNum)) {
//            App.showToast(R.string.idcard_notformat);
//            return;
//        }
        App.getSPUtils().put("authIdCardNum", authIdCardNum);
        AuthInfoPramModel authInfoPramModel= (AuthInfoPramModel) getIntent().getSerializableExtra("AUTH_INFO");
        Bundle bundle=new Bundle();
        bundle.putSerializable("AUTH_INFO",authInfoPramModel);
        toActivity(bundle,RealNameAuthActivity.class);
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

    }

    @Override
    public void uploadSuess(UploadResModel uploadResModel) {
        showContent();
        if (uploadResModel != null && uploadResModel.getFiles().size() > 0) {
            if ("cardPositive".equals(type)) {
                cardPositiveIconId = uploadResModel.getFiles().get(0).getId();
                App.getSPUtils().put("cardPositiveIconId", uploadResModel.getFiles().get(0).getId());
                App.getSPUtils().put("cardPositiveIconPath", uploadResModel.getFiles().get(0).getUrl());
            } else if ("cardSide".equals(type)) {
                cardSideIconId = uploadResModel.getFiles().get(0).getId();
                App.getSPUtils().put("cardSideIconId", uploadResModel.getFiles().get(0).getId());
                App.getSPUtils().put("cardSideIconPath", uploadResModel.getFiles().get(0).getUrl());
            }
        }
    }

    @Override
    public void authInfoSuess() {
        showContent();
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


    private int getMyColor(int colorID) {
        return this.getResources().getColor(colorID);
    }


}
