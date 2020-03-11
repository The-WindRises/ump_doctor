package it.swiftelink.com.factory.presenter.help;

import android.util.Log;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.help.HelpResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/8/1 12:13
 * @一句话描述 帮助
 */


public class HelpPresenter extends BasePresenter<HelpContract.View> implements HelpContract.Presenter {

    public HelpPresenter(HelpContract.View view) {
        super(view);
    }

    @Override
    public void getHelpList(String language) {

        start();
        Observable<HelpResModel> observable = NetWork.getRequest().getHelpList(language);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<HelpResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("getHelpList", e.getMessage());
            }

            @Override
            public void onSuccess(HelpResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.getHelpListSuccess(resModel);
                } else {
                    mView.showError(Common.UrlApi.GET_HELP, resModel.getMessage());
                }
            }
        });
    }
}
