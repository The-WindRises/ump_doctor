package it.swiftelink.com.factory.presenter.scheduling;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.scheduling.DaySchedulingdResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingdRecordsResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/25 21:18
 * @一句话描述 调班记录
 */

public class SchedRecordsContract extends BaseContract {

    public interface View extends BaseContract.View<SchedRecordsContract.Presenter> {
        /**
         * 调班记录
         *
         * @param resModel
         */
        void schedulingdRecords(SchedulingdRecordsResModel resModel);
    }

    public interface Presenter extends BaseContract.Presenter {
        /**
         * 调班记录
         */
        void chedulingdRecords(int currPage, int pageSize);

    }
}
