package it.swiftelink.com.factory.presenter.scheduling;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.scheduling.ApplyResModel;
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

public class SchedulingApplyPresenter
        extends BasePresenter<SchedulingApplyContract.View> implements SchedulingApplyContract.Presenter {
    public SchedulingApplyPresenter(SchedulingApplyContract.View view) {
        super(view);
    }



    @Override
    public void toApplySuccess(String originalShiftId, String applyShiftId, String type, String applyReason) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().getToApply(originalShiftId,applyShiftId,type,applyReason);
             NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
                 @Override
                 public void onError(ApiException e) {
                     mView.showError(Common.UrlApi.GET_TOAPPLY,e.getMessage());
                 }

                 @Override
                 public void onSuccess(BaseResModel baseResModel) {
                     if (baseResModel.isSuccess())
                     {
                         mView.toApplySuccess();
                     }else{

                         mView.showError(Common.UrlApi.GET_TOAPPLY,baseResModel.getMessage());
                     }
                 }
             });

    }

    @Override
    public void getToApplyList() {
        start();
        Observable<ApplyResModel> observable = NetWork.getRequest().getToApplyList();

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<ApplyResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_TOAPPLYLIST, e.getMessage());
            }

            @Override
            public void onSuccess(ApplyResModel applyResModel) {
                if (applyResModel.isSuccess()) {
                    mView.getToApplyListSuccess(applyResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_TOAPPLYLIST, applyResModel.getMessage());
                }
            }
        });

    }


}
