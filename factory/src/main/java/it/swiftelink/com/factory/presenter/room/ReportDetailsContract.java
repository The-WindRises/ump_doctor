package it.swiftelink.com.factory.presenter.room;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.room.InquiryReportResModel;
import it.swiftelink.com.factory.model.room.RecipeInfoResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/29 14:28
 * @一句话描述 报告详情
 */

public class ReportDetailsContract extends BaseContract {

    public interface View extends BaseContract.View<ReportDetailsContract.Presenter> {
        void getInquiryReportInfo(InquiryReportResModel reportResModel);

        void getRecipeInfo(RecipeInfoResModel recipeInfoResModel);
    }

    public interface Presenter extends BaseContract.Presenter {
        /**
         * 处方
         *
         * @param id
         */
        void getInquiryReportInfo(String id);

        /**
         * 报告详情
         *
         * @param id
         */

        void getRecipeInfo(String id);
    }
}
