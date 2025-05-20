package org.heal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A general utility class for dealing with dates.
 */
public class DateTools {

    public static boolean isValidDate(String dateString) {
        boolean match = false;
        if (null != dateString) {
            SimpleDateFormat formatter = new SimpleDateFormat();
            formatter.setLenient(false);
            for (int i = 0; i < POSSIBLE_DATE_FORMATS.length && !match; ++i) {
                formatter.applyPattern(POSSIBLE_DATE_FORMATS[i]);
                try {
                    formatter.parse(dateString);
                    match = true;
                } catch (ParseException e) {
                    // do nothing
                }
            }
        }
        return match;
    }
}
