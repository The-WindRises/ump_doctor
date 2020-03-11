package it.swiftelink.com.factory.presenter.income;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.income.BackCardParamModel;
import it.swiftelink.com.factory.model.income.BackResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/25 16:05
 * @一句话描述 银行卡列表
 */


public class MyBankCardContract extends BaseContract {

    public interface View extends BaseContract.View<MyBankCardContract.Presenter> {
        /**
         * 银行卡列表
         */
        void getBackList(BackResModel backResModel);
    }

    public interface Presenter extends BaseContract.Presenter {
        /**
         * 银行卡列表
         *
         * @param
         */
        void getBackList(int currPage, int pageSize);
    }
}
