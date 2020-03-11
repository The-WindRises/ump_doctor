package it.swiftelink.com.common.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.webkit.WebSettings;

import java.text.SimpleDateFormat;
import java.util.UUID;

import it.swiftelink.com.common.app.Application;

public class Utils {
    public static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(Application.getInstance());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


//    public static String getMacAddress() {
//        String macAddress = null;
//        WifiManager wifiManager =
//                (WifiManager) Application.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());
//
//        if (!wifiManager.isWifiEnabled()) {
//            //必须先打开，才能获取到MAC地址
//            wifiManager.setWifiEnabled(true);
//            wifiManager.setWifiEnabled(false);
//        }
//        if (null != info) {
//            macAddress = info.getMacAddress();
//        }
//        return macAddress + System.currentTimeMillis();
//    }


    public static String getClientId() {

        String uuid = UUID.randomUUID().toString();

        String dateToString = DateTimeUtils.getCurrentDateStr();

        return "ANDROID_"+dateToString+"_"+uuid;

    }

    public static String getVersionName() {
        try {
            return "V " + Application.getInstance().getPackageManager().getPackageInfo(Application.getInstance().getPackageName(), 0).versionName + Constants.ENV_NAME;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getVersionCode() {
        try {
            return Application.getInstance().getPackageManager().getPackageInfo(Application.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
