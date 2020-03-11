package it.swiftelink.com.factory.presenter.videochat;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.evaluation.EvaluationParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationSaveParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationUpdateParmModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 *
 */
public class TRTCMainPresenter extends BasePresenter<TRTCMainContract.View> implements TRTCMainContract.Presenter {
    public TRTCMainPresenter(TRTCMainContract.View view) {
        super(view);
    }

    @Override
    public void endTRTC(String id, boolean isPassive) {
        Observable<BaseResModel> observable = NetWork.getRequest().endTRTC(id, isPassive);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.POST_ENDTRTC, e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {
                if (baseResModel.isSuccess()) {
                    mView.endTRTCSuccess();
                } else {
                    mView.showError(Common.UrlApi.POST_ENDTRTC, baseResModel.getMessage());
                }

            }
        });

    }


//    @Override
//    public void getEvaluationListSuccess( EvaluationParmModel parmModel) {
//        start();
//        Observable<EvaluationResModel> observable = NetWork.getRequest().getEvaluationList( parmModel);
//        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<EvaluationResModel>() {
//            @Override
//            public void onError(ApiException e) {
//                Log.i("evaluationListSuccess", e.getMessage());
//            }
//
//            @Override
//            public void onSuccess(EvaluationResModel b) {
//
//                if (b.isSuccess()) {
//                    mView.getEvaluationListSuccess(b);
//                } else {
//                    mView.showError(Common.UrlApi.GET_EVALUATIONLISTSUCCESS, b.getMessage());
//                }
//            }
//        });
//    }
//
//    @Override
//    public void evaluationSave(EvaluationSaveParmModel saveParmModel) {
//        start();
//        Observable<BaseResModel> observable = NetWork.getRequest().evaluationSave(saveParmModel);
//        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
//            @Override
//            public void onError(ApiException e) {
//                Log.i("evaluationUpdate", e.getMessage());
//            }
//
//            @Override
//            public void onSuccess(BaseResModel baseResModel) {
//
//                if (baseResModel.isSuccess()) {
//                    mView.evaluationSave();
//                } else {
//                    mView.showError(Common.UrlApi.POST_EVALUATIONUPDATE, baseResModel.getMessage());
//                }
//            }
//        });
//    }

}
