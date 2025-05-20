package org.heal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A general utility class for dealing with dates.
 */
public class DateTools {

    public static final String DEFAULT_DATE_FORMAT = new String("yyyy-MM-dd");

    public static final String LONG_DATE_FORMAT = new String("yyyy-MM-dd HH:mm:ss");

    private static final String[] POSSIBLE_DATE_FORMATS = new String[] { DEFAULT_DATE_FORMAT, LONG_DATE_FORMAT, "MM/dd/yyyy", "MM-dd-yyyy", "yyyy" };

    private DateTools() {
    }

    /**
     * @param date A {@link Date} to format.
     * @return A String representation of the date parameter in
     * 		the {@link #DEFAULT_DATE_FORMAT default format}.
     */
    public static String format(Date date);

    /**
     * @param date A {@link Date} to format.
     * @param format A format (corresponding to {@link SimpleDateFormat}'s syntax)
     * 		to convert the date to.
     * @return A String representation of the date parameter in the
     * 		specified format, or null if the given date is null.
     */
    public static String format(Date date, String format);

    /**
     * @param dateString A String to parse into a {@link Date} object.
     * @return A {@link Date} representation of the String when possible,
     * 		or <code>null</code> if the date cannot be parsed.
     */
    public static Date parse(String dateString);

    /**
     * @param dateString A String to parse into a {@link Date} object.
     * @param format A format to use to parse the date parameter with.
     * @return A {@link Date} representation of the String when possible,
     * 		or <code>null</code> if the date cannot be parsed with the
     * 		given format.
     */
    public static Date parse(String dateString, String format);

    /**
     * @param dateString A String that may or may not represent a
     * 		{@link Date} that can be parsed.
     * @return <code>true</code> if a {@link Date} can be parsed from
     * 		the String, or <code>false</code> otherwise.
     * @see #parse(String dateString)
     */
    public static boolean isValidDate(String dateString);

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
    public static boolean isValidDate(String dateString, String format);
}
