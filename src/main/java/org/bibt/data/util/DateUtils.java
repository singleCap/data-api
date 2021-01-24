package org.bibt.data.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 *
 * @author zengfanyong
 * @date 2020/11/18 15:06
 */
@Slf4j
public class DateUtils {

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    /** 禁止创建对象 */
    private DateUtils() {
        throw new UnsupportedOperationException("Construct DateUtils");
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date
     *      Date时间
     * @return LocalDateTime
     *      LocalDateTime时间
     */
    public static LocalDateTime date2LocalDateTime(final Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime
     *      LocalDateTime时间
     * @return Date
     *      Date时间
     */
    public static Date localDataTime2Date(final LocalDateTime localDateTime) {
        final Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前时间
     * 默认时间格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return String
     *      当前时间
     */
    public static String getCurrentTime() {
        return getCurrentTime(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前时间
     *
     * @param format
     *      时间格式
     * @return String
     *      当前时间
     */
    public static String getCurrentTime(final String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式时间
     *
     * @param date
     *      Date时间
     * @param format
     *      时间格式
     * @return String
     *      格式时间
     */
    public static String format(final Date date, final String format) {
        return format(date2LocalDateTime(date), format);
    }

    /**
     * 格式时间
     *
     * @param localDateTime
     *      LocalDateTime时间
     * @param format
     *      时间格式
     * @return String
     *      格式时间
     */
    public static String format(final LocalDateTime localDateTime, final String format) {
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Date时间 转 时间字符串
     * 默认时间格式为 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     *      Date时间
     * @return String
     *      时间字符串
     */
    public static String date2String(final Date date) {
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }
    
    /**
     * 时间字符串 转 Date时间
     * 默认时间格式为 yyyy-MM-dd HH:mm:ss
     *
     * @param str
     *      时间字符串
     * @return Date
     *      Date时间
     */
    public static Date string2Date(final String str) {
        return parse(str, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 时间字符串 转 Date时间
     * 
     * @param str
     *      时间字符串
     * @param format
     *      时间格式
     * @return Date
     *      Date时间
     */
    public static Date parse(final String str, final String format) {
        try {
            final LocalDateTime localDateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern(format));
            return localDataTime2Date(localDateTime);
        } catch (Exception e) {
            log.error("error while parse date:" + str, e);
        }
        return null;
    }

    /**
     * 毫秒级时间差
     *
     * @param date1
     *      时间1
     * @param date2
     *      时间2
     * @return
     *      毫秒级时间差
     */
    private static long differMs(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        return Math.abs(date1.getTime() - date2.getTime());
    }

    /**
     * 秒级时间差
     *
     * @param date1
     *      时间1
     * @param date2
     *      时间2
     * @return
     *      秒级时间差
     */
    public static long differSec(final Date date1, final Date date2) {
        return (long) Math.ceil(differMs(date1, date2) / 1000.0);
    }

    /**
     * 分钟级时间差
     *
     * @param date1
     *      时间1
     * @param date2
     *      时间2
     * @return
     *      分钟级时间差
     */
    public static long differMin(final Date date1, final Date date2) {
        return (long) Math.ceil(differSec(date1, date2) / 60.0);
    }

    /**
     * 小时级时间差
     *
     * @param date1
     *      时间1
     * @param date2
     *      时间2
     * @return
     *      小时级时间差
     */
    public static long differHours(final Date date1, final Date date2) {
        return (long) Math.ceil(differMin(date1, date2) / 60.0);
    }

    /**
     * 获取某天
     *
     * @param date
     *      Date时间
     * @param day
     *      天数，接受正数或负数
     * @return
     *      计算后的某天
     */
    public static Date getSomeDay(final Date date, final int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 根据时间获取属于第几个小时（在一天中）
     *
     * @param date
     *      Date时间
     * @return int
     *      第几个小时
     */
    public static int getHourIndex(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 比较俩时间
     *
     * @param future
     *      未来时间
     * @param old
     *      老旧时间
     * @return boolean
     *      true：未来时间大于老旧时间
     *      false：未来时间小于或等于老旧时间
     */
    public static boolean compare(final Date future, Date old) {
        return future.getTime() > old.getTime();
    }

    /**
     * 获取调度时间
     *
     * @param schedule
     *      调度命令时间字符串
     * @return
     *      调度时间
     */
    public static Date getScheduleDate(final String schedule) {
        return string2Date(schedule);
    }

    /**
     * 格式时间，使时间可读
     * 例：1天 5小时:2分钟:19秒
     *
     * @param ms
     *      毫秒
     * @return String
     *      格式后的时间字符串
     */
    public static String format2Readable(final long ms) {

        final long days = ms / (1000 * 60 * 60 * 24);
        final long hours = (ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        final long minutes = (ms % (1000 * 60 * 60)) / (1000 * 60);
        final long seconds = (ms % (1000 * 60)) / 1000;

        return String.format("%02d天 %02d小时:%02d分钟:%02d秒", days, hours, minutes, seconds);
    }

    /**
     * 获取时间所在一周中的周一
     * 默认一周的第一天为周一
     *
     * @param date
     *      Date时间
     * @return Date
     *      周一的Date时间
     */
    public static Date getMonday(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获取时间所在一周中的周日
     * 默认一周的第一天为周一
     *
     * @param date
     *      Date时间
     * @return Date
     *      周日的Date时间
     */
    public static Date getSunday(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }

    /**
     * 获取时间所在一月中的第一天
     *
     * @param date
     *      Date时间
     * @return Date
     *      1号的Date时间
     */
    public static Date getFirstDayOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取一天中的某小时
     *
     * @param date
     *      Date时间
     * @param offsetHour
     *      小时的序号
     * @return Date
     *      某小时
     */
    public static Date getSomeHourOfDay(final Date date, final int offsetHour) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + offsetHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取时间所在一月中的最后一天
     *
     * @param date
     *      Date时间
     * @return Date
     *      最后一天的Date时间
     */
    public static Date getLastDayOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取一天的开始时间
     * 格式：YYYY-MM-DD 00:00:00.0
     *
     * @param date
     *      Date时间
     * @return Date
     *      一天的开始时间
     */
    public static Date getStartOfDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取一天的结束时间
     * 格式：YYYY-MM-DD 23:59:59.999
     *
     * @param date
     *      Date时间
     * @return Date
     *      一天的结束时间
     */
    public static Date getEndOfDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取一小时的开始时间
     * 格式：YYYY-MM-DD HH:00:00.0
     *
     * @param date
     *      Date时间
     * @return Date
     *      一小时的开始时间
     */
    public static Date getStartOfHour(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取一小时的结束时间
     * 格式：YYYY-MM-DD HH:59:59.999
     *
     * @param date
     *      Date时间
     * @return Date
     *      一小时的结束时间
     */
    public static Date getEndOfHour(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取当前Date时间
     *
     * @return Date
     *      当前Date时间
     */
    public static Date getCurrentDate() {
        return DateUtils.parse(DateUtils.getCurrentTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 增加时间配置
     *
     * @param date
     *      Date时间
     * @param calendarField
     *      calendar字段
     * @param amount
     *      数额
     * @return Date
     *      增加时间配置的Date时间
     */
    public static Date add(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 从当前时间开始，获取目标时间之前还剩多少秒
     * targetTime = baseTime + intervalSeconds
     *
     * @param baseTime
     *      目标时间
     * @param intervalSeconds
     *      间隔的秒数
     * @return long
     *      秒数
     */
    public static long getRemainTime(final Date baseTime, final long intervalSeconds) {
        if (baseTime == null) {
            return 0;
        }
        final long usedTime = (System.currentTimeMillis() - baseTime.getTime()) / 1000;
        return intervalSeconds - usedTime;
    }

    /**
     * 获取当前时间的时间戳
     * 默认格式：yyyyMMddHHmmssSSS
     *
     * @return String
     *      当前时间的时间戳
     */
    public static String getCurrentTimeStamp() {
        return getCurrentTime(YYYYMMDDHHMMSSSSS);
    }

}
