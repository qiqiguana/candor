package net.sourceforge.ext4j.taglib.tag;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.jsp.JspTagException;
import net.sourceforge.ext4j.taglib.bo.IRequest;

/**
 * @author luc
 */
public class Functions {

    /**
     * Format a date with a pattern (using java.text.SimpleDateFormat)
     *
     * @param pDateTime the date
     * @param pPattern the pattern
     * @return the date to display
     */
    public static String dateFormat(Date pDateTime, String pPattern);
}
