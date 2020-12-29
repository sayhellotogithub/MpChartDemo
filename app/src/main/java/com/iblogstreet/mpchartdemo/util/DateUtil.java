package com.iblogstreet.mpchartdemo.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author think
 * @date 2018/7/4 下午5:47
 */
public class DateUtil {
    public static String FORMAT_Y_M_D = "yyyy/MM/dd";
    public static String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static String FORMAT_YYYY_MM_DD_CHINA = "yyyy年MM月dd日";
    public static String FORMAT_MM_DD_CHINA = "MM月dd日";
    public static String FORMAT_YYYY_MM_CHINA = "yyyy年MM月";
    public static String FORMAT_YYYY_M_CHINA = "yyyy年M月";
    public static String FORMAT_YYYY_MM = "yyyy-MM";
    public static String FORMAT_YYYYMMDD = "yyyyMMdd";
    public static String FORMAT_MM_DD = "MM-dd";
    public static String FORMAT_MM_DOT_DD = "MM.dd";
    public static String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static String FORMAT_YYYY_MM_DD_HH_MM_REVERSE_LINE = "yyyy/MM/dd HH:mm";
    public static String FORMAT_DD = "dd";
    public static String FORMAT_D = "d";
    public static String FORMAT_YYYY = "yyyy";
    public static String FORMAT_MM_DD__REVERSE_LINE = "MM/dd";
    public static String[] WEEK_DAYS = {
            "周日",
            "周一",
            "周二",
            "周三",
            "周四",
            "周五",
            "周六"
    };

    /**
     * Long 转换时间
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String format(Long time, String pattern) {
        if (time == null) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(time);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间转 Long
     *
     * @param time
     * @param pattern
     * @return
     */
    public static long format(String time, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(time).getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    public static String formatYMD(long time) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_Y_M_D);
        return format.format(time);
    }

    public static String formatMM_DD(Long time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_MM_DD);
        return format.format(time);
    }

    public static String formatYYYY_MM_DD(long time) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
        return format.format(time);
    }

    /**
     * 将字符串转成yyyy-mm-dd格式
     *
     * @param date
     * @return
     */
    public static String formatStringToYYYY_MM_DD(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);

    }

    /**
     * 将字符串转成yyyy-mm-dd格式
     *
     * @param date
     * @return
     */
    public static String formatStringToYYYY_MM_DD(String date, SimpleDateFormat format) {
        try {
            Date parse = format.parse(date);
            return formatStringToYYYY_MM_DD(parse);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String formatTimSatmpToString(String timeStamp, String format) {
        if (TextUtils.isEmpty(timeStamp)) {
            return "";
        }
        try {
            long timeS = Long.parseLong(timeStamp);
            return format(timeS, format);
        } catch (Exception e) {
            e.printStackTrace();
            return timeStamp;
        }
    }

    /**
     * 获得本周一与当前日期相差的天数
     *
     * @return
     */
    public static int getMondayPlus(Calendar cd) {
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }


    /**
     * 根据星期取日期
     * 这周的日期
     *
     * @param plus
     * @return
     */
    public static String getCurrDateForWeek(int plus, String pattern) {
        return format(getCurrDateForWeek(Calendar.getInstance(), plus), pattern);
    }

    /**
     * 根据星期取日期
     *
     * @param calendar
     * @param plus     0,1,2,3 周一 周二 周三
     * @return
     */
    public static long getCurrDateForWeek(Calendar calendar, int plus) {
        int mondayPlus = getMondayPlus(calendar);
        calendar.add(GregorianCalendar.DATE, mondayPlus + plus);
        return calendar.getTime().getTime();
    }

    /**
     * 根据星期取日期
     *
     * @param plus 0,1,2,3
     * @return
     */
    public static long getCurrDateForWeek(int plus) {
        Calendar calendar = Calendar.getInstance();
        int mondayPlus = getMondayPlus(calendar);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(GregorianCalendar.DATE, mondayPlus + plus);
        return calendar.getTime().getTime();
    }

    public static long getNextWeek(int plus) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        int mondayPlus = getMondayPlus(calendar);
        calendar.add(GregorianCalendar.DATE, mondayPlus + plus);
        return calendar.getTime().getTime();
    }

    /**
     * 根据星期取日期
     * 这周的日期
     *
     * @param plus
     * @return
     */
    public static String getNextWeek(int plus, String pattern) {
        return format(getNextWeek(plus), pattern);
    }

    /**
     * 根据日期得到星期
     *
     * @return
     */
    public static int getWeekByDate(Long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return w;
    }

    public static String getWeekStringByDate(Long date) {
        if (date == null) {
            return "";
        }
        return WEEK_DAYS[getWeekByDate(date)];
    }

    /**
     * 判断当前时间是否是本周
     *
     * @param time
     * @return
     */
    public static boolean isThisWeek(Long time) {
        if (time == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();

        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        //如果是星期天的话，则要特殊处理，因为外国将星期天定义为一周的开始
        if (getWeekByDate(System.currentTimeMillis()) == 0) {
            currentWeek--;
        }

        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    /**
     * 判断选择的日期是否是今天
     */

    public static boolean isToday(Long time) {
        if (time == null) {
            return false;
        }
        return isThisTime(time, FORMAT_YYYY_MM_DD);
    }

    private static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        //参数时间
        String param = sdf.format(date);
        //当前时间
        String now = sdf.format(System.currentTimeMillis());
        if (TextUtils.equals(param, now)) {
            return true;
        }
        return false;
    }

    /**
     * 两个日期的间隔
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int diffDaysByMillisecond(Date startDate, Date endDate) {
        int days = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 两个日期的间隔
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int diffDaysByMillisecond(Long startTime, Long endTime) {
        int days = (int) ((endTime - startTime) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 日期加一天
     *
     * @param date
     * @return
     */
    public static long addOneDay(Long date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }


    /**
     * 日期加天
     *
     * @param date  日期
     * @param dates 天数
     * @return
     */
    public static long addDay(Long date, int dates) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.add(Calendar.DATE, dates);
        return calendar.getTimeInMillis();
    }

    /**
     * 智能转换时间
     * 1分钟内：刚刚
     * 1小时内：xx分钟前
     * 1-24小时内：xx小时前
     * 本年度内：MM/DD HH:MM
     * 其他年份：YYYY/MM/DD HH:MM
     *
     * @param time
     * @return
     */
    public static String parseSmartDesc(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Calendar nowCalendar = Calendar.getInstance();

        long diff = Math.abs(calendar.getTimeInMillis() - nowCalendar.getTimeInMillis());
        if (diff < 60 * 1000) {
            return "刚刚";
        } else if (diff < 60 * 1000 * 60) {
            long minute = diff / (60 * 1000);
            return String.format("%s分钟前", minute);
        } else if (diff < 60 * 1000 * 60 * 24) {
            long hour = diff / (60 * 1000 * 60);
            return String.format("%s小时前", hour);
        } else {
            if (calendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR)) {
                return format(time, "MM/dd HH:mm");
            } else {
                return format(time, FORMAT_YYYY_MM_DD_HH_MM_REVERSE_LINE);
            }
        }
    }

    /**
     * 判断是否是今年
     *
     * @param time
     * @return
     */
    public static boolean isThisYear(long time) {
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(time);

        return calendar.get(Calendar.YEAR) == thisYear;
    }

    /**
     * 获取每月第一天
     *
     * @return
     */
    public static Long getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 0);
        return calendar.getTime().getTime();
    }


    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获取每个季度的第一天
     *
     * @param date
     * @return
     */

    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }


    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }


}
