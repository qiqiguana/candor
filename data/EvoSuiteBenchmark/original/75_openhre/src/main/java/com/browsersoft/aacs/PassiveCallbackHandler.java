/*
 *   CVS $Id: PassiveCallbackHandler.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
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
package com.browsersoft.aacs;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * @author gsharma
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * CallbackHandler which passes the user's credentials obtained via the web interface to the kerberos server
 */
public class PassiveCallbackHandler implements CallbackHandler {

	private String user = "";
    private String pass = "";
    
	public PassiveCallbackHandler(String username, String passwd) {
		user = username;
		pass = passwd;
	}
	
	public void handle(Callback[] callbacks)
    throws java.io.IOException, UnsupportedCallbackException {
        
		for (int i = 0; i < callbacks.length; i++) {
            
        	if (callbacks[i] instanceof NameCallback) {
                NameCallback nameCb = (NameCallback)callbacks[i];
                nameCb.setName(user);
            } 
            
            else if(callbacks[i] instanceof PasswordCallback) {
                PasswordCallback passCb = (PasswordCallback)callbacks[i];
                passCb.setPassword(pass.toCharArray());
            }
            
            else {
                throw(new UnsupportedCallbackException(callbacks[i],
                    "Callback class not supported"));
             }
        }
    }
}
