package it.swiftelink.com.factory.presenter.income;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.income.BackCardParamModel;

public class AddBankContract extends BaseContract {

    public interface View extends BaseContract.View<AddBankContract.Presenter> {
        /**
         * 绑定银行卡
         */
        void bindBackCardSuccess();
    }
    public interface Presenter extends BaseContract.Presenter {
        /**
         * 绑定银行卡
         *
         * @param
         */
        void bindBackCard(String name, String cardNo, String bank, String mobile);
    }
}
