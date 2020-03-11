package it.swiftelink.com.factory.presenter.videochat;

import it.swiftelink.com.common.factory.BaseContract;

public class MessageChatContract {

    public interface View extends BaseContract.View<Presenter> {

        void endVideoChatSuccess();
    }

    public interface Presenter extends BaseContract.Presenter {

        void endVideoChat(String diagnosisId, boolean isPassive);


    }
}
