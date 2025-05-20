/*
 *   CVS $Id: Login.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
 * 
 *   ====================================================================
 *                 Open Source Health Records Exchange
 *   ====================================================================
 *
 *   Copyright (C) 2005, 2006 Browsersoft Inc.
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License, version 2, 
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   The GNU General Public License is available at
 *   http://www.fsf.org/licensing/licenses/gpl.html
 *
 *   Email: info@openhre.org
 *   Web:   http://www.openhre.org
 */
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

/**
 * This class manages authentication and user 
 * attribute retrieval when a user tries to log in.
 */

public class Login {


    //private static String[] names = null;

	//private static String[] mail = null;

    private String username = "";
    private static String organization = "OPENHRE";
    private static String country = "US";
    private String password = "";
    private String userid = "";
    private String ipAddr = "";
   // private static String userdb = "users";
    private static String configFile = "AACS"; //AACS.properties
    private static String authType = "simple";
    private static String xacmlConfig = "";
    private static String securityLog = "";
    private static String securityLogFilePath = "";
    
    private final static String appDateFormat = "MM/dd/yyyy";			// Date format used by OpenHRE
    private final static String ldapGTFormat = "yyyyMMddHHmmssZ";		// LDAP GeneralizedTime Format

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
    	this();
    	this.setUserId(userId);
    }

    /**
     * Construct a Login with a UserId and IP Address
     */
    public Login(String userId, String ipAddr) {
    	this(userId);
    	this.setIpAddr(ipAddr);
    }

    /**
     * @param theConfigFile the properties file
     */
    public static void setConfigFile(String theConfigFile) {
        configFile = theConfigFile;
    }
    /**
     * Load and get the Properties from the Config File
     * @param theConfigFile the properties file
     */
    public static Properties getProps(String theConfigFile) {
    	setConfigFile(theConfigFile);
    	return getProps();
    }
    /**
     * Load and get the Properties from the preset Config File
     */
    public static Properties getProps() {
    	init();
    	return props;
    }
    /**
     * Initialize and load all the users.
     */
    public static String getSecurityLogFilePath() {
        return securityLogFilePath;
    }
    public static void init() {
    	if (props != null) return;
        props = new Properties();
        cat.info("Loading Properties from " + configFile);
    	loadProperties(props, configFile);
    	
        // set the default organization and country
        organization = props.getProperty("organization", "OPENHRE");
        country = props.getProperty("country", "US");
        authType = props.getProperty("AuthType", "simple");
        xacmlConfig = props.getProperty("XACMLConfig", "");

        cat.info("organization is " + organization);
        cat.info("country is " + country);
        cat.info("AuthType is " + authType);
        cat.info("XACMLConfig is " + xacmlConfig);
        
        securityLog = props.getProperty("SecurityLog", "");
        // create security log file if it doesn't exist
        try {
            File file = new File(securityLog);
            securityLogFilePath = file.getAbsolutePath();
            cat.info("Preparing Security Log file " + securityLogFilePath);
        
            // Create file if it does not exist
            file.createNewFile();
            
            // Get the XACML PDPadapter
            pdpadapter = new PDPadapter(xacmlConfig);
            
        } catch (Exception e) {
            cat.fatal("Exception initializing Login manager",e);
            e.printStackTrace();
        }
        
        if (userMgr == null) {
	        if (authType.equals("simple")) {        	
	            userMgr = new SimpleUserMgr(configFile);
	        }
	        else if (authType.equals("krb5")) {        	
	            userMgr = new KrbUserMgr();
	        }
        }
       
    }

    public static void loadProperties(Properties props, String configFile) {
 	   	try {
 	   		props.load(Login.class.getResourceAsStream(configFile+".properties"));
 	   	} catch (Exception e) {
 	   		System.out.println("Could not read "+configFile+".properties");
 	   		e.printStackTrace();
 	   	}
    }


    /** set the username  and get the user corresponding to the resulting dn
     * @param username
     */
    public void setUsername(String username) {
//		System.out.println("setting username: " +username);
//        user = null;
        this.username = username.trim();
        //user = userMgr.getUser(username, organization);
        user.setUserName(username);
        cat.debug("setUserName: "+username);

    }

    /**
     * Get filepath of security log.
     * ASSUMPTION: init() was called (i.e. securityLog was initialized)
     * @return absolute filepath
     */


    /**
     * get the current username  from user if defined
     */
    public String getUsername() {
        if (user != null)
            return user.getUserName();
        else
            return username;
    }
    /**
     * set ipAddr for login
     * @param ipAddr
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
        if (user != null)
            user.setIpAddr(ipAddr);
    }
    /**
     * get ipAddress of user if defined
     * return String
     */
    public String getIpAddr() {
        if (user != null)
            return user.getIpAddr();
        else
            return ipAddr;
    }
    /**
     * set the password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     *  set the email address
     * @param email address
     */
/*    public void setEmail(String email) {
//		System.out.println("setting email: "+email);
        this.email = email;
        if (user != null) user.setUserId(email);
    }
*/
    
    /**
     * set the active user from the userId
     * @param userId
     */
    public void setUserId(String usrId) {
    	userid = usrId;
        cat.debug("setUserId: "+userid);

    }
    

    /**
     * get the current User
     */
    public User getUser() {
        return user;
    }
    

    /**
     * get the current User ID
     */
    public String getUserId() {
        //getUser();
        if (user != null)
            return user.getUserId();
        else
            return null;
    }
    

    /**
     * get the current email address
     */
    public String getEmail() {
        //getUser();
        if (user != null)
            return user.getUserId();
        else
            return null;
    }
    
    /**
     * get the current LdapService
     */
    public LdapService getLdapService() {
        if (lservice == null)
        	return (lservice = new LdapService(user.getLdapContext()));
        else
        	return lservice;
    }

        /**
     * get the User from one of two ways. Return the current user if it is already defined.
     * First see if there is a defined email address (userId) and get the user directly
     * If not, then check the organization table and the the associated table of username
     * using the username and organization (country is defaulted to "US").
     */
/*    public User getUser() {
        //   try {
        // makeDN();
        if (user != null) return user;
        if (userid != null || userid != "")
            user = userMgr.getUser(userid);
        else
            user = userMgr.getUser(username, organization, userid);
        cat.debug("getUser: "+user);
        return user;

    }
*/
    /**
     * return the role of the current user.
     * @return String containing the Role of the current user
     /
    public String getRole() {
        if (user != null)
            return user.getRole();
        else
            return null;
    }

    /**
     * get the Distinguished Name of the user
     * @return String containing the distinguished name
     /
    public String getDN() {
        if (user != null)
            return user.getDN();
        else
            return null;
    }
    */
    
    /**
     * Authorize a User without checking the password.
     * Note that this should only be called for Users
     * that are already Authenticated.
     * @return user
     */
    public User authorize() 
			throws LoginException {
        user = null;
        
        if (userMgr == null) 
        	throw new LoginException("UserMgr undefined");
        if (password == null)
            throw new LoginException("Password not supplied");

    	user = userMgr.getUser(this.userid);
    	user.setIpAddr(this.ipAddr);
    	
		// compare password validity start date with current date
		Date today = new Date();
		Date pwvd = null; 
		if ((user.getPassvalidstart() != null)
		&& (!user.getPassvalidstart().equals(""))) {					
		    pwvd = convertDateFromUTC(user.getPassvalidstart());
			if (!dateCompare(pwvd, today)) {
			    log(user, "Not allowed to login currently.");
			    user = null;
			    throw new LoginException( 
			    	"Not allowed to login currently.");
			}
		}

		// check if user needs to change password before proceeding
		user.setPasschange(false);
		if ((user.getPassvalidstart() != null) 
		&&  (user.getPassrenewal() != null)	
		&&  (!user.getPassvalidstart().equals(""))
		&&  (!user.getPassrenewal().equals(""))
		&&  (!user.getPassrenewal().equals("-1"))) {					
			Calendar c1 = Calendar.getInstance(); 
		    c1.setTime(pwvd);
		    c1.add(Calendar.DATE, Integer.parseInt(user.getPassrenewal()));
		    if (dateCompare(c1.getTime(), today)) {
		    	user.setPasschange(true);
		    }
		}
		
		// Performing IP based filtering and active groups determination 
		// based on current location.
		// Make sure that AuthType=krb5 and IPfiltering=yes in the properties file.
		if ((user.getAllowedips() != null)
		&&  (props.getProperty("AuthType", "krb5").equals("krb5")) 
		&&  (props.getProperty("IPfiltering", "yes").equals("yes"))) {

		    int currentip = getIpInteger(user.getIpAddr());
		    
		    // IP based filtering
		    cat.info("Performing IP filtering for " + getUserId());
		    			    
		    boolean ipcheck_result = ipRangeCheck(user.getAllowedips(), currentip);
		    					
			if (ipcheck_result == false) {
			    log(user, "Not authorized from the current IP address.");
			    throw new LoginException(
			    	"Not authorized from the current IP address.");
			}
			
			// Active groups determination
			cat.info("Determining active groups for " + getUserId());
			boolean faccheck_result;
			
			// get all groups from LDAPservice
			Set usergrps = user.getGroups();
			if (usergrps != null
			&&  usergrps.size() > 0) {
				// Setup the interface to the LDAP server
		        getLdapService();
		        //session.setAttribute("ldapservice", lservice);	
		        
				Iterator grpit = usergrps.iterator();	
				
			    while (grpit.hasNext()) {
			        // Fetch each group's details from LDAP
				    Group grp = (Group)lservice.getGroup(grpit.next().toString());
				    
				    faccheck_result = ipRangeCheck(grp.getAllowedips(), currentip);
				    					
					if (faccheck_result == true) {
					    log(user, "Active group: " + grp.getCn());
					    user.addActivegroup(grp.getCn());
					}						    
				}
			    if (user.getActivegroups() == null || user.getActivegroups().size() == 0) {
			        log(user," Accessing from remote location.");
			        user.addActivegroup("remote");
			    }
			}
		} // end IP based filtering and Active groups determination				
		
    	return user;
    }


    /** 
     * Authenticate the user by checking the password
     * and then Authorize them.
     * @return user
     */
    public User authenticate(String userid, 
    						String password, 
    						String ipaddr) 
    		throws LoginException {
    	this.userid = userid;
    	this.password = password;
    	this.ipAddr = ipaddr;
    	
    	return authenticate();
    }

    /** 
     * Authorize a pre-Authenticated User
     * @return user
     */
    public User authorize(String userid, String ipaddr) 
    		throws LoginException {
    	this.userid = userid;
    	this.ipAddr = ipaddr;
    	
    	return authorize();
    }

    /** 
     * Authenticate the user by checking the password
     * and then Authorize them.
     * @return user
     */
    public User authenticate() 
    		throws LoginException {
        
        user = null;
        
        if (userMgr == null) 
        	throw new LoginException("UserMgr undefined");
        if (password == null)
            throw new LoginException("Password not supplied");

        if (!userMgr.checkSecret(userid, password, authType)) {
            cat.warn("Invalid Password! User: " + username + " ,userid: " + userid);
            throw new LoginException("Password invalid");
        }
    	user = userMgr.getUser();
    	user.setIpAddr(this.ipAddr);
    	
    	authorize();
    	
		return user;
    }

    /**
     * check permissions for the current user
     */
    public boolean accessDecision(User usr, String resource, String action) {
    	if ("simple".equals(authType)) {
    		cat.debug("accessDecision() always true for simple authType");
    		return true;
    	}
		if (cat.isDebugEnabled()) {
    		cat.debug("User for accessDecision() is " + usr);
    		cat.debug("resource is " + resource);
    		cat.debug("action is " + action);
        	cat.debug("XACMLConfig is " + xacmlConfig);
    	}
    	if (usr == null) return false;
        
        boolean ac_result = false;
        
        try {
            // create XACML request            
            RequestCtx request =
                new RequestCtx(
                	RequestBuilder.setupSubjects(usr.getUserId(),usr.getRoles(), 
                			usr.getActivegroups() == null
                			? usr.getGroups() : usr.getActivegroups()
                		), 
                		RequestBuilder.setupResource(resource),
                		RequestBuilder.setupAction(action), new HashSet());
            
            // encode the Request and print it to standard out
            request.encode(System.out, new Indenter());
            
            if (pdpadapter.makeDecision(request) == 0)
                ac_result = true;
            else
                ac_result = false;
	        cat.debug("Access Decision: " + ac_result);
        } catch (Exception e) {
            cat.error(e);
            e.printStackTrace();
        }
        
        return ac_result;
        
    }
    
    public void log(User user, String info) {
        FileAppender appender = null;
	    try {
	         appender = new FileAppender(new SimpleLayout(), securityLog, true);
	    } catch(Exception e) {
	        cat.error(e);
	    }
	    cat.addAppender(appender);
	    Level oldlevel = cat.getLevel();
	    cat.setLevel((Level) Level.INFO);
	    Date now = new Date();
	    if (user == null) {
            cat.info(now + " - " + username + " - " + userid +
                 " - " + ipAddr + " -- " + info);
	    } else {
            // new log format (for better parsing in Cocoon)
            cat.info(now + " - " + user.getUserName() + " - " + user.getUserId() +
                 " - " + user.getIpAddr() + " -- " + info);
	    }
	    cat.setLevel(oldlevel);
	    cat.removeAppender(appender);
    }
    

    /**
     * logout the current user
     */
    public void logOut() {
    	userMgr.logOut();
    	user = null;
        password = null;
        username = null;
    }
    
	private Date convertDateFromUTC(String specdatetime) {
	    // Extract from LDAP GeneralizedTime format and Convert date 
	    // and time from UTC time to local time
       TimeZone localtime = TimeZone.getDefault();
       SimpleDateFormat df1 = new SimpleDateFormat(appDateFormat);
       df1.setTimeZone(localtime);

       SimpleDateFormat df2 = new SimpleDateFormat(ldapGTFormat);	       
       Date specdate = null;
       try {
           specdate = df2.parse(specdatetime);
       } catch (ParseException pe){
           pe.printStackTrace();
       }       
       return specdate;	    
	}
	
	private boolean dateCompare(Date date1, Date date2) {
	    Calendar c1 = Calendar.getInstance(); 
	    Calendar c2 = Calendar.getInstance(); 
	    c1.setTime(date1); 
		c2.setTime(date2); 
	    if (c1.before(c2))
	       return true;
	    else
	       return false;
	}
	
	private int getIpInteger(String ip) {
	    StringTokenizer st = new StringTokenizer(ip, ".");
	    int bitposition = 24;
	    int ipnumber = 0;
	    while (st.hasMoreTokens()) {
	        String curtoken = st.nextToken();
	        if (ipnumber == 0)
	            ipnumber = (Integer.parseInt(curtoken) << bitposition);
	        else
	            ipnumber = ipnumber | (Integer.parseInt(curtoken) << bitposition);
	        bitposition -= 8;
	    }
	    return ipnumber;
	}
		
	private boolean ipRangeCheck(Set allowedips, int currentip) {
	    // Check if the current IP address falls between the given range of allowed IP addresses
	    
	    boolean ipmatch = false;
	    
	    if (allowedips == null) {
	        cat.debug("No IP address range specified.");
	        return false;
	    }
	    
	    Set allowed_ips = new HashSet();
	    allowed_ips = (HashSet)allowedips;
		Iterator ipit = allowed_ips.iterator();	
		
	    while (ipit.hasNext()) {
		    int finalip = 0;						
		    int n = 0;			// subnet mask bits
		    
		    // get the IP address and the subnet mask bits (if any)
		    StringTokenizer st = new StringTokenizer(ipit.next().toString(), "/");
		    if (st.hasMoreTokens()) {
		        finalip = getIpInteger(st.nextToken());
		        if (st.hasMoreTokens())
		        	n = Integer.parseInt(st.nextToken());
		    }
		        
		    // Every IP address allowed for the user
		    if (finalip == 0) {
		        cat.info("All IP addresses allowed.");
		        ipmatch = true;
		    }
		    
		    if (n != 0) {
				// compute an n bit subnet mask, all 1s except for last n bits.
				int subnet = ~( ( 1 << n ) - 1 );
									    
			    int t1 = finalip & subnet;
			    int t2 = currentip & subnet;
			    
			    if (t1 == t2) 
			        ipmatch = true;
			    
		    } else {
		        if (finalip == currentip)
			        ipmatch = true;
		    }					        
		}
	    return ipmatch;
	}
	
	/**
	 * Change the User's password
	 * (this should be moved to KrbUserMgr)
	 */
	public void changePassword(String newpass) 
			throws Exception {
        // read the configuration properties
		if (cat.isDebugEnabled()) cat.debug("using props "+props);
		String krbrealm = props.getProperty("krbrealm", "YOUR-KERBEROS-REALM");
		String editkrb = props.getProperty("editkrb", "no");
		String kadminLocal = props.getProperty("kadmin.local", "kadmin.local");
        int exitVal = 0;
        
        String userid = user.getUserId();
        // strip only the unique username from the userid
        int ind = userid.indexOf("@");
    	if (ind > 0)
    	    userid = userid.substring(0,ind);
    	
        cat.debug("editkrb: " + editkrb);
		if (editkrb.toLowerCase().equals("yes")) {
		    
	        // Execute the UNIX command to reset the principal's password in the Kerberos 
		    // database.
	        // Assumption: The web server and the kerberos server are running on the same UNIX
	        // based machine.Otherwise please modify the following according to your settings.
	        				        	
	        String krbprinc = userid + "@" + krbrealm;
		    cat.info("Reseting principal \"" + krbprinc + "\"'s password in " +
		    		"the Kerberos database");
		    Runtime rt = Runtime.getRuntime();
		   	
		    // Create a String array consisting of the command and its arguments
		    String[] cmd = {kadminLocal, "-p " + userid, 
		            "-q \"\"cpw -pw " + newpass + " " + krbprinc + "\"\""};
		    cat.debug("exec'ing Runtime command: " + cmd[0]+' '+cmd[1]+' '+cmd[2]);
		    Process ps = rt.exec(cmd);
		    
			// For printing error messages
			StreamHandler errorHandler = new
				StreamHandler(ps.getErrorStream(), "ERROR");            
            
            // For printing output
			StreamHandler outputHandler = new
				StreamHandler(ps.getInputStream(), "OUTPUT");
                
            // Start printing
            errorHandler.start();
            outputHandler.start();
                                    
            // Print exit value
		    exitVal = ps.waitFor();
		    cat.debug("Kerberos update status: " + exitVal);					    
		}
		
		if (exitVal == 0) {
		    
		    getLdapService();
		    Person person = lservice.getPerson(userid);
		    person.setPasschange("no");
		    // Put current date as password valid start date
		    Date today = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat(ldapGTFormat);	
		    person.setPassvalidstart(sdf.format(today));
		    
			if (lservice.updatePerson(person, person.getDn()) == true) {
			}
		    else
				throw new LoginException(
					"Cannot update your profile. Please contact the administrator!");
	    } else
	    	throw new LoginException(
				"Cannot update your profile. Please contact the administrator!");
	  
	}
	
  /*  /** obtain list of valid users
     * @return String[] list of known users
     /
    public static String[] getNames() {
        if (names == null) {
            if (userMgr == null) init();
            names = userMgr.getUserNames();
        }

        return names;
    }

    /**
     * return known email addresses
     * @return String[] list of known emails
     /
    public static String[] getMail() {
        if (mail == null) {
            if (userMgr == null) init();
            mail = userMgr.getUserIds();
        }
        /*
        {
            if (id == null) init();
            ArrayList v = null;
            try {
                JDBMEnumeration e = id.keys();
                v = new ArrayList();
                while (e.hasMoreElements()) {
                    v.add(e.nextElement());
                }
            } catch (IOException e1) {
                cat.error("getMail: " + e1);
                return new String[0];
            }
            mail = new String[v.size()];
            cat.debug("getMail: "+mail.length+ " elements");
            v.toArray(mail);
        }  /
        return mail;
    }


    /**
     *  set the SimpleUserMgr containing the Users.
     * @param theUserMgr to be used
     /
    public static void setUserMgr(UserMgr theUserMgr) {
        userMgr = theUserMgr;
    }

    /**
     * get the SimpleUserMgr that contains the Users
     * @return SimpleUserMgr
     /
    public static SimpleUserMgr getUserMgr() {
        return userMgr;
    }

    /**
     * get all the users in memory
     * @return String
     /
    public String toString() {
        return userMgr.export();
        /*
        StringBuffer buff = new StringBuffer();
        try {
            JDBMEnumeration c = id.values();
            while (c.hasMoreElements()) {
                buff.append(c.nextElement().toString() + '\n');
            }
        } catch (IOException e) {
            cat.error("getUsers: " + e);
        }
        return buff.toString();
        /
    }

    /**
     * test program
     /
    public static void main(String[] argv) {
        Login login = new Login();
      //  SimpleUserMgr.setConfigFile(argv[0]);
        // SimpleUserMgr usrMgr = new SimpleUserMgr(argv[0]);
        // usrMgr.init();
        login.setConfigFile(argv[0]);
        login.init();
        login.getUserMgr().addUser("cn=Jim Smith, o=LANL, c=US", "Jim Smith", "Smith", "smith@foo.com", "submitter", "junk");
        login.getUserMgr().addUser("dn: cn=Dave Barry, o=UNM, c=US\ncn: Dave Barry\nemail: barry@goofoff.com\nsn: Barry\nuserPassword: stuff\nrole: staff");

        login.setUsername("Dave Barry");
        login.setOrg("UNM");
        login.setUsername("Dave Barry");
        cat.debug(login.getUser());
        login.getUserMgr().delUser("smith@foo.com");
        String[] mail = Login.getMail();
        cat.debug("Mail: "+mail[0]);
        cat.debug(login.getUserMgr().getMailbyOrg("LANL"));
        String[] names = Login.getNames();
        cat.debug("names: "+names[0]);
        System.out.println(login.toString());

    } */
}
