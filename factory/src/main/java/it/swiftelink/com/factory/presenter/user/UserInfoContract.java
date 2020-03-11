package it.swiftelink.com.factory.presenter.user;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.factory.model.user.UseInfoResModel;

public class UserInfoContract extends BaseContract {

    public interface View extends BaseContract.View<UserInfoContract.Presenter> {
        void getUserInfoSuess(UseInfoResModel useInfoResModel);

        void checkIsEdit(BaseResModel baseResModel);
    }

    public interface Presenter extends BaseContract.Presenter {
        void getUserInfo();

        void checkIsEdit();
    }


}
