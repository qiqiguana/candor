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

    public static String addS(String pText) {
        if (pText == null || pText.trim().equals(""))
            return "";
        if (pText.charAt(pText.length() - 1) == 's')
            return pText + "'";
        else
            return pText + "'s";
    }
}
