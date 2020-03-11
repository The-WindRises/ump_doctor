package it.swiftelink.com.factory.presenter.my;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.home.BannerResModel;
import it.swiftelink.com.factory.model.home.OrdersRankingResModel;
import it.swiftelink.com.factory.model.home.StatisticsResModel;
import it.swiftelink.com.factory.model.message.MessageRemindResModel;
import it.swiftelink.com.factory.model.my.DoctorInforResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/24 20:41
 * @一句话描述 首页契约类
 */

public class MyContract extends BaseContract {

    public interface View extends BaseContract.View<Presenter> {

        void getDoctorInfoSuccess(DoctorInforResModel resModel);

        void diagnosisMonthinfo(StatisticsResModel resModel);

        void remindCount(MessageRemindResModel messageRemindResModel);

    }

    public interface Presenter extends BaseContract.Presenter {
        void getDoctorInfo(String language);
        void diagnosisMonthinfo(String doctorId);
        void remindCount();
    }

}
