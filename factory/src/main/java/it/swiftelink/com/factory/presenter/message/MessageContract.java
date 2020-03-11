package it.swiftelink.com.factory.presenter.message;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.message.MessageResModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;

/**
 * @作者 Arvin
 * @时间 2019/7/26 9:42
 * @一句话描述 忘记密码契约
 */

public class MessageContract extends BaseContract {

    public interface View extends BaseContract.View<MessageContract.Presenter> {

        void getMessageListSuccess(MessageResModel resModel);

        void readMessageSuccess();
    }

    public interface Presenter extends BaseContract.Presenter {

        void getMessageList(int currPage, int pageSize);

        void readMessage(String id);
    }
}
