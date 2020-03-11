package it.swiftelink.com.vcs_doctor.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.RequiresApi;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.factory.model.NoticeResModel;
import it.swiftelink.com.factory.net.NetWork;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.receiver.AlarmssReceiver;
import rx.Observable;

public class WaitingService extends Service {
    private AlarmManager mManager;
    private PendingIntent mPi;
    private static boolean control = false;

    private boolean isplay = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        long triggerAtTime = SystemClock.elapsedRealtime() + 5 * 1000;
        mManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmssReceiver.class);//开启广播
        mPi = PendingIntent.getBroadcast(this, 0, i, 0);
        mManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, mPi);//启动
        getNotice();
        createNotificationChannel();
        return super.onStartCommand(intent, flags, startId);
    }

    //获取订单通知
    private void getNotice() {
        Observable<NoticeResModel> observable = NetWork.getRequest().notice();
        NetWork.getInstance().netRequestNot401(observable, new NetWork.NetCallback<NoticeResModel>() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(NoticeResModel noticeResModel) {
                if (noticeResModel != null) {
                    if (noticeResModel.isData()) {
                        MediaPlayer mPlayer = MediaPlayer.create(getApplication(), R.raw.ling);
                        if (!mPlayer.isPlaying()) {
                            mPlayer.start();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mManager != null) {
            mManager.cancel(mPi);
        }
        super.onDestroy();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 通知渠道的id
        String id = "my_channel_01";
        // 用户可以看到的通知渠道的名字.
        CharSequence name = getString(R.string.app_name);
//         用户可以看到的通知渠道的描述 "后台服务用户监听最新订单"
        String description = getString(R.string.app_servicedescribe);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//         最后在notificationmanager中创建该通知渠道 //
        mNotificationManager.createNotificationChannel(mChannel);
        // 为该通知设置一个id
        int notifyID = 1;
        // 通知渠道的id
        String CHANNEL_ID = "my_channel_01";
        Notification notification = new Notification.Builder(this)
                .build();
        startForeground(1, notification);
    }

}
