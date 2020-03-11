package it.swiftelink.com.factory.presenter.scheduling;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.scheduling.DaySchedulingdResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingdRecordsResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/25 17:35
 * @一句话描述 排版相关的契约类
 */

public class SchedulingContract extends BaseContract {

    public interface View extends BaseContract.View<SchedulingContract.Presenter> {



        /**
         * 我的排班
         */
        void doctorschedulingList(SchedulingResModel resModel);

        /**
         * 获取每天的排版记录
         */
        void doctorschedulingdDayList(DaySchedulingdResModel resModel);


    }

    public interface Presenter extends BaseContract.Presenter {

        /**
         * 我的排班
         */
        void doctorschedulingList(String yearAndMouth);

        /**
         * 获取每天的排版记录
         */
        void doctorschedulingdDayList(String date);


    }
}
