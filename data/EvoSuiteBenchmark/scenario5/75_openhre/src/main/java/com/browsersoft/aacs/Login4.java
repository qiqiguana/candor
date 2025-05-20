package com.browsersoft.aacs;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import javax.security.auth.login.LoginException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import com.browsersoft.aacs.xacml.PDPadapter;
import com.browsersoft.aacs.xacml.RequestBuilder;
import com.sun.xacml.Indenter;
import com.sun.xacml.ctx.RequestCtx;

/**
 * User class contains all the necessary attributes of a logged in user
 *
 * @author $Author: grodecki $
 * @version $Id: User.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
 */
public class User implements Comparable, Serializable {

    /**
     * set the IpAddr of this user
     *
     * @param ipAddr (should validate the address)
     */
    public void setIpAddr(String ipAddr);
}

/**
 * Interface UserMgr
 */
public interface UserMgr {

    /**
     * @param userid
     * @param password
     * @param authType
     * @return boolean
     */
    public boolean checkSecret(String userid, String password, String authType);

    /**
     * @return User
     */
    public User getUser();
}

public class Login {

    /**
     * Authenticate the user by checking the password
     * and then Authorize them.
     *
     * @return user
     */
    public User authenticate() throws LoginException {
        user = null;
        if (userMgr == null)
            throw new LoginException("UserMgr undefined");
        if (password == null)
            throw new LoginException("Password not supplied");
        if (!userMgr.checkSecret(userid, password, authType)) {
            cat.warn("Invalid Password! User: " + username + " ,userid: " + userid);
            throw new LoginException("Password invalid");
        }
        user = userMgr.getUser();
        user.setIpAddr(this.ipAddr);
        authorize();
        return user;
    }

    /**
     * Authorize a User without checking the password.
     * Note that this should only be called for Users
     * that are already Authenticated.
     * @return user
     */
    public User authorize() throws LoginException;
}
