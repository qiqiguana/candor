package com.browsersoft.jaas;

import java.util.*;
import java.io.IOException;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;
import com.browsersoft.aacs.Login;
import com.browsersoft.aacs.User;

/**
 * <p> This sample LoginModule authenticates users with a password.
 *
 * <p> This LoginModule only recognizes one user:	testUser
 * <p> testUser's password is:	testPassword
 *
 * <p> If testUser successfully authenticates itself,
 * an <code>AACSPrincipal</code> with the testUser's user name
 * is added to the Subject.
 *
 * <p> This LoginModule recognizes the debug option.
 * If set to true in the login Configuration,
 * debug messages will be output to the output stream, System.out.
 *
 * <p> The config option specifies the AACS Properties file,
 * without the assumed ".properties" suffix.
 *
 * @version $Id: AACSLoginModule.java,v 1.1 2006/11/06 19:52:19 grodecki Exp $
 */
public class AACSLoginModule implements LoginModule {

    public boolean commit() throws LoginException {
        if (succeeded == false) {
            return false;
        } else {
            // add a Principal (authenticated identity)
            // to the Subject
            // add an AACSUserPrincipal to the Subject
            userPrincipal = new AACSUserPrincipal(username);
            if (!subject.getPrincipals().contains(userPrincipal))
                subject.getPrincipals().add(userPrincipal);
            if (debug) {
                System.out.println("\t\t[AACSLoginModule] " + "added AACSUserPrincipal [" + userPrincipal + "] to Subject");
            }
            // add the "aacs_role" to the Subject
            rolePrincipal = new AACSRolePrincipal("aacs_role");
            if (!subject.getPrincipals().contains(rolePrincipal))
                subject.getPrincipals().add(rolePrincipal);
            if (debug) {
                System.out.println("\t\t[AACSLoginModule] " + "added AACSRolePrincipal [" + rolePrincipal + "] to Subject");
            }
            // in any case, clean out state
            username = null;
            for (int i = 0; i < password.length; i++) password[i] = ' ';
            password = null;
            commitSucceeded = true;
            return true;
        }
    }
}
