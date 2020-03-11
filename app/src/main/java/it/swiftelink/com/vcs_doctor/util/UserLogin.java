package it.swiftelink.com.vcs_doctor.util;

import android.content.Intent;
import android.text.TextUtils;

import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.vcs_doctor.App;

/**
 * @作者 Arvin
 * @时间 2019/8/3 16:31
 * @一句话描述 用户登录
 */
public class UserLogin {
    public static boolean isLogin() {
        String token = App.getSPUtils().getString(Common.SPApi.TOKEN);
        if (TextUtils.isEmpty(token)) {
            return false;
        } else {
            return true;
        }
    }
}
