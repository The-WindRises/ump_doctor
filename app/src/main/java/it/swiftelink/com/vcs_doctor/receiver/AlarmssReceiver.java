package it.swiftelink.com.vcs_doctor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import it.swiftelink.com.vcs_doctor.service.WaitingService;


public class AlarmssReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intentWaitingService = new Intent(context, WaitingService.class);
            context.startForegroundService(intentWaitingService);
        } else {
            Intent intentWaitingService = new Intent(context, WaitingService.class);
            context.startService(intentWaitingService);
        }
    }
}
