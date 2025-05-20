/*
 *   CVS $Id: AACSLoginModule.java,v 1.1 2006/11/06 19:52:19 grodecki Exp $
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
    private Map sharedState; // shared between the LoginModules
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
     * Initialize this <code>LoginModule</code>.
     *
     * <p>
     *
     * @param subject the <code>Subject</code> to be authenticated. <p>
     *
     * @param callbackHandler a <code>CallbackHandler</code> for communicating
     *			with the end user (prompting for user names and
     *			passwords, for example). <p>
     *
     * @param sharedState shared <code>LoginModule</code> state. <p>
     *
     * @param options options specified in the login
     *			<code>Configuration</code> for this particular
     *			<code>LoginModule</code>.
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map sharedState, Map options) {
 
	this.subject = subject;
	this.callbackHandler = callbackHandler;
	this.sharedState = sharedState;
	this.options = options;
	
	user = null;
	login = null;

	// initialize any configured options
	debug = "true".equalsIgnoreCase((String)options.get("debug"));
	
	if (propsfile == null) {
		// only do the first time
		propsfile = (String)options.get("config");
		if (propsfile == null) propsfile = aacsProps;
		Login.setConfigFile(propsfile);
		Login.init();
	}
	
	if (debug) System.out.println("AACSLoginModule initialized");
	
    }

    /**
     * Authenticate the user with a user name and password.
     *
     * <p>
     *
     * @return true in all cases since this <code>LoginModule</code>
     *		should not be ignored.
     *
     * @exception FailedLoginException if the authentication fails. <p>
     *
     * @exception LoginException if this <code>LoginModule</code>
     *		is unable to perform the authentication.
     */
    public boolean login() throws LoginException {

    if (debug) System.out.println("AACSLoginModule login() called");

    // prompt for a user name and password
	if (callbackHandler == null)
	    throw new LoginException("Error: no CallbackHandler available " +
			"to garner authentication information from the user");

	Callback[] callbacks = new Callback[2];
	callbacks[0] = new NameCallback("user name: ");
	callbacks[1] = new PasswordCallback("password: ", false);
 
	try {
	    callbackHandler.handle(callbacks);
	    username = ((NameCallback)callbacks[0]).getName();
	    char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
	    if (tmpPassword == null) {
		// treat a NULL password as an empty password
		tmpPassword = new char[0];
	    }
	    password = new char[tmpPassword.length];
	    System.arraycopy(tmpPassword, 0,
			password, 0, tmpPassword.length);
	    ((PasswordCallback)callbacks[1]).clearPassword();
 
	} catch (java.io.IOException ioe) {
	    throw new LoginException(ioe.toString());
	} catch (UnsupportedCallbackException uce) {
	    throw new LoginException("Error: " + uce.getCallback().toString() +
		" not available to garner authentication information " +
		"from the user");
	}

	// print debugging information
	if (debug) {
	    System.out.println("\t\t[AACSLoginModule] " +
				"user entered user name: " +
				username);
	    System.out.print("\t\t[AACSLoginModule] " +
				"user entered password: ");
	    for (int i = 0; i < password.length; i++)
		System.out.print(password[i]);
	    System.out.println();
	}

	// verify the username/password
	login = new Login(username);
	login.setPassword(new String(password));
	user = login.authenticate();
	if (user != null) {
	    if (debug)
		System.out.println("\t\t[AACSLoginModule] " +
				"authentication succeeded");
	    succeeded = true;
	    return true;
	} else {
	    // authentication failed -- clean out state
	    if (debug)
		System.out.println("\t\t[AACSLoginModule] " +
				"authentication failed");
	    succeeded = false;
	    username = null;
	    for (int i = 0; i < password.length; i++)
		password[i] = ' ';
	    password = null;
		throw new FailedLoginException("Login Incorrect");
	}
    }

    /**
     * <p> This method is called if the LoginContext's
     * overall authentication succeeded
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method associates a
     * <code>AACSUserPrincipal</code> and <code>AACSRolePrincipal</code>
     * with the <code>Subject</code> located in the
     * <code>LoginModule</code>.  If this LoginModule's own
     * authentication attempted failed, then this method removes
     * any state that was originally saved.
     *
     * <p>
     *
     * @exception LoginException if the commit fails.
     *
     * @return true if this LoginModule's own login and commit
     *		attempts succeeded, or false otherwise.
     */
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
		System.out.println("\t\t[AACSLoginModule] " +
				"added AACSUserPrincipal ["+userPrincipal+"] to Subject");
	    }
	    // add the "aacs_role" to the Subject
	    rolePrincipal = new AACSRolePrincipal("aacs_role");
	    if (!subject.getPrincipals().contains(rolePrincipal))
	    	subject.getPrincipals().add(rolePrincipal);

	    if (debug) {
		System.out.println("\t\t[AACSLoginModule] " +
				"added AACSRolePrincipal ["+rolePrincipal+"] to Subject");
	    }

	    // in any case, clean out state
	    username = null;
	    for (int i = 0; i < password.length; i++)
	    	password[i] = ' ';
	    password = null;

	    commitSucceeded = true;
	    return true;
	}
    }

    /**
     * <p> This method is called if the LoginContext's
     * overall authentication failed.
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * did not succeed).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> and <code>commit</code> methods),
     * then this method cleans up any state that was originally saved.
     *
     * <p>
     *
     * @exception LoginException if the abort fails.
     *
     * @return false if this LoginModule's own login and/or commit attempts
     *		failed, and true otherwise.
     */
    public boolean abort() throws LoginException {
	if (succeeded == false) {
	    return false;
	} else if (succeeded == true && commitSucceeded == false) {
	    // login succeeded but overall authentication failed
	    succeeded = false;
	    username = null;
	    if (password != null) {
		for (int i = 0; i < password.length; i++)
		    password[i] = ' ';
		password = null;
	    }
	    userPrincipal = null;
	    rolePrincipal = null;
	} else {
	    // overall authentication succeeded and commit succeeded,
	    // but someone else's commit failed
	    logout();
	}
	return true;
    }

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
    public boolean logout() throws LoginException {

	subject.getPrincipals().remove(userPrincipal);
	succeeded = false;
	succeeded = commitSucceeded;
	username = null;
	if (password != null) {
	    for (int i = 0; i < password.length; i++)
	    	password[i] = ' ';
	    password = null;
	}
	userPrincipal = null;
	rolePrincipal = null;
	user =  null;
	login = null;
	return true;
    }
}
