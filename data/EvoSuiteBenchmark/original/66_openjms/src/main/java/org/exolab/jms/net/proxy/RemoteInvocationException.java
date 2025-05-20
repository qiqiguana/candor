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
 * $Id: RemoteInvocationException.java,v 1.1 2004/11/26 01:51:05 tanderson Exp $
 */
package org.exolab.jms.net.proxy;

import java.io.PrintStream;
import java.io.PrintWriter;


/**
 * A <code>RuntimeException</code> when a remote invocation generates an
 * exception which is not declared by the caller
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2004/11/26 01:51:05 $
 * @see Proxy
 */
public class RemoteInvocationException extends RuntimeException {

    /**
     * The thrown target exception
     */
    private Throwable _target;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;


    /**
     * Construct a new <code>RemoteInvocationException</code>
     */
    public RemoteInvocationException() {
    }

    /**
     * Construct a new <code>RemoteInvocationException</code> with no target
     * exception
     *
     * @param detail the detail message
     */
    public RemoteInvocationException(String detail) {
        super(detail);
    }

    /**
     * Construct a new <code>RemoteInvocationException</code> with a target
     * exception
     *
     * @param target the target exception
     */
    public RemoteInvocationException(Throwable target) {
        _target = target;
    }

    /**
     * Construct a new <code>RemoteInvocationException</code> with a detail
     * message and target exception
     *
     * @param detail the detail message
     * @param target the target exception
     */
    public RemoteInvocationException(String detail, Throwable target) {
        super(detail);
        _target = target;
    }

    /**
     * Get the thrown target exception
     *
     * @return the target exception
     */
    public Throwable getTargetException() {
        return _target;
    }

    /**
     * Writes the stack trace of the thrown target exception to the specified
     * print stream.
     *
     * @param stream the stream to write to
     */
    public void printStackTrace(PrintStream stream) {
        synchronized (stream) {
            if (_target != null) {
                stream.print(getClass().getName() + ": ");
                _target.printStackTrace(stream);
            } else {
                super.printStackTrace(stream);
            }
        }
    }

    /**
     * Writes the stack trace of the thrown target exception to the specified
     * print writer.
     *
     * @param writer the writer to write to
     */
    public void printStackTrace(PrintWriter writer) {
        synchronized (writer) {
            if (_target != null) {
                writer.print(getClass().getName() + ": ");
                _target.printStackTrace(writer);
            } else {
                super.printStackTrace(writer);
            }
        }
    }

}
