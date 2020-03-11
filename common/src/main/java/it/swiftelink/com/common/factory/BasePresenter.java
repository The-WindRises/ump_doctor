package it.swiftelink.com.common.factory;

/**
 * Created by Administrator on 2018/7/11.
 */

public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {

    protected T mView;

    public BasePresenter(T view) {
        setView(view);
    }

    private void setView(T view) {
        mView = view;
        mView.setPresenter(this);
    }

    /**
     * 获取view的方法，不能被继承
     *
     * @return
     */
    protected final T getView() {
        return mView;
    }

    @Override
    public void start() {
        T view = mView;
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void destroy() {
        T view = mView;
        if (view != null) {
            view.setPresenter(null);
        }
    }
}
