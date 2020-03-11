package it.swiftelink.com.factory.presenter.income;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.income.BackResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/25 16:09
 * @一句话描述 银行卡列表契约类
 */

public class MyBankCardPresenter extends BasePresenter<MyBankCardContract.View> implements MyBankCardContract.Presenter {

    public MyBankCardPresenter(MyBankCardContract.View view) {
        super(view);
    }

    @Override
    public void getBackList(int currPage, int pageSize) {
        start();
        Observable<BackResModel> observable = NetWork.getRequest().getBackList(currPage, pageSize);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BackResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.POST_ADDBACKLIST_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(BackResModel backResModel) {
                if (backResModel.isSuccess()) {
                    mView.getBackList(backResModel);
                } else {
                    mView.showError(Common.UrlApi.POST_ADDBACKLIST_CODE, backResModel.getMessage());
                }

            }
        });
    }
}
