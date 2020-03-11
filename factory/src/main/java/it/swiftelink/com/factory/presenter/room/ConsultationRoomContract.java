package it.swiftelink.com.factory.presenter.room;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.NoticeResModel;
import it.swiftelink.com.factory.model.PageParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationSaveParmModel;
import it.swiftelink.com.factory.model.room.ClinicinfoResModel;
import it.swiftelink.com.factory.model.room.DiagnosisOrderResModel;
import it.swiftelink.com.factory.model.room.DoctorProfileResModel;
import it.swiftelink.com.factory.model.room.PatientInfoResModel;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/24 20:41
 * @一句话描述 我的诊室契约类
 */

public class ConsultationRoomContract extends BaseContract {

    public interface View extends BaseContract.View<Presenter> {
        //获取医生信息
        void getClinicinfoSuccess(ClinicinfoResModel clinicinfoResModel);

        //医生上线
        void onlineSuccess();

        /**
         * 抢单接口
         */
        void matchOrderSuccess(PatientInfoResModel resModel);

        //trtc成功
        void getTrtcConfigSuccess(TrtcConfigResModel model);


        void getEvaluationListSuccess(EvaluationResModel evaluationResModel);

        //
        void evaluationSave();

        //获取医生简介
        void getDoctorProfile(DoctorProfileResModel doctorProfileResModel);

    }

    public interface Presenter extends BaseContract.Presenter {
        //获取医生信息
        void getClinicinfo();

        //医生上线
        void online(String onlineStatus);

        /**
         * 医生端抢单
         *
         * @param diagnosisId
         */
        void matchOrder(String diagnosisId);

        /**
         * 获取trtc 相关
         */
        void getTrtcConfig(String uuid, String userId);


        void getEvaluationListSuccess(EvaluationParmModel parmModel);

        void evaluationSave(EvaluationSaveParmModel saveParmModel);


        void getDoctorProfile();
    }

}
