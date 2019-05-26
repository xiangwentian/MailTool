package com.workman.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @创建日期 2009-3-11 下午11:13:41
 * @类名 DateUtil.java
 * @模块 日期转换工具
 */
public class DateUtil {

    public static final String DATEFORMAT1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATEFORMAT3 = "yyyyMMddHHmmss";
    public static final String DATEFORMAT2 = "yyMMddHHmm";
    public static final String DATEFORMAT4 = "yyyy.MM.dd";
    public static final String DATEFORMAT5 = "yyyy-MM";
    public static final String DATEFORMAT6 = "yyyy-MM-dd";
    public static final String DATEFORMAT7 = "yyyy-MM-dd HH:mm";
    public static final String DATEFORMAT8 = "yyyyMMdd";

    /**
     * 转换日期为自定义字符串
     *
     * @param aDate 日期
     * @param aMask 目标格式
     * @return
     */
    public static String parserDateToString(Date aDate, String aMask) {
        if (aDate != null) {
            return new SimpleDateFormat(aMask == null ? DATEFORMAT1 : aMask).format(aDate);
        }
        return null;
    }

    /**
     * 字符串转换成时间格式：自定义目标格式，验证格式，如果格式不正确返回null
     *
     * @param aDate 时间
     * @param aMask 目标格式
     * @return
     */
    public static String DateFromString(String aDate, String aMask) {
        if (aDate == null) {
            return null;
        }
        String dt = null;
        try {
            dt = new SimpleDateFormat(aMask == null ? DATEFORMAT1 : aMask)
                    .format(new SimpleDateFormat(aMask == null ? DATEFORMAT1 : aMask).parse(aDate));
        } catch (ParseException e) {
        }
        return dt;
    }

    /**
     * 字符串转换成时间格式：自定义目标格式，验证格式，如果格式不正确返回null
     *
     * @param aDate 时间
     * @param aMask 目标格式
     * @return Date
     */
    public static Date parserDateFromString(String aDate, String aMask) {
        if (aDate == null) {
            return null;
        }
        Date dt = null;
        try {
            dt = new SimpleDateFormat(aMask == null ? DATEFORMAT1 : aMask).parse(aDate);
        } catch (ParseException e) {
        }
        return dt;
    }

    /**
     * 当前系统时间生成日期
     */
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT3);
        return simpleDateFormat.format(new Date()).toString();
    }

    /**
     * DATEFORMAT1
     *
     * @return
     */
    public static String getNowTime1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT1);
        return simpleDateFormat.format(new Date()).toString();
    }

    /**
     * 根据时间转换时间
     */
    public static String getNowTime(String outDateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outDateFormat);
        return simpleDateFormat.format(new Date()).toString();
    }

    /**
     * 根据给定的格式输出日
     *
     * @param inDate
     * @param inDateFormat
     * @param outDateFormat
     * @return
     * @throws ParseException
     */
    public static String formatDate(String inDate, String inDateFormat,
                                    String outDateFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inDateFormat);
        Date date = simpleDateFormat.parse(inDate);
        simpleDateFormat.applyPattern(outDateFormat);
        return simpleDateFormat.format(date);
    }

    /**
     * @param date2
     * @return
     * @Title:isDateBefore
     * @Description:判断参数时间是否比现在时间大
     */
    public static boolean isDateBefore(String date2) {
        try {
            Date date1 = new Date();
            DateFormat df = DateFormat.getDateTimeInstance();
            return date1.before(df.parse(date2));
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * @param date2
     * @return
     * @Title:isDateAfter
     * @Description:判断参数时间是否比现在时间小
     */
    public static boolean isDateAfter(String date2) {
        try {
            Date date1 = new Date();
            DateFormat df = DateFormat.getDateTimeInstance();
            return date1.after(df.parse(date2));
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @Title:compareDate
     * @Description:比较时间1是否大于时间2
     */
    public static boolean compareDate(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date2).before(df.parse(date1));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 格式化时间为当前时区的时间
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date formatZoneDate(String date) throws ParseException {
        SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
                java.util.Locale.ENGLISH);
        return sfStart.parse(date);
    }

    /**
     * 格式化时间为当前时区的时间
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatZoneDate2(String date) throws ParseException {
        SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
                java.util.Locale.ENGLISH);
        return sfStart.format(date);
    }

    /**
     * 格式化时间为当前时区的时间
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date formatZoneDate3(String date) throws ParseException {
        SimpleDateFormat sfStart = new SimpleDateFormat(DATEFORMAT1, java.util.Locale.ENGLISH);
        return sfStart.parse(date);
    }

    /**
     * 秒转换为中文时间格式,如:3小时33分钟
     *
     * @param second 秒数
     * @return
     */
    public static String second2CnDes(Long second) {
        if (second == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(second / 3600);
        sb.append("小时");
        sb.append((second % 3600) % 60 > 0 ? ((second % 3600) / 60) + 1 : (second % 3600) / 60);
        sb.append("分钟");
        return sb.toString();
    }

    /**
     * 比较传入时间是否大于当前时间
     *
     * @param date
     * @return
     */
    public static boolean afterCurrentTime(Date date) {
        boolean indate = false;//定义在有效期内
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);

        Date now = new Date();
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(now);
        //入参时间在当前时间之后，说明有效期内
        if (dateCalendar.after(targetCalendar)) {
            indate = true;
        }

        return indate;
    }

    /**
     * 判断当前时间是否超过
     *
     * @param start
     * @return
     */
    public static boolean compareTimeOut(Date start, int hour) {
        Date end = new Date();
        long cha = end.getTime() - start.getTime();

        double result = cha * 1.0 / (1000 * 60 * 60);
        if (result <= hour) {//默认24小时
            return false;//说明小于24小时
        } else {
            return true;
        }

    }

    public static String processTimeParam(int day, String dateType) {
        String resultTime = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat(dateType);
            Date timeNow = new Date();
            Calendar begin = Calendar.getInstance();
            begin.setTime(timeNow);
            begin.add(Calendar.DAY_OF_MONTH, -day);
            resultTime = df.format(begin.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultTime;
    }

    public static void main(String[] args) {
    }
}
