package it.swiftelink.com.factory.presenter.user;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.user.AgrreementResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

public class AgreementPresenter extends BasePresenter<AgreementContract.View> implements AgreementContract.Presenter {

    public AgreementPresenter(AgreementContract.View view) {
        super(view);
    }

    @Override
    public void getAgrreement(String language, String type) {
        start();
        Observable<AgrreementResModel> observable = NetWork.getRequest().getAgrreement(language, type);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<AgrreementResModel>() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(AgrreementResModel agrreementResModel) {

                if (agrreementResModel.isSuccess()) {

                    mView.getAgrreementSuess(agrreementResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_AGEERRMENT, agrreementResModel.getMessage());

                }


            }
        });

    }
}
