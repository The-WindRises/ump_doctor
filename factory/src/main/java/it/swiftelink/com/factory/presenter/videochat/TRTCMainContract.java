package it.swiftelink.com.factory.presenter.videochat;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.evaluation.EvaluationParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationSaveParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationUpdateParmModel;

public class TRTCMainContract extends BaseContract {

    public interface View extends BaseContract.View<TRTCMainContract.Presenter> {

        void endTRTCSuccess();
//        void getEvaluationListSuccess(EvaluationResModel evaluationResModel);
//        void evaluationSave();
    }

    public interface Presenter extends BaseContract.Presenter {
        void endTRTC(String diagnosisId, boolean isPassive);
//        void getEvaluationListSuccess(EvaluationParmModel parmModel);
//
//        void evaluationSave(EvaluationSaveParmModel saveParmModel);
    }

}
