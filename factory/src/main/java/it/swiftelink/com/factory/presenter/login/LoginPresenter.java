package it.swiftelink.com.factory.presenter.login;


import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.common.utils.SPUtils;
import it.swiftelink.com.factory.model.login.LoginParamModel;
import it.swiftelink.com.factory.model.login.LoginResModel;
import it.swiftelink.com.factory.model.login.RegisterParamModel;
import it.swiftelink.com.factory.model.login.RegisterResModel;
import it.swiftelink.com.factory.model.login.SmsLoginParamModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.model.sms.SmsCodeResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     */
    @Override
    public void login(String userName, String password) {
        start();

        Observable<LoginResModel> observable = NetWork.getRequest().login(new LoginParamModel(userName, password));


        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<LoginResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.POST_LOGIN, e.getMessage());
            }

            @Override
            public void onSuccess(LoginResModel loginResModel) {
                if (loginResModel.isSuccess()) {
                    mView.loginSuccess(loginResModel);
                } else {
                    mView.showError(Common.UrlApi.POST_LOGIN, loginResModel.getMessage());
                }
            }
        });

    }

    @Override
    public void smsLogin(String phoneNum, String smsCode) {
        start();
        Observable<LoginResModel> observable = NetWork.getRequest().smsLogin(new SmsLoginParamModel(phoneNum,smsCode));
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<LoginResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.POST_LOGIN, e.getMessage());
            }
            @Override
            public void onSuccess(LoginResModel loginResModel) {
                if (loginResModel.isSuccess()) {
                    mView.loginSuccess(loginResModel);
                } else {
                    mView.showError(Common.UrlApi.POST_LOGIN, loginResModel.getMessage());
                }
            }
        });
    }

    /**
     * 获取短信验证码
     *
     * @param smsCodeParamModel
     */
    @Override
    public void getPhoneCode(SmsCodeParamModel smsCodeParamModel) {
        start();
        Observable<SmsCodeResModel> observable = NetWork.getRequest().getSmsCode(smsCodeParamModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<SmsCodeResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.Get_SMSCODE, e.getMessage());
            }

            @Override
            public void onSuccess(SmsCodeResModel smsCodeResModel) {
                if (smsCodeResModel.isSuccess()) {
                    mView.getPhoneCodeResModelSuccess();
                } else {
                    mView.showError(Common.UrlApi.Get_SMSCODE, smsCodeResModel.getMessage());
                }
            }
        });
    }
}
