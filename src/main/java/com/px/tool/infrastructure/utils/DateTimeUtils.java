package com.px.tool.infrastructure.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public final class DateTimeUtils {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy";

    private static final SimpleDateFormat fm_date_time = new SimpleDateFormat(DATE_TIME_PATTERN);

    public static long nowAsMilliSec() {
        return System.currentTimeMillis();
    }

    public static Date nowAsDate() {
        return new Date();
    }

    public static String toString(Long time) {
        if (Objects.isNull(time)) {
            return "";
        }
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Saigon"));
        cal.setTime(new Date(time));
        return String.format("Ngày %s Tháng %s Năm %s", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
    }

    // Serve for Statistical
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
        return String.format("Ngày %s Tháng %s Năm %s", day, month + 1, year);
    }

}