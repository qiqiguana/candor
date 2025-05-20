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

    // initial state
    private Subject subject;

    private CallbackHandler callbackHandler;

    // shared between the LoginModules
    private Map sharedState;

    private Map options;

    // configurable option
    private boolean debug = false;

    // the authentication status
    private boolean succeeded = false;

    private boolean commitSucceeded = false;

    // username and password
    private String username;

    private char[] password;

    // Principals
    private AACSUserPrincipal userPrincipal;

    private AACSRolePrincipal rolePrincipal;

    // AACS Beans
    private User user;

    private Login login;

    // Default and actual AACS properties file
    private final static String aacsProps = "/AACS";

    private static String propsfile = null;

    /**
     *  Initialize this <code>LoginModule</code>.
     *
     *  <p>
     *
     *  @param subject the <code>Subject</code> to be authenticated. <p>
     *
     *  @param callbackHandler a <code>CallbackHandler</code> for communicating
     * 			with the end user (prompting for user names and
     * 			passwords, for example). <p>
     *
     *  @param sharedState shared <code>LoginModule</code> state. <p>
     *
     *  @param options options specified in the login
     * 			<code>Configuration</code> for this particular
     * 			<code>LoginModule</code>.
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options);

    /**
     *  Authenticate the user with a user name and password.
     *
     *  <p>
     *
     *  @return true in all cases since this <code>LoginModule</code>
     * 		should not be ignored.
     *
     *  @exception FailedLoginException if the authentication fails. <p>
     *
     *  @exception LoginException if this <code>LoginModule</code>
     * 		is unable to perform the authentication.
     */
    public boolean login() throws LoginException;

    /**
     *  <p> This method is called if the LoginContext's
     *  overall authentication succeeded
     *  (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     *  succeeded).
     *
     *  <p> If this LoginModule's own authentication attempt
     *  succeeded (checked by retrieving the private state saved by the
     *  <code>login</code> method), then this method associates a
     *  <code>AACSUserPrincipal</code> and <code>AACSRolePrincipal</code>
     *  with the <code>Subject</code> located in the
     *  <code>LoginModule</code>.  If this LoginModule's own
     *  authentication attempted failed, then this method removes
     *  any state that was originally saved.
     *
     *  <p>
     *
     *  @exception LoginException if the commit fails.
     *
     *  @return true if this LoginModule's own login and commit
     * 		attempts succeeded, or false otherwise.
     */
    public boolean commit() throws LoginException;

    /**
     *  <p> This method is called if the LoginContext's
     *  overall authentication failed.
     *  (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     *  did not succeed).
     *
     *  <p> If this LoginModule's own authentication attempt
     *  succeeded (checked by retrieving the private state saved by the
     *  <code>login</code> and <code>commit</code> methods),
     *  then this method cleans up any state that was originally saved.
     *
     *  <p>
     *
     *  @exception LoginException if the abort fails.
     *
     *  @return false if this LoginModule's own login and/or commit attempts
     * 		failed, and true otherwise.
     */
    public boolean abort() throws LoginException;

    /**
     * Logout the user.
     *
     * <p> This method removes the <code>AACSPrincipal</code>
     * that was added by the <code>commit</code> method.
     *
     * <p>
     *
     * @exception LoginException if the logout fails.
     *
     * @return true in all cases since this <code>LoginModule</code>
     *          should not be ignored.
     */
    public boolean logout() throws LoginException;
}
