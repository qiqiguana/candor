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
 *
 * @author $Author: grodecki $
 * @version $Id: User.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
 */
public class User implements Comparable, Serializable {

    // static final long serialVersionUID = -3313178117323543078L;
    static final long serialVersionUID = 6729362233633496647L;

    // id (key)
    private String userId = "";

    // current IP address
    private String ipAddr;

    //cn CommonName
    private String userName = "";

    private String email = "";

    private Set roles;

    private Set groups;

    // Groups active for the user based on his location
    private Set activegroups;

    private String passvalidstart = "";

    private String passrenewal = "";

    // user password change required before using the system?
    private boolean passchange = false;

    private Set allowedips;

    LdapContext dctx;

    // c
    private String country = "";

    // o
    private String organization = "";

    // encrypted    // userPassword
    private String passwd = "";

    // surname
    private String sn = "";

    private transient MessageDigest sha;

    private static String sep = System.getProperty("line.separator");

    private static String salt = "XXXX";

    private static org.apache.log4j.Logger cat = org.apache.log4j.Logger.getLogger(User.class.getName());

    public User() {
    }

    /**
     * User object constructed from userId and userName
     */
    public User(String userId, String userName) {
    }

    public void init();

    /**
     * get userId previously set
     * @return userId
     */
    public String getUserId();

    /**
     * set the UserId
     * @param id to be set
     */
    public void setUserId(String id);

    /**
     * encode the  password
     * @param password to be encoded
     */
    public void encodePassword(String password);

    /**
     * Validate the password
     * @param password
     * @return
     */
    public boolean checkPassword(String password);

    /**
     * set the encrypted password
     * @param password  as encrypted digest (SHA algorithm)
     */
    public void setPassword(String password);

    /**
     * get the encrypted Password
     * @return encrypted password string
     */
    public String getPassword();

    /**
     * set the SurName
     * @param surname
     */
    public void setSurName(String surname);

    /**
     * get the SurName
     * @return sn
     */
    public String getSurName();

    /**
     * get userName
     * @return userName
     */
    public String getUserName();

    /**
     * set the User Name (cn field in LDAP
     * @param name
     */
    public void setUserName(String name);

    /**
     * get the IpAddr of this user
     * @return String containing ipAddr
     */
    public String getIpAddr();

    /**
     * set the IpAddr of this user
     * @param ipAddr  (should validate the address)
     */
    public void setIpAddr(String ipAddr);

    public String getEmail();

    public void setEmail(String email);

    public Set getRoles();

    public void addRole(String role);

    public Set getGroups();

    public void addGroup(String group);

    public Set getActivegroups();

    public void addActivegroup(String group);

    public String getPassvalidstart();

    public void setPassvalidstart(String passvalidstart);

    public String getPassrenewal();

    public void setPassrenewal(String passrenewal);

    public boolean getPasschange();

    public void setPasschange(boolean passchange);

    public Set getAllowedips();

    public void addAllowedip(String allowed_ip);

    public void setLdapContext(LdapContext dctx);

    public LdapContext getLdapContext();

    /**
     * set the Distinguished Name of the person
     * @param dn containing distinguished name    (should check its validity)
     */
    public void setDN(String dn);

    /**
     * set the salt for encryption
     * @param theSalt
     */
    public static void setSalt(String theSalt);

    /**
     * get the Distinguished Name of the Person
     * @return String containing the distinguished name
     */
    public String getDN();

    /**
     * get the country (c) parameter
     * @return country
     */
    public String getCountry();

    /**
     * set the country parameter
     * @param c country
     */
    public void setCountry(String c);

    /**
     * get the Organization of the user from the Distinguished Name dn
     * @return String representing the organization
     * The dn string is of the form cn="name", o="organization", c="country"
     */
    public String getOrg();

    /**
     * Return a Properties sheet with identification
     * @return
     */
    public Properties getCredentials();

    /**
     * set the organization
     */
    public void setOrg(String org);

    /**
     * copy nonempty fiels into the fields of object
     * @param newUser
     */
    public void update(User newUser);

    /**
     * String representation of user is simply its userId;
     * @return String representation of User
     */
    public String toString();

    /**
     * String representation with user defined separator
     * @param sep
     * @return String representation of User
     */
    public String toString(String sep);

    /**
     * create hashCode combining unique userId and ipAddr so user
     * is distinguished coming from different "locations"
     */
    public int hashCode();

    /**
     *  Equality check for user to see if he is already known.
     */
    public boolean equals(Object user);

    /**
     *  Compare to operation to see if the input user is the same as this user.
     */
    public int compareTo(Object user);

    public String createDigest(byte[] salt, String identity);

    /**
     * Check Digest against identity
     * @param digest is digest to be checked against
     * @param identity to be checked
     */
    public boolean checkDigest(String digest, String identity);

    /**
     * Combine two byte arrays
     * @param l first byte array
     * @param r second byte array
     * @return byte[] combined byte array
     */
    private static byte[] concatenate(byte[] l, byte[] r);

    /**
     * split a byte array in two
     * @param src byte array to be split
     * @param n element at which to split the byte array
     * @return byte[][]  two byte arrays that have been split
     */
    private static byte[][] split(byte[] src, int n);
}
