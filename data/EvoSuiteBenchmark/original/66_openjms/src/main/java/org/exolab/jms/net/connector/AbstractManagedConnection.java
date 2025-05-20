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
 * $Id: AbstractManagedConnection.java,v 1.4 2005/06/04 14:28:52 tanderson Exp $
 */
package org.exolab.jms.net.connector;


/**
 * Abstract implementation of the {@link ManagedConnection} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/06/04 14:28:52 $
 */
public abstract class AbstractManagedConnection implements ManagedConnection {

    /**
     * The event listener for connection events.
     */
    private ManagedConnectionListener _listener;


    /**
     * Construct a new <code>AbstractManagedConnection</code>.
     */
    public AbstractManagedConnection() {
    }

    /**
     * Registers a connection event listener.
     *
     * @param listener the connection event listener
     * @throws ResourceException for any error
     */
    public synchronized void setConnectionEventListener(
            ManagedConnectionListener listener)
            throws ResourceException {
        _listener = listener;
    }

    /**
     * Notifies when the peer closes the physical connection.
     */
    protected void notifyClosed() {
        ManagedConnectionListener listener = getConnectionEventListener();
        if (listener != null) {
            listener.closed(this);
        }
    }

    /**
     * Notifies of an error on the physical connection.
     *
     * @param error the error
     */
    protected void notifyError(Throwable error) {
        ManagedConnectionListener listener = getConnectionEventListener();
        if (listener != null) {
            listener.error(this, error);
        }
    }

    /**
     * Returns the connection event listener.
     *
     * @return the connection event listener, or <code>null</code> if no
     *         listener is registered
     */
    protected synchronized ManagedConnectionListener
            getConnectionEventListener() {
        return _listener;
    }
}
