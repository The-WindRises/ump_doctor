package it.swiftelink.com.factory.presenter.auth;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.auth.AuthInfoPramModel;
import it.swiftelink.com.factory.model.auth.AuthParmModel;
import it.swiftelink.com.factory.model.auth.UploadResModel;
import it.swiftelink.com.factory.model.user.EditUserParamModel;

public class AuthContract extends BaseContract {

    public interface View extends BaseContract.View<AuthContract.Presenter> {
        void uploadSuess(UploadResModel uploadResModel);

        void authInfoSuess();

        void authSuess();

        void  editUserInfoSuess();

        void uploadImageProgressDialog(int progress);
    }



    public interface Presenter extends BaseContract.Presenter {
        void uploadImage(String path);

        void authInfo(AuthInfoPramModel authInfoPramModel);

        void auth(AuthParmModel authParmModel);

        void editUserInfo(EditUserParamModel editUserParamModel);
    }
}
