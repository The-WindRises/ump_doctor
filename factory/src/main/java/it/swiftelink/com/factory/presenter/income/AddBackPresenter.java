package it.swiftelink.com.factory.presenter.income;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.income.BackCardParamModel;
import it.swiftelink.com.factory.model.income.BalanceResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/25 15:19
 * @一句话描述 绑定银行卡契约类
 */

public class AddBackPresenter extends BasePresenter<AddBankContract.View> implements AddBankContract.Presenter {

    public AddBackPresenter(AddBankContract.View view) {
        super(view);
    }


    @Override
    public void bindBackCard(String name, String cardNo, String bank, String mobile) {
        start();
        Observable<BalanceResModel> observable = NetWork.getRequest().bindBackCard(name,cardNo,bank,mobile);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BalanceResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.POST_ADDBACK_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(BalanceResModel balanceResModel) {
                if (balanceResModel.isSuccess()) {
                    mView.bindBackCardSuccess();
                } else {
                    mView.showError(Common.UrlApi.POST_ADDBACK_CODE, balanceResModel.getMessage());
                }
            }
        });
    }
}
