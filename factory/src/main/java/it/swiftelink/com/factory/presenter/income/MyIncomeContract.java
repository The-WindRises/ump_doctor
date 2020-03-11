package it.swiftelink.com.factory.presenter.income;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.income.BackResModel;
import it.swiftelink.com.factory.model.income.MyIncomeRecordResModel;
import it.swiftelink.com.factory.model.income.MyIncomeResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/26 15:49
 * @一句话描述 我的收入契约类
 */


public class MyIncomeContract extends BaseContract {

    public interface View extends BaseContract.View<MyIncomeContract.Presenter> {
        /**
         * 获取我的收入
         *
         * @param myIncomeResModel
         */
        void getMyIncome(MyIncomeResModel myIncomeResModel);

        /**
         * 按照月份获取收入
         * @param myIncomeRecordResModel
         */
        void getMyIncomeRecord(MyIncomeRecordResModel myIncomeRecordResModel);

    }

    public interface Presenter extends BaseContract.Presenter {
        /**
         * 获取我的收入
         */

        void getMyIncome();
        /**
         * 按照月份获取收入
         * @param date
         */
        void getMyIncomeRecord(String date);
    }

}
