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
}
