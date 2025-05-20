package com.browsersoft.aacs;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import javax.security.auth.login.LoginException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import com.browsersoft.aacs.xacml.PDPadapter;
import com.browsersoft.aacs.xacml.RequestBuilder;
import com.sun.xacml.Indenter;
import com.sun.xacml.ctx.RequestCtx;

public class Login {

    private String username = "";

    private static String organization = "OPENHRE";

    private static String country = "US";

    private String password = "";

    private String userid = "";

    private String ipAddr = "";

    //AACS.properties
    private static String configFile = "AACS";

    private static String authType = "simple";

    private static String xacmlConfig = "";

    private static String securityLog = "";

    private static String securityLogFilePath = "";

    // Date format used by OpenHRE
    private final static String appDateFormat = "MM/dd/yyyy";

    // LDAP GeneralizedTime Format
    private final static String ldapGTFormat = "yyyyMMddHHmmssZ";

    private static Properties props;

    private static UserMgr userMgr;

    private static PDPadapter pdpadapter;

    private User user;

    private LdapService lservice;

    private static Logger cat = Logger.getLogger(Login.class.getName());

    /**
     * Public default constructor
     */
    public Login() {
    }

    /**
     * Construct a Login with a UserId
     */
    public Login(String userId) {
    }

    /**
     * Construct a Login with a UserId and IP Address
     */
    public Login(String userId, String ipAddr) {
    }

    /**
     * @param theConfigFile the properties file
     */
    public static void setConfigFile(String theConfigFile);

    /**
     * Load and get the Properties from the Config File
     * @param theConfigFile the properties file
     */
    public static Properties getProps(String theConfigFile);

    /**
     * Load and get the Properties from the preset Config File
     */
    public static Properties getProps();

    /**
     * Initialize and load all the users.
     */
    public static String getSecurityLogFilePath();

    public static void init();

    public static void loadProperties(Properties props, String configFile);

    /**
     * set the username  and get the user corresponding to the resulting dn
     * @param username
     */
    public void setUsername(String username);

    /**
     * get the current username  from user if defined
     */
    public String getUsername();

    /**
     * set ipAddr for login
     * @param ipAddr
     */
    public void setIpAddr(String ipAddr);

    /**
     * get ipAddress of user if defined
     * return String
     */
    public String getIpAddr();

    /**
     * set the password
     * @param password
     */
    public void setPassword(String password);

    /**
     * set the active user from the userId
     * @param userId
     */
    public void setUserId(String usrId);

    /**
     * get the current User
     */
    public User getUser();

    /**
     * get the current User ID
     */
    public String getUserId();

    /**
     * get the current email address
     */
    public String getEmail();

    /**
     * get the current LdapService
     */
    public LdapService getLdapService();

    /**
     * Authorize a User without checking the password.
     * Note that this should only be called for Users
     * that are already Authenticated.
     * @return user
     */
    public User authorize() throws LoginException;

    /**
     * Authenticate the user by checking the password
     * and then Authorize them.
     * @return user
     */
    public User authenticate(String userid, String password, String ipaddr) throws LoginException;

    /**
     * Authorize a pre-Authenticated User
     * @return user
     */
    public User authorize(String userid, String ipaddr) throws LoginException;

    /**
     * Authenticate the user by checking the password
     * and then Authorize them.
     * @return user
     */
    public User authenticate() throws LoginException;

    /**
     * check permissions for the current user
     */
    public boolean accessDecision(User usr, String resource, String action);

    public void log(User user, String info);

    /**
     * logout the current user
     */
    public void logOut();

    private Date convertDateFromUTC(String specdatetime);

    private boolean dateCompare(Date date1, Date date2);

    private int getIpInteger(String ip);

    private boolean ipRangeCheck(Set allowedips, int currentip);

    /**
     * Change the User's password
     * (this should be moved to KrbUserMgr)
     */
    public void changePassword(String newpass) throws Exception;
}
