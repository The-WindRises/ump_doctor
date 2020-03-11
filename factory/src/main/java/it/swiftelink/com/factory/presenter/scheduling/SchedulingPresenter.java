package it.swiftelink.com.factory.presenter.scheduling;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.room.ClinicinfoResModel;
import it.swiftelink.com.factory.model.scheduling.DaySchedulingdResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingdRecordsResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/25 17:56
 * @一句话描述 排班相关
 */

public class SchedulingPresenter
        extends BasePresenter<SchedulingContract.View> implements SchedulingContract.Presenter {
    public SchedulingPresenter(SchedulingContract.View view) {
        super(view);
    }

    /**
     * 获取我的排班
     *
     * @param yearAndMouth
     */
    @Override
    public void doctorschedulingList(String yearAndMouth) {
        start();
        Observable<SchedulingResModel> observable = NetWork.getRequest().doctorschedulingList(yearAndMouth);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<SchedulingResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.POST_DOCTORSCHEDULING_LIST_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(SchedulingResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.doctorschedulingList(resModel);
                } else {
                    mView.showError(Common.UrlApi.POST_DOCTORSCHEDULING_LIST_CODE, resModel.getMessage());
                }

            }
        });
    }

    /**
     * 获取每天的排班
     *
     * @param date
     */
    @Override
    public void doctorschedulingdDayList(String date) {

        start();
        Observable<DaySchedulingdResModel> observable = NetWork.getRequest().doctorschedulingdDayList(date);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<DaySchedulingdResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.POST_DOCTORSCHEDULINGDAY_LIST_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(DaySchedulingdResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.doctorschedulingdDayList(resModel);
                } else {
                    mView.showError(Common.UrlApi.POST_DOCTORSCHEDULINGDAY_LIST_CODE, resModel.getMessage());
                }

            }
        });

    }

}
