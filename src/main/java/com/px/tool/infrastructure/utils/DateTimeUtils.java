package com.px.tool.infrastructure.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public final class DateTimeUtils {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy";
    private static final String FILE_PATTERN = "yyyy_MM_dd_HH_mm_ss";

    private static final SimpleDateFormat fm_date_time = new SimpleDateFormat(DATE_TIME_PATTERN);

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

    public static String toString(Date time) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Saigon"));
        cal.setTime(time);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("Ngày %s Tháng %s Năm %s", day, month, year);
    }

    public static String toString(Long time) {
        if (Objects.isNull(time)) {
            return "";
        }
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Saigon"));
        cal.setTime(new Date(time));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("Ngày %s Tháng %s Năm %s", day, month, year);
    }

    public static String dateLongToString(Long time) {
        if (Objects.isNull(time)) {
            return "";
        }
        return fm_date_time.format(new Date(time));
    }

    public static String nowAsString() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Saigon"));
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("Ngày %s Tháng %s Năm %s", day, month, year);
    }

}