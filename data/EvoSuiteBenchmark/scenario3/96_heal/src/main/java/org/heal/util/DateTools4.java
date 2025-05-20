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
     * 		{@link Date} that can be parsed.
     * @return <code>true</code> if a {@link Date} can be parsed from
     * 		the String, or <code>false</code> otherwise.
     * @see #parse(String dateString)
     */
    public static boolean isValidDate(String dateString);
}
