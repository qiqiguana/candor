package bible.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.*;
import javax.servlet.ServletRequest;

import bible.util.Logger;
import bible.util.Util;

/**
 * @author  James Stauffer
 * @version $Id: ServletConnection.java,v 1.1.1.1 2001/02/10 05:46:08 jstauffe Exp $
 */
public class ServletConnection {
    
    public ServletConnection(HttpServletRequest request, HttpServletResponse response) {
        this(request, response, System.currentTimeMillis());
    }
    
    public ServletConnection(HttpServletRequest request, HttpServletResponse response, long startTime) {
        this.request = request;
        this.response = response;
        this.username = ServletUtil.GetUsername(request);
        this.startTime = startTime;
        UrlRequestLog.logStart(getUsername(), getStartTime(), getRemoteHost(), request.getRequestURI());
        try {
            responseSent = false;
            sos = new HTMLOutputStream(response);
        } catch(IOException e) {
            log(e);
        }
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    public String getQueryString() {
        return request.getQueryString();
    }
    
    public String getRemoteHost() {
        return request.getRemoteHost();
    }
    
    public void log(Throwable e) {
        log(e, "Unknown", true);
    }
    
    public void log(Throwable e, String servlet) {
        log(e, servlet, true);
    }
    
    /**
     * Doesn't try to send the content already generated.
     */
    public void log(Throwable e, String servlet, boolean displayErrorMessage) {//, String member
        Logger.Log(e, username);
        if(displayErrorMessage) {
            displayError();
        }
    }
    
    public void log(String message) {
        Logger.Log(message);
    }
    
    public void flushResponse() throws IOException {
        if(!responseSent) {
            responseSent = true;
        } else {
            log(new IOException("Warning: response already sent."));
        }
        if(redirected) {
            resetOutputStream();
        } else {
            sos.flush();//Ideally this should only occur if(!responseSent)
        }
    }
    
    /**
     * Doesn't try to send the content already generated.
     */
    public void displayErrorMessage(String message) {
        try {
            sos = new HTMLOutputStream(response);
            displayMessagePage(message);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public void displayMessagePage(String message) {
        sendRedirect("/jsp/Message.jsp?s=" + message);
    }
    
    public void sendRedirect(String location) {
        try {
            response.sendRedirect(location);
        } catch(IOException e) {
            Logger.Log(e, username);
        }
        redirected = true;
    }
    
    public void setRedirected() {
        redirected = true;
    }
    
    public boolean isRedirected() {
        return redirected;
    }
    
    public String getUsername() {
        return username;
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }
    
    public HttpServletResponse getResponse() {
        return response;
    }

    public String getRequestParameter(String key) {
        return request.getParameter(key);
    }
    
    public String[] getRequestParameters(String key) {
        return request.getParameterValues(key);
    }
    
    public void logConnectionClose(boolean error) {
        UrlRequestLog.logEnd(getUsername(), getStartTime(), System.currentTimeMillis(), error);
    }
    
    public void redirectWithout(String parameter) throws IOException {
        sendRedirect(getURLwithout(parameter));
    }
    
    public void displayOfflineMessage() {
        sendRedirect("/jsp/Offline.jsp");
    }
    
    public void print(String line) {
        sos.print(line);
    }
    
    public void println(String line) {
        sos.println(line);
    }

    public PrintWriter getPW() {
        return sos.getPW();
    }

    public InputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    public boolean isBypassUser() {
        String[] bypassers = Base.GetOptions().getBypassAccounts();
        for(int index = 0; index < bypassers.length; index++) {
            if(bypassers[index].equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns a string describing the current log.
     */
    public void displayError() {
        sendRedirect("/jsp/Error.jsp?time=" + System.currentTimeMillis());
    }
    
    public void resetOutputStream() throws IOException {
        int size = sos.getSize();
        if(size > 0) {
            String contents = sos.reset();
            log(Util.ToString(new IOException("Throwing away " + size + " bytes in " + sos)) + "Contents:" + contents);
        }
    }

    /**
     * Very strict equal test.
     */
    public boolean equals(Object other) {
        return other == this;
    }
    
    public String toString() {
        return getClass().getName() + ":[startTime=" + startTime
        + ", username=" + username
        + ", " + (responseSent ? "" : "!") + "responseSent"
        + ", " + (redirected ? "" : "!") + "redirected"
        + "]";
    }

    private String getURLwithout(String parameter) {
        String startUrl = request.getRequestURI();
        String endUrl = startUrl;
        int startIndex = startUrl.indexOf(parameter);
        if(startIndex >= 0) {// found
            endUrl = startUrl.substring(0, startIndex);// get part before
            
            int endIndex = startUrl.indexOf(QUERY_SEP, startIndex + parameter.length());//Look for anything after
            if(endIndex >= 0) {// basically >= startIndex + parameter.length()
                endUrl += startUrl.substring(endIndex + 1);// add part after
                
            }
            while(endUrl.endsWith(QUERY_SEP)) {// Remove & if it is on the end
                endUrl = endUrl.substring(0, endUrl.length() - QUERY_SEP.length());
            }
            if(endUrl.endsWith(QUERY_CHAR)) {// Remove ? if it is on the end
                endUrl = endUrl.substring(0, endUrl.length() - QUERY_CHAR.length());
            }
        }
        return endUrl;
    }
    
    private String getURLwith(String parameter) {
        String url = request.getRequestURI();
        
        int queryCharIndex = url.indexOf(QUERY_CHAR);
        if(queryCharIndex >= 0) {
            if(queryCharIndex != (url.length() - 1)) {// Not last character(other stuff in query)
                url += QUERY_SEP;
            }
        } else {
            url += QUERY_CHAR;
        }
        
        return url + parameter;
    }
    
    public String getFullUrl(){
        String query = request.getQueryString();
        String url = request.getRequestURI();
        if (query != null)  {
            if (url.indexOf(QUERY_CHAR) == -1)  { // Jsdk2.0 case
                url = url + QUERY_CHAR + query;
            }
        }
        return url;
    }
    
    private final static String QUERY_CHAR = "?";
    private final static String QUERY_SEP  = "&";
    private boolean              responseSent;//Only used for debugging
    private boolean              redirected = false;
    private long                 startTime;
    private HTMLOutputStream     sos;
    private HttpServletRequest   request;
    private HttpServletResponse  response;
    private String               username;
    private static URLRequestLog UrlRequestLog = new URLRequestLog();
}

class URLRequestLog {
    public URLRequestLog() {
        logger = new Logger("connection/");
    }
    
    public void logStart(String username, long startTime, String remoteHost, String URL) {
        logger.log(username + "\t on \t" + Logger.FormatDate(startTime) + "\t from host \t" + remoteHost
            + "\t for \t" + URL);
    }
    
    public void logEnd(String username, long startTime, long endTime, boolean error) {
        logger.log(username + "\t on \t" + Logger.FormatDate(startTime) + "\t closed after \t"
            + (endTime - startTime) + "\t milliseconds" + (error ? "\t with error" :""));
    }
    
    public void logMessage(String username, long startTime, String message) {
        logger.log(username + "\t on \t" + Logger.FormatDate(startTime) + "\t " + message);
    }
    
    private Logger logger;
}
