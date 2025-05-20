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
 * $Id: DefaultORB.java,v 1.14 2008/01/07 13:00:16 tanderson Exp $
 */
package org.exolab.jms.net.orb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.server.ExportException;
import java.security.Principal;
import java.util.Map;

import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.common.security.BasicPrincipal;
import org.exolab.jms.common.threads.DefaultThreadPoolFactory;
import org.exolab.jms.net.connector.AbstractConnectionManager;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.connector.CallerListener;
import org.exolab.jms.net.connector.Connection;
import org.exolab.jms.net.connector.Invocation;
import org.exolab.jms.net.connector.InvocationHandler;
import org.exolab.jms.net.connector.MulticastCallerListener;
import org.exolab.jms.net.connector.Request;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.Response;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.registry.LocalRegistry;
import org.exolab.jms.net.registry.Registry;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.common.threads.ThreadPoolFactory;
import org.exolab.jms.net.util.MethodHelper;
import org.exolab.jms.net.util.Properties;


/**
 * The <code>DefaultORB</code> class manages exported objects.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.14 $ $Date: 2008/01/07 13:00:16 $
 */
class DefaultORB extends AbstractORB {

    /**
     * The registry.
     */
    private LocalRegistry _registry;

    /**
     * The connection manager.
     */
    private AbstractConnectionManager _manager;

    /**
     * The caller event listeners.
     */
    private MulticastCallerListener _listeners;

    /**
     * The thread pool factory.
     */
    private ThreadPoolFactory _factory;

    /**
     * The thread pool for scheduling invocation requests.
     */
    private PooledExecutor _pool;

    /**
     * The maximum no. of threads to use in the thread pool.
     */
    private int _maxThreads;

    /**
     * Synchronization helper for accessing _pool.
     */
    private final Object _poolLock = new Object();

    /**
     * Current caller.
     */
    private final ThreadLocal _caller = new ThreadLocal();

    /**
     * Constant that holds the name of the connection property for specifying
     * the maximum no. of threads to use for servicing invocations.
     */
    private static final String MAX_THREADS_NAME
            = "org.exolab.jms.net.orb.threads.max";

    /**
     * Constant that holds the name of the connection property for specifying
     * the thread pool factory.
     */
    private static final String THREAD_POOL_FACTORY
            = "org.exolab.jms.net.orb.threads.factory";

    /**
     * Default max no. of threads to service invocations, if none is specified.
     */
    private static final int MAX_THREADS = Integer.MAX_VALUE;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(DefaultORB.class);


    /**
     * Construct a new <code>DefaultORB</code> with the connection
     * authenticator. All proxies will be loaded using this instance's class
     * loader.
     *
     * @param authenticator the connection authenticator
     * @throws RemoteException for any error
     */
    public DefaultORB(Authenticator authenticator)
            throws RemoteException {
        this(authenticator, DefaultORB.class.getClassLoader(), null);
    }

    /**
     * Construct a new <code>DefaultORB</code> with the connection
     * authenticator, and properties to configure the ORB. All proxies will be
     * loaded using this instance's class loader.
     *
     * @param authenticator the connection authenticator
     * @param properties    properties to configure the ORB. May be
     *                      <code>null</code>
     * @throws RemoteException for any error
     */
    public DefaultORB(Authenticator authenticator, Map properties)
            throws RemoteException {
        this(authenticator, DefaultORB.class.getClassLoader(), properties);
    }

    /**
     * Construct a new <code>DefaultORB</code> with properties to configure the
     * ORB. Connections will be unauthenticated. All proxies will be loaded
     * using this instance's class loader.
     *
     * @throws RemoteException for any error
     */
    public DefaultORB(Map properties) throws RemoteException {
        this(new DummyAuthenticator(), DefaultORB.class.getClassLoader(),
                properties);
    }

    /**
     * Construct a new <code>DefaultORB</code>. Connections will be
     * unauthenticated. All proxies will be loaded using this instance's class
     * loader.
     *
     * @throws RemoteException for any error
     */
    public DefaultORB() throws RemoteException {
        this(new DummyAuthenticator(), DefaultORB.class.getClassLoader(),
                null);
    }

    /**
     * Construct a new <code>DefaultORB</code> with the connection
     * authenticator, the class loader used to load proxies, and properties to
     * configure the ORB.
     *
     * @param authenticator the connection authenticator
     * @param loader        the class loader to load proxies
     * @param properties    properties to configure the ORB. May be
     *                      <code>null</code>
     * @throws RemoteException for any error
     */
    public DefaultORB(Authenticator authenticator, ClassLoader loader,
                      Map properties)
            throws RemoteException {
        super(loader, properties);
        if (authenticator == null) {
            throw new IllegalArgumentException(
                    "Argument 'authenticator' is null");
        }

        Properties helper = new Properties(properties, null);
        try {
            _maxThreads = helper.getInt(MAX_THREADS_NAME, MAX_THREADS);
        } catch (ResourceException exception) {
            throw new RemoteException("Failed to construct thread pool",
                    exception);
        }

        _factory = (ThreadPoolFactory) helper.getProperties().get(
                THREAD_POOL_FACTORY);
        if (_factory == null) {
            _factory = new DefaultThreadPoolFactory(null);
        }

        try {
            _manager = createConnectionManager(new Handler(), authenticator);
        } catch (ResourceException exception) {
            throw new RemoteException("Failed to construct connection manager",
                    exception);
        }
    }

    /**
     * Returns a reference to the registry service.
     *
     * @return the registry service
     * @throws RemoteException if the service cannot be exported
     */
    public synchronized LocalRegistry getRegistry() throws RemoteException {
        if (_registry == null) {
            _registry = new RegistryService(this);
        }
        return _registry;
    }

    /**
     * Returns a reference to a remote registry service.
     *
     * @param properties the connection properties.
     * @return the registry service
     * @throws RemoteException for any error
     */
    public Registry getRegistry(Map properties) throws RemoteException {
        if (properties == null || properties.get(PROVIDER_URI) == null) {
            throw new ConnectException(PROVIDER_URI + " not specified");
        }
        Registry registry;
        String uri = (String) properties.get(PROVIDER_URI);
        String principal = (String) properties.get(SECURITY_PRINCIPAL);
        String credentials = (String) properties.get(SECURITY_CREDENTIALS);
        Principal subject = null;

        if (principal != null) {
            subject = new BasicPrincipal(principal, credentials);
        }

        try {
            registry = Locator.getRegistry(subject, uri, _manager,
                    getProxyClassLoader(),
                    properties);
        } catch (InvalidURIException exception) {
            throw new RemoteException("Invalid URI: " + uri, exception);
        }
        return registry;
    }

    /**
     * Export an object to the current remote caller. Only the remote caller may
     * perform invocations.
     *
     * @param object the object to export
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    public Proxy exportObjectTo(Object object) throws ExportException,
            StubNotFoundException {
        Caller caller = (Caller) _caller.get();
        if (caller == null) {
            throw new ExportException("Cannot export - no current caller");
        }
        return doExportTo(object, caller.getLocalURI());
    }

    /**
     * Unexport an object.
     *
     * @param object the object to export
     * @throws java.rmi.NoSuchObjectException if the object isn't exported
     */
    public synchronized void unexportObject(Object object)
            throws NoSuchObjectException {
        super.unexportObject(object);
        if (getExported() == 0) {
            // no more exported objects, so shutdown the thread pool.
            // The other alternative is to reduce the keep alive time for
            // unused threads however this is more likely to require
            // more resources over time.
            synchronized (_poolLock) {
                if (_pool != null) {
                    _log.debug("Shutting down thread pool");
                    _pool.shutdownNow();
                    _pool = null;
                }
            }
        }
    }

    /**
     * Returns the current caller.
     *
     * @return the current caller, or <code>null</code> if no call is in
     *         progress
     * @throws RemoteException for any error
     */
    public Caller getCaller() throws RemoteException {
        return (Caller) _caller.get();
    }

    /**
     * Registers a caller event listener.
     *
     * @param listener the listener to notify
     */
    public void addCallerListener(CallerListener listener) {
        synchronized (this) {
            if (_listeners == null) {
                _listeners = new MulticastCallerListener();
                _manager.setCallerListener(_listeners);
            }
        }
        _listeners.addCallerListener(listener);
    }

    /**
     * Deregisters a caller event listener.
     *
     * @param listener the listener to remove
     */
    public void removeCallerListener(CallerListener listener) {
        MulticastCallerListener listeners;
        synchronized (this) {
            listeners = _listeners;
        }
        if (listeners != null) {
            listeners.removeCallerListener(listener);
        }
    }

    /**
     * Register a caller event listener.
     *
     * @param uri      the remote URI to listen on
     * @param listener the listener to notify
     * @throws InvalidURIException if <code>uri</code> is invalid
     */
    public void addCallerListener(String uri, CallerListener listener)
            throws InvalidURIException {
        synchronized (this) {
            if (_listeners == null) {
                _listeners = new MulticastCallerListener();
                _manager.setCallerListener(_listeners);
            }
        }
        _listeners.addCallerListener(uri, listener);
    }

    /**
     * Deregister a caller event listener.
     *
     * @param uri      the remote URI the listener is listening for events on
     * @param listener the listener to remove
     * @throws InvalidURIException if <code>uri</code> is invalid
     */
    public void removeCallerListener(String uri, CallerListener listener)
            throws InvalidURIException {
        MulticastCallerListener listeners;
        synchronized (this) {
            listeners = _listeners;
        }
        if (listeners != null) {
            listeners.removeCallerListener(uri, listener);
        }
    }

    /**
     * Shuts down the ORB.
     *
     * @throws RemoteException for any error
     */
    public void shutdown() throws RemoteException {
        try {
            _manager.close();
        } catch (ResourceException exception) {
            throw new RemoteException("Failed to close connection manager",
                    exception);
        }
        // super.close(); @todo
    }

    /**
     * Creates a new connection manager.
     *
     * @param handler       the invocation handler
     * @param authenticator the connection authenticator
     * @return a new connection manager
     * @throws ResourceException for any error
     */
    protected AbstractConnectionManager createConnectionManager(
            InvocationHandler handler, Authenticator authenticator)
            throws ResourceException {
        return new DefaultConnectionManager(handler, authenticator,
                getProperties());
    }

    /**
     * Connect to the specified URI.
     *
     * @param uri         the URI to establish a connection with
     * @param principal   specifies the identity of the principal. If
     *                    <code>null</code>, indicates to connect anonymously.
     * @param credentials the credentials of the principal
     * @return the local address that the connection is bound to
     * @throws ExportException for any error
     */
    protected URI connect(URI uri, String principal, String credentials)
            throws ExportException {
        URI result;
        try {
            Principal subject = null;
            if (principal != null) {
                subject = new BasicPrincipal(principal, credentials);
            }
            Connection connection = _manager.getConnection(subject, uri);
            result = connection.getLocalURI();

            // @todo - closing the connection will work for now in that
            // connection reference counts for OpenJMS will be correct. Won't
            // support the case where a client exports an object to the server
            // and then disposes its server proxies - the connection
            // will be prematurely closed. The connection needs to be kept
            // until the object is unexported.
            connection.close();
        } catch (ResourceException exception) {
            throw new ExportException("Failed to connect to URI: " + uri,
                    exception);
        }
        return result;
    }

    /**
     * Accept connections on the specified URI.
     *
     * @param uri the URI to accept connections on
     * @throws ExportException for any error
     */
    protected void accept(URI uri) throws ExportException {
        try {
            _manager.accept(uri, getProperties());
        } catch (ResourceException exception) {
            throw new ExportException("Failed to accept connections on URI: "
                    + uri, exception);
        }
    }

    /**
     * Returns the thread pool, creating one if it doesn't exist.
     *
     * @return the thread pool
     */
    private PooledExecutor getThreadPool() {
        synchronized (_poolLock) {
            if (_pool == null) {

                _pool = _factory.create("ORB", _maxThreads);
                _pool.abortWhenBlocked();
            }
            return _pool;
        }
    }

    /**
     * Closes the thread pool.
     */

    /**
     * Returns the method corresponding to the supplied object and method
     * identifier.
     *
     * @param object   the object to locate the method for
     * @param methodID the method identifier
     * @return the method
     * @throws NoSuchMethodException if a corresponding method cannot be found
     */
    private Method getMethod(Object object, long methodID)
            throws NoSuchMethodException {

        Method result = null;
        Method[] methods = MethodHelper.getAllInterfaceMethods(
                object.getClass());
        for (int i = 0; i < methods.length; ++i) {
            Method method = methods[i];
            if (MethodHelper.getMethodID(method) == methodID) {
                result = method;
                break;
            }
        }
        if (result == null) {
            throw new NoSuchMethodException(
                    "Failed to resolve method for methodID=" + methodID);
        }
        return result;
    }

    /**
     * Invocation handler, that delegates invocations to objects managed by the
     * DefaultORB.
     */
    private class Handler implements InvocationHandler {

        /**
         * Perform an invocation.
         *
         * @param invocation the invocation
         */
        public void invoke(final Invocation invocation) {
            Runnable invoker = new Runnable() {
                public void run() {
                    Response response;
                    try {
                        Request request = invocation.getRequest();
                        Caller caller = invocation.getCaller();
                        response = invoke(request, caller);
                    } catch (Throwable exception) {
                        response = new Response(exception);
                    }
                    invocation.setResponse(response);
                }
            };

            try {
                getThreadPool().execute(invoker);
            } catch (Throwable exception) {
                _log.debug("Pool failed to execute invocation", exception);
                invocation.setResponse(new Response(exception));
            }
        }

        /**
         * Handle a method invocation and return the result.
         *
         * @param request the request
         * @param caller  the caller performing the invocation
         * @return the result of the invocation
         */
        protected Response invoke(Request request, Caller caller) {
            Response response;
            try {
                Object object = getObject(request.getObjID(),
                        request.getURI());
                Method method = request.getMethod();
                if (method == null) {
                    // resolve the method using its id
                    method = getMethod(object, request.getMethodID());
                }
                Object[] args = request.getArgs();
                if (args == null) {
                    // deserialize the arguments
                    args = request.readArgs(method);
                }
                if (_log.isDebugEnabled()) {
                    _log.debug("Invoking " + method + " on " + object);
                }
                _caller.set(caller);
                Object result = method.invoke(object, args);
                response = new Response(result, method);
            } catch (InvocationTargetException exception) {
                Throwable target = exception.getTargetException();
                if (target == null) {
                    target = exception;
                }
                response = new Response(target);
            } catch (Throwable exception) {
                response = new Response(exception);
            } finally {
                _caller.set(null);
            }
            return response;
        }

    }

    /**
     * Dummy connection authenticator, which simply flags all principals as
     * authenticated.
     */
    private static class DummyAuthenticator implements Authenticator {

        /**
         * Determines if a principal has permissions to connect
         *
         * @param principal the principal to check
         * @return <code>true</code> if the principal has permissions to
         *         connect
         * @throws ResourceException if an error occurs
         */
        public boolean authenticate(Principal principal)
                throws ResourceException {
            return true;
        }
    }

}
