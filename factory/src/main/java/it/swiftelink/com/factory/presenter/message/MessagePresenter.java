package it.swiftelink.com.factory.presenter.message;

import android.util.Log;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.factory.BasePresenter;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.message.MessageResModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.model.sms.SmsCodeResModel;
import it.swiftelink.com.factory.net.NetWork;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/7/26 9:50
 * @一句话描述 忘记密码
 */
public class MessagePresenter extends BasePresenter<MessageContract.View> implements MessageContract.Presenter {
    public MessagePresenter(MessageContract.View view) {
        super(view);
    }

    @Override
    public void getMessageList(int currPage, int pageSize) {
        Observable<MessageResModel> observable = NetWork.getRequest().getMessageList(currPage, pageSize);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<MessageResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.e("getMessageList", e.getMessage());
            }

            @Override
            public void onSuccess(MessageResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.getMessageListSuccess(resModel);
                } else {
                    mView.showError(Common.UrlApi.GET_MESSAGELIST, resModel.getMessage());
                }
            }
        });


    }

    @Override
    public void readMessage(String id) {
        start();
        Observable<BaseResModel> observable = NetWork.getRequest().readMessage(id);
        NetWork.getInstance().netRequest(observable, new NetWork.NetCallback<BaseResModel>() {
            @Override
            public void onError(ApiException e) {
                Log.e("getMessageList", e.getMessage());
            }

            @Override
            public void onSuccess(BaseResModel resModel) {
                if (resModel.isSuccess()) {
                    mView.readMessageSuccess();
                } else {
                    mView.showError(Common.UrlApi.GET_MESSAGELIST, resModel.getMessage());
                }
            }
        });
    }
}
