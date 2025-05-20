/*
 *   CVS $Id: Person.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
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

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Person implements DirContext {
    private String uid;					// LDAP uid
    private String id;					// fully qualified id including the LDAP domain name
    private String cn;					// complete name
    private String dn;					// LDAP distinguished name
    private String sn;					// surname
    private String givenname;			// first name
    private String email;				//email address
    private String telephoneNumber;
    private String faxTelephoneNumber;
    private String postalAddress;
    private String[] roles;
    private String[] groups;
	private String passvalidstart="";
	private String passrenewal="";
	private String passchange = "";
    private String allowedips;
    
    private String domain;
    private String basedn;
    private String krbrealm;
    private static Properties props = new Properties();
    private static String configFile = "/KrbLdap";
    
    private static String appDateFormat = "MM/dd/yyyy";			// Date format used by OpenHRE
    private static String ldapGTFormat = "yyyyMMddHHmmssZ";		// LDAP GeneralizedTime Format
    
    Attributes myAttrs = new BasicAttributes(true);
    Attribute oc = new BasicAttribute("objectclass");
    Attribute etSet = new BasicAttribute("employeeType");			// Attribute Set for roles
    Attribute ouSet = new BasicAttribute("ou");						// Attribute Set for groups
    Attribute ipSet = new BasicAttribute("ipHostNumber");			// Attribute Set for allowed IP addresses
 
    // default constructor
    public Person() {
        init();
    }
    
    public Person(String uid, String cn) {
        init();
        this.uid = uid;
        this.id = uid + "@" + domain;
        this.cn = cn;
    }
    
    public Person(String uid, String givenname, String sn, String email, String[] roles, String[] groups, 
            	String passvalidstart, String passrenewal, String passchange, String allowedips, 
            	String tel, String fax, String postalAddr) {
       init();
       this.dn="uid=" + uid + ",ou=people," + basedn;
       this.uid = uid;
       this.id = uid + "@" + domain;
       this.sn= sn;
       this.givenname = givenname;
       this.cn = givenname +" "+ sn;
       this.email = email;
       this.telephoneNumber = tel;
       this.faxTelephoneNumber = fax;
       this.postalAddress = postalAddr;
       this.roles = roles;
       this.groups = groups;
       this.passvalidstart = passvalidstart;
       this.passrenewal = passrenewal;
       this.passchange = passchange;
       this.allowedips = allowedips;
       
       setAttributes();
    }
    
    private void init() {
        // read the configuration properties
 	    Login.loadProperties(props, configFile);
        
 		basedn = props.getProperty("basedn", "dc=arch,dc=org");
 		krbrealm = props.getProperty("krbrealm", "<YOUR-KRB-REALM>");
 		domain = basedn.trim().replaceAll("dc=","");
 		domain = domain.replaceAll(",", ".");
    }
    
	public void setAttributes() {
	       oc.add("inetOrgPerson");
	       oc.add("organizationalPerson");
	       oc.add("person");
	       oc.add("krb5Principal");
	       oc.add("krb5KDCEntry");
	       oc.add("ipHost");
	       oc.add("top");
	       myAttrs.put(oc);
	       myAttrs.put("uid",uid);
	       myAttrs.put("cn",cn);
	       myAttrs.put("sn",sn);
	       myAttrs.put("givenname",givenname);
	       myAttrs.put("mail",email);
	       myAttrs.put("krb5PrincipalName",uid + "@" + krbrealm);
	       myAttrs.put("krb5KeyVersionNumber", "1");
	       
	       // This attribute will force the user to change the password before using the system
	       // when the administrator creates the user or resets his/her password
	       if (passchange.equals("yes"))
	           myAttrs.put("krb5MaxLife", "-1");
	       else if (passchange.equals("no"))
	           myAttrs.put("krb5MaxLife", "0");
	       
	       // krb5ValidStart will keep on changing whenever user updates his/her password.
	       // krb5MaxRenew contains days after the krb5ValidStart, after which the user will 
	       // be forced to update his/her password. 
	       if (!passvalidstart.trim().equals("")) {
	           String pwvalstart = convertDateToUTC(passvalidstart);
	           myAttrs.put("krb5ValidStart", pwvalstart);
	           if (!passrenewal.trim().equals(""))
	               myAttrs.put("krb5MaxRenew", passrenewal);
	           else
	               myAttrs.put("krb5MaxRenew", "-1");
	       }
	       
	       
	       // Add all the roles
	       for (int i = 0; i < roles.length; i++)
	           etSet.add(roles[i]);
	       myAttrs.put(etSet);
	       
	       // Add all the groups
	       for (int i = 0; i < groups.length; i++) {
	           if (groups[i].toString().equals("None")){
	               ouSet.clear();
	               ouSet.add(" ");					// To avoid invalid LDAP value exception while adding
	               break;
	           }
	           ouSet.add(groups[i]);
	       }
	       myAttrs.put(ouSet);
	       
	       // Add allowed IP addresses (if any)
	       if (allowedips.length() > 1) {
	           StringTokenizer st = new StringTokenizer(allowedips, "\n");
	           while (st.hasMoreTokens()) {
	               String ip = st.nextToken().trim().replaceAll("\n", "");
	               ip = ip.replaceAll("\t","");
	               ip = ip.replaceAll(" ","");
	               ipSet.add(ip);
	           }
	       }
	       else
	           ipSet.add("0");
	       myAttrs.put(ipSet);
	       
	       if (telephoneNumber.length() > 1)
	           myAttrs.put("telephoneNumber",telephoneNumber);
	       else
	           myAttrs.put("telephoneNumber","0");			// Due to LDAP schema numerical value constraint
	       
	       if (faxTelephoneNumber.length() > 1)
	           myAttrs.put("facsimileTelephoneNumber",faxTelephoneNumber);
	       else
	           myAttrs.put("facsimileTelephoneNumber","0");	// Due to LDAP schema numerical value constraint
	       
	       myAttrs.put("postalAddress",postalAddress);	       	       
	}
	
	private String convertDateToUTC(String specdatetime) {
	    // Convert date and time from local time to UTC time
	    // and convert to LDAP GeneralizedTime format (for eg. 20040101000000+0000)
       TimeZone utc = TimeZone.getTimeZone("UTC");
       SimpleDateFormat df1 = new SimpleDateFormat(ldapGTFormat);
       df1.setTimeZone(utc);

       SimpleDateFormat df2 = new SimpleDateFormat(appDateFormat);	       
       Date specdate = null;
       try {
           specdate = df2.parse(specdatetime);
       } catch (ParseException pe){
           pe.printStackTrace();
       }       
       return df1.format(specdate);	       
	}
	
	private String convertDateFromUTC(String specdatetime) {
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
       return df1.format(specdate);	    
	}
	
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        this.id = uid + "@" + domain;
    }
    
    public String getId() {
        return id;
    }
       
    public String getCn() {
        return cn;
    }
    
    public void setCn(String cn) {
        this.cn = cn;
    }
 
    public String getDn() {
        return dn;
    }
    
    public void setDn(String dn) {
        this.dn = dn;
    }
    
    public String getSn() {
        return sn;
    }
    
    public void setSn(String sn) {
        this.sn = sn;
    }
    
    public String getGivenname() {
        return givenname;
    }
    
    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
    
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    
    public String getFaxTelephoneNumber() {
        return faxTelephoneNumber;
    }
    
    public void setFaxTelephoneNumber(String faxTelephoneNumber) {
        this.faxTelephoneNumber = faxTelephoneNumber;
    }
    
    public String getPostalAddress() {
        return postalAddress;
    }
	
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
    
    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
    
    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }
    
    public String getPassvalidstart() {
	    return passvalidstart;
	}
	
	public void setPassvalidstart(String passvalidstart) {
		this.passvalidstart = convertDateFromUTC(passvalidstart);
	}

	public String getPassrenewal() {
	    return passrenewal;
	}
	
	public void setPassrenewal(String passrenewal) {
		this.passrenewal = passrenewal;
	}	

	public String getPasschange() {
	    return passchange;
	}
	
	public void setPasschange(String passchange) {
		this.passchange = passchange;
	}
    
    public String getAllowedips() {
        return allowedips;
    }

    public void addAllowedip(String allowed_ip) {
        if (allowedips == null)
            allowedips = "";
        if (!allowed_ip.equals("0")) {
            if (allowedips.equals(""))
                allowedips = allowed_ip;
            else
                allowedips = allowedips + "\n" + allowed_ip;
        }
    }
    
    public Attributes getAttributes() throws NamingException {
       return myAttrs;
    }
    
    // overriding DirContext methods
    
	public void bind(Name name, Object obj, Attributes attrs){
	}
	
	public void bind(String name, Object obj, Attributes attrs){
	}
	
	public DirContext createSubcontext(Name name, Attributes attrs){
		return null;
	}

	public DirContext createSubcontext(String name, Attributes attrs) {
		return null;
	}
 	
    public Attributes getAttributes(String name) throws NamingException {
       if (! name.equals("")){
          throw new NameNotFoundException();
       }
       return myAttrs;
    }
 
    public Attributes getAttributes(Name name) throws NamingException {
       return getAttributes(name.toString());
    }
 
    public Attributes getAttributes(String name, String[] ids)
                                             throws NamingException {
       if(! name.equals(""))
          throw new NameNotFoundException();
       Attributes answer = new BasicAttributes(true);
       Attribute target;
       for (int i = 0; i < ids.length; i++){
          target = myAttrs.get(ids[i]);
          if (target != null){
             answer.put(target);
          }
       }
       return answer;
    }

    public Attributes getAttributes(Name name, String[] ids)
                                               throws NamingException {
       return getAttributes(name.toString(), ids);
    }

    public DirContext getSchema(Name name) {
    	return null;
    }
    		
    public DirContext getSchema(String name) {
    	return null;
    }

    public DirContext getSchemaClassDefinition(Name name){
    	return null;
    }

    public DirContext getSchemaClassDefinition(String name){
    	return null;
    }

    public void modifyAttributes(Name name, int mod_op, Attributes attrs){
    }

    public void modifyAttributes(Name name, ModificationItem[] mods){
    }

    public void modifyAttributes(String name, int mod_op, Attributes attrs){
    }

    public void modifyAttributes(String name, ModificationItem[] mods){
    }

    public void rebind(Name name, Object obj, Attributes attrs){
    }

    public void rebind(String name, Object obj, Attributes attrs){
    }
    
    public NamingEnumeration search(Name name, Attributes matchingAttributes){
        return null;
    }

    public NamingEnumeration search(Name name, Attributes matchingAttributes, String[] attributesToReturn){
        return null;
    }
 
    public NamingEnumeration search(Name name, String filterExpr, Object[] filterArgs, SearchControls cons){
        return null;
    }
 
    public NamingEnumeration search(Name name, String filter, SearchControls cons){
        return null;
    }
 
    public NamingEnumeration search(String name, Attributes matchingAttributes){
        return null;
    }

    public NamingEnumeration search(String name, Attributes matchingAttributes, String[] attributesToReturn){
        return null;
    }
 
    public NamingEnumeration search(String name, String filterExpr, Object[] filterArgs, SearchControls cons){
        return null;
    }
 
    public NamingEnumeration search(String name, String filter, SearchControls cons) {
        return null;
    }
    
    //  overriding Context methods
    
    public Object 	addToEnvironment(String propName, Object propVal) {
        return null;
    }
    
    public void bind(Name name, Object obj) {
    }
		
	public void bind(String name, Object obj) {
    }
		
	public void close() {
    }
		
	public Name composeName(Name name, Name prefix) {
        return null;
    }
    
	public String composeName(String name, String prefix) {
        return null;
    }
    
	public Context createSubcontext(Name name) {
        return null;
    }
    
	public Context createSubcontext(String name) {
        return null;
    }
    
	public void destroySubcontext(Name name) {
    }
		
	public void destroySubcontext(String name) {
    }
		
	public Hashtable getEnvironment() {
        return null;
    }
    
	public String getNameInNamespace() {
        return null;
    }
    
	public NameParser getNameParser(Name name) {
        return null;
    }
    
	public NameParser getNameParser(String name) {
        return null;
    }
    
	public NamingEnumeration list(Name name) {
        return null;
    }
    
	public NamingEnumeration list(String name) {
        return null;
    }
    
	public NamingEnumeration listBindings(Name name) {
        return null;
    }
    
	public NamingEnumeration listBindings(String name) {
        return null;
    }
    
	public Object lookup(Name name) {
        return null;
    }
    
	public Object lookup(String name) {
        return null;
    }
    
	public Object lookupLink(Name name) {
        return null;
    }
    
	public Object lookupLink(String name) {
        return null;
    }
    
	public void rebind(Name name, Object obj) {
    }
		
	public void rebind(String name, Object obj) {
    }
		
	public Object removeFromEnvironment(String propName) {
        return null;
    }
    
	public void rename(Name oldName, Name newName) {
    }
		
	public void rename(String oldName, String newName) {
    }
		
	public void unbind(Name name) {
    }
		
	public void unbind(String name) {
    }

}