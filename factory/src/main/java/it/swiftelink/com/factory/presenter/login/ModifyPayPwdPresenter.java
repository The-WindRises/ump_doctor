package it.swiftelink.com.factory.presenter.login;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.model.sms.SmsCodeResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/27 14:17
 * @一句话描述 修改密码Presenter
 */

public class ModifyPayPwdPresenter
        extends BasePresenter<ModifyPayPwdContract.View>
        implements ModifyPayPwdContract.Presenter {
    public ModifyPayPwdPresenter(ModifyPayPwdContract.View view) {
        super(view);
    }


    @Override
    public void modifyPayPwd(String mobile, String smsCode, String password) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().updPayPwd(mobile,smsCode,password);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {

                mView.showError(Common.UrlApi.GET_UPDPAYPWD_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {

                if (baseResModel.isSuccess()) {
                    mView.modifyPayPwdSuccess();
                } else {
                    mView.showError(Common.UrlApi.GET_UPDPAYPWD_CODE, baseResModel.getMessage());
                }
            }
        });

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
}
