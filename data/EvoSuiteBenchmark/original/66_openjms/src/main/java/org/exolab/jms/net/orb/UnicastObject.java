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
 * $Id: UnicastObject.java,v 1.2 2005/05/27 13:44:45 tanderson Exp $
 */
package org.exolab.jms.net.orb;

import java.rmi.NoSuchObjectException;
import java.rmi.StubNotFoundException;
import java.rmi.server.ExportException;

import org.exolab.jms.net.proxy.Proxy;

/**
 * <code>UnicastObject</code> is a convenience class which may be sublassed from
 * in order to provide remoting of behaviour.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/05/27 13:44:45 $
 */
public abstract class UnicastObject {

    /**
     * The ORB to export this with.
     */
    private ORB _orb;

    /**
     * The ORB to export this on. If <code>null</code>, the ORB default export
     * URI will be used.
     */
    private final String _uri;

    /**
     * The proxy.
     */
    private Proxy _proxy;


    /**
     * Construct a new <code>UnicastObject</code>.
     *
     * @param orb the ORB to export this with
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    protected UnicastObject(ORB orb)
            throws ExportException, StubNotFoundException {
        this(orb, null);
    }

    /**
     * Construct a new <code>UnicastObject</code>.
     *
     * @param orb the ORB to export this with
     * @param uri the URI to export this on. If <code>null</code>, use the ORB
     *            default export URI
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    protected UnicastObject(ORB orb, String uri)
            throws ExportException, StubNotFoundException {
        this(orb, uri, false);
    }

    /**
     * Construct a new <code>UnicastObject</code>.
     *
     * @param orb      the ORB to export this with
     * @param uri      the URI to export this on
     * @param exportTo if <code>false</code>, {@link ORB#exportObject(Object,
     *                 String)} is used to export this. If <code>true</code> and
     *                 <code>uri</code> is non-null,
     *                 {@link ORB#exportObjectTo(Object, String)} is used,
     *                 otherwise {@link ORB#exportObjectTo(Object)} is used.
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    protected UnicastObject(ORB orb, String uri, boolean exportTo)
            throws ExportException, StubNotFoundException {
        if (orb == null) {
            throw new IllegalArgumentException("Argument 'orb' is null");
        }
        if (!exportTo) {
            _proxy = orb.exportObject(this, uri);
        } else if (uri != null) {
            _proxy = orb.exportObjectTo(this, uri);
        } else {
            _proxy = orb.exportObjectTo(this);
        }
        _orb = orb;
        _uri = uri;
    }

    /**
     * Returns a proxy to invoke methods on this.
     *
     * @return a proxy to invoke methods on this
     */
    public Proxy getProxy() {
        return _proxy;
    }

    /**
     * Unexport this object.
     *
     * @throws NoSuchObjectException if the object isn't exported
     */
    public void unexportObject() throws NoSuchObjectException {
        _orb.unexportObject(this);
    }

    /**
     * Returns the ORB.
     *
     * @return the ORB
     */
    protected ORB getORB() {
        return _orb;
    }

    /**
     * Returns the URI this was exported on.
     *
     * @return the URI this was exported on, or <code>null</code> if the ORB
     *         default URI was used.
     */
    protected String getURI() {
        return _uri;
    }
}
