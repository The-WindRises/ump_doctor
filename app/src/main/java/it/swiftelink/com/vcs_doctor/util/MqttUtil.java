package it.swiftelink.com.vcs_doctor.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

import it.swiftelink.com.common.utils.Utils;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.service.MqttMessageCallBack;

/**
 * @作者 Arvin
 * @时间 2019/10/15 16:50
 * @一句话描述 mqtt工具类
 */
public class MqttUtil {
    private String Tag = MqttUtil.class.getSimpleName();
    private String host = "tcp://mqtt.swiftelink.com:1883";
    private String userName = "admin";
    private String passWord = "public";
    private MqttAndroidClient client;
    private MqttConnectOptions mqttConnectOptions;
    private MqttMessageCallBack mqttMessageCallBack;

    private static final String TAG = "MqttUtil";

    private MqttUtil() {
    }

    private static class MqttUtilHoler {
        private static MqttUtil INSTANCE = new MqttUtil();
    }

    public static MqttUtil getInstance() {
        return MqttUtilHoler.INSTANCE;
    }

    public void init() {

        Log.i(TAG, "init: " + Utils.getClientId());
        //客户端
        client = new MqttAndroidClient(App.getInstance(), host, Utils.getClientId());
        //配置连接信息
        mqttConnectOptions = new MqttConnectOptions();
        //是否清除缓存
        mqttConnectOptions.setCleanSession(false);
        //是否重连
        mqttConnectOptions.setAutomaticReconnect(true);
        //设置心跳,60s
        mqttConnectOptions.setKeepAliveInterval(30);
        //超时时间
        mqttConnectOptions.setConnectionTimeout(30);
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        if (mqttConnectOptions != null) {
            mqttConnectOptions.setUserName(userName);
            mqttConnectOptions.setPassword(passWord.toCharArray());
        }
        doClientConnection();
    }


    public void setMqttCallback(MqttMessageCallBack mqttCallBack) {
        try {
            this.mqttMessageCallBack = mqttCallBack;
            if (client != null) {
                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {
                        Log.i(Tag, "连接丢失重新连接" + cause.getLocalizedMessage());
                        doClientConnection();
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        String str1 = new String(message.getPayload());
                        Log.i(Tag, "mqtt推送内容Topic=" + topic);
                        Log.i(Tag, "mqtt推送内容:" + str1);
                        if (mqttMessageCallBack != null) {
                            mqttMessageCallBack.messageArrived(topic, str1);
                        }
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消订阅主题
     *
     * @param topic
     */
    public void uSubriceTopic(String topic) {
        try {
            if (client != null) {
                client.unsubscribe(topic, App.getInstance(), new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i(Tag, "取消订阅成功!");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.i(Tag, "取消订阅失败!");
                    }
                });
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅主题
     *
     * @param topic
     * @param qos
     */
    public void subriceTopic(final String topic, int qos) {
        try {
            if (client != null) {
                client.subscribe(topic, qos, App.getInstance(), new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i(Tag, "订阅成功>>主题:" + topic);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.i(Tag, "订阅失败>>主题:" + topic);
                        mqttMessageCallBack.reSubscribe(topic);
                    }
                });
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param topic
     * @param mesg
     */
    public void sendMes(final String topic, final String mesg) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(mesg.getBytes());
        try {
            if (client != null) {
                client.publish(topic, mqttMessage, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i(Tag, "消息发送的Topic:" + topic);
                        Log.i(Tag, "消息发送成功!>>>>" + mesg);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.i(Tag, "消息发送失败Topic:" + topic);
                    }
                });
            }
        } catch (MqttException e) {
            e.printStackTrace();
            Log.i(Tag, "消息发送失败Topic:" + topic);
        }
    }

    /**
     * 判断网络是否连接
     */
    private boolean isConnectIsNormal() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.i(Tag, "MQTT当前网络名称：" + name);
            return true;
        } else {
            Log.i(Tag, "MQTT 没有可用网络");
            return false;
        }
    }

    /**
     * 连接MQTT服务器
     */
    public void doClientConnection() {

        if (client != null && !client.isConnected() && isConnectIsNormal()) {
            try {
                client.connect(mqttConnectOptions, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i(Tag, "连接成功");
                        mqttMessageCallBack.mqttLoginSuccess();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.i(Tag, "连接失败" + exception.getMessage());
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        try {
            if (client != null)
                client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    //网络未连接
    private static final boolean NETWORK_NONE = false;
    //移动数据或无线网络连接
    private static final boolean NETWORK_AVAILABLE = true;

    /**
     * 获取当前网络状态
     *
     * @param context 上下文对象
     * @return boolean
     */
    public static boolean getNetStatus(Context context) {
        // 获取系统连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络状态信息
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager
                .getActiveNetworkInfo() : null;
        //判断网络NetworkInfo是否不为空且连接
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            //网络连接可用
            return NETWORK_AVAILABLE;

        } else {
            return NETWORK_NONE;//网络不可用（未连接）
        }

    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }


}
