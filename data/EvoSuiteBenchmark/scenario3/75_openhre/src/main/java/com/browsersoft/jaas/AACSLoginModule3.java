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

    /**
     * Logout the user.
     *
     * <p> This method removes the <code>AACSPrincipal</code>
     * that was added by the <code>commit</code> method.
     *
     * <p>
     *
     * @exception LoginException if the logout fails.
     * @return true in all cases since this <code>LoginModule</code>
     *          should not be ignored.
     */
    public boolean logout() throws LoginException;
}
