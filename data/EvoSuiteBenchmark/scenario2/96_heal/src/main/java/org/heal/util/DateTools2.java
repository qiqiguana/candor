package org.heal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A general utility class for dealing with dates.
 */
public class DateTools {

    /**
     * @param dateString A String to parse into a {@link Date} object.
     * @return A {@link Date} representation of the String when possible,
     * 		or <code>null</code> if the date cannot be parsed.
     */
    public static Date parse(String dateString) {
        Date ret = null;
        if (null != dateString) {
            boolean match = false;
            SimpleDateFormat formatter = new SimpleDateFormat();
            formatter.setLenient(false);
            for (int i = 0; i < POSSIBLE_DATE_FORMATS.length && !match; ++i) {
                formatter.applyPattern(POSSIBLE_DATE_FORMATS[i]);
                try {
                    ret = formatter.parse(dateString);
                    match = true;
                } catch (ParseException e) {
                    // do nothing
                }
            }
        }
        return ret;
    }
}
