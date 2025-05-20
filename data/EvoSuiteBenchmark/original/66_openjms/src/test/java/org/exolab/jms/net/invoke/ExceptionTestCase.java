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
 * $Id: ExceptionTestCase.java,v 1.4 2005/05/24 05:50:56 tanderson Exp $
 */
package org.exolab.jms.net.invoke;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.AssertionFailedError;
import junit.framework.Protectable;
import junit.framework.TestCase;

import org.exolab.jms.net.ExceptionService;
import org.exolab.jms.net.ExceptionServiceImpl;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.ORBFactory;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.proxy.RemoteInvocationException;
import org.exolab.jms.net.registry.Registry;
import org.exolab.jms.net.util.SSLUtil;


/**
 * Tests exception handling.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/05/24 05:50:56 $
 */
public abstract class ExceptionTestCase extends TestCase {

    /**
     * The export URI.
     */
    private final String _uri;

    /**
     * The route URI.
     */
    private final String _routeURI;

    /**
     * Connection properties used when establishing a connection to the remote
     * ORB. May be <code>null</code>
     */
    private final Map _connectionProps;

    /**
     * Connection properties used when constructing the local ORB. May
     * be <code>null</code>
     */
    private final Map _acceptorProps;
    
    /**
     * The ORB.
     */
    private ORB _orb;

    /**
     * The exception service proxy.
     */
    private ExceptionService _service;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(ExceptionTestCase.class);

    /**
     * Exception service name.
     */
    private static final String EXCEPTION_SERVICE = "exception";


    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name the name of test case
     * @param uri  the export URI
     */
    public ExceptionTestCase(String name, String uri) {
        this(name, uri, null, null);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name       the name of test case
     * @param uri        the export URI
     * @param routeURI   the route URI
     */
    public ExceptionTestCase(String name, String uri, String routeURI) {
        this(name, uri, routeURI, null, null);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name       the name of test case
     * @param uri        the export URI
     * @param properties connection properties. May be <code>null</code>
     */
    public ExceptionTestCase(String name, String uri, Map properties) {
        this(name, uri, null, properties, properties);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name       the name of test case
     * @param uri        the export URI
     * @param routeURI   the route URI
     * @param properties connection properties. May be <code>null</code>
     */
    public ExceptionTestCase(String name, String uri, String routeURI,
                             Map properties) {
        this(name, uri, routeURI, properties, properties);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name       the name of test case
     * @param uri        the export URI
     * @param routeURI   the route URI
     * @param connectionProps connection properties. May be <code>null</code>
     * @param acceptorProps acceptor properites. May be <code>null</code> 
     */
    public ExceptionTestCase(String name, String uri, String routeURI,
                             Map connectionProps, Map acceptorProps) {
        super(name);
        _uri = uri;
        _routeURI = routeURI;
        _connectionProps = connectionProps;
        _acceptorProps = acceptorProps;
    }

    /**
     * Verifies that a declared <code>Throwable</code> is propagated to the
     * client.
     *
     * @throws Exception for any error
     */
    public void testDeclaredThrowable() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwThrowable();
            }
        };
        checkException(protectable, Throwable.class, null);
    }

    /**
     * Verifies that a declared <code>Exception</code> is propagated to the
     * client.
     *
     * @throws Exception for any error
     */
    public void testDeclaredException() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwException();
            }
        };
        checkException(protectable, Exception.class, null);
    }

    /**
     * Verifies that a declared <code>Error</code> is propagated to the client.
     *
     * @throws Exception for any error
     */
    public void testDeclaredError() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwError();
            }
        };
        checkException(protectable, Error.class, null);
    }

    /**
     * Verifies that an undeclared <code>Error</code> is propagated to the
     * client, wrapped in a {@link RemoteInvocationException}.
     *
     * @throws Exception for any error
     */
    public void testUndeclaredError() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwUndeclaredError();
            }
        };
        checkException(protectable, RemoteInvocationException.class,
                       Error.class);
    }

    /**
     * Verifies that a declared <code>RuntimeException</code> is propagated to
     * the client.
     *
     * @throws Exception for any error
     */
    public void testDeclaredRuntimeException() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwRuntimeException();
            }
        };
        checkException(protectable, RuntimeException.class, null);
    }

    /**
     * Verifies that an undeclared <code>RuntimeException</code> is propagated
     * to the client, wrapped in a {@link RemoteInvocationException}.
     *
     * @throws Exception for any error
     */
    public void testUndeclaredRuntimeException() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwUndeclaredRuntimeException();
            }
        };
        checkException(protectable, RemoteInvocationException.class,
                       RuntimeException.class);
    }

    /**
     * Verifies that a declared <code>RemoteException</code> is propagated to
     * the client.
     *
     * @throws Exception for any error
     */
    public void testDeclaredRemoteException() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwRemoteException();
            }
        };
        checkException(protectable, RemoteException.class, null);
    }

    /**
     * Verifies that an undeclared <code>Error</code> thrown by a method
     * declaring <code>RemoteException</code> is propagated to the client,
     * wrapped in a <code>RemoteException</code>.
     *
     * @throws Exception for any errror
     */
    public void testUndeclaredError2() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwUndeclaredError2();
            }
        };
        checkException(protectable, RemoteException.class, Error.class);
    }

    /**
     * Verifies that an undeclared <code>RuntimeException</code> thrown by a
     * method declaring <code>RemoteException</code> is propagated to the
     * client, wrapped in a <code>RemoteException</code>.
     *
     * @throws Exception for any errror
     */
    public void testUndeclaredRuntimeException2() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwUndeclaredRuntimeException2();
            }
        };
        checkException(protectable, RemoteException.class,
                       RuntimeException.class);
    }

    /**
     * Verifies that an undeclared <code>RemoteInvocationException</code> thrown
     * by a method is propagated to the client unchanged.
     *
     * @throws Exception for any errror
     */
    public void testUndeclaredRemoteInvocationException() throws Exception {
        Protectable protectable = new Protectable() {
            public void protect() throws Throwable {
                _service.throwUndeclaredRemoteInvocationException();
            }
        };
        checkException(protectable, RemoteInvocationException.class, null);
    }

    /**
     * Returns connection properties for establishing a connection to the remote
     * ORB.
     *
     * @return the connection properties, or <code>null</code> if the default
     *         connection properties should be used
     * @throws IOException if a store cannot be found
     */
    protected Map getConnectionProperties() throws IOException {
        Map properties = new HashMap();
        properties.put(ORB.PROVIDER_URI, getServerURI());
        if (_connectionProps != null) {
            properties.putAll(_connectionProps);
        }
        return properties;
    }

    /**
     * Returns the acceptor properties to use when accepting connections.
     *
     * @return the acceptor properties, or <code>null</code> if the default
     *         connection properties should be used
     * @throws Exception for any error
     */
    protected Map getAcceptorProperties() throws Exception {
        Map properties = new HashMap();
        properties.put(ORB.PROVIDER_URI, _uri);
        if (_acceptorProps != null) {
            properties.putAll(_acceptorProps);
        }
        return properties;
    }

    /**
     * Helper to return the server URI.
     *
     * @return the server URI
     */
    protected String getServerURI() {
        return (_routeURI != null) ? _routeURI : _uri;
    }

    /**
     * Sets up the test case.
     *
     * @throws Exception for any error
     */
    protected void setUp() throws Exception {
        _log.debug("setUp() [test=" + getName() + ", uri=" + _uri + "]");
        _orb = ORBFactory.createORB(getAcceptorProperties());
        if (_routeURI != null) {
            _orb.addRoute(_uri, _routeURI);
        }
        Registry serverRegistry = _orb.getRegistry();

        Proxy proxy = _orb.exportObject(new ExceptionServiceImpl());
        serverRegistry.bind(EXCEPTION_SERVICE, proxy);

        // get a proxy to the registry, and look up the service
        Registry clientRegistry = _orb.getRegistry(getConnectionProperties());
        _service = (ExceptionService) clientRegistry.lookup(EXCEPTION_SERVICE);
    }

    /**
     * Cleans up the test case.
     *
     * @throws Exception for any error
     */
    protected void tearDown() throws Exception {
        _log.debug("tearDown() [test=" + getName() + ", uri=" + _uri + "]");
        _orb.shutdown();

        // reset any SSL properties that may have been set.
        SSLUtil.clearProperties();
    }

    /**
     * Verifies that a method throws an exception of the expected type.
     *
     * @param protectable wraps the method to invoke
     * @param expected    the expected type of the exception
     * @param nested      the expected type of the nested exception, or
     *                    <code>null</code>, if no nested exception is expected
     */
    private void checkException(Protectable protectable, Class expected,
                                Class nested) {
        try {
            protectable.protect();
            fail("Expected exception of type=" + expected.getName()
                 + " to be thrown");
        } catch (RemoteInvocationException exception) {
            checkExceptionType(exception, expected);
            if (nested != null) {
                checkNestedExceptionType(exception.getTargetException(),
                                         nested);
            }
        } catch (RemoteException exception) {
            checkExceptionType(exception, expected);
            if (nested != null) {
                checkNestedExceptionType(exception.detail, nested);
            }
        } catch (AssertionFailedError error) {
            throw error;
        } catch (Throwable throwable) {
            checkExceptionType(throwable, expected);
        }
    }

    /**
     * Verifies that an exception is of the expected type.
     *
     * @param exception the exception to check
     * @param expected  the expected exception type
     */
    private void checkExceptionType(Throwable exception, Class expected) {
        Class actual = exception.getClass();
        if (!actual.equals(expected)) {
            fail("Expected exception of type=" + expected.getName()
                 + " to be thrown, but got type=" + actual.getName()
                 + ", message=" + exception.getMessage());
        }
    }

    /**
     * Verifies that a nested exception is of the expected type.
     *
     * @param exception the exception to check
     * @param expected  the expected exception type
     */
    private void checkNestedExceptionType(Throwable exception,
                                          Class expected) {
        Class actual = exception.getClass();
        if (!actual.equals(expected)) {
            fail("Expected nested exception of type=" + expected.getName()
                 + " but got type=" + actual.getClass().getName()
                 + ", message=" + exception.getMessage());
        }
    }

}
