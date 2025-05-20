package org.heal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A general utility class for dealing with dates.
 */
public class DateTools {

    public static String format(Date date) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return formatter.format(date);
    }
}
