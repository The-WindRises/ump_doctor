package it.swiftelink.com.factory.presenter.user;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.user.AgrreementResModel;
import it.swiftelink.com.factory.model.user.UseInfoResModel;

public class AgreementContract extends BaseContract {

    public interface View extends BaseContract.View<AgreementContract.Presenter> {

        void getAgrreementSuess(AgrreementResModel agrreementResModel);
    }

    public interface Presenter extends BaseContract.Presenter {

        void getAgrreement(String language, String type);
    }
}
