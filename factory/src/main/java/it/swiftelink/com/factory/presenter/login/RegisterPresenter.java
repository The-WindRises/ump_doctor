package it.swiftelink.com.factory.presenter.login;


import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.login.RegisterParamModel;
import it.swiftelink.com.factory.model.login.RegisterResModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.model.sms.SmsCodeResModel;
import it.swiftelink.com.factory.model.user.AgrreementResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

public class RegisterPresenter extends BasePresenter<RegisterConract.View> implements RegisterConract.Presenter {

    public RegisterPresenter(RegisterConract.View view) {
        super(view);
    }
    /**
     * 获取验证码
     *
     * @param smsCodeParamModel
     */
    @Override
    public void getPhoneCode(final SmsCodeParamModel smsCodeParamModel) {
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

    @Override
    public void register(RegisterParamModel registerParamModel) {

        start();
        Observable<RegisterResModel> observable = NetWork.getRequest().register(registerParamModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<RegisterResModel>() {
            @Override
            public void onError(ApiException e) {

                mView.showError(Common.UrlApi.POST_REGISTER, e.getMessage());
            }

            @Override
            public void onSuccess(RegisterResModel registerResModel) {
                if (registerResModel.isSuccess()) {
                    mView.registerSuccess(registerResModel);
                } else {
                    mView.showError(Common.UrlApi.POST_REGISTER, registerResModel.getMessage());
                }

            }
        });
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
