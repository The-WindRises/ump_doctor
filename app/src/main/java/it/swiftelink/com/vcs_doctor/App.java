package it.swiftelink.com.vcs_doctor;


import android.content.Intent;
import android.os.Build;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import it.swiftelink.com.common.app.Application;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.utils.ConfigHelper;
import it.swiftelink.com.common.utils.MultiLanguageUtil;
import it.swiftelink.com.factory.net.NetWork;
import it.swiftelink.com.vcs_doctor.service.MqttService;
import it.swiftelink.com.vcs_doctor.util.UserLogin;

public class App extends Application {

    public static Map<String, String> order = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        final App _this = this;
//        ConfigHelper.getInstance().loadConfig();
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        Bugly.init(getApplicationContext(), "5a34334db5", true);
        CrashReport.initCrashReport(getApplicationContext(), "5a34334db5", false);
        NetWork.getInstance().init();
        MultiLanguageUtil.init(_this);
        closeAndroidPDialog();
        // 配置一些Config，按需配置
        TUIKitConfigs configs = TUIKit.getConfigs();
        configs.setSdkConfig(new TIMSdkConfig(Common.SDKAPPID));
        configs.setCustomFaceConfig(new CustomFaceConfig());
        configs.setGeneralConfig(new GeneralConfig());
        TUIKit.init(_this, Common.SDKAPPID, configs);
        //闪退重新启动服务
        if (UserLogin.isLogin()) {
            startService();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (order != null) {
            order.clear();
        }
    }

    private void startService() {
        Intent intent = new Intent(this, MqttService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }


    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
