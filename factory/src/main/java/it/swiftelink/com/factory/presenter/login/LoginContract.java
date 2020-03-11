package it.swiftelink.com.factory.presenter.login;


import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.login.LoginResModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;

/**
 * @作者 Arvin
 * @时间 2019/7/24 16:08
 * @一句话描述 登录契约类
 */


public class LoginContract extends BaseContract {

    public interface View extends BaseContract.View<Presenter> {
        //登录成功
        void loginSuccess(LoginResModel loginResModel);

        //获取短信验证码成功
        void getPhoneCodeResModelSuccess();
    }

    public interface Presenter extends BaseContract.Presenter {
        //发起一个登录
        void login(String userName, String password);

        //短息验证码登录
        void smsLogin(String phoneNum, String smsCode);


        //获取短信验证码
        void getPhoneCode(SmsCodeParamModel smsCodeParamModel);
    }
}
