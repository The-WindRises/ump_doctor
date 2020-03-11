package it.swiftelink.com.factory.presenter.home;

import java.util.HashMap;
import java.util.Map;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.PageParmModel;
import it.swiftelink.com.factory.model.home.BannerResModel;
import it.swiftelink.com.factory.model.home.OrdersRankingResModel;
import it.swiftelink.com.factory.model.home.StatisticsResModel;
import it.swiftelink.com.factory.model.message.MessageRemindResModel;
import it.swiftelink.com.factory.model.room.DiagnosisOrderResModel;
import it.swiftelink.com.factory.model.room.PatientInfoResModel;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    public HomePresenter(HomeContract.View view) {
        super(view);
    }

    /**
     * 获取订单排行榜
     *
     * @param language
     */
    @Override
    public void getOrdersRankinglist(String language) {
        start();
        Observable<OrdersRankingResModel> observable = NetWork.getRequest().getOrdersRankinglist(language);


        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<OrdersRankingResModel>() {
            @Override
            public void onError(ApiException e) {

                mView.showError(Common.UrlApi.GET_ORDERSRANKINGLIST_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(OrdersRankingResModel ordersRankingResModel) {
                if (ordersRankingResModel.isSuccess()) {
                    mView.getOrdersRankinglist(ordersRankingResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_ORDERSRANKINGLIST_CODE, ordersRankingResModel.getMessage());
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

    /**
     * 获取轮播图
     */
    @Override
    public void getBannerList(String language, String type) {
        start();
        Observable<BannerResModel> observable = NetWork.getRequest().getBannerList(language, type);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BannerResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_BANNERLIST_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(BannerResModel bannerResModel) {

                if (bannerResModel.isSuccess()) {
                    mView.getBannerList(bannerResModel);
                } else {


                    mView.showError(Common.UrlApi.GET_BANNERLIST_CODE, bannerResModel.getMessage());
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

//    @Override
//    public void diagnosisOrderList(PageParmModel pageParmModel) {
//        Observable<DiagnosisOrderResModel> observable = NetWork.getRequest().diagnosisOrderList(pageParmModel);
//        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<DiagnosisOrderResModel>() {
//            @Override
//            public void onError(ApiException e) {
//                mView.showError(Common.UrlApi.GET_DIAGNOSISORDERLIST_CODE, e.getMessage());
//            }
//
//            @Override
//            public void onSuccess(DiagnosisOrderResModel resModel) {
//                if (resModel != null && resModel.isSuccess()) {
//                    mView.diagnosisOrderList(resModel);
//                } else {
//                    mView.showError(Common.UrlApi.GET_DIAGNOSISORDERLIST_CODE,
//                            resModel.getMessage());
//                }
//            }
//        });
//    }

    /**
     * 抢单接口
     *
     * @param diagnosisId
     */
//    @Override
//    public void matchOrder(String diagnosisId) {
//        start();
//        Observable<BaseResModel> observable = NetWork.getRequest().matchOrder(diagnosisId);
//
//        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
//            @Override
//            public void onError(ApiException e) {
//                mView.showError(Common.UrlApi.GET_MATCHORDER, e.getMessage());
//            }
//
//            @Override
//            public void onSuccess(BaseResModel resModel) {
//                if (resModel != null && resModel.isSuccess()) {
//                    mView.matchOrderSuccess();
//                } else {
//                    mView.showError(Common.UrlApi.GET_MATCHORDER,
//                            resModel.getMessage());
//                }
//            }
//        });
//    }

    /**
     * 获取会员聊天信息信息
     */
//    @Override
//    public void getPatientInfo() {
//        start();
//        Observable<PatientInfoResModel> observable = NetWork.getRequest().getPatientInfo();
//
//        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<PatientInfoResModel>() {
//            @Override
//            public void onError(ApiException e) {
//                mView.showError(Common.UrlApi.GET_PATIENTINFO, e.getMessage());
//            }
//
//            @Override
//            public void onSuccess(PatientInfoResModel resModel) {
//                if (resModel != null && resModel.isSuccess()) {
//                    mView.getPatientInfoSuccess(resModel);
//                } else {
//                    mView.showError(Common.UrlApi.GET_PATIENTINFO,
//                            resModel.getMessage());
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public void getTrtcConfig(String uuid, String userId) {
//        start();
//
//        Observable<TrtcConfigResModel> observable = NetWork.getRequest().getTrtcConfig(uuid, userId);
//
//        NetWork.getInstance().baseNetRequest(observable, new NetWork.NetCallback<TrtcConfigResModel>() {
//            @Override
//            public void onError(ApiException e) {
//                mView.showError(Common.UrlApi.GET_TRTC_CONFIG, e.getMessage());
//            }
//            @Override
//            public void onSuccess(TrtcConfigResModel trtcConfigResModel) {
//
//                if (trtcConfigResModel.getUsers().size() > 0) {
//                    mView.getTrtcConfigSuccess(trtcConfigResModel);
//                } else {
//                    mView.showError(Common.UrlApi.GET_TRTC_CONFIG, trtcConfigResModel.getErrorMessage());
//                }
//            }
//        });
//    }


}
