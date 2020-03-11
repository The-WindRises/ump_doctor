package it.swiftelink.com.factory.presenter.login;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.login.LoginResModel;
import it.swiftelink.com.factory.model.login.RegisterParamModel;
import it.swiftelink.com.factory.model.login.RegisterResModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.model.user.AgrreementResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/24 16:09
 * @一句话描述 注册契约类
 */


public class RegisterConract extends BaseContract {

    public interface View extends BaseContract.View<RegisterConract.Presenter> {
        //注册成功
        void registerSuccess(RegisterResModel registerResModel);

        //获取短信验证码成功
        void getPhoneCodeResModelSuccess();

        void getAgrreementSuess(AgrreementResModel agrreementResModel);

    }

    public interface Presenter extends BaseContract.Presenter {

        //获取短信验证码
        void getPhoneCode(SmsCodeParamModel smsCodeParamModel);

        //注册
        void register(RegisterParamModel registerParamModel);

        void getAgrreement(String language, String type);
    }
}
