package it.swiftelink.com.factory.presenter.income;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.home.BannerResModel;
import it.swiftelink.com.factory.model.home.OrdersRankingResModel;
import it.swiftelink.com.factory.model.income.BalanceResModel;
import it.swiftelink.com.factory.net.NetWork;
import it.swiftelink.com.factory.presenter.home.HomeContract;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/25 14:49
 * @一句话描述 余额Presenter
 */


public class SmallChangePresenter extends BasePresenter<SmallChangeContract.View> implements SmallChangeContract.Presenter {

    public SmallChangePresenter(SmallChangeContract.View view) {
        super(view);
    }

    /**
     * 获取余额
     */
    @Override
    public void getBalance() {
        start();
        Observable<BalanceResModel> observable = NetWork.getRequest().getBalance();

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BalanceResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_BALANCE_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(BalanceResModel balanceResModel) {
                if (balanceResModel.isSuccess()) {
                    mView.getBalance(balanceResModel.getData());
                } else {
                    mView.showError(Common.UrlApi.GET_BALANCE_CODE, balanceResModel.getMessage());
                }

            }
        });

    }
}
