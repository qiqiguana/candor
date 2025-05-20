/*
 * Base.java
 * Copyright (C) 1997. All rights reserved.
 * St. Paul Software, St. Paul, MN, USA
 */

package bible.servlet;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import bible.util.*;

/**
 * All servlets should extend this servlet so that connections can be tracked(which should happen automatically).
 *
 * To extend this class, use something like the following:
 * <PRE>
 *   private static Queue ConnectionTimes = new Queue();
 *
 *   protected Queue getConnections() {
 *       return ConnectionTimes;
 *   }
 *
 *   protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
 *       //work done here
 *   }
 * </PRE>
 *
 * @author  James Stauffer
 * @version $Id: Base.java,v 1.1.1.1 2001/02/10 05:46:07 jstauffe Exp $
 */
public abstract class Base extends HttpServlet {
    
    public Vector getServletConnections() {
        return getConnections().getObjects();
    }
    
    public int getPeakConnections() {
        return getConnections().getPeakNumberItems();
    }
    
    public static Options GetOptions() {
        return options;
    }
    
    public static void Log(Exception e) {
        Log(e, "");
    }
    
    public static void Log(Exception e, String user) {
        Logger.Log(e, user);
    }
    
    public static void LogMessage(String message) {
        Logger.Log(message);
    }
    
    public static String GetHostName() {
        return HostName;
    }
    /**
     * This method is called by an HTTP GET servlet request.
     *
     * @author  James Stauffer
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        doGetPost(request, response, true);
    }
    
    /**
     * This method is called by an HTTP POST servlet request.
     *
     * @author  James Stauffer
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        doGetPost(request, response, false);
    }
    
    /**
     * This method is called by an HTTP GET or POST servlet request.
     *
     * @author  James Stauffer
     */
    protected void doGetPost(HttpServletRequest request, HttpServletResponse response, boolean get)
    throws ServletException, java.io.IOException {
        if(StandardOutput != null) {
            StandardOutput.flush();
        }
        ServletConnection conn = new ServletConnection(request, response);
        bible.util.Queue q = getConnections();
        if(options.getEnabled() || conn.isBypassUser()) {
            
            q.enqueue(conn);
            try {// Process the normal request
                if(get) {
                    get(conn);//******************** Do the Custom Work
                } else {
                    post(conn);//******************** Do the Custom Work
                }
                conn.logConnectionClose(false);
            } catch(Throwable e) {
                try {
                    conn.log(e);//Displays an error page.
                } finally {
                    conn.logConnectionClose(true);
                }
                if(e instanceof Error) {
                    throw (Error)e;
                }
            } finally {
                try {
                    conn.flushResponse();
                } finally {
                    int removals = q.remove(conn);
                    if(removals != 1) {
                        Log(new Exception(getClass().getName() + " removed " + removals  + " copies of " + conn));
                    }
                }
                System.gc();//Suggest garbage collection
            }
        } else {
            try {
                if(q.getNumberItems() == 0) {
                    EmailNotify();
                }
                conn.displayOfflineMessage();
                conn.flushResponse();
                conn.logConnectionClose(false);
                System.gc();//Suggest garbage collection
            } catch(Exception e) {
                conn.logConnectionClose(true);
            }
        }
        if(StandardOutput != null) {
            StandardOutput.flush();
        }
    }
    
    protected static Base[] GetServlets() {
        if(Servlets == null) {
            Servlets = new Base[]{};
        }
        return Servlets;
    }
    
    /**
     *
     */
    protected static void EmailNotify() {
        if(!options.getEnabled()) {
            // First check if all the servlets have no connections
            Base[] servlets = GetServlets();
            for(int index = 0; index < servlets.length; index++) {
                if(servlets[index].getConnections().getNumberItems() > 0) {
                    return;// Still some connections to close, so abort email.
                }
            }
            
            String webServer = "webecServer";
            try {
                webServer = InetAddress.getLocalHost().getHostName();
            } catch(java.net.UnknownHostException e) {
                Log(e);
            }
            
/*            //Then send the email
            try {
                Message notificationEmail = new Message(options.getMailServerIP());
                notificationEmail.setFrom("Servlets@" + webServer);// Comes from Properties
                notificationEmail.startEmailSession();
                notificationEmail.setRecipients(options.getNotificationEmail());
                notificationEmail.setSubject("Connections to " + webServer + " completed.");
                notificationEmail.setMessageBody("All connections to sps.webec.server.servlets.* on " + webServer
                + " have been been completed.");
                if(notificationEmail.send() != 0) {
                    LogMessage("Base.EmailNotify mail error Message=" + notificationEmail);
                }
                
            } catch(IOException e) {
                LogException(e);
            }
*/        }
    }
    
    /**
     *
     */
    protected static Date GetStartTime() {
        return StartTime;
    }
    
    /**
     * Override this method(not doGet).
     */
    protected void get(ServletConnection conn) throws ServletException, java.io.IOException {}
    /**
     * Override this method(not doPost).
     */
    protected void post(ServletConnection conn) throws ServletException, java.io.IOException {}
    
    /**
     * This should return a static Queue.
     */
    protected abstract bible.util.Queue getConnections();
    
    private static Options options;
    private static Base[] Servlets;
    private static Logger StandardOutput;
    private static String HostName = "unknown";
    
    /**
     * The time that the servlets where first used since the servlet engine was loaged last.
     */
    private static Date StartTime;
    
    static {
        try {
            options = new Options();
            StartTime = new Date();
            if(options.getLogStandardOutput()) {
                StandardOutput = new Logger("Output/", true);
            }
        } catch(Exception e) {
            Log(e, "bible.server.servlet.Base.static");
        }
        try {
            HostName = java.net.InetAddress.getLocalHost().toString();
        } catch(Exception e) {
            HostName += " (" + e.getMessage() + ")";
        }
    }

    /**
     * ****************************************************************************************
     *  Options member class handles load and storage of Application.properties.
     */
    public static class Options {
        //  Constructors.
        
        protected Options() {
            try {
                loadProperties();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //  Public methods.
        public String getMailServerIP() {
            return mailServerIP;
        }
        
        public String getNotificationEmail() {
            return notificationEmail;
        }
        
        public boolean getEnabled() {
            return enableApplications;
        }
        
        public String getEnabledString() {
            return String.valueOf(getEnabled());
        }
        
        public String[] getBypassAccounts() {
            return bypassAccounts;
        }
        
        public String getBypassAccountsString() {
            return Util.ToString(getBypassAccounts(), BYPASS_SEPARATOR);
        }
        
        public boolean getLogStandardOutput() {
            return logStandardOutput;
        }

        //The following set methods should only be used by ApplicationManager
        public void setMailServerIP(String IP) {
            mailServerIP = IP;
        }
        
        public void setNotificationEmail(String email) {
            notificationEmail = email;
        }
        
        public void setEnabled(String status) {
            setEnabled(new Boolean(status).booleanValue());
        }
        
        public void setEnabled(boolean status) {
            enableApplications = status;
        }
        
        public void setBypassAccounts(String logins) {
            setBypassAccounts(Util.ToStringArray(logins, BYPASS_SEPARATOR));
        }
        
        private void setBypassAccounts(String[] logins) {
            bypassAccounts = logins;
        }
        
        public void setLogStandardOutput(String status) {
            setLogStandardOutput(new Boolean(status).booleanValue());
        }
        
        public void setLogStandardOutput(boolean status) {
            logStandardOutput = status;
        }

        //  Private properties.
        private String   mailServerIP = "localhost";
        private String   notificationEmail = "Stauffer_James@yahoo.com";
        private boolean  enableApplications = true;
        private String[] bypassAccounts = new String[0];
        private boolean  logStandardOutput = false;

        private static final File PROPERTIES_FILE = new File("bible/properties/Application.properties");
        public static final String MAIL_IP_KEY   = "MailServer";
        public static final String EMAIL_KEY     = "Email";
        public static final String ENABLED_KEY   = "Enabled";
        public static final String BYPASS_KEY    = "BypassAccounts";
        public static final String BYPASS_SEPARATOR = ",";
        public static final String LOG_STANDARD_OUTPUT = "LogStandardOutput";

        //  Private methods.
        private void loadProperties() throws IOException {
            if (PROPERTIES_FILE.exists()) {
                Properties properties = new Properties();
                FileInputStream fis = new FileInputStream(PROPERTIES_FILE);
                properties.load(fis);
                
                setMailServerIP(     properties.getProperty(MAIL_IP_KEY));
                setNotificationEmail(properties.getProperty(EMAIL_KEY));
                setEnabled(          properties.getProperty(ENABLED_KEY));
                setBypassAccounts(   properties.getProperty(BYPASS_KEY));
                setLogStandardOutput(properties.getProperty(LOG_STANDARD_OUTPUT));

                fis.close();
            }
        }
        
        public void saveProperties() throws IOException {
            if (!PROPERTIES_FILE.exists() || PROPERTIES_FILE.canWrite()) {
                Properties properties = new Properties();
                FileOutputStream fos = new FileOutputStream(PROPERTIES_FILE);
                
                properties.put(MAIL_IP_KEY,   getMailServerIP());
                properties.put(EMAIL_KEY,     getNotificationEmail());
                properties.put(ENABLED_KEY,   getEnabledString());
                properties.put(BYPASS_KEY,    getBypassAccountsString());

                properties.save(fos, "Application Properties saved by bible.servlet.Base.Options.saveProperties() on " 
                    + new Date().toString());
                fos.close();
            } else {
                throw new IOException("Unable to write to " + PROPERTIES_FILE.toString());
            }
        }
    }// of Options
}

