package it.swiftelink.com.common.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

public class ApkUtils {
    /**
     * 安装APK工具类
     *
     * @param context  上下文
     */
    public static void installAPK(Context context, Uri apkUri) {
        try {
            Intent intent = new Intent();

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // 授予目录临时共享权限
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getCachePath(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            //外部存储不可用
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

}
