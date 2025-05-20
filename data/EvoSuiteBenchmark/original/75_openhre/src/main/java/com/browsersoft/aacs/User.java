/*
 *   CVS $Id: User.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
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

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.ldap.LdapContext;

import org.apache.xerces.impl.dv.util.Base64;


/**
 * User class contains all the necessary attributes of a logged in user
 * @author $Author: grodecki $
 * @version $Id: User.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
 */
public class User implements Comparable, Serializable {
   // static final long serialVersionUID = -3313178117323543078L;
    static final long serialVersionUID = 6729362233633496647L;

    private String userId = "";   		// id (key)
    private String ipAddr;   			// current IP address
    private String userName = "";    	//cn CommonName
    private String email="";
    private Set roles;
    private Set groups;
    private Set activegroups;			// Groups active for the user based on his location
	private String passvalidstart="";
	private String passrenewal="";
	private boolean passchange = false;	// user password change required before using the system?
    private Set allowedips;
    LdapContext dctx;
    // private String dn;  //Distinguished name    (cn, o, c)
    private String country = "";                // c
    private String organization = "";           // o
    private String passwd = ""; // encrypted    // userPassword
    private String sn = ""; // surname
    private transient MessageDigest sha;
    private static String sep = System.getProperty("line.separator");
    private static String salt = "XXXX";

    private static org.apache.log4j.Logger cat 
      = org.apache.log4j.Logger.getLogger(User.class.getName());

    public User() {
    }

    /**
     * User object constructed from userId and userName
     */
    public User(String userId, String userName) {
    	this();
    	//	System.out.println("userId: "+userId);
        this.userId = userId.trim();        //id
        this.userName = userName.trim();      //cn
    }
    
    public void init() {
        try {
            sha = MessageDigest.getInstance("SHA-1");
        } catch (java.security.NoSuchAlgorithmException e) {
            cat.error("SHA-1 constructor failed: ",e);
        }
    }

    /**
     * get userId previously set
     * @return userId
     */
    public String getUserId() {
        //	System.out.println("getUserId: "+userId);
        return userId;
    }

    /**
     * set the UserId
     * @param id to be set
     */
    public void setUserId(String id) {
        userId = id.trim();
    }

    /**
     * encode the  password
     * @param password to be encoded
     */
    public void encodePassword(String password) {
        // only set password if it is non null.
        if (passwd != null && (password != null) && !password.equals(""))
          passwd = createDigest(salt.getBytes(), password);
    }

    /**
     * Validate the password
     * @param password
     * @return
     */
    public boolean checkPassword(String password) {
        if (passwd == null) return false;
        return checkDigest(passwd, password);
    }
    
    /**
     * set the encrypted password
     * @param password  as encrypted digest (SHA algorithm)
     */
    public void setPassword(String password) {
        passwd = password;
    }

    /**
     * get the encrypted Password
     * @return encrypted password string
     */
    public String getPassword() {
        return passwd;
    }

    /**
     * set the SurName
     * @param surname
     */
    public void setSurName(String surname) {
        sn = surname;
    }

    /**
     * get the SurName
     * @return sn
     */
    public String getSurName() {
        return sn;
    }

    /**
     * get userName
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set the User Name (cn field in LDAP
     * @param name
     */
    public void setUserName(String name) {
        userName = name.trim();
    }

    /**
     * get the IpAddr of this user
     * @return String containing ipAddr
     */
    public String getIpAddr() {
        return ipAddr;
    }

    /**
     * set the IpAddr of this user
     * @param ipAddr  (should validate the address)
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email.trim();
    }
    
    public Set getRoles() {
        return roles;
    }

    public void addRole(String role) {
        if (roles == null)
            roles = new HashSet();
        roles.add(role);
    }

    public Set getGroups() {
        return groups;
    }

    public void addGroup(String group) {
        if (groups == null)
            groups = new HashSet();
        groups.add(group);
    }
    
    public Set getActivegroups() {
        return activegroups;
    }

    public void addActivegroup(String group) {
        if (activegroups == null)
            activegroups = new HashSet();
        activegroups.add(group);
    }
    
    public String getPassvalidstart() {
	    return passvalidstart;
	}
	
	public void setPassvalidstart(String passvalidstart) {
		this.passvalidstart = passvalidstart;
	}

	public String getPassrenewal() {
	    return passrenewal;
	}
	
	public void setPassrenewal(String passrenewal) {
		this.passrenewal = passrenewal;
	}	

	public boolean getPasschange() {
	    return passchange;
	}
	
	public void setPasschange(boolean passchange) {
		this.passchange = passchange;
	}
        
    public Set getAllowedips() {
        return allowedips;
    }

    public void addAllowedip(String allowed_ip) {
        if (allowedips == null)
            allowedips = new HashSet();
        allowedips.add(allowed_ip);
    }
    
    public void setLdapContext(LdapContext dctx) {
        this.dctx = dctx;
    }
    
    public LdapContext getLdapContext() {
        return dctx;
    }
    
    /**
     * set the Distinguished Name of the person
     * @param dn containing distinguished name    (should check its validity)
     */
    public void setDN(String dn) {
      //  cat.debug("setDN: " + dn);
        if (dn != null) {
            StringTokenizer st = new StringTokenizer(dn, ",");
            while (st.hasMoreTokens()) {
                String tok = st.nextToken();
                StringTokenizer stoken = new StringTokenizer(tok, "=");
                String name = stoken.nextToken();
                // System.out.println("token: "+tok +" name: "+name);
                if (name.trim().equals("o")) {
                    organization = stoken.nextToken();
                    //   cat.debug("org: "+organization);
                    //break;
                } else if (name.trim().equals("c")) {
                    country = stoken.nextToken();
                } else if (name.trim().equals("cn")) {
                    userName = stoken.nextToken();
                }

            }
        }
        else
          cat.warn("setDN: dn is null");
        return;
    }

    /**
     * set the salt for encryption
     * @param theSalt
     */
    public static void setSalt(String theSalt) {
        salt = theSalt;
    }

    /**
     * get the Distinguished Name of the Person
     * @return String containing the distinguished name
     */
    public String getDN() {
        return "cn=" + userName + ", o=" + organization + ", c=" + country;
        //  return dn;
    }

    /**
     * get the country (c) parameter
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * set the country parameter
     * @param c country
     */
    public void setCountry(String c) {
        country = c;
    }

    /**
     * get the Organization of the user from the Distinguished Name dn
     * @return String representing the organization
     * The dn string is of the form cn="name", o="organization", c="country"
     */
    public String getOrg() {

        return organization;
    }
    /**
     * Return a Properties sheet with identification
     * @return
     */
    public Properties getCredentials() {
        Properties props = new Properties();
        props.setProperty("subjectDN", getDN());
        props.setProperty("EMail",getUserId());
        return props;
    }
    
    /**
     * set the organization
     */
    public void setOrg(String org) {
        organization = org;
    }

    /**
     * copy nonempty fiels into the fields of object
     * @param newUser
     */
    public void update(User newUser) {
        organization = newUser.getOrg();
        country = newUser.getCountry();
        roles = newUser.getRoles();
        groups = newUser.getGroups();
        userName = newUser.getUserName();
        sn = newUser.getSurName();
        passwd = newUser.getPassword();
        userId = newUser.getUserId();

    }

    /**
     * String representation of user is simply its userId;
     * @return String representation of User
     */
    public String toString() {

        // char sep = '\n';
        StringBuffer buff = new StringBuffer();
        buff.append("dn: " + getDN() + sep);
        buff.append("cn: " + getUserName() + sep);
        buff.append("id: " + getUserId() + sep);
        buff.append("sn: " + getSurName() + sep);
        buff.append("userPassword: " + getPassword() + sep);
        
        if (getRoles() != null) {
	        Iterator it = getRoles().iterator();
	        while (it.hasNext())
	            buff.append("role: " + (String)(it.next()) + sep);
        }
        
        if (getGroups() != null) {
	        Iterator it = getGroups().iterator();
	        while (it.hasNext())
	            buff.append("group: " + (String)(it.next()) + sep);
        }
        
        if (getIpAddr() != null) buff.append("ip: "+ getIpAddr() +sep);
        buff.append("objectClass: top" + sep);
        buff.append("objectClass: person" + sep);
        return buff.toString();
    }

    /**
     * String representation with user defined separator
     * @param sep
     * @return String representation of User
     */
    public String toString(String sep) {
        StringBuffer buff = new StringBuffer();
        buff.append("dn: " + getDN() + sep);
        buff.append("cn: " + getUserName() + sep);
        buff.append("id: " + getUserId() + sep);
        buff.append("sn: " + getSurName() + sep);
        buff.append("userPassword: " + getPassword() + sep);
        
        Set rolebuf = new HashSet();
        rolebuf = getRoles();
        Iterator it = rolebuf.iterator();
        while (it.hasNext())
            buff.append("role: " + (String)(it.next()) + sep);
        
        Set groupbuf = new HashSet();
        groupbuf = getGroups();
        it = groupbuf.iterator();
        while (it.hasNext())
            buff.append("group: " + (String)(it.next()) + sep);
        
        buff.append("objectClass: top" + sep);
        buff.append("objectClass: person" + sep);
        return buff.toString();
    }

    /**
     * create hashCode combining unique userId and ipAddr so user 
     * is distinguished coming from different "locations"
     */
    public int hashCode() {
        // Improve hashcode calculation using member variables of this class
        return 13 * userId.hashCode() + 7 * ipAddr.hashCode();
    }

    /**
     *  Equality check for user to see if he is already known.
     */
    public boolean equals(Object user) {
        // Return true if the result of the compareTo() method is zero
        return compareTo(user) == 0;
    }

    /**
     *  Compare to operation to see if the input user is the same as this user.
     */
    public int compareTo(Object user) {
        // Compare the user IDs of the two user objects -
        //  result is zero if they're identical (uses String compareTo function)
        int result = userId.compareTo(((User) user).getUserId());

        // If result is zero from previous method, return the comparison of IP addresses.
        // Otherwise, return the result.
        return result == 0 ? ipAddr.compareTo(((User) user).getIpAddr()) : result;
    }
    /**
     * Create Digest for each input identity
     * @param salt to set the base for the encryption
     * @param identity to be encrypted
     */

    public String createDigest(byte[] salt, String identity) {
        String label = (salt.length > 0) ? "{SSHA}" : "{SHA}";

        if (sha == null) init();
        sha.reset();
        sha.update(identity.getBytes());
        sha.update(salt);
        byte[] pwhash = sha.digest();
        return label + new String(Base64.encode(concatenate(pwhash, salt)));
    }

    /**
     * Check Digest against identity
     * @param digest is digest to be checked against
     * @param identity to be checked
     */
    public boolean checkDigest(String digest, String identity) {
        if (digest.regionMatches(true, 0, "{SHA}", 0, 5)) {
            digest = digest.substring(5); // ignore the label
        } else if (digest.regionMatches(true, 0, "{SSHA}", 0, 6)) {
            digest = digest.substring(6); // ignore the label
        }

        byte[][] hs = split(Base64.decode(digest.getBytes()), 20);
        byte[] hash = hs[0];
        byte[] salt = hs[1];

        if (sha == null) init();
        sha.reset();
        sha.update(identity.getBytes());
        sha.update(salt);
        byte[] pwhash = sha.digest();
        boolean valid = true;
        if (!MessageDigest.isEqual(hash, pwhash)) {
            valid = false;
            cat.warn("doesn't match: " + identity);

        }

        return valid;
    }
    /**
     * Combine two byte arrays
     * @param l first byte array
     * @param r second byte array
     * @return byte[] combined byte array
     */
    private static byte[] concatenate(byte[] l, byte[] r) {
        byte[] b = new byte[l.length + r.length];
        System.arraycopy(l, 0, b, 0, l.length);
        System.arraycopy(r, 0, b, l.length, r.length);
        return b;
    }

    /**
     * split a byte array in two
     * @param src byte array to be split
     * @param n element at which to split the byte array
     * @return byte[][]  two byte arrays that have been split
     */
    private static byte[][] split(byte[] src, int n) {
        byte[] l, r;
        if (src == null || src.length <= n) {
            l = src;
            r = new byte[0];
        } else {
            l = new byte[n];
            r = new byte[src.length - n];
            System.arraycopy(src, 0, l, 0, n);
            System.arraycopy(src, n, r, 0, r.length);
        }
        byte[][] lr = {l, r};
        return lr;
    }
    
}
