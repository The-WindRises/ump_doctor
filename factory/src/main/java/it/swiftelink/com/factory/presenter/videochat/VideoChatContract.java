package it.swiftelink.com.factory.presenter.videochat;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;

public class VideoChatContract {

    public interface View extends BaseContract.View<VideoChatContract.Presenter> {

        void getTrtcConfigSuccess(TrtcConfigResModel model);


    }

    public interface Presenter extends BaseContract.Presenter {
        void getTrtcConfig(String uuid, String userId);

    }
}
