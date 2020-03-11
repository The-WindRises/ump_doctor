package it.swiftelink.com.common.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.swiftelink.com.common.event.TokenInvalidEvent;
import it.swiftelink.com.common.flyn.Eyes;
import it.swiftelink.com.common.net.LanguageEvent;
import it.swiftelink.com.common.utils.AndroidBug5497Workaround;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.LanguageType;
import it.swiftelink.com.common.utils.LocaleManager;
import it.swiftelink.com.common.utils.MultiLanguageUtil;
import it.swiftelink.com.common.utils.OnChangeLanguageEvent;

/**
 * Created by Administrator on 2018/7/10.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String language;
    private Resources resources;

    public void setMaxAspect() {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo == null) {
            throw new IllegalArgumentException(" get application info = null, has no meta data! ");
        }
        applicationInfo.metaData.putString("android.max_aspect", "2.5");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().register(this);
        }
        initLanguage();
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            setContentView(getLayoutId());
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    private void initLanguage() {
        int languageType = Application.getSPUtils().getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
        switch (languageType) {
            case LanguageType.LANGUAGE_EN:
                //英文
                language = Constants.EN_US;
                break;
            case LanguageType.LANGUAGE_CHINESE_SIMPLIFIED:
                //简体
                language = Constants.ZH_CN;
                break;
            case LanguageType
                    .LANGUAGE_CHINESE_TRADITIONAL:
                //繁体
                language = Constants.ZH_TW;
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//接收通知进行语言切换
    public void onTokentInvalidEvent(TokenInvalidEvent tokenInvalidEvent) {
        Class clazz = null;
        try {
            clazz = Class.forName("it.swiftelink.com.vcs_doctor.ui.activity.login.SmsLoginActivity");
            toActivity(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void out() {
        finish();
    }

    protected void initWindows() {

    }

    /**
     * 初始化相关参数
     *
     * @return
     */
    protected Boolean initArgs(Bundle bundle) {

        return true;
    }

    /**
     * 获取xml 的id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        Eyes.translucentStatusBar(this, false);
        AndroidBug5497Workaround.assistActivity(this);
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 当点击导航栏上面的返回键
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {

        out();
        return super.onSupportNavigateUp();
    }

    /**
     * 当返回键被点击
     */
    @Override
    public void onBackPressed() {
        @SuppressLint("RestrictedApi") List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment ft : fragments) {
                if (ft instanceof BaseFragment) {
                    if (((BaseFragment) ft).onBackPressd()) {
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        out();
    }


    protected void toActivity(Bundle bundle, Class cls) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void toActivity(Class cls) {
        Bundle bundle1 = new Bundle();
        Intent intent = new Intent(this, cls);
        startActivity(intent);

    }

    protected void toStartActivityForResult(Class cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
