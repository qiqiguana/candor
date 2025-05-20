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
 */

package org.exolab.jms.service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Default implementation of the {@link Services} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @version $Revision: 1.3 $ $Date: 2005/08/31 00:42:17 $
 * @see Service
 * @see Serviceable
 */
public class ServiceManager extends Service implements Services {

    /**
     * A map of service type -> service instance (i.e, Class -> Object)
     * representing the set of services. The service instance may be null,
     * indicating that the service has been registered but not yet created.
     */
    private final Map _services = new HashMap();

    /**
     * A list of the service types in the order that they were registered. This
     * is used by {@link #doStart} to resolve services in the order that they
     * were registered with this.
     */
    private final List _addOrder = new ArrayList();

    /**
     * A list of service typs in the order that they were created. This is used
     * to ensure dependent services are started in the correct order.
     */
    private final List _createOrder = new ArrayList();


    /**
     * Construct a new <code>ServiceManager</code>.
     */
    public ServiceManager() {
        _services.put(Services.class, this);
    }

    /**
     * Add a service of the specified type.
     * <p/>
     * The service will be constructed when it is first accessed via {@link
     * #getService}.
     *
     * @param type the type of the service
     * @throws ServiceAlreadyExistsException if the service already exists
     * @throws ServiceException              for any service error
     */
    public synchronized void addService(Class type) throws ServiceException {
        if (type == null) {
            throw new IllegalArgumentException("Argument 'type' is null");
        }
        checkExists(type);
        _services.put(type, null);
        _addOrder.add(type);
    }

    /**
     * Add a service instance.
     *
     * @param service the service instance
     * @throws ServiceAlreadyExistsException if the service already exists
     * @throws ServiceException              for any service error
     */
    public void addService(Object service) throws ServiceException {
        if (service == null) {
            throw new IllegalArgumentException("Argument 'service' is null");
        }
        Class type = service.getClass();
        checkExists(type);
        _services.put(type, service);
        _addOrder.add(type);
    }

    /**
     * Returns a service given its type.
     * <p/>
     * If the service has been registered but not constructed, it will be
     * created and any setters populated.
     *
     * @param type the type of the service
     * @return an instance of <code>type</code>
     * @throws ServiceDoesNotExistException if the service doesn't exist, or is
     *                                      dependent on a service which doesn't
     *                                      exist
     * @throws ServiceException             for any service error
     */
    public synchronized Object getService(Class type) throws ServiceException {
        LinkedList creating = new LinkedList();
        List created = new ArrayList();
        Object service = getService(type, creating, created);
        Iterator iterator = created.iterator();
        while (iterator.hasNext()) {
            invokeSetters(iterator.next());
        }
        return service;
    }

    /**
     * Start the service.
     *
     * @throws ServiceException if the service fails to start, or is already
     *                          running
     */
    protected void doStart() throws ServiceException {
        for (Iterator iter = _addOrder.iterator(); iter.hasNext();) {
            Class type = (Class) iter.next();
            getService(type);
        }

        for (Iterator iter = _createOrder.iterator(); iter.hasNext();) {
            Class type = (Class) iter.next();
            Object service = getService(type);
            if (service instanceof Serviceable) {
                ((Serviceable) service).start();
            }
        }
    }

    /**
     * Stop the service.
     *
     * @throws ServiceException if the service fails to stop, or is already
     *                          stopped
     */
    protected void doStop() throws ServiceException {
        for (Iterator iter = _createOrder.iterator(); iter.hasNext();) {
            Class type = (Class) iter.next();
            Object service = getService(type);
            if (service instanceof Serviceable) {
                ((Serviceable) service).stop();
            }
        }
    }

    /**
     * Returns a service given its type, creating it if required.
     *
     * @param type     the type of the service
     * @param creating the set of services currently being created
     * @param created  the set of services already created
     * @return the service corresponding to <code>type<code>
     * @throws ServiceDoesNotExistException if no service matches <code>type</code>
     * @throws ServiceException             for any service error
     */
    private Object getService(Class type, LinkedList creating, List created)
            throws ServiceException {
        Iterator types = _services.keySet().iterator();
        List matches = new ArrayList();
        while (types.hasNext()) {
            Class clazz = (Class) types.next();
            if (type.isAssignableFrom(clazz)) {
                matches.add(clazz);
            }
        }
        if (matches.isEmpty()) {
            String msg = "Service of type " + type.getName()
                    + " not registered";
            Class requiredBy = null;
            if (!creating.isEmpty()) {
                requiredBy = (Class) creating.getLast();
                msg += ", but required by " + requiredBy.getName();
            }
            throw new ServiceDoesNotExistException(msg);
        } else if (matches.size() > 1) {
            throw new ServiceException(
                    "Multiple services match service type " + type.getName());
        }
        Class match = (Class) matches.get(0);
        Object service = _services.get(match);
        if (service == null) {
            service = createService(match, creating, created);
            _services.put(match, service);
            _createOrder.add(match);
        }
        return service;
    }

    /**
     * Create a new service given its type.
     *
     * @param type     the service type
     * @param creating the set of services currently being created
     * @param created  the set of services already created
     * @return the service corresponding to <code>type<code>
     * @throws ServiceException if the service can't be constructed
     */
    protected Object createService(Class type, LinkedList creating,
                                   List created) throws ServiceException {
        if (creating.contains(type)) {
            throw new ServiceException("Circular dependency trying to construct "
                                       + type.getName() + ": " + creating);
        }
        Object service;
        Constructor[] constructors = type.getConstructors();
        if (constructors.length > 1) {
            throw new ServiceException("Cannot create service of type "
                                       + type.getName()
                                       + ": multiple public constructors");
        } else if (constructors.length != 1) {
            throw new ServiceException("Cannot create service of type "
                                       + type.getName()
                                       + ": no public constructor");
        }
        Constructor ctor = constructors[0];
        Class[] types = ctor.getParameterTypes();
        Object[] args = new Object[types.length];
        try {
            creating.add(type);
            for (int i = 0; i < types.length; ++i) {
                args[i] = getService(types[i], creating, created);
            }
            service = ctor.newInstance(args);
            created.add(service);
        } catch (IllegalAccessException exception) {
            throw new ServiceException(
                    "Failed to create service of type: " + type,
                    exception);
        } catch (InvocationTargetException exception) {
            Throwable target = exception.getTargetException();
            if (target == null) {
                target = exception;
            }
            throw new ServiceException(
                    "Failed to create service of type: " + type,
                    target);
        } catch (InstantiationException exception) {
            throw new ServiceException(
                    "Failed to create service of type: " + type,
                    exception);
        } finally {
            creating.remove(type);
        }
        return service;
    }

    /**
     * Populates all the public setters for the supplied service.
     * <p/>
     * The service must following bean naming conventions, and there must be a
     * service registered for each of setter's arguments.
     *
     * @param service the service to populate.
     * @throws ServiceException
     */
    private void invokeSetters(Object service) throws ServiceException {
        PropertyDescriptor[] descriptors;
        try {
            BeanInfo info = Introspector.getBeanInfo(service.getClass());
            descriptors = info.getPropertyDescriptors();
        } catch (IntrospectionException exception) {
            throw new ServiceException(exception.getMessage(), exception);
        }
        for (int i = 0; i < descriptors.length; ++i) {
            PropertyDescriptor descriptor = descriptors[i];
            Method method = descriptor.getWriteMethod();
            if (method != null) {
                Class type = descriptor.getPropertyType();
                Object[] args = new Object[1];
                args[0] = getService(type);
                try {
                    method.invoke(service, args);
                } catch (IllegalAccessException exception) {
                    throw new ServiceException(
                            "Failed to create service of type: " + type,
                            exception);
                } catch (InvocationTargetException exception) {
                    Throwable target = exception.getTargetException();
                    if (target == null) {
                        target = exception;
                    }
                    throw new ServiceException(
                            "Failed to create service of type: " + type,
                            target);
                }
            }
        }
    }

    /**
     * Checks if a service has been registered.
     *
     * @param type the type of the service
     * @throws ServiceAlreadyExistsException if the service is already
     *                                       registered
     */
    protected void checkExists(Class type)
            throws ServiceAlreadyExistsException {
        if (_services.get(type) != null) {
            throw new ServiceAlreadyExistsException(
                    "Service of type " + type + " already registered");
        }
    }
}
