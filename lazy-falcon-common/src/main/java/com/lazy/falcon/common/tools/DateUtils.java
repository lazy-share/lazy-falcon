package com.lazy.falcon.common.tools;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author laizhiyuan
 * @date 2017/12/28.
 */
public abstract class DateUtils {

    public static final String YYYY_MM_DD_HH_MM_SS  = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD  = "yyyy-MM-dd";
    public static final String DEFAULT_FORMAT = YYYY_MM_DD_HH_MM_SS;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);

    public static synchronized SimpleDateFormat getSimpleDateFormat(String format){
        if (format == null || DEFAULT_FORMAT.equals(format)){
            return simpleDateFormat;
        }else {
            return new SimpleDateFormat(format);
        }
    }

    public static Date getCurrentDate(String format){
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        String currentDateStr = simpleDateFormat.format(new Date());
        try {
            Date currentDate = simpleDateFormat.parse(currentDateStr);
            return currentDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp getCurrentTimestamp(String format){
        return Timestamp.valueOf(getCurrentDateStr(format));
    }

    public static Timestamp string2Timestamp(String time){
        if (time == null || "".equals(time)){
            return null;
        }

        Timestamp timestamp = Timestamp.valueOf(time);
        return timestamp;
    }

    public static Long stringTime2Unix(String time, String format){
        if (time == null || "".equals(time)){
            return null;
        }
        DateFormat simpleDateFormat = getSimpleDateFormat(format);
        try {
            Date currentDate = simpleDateFormat.parse(time);
            long unix = currentDate.getTime();
            return unix;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentDateStr(String format){
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        String currentDateStr = simpleDateFormat.format(new Date());
        return currentDateStr;
    }

    /**
     * Date -> String
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format){
        if (date == null){
            return "";
        }
        if (StringUtils.isEmpty(format)){
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * String -> Date
     * @param strDate
     * @param dateFormat
     * @return
     */
    public static Date strToUtilDate(String strDate, String dateFormat) {
             SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
             Date date = null;
             try {
                     date = sf.parse(strDate);
                } catch (ParseException e) {
                     e.printStackTrace();
                 }
             return date;
         }

    /**
     * Timestamp -> String
     * @param timestamp
     * @return
     */
    public static String timestamp2Str(Timestamp timestamp){
        if (timestamp == null){
            return "";
        }
        DateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
        String strDate = simpleDateFormat.format(timestamp);
        return strDate;
    }

    /**
     * 返回制定范围日期，1为明天，-1为昨天，以此类推
     * @param today
     * @param range
     * @return
     */
    public static Date getSpecifyDates(Date today, Integer range) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + range);
        return calendar.getTime();
    }

    /**
     * 获取指定日期是星期几
     * 参数为null时表示获取当前日期是星期几
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if(date != null){
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 自测
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(timestamp2Str(new Timestamp(System.currentTimeMillis())));
        System.out.println(date2Str(new Date(), null));
    }
}
