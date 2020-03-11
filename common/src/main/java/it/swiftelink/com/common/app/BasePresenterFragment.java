package it.swiftelink.com.common.app;

import android.view.View;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.common.widget.stateview.StateView;


/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/2  15:43
 */

public abstract class BasePresenterFragment<T extends BaseContract.Presenter> extends BaseFragment
        implements BaseContract.View<T> {

    protected T mPresenter;
    protected StateView mStateView;

    protected View loadingView;



    @Override
    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }

    protected T getPresenter() {
        return mPresenter;
    }

    //初始化presenter
    protected abstract T initPresenter();


    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        mStateView = getStateView();
    }

    @Override
    protected void initData() {
        super.initData();
        initPresenter();
    }

    /**
     * 设置空布局
     */
    protected abstract StateView getStateView();


    @Override
    public void showLoading() {
        if (mStateView != null) {
            loadingView = mStateView.showLoading();
        }
    }



    @Override
    public void showRetry(final int type) {
        if (mStateView != null) {
            mStateView.setBackState(Common.NetState.STATE_RETRY);
            loadingView = mStateView.showRetry();

            mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                @Override
                public void onRetryClick() {
                    retry(type);
                }
            });
        }
    }

    @Override
    public void showContent() {
        if (mStateView != null) {
            mStateView.showContent();
            mStateView.setBackState(Common.NetState.STATE_CONTENT);
        }
    }

    @Override
    public void showEmpty() {
        if (mStateView != null) {
            mStateView.showEmpty();
            mStateView.setBackState(Common.NetState.STATE_EMPTY);
        }
    }



}
