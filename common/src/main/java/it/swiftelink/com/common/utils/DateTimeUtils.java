package it.swiftelink.com.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.swiftelink.com.common.R;


public class DateTimeUtils implements DatePicker.OnDateChangedListener {
    private DatePicker datePicker;
    private AlertDialog ad;
    private String dateTime;
    private String initDateTime;
    private Activity activity;

    public DateTimeUtils(Activity activity, String initDateTime) {
        this.activity = activity;
        this.initDateTime = initDateTime;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        dateTime = sdf.format(calendar.getTime());
        ad.setTitle(dateTime);
    }


    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.YEAR)
                    + calendar.get(Calendar.MONTH)
                    + calendar.get(Calendar.DAY_OF_MONTH)
                    + calendar.get(Calendar.HOUR_OF_DAY) + "";
        }
        datePicker.setMaxDate(new Date().getTime());
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
    }


    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    public static String getAge(String birthTime, Context context) {

        int yearToday = Calendar.getInstance().get(Calendar.YEAR);
        Date date = DateTimeUtils.strToDate(birthTime);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int age = yearToday - year;
        if (age > 3) {
            return age + context.getResources().getString(R.string.label_year);
        } else {
            Date date1 = new Date();

            try {
                int betweenDays = DateTimeUtils.daysBetween(date, date1);
                if (betweenDays < 60) {
                    return betweenDays + context.getResources().getString(R.string.label_day);
                } else {
                    int monthDiff = DateTimeUtils.getMonthDiff(date1, date);

                    return monthDiff + context.getResources().getString(R.string.label_month);
                }
            } catch (ParseException e) {
                return "";
            }
        }
    }

    /**
     * 获取两个日期相差的月数
     *
     * @param d1 较大的日期
     * @param d2 较小的日期
     * @return 如果d1>d2返回 月数差 否则返回0
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16 d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) yearInterval--;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) monthInterval--;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    public AlertDialog dateTimePicKDialog(final TextView inputDate) {
        RelativeLayout dateTimeLayout = (RelativeLayout) activity
                .getLayoutInflater().inflate(R.layout.layout_date_picker, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.date_picker);
        init(datePicker);


        ad = new AlertDialog.Builder(activity)
                .setTitle(initDateTime)
                .setView(dateTimeLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(dateTime);


                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();

        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    private Calendar getCalendarByInintData(String initDateTime) {
        Calendar calendar = Calendar.getInstance();

        String date = initDateTime.substring(0, 10);

        String yearStr = spliteString(date, "-", "index", "front"); // 年份
        String monthAndDay = spliteString(date, "-", "index", "back"); // 月日

        String monthStr = spliteString(monthAndDay, "-", "index", "front"); // 月
        String dayStr = spliteString(monthAndDay, "-", "index", "back"); // 日


        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
        int currentDay = Integer.valueOf(dayStr.trim()).intValue();

        calendar.set(currentYear, currentMonth, currentDay);
        return calendar;
    }

    public static String spliteString(String srcStr, String pattern,
                                      String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern);
        } else {
            loc = srcStr.lastIndexOf(pattern);
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }


    public static final long M24HOURMS = 86400000;

    public static List<Long> getWeekDayList(String startTime) {
        // 存放每一天时间的集合
        List<Long> weekMillisList = new ArrayList<Long>();
        long dateMill = 0;

        long startDate = Long.parseLong(startTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateMill);


        for (int i = 0; i < 7; i++) {
            weekMillisList.add(startDate + M24HOURMS * i);
        }


        return weekMillisList;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static boolean isIn(Date currentDate, Date beginDate, Date endDate) {
        long beginMills = beginDate.getTime();
        long endMills = endDate.getTime();
        long thisMills = currentDate.getTime();
        return thisMills >= Math.min(beginMills, endMills) && thisMills <= Math.max(beginMills, endMills);
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(String time) {
        long timeL = Long.parseLong(time);
        Date d = new Date(timeL);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getCurrentDateStr() {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return sf.format(date);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToStringDay(String time) {
        long timeL = Long.parseLong(time);
        Date d = new Date(timeL);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToHour(String time) {
        long timeL = Long.parseLong(time);
        Date d = new Date(timeL);
        SimpleDateFormat sf = new SimpleDateFormat("HH");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToStringHour(String time) {
        long timeL = Long.parseLong(time);
        Date d = new Date(timeL);
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd HH:mm");
        return sf.format(d);
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static Date getLongToDate(String time){

        long timeL = Long.parseLong(time);
        Date d = new Date(timeL);

        return d;
    }

    /**
     * 比较两个日期是否为同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一天
     * @since 4.1.13
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return isSameDay(calendar(date1), calendar(date2));
    }

    /**
     * 比较两个日期是否为同一天
     *
     * @param cal1 日期1
     * @param cal2 日期2
     * @return 是否为同一天
     * @since 4.1.13
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && //
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && //
                cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA);
    }

    /**
     * 转换为Calendar对象
     *
     * @param date 日期对象
     * @return Calendar对象
     */
    public static Calendar calendar(Date date) {
        return calendar(date.getTime());
    }

    /**
     * 转换为Calendar对象
     *
     * @param millis 时间戳
     * @return Calendar对象
     */
    public static Calendar calendar(long millis) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal;
    }



}
