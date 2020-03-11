package it.swiftelink.com.common.utils;

import android.content.pm.PackageManager;
import android.util.Log;

import java.io.StringReader;
import java.util.Properties;

import it.swiftelink.com.common.app.Application;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConfigHelper {

    private static ConfigHelper INSTANCE;
    private static boolean loadOk = false;

    private final static String CONFIG_URL = "http://www.umpmedical.com:8888/cloud-cms-vcs.properties";

    public static ConfigHelper getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new ConfigHelper();
        }
        return INSTANCE;
    }

    public void loadConfig(){
        new Thread(){
            public void run(){
                try {
                    Properties properties = getConfig();
                    if(!properties.isEmpty()) {
                        String versionName = Application.getInstance().getPackageManager().getPackageInfo(Application.getInstance().getPackageName(), 0).versionName;
                        Constants.API_BASE_URL = properties.getProperty("vcs.doctor." + versionName + ".api");
                        Constants.ROOMTOPIC = properties.getProperty("vcs.doctor." + versionName + ".topic");
                        Constants.SENDTOPIC = properties.getProperty("vcs.doctor." + versionName + ".topic");
                        Constants.ENV_NAME = properties.getProperty("vcs.doctor." + versionName + ".env","");
                    }
                } catch (Exception e) {
                    Log.e("ConfigHelper","",e);
                } finally {
                    loadOk = true;
                }
            }
        }.start();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (loadOk){
                break;
            }
        }
    }

    public Properties getConfig(){
        Properties properties = new Properties();
        try{
            Class.forName("it.swiftelink.com.common.utils.Constants");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(CONFIG_URL)//请求接口。如果需要传参拼接到接口后面。
                    .build();//创建Request 对象
            Response response = null;
            response = client.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()) {
                String body = response.body().string();
                Log.d("ConfigHelper","response.code()=="+response.code());
                Log.d("ConfigHelper","response.message()=="+response.message());
                Log.d("ConfigHelper","res=="+body);
                properties.load(new StringReader(body));
                Log.d("ConfigHelper","properties=="+properties);
            }else{
                throw new Exception(response.message());
            }
        }catch (Exception e){
            Log.e("ConfigHelper","获取远程配置失败",e);
        }
        return properties;
    }

    public interface Callback{
        void run();
    }
}
