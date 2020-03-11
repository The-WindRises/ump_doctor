package it.swiftelink.com.factory.presenter.login;

import android.util.Log;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.login.ForgetPsdParaModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.model.sms.SmsCodeResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/26 9:50
 * @一句话描述 忘记密码
 */

public class ForgetPsdPresenter extends BasePresenter<ForgetPsdContract.View> implements ForgetPsdContract.Presenter {

    public ForgetPsdPresenter(ForgetPsdContract.View view) {
        super(view);
    }


    /**
     * 忘记密码
     *
     * @param forgetPsdParaModel
     */
    @Override
    public void forgetPsd(ForgetPsdParaModel forgetPsdParaModel) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().forgetPsd(forgetPsdParaModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("forgetPsd", e.getMessage());
            }
            @Override
            public void onSuccess(BaseResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.forgetPsdSuccess();
                } else {
                    mView.showError(Common.UrlApi.POST_FORGETPSD, resModel.getMessage());
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
