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
 *    please contact jima@intalio.com.
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
 * $Id: ServerException.java,v 1.1 2004/11/26 01:51:00 tanderson Exp $
 */
package org.exolab.jms.server;

import org.exolab.jms.service.ServiceException;


/**
 * A general purpose exception to indicate a problem in the JMS server.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:00 $
 * @author      <a href="mailto:tima@intalio">Tim Anderson</a>
 */
public class ServerException extends ServiceException {

    /**
     * Construct a new <code>ServerException</code> with no information.
     */
    public ServerException() {
    }

    /**
     * Construct a new <code>ServerException</code> with a detail message
     *
     * @param message a message describing this exception
     */
    public ServerException(String message) {
        super(message);
    }

    /**
     * Construct a new <code>ServerException</code> with the exception that
     * caused it.
     *
     * @param cause the exception that caused this exception
     */
    public ServerException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct a new <code>ServerException</code> with a detail
     * message and the exception that caused it
     *
     * @param message a message describing this exception
     * @param cause the exception that caused this exception
     */
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

} //-- ServerException
