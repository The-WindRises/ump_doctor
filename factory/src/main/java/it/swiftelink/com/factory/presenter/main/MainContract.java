package it.swiftelink.com.factory.presenter.main;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.login.LoginResModel;
import it.swiftelink.com.factory.model.main.VersionResModel;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;
import it.swiftelink.com.factory.presenter.message.MessageContract;

public class MainContract extends BaseContract {

    public interface View extends BaseContract.View<MainContract.Presenter> {

        //trtc成功
        void getTrtcConfigSuccess(TrtcConfigResModel model);

        void checkVersion(VersionResModel versionResModel);
        void loginTestSuccess(LoginResModel loginResModel);
    }

    public interface Presenter extends BaseContract.Presenter {

        void checkVersion(String device, String type);

        /**
         * 获取trtc 相关
         */
        void getTrtcConfig(String userId);

        void loginTest();

    }
}
