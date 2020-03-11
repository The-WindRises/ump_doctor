package it.swiftelink.com.common.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import it.swiftelink.com.common.app.Application;

/**
 * Created by Administrator on 2018/5/11.
 */

public class TxtUtils {

    /**
     * 校验身份证
     */
    public static boolean chickedIdCard(String IDNumber) {
        String reg18 = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";
        return Pattern.matches(reg18, IDNumber);
    }

    /**
     * 校验电话号码
     */
    public static boolean chickedPhone(String phone) {
        return isChinaPhoneLegal(phone) || isHKPhoneLegal(phone);
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 校验邮箱
     */
    public static boolean chickedEmail(String email) {

        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配
        return m.matches();
    }


    /**
     * 从assert中获取一个字符串
     */
    public String getStringFromAssert(Context context, String fileName) {
        String content = null; // 结果字符串
        try {
            InputStream is = context.getResources().getAssets().open(fileName); // 打开文件
            int ch = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream(); // 实现了一个输出流
            while ((ch = is.read()) != -1) {
                out.write(ch); // 将指定的字节写入此 byte 数组输出流
            }
            byte[] buff = out.toByteArray();// 以 byte 数组的形式返回此输出流的当前内容
            out.close(); // 关闭流
            is.close(); // 关闭流
            content = new String(buff, "UTF-8"); // 设置字符串编码
        } catch (Exception e) {
            Toast.makeText(context, "对不起，没有找到指定文件！", Toast.LENGTH_SHORT).show();
        }
        return content;
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardNum
     * @return
     */
    public static boolean checkBankCard(String cardNum) {
        if (cardNum == null || cardNum.length() < 15 || cardNum.length() > 19) {
            Application.showToast("请填写正确的银行卡号");
            return false;
        } else {
            char bit = getBankCardCheckCode(cardNum.substring(0, cardNum.length() - 1));
            if (bit == 'N') {
                return false;
            }
            return cardNum.charAt(cardNum.length() - 1) == bit;
        }


    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


    public static String NumberFormat(float f, int m) {
        return String.format("%." + m + "f", f);
    }

    public static float NumberFormatFloat(float f, int m) {
        String strfloat = NumberFormat(f, m);
        return Float.parseFloat(strfloat);
    }

    //ba
    public static String formatDateTime(long mss) {
        String DateTimes = null;
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        if (days > 0) {
            DateTimes = days + "天";
//                    + hours + "小时" + minutes + "分钟" + seconds + "秒";
        } else if (hours > 0) {
            DateTimes = hours + "小时";
//                    + minutes + "分钟" + seconds + "秒";
        } else if (minutes > 0) {
            DateTimes = minutes + "分钟";
//                    + seconds + "秒";
        } else {
            DateTimes = seconds + "秒";
        }

        return DateTimes;
    }

    //将字符串转成保留两位数的字符串
    public static String getDouble(String num) {
        double doubleNum = Double.parseDouble(num);

        return "" + Math.floor(doubleNum * 100 + 0.45) / 100;
    }

    /**
     * 校验密码
     */
    public static boolean chickedPassword(String psd) {
        if (psd.length() <= 14 && psd.length() >= 6) {
            //            String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
            //            //正则表达式的模式
            //            Pattern p = Pattern.compile(PW_PATTERN);
            //            //正则表达式的匹配器
            //            Matcher m = p.matcher(psd)
            // 进行正则匹配
            return true;
        } else {
            return false;
        }
    }

    public static String duplicateRemovalStr(String str) {

        if (str != null && !"".equals(str) && str.contains(",")) {
            String[] strs = str.split(",");
            Map<String, Boolean> sm = new HashMap<String, Boolean>();
            List<String> l = new ArrayList<String>();
            for (String st : strs) {
                if (!sm.containsKey(st)) {
                    l.add(st);
                    sm.put(st, true);
                }
            }
            StringBuilder rvaluationStringBuilder = new StringBuilder();
            for (String st : l) {
                rvaluationStringBuilder.append(rvaluationStringBuilder);
                rvaluationStringBuilder.append(",");
            }

            return rvaluationStringBuilder.toString();
        } else {
            return str;
        }
    }

}
