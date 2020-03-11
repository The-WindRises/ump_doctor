package it.swiftelink.com.factory.presenter.income;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.home.BannerResModel;
import it.swiftelink.com.factory.model.home.OrdersRankingResModel;
import it.swiftelink.com.factory.presenter.home.HomeContract;

/**
 * @作者 Arvin
 * @时间 2019/7/25 14:44
 * @一句话描述 零钱 的契约类
 */
public class SmallChangeContract extends BaseContract {

    public interface View extends BaseContract.View<SmallChangeContract.Presenter> {
        /**
         * 获取余额
         *
         * @param balance
         */
        void getBalance(String balance);
    }

    public interface Presenter extends BaseContract.Presenter {
        /**
         * 获取余额
         */
        void getBalance();
    }
}
