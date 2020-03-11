package it.swiftelink.com.factory.presenter.login;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;

/**
 * @作者 Arvin
 * @时间 2019/7/27 12:19
 * @一句话描述 修改登录密码
 */

public class ModifyLoginPwdContract extends BaseContract {
    public interface View extends BaseContract.View<ModifyLoginPwdContract.Presenter> {
        //修改密码
        void modifyLoginPwdSuccess();



    }

    public interface Presenter extends BaseContract.Presenter {
        //修改密码
        void modifyLoginPwd(String oldPwd, String newPwd, String type);
    }
}
