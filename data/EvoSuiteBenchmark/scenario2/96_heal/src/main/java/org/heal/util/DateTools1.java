package org.heal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A general utility class for dealing with dates.
 */
public class DateTools {

    /**
     * @param date A {@link Date} to format.
     * @param format A format (corresponding to {@link SimpleDateFormat}'s syntax)
     * 		to convert the date to.
     * @return A String representation of the date parameter in the
     * 		specified format, or null if the given date is null.
     */
    public static String format(Date date, String format) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
