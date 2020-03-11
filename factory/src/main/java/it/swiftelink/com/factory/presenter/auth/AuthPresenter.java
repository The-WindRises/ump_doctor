package it.swiftelink.com.factory.presenter.auth;

import android.os.Handler;
import android.util.Log;

import net.qiujuer.genius.kit.handler.Run;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.auth.AuthInfoPramModel;
import it.swiftelink.com.factory.model.auth.AuthParmModel;
import it.swiftelink.com.factory.model.auth.UploadResModel;
import it.swiftelink.com.factory.model.user.EditUserParamModel;
import it.swiftelink.com.factory.net.NetWork;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class AuthPresenter extends BasePresenter<AuthContract.View> implements AuthContract.Presenter {

    public AuthPresenter(AuthContract.View view) {
        super(view);
    }
    Handler handler=new Handler();

    @Override
    public void uploadImage(String path) {

        start();
        File file = new File(path);
        if (file != null) {

            ProgressRequestBody requestFile=new ProgressRequestBody(file,"multipart/form-data", new ProgressRequestBody.UploadCallbacks() {
                @Override
                public void onProgressUpdate(final int percentage, long id) {

                    mView.uploadImageProgressDialog(percentage);

                }


            });

//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("files[]", file.getName(), requestFile);
            Observable<UploadResModel> observable = NetWork.getRequest().uploadImage(filePart);
            NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<UploadResModel>() {
                @Override
                public void onError(ApiException e) {

                    Log.e("uploadImage", e.getMessage());
                }

                @Override
                public void onSuccess(UploadResModel uploadResModel) {
                    if (uploadResModel != null) {
                        mView.uploadSuess(uploadResModel);
                    } else {
                        mView.showError(Common.UrlApi.GET_UPLOAD, "上传图片失败");
                    }
                }
            });
        }
    }

    @Override
    public void authInfo(AuthInfoPramModel authInfoPramModel) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().authInfo(authInfoPramModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.POST_AUTHINFO, e.getMessage());
                Log.i("authInfo", e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {
                if (baseResModel.isSuccess()) {
                    mView.authInfoSuess();
                } else {
                    mView.showError(Common.UrlApi.POST_AUTHINFO, baseResModel.getMessage());
                }
            }

        });

    }

    @Override
    public void auth(AuthParmModel authParmModel) {

        start();
        Observable<BaseResModel> observable = NetWork.getRequest().auth(authParmModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("auth", e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {
                if (baseResModel.isSuccess()) {
                    mView.authSuess();
                } else {
                    mView.showError(Common.UrlApi.POST_AUTH, baseResModel.getMessage());
                }
            }
        });
    }

    @Override
    public void editUserInfo( EditUserParamModel editUserParamModel) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().editUserInfo(editUserParamModel);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.i("auth", e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel baseResModel) {
                if (baseResModel.isSuccess()) {
                    mView.editUserInfoSuess();
                } else {
                    mView.showError(Common.UrlApi.POST_AUTH, baseResModel.getMessage());
                }
            }
        });
    }
}
