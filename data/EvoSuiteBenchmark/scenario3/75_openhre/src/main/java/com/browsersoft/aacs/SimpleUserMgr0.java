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

    /**
     * get User with username, org and email
     *
     * @param username cn variable
     * @param org o variable
     * @param email email variable
     * @return User
     */
    public User getUser(String username, String org, String email);
}
