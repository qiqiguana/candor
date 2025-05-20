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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: UnicastDelegate.java,v 1.2 2005/11/16 12:32:49 tanderson Exp $
 */
package org.exolab.jms.net.orb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.server.ObjID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.connector.Connection;
import org.exolab.jms.net.connector.ConnectionContext;
import org.exolab.jms.net.connector.ConnectionFactory;
import org.exolab.jms.net.connector.Request;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.Response;
import org.exolab.jms.net.proxy.Delegate;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URIHelper;


/**
 * <code>UnicastDelegate</code> supports the invocation of methods on a single
 * remote object, over arbitrary transport protocols.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/16 12:32:49 $
 */
public class UnicastDelegate implements Delegate, Serializable {

    /**
     * Serialization version.
     */
    static final long serialVersionUID = 1;

    /**
     * The target object identifier.
     */
    private ObjID _objID;

    /**
     * The connection URI.
     */
    private String _uri;

    /**
     * The connection used to make invocations.
     */
    private transient Connection _connection;

    /**
     * The connection factory.
     */
    private transient ConnectionFactory _factory;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(UnicastDelegate.class);


    /**
     * Default constructor for serialization support.
     */
    protected UnicastDelegate() {
    }

    /**
     * Construct a new <code>UnicastDelegate</code>.
     * <p/>
     * This is intended for use on the server side, to create delegates
     * for serialization to clients.
     *
     * @param objID the identifier of the target object
     * @param uri   the connection URI
     */
    public UnicastDelegate(ObjID objID, String uri) {
        _objID = objID;
        _uri = uri;
    }

    /**
     * Construct a new <code>UnicastDelegate</code>.
     * <p/>
     * This is intended for use on the client side.
     *
     * @param objID      the identifier of the target object
     * @param connection the connection used to make invocations
     */
    public UnicastDelegate(ObjID objID, Connection connection) {
        _objID = objID;
        _connection = connection;
    }

    /**
     * Invoke a method.
     *
     * @param method   the method to invoke
     * @param args     the arguments to pass
     * @param methodID the unique identifier for the method
     * @return the result of the invocation
     * @throws Throwable for any error
     */
    public Object invoke(Method method, Object[] args, long methodID)
            throws Throwable {
        Request request = new Request(_objID, method, args, methodID);
        Response response = getConnection().invoke(request);
        if (response.isException()) {
            throw response.getException();
        }
        return response.getObject();
    }

    /**
     * Dispose the delegate, releasing any resources.
     * <p/>
     * It is an error to invoke any method other than this, after the delegate
     * has been disposed.
     */
    public synchronized void dispose() {
        if (_connection != null) {
            try {
                _connection.close();
            } catch (ResourceException exception) {
                _log.warn("Failed to close connection", exception);
            } finally {
                _connection = null;
                _factory = null;
            }
        }
    }

    /**
     * Returns the connection to perform invocations.
     *
     * @return the connection to perform invocations
     * @throws InvalidURIException if the URI is invalid
     * @throws ResourceException   if a connection cannot be created
     */
    protected synchronized Connection getConnection()
            throws InvalidURIException, ResourceException {
        if (_connection == null) {
            _connection = _factory.getConnection(null, URIHelper.parse(_uri));
        }
        return _connection;
    }

    /**
     * Write this to a stream.
     *
     * @param out the stream to write to
     * @throws IOException for any I/O error
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * Read the state of this from a stream.
     *
     * @param in the stream to read from
     * @throws ClassNotFoundException if a class cannot be deserialized
     * @throws IOException            for any I/O error
     */
    private void readObject(ObjectInputStream in)
            throws ClassNotFoundException, IOException {
        in.defaultReadObject();

        // try and get a reference to the connection
        _connection = ConnectionContext.getConnection(URIHelper.parse(_uri));
        if (_connection == null) {
            // cache the factory, so the connection can be obtained when
            // needed
            _factory = ConnectionContext.getConnectionFactory();
        }
    }

}
