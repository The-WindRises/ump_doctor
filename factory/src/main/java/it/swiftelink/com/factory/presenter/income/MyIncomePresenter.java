package it.swiftelink.com.factory.presenter.income;


import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.income.BackResModel;
import it.swiftelink.com.factory.model.income.MyIncomeRecordResModel;
import it.swiftelink.com.factory.model.income.MyIncomeResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/26 15:55
 * @一句话描述 我的收入Presenter
 */


public class MyIncomePresenter extends BasePresenter<MyIncomeContract.View> implements MyIncomeContract.Presenter {


    public MyIncomePresenter(MyIncomeContract.View view) {
        super(view);
    }

    /**
     * 获取我的收入
     */
    @Override
    public void getMyIncome() {
        start();
        Observable<MyIncomeResModel> observable = NetWork.getRequest().getMyIncome();

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<MyIncomeResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_RECORD_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(MyIncomeResModel myIncomeResModel) {
                if (myIncomeResModel.isSuccess()) {
                    mView.getMyIncome(myIncomeResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_RECORD_CODE, myIncomeResModel.getMessage());
                }

            }
        });

    }

    /**
     * 按月查询收入支出记录
     *
     * @param date
     */
    @Override
    public void getMyIncomeRecord(String date) {
        start();
        Observable<MyIncomeRecordResModel> observable = NetWork.getRequest().getMyIncomeRecord(date);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<MyIncomeRecordResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_RECORD_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(MyIncomeRecordResModel myIncomeRecordResModel) {
                if (myIncomeRecordResModel.isSuccess()) {
                    mView.getMyIncomeRecord(myIncomeRecordResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_RECORD_CODE, myIncomeRecordResModel.getMessage());
                }

            }
        });


    }
}
