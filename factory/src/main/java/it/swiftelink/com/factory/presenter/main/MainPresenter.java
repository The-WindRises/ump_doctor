package it.swiftelink.com.factory.presenter.main;

import android.util.Log;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.login.LoginParamModel;
import it.swiftelink.com.factory.model.login.LoginResModel;
import it.swiftelink.com.factory.model.main.VersionResModel;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void checkVersion(String device, String type) {

        Observable<VersionResModel> observable = NetWork.getRequest().checkVersion(device, type);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<VersionResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("lqi","---------------"+e.getMessage());
            }

            @Override
            public void onSuccess(VersionResModel versionResModel) {
                if (null != versionResModel && versionResModel.isSuccess()) {
                    mView.checkVersion(versionResModel);
                }
            }
        });
    }

    @Override
    public void getTrtcConfig(String userId) {
        Observable<TrtcConfigResModel> observable = NetWork.getRequest().getTrtcConfig("va1232v3314", userId);
        NetWork.getInstance().baseNetRequest(observable, new NetWork.NetCallback<TrtcConfigResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_TRTC_CONFIG, "连接服务器失败正在重试!");
                Log.i("trtc", e.getMessage());
            }

            @Override
            public void onSuccess(TrtcConfigResModel trtcConfigResModel) {
                mView.getTrtcConfigSuccess(trtcConfigResModel);
            }
        });
    }

    @Override
    public void loginTest() {
        Observable<LoginResModel> observable = NetWork.getRequest().login(new LoginParamModel("13832690678", "123456"));


        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<LoginResModel>() {
            @Override
            public void onError(ApiException e) {
            }

            @Override
            public void onSuccess(LoginResModel loginResModel) {
                if (loginResModel.isSuccess()) {
                    mView.loginTestSuccess(loginResModel);
                }
            }
        });
    }

}
