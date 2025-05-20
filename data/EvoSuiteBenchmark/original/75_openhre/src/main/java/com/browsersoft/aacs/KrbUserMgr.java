/*
 *   CVS $Id: KrbUserMgr.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
 * 
 *   ====================================================================
 *                 Open Source Health Records Exchange
 *   ====================================================================
 *
 *   Copyright (C) 2005 Browsersoft Inc.
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

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 * @author gsharma
 *
 * KrbUserMgr authenticates the user using Kerberos v5 authentication and uses the user's credentials to
 * fetch other user information from a persistant storage based on LDAP.
 */

public class KrbUserMgr implements UserMgr {

	private static org.apache.log4j.Logger cat = org.apache.log4j.Logger.getLogger(KrbUserMgr.class.getName());

    private User user;
    private LoginContext lctx = null;
    
    private static Properties props = new Properties();
    private static String configFile = "/KrbLdap";
    
    static {
 	    Login.loadProperties(props, configFile);
    }
    
    /**
     * Public default constructor
     */
    public KrbUserMgr() {
    	
    }
    
    /**
     * Kerberos Authentication
    */
    
    public boolean checkSecret(String userid, String password, String authType) {
        if (password == null) return false;
        
        if (authType.equals("krb5")) {
        	
            // strip only the unique username from the userid
            int ind = userid.indexOf("@");
            String tmp_userid = "";
        	if (ind > 0)
        	    tmp_userid = userid.substring(0,ind);
        	
        	user = new User();
        	user.setUserId(userid);
        	String jaaslogin = props.getProperty("jaaslogin", "AACSLogin");

        	cat.info("Starting Kerberos Authentication");
        	// Kerberos Authentication
    		PassiveCallbackHandler cbh = new PassiveCallbackHandler(tmp_userid, password);
    		
    	    try {
    	       lctx = new LoginContext(jaaslogin, cbh);
    	    } catch(LoginException le) {
    	    	cat.warn("LoginContext cannot be created. "+ le.getMessage());
    	    	return false;
    	    } catch(SecurityException se) {
    	      cat.warn("LoginContext cannot be created. "+ se.getMessage());
    	    }
    	    try {
    	    	
    	    	lctx.login();
    	    
    	    } catch (AccountExpiredException aee) {

    			cat.warn("Your account has expired.  " +
    					"Please notify your administrator.");
    			return false;

    		} catch (CredentialExpiredException cee) {

    			cat.warn("Your credentials have expired.");
    			return false;
    	    
    		} catch(LoginException le) {
    		     System.out.println("Authentication failed. " + le.getMessage());
    		     return false;
    	    
    		} catch (Exception e) {

    			cat.warn("Unexpected Exception - unable to continue");
    			e.printStackTrace();
    			return false;
    		}
    		
    	    cat.info("Authentication succeeded.\n");
    	    
    	    Subject subject = lctx.getSubject();
    	    Set princs = subject.getPrincipals();
    	    Iterator princs_iter = princs.iterator();
    	    while( princs_iter.hasNext())
    	    {
    	    	cat.debug("Principal: " + princs_iter.next());
    	    }
    	    
    	    LdapFetch action = new LdapFetch(subject, user);
    	    Subject.doAsPrivileged(subject, action, null);   	    
    	    return true;
    	    
        }
        
        else
        	return false;
                
    }
    
    public User getUser()
    {
    	return user;
    }
    
    public User getUser(String userid)
    {
    	return user;
    }
    
    public void logOut() {
    	try {
    	    lctx.logout();
    	    user.getLdapContext().close();
	    } catch(LoginException le) {
		     System.out.println("Authentication failed. " + le.getMessage());
	   
		} catch (Exception e) {
	
			cat.warn("Unexpected Exception - unable to continue");
			e.printStackTrace();
		}
    }

}
