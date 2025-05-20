/*
 *   CVS $Id: SimpleUserMgr.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
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

/**
 * This class manages a persistent storage of Users. It can add users from a file in
 * LDAP's LDIF format.  The values required are dn (Distinguished Name), cn (Common Name), userPassword, email,
 * ou and employeeType.
 * This is intended as an interface for LDAP, but provides a simple persistent Hashtables using JDBM.
 *
 * The id Hashtable (name "userids") contains the User objects with the email address as the key
 * The country (name "countries") Hashtable contains a HashSet of all the organizations in a given country
 * To get a list of all supported countries simply get the keys of the countries Hashtable
 * There also is a persistent Hashtable for each organization with is LDAP "o" value as its name
 * It returns a Hashtable of users within that organization, with key the "cn" (Username) and value "email"
 * Thus a user can be looked up by username and organization and return the "key" email address from which
 * the full User object can be obtained from the id Hashtable
 *
 * When a new organization is encountered (with a user with "o" and "c" dn parameters, it is registered
 * into the list of organizations for that country in the country Hashtable
 *
 * JDBM (http://jdbm.sourceforge.net) persistent hashtable implemenation is used for persistence
 *
 * @author $Author: grodecki $
 * @version $Id: SimpleUserMgr.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
 */

public class SimpleUserMgr implements UserMgr {

    //private JDBMHashtable dn;
    private JDBMHashtable id = null;
    private JDBMHashtable users;
    private JDBMHashtable country;
    private String username = "";
    // private String password = "";
    // private String email = "";
    private static String userfile = "login.users";
    private String userdb = "users";
    private static String configFile = "test";
    private JDBMRecordManager recman;
    private static Properties props = new Properties();
    private Vector profile_vec = null;
    // private Hashtable users = null;

    private User user;
    private static org.apache.log4j.Logger cat 
      = org.apache.log4j.Logger.getLogger(SimpleUserMgr.class.getName());

    /**
     * Public default constructor
     */
    public SimpleUserMgr() {
        // Defer initialization for servlets
        //  init();
    }

    /**
     * Constructor which specifies the configProperties to be read
     */
    public SimpleUserMgr(String configProperties) {
        setConfigFile(configProperties);
        init();
    }

    /**
     * Initialize Persistent storage
     * There are two primary hashtables.   The first (country) contains a list of the organizations which are keys
     * to user hashtables for each organization.  The organization hashtable is a map from the username
     * key to the userId (email address).  The second (id) is the hashtable based on the email address
     * which is used as a userId since the email address is supposed to be unique.  For each organization
     * there is a separate hashtable of the users for that organization.  Thus one can look a user up
     * across organizations or within an organization.  All the user objects are contained in the second (id) hashtable.
     *
     */
    public void init() {
      //  ResourceBundle bundle = ResourceBundle.getBundle(configFile);
 	    Login.loadProperties(props, configFile);
        userdb = props.getProperty("users", userdb);
//      New code to find the actual db file, as long as it is on the classpath
        String tmpDb = "/" + userdb + ".db";
        URL fileUrl = UserMgr.class.getResource(tmpDb);
		String pathAndFile = fileUrl.getFile();
		// Now remove the final '.db' from the returned string.
		userdb = pathAndFile.substring(0, pathAndFile.length()-3);

        cat.debug("init: accessing: "+userdb);
        try {
            recman = new JDBMRecordManager(userdb);
            //dn = recman.getHashtable("usernames");
            id = recman.getHashtable("userids");
            if (cat.isDebugEnabled()) {
            	for (JDBMEnumeration en = id.keys(); en.hasMoreElements(); ) {
            		String key = (String) en.nextElement();
            		cat.debug("Key = " + key);
            	}
            }
            country = recman.getHashtable("countries");
        } catch (IOException e) {
            cat.error("init: " + e);
        }
    }

    /**
     * add Users from previously defined userfile
     */
    public void addUsers() {
        addUsers(userfile);
    }

    /**
     *  Add users from an input file
     * @param userfile
     */
    public void addUsers(String userfile) {

        // Query using JNDI to get list of users, not implemented correctly yet.

        // Read from file to get users (in ldif format)
        // e.g.:
        //  dn: cn= David Forslund, o=LANL, c=US
        //	cn: David Forslund
        //	email: dwf@lanl.gov
        //  userPassword: test
        // Results are put into a User object and the User into a hashtable with email/userId
        // as the key
        // various arrays are created for listing the users as needed.


        // userfile = props.getProperty("login.users", userfile);
        // String country = props.getProperty("country","US");

        try {

            // orgs = recman.getHashtable(country);
            // Read in user list if not already done      ###

            String line = null;
            InputStream is = getClass().getResourceAsStream(userfile);
            //if (theFile.exists()) {
            if (is != null) {
                cat.debug("Reading  " + userfile);

                //	FileReader inFile = new FileReader(theFile);
                InputStreamReader inFile = new InputStreamReader(is);
                BufferedReader inReader = new BufferedReader(inFile);
                profile_vec = new Vector();
                user = null;
                HashSet set = null;     // unique set of organizations in a country
                while (((line = inReader.readLine()) != null)) {
                    parseLine(line);
                }
                //HashSet set = (HashSet) orgs.get(country);

                //if (set == null) set = new HashSet();
                inReader.close();
                if ((profile_vec != null)) {
                    addProfile();
                    for (int i = 0; i < profile_vec.size(); i++) {
                        User u = (User) profile_vec.elementAt(i);
                        String c = u.getCountry();
                        //cat.debug("addUsers\n "+u.toString());
                        String org = u.getOrg();
                        if (c != null) set = (HashSet) country.get(c);
                        if (set == null) set = new HashSet();
                        // get the hashtable for that organization
                        // Update list  of organizations for a country
                        set.add(org);

                        //  cat.debug("organization = " + org);
                        JDBMHashtable users = recman.getHashtable(org);
                        // insert userID in users table with userName as key
                        users.put(u.getUserName(), u.getUserId());   // put userId in dn with dn as key

                        id.put(u.getUserId(), u);   // put user in id with mail as key

                        country.put(c, set);
                    }
                    // put the list of names into the organization hashtable
                    // cat.debug(set.size() + " organizations");

                }

            }

        } catch (IOException e) {
            cat.error("UserMgr reading error adding users " + e, e);

        }


        //}
    }

    /**
     * parse the line and add the user to the list
     * @param line to be parsed
     */
    public void parseLine(String line) {

        try {
            //user = null;
            if (line.startsWith("#") || line.startsWith("//")) return;
            StringTokenizer tmp_st = new StringTokenizer(line, ":");
            if (tmp_st.countTokens() == 0) return;
            String tmp_tok = tmp_st.nextToken();
            //   System.out.println("parseLine: "+tmp_tok);
            if (tmp_tok.equals("dn")) {
                // We have a new defined person so save old data and reset
                if (user == null) {
                    user = new User();
                } else {   // user is complete so store it
                    addProfile();

                }
                user.setDN(tmp_st.nextToken().trim());
            } else if (tmp_tok.equals("cn")) {
                user.setUserName(tmp_st.nextToken().trim());
            } else if (tmp_tok.equals("email")) {
                user.setUserId(tmp_st.nextToken().trim());
            } else if (tmp_tok.equals("userPassword")) {
                //user.setPassword(tmp_st.nextToken().trim());
                user.encodePassword(tmp_st.nextToken().trim());
            } else if (tmp_tok.equals("employeeType")) {
                // specify role(s) of user
                user.addRole(tmp_st.nextToken().trim());
            } else if (tmp_tok.equals("ou")) {
                // specify group(s) of user
                user.addGroup(tmp_st.nextToken().trim());
            } else if (tmp_tok.equals("sn")) {
                user.setSurName(tmp_st.nextToken().trim());
            } else if (tmp_tok.equals("ip")) {
                // specify ip Addr of user
                user.setIpAddr(tmp_st.nextToken().trim());
            }
            
        } catch (Exception e) {
            cat.error("parseLine failed:" + e);
        }

    }

    /**
     * Add the user profile
     */
    private void addProfile() {
        profile_vec.addElement(user);
        //  cat.debug("name: "+user.getUserName()+", email: "+user.getUserId()+", passwd: "+user.getPassword());
        user = new User();
    }

    /**
     * addUser
     * @param u String with multiple lines with all the data for a user
     */
    public void addUser(String u) {
        BufferedReader reader = new BufferedReader(new StringReader(u));
        String line = null;
        User saveUser = user;
        try {
            while ((line = reader.readLine()) != null)
                parseLine(line);
            if (user != null) addUser(user);
        } catch (IOException e) {
            cat.error("addUser: " + e);
        }
        user = saveUser;

    }

    /**
     * add User to the Persistent Hashtable
     * @param theDN The distinguished name (cn= "name", o="organization", c="country")
     * @param cn  LDAP username
     * @param sn   LDAP surname
     * @param email   LDAP email address (userId)
     * @param role
     * @param password
     */
    public void addUser(String theDN, String cn, String sn, String email, String role, String password) {
        User newUser = new User(email, cn);
        newUser.addRole(role);
        newUser.setPassword(password);
        newUser.setSurName(sn);
        newUser.setDN(theDN);
        addUser(newUser);
    }

    /**
     *  Add a User already constructed
     * @param newUser
     */

    public void addUser(User newUser) {

        String c = newUser.getCountry();
        String org = newUser.getOrg();
        String userName = newUser.getUserName();
        String email = newUser.getUserId();
        cat.debug("addUser: " + newUser.toString());
        User oldUser = getUser(newUser);
        if (oldUser == null) oldUser = new User();

        oldUser.update(newUser);
        try {
            // First make sure the organization is in the country list
            HashSet set = (HashSet) country.get(c);
            if (set == null) set = new HashSet();
            set.add(org);
            country.put(c, set);
            // get the users Hashtable based on the organization
            users = recman.getHashtable(org);

            users.put(userName, email);
            id.put(email, oldUser);
            cat.debug("User: " + oldUser.toString() + " added!");
        } catch (IOException e) {
            cat.error("addUser: " + e);
        }
    }


    /**
     * delete User based on the unique UserId
     * @param userId corresponding to email address
     */
    public void delUser(String userId) {
        try {
            cat.debug("delUser trying to remove: " + userId);
            User delUser = (User) id.get(userId);
            if (delUser != null) {
                id.remove(userId);
                cat.debug("delUser removed from id: " + userId);
                String uName = delUser.getUserName();
                users = recman.getHashtable(delUser.getOrg());
                cat.debug("removing " + uName + " from dn");
                String u = (String) users.get(uName);
                if (u != null) {
                    users.remove(uName);
                    cat.debug("delUser removed from dn: " + uName);
                }
            } else
                cat.debug("delUser: id='" + userId + "' not found");
        } catch (IOException e) {
            cat.error("delUser: " + userId + " " + e);
        }


    }

    /**
     * get the User based on name and organization
     * @param userName
     * @param org
     * @return User
     */
    public User getUser(String userName, String org) {
        User user = null;
        try {
            users = recman.getHashtable(org);
            String userId = (String) users.get(username);
            if (userId != null) user = (User) id.get(userId);
            if (user == null) cat.warn("getUser(" + userName + "," + org + "): not found");
        } catch (IOException e) {
            cat.error("getUser: " + e);
        }
        return user;
    }

    public User getUser()
    {
    	cat.debug("userID: "+ user.getUserId());
    	cat.debug("userName: "+ user.getUserName());
        return user;
    }
    /**
     * get User by the unique userId (email)
     * @param userId
     * @return User
     */
    public User getUser(String userId) {
        cat.debug("getUser: " + userId);
        User user = null;
        try {
            user = (User) id.get(userId);
            cat.debug("getUser: found user " + user.toString());
        } catch (Exception e) {
            user=null;
            cat.error("getUser: user not found " + e);
        }
        //cat.debug("getUser: found user " + user.toString());
        return user;
    }

    /**
     * get User with username, org and email
     * @param username  cn variable
     * @param org       o variable
     * @param email       email variable
     * @return User
     */
    public User getUser(String username, String org, String email) {
        User user = null;
        try {
            // try unique email first (this should always return the user)
            if (email != null && !email.equals(""))
                user = (User) id.get(email);
            if ((user == null) && (org != null) && (username != null)) {
                // get the user list for the organization
                users = recman.getHashtable(org);
                if (users != null) {
                    String userid = (String) users.get(username);
                    if (userid != null) user = (User) id.get(userid);
                }
            }
            if (user == null) cat.warn("getUser(" + username + "," + org + "," + email + "): user not found, ");
        } catch (IOException e) {
            cat.error("getUser: " + e);
        }
        return user;
    }

    /**
     * Find a User given a partially completed User object as a template
     * @param findUser
     * @return User
     */
    public User getUser(User findUser) {
        User user = new User();
        String email = findUser.getUserId();
        try {
            if (email != null && !email.equals(""))
                user = (User) id.get(email);
            if ((user == null) && (findUser.getOrg() != null) && findUser.getUserName() != null) {
                users = recman.getHashtable(findUser.getOrg());
                if (users != null) {
                    String userid = (String) users.get(username);
                    if (userid != null) user = (User) id.get(userid);
                }
            }
            if (user == null) {
                cat.warn("getUser(" + findUser.toString() + "): user not found");

            }
        } catch (IOException e) {
            cat.error("getUser: " + e);
        }
        return user;

    }

    /**
     * Get all the userNames for a given organization
     * @param org the organization name (o field in LDAP);
     * @return String[] list of names within the organization
     */
    public String[] getNamesbyOrg(String org) {
        Vector v = new Vector();
        try {
            JDBMHashtable users = recman.getHashtable(org);
            JDBMEnumeration e = users.keys();
            while (e.hasMoreElements()) {
                v.addElement(e.nextElement());
            }
        } catch (IOException e1) {
            cat.error("getNamesbyOrg: " + e1);
        }
        String[] s = new String[v.size()];
        v.copyInto(s);
        return s;
    }

    /**
     * get the email addresses of all users in an organization
     * @param org name of the organization (o LDAP field)
     * @return String[] array of email addresses
     */
    public String[] getMailbyOrg(String org) {
        Vector v = new Vector();
        try {
            JDBMHashtable users = recman.getHashtable(org);
            JDBMEnumeration e = users.values();
            while (e.hasMoreElements()) {
                v.addElement(e.nextElement());
            }
        } catch (IOException e1) {
            cat.error("getMailbyOrg: " + e1);
        }
        String[] s = new String[v.size()];
        v.copyInto(s);
        return s;
    }

    /**
     * get list of all UserIds in DB
     * @return String[] list of UserIds
     */
    public String[] getUserIds() {
        String[] mail;
        if (id == null) init();
        ArrayList v = null;
        try {
            JDBMEnumeration e = id.keys();
            v = new ArrayList();
            while (e.hasMoreElements()) {
                v.add(e.nextElement());
            }
        } catch (IOException e1) {
            cat.error("getUserIds: " + e1);
            return new String[0];
        }
        mail = new String[v.size()];
        v.toArray(mail);
        // cat.debug("getUserIds: "+mail.length +" mail: "+mail[0]);
        return mail;
    }

    /** obtain list of valid users
     * @return String[] list of known users
     */
    public String[] getUserNames() {
        String[] names;
        if (id == null) init();
        ArrayList v = null;
        try {
            JDBMEnumeration e = id.values();
            v = new ArrayList();
            while (e.hasMoreElements()) {
                v.add(((User) e.nextElement()).getUserName());
            }
        } catch (IOException e1) {
            cat.error("getUserNames: " + e1);
            return new String[0];
        }
        // for (int i = 0;i< v.size(); i++)
        //      cat.debug("name: "+v.get(i));
        names = new String[v.size()];
        cat.debug("getUserNames: found " + names.length + " elements");
        v.toArray(names);


        return names;
    }

    /**
     * Bean setter  and getter methods
     * @param theConfigFile the properties file
     */

    public static void setConfigFile(String theConfigFile) {
        configFile = theConfigFile;
    }


    /**
     *  set the file of users to be read.
     * @param file to be read
     */
    public static void setUserfile(String file) {
        userfile = file;
    }

    /**
     * get all the users in in the persistent hashtable
     * @return String
     */
    public String export() {
        try {
            JDBMEnumeration c = id.values();
            StringBuffer buff = new StringBuffer();
            while (c.hasMoreElements()) {
                buff.append(c.nextElement().toString() + '\n');
            }
            return buff.toString();
        } catch (IOException e) {
            cat.error("getUsers: " + e);
            return null;
        }
    }

    public boolean checkSecret(String userid, String password, String authType) {
        user = getUser(userid);
        if (user != null)
        {
	        String passwd = user.getPassword();
	        if (passwd == null) return false;
	        return user.checkDigest(passwd, password);
        }
        else return false;
    }
    
    public void logOut() {
    	return;
    }
    
    public static void main(String[] argv) {
        if (argv.length < 1) {
            System.out.println("usage: SimpleUserMgr 'file' where 'file' is a ResourceBundle (file.properties)\n"
                    + "that has an optional property 'users' which is the name of the database to be created or read\n"
                    + "and a property 'login.users' is a ldif text file containing the users to be added.");
            System.exit(0);
        }
        SimpleUserMgr.setConfigFile(argv[0]);
        System.out.println("Config Resource is "+argv[0]+".properties");
        SimpleUserMgr userMgr = new SimpleUserMgr();
        userMgr.init();
        String file = props.getProperty(userfile,"/users.txt");
        System.out.println("User File is "+file);
        if (file != null && file != "") userMgr.addUsers(file);
        System.out.println("Users Added:\n"+userMgr.export());
    }
}
