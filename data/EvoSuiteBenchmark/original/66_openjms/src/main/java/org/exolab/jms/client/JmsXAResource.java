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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsXAResource.java,v 1.1 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.exolab.jms.server.ServerSession;

/**
 * Client implementation of the <code>javax.transaction.xa.XAResource</code>
 * interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/03/18 03:36:37 $
 * @see JmsXASession
 * @see JmsXAQueueSession
 * @see JmsXATopicSession
 */
class JmsXAResource implements XAResource {

    /**
     * The server session proxy.
     */
    private ServerSession _session;

    /**
     * The resource manager identifier, cached on first query.
     */
    private String _rmId = null;


    /**
     * Construct a new <code>JmsXAResource</code>.
     *
     * @param session the server session proxy
     */
    public JmsXAResource(ServerSession session) {
        if (session == null) {
            throw new IllegalArgumentException("Argument 'session' is null");
        }
        _session = session;

    }

    /**
     * Starts work on behalf of a transaction branch specified in xid. If TMJOIN
     * is specified, the start applies to joining a transaction previously seen
     * by the resource manager. If TMRESUME is specified, the start applies to
     * resuming a suspended transaction specified in the parameter xid. If
     * neither TMJOIN nor TMRESUME is specified and the transaction specified by
     * xid has previously been seen by the resource manager, the resource
     * manager throws the XAException exception with XAER_DUPID error code.
     *
     * @param xid   a global transaction identifier to be associated with the
     *              resource.
     * @param flags one of TMNOFLAGS, TMJOIN, or TMRESUME.
     * @throws XAException for any error
     */
    public void start(Xid xid, int flags)
            throws XAException {
        _session.start(xid, flags);
    }

    /**
     * Ask the resource manager to prepare for a transaction commit of the
     * transaction specified in xid.
     *
     * @param xid a global transaction identifier
     * @return a value indicating the resource manager's vote on the outcome of
     *         the transaction. The possible values are: XA_RDONLY or XA_OK. If
     *         the resource manager wants to roll back the transaction, it
     *         should do so by raising an appropriate XAException in the prepare
     *         method.
     * @throws XAException for amy error
     */
    public int prepare(Xid xid) throws XAException {
        return _session.prepare(xid);
    }

    /**
     * Commits the global transaction specified by xid.
     *
     * @param xid      a global transaction identifier
     * @param onePhase If true, the resource manager should use a one-phase
     *                 commit protocol to commit the work done on behalf of
     *                 xid.
     * @throws XAException for any error
     */
    public void commit(Xid xid, boolean onePhase)
            throws XAException {
        _session.commit(xid, onePhase);
    }


    /**
     * Ends the work performed on behalf of a transaction branch. The resource
     * manager disassociates the XA resource from the transaction branch
     * specified and lets the transaction complete.
     *
     * @param xid   a global transaction identifier that is the same as the
     *              identifier used previously in the start method.
     * @param flags One of TMSUCCESS, TMFAIL, or TMSUSPEND.
     * @throws XAException for amy error
     */
    public void end(Xid xid, int flags)
            throws XAException {
        _session.end(xid, flags);
    }

    /**
     * Tells the resource manager to forget about a heuristically completed
     * transaction branch.
     *
     * @param xid a global transaction identifier.
     * @throws XAException for any error
     */
    public void forget(Xid xid)
            throws XAException {
        _session.forget(xid);
    }

    /**
     * Obtains the current transaction timeout value set for this XAResource
     * instance. If XAResource.setTransactionTimeout was not used prior to
     * invoking this method, the return value is the default timeout set for the
     * resource manager; otherwise, the value used in the previous
     * setTransactionTimeout call is returned.
     *
     * @return the transaction timeout value in seconds.
     * @throws XAException for any error
     */
    public int getTransactionTimeout()
            throws XAException {
        return _session.getTransactionTimeout();
    }

    /**
     * Sets the current transaction timeout value for this XAResource instance.
     * Once set, this timeout value is effective until setTransactionTimeout is
     * invoked again with a different value. To reset the timeout value to the
     * default value used by the resource manager, set the value to zero. If the
     * timeout operation is performed successfully, the method returns true;
     * otherwise false. If a resource manager does not support explicitly
     * setting the transaction timeout value, this method returns false.
     *
     * @param seconds the transaction timeout value in seconds.
     * @return true if the transaction timeout value is set successfully;
     *         otherwise false.
     * @throws XAException for any error
     */
    public boolean setTransactionTimeout(int seconds)
            throws XAException {
        return _session.setTransactionTimeout(seconds);
    }

    /**
     * This method is called to determine if the resource manager instance
     * represented by the target object is the same as the resouce manager
     * instance represented by the parameter xares.
     *
     * @param xares an XAResource object whose resource manager instance is to
     *              be compared with the resource manager instance of the target
     *              object.
     * @return true if it's the same RM instance; otherwise false.
     * @throws XAException for any error
     */
    public boolean isSameRM(XAResource xares) throws XAException {
        boolean result = (xares instanceof JmsXAResource);
        if (result) {
            JmsXAResource other = (JmsXAResource) xares;
            result = (other.getResourceManagerId() == getResourceManagerId());
        }

        return result;
    }

    /**
     * Obtains a list of prepared transaction branches from a resource manager.
     * The transaction manager calls this method during recovery to obtain the
     * list of transaction branches that are currently in prepared or
     * heuristically completed states.
     *
     * @param flag one of TMSTARTRSCAN, TMENDRSCAN, TMNOFLAGS. TMNOFLAGS must be
     *             used when no other flags are set in the parameter.
     * @return the resource manager returns zero or more XIDs of the transaction
     *         branches that are currently in a prepared or heuristically
     *         completed state. If an error occurs during the operation, the
     *         resource manager should throw the appropriate XAException.
     * @throws XAException for any error
     */
    public Xid[] recover(int flag)
            throws XAException {
        return _session.recover(flag);
    }

    /**
     * Informs the resource manager to roll back work done on behalf of a
     * transaction branch.
     *
     * @param xid a global transaction identifier
     * @throws XAException for any error
     */
    public void rollback(Xid xid)
            throws XAException {
        _session.rollback(xid);
    }

    /**
     * Return the identity of the associated resource manager. If the value is
     * not cached locally then grab it from the server.
     *
     * @return the identity of the resource manager
     * @throws XAException for any error
     */
    public synchronized String getResourceManagerId() throws XAException {
        if (_rmId == null) {
            _rmId = _session.getResourceManagerId();
        }
        return _rmId;
    }

}
