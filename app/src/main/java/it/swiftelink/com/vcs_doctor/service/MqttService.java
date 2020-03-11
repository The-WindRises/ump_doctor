package it.swiftelink.com.vcs_doctor.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import it.swiftelink.com.common.app.Application;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.MsgEvent;
import it.swiftelink.com.vcs_doctor.receiver.NetReceiver;
import it.swiftelink.com.vcs_doctor.util.MqttUtil;

/**
 * @作者 Arvin
 * @时间 2019/10/14 9:34
 * @一句话描述 mqtt服务
 */
public class MqttService extends Service implements NetReceiver.NetStatusMonitor {
    public static final String CHANNEL_ID_STRING = "nyd001";
    private NetReceiver netBroadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerBroadcastReceiver();
        Notification();
        final String mRoomTopic = Constants.ROOMTOPIC + Application.getSPUtils().getString("LoginDoctorId");
        MqttUtil.getInstance().init();
        MqttUtil.getInstance().setMqttCallback(new MqttMessageCallBack() {
            @Override
            public void messageArrived(String topic, String message) {
                if (topic.equals(mRoomTopic)) {
                    App.order.put("order", message);
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(Constants.ORDERREFRESH);
                    getBaseContext().sendBroadcast(broadcastIntent);
                }
            }

            @Override
            public void reSubscribe(String topic) {

            }

            @Override
            public void mqttLoginSuccess() {
                MqttUtil.getInstance().subriceTopic(mRoomTopic, 2);
            }
        });
    }

    /**
     * 注册网络状态广播
     */
    private void registerBroadcastReceiver() {
        //注册广播
        if (netBroadcastReceiver == null) {
            netBroadcastReceiver = new NetReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netBroadcastReceiver, filter);
            //设置监听
            netBroadcastReceiver.setStatusMonitor(this);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(netBroadcastReceiver);
    }

    //显示通知
    private void Notification() {
        NotificationManager notificationManager = (NotificationManager) App.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID_STRING, App.getInstance().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
            startForeground(1, notification);
        }
    }

    @Override
    public void onNetChange(boolean netStatus) {
        if (netStatus) {
            MqttUtil.getInstance().doClientConnection();
        }
    }
}
