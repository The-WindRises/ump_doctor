package it.swiftelink.com.factory.presenter.auth;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.auth.DepartmentResModel;

public class DepartmentContract extends BaseContract {


    public interface View extends BaseContract.View<DepartmentContract.Presenter> {

        //科室数据
        void getDepartmentListSuccess(DepartmentResModel departmentResModel);
    }

    public interface Presenter extends BaseContract.Presenter {

        void getDepartmentList(String language);
    }
}
