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
 * Copyright 2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ExceptionServiceImpl.java,v 1.2 2005/05/24 05:50:56 tanderson Exp $
 */
package org.exolab.jms.net;

import java.rmi.RemoteException;

import org.exolab.jms.net.proxy.RemoteInvocationException;


/**
 * Service which throws exceptions, for testing purposes.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/05/24 05:50:56 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class ExceptionServiceImpl implements ExceptionService {

    /**
     * Throws a <code>Throwable</code>.
     *
     * @throws Throwable when invoked
     */
    public void throwThrowable() throws Throwable {
        throw new Throwable("ExceptionService");
    }
    
    /**
     * Throws an <code>Exception</code>.
     *
     * @throws Exception when invoked
     */
    public void throwException() throws Exception {
        throw new Exception("ExceptionService");
    }

    /**
     * Throws an <code>Error</code> when invoked.
     *
     * @throws Error when invoked
     */
    public void throwError() throws Error {
        throw new Error("ExceptionService");
    }

    /**
     * Throws an <code>Error</code> when invoked.
     *
     * @throws Error when invoked
     */
    public void throwUndeclaredError() {
        throw new Error("ExceptionService");
    }

    /**
     * Throws an <code>Error</code> when invoked.
     *
     * @throws Error when invoked
     * @throws RemoteException will wrap the error when propagated to the
     * client
     */
    public void throwUndeclaredError2() throws RemoteException {
        throw new Error("ExceptionService");
    }

    /**
     * Throws a <code>RuntimeException</code> when invoked.
     *
     * @throws RuntimeException when invoked
     */
    public void throwRuntimeException() throws RuntimeException {
        throw new RuntimeException("ExceptionService");
    }

    /**
     * Throws a <code>RuntimeException</code> when invoked.
     *
     * @throws RuntimeException when invoked
     */
    public void throwUndeclaredRuntimeException() {
        throw new RuntimeException("ExceptionService");
    }

    /**
     * Throws a <code>RuntimeException</code> when invoked.
     *
     * @throws RuntimeException when invoked
     * @throws RemoteException will wrap the exception when propagated to the
     * client
     */
    public void throwUndeclaredRuntimeException2() throws RemoteException {
        throw new RuntimeException("ExceptionService");
    }

    /**
     * Throws a <code>RemoveInvocationException</code> when invoked.
     *
     * @throws RemoteInvocationException when invoked
     */
    public void throwUndeclaredRemoteInvocationException() {
        throw new RemoteInvocationException("ExceptionService");
    }

    /**
     * Throws a <code>RemoteException</code> when invoked.
     *
     * @throws RemoteException when invoked
     */
    public void throwRemoteException() throws RemoteException {
        throw new RemoteException("ExceptionService");
    }

}
