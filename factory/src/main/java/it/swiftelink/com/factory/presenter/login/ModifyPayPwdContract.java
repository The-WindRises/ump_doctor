package it.swiftelink.com.factory.presenter.login;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;

/**
 * @作者 Arvin
 * @时间 2019/7/27 12:19
 * @一句话描述 修改支付密码
 */

public class ModifyPayPwdContract extends BaseContract {
    public interface View extends BaseContract.View<ModifyPayPwdContract.Presenter> {
        //修改密码
        void modifyPayPwdSuccess();

        //获取短信验证码成功
        void getPhoneCodeResModelSuccess();
    }

    public interface Presenter extends BaseContract.Presenter {
        //修改支付密码
        void modifyPayPwd(String mobile, String smsCode, String password);

        //获取短信验证码
        void getPhoneCode(SmsCodeParamModel smsCodeParamModel);
    }
}
