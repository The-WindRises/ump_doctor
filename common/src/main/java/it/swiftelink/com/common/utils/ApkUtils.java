package it.swiftelink.com.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import java.io.File;

import androidx.core.content.FileProvider;

public class ApkUtils {
    /**
     * 安装APK工具类
     *
     * @param context  上下文
     * @param filePath 文件路径
     */
    public static void installAPK(Context context, String filePath) {
        try {
            Intent intent = new Intent();

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            File apkFile = new File(filePath);
            Uri apkUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // 授予目录临时共享权限
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String authority = context.getPackageName() + ".image_provider";
                apkUri = FileProvider.getUriForFile(context, authority, apkFile);
            } else {
                apkUri = Uri.fromFile(apkFile);
            }
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
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
