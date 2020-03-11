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

public class ModifyLoginPwdPresenter
        extends BasePresenter<ModifyLoginPwdContract.View>
        implements ModifyLoginPwdContract.Presenter {
    public ModifyLoginPwdPresenter(ModifyLoginPwdContract.View view) {
        super(view);
    }

    @Override
    public void modifyLoginPwd(String oldPwd, String newPwd, String type) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().updatePwdByDoctorAndPatient(oldPwd, newPwd, type);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {

                mView.showError(Common.UrlApi.GET_UPDPWD_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {
                if (baseResModel.isSuccess()) {
                    mView.modifyLoginPwdSuccess();
                } else {
                    mView.showError(Common.UrlApi.GET_UPDPWD_CODE, baseResModel.getMessage());
                }
            }
        });
    }
}
