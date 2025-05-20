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

    /**
     * Check Digest against identity
     *
     * @param digest is digest to be checked against
     * @param identity to be checked
     */
    public boolean checkDigest(String digest, String identity) {
        if (digest.regionMatches(true, 0, "{SHA}", 0, 5)) {
            // ignore the label
            digest = digest.substring(5);
        } else if (digest.regionMatches(true, 0, "{SSHA}", 0, 6)) {
            // ignore the label
            digest = digest.substring(6);
        }
        byte[][] hs = split(Base64.decode(digest.getBytes()), 20);
        byte[] hash = hs[0];
        byte[] salt = hs[1];
        if (sha == null)
            init();
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
}
