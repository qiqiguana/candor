package bible.util;

import java.io.*;
import java.text.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Message logger
 *
 * @author  Umesh Berry
 * @version $Id: Logger.java,v 1.3 2001/02/21 01:58:33 jstauffe Exp $
 */
public class Logger {

    public Logger() {
        this("Error");
    }

    public Logger(String path) {
        this(path, false);
    }

    /**
     * Logs messages to path/year/month/date/(time when this
     * Logger was created).log. That file will be off the directory that this
     * Logger was inititalized with.
     */
    public Logger(String path, boolean captureConsole) {
        try {
            if(!path.endsWith("/") && !path.endsWith("\\")) {
                path += "/";
            }
            Date time = new Date();
            File dir = new File("bible/logs/" + path + FileDateFormat.format(time));
            dir.mkdirs();
            String logFileBegin = dir + "/" + FileTimeFormat.format(time);
            String logFileEng = ".log";
            File logFile = new File(logFileBegin + logFileEng);
            for(int index = 2; logFile.exists(); index++) {//Find a unique name
                logFile = new File(logFileBegin + "-" + index + logFileEng);
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(logFile));
            pw = new PrintWriter(bos);
            if(captureConsole) {
                PrintStream ps = new PrintStream(bos);
                System.setOut(ps);
                System.setErr(ps);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Useful when the console is captured
     * @author James Stauffer
     */
    public void flush() {
        pw.flush();
    }

    public void log(String message) {
        if (pw != null) {
            pw.println(message);
            pw.flush();
        }
    }

    public void logln() {
        if (pw != null) {
            pw.println();
            pw.flush();
        }
    }

    public void log(Throwable e) {
        if (pw != null) {
            pw.print("Stack trace: ");
            e.printStackTrace(pw);
            pw.flush();
        }
    }

    public void close() {
        if (pw != null) {
            pw.close();
        }
    }

    public static void Log(String message) {
        Logger logger = new Logger("Info");
        logger.log(message);
        logger.close();
    }

    public static void Log(Throwable e, String extraInfo)  {
        Logger logger = new Logger("Error");
        if (extraInfo != null) {
            logger.log(extraInfo);
        }
        logger.log(e);
        logger.close();
    }

    public static void Log(Throwable e, javax.servlet.http.HttpServletRequest request)  {
        Logger logger = new Logger("ServletError");
        logger.log("Parameters");
        for(Enumeration parameterNames = request.getParameterNames(); parameterNames.hasMoreElements();) {
            String paramName = (String)parameterNames.nextElement();
            logger.log(paramName + ":");
            String[] paramValues = request.getParameterValues(paramName);
            for(int index = 0; index < paramValues.length; index++) {
                logger.log("\t" + paramValues[index]);
            }
        }
        logger.logln();
        logger.log("Headers");
        for(Enumeration headerNames = request.getHeaderNames(); headerNames.hasMoreElements();) {
            String headerName = (String)headerNames.nextElement();
            logger.log(headerName + ":" + request.getHeader(headerName));
        }
        logger.logln();
        logger.log(e);
        logger.close();
    }

    public static void Log(Throwable e)  {
        Log(e, (String)null);
    }

    public static String FormatDate(long time) {
        return LongEntryDateFormat.format(new Date(time));
    }

    protected void finalize() throws Throwable {
        close();
    }

    private PrintWriter pw;
    private static DateFormat LongEntryDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm:ss.SSSS z");//Ex: Fri Oct 01, 1999 07:10:34.372 CDT
    private static DateFormat ShortEntryDateFormat = new SimpleDateFormat("[HH:mm:ss.S] : ");
    private static DateFormat FileDateFormat = new SimpleDateFormat("yyyy/MM-MMM/dd-EEE");//Ex: 2001/01-Jan/12-Fri
    private static DateFormat FileTimeFormat = new SimpleDateFormat("HH-mm-ss-SSSS");

    static {
        FileTimeFormat.setTimeZone(TimeZone.getDefault());

    }
}

