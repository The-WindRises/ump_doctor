package it.swiftelink.com.factory.presenter.scheduling;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.scheduling.SchedulingdRecordsResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/25 21:20
 * @一句话描述 调班记录Presenter
 */


public class SchedRecordsPresenter extends BasePresenter<SchedRecordsContract.View> implements SchedRecordsContract.Presenter {

    public SchedRecordsPresenter(SchedRecordsContract.View view) {
        super(view);
    }

    @Override
    public void chedulingdRecords(int currPage, int pageSize) {

        start();
        Observable<SchedulingdRecordsResModel> observable = NetWork.getRequest().chedulingdRecords(currPage, pageSize);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<SchedulingdRecordsResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_APPROVELIST_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(SchedulingdRecordsResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.schedulingdRecords(resModel);
                } else {
                    mView.showError(Common.UrlApi.GET_APPROVELIST_CODE, resModel.getMessage());
                }

            }
        });




    }
}
