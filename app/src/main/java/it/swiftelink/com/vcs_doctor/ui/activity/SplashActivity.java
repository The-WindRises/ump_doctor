package it.swiftelink.com.vcs_doctor.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import java.util.Locale;

import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.LanguageType;
import it.swiftelink.com.common.utils.MultiLanguageUtil;
import it.swiftelink.com.vcs_doctor.R;

public class SplashActivity extends BaseActivity {
    int languageType;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }

        String language = locale.getLanguage();
        String languageAndArea = locale.getLanguage() + "-" + locale.getCountry();
        switch (language){
            case "zh":
                if(languageAndArea.equals("zh-HK") || languageAndArea.equals("zh-TW") || locale.toString().equals("zh_CN_#Hant")){
                    languageType = LanguageType.LANGUAGE_CHINESE_TRADITIONAL;
                }else {
                    languageType = LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
                }
                break;
            default:
                languageType = LanguageType.LANGUAGE_EN;
                break;
        }
        MultiLanguageUtil.getInstance().updateLanguage(languageType);
        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    sleep(1500);//使程序休眠一秒
                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(it);
                    finish();//关闭当前活动
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }
}
