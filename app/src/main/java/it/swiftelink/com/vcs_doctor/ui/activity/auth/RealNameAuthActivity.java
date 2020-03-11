package it.swiftelink.com.vcs_doctor.ui.activity.auth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.auth.AuthInfoPramModel;
import it.swiftelink.com.factory.model.auth.AuthParmModel;
import it.swiftelink.com.factory.model.auth.DoctorFile;
import it.swiftelink.com.factory.model.auth.UploadResModel;
import it.swiftelink.com.factory.model.user.UseInfoResModel;
import it.swiftelink.com.factory.presenter.auth.AuthContract;
import it.swiftelink.com.factory.presenter.auth.AuthPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.UpdateEvent;
import it.swiftelink.com.vcs_doctor.ui.activity.MainActivity;

/**
 * 实名认证
 */
@BindEventBus
public class RealNameAuthActivity extends BasePresenterActivity<AuthContract.Presenter> implements AuthContract.View {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.set_three)
    TextView set_three;
    @BindView(R.id.qualification_number)
    EditText qualificationNumberEt;
    @BindView(R.id.doctorstitle_Positive)
    ImageView doctorstitlePositive;
    @BindView(R.id.doctorstitle_Side)
    ImageView doctorstitleSide;
    @BindView(R.id.qualification1_Positive)
    ImageView qualification1Positive;
    @BindView(R.id.qualification1_Side)
    ImageView qualification1Side;
    @BindView(R.id.other_Positive)
    ImageView otherPositive;
    @BindView(R.id.other_Side)
    ImageView otherSide;
    @BindView(R.id.doctorstitlenumber)
    EditText doctorstitleNumberEt;
    @BindView(R.id.qualification1_number)
    EditText qualification1NumberEt;
    @BindView(R.id.qualification_Positive)
    ImageView qualificationPositive;
    @BindView(R.id.qualification_Side)
    ImageView qualificationSide;

    @BindView(R.id.otherNumberEt)
    EditText otherNumberEt;
    @BindView(R.id.stateView)
    StateView stateView;


    ISListConfig config;

    String qualificationPositivePathId;
    String qualificationSidePathId;

    String doctorstitlePositivePathId;
    String doctorstitleSidePathId;

    String qualification1PositivePathId;
    String qualification1SidePathId;

    String otherPositivePathId;
    String otherSidePathId;


    String qualificationPositivePath;
    String qualificationSidePath;

    String doctorstitlePositivePath;
    String doctorstitleSidePath;

    String qualification1PositivePath;
    String qualification1SidePath;
    String otherPositivePath;
    String otherSidePath;
    String tyre = "qualificationPositive";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_real_name_auth;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tv_title.setText(getString(R.string.authentication));
        set_three.setBackgroundResource(R.mipmap.set_yes);
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
                .title(getString(R.string.tupian))
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#0694F8"))
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

    @OnClick({R.id.btn_back, R.id.qualification_Positive, R.id.auth_btn, R.id.qualification_Side, R.id.doctorstitle_Positive, R.id.doctorstitle_Side, R.id.qualification1_Positive, R.id.qualification1_Side, R.id.other_Positive, R.id.other_Side})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.qualification_Positive:
                //医生职业证书 正面
                // 跳转到图片选择器
                ISNav.getInstance().toListActivity(this, config, Constants.QUALIFICATION_POSITIVE);
                break;
            case R.id.qualification_Side:
                //医生职业证书 反面

                ISNav.getInstance().toListActivity(this, config, Constants.QUALIFICATION_SIDE);
                break;
            case R.id.doctorstitle_Positive:
                //医生职程证书 正面

                ISNav.getInstance().toListActivity(this, config, Constants.DOCTORSTITLE_POSITIVE);
                break;
            case R.id.doctorstitle_Side:
                //医生职程证书 反面

                ISNav.getInstance().toListActivity(this, config, Constants.DOCTORSTITLE_SIDE);
                break;
            case R.id.qualification1_Positive:
                //医生资格证书 正面
                ISNav.getInstance().toListActivity(this, config, Constants.QUALIFICATION1_POSITIVE);
                break;
            case R.id.qualification1_Side:
                //医生资格证书 反面
                ISNav.getInstance().toListActivity(this, config, Constants.QUALIFICATION1_SIDE);

                break;
            case R.id.other_Positive:
                //其他证书 正面
                ISNav.getInstance().toListActivity(this, config, Constants.OTHER_POSITIVE);
                break;
            case R.id.other_Side:
                //其他证书 反面
                ISNav.getInstance().toListActivity(this, config, Constants.OTHER_SIDE);
                break;
            case R.id.auth_btn:
                submit();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        out();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == Constants.QUALIFICATION_POSITIVE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                tyre = "qualificationPositive";
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), qualificationPositive, R.mipmap.certificate1);
                mPresenter.uploadImage(pathList.get(0));
            }
        } else if (requestCode == Constants.QUALIFICATION_SIDE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {

                tyre = "qualificationSide";
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), qualificationSide, R.mipmap.certificate2);
                mPresenter.uploadImage(pathList.get(0));
            }

        } else if (requestCode == Constants.DOCTORSTITLE_POSITIVE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                tyre = "doctorstitlePositive";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), doctorstitlePositive, R.mipmap.certificate1);
            }

        } else if (requestCode == Constants.DOCTORSTITLE_SIDE && resultCode == RESULT_OK && data != null) {

            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {

                tyre = "doctorstitleSide";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), doctorstitleSide, R.mipmap.certificate2);
            }

        } else if (requestCode == Constants.QUALIFICATION1_POSITIVE && resultCode == RESULT_OK && data != null) {

            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                tyre = "qualification1Positive";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), qualification1Positive, R.mipmap.certificate1);
            }

        } else if (requestCode == Constants.QUALIFICATION1_SIDE && resultCode == RESULT_OK && data != null) {

            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                tyre = "qualification1Side";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), qualification1Side, R.mipmap.certificate2);
            }

        } else if (requestCode == Constants.OTHER_POSITIVE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                tyre = "otherPositive";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), otherPositive, R.mipmap.certificate1);
            }
        } else if (requestCode == Constants.OTHER_SIDE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList.size() > 0) {
                tyre = "otherSide";
                mPresenter.uploadImage(pathList.get(0));
                GlideLoadUtils.getInstance().glideLoad(this, pathList.get(0), otherSide, R.mipmap.certificate2);
            }
        }
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
                    String imagePath = doctorFilesBeans.get(i).getFilePath();
                    String no = doctorFilesBeans.get(i).getNo();
                    String fileid = doctorFilesBeans.get(i).getFileId();
                    if (!TextUtils.isEmpty(tyep)) {
                        if (tyep.equals("Practice_Reverse")) {
                            //执业证书反面
                            GlideLoadUtils.getInstance().glideLoad(this, imagePath, qualificationPositive, R.mipmap.certificate2);

                            qualificationPositivePathId = fileid;
                            qualificationPositivePath = imagePath;

                        } else if (tyep.equals("Practice_Front")) {
                            //执业证书正面
                            GlideLoadUtils.getInstance().glideLoad(this, imagePath, qualificationSide, R.mipmap.certificate1);

                            qualificationSidePathId = fileid;
                            qualificationSidePath = imagePath;
                        }

                        if (tyep.equals("Practice_Front") || tyep.equals("Practice_Reverse")) {
                            qualificationNumberEt.setText(no);

                        }
                        if (tyep.equals("Job_Certificate_Front")) {
                            //职称证书正面

                            doctorstitlePositivePathId = fileid;
                            doctorstitlePositivePath = imagePath;

                            GlideLoadUtils.getInstance().glideLoad(this, imagePath, doctorstitlePositive, R.mipmap.certificate1);
                        } else if (tyep.equals("Job_Certificate_Reverse")) {
                            //职称证书反面
                            doctorstitleSidePathId = fileid;
                            doctorstitleSidePath = imagePath;
                            GlideLoadUtils.getInstance().glideLoad(this, imagePath, doctorstitleSide, R.mipmap.certificate1);

                        }

                        if (tyep.equals("Job_Certificate_Reverse") || tyep.equals("Job_Certificate_Front")) {
                            //职称证书编号
                            doctorstitleNumberEt.setText(no);
                        }

                        if (tyep.equals("Qualification_Front")) {
                            //资格证书正面照
                            qualification1PositivePathId = fileid;
                            qualification1PositivePath = imagePath;


                            GlideLoadUtils.getInstance().glideLoad(this, imagePath, qualification1Positive, R.mipmap.certificate1);
                        } else if (tyep.equals("Qualification_Reverse")) {

                            qualification1SidePathId = fileid;
                            qualification1SidePath = imagePath;
                            //资格证书反面照
                            GlideLoadUtils.getInstance().glideLoad(this, imagePath, qualification1Side, R.mipmap.certificate2);
                        }

                        if (tyep.equals("Qualification_Reverse") || tyep.equals("Qualification_Front")) {
                            //资格证书编号
                            qualification1NumberEt.setText(no);
                        }

                        if (tyep.equals("Other_Certificate_One")) {

                            otherPositivePathId = fileid;
                            otherPositivePath = imagePath;
                            GlideLoadUtils.getInstance().glideLoad(this, imagePath, otherPositive, R.mipmap.certificate1);

                        } else if (tyep.equals("Other_Certificate_Two")) {
                            GlideLoadUtils.getInstance().glideLoad(this, imagePath, otherSide, R.mipmap.certificate2);
                            otherSidePathId = fileid;
                            otherSidePath = imagePath;

                        }

                    }
                }
            }
        }
    }


    void submit() {


        if (TextUtils.isEmpty(qualificationPositivePathId)) {
            App.showToast(R.string.select_qualificationpositive);
            return;
        }

        if (TextUtils.isEmpty(qualificationSidePathId)) {
            App.showToast(R.string.select_qualification);
            return;
        }
        String qualificationNumber = qualificationNumberEt.getText().toString().trim();

        if (TextUtils.isEmpty(qualificationNumber)) {

            App.showToast(R.string.select_qualificationnumber);
            return;
        }

        AuthInfoPramModel authInfoPramModel= (AuthInfoPramModel) getIntent().getSerializableExtra("AUTH_INFO");
        mPresenter.authInfo(authInfoPramModel);

    }
    private void authUser(){
        String qualificationNumber = qualificationNumberEt.getText().toString().trim();
        String doctorstitleNumber = doctorstitleNumberEt.getText().toString().trim();
        String qualification1Number = qualification1NumberEt.getText().toString().trim();
        String cardPositiveIconId = App.getSPUtils().getString("cardPositiveIconId");
        String cardPositiveIconPath = App.getSPUtils().getString("cardPositiveIconPath");
        String cardSideIconId = App.getSPUtils().getString("cardSideIconId");
        String cardSideIconPath = App.getSPUtils().getString("cardSideIconPath");
        String authIdCardNum = App.getSPUtils().getString("authIdCardNum");

        List<DoctorFile> doctorFiles = new ArrayList<>();

        //身份证
        DoctorFile doctorFileIdCardFront = new DoctorFile();
        doctorFileIdCardFront.setNo(authIdCardNum);
        doctorFileIdCardFront.setFileId(cardPositiveIconId);
        doctorFileIdCardFront.setFilePath(cardPositiveIconPath);
        doctorFileIdCardFront.setType("IdCard_Front");
        doctorFiles.add(doctorFileIdCardFront);
        DoctorFile doctorFileIdCardReverse = new DoctorFile();
        doctorFileIdCardReverse.setNo(authIdCardNum);
        doctorFileIdCardReverse.setFileId(cardSideIconId);
        doctorFileIdCardReverse.setFilePath(cardSideIconPath);
        doctorFileIdCardReverse.setType("IdCard_Reverse");
        doctorFiles.add(doctorFileIdCardReverse);
        //执业证书
        DoctorFile practiceReverseFile = new DoctorFile();
        practiceReverseFile.setType("Practice_Front");
        practiceReverseFile.setFileId(qualificationPositivePathId);
        practiceReverseFile.setFilePath(qualificationPositivePath);
        practiceReverseFile.setNo(qualificationNumber);
        doctorFiles.add(practiceReverseFile);
        DoctorFile PracticeFrontFile = new DoctorFile();
        PracticeFrontFile.setType("Practice_Reverse");
        PracticeFrontFile.setFileId(qualificationSidePathId);
        PracticeFrontFile.setFilePath(qualificationSidePath);
        PracticeFrontFile.setNo(qualificationNumber);
        doctorFiles.add(PracticeFrontFile);
        //职称证书
        if (!"".equals(doctorstitlePositivePathId) && !"".equals(doctorstitlePositivePath)) {
            DoctorFile jobDoctorFileFront = new DoctorFile();
            jobDoctorFileFront.setNo(doctorstitleNumber);
            jobDoctorFileFront.setType("Job_Certificate_Front");
            jobDoctorFileFront.setFileId(doctorstitlePositivePathId);
            jobDoctorFileFront.setFilePath(doctorstitlePositivePath);
            doctorFiles.add(jobDoctorFileFront);
            DoctorFile jobDoctorFileReverse = new DoctorFile();
            jobDoctorFileReverse.setNo(doctorstitleNumber);
            jobDoctorFileReverse.setType("Job_Certificate_Reverse");
            jobDoctorFileReverse.setFileId(doctorstitleSidePathId);
            jobDoctorFileReverse.setFilePath(doctorstitleSidePath);
            doctorFiles.add(jobDoctorFileReverse);
        }
        if (qualification1PositivePathId != "" && qualification1PositivePath != "") {
            //资格证书
            DoctorFile qualification1Front = new DoctorFile();
            qualification1Front.setNo(qualification1Number);
            qualification1Front.setFileId(qualification1PositivePathId);
            qualification1Front.setFilePath(qualification1PositivePath);
            qualification1Front.setType("Qualification_Front");
            doctorFiles.add(qualification1Front);
            DoctorFile qualification1Reverse = new DoctorFile();
            qualification1Reverse.setNo(qualification1Number);
            qualification1Reverse.setFileId(qualification1SidePathId);
            qualification1Reverse.setFilePath(qualification1SidePath);
            qualification1Reverse.setType("Qualification_Reverse");
            doctorFiles.add(qualification1Reverse);
        }
        //其他证书
        if (!TextUtils.isEmpty(otherPositivePathId) && !TextUtils.isEmpty(otherSidePathId)) {
            DoctorFile otherDoctorFile1 = new DoctorFile();
            otherDoctorFile1.setType("Other_Certificate_One");
            otherDoctorFile1.setFileId(otherPositivePathId);
            otherDoctorFile1.setFilePath(otherPositivePath);
            doctorFiles.add(otherDoctorFile1);
            DoctorFile otherDoctorFile2 = new DoctorFile();
            otherDoctorFile2.setType("Other_Certificate_Two");
            otherDoctorFile2.setFileId(otherSidePathId);
            otherDoctorFile2.setFilePath(otherSidePath);
            doctorFiles.add(otherDoctorFile2);
        }
        AuthParmModel authParmModel = new AuthParmModel();
        authParmModel.setDoctorFileList(doctorFiles);
        mPresenter.auth(authParmModel);
    }
    @Override
    public void uploadSuess(UploadResModel uploadResModel) {
        showContent();
        if (uploadResModel != null && uploadResModel.getFiles().size() > 0) {

            if (tyre.equals("qualificationPositive")) {
                qualificationPositivePathId = uploadResModel.getFiles().get(0).getId();
                qualificationPositivePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (tyre.equals("qualificationSide")) {
                qualificationSidePathId = uploadResModel.getFiles().get(0).getId();
                qualificationSidePath = uploadResModel.getFiles().get(0).getUrl();

            } else if (tyre.equals("doctorstitlePositive")) {

                doctorstitlePositivePathId = uploadResModel.getFiles().get(0).getId();
                doctorstitlePositivePath = uploadResModel.getFiles().get(0).getUrl();


            } else if (tyre.equals("doctorstitleSide")) {

                doctorstitleSidePathId = uploadResModel.getFiles().get(0).getId();
                doctorstitleSidePath = uploadResModel.getFiles().get(0).getUrl();

            } else if (tyre.equals("qualification1Side")) {

                qualification1SidePathId = uploadResModel.getFiles().get(0).getId();
                qualification1SidePath = uploadResModel.getFiles().get(0).getUrl();


            } else if (tyre.equals("qualification1Positive")) {
                qualification1PositivePathId = uploadResModel.getFiles().get(0).getId();
                qualification1PositivePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (tyre.equals("otherPositive")) {
                otherPositivePathId = uploadResModel.getFiles().get(0).getId();
                otherPositivePath = uploadResModel.getFiles().get(0).getUrl();
            } else if (tyre.equals("otherSide")) {
                otherSidePathId = uploadResModel.getFiles().get(0).getId();
                otherSidePath = uploadResModel.getFiles().get(0).getUrl();
            }
        }

    }

    @Override
    public void authInfoSuess() {
        showContent();
        authUser();
    }


    @Override
    public void authSuess() {
        showContent();
        App.showToast(R.string.submint_suess);
        EventBus.getDefault().postSticky(new UpdateEvent(Common.EventbusType.ISLOGIN));
        toActivity(MainActivity.class);
        finish();
    }

    @Override
    public void editUserInfoSuess() {

    }

    @Override
    public void uploadImageProgressDialog(int progress) {

    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
    }
}
