package org.heal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A general utility class for dealing with dates.
 */
public class DateTools {

    /**
     * @param dateString A String that may or may not represent a
     * 		{@link Date} that can be parsed with the given format.
     * @param format A format to use to check if the String can
     * 		be parsed into a {@link Date}.
     * @return <code>true</code> if a {@link Date} can be parsed from
     * 		the String in the given format, or <code>false</code>
     * 		otherwise.
     * @see #parse(String dateString, String format)
     */
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
