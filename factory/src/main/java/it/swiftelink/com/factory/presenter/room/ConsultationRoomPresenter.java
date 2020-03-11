package it.swiftelink.com.factory.presenter.room;

import android.media.MediaPlayer;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.R;
import it.swiftelink.com.factory.model.NoticeResModel;
import it.swiftelink.com.factory.model.PageParmModel;
import it.swiftelink.com.factory.model.consultation.ConsultationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationSaveParmModel;
import it.swiftelink.com.factory.model.room.ClinicinfoResModel;
import it.swiftelink.com.factory.model.room.DiagnosisOrderResModel;
import it.swiftelink.com.factory.model.room.DoctorProfileResModel;
import it.swiftelink.com.factory.model.room.PatientInfoResModel;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/24 20:50
 * @一句话描述 我的诊室Presenter
 */


public class ConsultationRoomPresenter extends
        BasePresenter<ConsultationRoomContract.View>
        implements ConsultationRoomContract.Presenter {

    public ConsultationRoomPresenter(ConsultationRoomContract.View view) {
        super(view);
    }

    /**
     * 获取医生信息
     */
    @Override
    public void getClinicinfo() {
        Observable<ClinicinfoResModel> observable = NetWork.getRequest().getClinicinfo();

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<ClinicinfoResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_CLINICINFO, e.getMessage());

            }

            @Override
            public void onSuccess(ClinicinfoResModel clinicinfoResModel) {
                if (clinicinfoResModel.isSuccess()) {
                    mView.getClinicinfoSuccess(clinicinfoResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_CLINICINFO, clinicinfoResModel.getMessage());
                }
            }
        });


    }

    /**
     * 医生上线 在线状态（Online：在线，Offline：离线）
     *
     * @param onlineStatus
     */
    @Override
    public void online(String onlineStatus) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().online(onlineStatus);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.PUT_ONLINE_CODE, e.getMessage());

            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {
                if (baseResModel.isSuccess()) {
                    mView.onlineSuccess();
                } else {
                    mView.showError(Common.UrlApi.PUT_ONLINE_CODE, baseResModel.getMessage());
                }
            }
        });
    }


    /**
     * 抢单接口
     *
     * @param diagnosisId
     */
    @Override
    public void matchOrder(String diagnosisId) {
        start();
        Observable<PatientInfoResModel> observable = NetWork.getRequest().matchOrder(diagnosisId);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<PatientInfoResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_MATCHORDER, e.getMessage());
            }

            @Override
            public void onSuccess(PatientInfoResModel resModel) {
                if (resModel != null && resModel.isSuccess()) {
                    mView.matchOrderSuccess(resModel);
                } else {
                    mView.showError(Common.UrlApi.GET_MATCHORDER,
                            resModel.getMessage());
                }
            }
        });
    }


    @Override
    public void getTrtcConfig(String uuid, String userId) {
        start();
        Observable<TrtcConfigResModel> observable = NetWork.getRequest().getTrtcConfig(uuid, userId);
        NetWork.getInstance().baseNetRequest(observable, new NetWork.NetCallback<TrtcConfigResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_TRTC_CONFIG, "连接服务器失败正在重试!");
                Log.i("trtc", e.getMessage());
            }

            @Override
            public void onSuccess(TrtcConfigResModel trtcConfigResModel) {
                if (trtcConfigResModel.isSuccess() && trtcConfigResModel.getUsers().size() > 0) {
                    mView.getTrtcConfigSuccess(trtcConfigResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_TRTC_CONFIG, trtcConfigResModel.getMessage());
                }
            }
        });
    }

    @Override
    public void getEvaluationListSuccess(EvaluationParmModel parmModel) {
        start();
        Observable<EvaluationResModel> observable = NetWork.getRequest().getEvaluationList(parmModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<EvaluationResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("evaluationListSuccess", e.getMessage());
            }

            @Override
            public void onSuccess(EvaluationResModel b) {

                if (b.isSuccess()) {
                    mView.getEvaluationListSuccess(b);
                } else {
                    mView.showError(Common.UrlApi.GET_EVALUATIONLISTSUCCESS, b.getMessage());
                }
            }
        });
    }

    @Override
    public void evaluationSave(EvaluationSaveParmModel saveParmModel) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().evaluationSave(saveParmModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("evaluationUpdate", e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {

                if (baseResModel.isSuccess()) {
                    mView.evaluationSave();
                } else {
                    mView.showError(Common.UrlApi.POST_EVALUATIONUPDATE, baseResModel.getMessage());
                }
            }
        });
    }


    @Override
    public void getDoctorProfile() {
        Observable<DoctorProfileResModel> observable = NetWork.getRequest().getDoctorProfile();
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<DoctorProfileResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_DOCTORPROFILE, e.getMessage());
            }

            @Override
            public void onSuccess(DoctorProfileResModel resModel) {
                if (resModel != null && resModel.isSuccess()) {
                    mView.getDoctorProfile(resModel);
                } else {
                    mView.showError(Common.UrlApi.GET_DOCTORPROFILE,
                            resModel.getMessage());
                }
            }
        });

    }
}
