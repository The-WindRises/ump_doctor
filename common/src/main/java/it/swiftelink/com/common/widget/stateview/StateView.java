package it.swiftelink.com.common.widget.stateview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import it.swiftelink.com.common.R;

/**
 * StateView is an invisible, zero-sized View that can be used
 * to lazily inflate loadingView/emptyView/retryView at runtime.
 *
 * @author Nukc
 * https://github.com/nukc
 */
public class StateView extends View {

    @IntDef({EMPTY, RETRY, LOADING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    public static final int EMPTY = 0x00000000;
    public static final int RETRY = 0x00000001;
    public static final int LOADING = 0x00000002;

    private static int backState;

    private int mEmptyResource;
    private int mRetryResource;
    private int mLoadingResource;

    private View mEmptyView;
    private View mRetryView;
    private View mLoadingView;


    private RelativeLayout.LayoutParams mLayoutParamsRelative;
    private ConstraintLayout.LayoutParams mLayoutParamsConstrain;
    private OnRetryClickListener mRetryClickListener;

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateView);
        mEmptyResource = a.getResourceId(R.styleable.StateView_emptyResource, 0);
        mRetryResource = a.getResourceId(R.styleable.StateView_retryResource, 0);
        mLoadingResource = a.getResourceId(R.styleable.StateView_loadingResource, 0);
        a.recycle();

        if (mEmptyResource == 0) {
            mEmptyResource = R.layout.layout_empty;
        }
        if (mRetryResource == 0) {
            mRetryResource = R.layout.layout_retry;
        }
        if (mLoadingResource == 0) {
            mLoadingResource = R.layout.layout_loading;
        }

        if (attrs == null) {
            mLayoutParamsRelative = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mLayoutParamsConstrain = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            mLayoutParamsRelative = new RelativeLayout.LayoutParams(context, attrs);
            mLayoutParamsConstrain = new ConstraintLayout.LayoutParams(context, attrs);
        }

        setVisibility(GONE);
        setWillNotDraw(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
    }

    @Override
    public void setVisibility(int visibility) {
        setVisibility(mEmptyView, visibility);
        setVisibility(mRetryView, visibility);
        setVisibility(mLoadingView, visibility);
    }

    private void setVisibility(View view, int visibility) {
        if (view != null && visibility != view.getVisibility()) {
            view.setVisibility(visibility);
        }
    }

    public void showContent() {
        setVisibility(GONE);
    }

    public View showEmpty() {
        if (mEmptyView == null) {
            mEmptyView = inflate(mEmptyResource, EMPTY);
        }

        showView(mEmptyView);
        return mEmptyView;
    }

    public View showRetry() {
        if (mRetryView == null) {
            mRetryView = inflate(mRetryResource, RETRY);
            mRetryView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRetryClickListener != null) {
                        showLoading();
                        mRetryView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mRetryClickListener.onRetryClick();
                            }
                        }, 400);
                    }
                }
            });
        }

        showView(mRetryView);
        return mRetryView;
    }

    public View showLoading() {
        if (mLoadingView == null) {
            mLoadingView = inflate(mLoadingResource, LOADING);
        }

        showView(mLoadingView);
        return mLoadingView;
    }

    /**
     * show the state view
     */
    private void showView(View view) {
        setVisibility(view, VISIBLE);
        hideViews(view);
    }

    /**
     * hide other views after show view
     */
    private void hideViews(View showView) {
        if (mEmptyView == showView) {
            setVisibility(mLoadingView, GONE);
            setVisibility(mRetryView, GONE);
        } else if (mLoadingView == showView) {
            setVisibility(mEmptyView, GONE);
            setVisibility(mRetryView, GONE);
        } else {
            setVisibility(mEmptyView, GONE);
            setVisibility(mLoadingView, GONE);
        }
    }

    private View inflate(@LayoutRes int layoutResource, @ViewType int viewType) {
        final ViewParent viewParent = getParent();

        if (viewParent != null && viewParent instanceof ViewGroup) {
            if (layoutResource != 0) {
                final ViewGroup parent = (ViewGroup) viewParent;
                final LayoutInflater factory = LayoutInflater.from(getContext());
                final View view = factory.inflate(layoutResource, parent, false);
                final int index = parent.indexOfChild(this);
                // 防止还能触摸底下的 View
                view.setClickable(true);
                // 先不显示
                view.setVisibility(GONE);

                final ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams != null) {
                    if (parent instanceof RelativeLayout) {
                        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutParams;
                        mLayoutParamsRelative.setMargins(lp.leftMargin, lp.topMargin,
                                lp.rightMargin, lp.bottomMargin);

                        parent.addView(view, index, mLayoutParamsRelative);
                    } else if (parent instanceof ConstraintLayout) {
                        parent.addView(view, index, mLayoutParamsConstrain);
                    } else {
                        parent.addView(view, index, layoutParams);
                    }
                } else {
                    parent.addView(view, index);
                }

                if (mLoadingView != null && mRetryView != null && mEmptyView != null) {
                    parent.removeViewInLayout(this);
                }
                return view;
            } else {
                throw new IllegalArgumentException("StateView must have a valid layoutResource");
            }
        } else {
            throw new IllegalStateException("StateView must have a non-null ViewGroup viewParent");
        }
    }

    /**
     * 设置 topMargin, 当有 actionbar/toolbar 的时候
     */
    public void setTopMargin() {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        layoutParams.topMargin = getActionBarHeight();
    }

    /**
     * @return actionBarSize
     */
    public int getActionBarHeight() {
        int height = 0;
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return height;
    }

    /**
     * 设置 emptyView 的自定义 Layout
     *
     * @param emptyResource emptyView 的 layoutResource
     */
    public void setEmptyResource(@LayoutRes int emptyResource) {
        this.mEmptyResource = emptyResource;
    }

    /**
     * 设置 retryView 的自定义 Layout
     *
     * @param retryResource retryView 的 layoutResource
     */
    public void setRetryResource(@LayoutRes int retryResource) {
        this.mRetryResource = retryResource;
    }

    /**
     * 设置 loadingView 的自定义 Layout
     *
     * @param loadingResource loadingView 的 layoutResource
     */
    public void setLoadingResource(@LayoutRes int loadingResource) {
        mLoadingResource = loadingResource;
    }

    /**
     * 设置当loading时返回显示的状态
     * @param state
     */
    public void setBackState(int state){
        this.backState = state;
    }

    /**
     * 设置当loading时返回显示的状态
     */
    public int getBackState(){
        return backState;
    }


    /**
     * 监听重试
     *
     * @param listener {@link OnRetryClickListener}
     */
    public void setOnRetryClickListener(OnRetryClickListener listener) {
        this.mRetryClickListener = listener;
    }

    /**
     * Listener used to receive a notification after the RetryView is clicked.
     */
    public interface OnRetryClickListener {
        void onRetryClick();
    }

}
