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

    public String getPassvalidstart();

    public void setPasschange(boolean passchange);

    public String getPassrenewal();

    public Set getAllowedips();

    /**
     * get the IpAddr of this user
     * @return String containing ipAddr
     */
    public String getIpAddr();

    public Set getGroups();

    public void addActivegroup(String group);

    public Set getActivegroups();
}

/**
 * Interface UserMgr
 */
public interface UserMgr {

    /**
     * @return User
     */
    public User getUser(String userid);
}

public class Group implements DirContext {

    public Set getAllowedips();

    public String getCn();
}

public class LdapService {

    public Group getGroup(String cn);
}

public class Login {

    /**
     * Authorize a User without checking the password.
     * Note that this should only be called for Users
     * that are already Authenticated.
     *
     * @return user
     */
    public User authorize() throws LoginException {
        user = null;
        if (userMgr == null)
            throw new LoginException("UserMgr undefined");
        if (password == null)
            throw new LoginException("Password not supplied");
        user = userMgr.getUser(this.userid);
        user.setIpAddr(this.ipAddr);
        // compare password validity start date with current date
        Date today = new Date();
        Date pwvd = null;
        if ((user.getPassvalidstart() != null) && (!user.getPassvalidstart().equals(""))) {
            pwvd = convertDateFromUTC(user.getPassvalidstart());
            if (!dateCompare(pwvd, today)) {
                log(user, "Not allowed to login currently.");
                user = null;
                throw new LoginException("Not allowed to login currently.");
            }
        }
        // check if user needs to change password before proceeding
        user.setPasschange(false);
        if ((user.getPassvalidstart() != null) && (user.getPassrenewal() != null) && (!user.getPassvalidstart().equals("")) && (!user.getPassrenewal().equals("")) && (!user.getPassrenewal().equals("-1"))) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(pwvd);
            c1.add(Calendar.DATE, Integer.parseInt(user.getPassrenewal()));
            if (dateCompare(c1.getTime(), today)) {
                user.setPasschange(true);
            }
        }
        // Performing IP based filtering and active groups determination
        // based on current location.
        // Make sure that AuthType=krb5 and IPfiltering=yes in the properties file.
        if ((user.getAllowedips() != null) && (props.getProperty("AuthType", "krb5").equals("krb5")) && (props.getProperty("IPfiltering", "yes").equals("yes"))) {
            int currentip = getIpInteger(user.getIpAddr());
            // IP based filtering
            cat.info("Performing IP filtering for " + getUserId());
            boolean ipcheck_result = ipRangeCheck(user.getAllowedips(), currentip);
            if (ipcheck_result == false) {
                log(user, "Not authorized from the current IP address.");
                throw new LoginException("Not authorized from the current IP address.");
            }
            // Active groups determination
            cat.info("Determining active groups for " + getUserId());
            boolean faccheck_result;
            // get all groups from LDAPservice
            Set usergrps = user.getGroups();
            if (usergrps != null && usergrps.size() > 0) {
                // Setup the interface to the LDAP server
                getLdapService();
                //session.setAttribute("ldapservice", lservice);
                Iterator grpit = usergrps.iterator();
                while (grpit.hasNext()) {
                    // Fetch each group's details from LDAP
                    Group grp = (Group) lservice.getGroup(grpit.next().toString());
                    faccheck_result = ipRangeCheck(grp.getAllowedips(), currentip);
                    if (faccheck_result == true) {
                        log(user, "Active group: " + grp.getCn());
                        user.addActivegroup(grp.getCn());
                    }
                }
                if (user.getActivegroups() == null || user.getActivegroups().size() == 0) {
                    log(user, " Accessing from remote location.");
                    user.addActivegroup("remote");
                }
            }
        }
        // end IP based filtering and Active groups determination
        return user;
    }

    private Date convertDateFromUTC(String specdatetime);

    private boolean dateCompare(Date date1, Date date2);

    public void log(User user, String info);

    private int getIpInteger(String ip);

    /**
     * get the current User ID
     */
    public String getUserId();

    private boolean ipRangeCheck(Set allowedips, int currentip);

    /**
     * get the current LdapService
     */
    public LdapService getLdapService();
}
