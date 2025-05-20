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
 * Copyright 1999-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ServiceException.java,v 1.1 2004/11/26 01:51:01 tanderson Exp $
 */
package org.exolab.jms.service;

import java.io.PrintStream;
import java.io.PrintWriter;


/**
 * This exception is thrown whenever there is a general exception with a 
 * service entitiy
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:01 $
 * @author      <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class ServiceException extends Exception {

    /**
     * The exception that caused this exception. May be <code>null</code>
     */
    private Throwable _cause;


    /**
     * Construct a new <code>ServiceException</code> with no information.
     */
    public ServiceException() {
        this(null, null);
    }

    /**
     * Construct a new <code>ServiceException</code> with a detail message
     *
     * @param message a message describing this exception
     */
    public ServiceException(String message) {
        this(message, null);
    }

    /**
     * Construct a new <code>ServiceException</code> with the exception that
     * caused it.
     *
     * @param cause the exception that caused this exception
     */
    public ServiceException(Throwable cause) {
        this(null, cause);
    }

    /**
     * Construct a new <code>ServiceException</code> with a detail
     * message and the exception that caused it
     *
     * @param message a message describing this exception
     * @param cause the exception that caused this exception
     */
    public ServiceException(String message, Throwable cause) {
        super(message);
        _cause = cause;
    }

    /**
     * Returns the exception that caused this exception
     *
     * @return the exception that caused this exception. 
     * May be <code>null</code>
     */
    public Throwable getRootCause() {
	return _cause;
    }

    /**
     * Prints the stack trace of the thrown exception to the standard error
     * stream
     */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /**
     * Prints the stack trace of the thrown exception to the specified
     * print stream.
     */
    public void printStackTrace(PrintStream stream) {
	synchronized (stream) {
	    if (_cause != null) {
		stream.print(getClass().getName() + ": ");
		_cause.printStackTrace(stream);
	    } else {
		super.printStackTrace(stream);
	    }
	}
    }

    /**
     * Prints the stack trace of the thrown throwable exception to the
     * specified print writer.
     */
    public void printStackTrace(PrintWriter writer) {
	synchronized (writer) {
	    if (_cause != null) {
		writer.print(getClass().getName() + ": ");
		_cause.printStackTrace(writer);
	    } else {
		super.printStackTrace(writer);
	    }
	}
    }

}
