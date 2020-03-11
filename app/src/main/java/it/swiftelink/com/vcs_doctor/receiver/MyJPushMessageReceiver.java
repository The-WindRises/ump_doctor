package it.swiftelink.com.vcs_doctor.receiver;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import it.swiftelink.com.vcs_doctor.ui.activity.message.MessageActivity;


/**
 * @Description:
 * @Author: klk
 * @CreateDate: 2020/1/3 12:41
 */
public class MyJPushMessageReceiver extends JPushMessageReceiver {
    public static int count;
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        count=count+1;
        JPushInterface.setBadgeNumber(context,count);
        AppShortCutUtil.setCount(count, context);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        //**************解析推送过来的json数据******************
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(notificationMessage.notificationExtras);
            String type = jsonObject.getString("type");
            String className = ((ActivityManager.RunningTaskInfo) ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1).get(0)).topActivity.getClassName();
            if (type.equals("NEWS")) {
                //消息类型为公告列表
                    Intent intent = new Intent(context, MessageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
