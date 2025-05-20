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
                    if (userid != null)
                        user = (User) id.get(userid);
                }
            }
            if (user == null)
                cat.warn("getUser(" + username + "," + org + "," + email + "): user not found, ");
        } catch (IOException e) {
            cat.error("getUser: " + e);
        }
        return user;
    }
}
