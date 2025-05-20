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
 *
 * $Id: BasicPrincipal.java,v 1.1 2004/11/26 01:50:34 tanderson Exp $
 */
package org.exolab.jms.common.security;

import java.io.Serializable;
import java.security.Principal;


/**
 * <code>BasicPrincipal</code> associates a user name with a password
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:34 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class BasicPrincipal implements Principal, Serializable {

    /**
     * The principal's name
     */
    private String _name;

    /**
     * The principal's password
     */
    private String _password;

    /**
     * Object version no. for serialization
     */
    static final long serialVersionUID = 1;


    /**
     * Construct a new <code>Principal</code>
     *
     * @param name the principal's name
     * @param password the principal's password
     */
    public BasicPrincipal(String name, String password) {
        if (name == null) {
            throw new IllegalArgumentException("Argument 'name' is null");
        }
        if (password == null) {
            throw new IllegalArgumentException("Argument 'password' is null");
        }
        _name = name;
        _password = password;
    }

    /**
     * Returns the name of this principal
     *
     * @return the name of this principal
     */
    public String getName() {
        return _name;
    }

    /**
     * Returns the password of this principal
     *
     * @return the password of this principal
     */
    public String getPassword() {
        return _password;
    }

    /**
     * Compares this principal to the specified object.
     *
     * @param another principal to compare with
     * @return <code>true</code> if the principal passed in is the same as
     * this principal; otherwise <code>false</code>
     */
    public boolean equals(Object another) {
        boolean equal = (this == another);
        if (!equal && another instanceof BasicPrincipal) {
            BasicPrincipal other = (BasicPrincipal) another;
            if (_name.equals(other.getName())
                && _password.equals(other.getPassword())) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * Returns a hashcode for this principal.
     *
     * @return a hashcode for this principal.
     */
    public int hashCode() {
        return _name.hashCode();
    }

}
