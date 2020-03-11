package it.swiftelink.com.common.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.swiftelink.com.common.net.LanguageEvent;
import it.swiftelink.com.common.utils.BindEventBus;

/**
 * Created by Administrator on 2018/7/10.
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    private Unbinder mUnbinder;
    protected String language = "zh_CN";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
                EventBus.getDefault().register(this);
            }
            String language = Application.getSPUtils().getString("appLanguage");
            if (!TextUtils.isEmpty(language)) {
                this.language = language;
            }
            int layoutId = getLayoutId();
            //初始化跟布局，但不添加到container，当return的时候进行添加
            View rootView = inflater.inflate(layoutId, container, false);

            initWidget(rootView);
            mRootView = rootView;
        } else {
            if (mRootView.getParent() != null) {
                ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            }
        }


        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)//接收通知进行语言切换
    public void onLanguageEvent(LanguageEvent event) {

    }

    protected void initArgs() {

    }

    /**
     * 获取布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(View view) {
        mUnbinder = ButterKnife.bind(this, view);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    protected boolean onBackPressd() {
        return false;
    }


    protected void toActivity(Bundle bundle, Class cls) {
        Bundle bundle1 = new Bundle();
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle1);
        }
        startActivity(intent);
    }

    protected void toActivity(Class cls) {
        Bundle bundle1 = new Bundle();
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
