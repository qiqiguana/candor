package com.imsmart.misc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MDate {

    public static final String DATE_WITH_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_ONLY = "yyyy_MM_dd";
    public static final String TIME_ONLY = "HH:mm:ss";
    public static final String YYYYMMDD = "yyyyMMDD";
    public static final String MM_DD_YYYY = "MM/DD/yyyy";

    public static String now(String dateTimeFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
        return sdf.format(cal.getTime());

    }

    public static String getFormattedDate(Date date, String dateTimeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
        return sdf.format(date);
    }

    public static Date parseDate(String strDate, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(strDate);
        return date;
    }
}
