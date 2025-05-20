package org.heal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A general utility class for dealing with dates.
 */
public class DateTools {

    public static boolean isValidDate(String dateString, String format) {
        boolean match = false;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        try {
            formatter.parse(dateString);
            match = true;
        } catch (ParseException e) {
            // do nothing
        }
        return match;
    }
}
