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
 * $Id: PoolEntry.java,v 1.2 2005/05/24 06:01:36 tanderson Exp $
 */
package org.exolab.jms.net.connector;


/**
 * An entry in the {@link ConnectionPool}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/05/24 06:01:36 $
 */
class PoolEntry {

    /**
     * The connection.
     */
    private ManagedConnection _connection;

    /**
     * If <code>true</code>, the connection was accepted via an {@link
     * ManagedConnectionAcceptor}, otherwise it was created via {@link
     * ManagedConnectionFactory#createManagedConnection}
     */
    private final boolean _accepted;

    /**
     * Determines if the connection has been initialised.
     */
    private boolean _initialised = false;


    /**
     * Construct a new <code>PoolEntry</code>.
     *
     * @param connection the managed connection
     * @param accepted  if <code>true</code> the connection was accepted via an
     *                  {@link ManagedConnectionAcceptor}, otherwise it was
     *                  created via
     *                  {@link ManagedConnectionFactory#createManagedConnection}
     */
    public PoolEntry(ManagedConnection connection, boolean accepted) {
        _connection = connection;
        _accepted = accepted;
    }

    /**
     * Returns the connection
     *
     * @return the connection
     */
    public ManagedConnection getManagedConnection() {
        return _connection;
    }

    /**
     * Determines if the connection was accepted
     *
     * @return <code>true</code> if the connection was accepted via an {@link
     *         ManagedConnectionAcceptor}, otherwise <code>false</code>,
     *         indicating it was created via
     *         {@link ManagedConnectionFactory#createManagedConnection}
     */
    public boolean getAccepted() {
        return _accepted;
    }

    /**
     * Determines if the connection has been initialised.
     * An initialised connection may be reaped.
     *
     * @return <code>true</code> if the connection has been initialised
     */
    public synchronized boolean isInitialised() {
        return _initialised;
    }

    /**
     * Marks the connection as being initialised.
     */
    public synchronized void setInitialised() {
        _initialised = true;
    }

}
