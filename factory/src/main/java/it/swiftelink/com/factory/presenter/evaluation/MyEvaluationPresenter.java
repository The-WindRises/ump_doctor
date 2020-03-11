package it.swiftelink.com.factory.presenter.evaluation;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.evaluation.EvaluationParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationUpdateParmModel;
import it.swiftelink.com.factory.model.evaluation.MyEvaluationResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/26 19:44
 * @一句话描述 我的评价Presenter
 */
public class MyEvaluationPresenter extends BasePresenter<MyEvaluationContract.View> implements MyEvaluationContract.Presenter {

    public MyEvaluationPresenter(MyEvaluationContract.View view) {
        super(view);
    }

    @Override
    public void evaluationList(int currentPage, int pageSize, String doctorStatus, String patientStatus) {
        start();
        Observable<MyEvaluationResModel> observable = NetWork.getRequest().evaluationList(currentPage, pageSize, doctorStatus, patientStatus, "");

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<MyEvaluationResModel>() {
            @Override
            public void onError(ApiException e) {

                mView.showError(Common.UrlApi.GET_MYEVALUATION_CODE, e.getMessage());
            }

            @Override
            public void onSuccess(MyEvaluationResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.evaluationList(resModel);
                } else {
                    mView.showError(Common.UrlApi.GET_MYEVALUATION_CODE, resModel.getMessage());
                }

            }


        });
    }

    @Override
    public void getEvaluationListSuccess( EvaluationParmModel parmModel) {
        start();
        Observable<EvaluationResModel> observable = NetWork.getRequest().getEvaluationList( parmModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<EvaluationResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("evaluationListSuccess", e.getMessage());
            }

            @Override
            public void onSuccess(EvaluationResModel b) {

                if (b.isSuccess()) {
                    mView.getEvaluationListSuccess(b);
                } else {
                    mView.showError(Common.UrlApi.GET_EVALUATIONLISTSUCCESS, b.getMessage());
                }
            }

        });
    }

    @Override
    public void evaluationUpdate(EvaluationUpdateParmModel updateParmModel) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().evaluationUpdate(updateParmModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("evaluationUpdate", e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {

                if (baseResModel.isSuccess()) {
                    mView.evaluationUpdate();
                } else {
                    mView.showError(Common.UrlApi.POST_EVALUATIONUPDATE, baseResModel.getMessage());
                }
            }


        });
    }


}
