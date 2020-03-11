package it.swiftelink.com.factory.presenter.my;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.home.BannerResModel;
import it.swiftelink.com.factory.model.home.OrdersRankingResModel;
import it.swiftelink.com.factory.model.home.StatisticsResModel;
import it.swiftelink.com.factory.model.message.MessageRemindResModel;
import it.swiftelink.com.factory.model.my.DoctorInforResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;


/**
 * @作者 Arvin
 * @时间 2019/7/31 18:37
 * @一句话描述 我的页面的数据
 */


public class MyPresenter extends BasePresenter<MyContract.View> implements MyContract.Presenter {

    public MyPresenter(MyContract.View view) {
        super(view);
    }

    @Override
    public void getDoctorInfo(String language) {
        start();
        Observable<DoctorInforResModel> observable = NetWork.getRequest().getDoctorInfo(language);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<DoctorInforResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.POST_DOCTORINFO, e.getMessage());
            }
            @Override
            public void onSuccess(DoctorInforResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.getDoctorInfoSuccess(resModel);
                } else {
                    mView.showError(Common.UrlApi.POST_DOCTORINFO, resModel.getMessage());
                }

            }
        });
    }
    //获取当月问诊信息

    @Override
    public void diagnosisMonthinfo(String doctorId) {

        Observable<StatisticsResModel> observable = NetWork.getRequest().diagnosisMonthinfo(doctorId);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<StatisticsResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_DIAGNOSISMONTHINFO, e.getMessage());
            }

            @Override
            public void onSuccess(StatisticsResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.diagnosisMonthinfo(resModel);
                } else {
                    mView.showError(Common.UrlApi.GET_DIAGNOSISMONTHINFO, resModel.getMessage());
                }
            }


        });
    }

    @Override
    public void remindCount() {
        Observable<MessageRemindResModel> observable = NetWork.getRequest().remindCount();
        NetWork.getInstance().netRequestNot401(observable, new NetWork.NetCallback<MessageRemindResModel>() {
            @Override
            public void onError(ApiException e) {

            }
            @Override
            public void onSuccess(MessageRemindResModel messageRemindResModel) {
                if (messageRemindResModel != null && messageRemindResModel.isSuccess()) {
                    mView.remindCount(messageRemindResModel);
                }
            }
        });
    }
}
