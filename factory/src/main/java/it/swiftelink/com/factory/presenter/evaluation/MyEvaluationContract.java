package it.swiftelink.com.factory.presenter.evaluation;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.evaluation.EvaluationParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationUpdateParmModel;
import it.swiftelink.com.factory.model.evaluation.MyEvaluationResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/26 19:40
 * @一句话描述 我的评价相关契约类
 */

public class MyEvaluationContract extends BaseContract {

    public interface View extends BaseContract.View<MyEvaluationContract.Presenter> {

        /**
         * 评价列表
         *
         * @param myEvaluationResModel
         */

        void evaluationList(MyEvaluationResModel myEvaluationResModel);


        void getEvaluationListSuccess(EvaluationResModel evaluationResModel);

        void evaluationUpdate();


    }


    public interface Presenter extends BaseContract.Presenter {
        /**
         * 评价列表
         *
         * @param currentPage
         * @param pageSize
         * @param doctorStatus
         * @param patientStatus
         */
        void evaluationList(int currentPage, int pageSize, String doctorStatus, String patientStatus);

        void getEvaluationListSuccess(EvaluationParmModel parmModel);


        void evaluationUpdate(EvaluationUpdateParmModel updateParmModel);
    }
}
