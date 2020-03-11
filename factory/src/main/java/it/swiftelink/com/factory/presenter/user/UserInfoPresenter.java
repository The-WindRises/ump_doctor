package it.swiftelink.com.factory.presenter.user;

import android.util.Log;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.factory.model.user.UseInfoResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

public class UserInfoPresenter extends BasePresenter<UserInfoContract.View> implements UserInfoContract.Presenter {
    public UserInfoPresenter(UserInfoContract.View view) {
        super(view);
    }

    @Override
    public void getUserInfo() {
        start();
        Observable<UseInfoResModel> observable = NetWork.getRequest().getUserInfo();
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<UseInfoResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("getUserInfo", e.getMessage());
            }

            @Override
            public void onSuccess(UseInfoResModel useInfoResModel) {
                if (useInfoResModel != null && useInfoResModel.isSuccess()) {
                    mView.getUserInfoSuess(useInfoResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_GETUSERINFO, useInfoResModel.getMessage());
                }
            }
        });

    }

    @Override
    public void checkIsEdit() {

        start();
        Observable<BaseResModel> observable = NetWork.getRequest().checkIsEdit();
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("getUserInfo", e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {
                if (baseResModel != null && baseResModel.getCode() == 200) {
                    mView.checkIsEdit(baseResModel);
                } else {
                    if (null != baseResModel) {
                        mView.showError(Common.UrlApi.GET_CHECKISEDIT, baseResModel.getMessage());
                    }
                }
            }
        });
    }


}
