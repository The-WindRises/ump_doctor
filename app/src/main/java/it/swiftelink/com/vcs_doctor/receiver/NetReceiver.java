package it.swiftelink.com.vcs_doctor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import it.swiftelink.com.vcs_doctor.util.MqttUtil;

/**
 * @Description:
 * @Author: klk
 * @CreateDate: 2019/11/1 18:27
 */
public class NetReceiver extends BroadcastReceiver {
    //网络状态监听接口
    private NetStatusMonitor netStatusMonitor;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //获取网络状态的类型
            boolean netStatus = MqttUtil.getNetStatus(context);
            if (netStatusMonitor != null)
                // 接口传递网络状态的类型到注册广播的页面
                netStatusMonitor.onNetChange(netStatus);
        }
    }

    /**
     * 网络状态类型改变的监听接口
     */
    public interface NetStatusMonitor {
        void onNetChange(boolean netStatus);
    }

    /**
     * 设置网络状态监听接口
     */
    public void setStatusMonitor(NetStatusMonitor netStatusMonitor) {
        this.netStatusMonitor = netStatusMonitor;
    }

}
