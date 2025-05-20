/*
 * Copyright (C) 2010-2011 VTT Technical Research Centre of Finland.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package fi.vtt.noen.mfw.unittests;

import org.osgi.framework.Bundle;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * A mocked bundlecontext that attaches together different types of services outside a container.
 *
 * @author Teemu Kanstren
 */
public class MockBundleContext extends EmptyBundleContext {
  private final Map<String, Collection<MockServiceReference>> serviceMap = new HashMap<String, Collection<MockServiceReference>>();
  private final Collection <ServiceListener> listeners = new ArrayList<ServiceListener>();
  private Map<String, String> properties = new HashMap<String, String>();

  public void setProperty(String key, String value) {
    properties.put(key, value);
  }

  @Override
  public String getProperty(String key) {
    return properties.get(key);
  }

  @Override
  public ServiceRegistration registerService(String serviceName, Object service, Dictionary properties) {
    Collection<MockServiceReference> services = serviceMap.get(serviceName);
    if (services == null) {
      services = new ArrayList<MockServiceReference>();
      serviceMap.put(serviceName, services);
    }
    if (service instanceof ServiceFactory) {
      ServiceFactory factory = (ServiceFactory) service;
//      service = factory.getService(null, );
      //service factories are not supported since it would require mocking Bundle class, which gets rather complex.. Use PAX exam instead
      throw new IllegalArgumentException("MockBundleContext does not support service factories");
    }
    MockServiceReference mock = new MockServiceReference(serviceName, service);
    services.add(mock);
    for (ServiceListener listener : listeners) {
      listener.serviceChanged(new ServiceEvent(ServiceEvent.REGISTERED, mock));
    }
    System.out.println("MockBundleContext: registered service:"+serviceName);
    return new MockServiceRegistration(mock);
  }

  @Override
  public ServiceRegistration registerService(String[] names, Object service, Dictionary dictionary) {
    ServiceRegistration registration = null;
    for (String name : names) {
      registration = registerService(name, service, dictionary);
      System.out.println("MockBundleContext: registered service with name:" + name + " obj:"+service);
    }
    return registration;
  }

  @Override
  public ServiceReference[] getServiceReferences(String serviceName, String s1) throws InvalidSyntaxException {
    Collection<MockServiceReference> services = serviceMap.get(serviceName);
    if (services == null) {
      return null;
    }
    ServiceReference[] refs = new ServiceReference[services.size()];
    int i = 0;
    for (MockServiceReference mock : services) {
      refs[i++] = mock;
    }
    return refs;
  }

  @Override
  public ServiceReference getServiceReference(String serviceName) {
    Collection<MockServiceReference> services = serviceMap.get(serviceName);
    System.out.println("getting:"+serviceName+" = ");
    if (services == null) {
      return null;
    }
    MockServiceReference reference = services.iterator().next();
    System.out.println("reference:"+reference);
    return reference;
  }

  @Override
  public Object getService(ServiceReference serviceReference) {
    if (!(serviceReference instanceof MockServiceReference)) {
      throw new IllegalArgumentException("MockBundleContext only supports MockServiceReference, got:"+serviceReference);
    }
    MockServiceReference ref = (MockServiceReference) serviceReference;
    return ref.reference;
  }

  @Override
  public void addServiceListener(ServiceListener listener) {
    listeners.add(listener);
  }

  private class MockServiceReference implements ServiceReference {
    private final String type;
    private final Object reference;

    private MockServiceReference(String type, Object reference) {
      this.type = type;
      this.reference = reference;
    }

    public Object getProperty(String s) {
      return null;
    }

    public String[] getPropertyKeys() {
      return new String[0];
    }

    public Bundle getBundle() {
      return null;
    }

    public Bundle[] getUsingBundles() {
      return new Bundle[0];
    }

    public boolean isAssignableTo(Bundle bundle, String s) {
      return false;
    }

    public int compareTo(Object o) {
      return 0;
    }
  }

  private class MockServiceRegistration implements ServiceRegistration {
    private final ServiceReference reference;

    private MockServiceRegistration(ServiceReference reference) {
      this.reference = reference;
    }

    public ServiceReference getReference() {
      return reference;
    }

    public void setProperties(Dictionary dictionary) {
    }

    public void unregister() {
      System.out.println("UNREGISTER ATTEMPT ON MOCKSERVICEREGISTRATION");
    }
  }
}
