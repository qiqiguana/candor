/*
 *   CVS $Id: LdapFetch.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
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

import java.security.PrivilegedAction;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;
import javax.net.ssl.SSLSession;
import javax.security.auth.Subject;

/**
 * @author gsharma
 *
 * LdapFetch is used to retrieve user details from a LDAP server 
 * including his/her roles and priviledges, 
 * for XACML based access control.
 */
public class LdapFetch implements PrivilegedAction {
   
	private Subject subjct;
	private User user;
	private static org.apache.log4j.Logger cat = org.apache.log4j.Logger.getLogger(LdapFetch.class.getName());
	
	private static Properties props = new Properties();
    private static String configFile = "/KrbLdap";
		
	public LdapFetch(Subject subj, User usr) {
		subjct = subj;
		user = usr;
	}
	
	public Object run() {

	try {
		// read the configuration properties
 	    Login.loadProperties(props, configFile);
		String ldapurl = props.getProperty("ldapurl", "ldap://someldapserver.com:389");
		String basedn = props.getProperty("basedn", "dc=arch,dc=org");
		String mutualauth = props.getProperty("mutualauth", "false");
		String truststore = props.getProperty("truststore", "open.keystore");
		String starttls = props.getProperty("starttls", "yes");
		
		//System.setProperty("javax.net.ssl.trustStore", "C:\\openhre.keystore");
		System.setProperty("javax.net.ssl.trustStore", truststore); 
		
		// Set up the environment for creating the initial context
		Hashtable env = new Hashtable(11);
		env.put(Context.INITIAL_CONTEXT_FACTORY, 
		    "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapurl);
		
		// Request the use of the "GSSAPI" SASL mechanism
		// Authenticate by using already established Kerberos credentials
		env.put(Context.SECURITY_AUTHENTICATION,"GSSAPI");
		
		//env.put("javax.security.sasl.server.authentication", mutualauth); //mutual authentication
		
		// Set authzId = u:<username>, since OpenLDAP expects SASL to provide authzId of the form 
		// u:<username> or dn:<LDAP-DN>
		Set princs = subjct.getPrincipals();
	    Iterator princs_iter = princs.iterator();
	    String princ_name = "";
	    
	    if( princs_iter.hasNext())
	    {
	    	princ_name = princs_iter.next().toString().trim();
	    	int indx = princ_name.indexOf("@");
	    	princ_name = princ_name.substring(0,indx);
	    }
		String authzId = "u:" + princ_name;	
		cat.debug("AuthzID: " + authzId);
		env.put("java.naming.security.sasl.authorizationId", authzId);
		
			
		// Create the initial context
		LdapContext dctx = new InitialLdapContext(env,null);
		user.setLdapContext(dctx);
		
		if (starttls.toLowerCase().equals("yes")) {
			// Start TLS
		    cat.info("Starting TLS");
			StartTlsResponse tls =
			    (StartTlsResponse) dctx.extendedOperation(new StartTlsRequest());
			SSLSession sess = tls.negotiate();
		} else
		    cat.info("No TLS support");
		
		// Search all entries.	SearchControls determines scope of search and what gets 
		// returned as a result of the search.
		SearchControls ctls = new SearchControls();
		ctls.setReturningObjFlag (false);
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String filter = "(uid=" + princ_name + ")";
		NamingEnumeration answer = dctx.search(basedn, filter, ctls);

		setUserAttributes(answer);
		//dctx.close();
		
	} catch (NamingException ne) {
		ne.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
     return null;
   }
      
	
	void setUserAttributes(NamingEnumeration naming_enum) throws Exception{
				
		try {
		    while (naming_enum.hasMore()) {
				SearchResult sr = (SearchResult)naming_enum.next();
				Attributes attrs = sr.getAttributes();
				
				if (attrs == null) {
				    cat.debug("This result has no attributes");
				} else {
				    
					for (naming_enum = attrs.getAll(); naming_enum.hasMore();) {
					    Attribute attrib = (Attribute)naming_enum.next();
					   					    
					    if (attrib.getID().equals("dn")) {
					    	for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String user_dn = (String)e.next();
					    		user.setDN(user_dn);
					    		cat.debug("dn: " + user_dn);
					    	}
						}
					    // user ID; make userId = uid@arch.org
					    else if (attrib.getID().equals("uid")) {
					    	for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String user_uid = (String)e.next();
					    		//String domain = basedn.replaceAll("dc=","");
					    		//user_uid = user_uid + "@" + domain.replaceAll(",", ".");
					    		//user.setUserId(user_uid);
					    		cat.debug("uid: " + user_uid);
					    	}
						}
					    // user's complete name
					    else if (attrib.getID().equals("cn")) {
					    	for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String user_cn = (String)e.next();
					    		user.setUserName(user_cn);
					    		cat.debug("cn: " + user_cn);
					    	}
						}
					    // group membership
					    else if (attrib.getID().equals("ou")) {
					    	for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String group = (String)e.next();
					    		user.addGroup(group);
					    		cat.debug("group: " + group);
					    	}
						}
					    // role membership
					    else if (attrib.getID().equals("employeeType")) {
					    	for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String role = (String)e.next();
					    		user.addRole(role);
					    		cat.debug("role: " + role);
					    	}
						}
					    // allowed IP addresses
					    else if (attrib.getID().equals("ipHostNumber")) {
					    	for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String allowed_ip = (String)e.next();
					    		user.addAllowedip(allowed_ip);
					    		cat.debug("allowed ip: " + allowed_ip);
					    	}
						}
					    // password validity start date
					    else if (attrib.getID().equals("krb5ValidStart")) {
					    	for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String pwvalidstart = (String)e.next();
					    		user.setPassvalidstart(pwvalidstart);
					    		cat.debug("Password valid from: " + pwvalidstart);
					    	}
						}
					    // password renewal time
					    else if (attrib.getID().equals("krb5MaxRenew")) {
					    	for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String pwrenewal = (String)e.next();
					    		user.setPassrenewal(pwrenewal);
					    		cat.debug("Password renewal time: " + pwrenewal + " days");
					    	}
						}
					    // user password change required if value = -1
					    else if (attrib.getID().equals("krb5MaxLife")) {
					    	for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String pwchange = (String)e.next();
					    		if (pwchange.trim().equals("-1"))
					    		    user.setPasschange(true);
					    		cat.debug("Password change: " + pwchange);
					    	}
						}
					    
					}
				}
		    }
		} catch (NamingException e) {
		    e.printStackTrace();
		}
	}		
	
	 
}
