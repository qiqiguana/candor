/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2003-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 */
package org.exolab.jms.authentication;

import java.security.Principal;

import org.exolab.jms.common.security.BasicPrincipal;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.service.Service;


/**
 * This is the active authentication component within the JMS server.
 *
 * @author <a href="mailto:knut@lerpold">Knut Lerpold</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 05:00:24 $
 */
public class AuthenticationMgr extends Service implements Authenticator {

    /**
     * The user manager.
     */
    private final UserManager _users;


    /**
     * Construct a new <code>AuthenticationManager</code>.
     *
     * @param users the user manager
     */
    public AuthenticationMgr(UserManager users) {
        super("AuthenticationMgr");
        if (users == null) {
            throw new IllegalArgumentException("Argument 'users' is null");
        }
        _users = users;
    }

    /**
     * Create a user.
     *
     * @param user the user to create
     * @return <code>true</code> if the user is created otherwise
     *         <code>false</code>
     */
    public boolean addUser(User user) {
        return _users.createUser(user);
    }

    /**
     * Remove this user
     *
     * @param user the user to remove
     * @return <code>true</code> if the user is removed otherwise
     *         <code>false</code>
     */
    public boolean removeUser(User user) {
        return _users.deleteUser(user);
    }

    /**
     * Update a user.
     *
     * @param user the user to update
     * @return <code>true</code> if the password is updated otherwise
     *         <code>false</code>
     */
    public boolean updateUser(User user) {
        return _users.updateUser(user);
    }

    /**
     * Validate the password for the specified user.
     *
     * @param username the user's name
     * @param password the password to check
     * @return <code>true</code> if the username and password exist, otherwise
     *         <code>false</code>
     */
    public boolean validateUser(String username, String password) {
        return _users.validateUser(username, password);
    }

    /**
     * Determines if a principal has permissions to connect
     *
     * @param principal the principal to check
     * @return <code>true</code> if the principal has permissions to connect
     */
    public boolean authenticate(Principal principal) {
        String user = null;
        String password = null;
        if (principal instanceof BasicPrincipal) {
            BasicPrincipal basic = (BasicPrincipal) principal;
            user = basic.getName();
            password = basic.getPassword();
        } else {
            // treat everything else as an unauthenticated/unknown user
        }
        return validateUser(user, password);
    }
}
