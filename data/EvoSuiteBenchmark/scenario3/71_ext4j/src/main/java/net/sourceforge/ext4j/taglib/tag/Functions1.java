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
     * Add 's or ' at the end of the text, for example "James'" for "James" or "Daniel's" for "Daniel"
     *
     * @param pText the text
     * @return the text with 's or '
     */
    public static String addS(String pText);
}
