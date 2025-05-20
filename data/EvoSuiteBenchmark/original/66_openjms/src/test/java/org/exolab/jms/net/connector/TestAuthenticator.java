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
 * Copyright 2004-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: TestAuthenticator.java,v 1.2 2005/04/17 14:13:40 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.security.Principal;


/**
 * Test implementation of the {@link Authenticator} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/04/17 14:13:40 $
 * @see ManagedConnectionAcceptor
 */
public class TestAuthenticator implements Authenticator {

    /**
     * The expected principals on requests. May be <code>null</code> to indicate
     * the no principals are expected.
     */
    private Principal[] _principals;


    /**
     * Construct a new <code>TestAuthenticator</code>, that rejects
     * all principals.
     */
    public TestAuthenticator() {
    }

    /**
     * Construct a new <code>TestAuthenticator</code>, that authenticates
     * a single principal.
     *
     * @param principal the principal to use. May be <code>null</code>
     */
    public TestAuthenticator(Principal principal) {
        if (principal != null) {
            _principals = new Principal[]{principal};
        }
    }

    /**
     * Construct a new <code>TestAuthenticator</code>. that authenticates
     * a set of principals.
     *
     * @param principals the principals to use. May be <code>null</code>
     */
    public TestAuthenticator(Principal[] principals) {
        _principals = principals;
    }

    /**
     * Determines if a principal has permissions to connect.
     *
     * @param principal the principal to check
     * @return <code>true</code> if the principal has permissions to connect
     * @throws ResourceException if an error occurs
     */
    public boolean authenticate(Principal principal) throws ResourceException {
        boolean result = false;
        if (_principals == null) {
            if (principal == null) {
                result = true;
            }
        } else {
            for (int i = 0; i < _principals.length; ++i) {
                Principal other = _principals[i];
                if ((other == null && principal == null)
                    || (other != null && other.equals(principal))) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

}
