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

package fi.vtt.noen.mfw.osmo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import fi.vtt.noen.mfw.bundle.blackboard.BlackboardActivator;
import fi.vtt.noen.mfw.bundle.common.Const;
import fi.vtt.noen.mfw.bundle.probe.plugins.communication.CommunicationPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.measurement.MeasurementPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.PAXmlRpcPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.ProbeAgentConfig;
import fi.vtt.noen.mfw.bundle.server.plugins.registry.RegistryPluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.registry.RegistryPluginImpl;
import fi.vtt.noen.mfw.bundle.server.plugins.rest.RestActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.rest.resources.ClientRequest;
import fi.vtt.noen.mfw.bundle.server.plugins.rest.resources.FrameworkInfo;
import fi.vtt.noen.mfw.bundle.server.plugins.rest.resources.ProbesInfo;
import fi.vtt.noen.mfw.bundle.server.plugins.rest.resources.Session;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.SAXmlRpcPluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.ServerAgentImpl;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.ServerPluginImpl;
import fi.vtt.noen.mfw.unittests.MockBundleContext;
import fi.vtt.noen.mfw.unittests.system.testprobes.ConfigurableTestProbeActivator;
import org.osgi.framework.BundleActivator;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static fi.vtt.noen.mfw.mbt.TestUtils.*;

/**
 * @author Teemu Kanstren
 */
public class ValidServerTestModel {
  private MockBundleContext mbc = new MockBundleContext();
  private WebResource wr;
  private Collection<BundleActivator> activators = new ArrayList<BundleActivator>();

  public ValidServerTestModel() throws Exception {
    BlackboardActivator ba = new BlackboardActivator();
    ba.start(mbc);
    activators.add(ba);

    ProbeAgentConfig config = new ProbeAgentConfig(6666, "http://localhost:5555/xmlrpc", 4000, 1000, 1000);
    PAXmlRpcPluginActivator pax = new PAXmlRpcPluginActivator(config);
    pax.start(mbc);
    activators.add(pax);

    //create 5 threads, timeout a hanging task after 10 seconds
    MeasurementPluginActivator mpa = new MeasurementPluginActivator(5, 10);
    mpa.start(mbc);
    activators.add(mpa);

    CommunicationPluginActivator cpa = new CommunicationPluginActivator();
    cpa.start(mbc);
    activators.add(cpa);
    Client client = Client.create();
    String pass = "password";
    // add authentication filter, it'll be always added to request as authorization header
    client.addFilter(new HTTPBasicAuthFilter("username", pass));
    wr = client.resource("http://localhost:8080/rest/");
    ClientRequest c = new ClientRequest("TestClient", "http://localhost:8088/rest/");
    Session session = wr.path("client/register").accept(MediaType.APPLICATION_XML).post(Session.class, c);
  }

  public ValidServerTestModel(boolean fake) throws Exception {
/*    MockBundleContext mbc = new MockBundleContext();
    BlackboardActivator ba = new BlackboardActivator();
    ba.start(mbc);
    
    RegistryPluginActivator rpa = new RegistryPluginActivator(20000, 1000);
    rpa.start(mbc);

    InMemoryPersistencePluginActivator ppa = new InMemoryPersistencePluginActivator();
    ppa.start(mbc);

    SAXmlRpcPluginActivator xpa = new SAXmlRpcPluginActivator(true);
    xpa.start(mbc);

    RestActivator ra = new RestActivator();
    ra.start(mbc);
*/
  }

  @Before
  public void start() {
    System.out.println("STARTED TEST");
  }

  @After
  public void end() {
    System.out.println("END TEST");
  }

  @AfterSuite
  public void shutdown() throws Exception {
    for (BundleActivator activator : activators) {
      activator.stop(mbc);
    }
  }

  @Transition("request mfw info")
  public void requestInfo() {
    FrameworkInfo info = wr.path("client/mfwinformation").accept(MediaType.APPLICATION_XML).get(FrameworkInfo.class);
    if (info != null) {
      System.out.println("  Id: " + info.getId());
      System.out.println("  Name: " + info.getName());
    }
  }

  @Guard("register probe")
  public boolean failed() {
    return false;
  }

  @Transition("register probe")
  public void registerProbe() {
    ConfigurableTestProbeActivator cpa = new ConfigurableTestProbeActivator(new OsmoTestProbe());
    try {
      cpa.start(mbc);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create and register new probe.", e);
    }
    //make a list of what base measures we should have available and at what precision
  }

  @Transition("request bm")
  public void requestBM() {
    //request a bm from one of the registered probes

  }

  @Transition("request probelist")
  public void requestProbeList() {
    ProbesInfo probes = wr.path("client/probes").accept(MediaType.APPLICATION_XML).get(ProbesInfo.class);
    System.out.println("Probes: "+probes);
  }

  public static void main(String[] args) throws Exception {
    OSMOTester osmo = new OSMOTester(new ValidServerTestModel());
    osmo.generate();
  }
}
