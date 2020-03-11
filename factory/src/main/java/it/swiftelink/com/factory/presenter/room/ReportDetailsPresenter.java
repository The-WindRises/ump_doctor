package it.swiftelink.com.factory.presenter.room;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.room.ClinicinfoResModel;
import it.swiftelink.com.factory.model.room.InquiryReportResModel;
import it.swiftelink.com.factory.model.room.RecipeInfoResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/29 14:34
 * @一句话描述 报告详情
 */

public class ReportDetailsPresenter extends
        BasePresenter<ReportDetailsContract.View> implements ReportDetailsContract.Presenter {
    public ReportDetailsPresenter(ReportDetailsContract.View view) {
        super(view);
    }

    @Override
    public void getInquiryReportInfo(String id) {

        start();
        Observable<InquiryReportResModel> observable = NetWork.getRequest().getInquiryReportInfo(id);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<InquiryReportResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_INQUIRYREPORTINFO_CODE, e.getMessage());

            }

            @Override
            public void onSuccess(InquiryReportResModel reportResModel) {
                if (reportResModel.isSuccess()) {
                    mView.getInquiryReportInfo(reportResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_INQUIRYREPORTINFO_CODE, reportResModel.getMessage());
                }
            }
        });

    }

    @Override
    public void getRecipeInfo(String id) {
        start();
        Observable<RecipeInfoResModel> observable = NetWork.getRequest().getRecipeInfo(id);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<RecipeInfoResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_RECIPEINFO_CODE, e.getMessage());

            }

            @Override
            public void onSuccess(RecipeInfoResModel reportResModel) {
                if (reportResModel.isSuccess()) {
                    mView.getRecipeInfo(reportResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_RECIPEINFO_CODE, reportResModel.getMessage());
                }
            }
        });

    }
}
