package it.swiftelink.com.factory.presenter.videochat;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

public class VideoChatPresenter extends BasePresenter<VideoChatContract.View> implements VideoChatContract.Presenter {
    public VideoChatPresenter(VideoChatContract.View view) {
        super(view);
    }

    @Override
    public void getTrtcConfig(String uuid, String userId) {
        start();

        Observable<TrtcConfigResModel> observable = NetWork.getRequest().getTrtcConfig(uuid, userId);

        NetWork.getInstance().baseNetRequest(observable, new NetWork.NetCallback<TrtcConfigResModel>() {
            @Override
            public void onError(ApiException e) {
                mView.showError(Common.UrlApi.GET_TRTC_CONFIG, e.getMessage());
            }

            @Override
            public void onSuccess(TrtcConfigResModel trtcConfigResModel) {

                if (trtcConfigResModel.getUsers().size() > 0) {
                    mView.getTrtcConfigSuccess(trtcConfigResModel);
                } else {
                    mView.showError(Common.UrlApi.GET_TRTC_CONFIG, trtcConfigResModel.getErrorMessage());
                }
            }
        });
    }
}
