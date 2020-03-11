package it.swiftelink.com.factory.presenter.consultation;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.consultation.ConsultationResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/27 14:44
 * @一句话描述 问诊记录
 */

public class ConsultationPresenter extends BasePresenter<ConsultationContract.View> implements ConsultationContract.Presenter {


    public ConsultationPresenter(ConsultationContract.View view) {
        super(view);
    }

    @Override
    public void getConsultationList(int currPage, int pageSize, String status,String appType) {
        start();
        Observable<ConsultationResModel> observable = NetWork.getRequest().getConsultationList(currPage, pageSize, status,appType);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<ConsultationResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_CONSULTATIONLIST_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(ConsultationResModel resModel) {

                if (resModel != null && resModel.isSuccess()) {
                    mView.getConsultationList(resModel);
                } else {
                    mView.showError(Common.UrlApi.GET_CONSULTATIONLIST_CODE,
                            resModel.getMessage());
                }
            }


        });

    }
}
