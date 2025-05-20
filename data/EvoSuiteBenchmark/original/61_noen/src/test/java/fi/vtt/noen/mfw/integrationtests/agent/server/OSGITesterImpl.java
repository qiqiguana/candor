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

package fi.vtt.noen.mfw.integrationtests.agent.server;

import fi.vtt.noen.mfw.bundle.blackboard.Blackboard;
import fi.vtt.noen.mfw.bundle.common.KnowledgeSource;
import fi.vtt.noen.mfw.bundle.server.plugins.derivedmeasure.DMPlugin;
import fi.vtt.noen.mfw.bundle.server.plugins.persistence.PersistencePluginImpl;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @see OSGITester
 *
 * @author Teemu Kanstren
 */
public class OSGITesterImpl implements OSGITester {
  private DMPlugin dmPlugin = null;
  private PersistencePluginImpl persistencePlugin = null;
  private Blackboard blackboard = null;
  /** The containers context to access the plugins etc. */
  private final BundleContext bc;

  public OSGITesterImpl(BundleContext bc) {
    this.bc = bc;
    System.out.println("new OSGITester");
  }

  /**
   * Get all plugins for testing. Needs to be called separately after all other plugins are loaded and
   * registered by the OSGI container.
   */
  public void init() {
    System.out.println("init called");
//    eventPlugin = (EventPlugin) getService(EventPlugin.class.getName());
//    controlPlugin = (ControlPlugin) getService(ControlPlugin.class.getName());
//    dmPlugin = (DMPlugin) getService(DMPlugin.class.getName());
//    persistencePlugin = (PersistencePlugin) getService(PersistencePlugin.class.getName());
//    blackboard = (Blackboard) getService(Blackboard.class.getName());
  }

  /**
   * To simplify getting services without cluttering code.
   *
   * @param name  Name that was used to register the service.
   * @return The requested service. If not found, OSGI throws exception (I think :)
   */
  private Object getService(String name) {
    ServiceReference ref = bc.getServiceReference(name);
    return bc.getService(ref);
  }

  /**
   * Test that all plugins have been registered with the blackboard after container startup.
   */
  public void testPluginsRegistered() {
    ServiceReference reference = bc.getServiceReference(Blackboard.class.getName());
    Object testobj = bc.getService(reference);
    Blackboard bb = (Blackboard) testobj;
    Collection<KnowledgeSource> plugins = bb.getPlugins();

    List<String> actual = new ArrayList<String>();
    for (KnowledgeSource plugin : plugins) {
      actual.add(plugin.getName());
    }
    //TODO: tähän testi että nuo kaikki haetut containerin kautta on myös oikein..
    System.out.println("actual:"+actual);

    assertTrue("PersistencePlugin service should be registered", actual.contains(PersistencePluginImpl.class.getName()));
    assertTrue("DMPlugin service should be registered", actual.contains(DMPlugin.class.getName()));
    //assertTrue("DataSource service should be registered", actual.contains(XMLDataSourcePlugin.class.getName()));

    assertEquals("Should have 5 services registered, has "+plugins.size()+": "+plugins, 5, plugins.size());
  }

  public void servicesStarted() throws Exception {
    Bundle bundle = getMeasurementFrameworkBundle();
    ServiceReference[] references = bundle.getRegisteredServices();
    Collection<Class> actual = new ArrayList<Class>();
    Collection<String> names = new ArrayList<String>();
    for (ServiceReference reference : references) {
      Object service = bc.getService(reference);
      actual.add(service.getClass());
      if (service instanceof KnowledgeSource) {
        KnowledgeSource ks = (KnowledgeSource) service;
        names.add(ks.getName());
      }
    }
    System.out.println("actual:"+actual);
    System.out.println("names-xx:"+names);
    //comparison needs to be string based because of different classloaders
/*    assertServiceFound(ControlPlugin.class, actual);
    assertServiceFound(EventPlugin.class, actual);
    assertServiceFound(PersistencePlugin.class, actual);
    assertServiceFound(DMPlugin.class, actual);
    assertTrue("Blackboard service should be started", actual.contains(BlackboardImpl.class.getName()));
    assertTrue("ControlPlugin service should be started", actual.contains(ControlPlugin.class.getName()));
    assertTrue("EventPlugin service should be started", actual.contains(EventPlugin.class.getName()));
    assertTrue("PersistencePlugin service should be started", actual.contains(PersistencePlugin.class.getName()));
    assertTrue("DMPlugin service should be started", actual.contains(DMPlugin.class.getName()));*/
    assertEquals("Should have 7 services, has " + actual.size() + ": " + actual, 7, actual.size());
  }

  private Bundle getMeasurementFrameworkBundle() {
    //TODO: this string to constants
    return getBundle("fi.vtt.noen.mfw.MeasurementFramework");
  }

  private Bundle getBundle(String name) {
    Bundle[] bundles = getBundles(name);
    if (bundles.length == 0) {
      return null;
    }
    if (bundles.length > 1) {
      throw new IllegalStateException("More than one bundle of type "+name+" found. Only one allowed.");
    }
    return bundles[0];
  }

  private Bundle[] getBundles(String name) {
    Collection<Bundle> result = new ArrayList<Bundle>();
    Bundle[] bundles = bc.getBundles();
      for (Bundle bundle : bundles) {
      if (name.equals(bundle.getSymbolicName())) {
        result.add(bundle);
      }
    }
    return result.toArray(new Bundle[0]);
  }
}
