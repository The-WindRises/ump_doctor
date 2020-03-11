package it.swiftelink.com.factory.presenter.auth;

import android.util.Log;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.auth.DepartmentResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

public class DepartmentPresenter extends BasePresenter<DepartmentContract.View> implements DepartmentContract.Presenter {

    public DepartmentPresenter(DepartmentContract.View view) {
        super(view);
    }

    @Override
    public void getDepartmentList(String language) {
        start();
        Observable<DepartmentResModel> observable = NetWork.getRequest().getDepartmentList(language);

        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<DepartmentResModel>() {
            @Override
            public void onError(ApiException e) {

                Log.i("getDepartmentList", e.getMessage());
            }

            @Override
            public void onSuccess(DepartmentResModel departmentResModel) {
                if (departmentResModel != null && departmentResModel.isSuccess()) {
                    mView.getDepartmentListSuccess(departmentResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_DEPARTMENTLIST, departmentResModel.getMessage());
                }
            }
        });

    }
}
