package com.browsersoft.aacs;

import jdbm.JDBMEnumeration;
import jdbm.JDBMHashtable;
import jdbm.JDBMRecordManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public class SimpleUserMgr implements UserMgr {

    //private JDBMHashtable dn;
    private JDBMHashtable id = null;

    private JDBMHashtable users;

    private JDBMHashtable country;

    private String username = "";

    // private String email = "";
    private static String userfile = "login.users";

    private String userdb = "users";

    private static String configFile = "test";

    private JDBMRecordManager recman;

    private static Properties props = new Properties();

    private Vector profile_vec = null;

    private User user;

    private static org.apache.log4j.Logger cat = org.apache.log4j.Logger.getLogger(SimpleUserMgr.class.getName());

    /**
     * Public default constructor
     */
    public SimpleUserMgr() {
    }

    /**
     * Constructor which specifies the configProperties to be read
     */
    public SimpleUserMgr(String configProperties) {
    }

    /**
     * Initialize Persistent storage
     * There are two primary hashtables.   The first (country) contains a list of the organizations which are keys
     * to user hashtables for each organization.  The organization hashtable is a map from the username
     * key to the userId (email address).  The second (id) is the hashtable based on the email address
     * which is used as a userId since the email address is supposed to be unique.  For each organization
     * there is a separate hashtable of the users for that organization.  Thus one can look a user up
     * across organizations or within an organization.  All the user objects are contained in the second (id) hashtable.
     */
    public void init();

    /**
     * add Users from previously defined userfile
     */
    public void addUsers();

    /**
     *  Add users from an input file
     * @param userfile
     */
    public void addUsers(String userfile);

    /**
     * parse the line and add the user to the list
     * @param line to be parsed
     */
    public void parseLine(String line);

    /**
     * Add the user profile
     */
    private void addProfile();

    /**
     * addUser
     * @param u String with multiple lines with all the data for a user
     */
    public void addUser(String u);

    /**
     * add User to the Persistent Hashtable
     * @param theDN The distinguished name (cn= "name", o="organization", c="country")
     * @param cn  LDAP username
     * @param sn   LDAP surname
     * @param email   LDAP email address (userId)
     * @param role
     * @param password
     */
    public void addUser(String theDN, String cn, String sn, String email, String role, String password);

    public void addUser(User newUser);

    /**
     * delete User based on the unique UserId
     * @param userId corresponding to email address
     */
    public void delUser(String userId);

    /**
     * get the User based on name and organization
     * @param userName
     * @param org
     * @return User
     */
    public User getUser(String userName, String org);

    public User getUser();

    /**
     * get User by the unique userId (email)
     * @param userId
     * @return User
     */
    public User getUser(String userId);

    /**
     * get User with username, org and email
     * @param username  cn variable
     * @param org       o variable
     * @param email       email variable
     * @return User
     */
    public User getUser(String username, String org, String email);

    /**
     * Find a User given a partially completed User object as a template
     * @param findUser
     * @return User
     */
    public User getUser(User findUser);

    /**
     * Get all the userNames for a given organization
     * @param org the organization name (o field in LDAP);
     * @return String[] list of names within the organization
     */
    public String[] getNamesbyOrg(String org);

    /**
     * get the email addresses of all users in an organization
     * @param org name of the organization (o LDAP field)
     * @return String[] array of email addresses
     */
    public String[] getMailbyOrg(String org);

    /**
     * get list of all UserIds in DB
     * @return String[] list of UserIds
     */
    public String[] getUserIds();

    /**
     * obtain list of valid users
     * @return String[] list of known users
     */
    public String[] getUserNames();

    public static void setConfigFile(String theConfigFile);

    /**
     *  set the file of users to be read.
     * @param file to be read
     */
    public static void setUserfile(String file);

    /**
     * get all the users in in the persistent hashtable
     * @return String
     */
    public String export();

    public boolean checkSecret(String userid, String password, String authType);

    public void logOut();

    public static void main(String[] argv);
}
