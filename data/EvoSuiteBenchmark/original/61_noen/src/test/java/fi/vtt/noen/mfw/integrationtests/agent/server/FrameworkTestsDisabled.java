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
import org.apache.felix.framework.cache.BundleCache;
import org.apache.felix.main.AutoProcessor;
import org.junit.Ignore;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import static org.junit.Assert.*;

/**
 * Integration tests for basic features of the measurement framework. These start the actual Felix container and
 * run all the things inside it.
 *
 * TODO: All tests are set to ignore. Should be fixed with paxtests or something similar..
 *
 * @author Teemu Kanstren
 */
public class FrameworkTestsDisabled {
  private static Framework fw = null;

  //@ BeforeClass
  public static void setUp() throws Exception {
    fw = startFelix();
  }

  //@ AfterClass
  public static void tearDown() throws Exception {
    fw.stop();
    fw.waitForStop(3000);
  }

  @Ignore
  public void bundleStarted() throws Exception {
    Bundle bundle = getMeasurementFrameworkBundle();
    assertNotNull("Measurement framework bundle should be loaded", bundle);
  }

  @Ignore
  public void servicesStarted() throws Exception {
    Bundle bundle = getMeasurementFrameworkBundle();
    BundleContext bc = fw.getBundleContext();
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
    System.out.println("names:"+names);
    //comparison needs to be string based because of different classloaders
    assertServiceFound(Blackboard.class, actual);
    assertServiceFound(KnowledgeSource.class, actual);
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

  public static void assertServiceFound(Class expected, Collection<Class> actual) {
    String expectedName = expected.toString();
    for (Class clazz : actual) {
      Class[] interfaces = clazz.getInterfaces();
      System.out.println("class:"+clazz.getCanonicalName()+" int:"+interfaces.length);
      for (Class service : interfaces) {
        System.out.println("service:"+service);
        if (service.toString().equals(expectedName)) {
          return;
        }
      }
    }
    assertTrue(expected+" service should be started", false);
  }

  @Ignore
  public void pluginsRegistered() throws Exception {
    BundleContext bc = fw.getBundleContext();
    ServiceReference reference = bc.getServiceReference(OSGITester.class.getName());
    final Object originalTester = bc.getService(reference);
    InvocationHandler handler = new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method testMethod = originalTester.getClass().getMethod(method.getName(), method.getParameterTypes());
        testMethod.invoke(originalTester, args);
        return null;
      }
    };
    OSGITester tester = (OSGITester) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{OSGITester.class}, handler);
    servicesStarted();
    tester.init();
    tester.testPluginsRegistered();
  }

  @Ignore
  public void servicesStartedOSGI() throws Exception {
    BundleContext bc = fw.getBundleContext();
    ServiceReference reference = bc.getServiceReference(OSGITester.class.getName());
    final Object originalTester = bc.getService(reference);
    InvocationHandler handler = new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method testMethod = originalTester.getClass().getMethod(method.getName(), method.getParameterTypes());
        testMethod.invoke(originalTester, args);
        return null;
      }
    };
    OSGITester tester = (OSGITester) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{OSGITester.class}, handler);
    tester.init();
    tester.servicesStarted();
  }

  @Ignore
  public void updateBundle() throws Exception {
    BundleContext bc = fw.getBundleContext();
    //TODO: these strings to constants
    Bundle update1 = getBundle("fi.vtt.noen.mfw.UpdateTester");
    assertNotNull("UpdateTester bundle should be installed", update1);

    update1.uninstall();
    update1 = getBundle("fi.vtt.noen.mfw.UpdateTester");
    assertNull("UpdateTester bundle should not be installed", update1);

    InputStream is = new FileInputStream("update-tester1.jar");
    update1 = bc.installBundle("fi.vtt.noen://bundles/UpdateTester", is);
    assertNotNull("UpdateTester bundle should be installed", update1);
    //update1.version();
    update1.start();
    update1 = getBundle("fi.vtt.noen.mfw.UpdateTester");
    assertNotNull("UpdateTester bundle should be installed", update1);

    update1.uninstall();
    update1 = getBundle("fi.vtt.noen.mfw.UpdateTester");
    assertNull("UpdateTester bundle should not be installed", update1);
    
    is = new FileInputStream("update-tester2.jar");
    update1 = bc.installBundle("fi.vtt.noen://bundles/UpdateTester", is);
    assertNotNull("UpdateTester bundle should be installed", update1);
    update1.start();
  }

  private Bundle getMeasurementFrameworkBundle() {
    //TODO: this string to constants
    return getBundle("fi.vtt.noen.mfw.MeasurementFramework");
  }

  private Bundle getBundle(String name) {
    Bundle[] bundles = getBundles(name);
    System.out.println("total bundles:"+bundles.length);
    for (Bundle bundle : bundles) {
      System.out.println("bundle:"+bundle.getSymbolicName());
    }
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
    BundleContext bc = fw.getBundleContext();
    Bundle[] bundles = bc.getBundles();
    for (Bundle bundle : bundles) {
      if (name.equals(bundle.getSymbolicName())) {
        result.add(bundle);
      }
    }
    return result.toArray(new Bundle[0]);
  }

/*  public static void main(String[] args) {
    File file = new File("felix-cache");
    deleteDir(file);
    file.mkdir();  }*/

  private static Framework startFelix() throws Exception {
    String bundleDir = "distro/server/bundles";
    File dir = new File(bundleDir);
    Map<String, String> config = new HashMap<String, String>();
    config.put("felix.log.level", "1");
    config.put("felix.auto.deploy.action", "install,update,start");
    config.put(AutoProcessor.AUTO_DEPLOY_DIR_PROPERY, dir.getAbsolutePath());
//    config.put(AutoProcessor.AUTO_DEPLOY_DIR_PROPERY, bundleDir);

    File cache = new File("felix-cache");
    deleteDir(cache);
    cache.mkdir();

    config.put(BundleCache.CACHE_ROOTDIR_PROP, ".");

    ServiceLoader<FrameworkFactory> loader = ServiceLoader.load(FrameworkFactory.class);
    FrameworkFactory factory = loader.iterator().next();
    Framework fw = factory.newFramework(config);
    fw.init();
    BundleContext bc = fw.getBundleContext();
    AutoProcessor.process(config, bc);
    fw.start();
    return fw;
  }

  private static void deleteDir(File dir) {
    if (dir.isDirectory()) {
        String[] children = dir.list();
        for (String file : children) {
            deleteDir(new File(dir, file));
        }
    }
    // The directory is now empty so delete it
    dir.delete();
    }
}
