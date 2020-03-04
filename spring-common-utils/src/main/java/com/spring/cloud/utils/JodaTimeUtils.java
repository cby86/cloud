package com.spring.cloud.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chenby
 */
public abstract class JodaTimeUtils {

    public static DateTime convertToDateTime(String dateStr, String pattern) {
        if (null == dateStr || "".equals(dateStr.trim())) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return formatter.parseDateTime(dateStr);
    }

    public static DateTime convertToDateTime(String dateStr) {
        return convertToDateTime(dateStr, "yyyy-MM-dd");
    }

    public static DateTime convertToDateTimes(String dateStr) {
        return convertToDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static DateTime convertToDayTime(String dateStr) {
        return convertToDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static DateTime convertToTime(String dateStr) {
        return convertToDateTime(dateStr, "yyyy-MM-dd H:m:s");
    }

    public static String convertToHoursMinutes(Date date) {
        if (null == date) {
            return null;
        }
        return convertToTime(date, "HH:mm");
    }

    public static String convertToDay(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (null == date) {
            return null;
        }
        return dateFormat.format(date);
    }

    public static String convertToTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null == date) {
            return null;
        }
        return dateFormat.format(date);
    }

    public static String convertToTime(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        if (null == date) {
            return null;
        }
        return dateFormat.format(date);
    }

    public static DateTime toDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date);
    }

    public static Date toDate(LocalDate startDate) {
        if (startDate != null) {
            return new DateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0, 0, 0).toDate();
        }
        return null;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param from
     * @param to
     * @return
     */
    public static int marginDays(Date from, Date to) {
        //去除分秒
        Date begin = toDate(new LocalDate(from));
        Date end = toDate(new LocalDate(to));
        long millis = end.getTime() - begin.getTime();
        return millis > 0 ? Integer.parseInt(String.valueOf(millis / (1000 * 3600 * 24))) : 0;
    }

    public static Date firstDayOfMonth(Date date) {
        DateTime dateTime = toDateTime(date);
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0, 0, 0).toDate();
    }

    public static Date lastDayOfMonth(Date date) {
        DateTime dateTime = toDateTime(date);
        DateTime dateTime1 = dateTime.dayOfMonth().withMaximumValue();
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime1.getDayOfMonth(), 23, 59, 59, 0).toDate();
    }

    public static Date dayTolast(Date date) {
        DateTime dateTime = toDateTime(date);
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 23, 59, 59, 0).toDate();
    }

    public static Date dayTofirst(Date date) {
        DateTime dateTime = toDateTime(date);
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0, 0, 0).toDate();
    }

    public static Date plusDays(Date date, int days) {
        DateTime dateTime = toDateTime(date);
        dateTime = dateTime.plusDays(days);
        return dateTime.toDate();
    }

    public static Date plusHours(Date date, int hours) {
        DateTime dateTime = toDateTime(date);
        dateTime = dateTime.plusHours(hours);
        return dateTime.toDate();
    }

    public static Date plusMinutes(Date date, int minutes) {
        DateTime dateTime = toDateTime(date);
        dateTime = dateTime.plusMinutes(minutes);
        return dateTime.toDate();
    }

    public static Date plusSeconds(Date date, int second) {
        DateTime dateTime = toDateTime(date);
        dateTime = dateTime.plusSeconds(second);
        return dateTime.toDate();
    }

    public static Date minusDays(Date date, int days) {
        DateTime dateTime = toDateTime(date);
        dateTime = dateTime.minusDays(days);
        return dateTime.toDate();
    }

    public static Date minusHours(Date date, int hours) {
        DateTime dateTime = toDateTime(date);
        dateTime = dateTime.minusHours(hours);
        return dateTime.toDate();
    }

    public static boolean isToday(Date date) {
        return date != null && new LocalDate(date).compareTo(new LocalDate()) == 0;
    }

    public static boolean isBefore(Date date, LocalDate localDate) {
        return date != null && new LocalDate(date).compareTo(localDate) < 0;
    }

    public static int getWeekIndex(Date date) {
        if (date == null) {
            return 0;
        }
        DateTime dateTime = toDateTime(date);
        return dateTime.getDayOfWeek();
    }

    public static int getMonthIndex(Date date) {
        if (date == null) {
            return 0;
        }
        DateTime dateTime = toDateTime(date);
        return dateTime.getDayOfMonth();
    }

    /**
     * 周开始
     *
     * @param date
     * @return
     */
    public static Date getFirstWeekDate(Date date) {
        int weekIndex = getWeekIndex(date);
        Date firstDate = weekIndex == 1 ? date : plusDays(date, -weekIndex + 1);
        return dayTofirst(firstDate);
    }

    public static long marginMinutes(Date from, Date to) {
        long millis = to.getTime() - from.getTime();
        return millis > 0 ? Integer.parseInt(String.valueOf(millis / (1000 * 60))) : 0;
    }

    public static int marginHours(Date from, Date to) {
        long millis = to.getTime() - from.getTime();
        return millis > 0 ? Integer.parseInt(String.valueOf(millis / (1000 * 60 * 60))) : 0;
    }

    public static int marginSeconds(Date from, Date to) {
        long millis = to.getTime() - from.getTime();
        return millis > 0 ? Integer.parseInt(String.valueOf(millis / 1000)) : 0;
    }

    public static String dateToWeek(Date datetime) {
        String[] weekDays = {"6", "0", "1", "2", "3", "4", "5"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(datetime);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String dataToWeekByChinese(int index) {
        switch (index) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            default:
                return "周日";
        }
    }

    public static String convertToTimeHm(Date date) {
        return convertToTime(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 判断某时间段是否与另一个时间段存在重叠
     *
     * @param a
     * @param b
     * @param x
     * @param y
     * @return
     */
    public static boolean checkSection(Date a, Date b, Date x, Date y) {
        if (y.compareTo(a) < 0 || b.compareTo(x) < 0) {
            return false;
        }
        return true;
    }

    /**
     * 转换时间
     *
     * @param time 例 02:00
     * @param date
     * @return
     */
    public static Date convertTimeToDate(String time, Date date) {

        String toTime = JodaTimeUtils.convertToTime(date == null ? new Date() : date, "yyyy-MM-dd") + " ";
        return JodaTimeUtils.convertToDateTime(toTime + time + ":00", "yyyy-MM-dd HH:mm:ss").toDate();
    }
}
