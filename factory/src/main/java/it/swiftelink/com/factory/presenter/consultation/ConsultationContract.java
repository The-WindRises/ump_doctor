package it.swiftelink.com.factory.presenter.consultation;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.consultation.ConsultationResModel;


/**
 * @作者 Arvin
 * @时间 2019/7/27 14:40
 * @一句话描述 问诊记录
 */


public class ConsultationContract extends BaseContract {

    public interface View extends BaseContract.View<ConsultationContract.Presenter> {

        void getConsultationList(ConsultationResModel resModel);

    }


    public interface Presenter extends BaseContract.Presenter {
        /**
         * 获取问诊记录
         *
         * @param currPage
         * @param pageSize
         * @param status
         */
        void getConsultationList(int currPage, int pageSize, String status,String appType);
    }
}
