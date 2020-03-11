package it.swiftelink.com.factory.presenter.scheduling;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.scheduling.ApplyResModel;
import it.swiftelink.com.factory.model.scheduling.DaySchedulingdResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/25 17:35
 * @一句话描述 排版相关的契约类
 */

public class SchedulingApplyContract extends BaseContract {

    public interface View extends BaseContract.View<SchedulingApplyContract.Presenter> {

        /**
         * 申请调班
         */
        void toApplySuccess();


        /**
         * 获取当前可调班数据
         */
        void getToApplyListSuccess(ApplyResModel applyResModel);

    }

    public interface Presenter extends BaseContract.Presenter {

        /**
         * 申请调班
         */
        void toApplySuccess(String originalShiftId, String applyShiftId, String type, String applyReason);

        /**
         * 获取当前可调班数据
         */
        void getToApplyList();
    }
}
