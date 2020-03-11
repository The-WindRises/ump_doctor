package it.swiftelink.com.factory.presenter.login;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.login.ForgetPsdParaModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;

/**
 * @作者 Arvin
 * @时间 2019/7/26 9:42
 * @一句话描述 忘记密码契约
 */

public class ForgetPsdContract extends BaseContract {

    public interface View extends BaseContract.View<ForgetPsdContract.Presenter> {
        //忘记密码
        void forgetPsdSuccess();

        //获取短信验证码成功
        void getPhoneCodeResModelSuccess();

    }

    public interface Presenter extends BaseContract.Presenter {
        //忘记密码
        void forgetPsd(ForgetPsdParaModel forgetPsdParaModel);

        //获取短信验证码
        void getPhoneCode(SmsCodeParamModel smsCodeParamModel);
    }
}
