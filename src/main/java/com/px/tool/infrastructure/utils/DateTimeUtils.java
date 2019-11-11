package com.px.tool.infrastructure.utils;

import org.apache.logging.log4j.util.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateTimeUtils {

    private static final String ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private static final String ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String FILE_PATTERN = "yyyy_MM_dd_HH_mm_ss";

    private static final SimpleDateFormat fm_date_time = new SimpleDateFormat(ISO_8601_PATTERN);

    public static long nowAsMilliSec() {
        return System.currentTimeMillis();
    }

    public static Date nowAsDate() {
        return new Date();
    }

    public static boolean isExpiredWithLimit(Integer limit, Date date) {
        long dateTime = date.getTime();
        return dateTime + limit * 3600000 > new Date().getTime();
    }

    public static String formatyyyyMMddhhmmDate(String dateTime) {
        if (dateTime == null) {
            return Strings.EMPTY;
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat(ISO_8601_PATTERN);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        try {
            Date date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException("Khong tim duoc dinh dang phu hop");
        }
    }

    public static String toString(Date time) {
        return toString(time, ISO_PATTERN);
    }

    public static String toString(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static String getFileSuffix() {
        SimpleDateFormat df = new SimpleDateFormat(FILE_PATTERN);
        return df.format(new Date());
    }

    public static String nowAsString() {
        return toString(new Date(), DATE_TIME_PATTERN);
    }

    public static boolean isBefore(Date date) {
        return date.before(new Date());
    }

    public static Date toDate(String reservedFrom) {
        try {
            return fm_date_time.parse(reservedFrom);
        } catch (ParseException e) {
            throw new RuntimeException("Khong tim duoc dinh dang phu hop");
        }
    }

    public static Date getLastMonthLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));


        return calendar.getTime();
    }

    public static Date getLastMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int max = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

}