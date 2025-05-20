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

package fi.vtt.noen.mfw.unittests.system;

import fi.vtt.noen.mfw.bundle.blackboard.BlackboardActivator;
import fi.vtt.noen.mfw.bundle.common.Const;
import fi.vtt.noen.mfw.bundle.probe.plugins.communication.CommunicationPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.measurement.MeasurementPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.PAXmlRpcPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.ProbeAgentConfig;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.ProbeAgentImpl;
import fi.vtt.noen.mfw.bundle.server.plugins.event.EventPluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.persistence.PersistencePluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.registry.RegistryPlugin;
import fi.vtt.noen.mfw.bundle.server.plugins.registry.RegistryPluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.sac.SACPlugin;
import fi.vtt.noen.mfw.bundle.server.plugins.sac.SACPluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.SAXmlRpcPluginActivator;
import fi.vtt.noen.mfw.probes.tester.TestProbeActivator;
import fi.vtt.noen.mfw.unittests.MockBundleContext;
import fi.vtt.noen.mfw.unittests.PortManager;
import org.junit.After;
import org.junit.Before;
import org.osgi.framework.BundleActivator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * @author Teemu Kanstren
 */
public abstract class BaseSystemTest {
  protected final static String ln = System.getProperty("line.separator");
  protected int serverPort = -1;
  protected int probePort = -1;
  protected ProbeAgentImpl probeAgent = null;
  protected MockBundleContext pbc = null;
  protected MockBundleContext sbc = null;
  protected Collection<BundleActivator> probeActivators = new ArrayList<BundleActivator>();
  protected Collection<BundleActivator> serverActivators = new ArrayList<BundleActivator>();
  protected RegistryPlugin registry = null;
  protected SACPlugin sac;
  protected SAXmlRpcPluginActivator serverXmlRpcActivator;
  protected int keepAliveInterval = 2000;
  protected int retryDelay = 1000;
  protected boolean localInUse = false;
  protected int taskTimeOut = 5;
  protected int threadPoolSize = 5;
  protected int subscriptionCheckInterval = 4000;

  @Before
  public void setup() throws Exception {
    customizeProperties();
    sbc = new MockBundleContext();
    if (localInUse) {
      pbc = sbc;
    } else {
      pbc = new MockBundleContext();
    }
    startServerAgent();
    startProbeAgent();
  }

  public void customizeProperties() {
  }

  @After
  public void teardown() throws Exception {
    stopProbeAgent();
    stopServerAgent();
  }

  protected void stopProbeAgent() {
    for (BundleActivator a : probeActivators) {
      try {
        a.stop(pbc);
      } catch (Exception e) {
        //we catch this to allow other activators a chance to release their resources
        e.printStackTrace();
      }
    }
    probeActivators.clear();
    pbc = new MockBundleContext();
  }

  protected void stopServerAgent() {
    for (BundleActivator a : serverActivators) {
      try {
        a.stop(pbc);
      } catch (Exception e) {
        //we catch this to allow other activators a chance to release their resources
        e.printStackTrace();
      }
    }
    serverActivators.clear();
    sbc = new MockBundleContext();
  }

  protected void startServerAgent() throws Exception {
    serverPort = PortManager.next();
    startServerAgent(serverPort);
  }

  protected void startServerAgent(int serverPort) throws Exception {
    createCustomServerBundles();

    BlackboardActivator ba = new BlackboardActivator();
    ba.start(sbc);
    serverActivators.add(ba);

    RegistryPluginActivator rpa = new RegistryPluginActivator(20000, keepAliveInterval);
    rpa.start(sbc);
    serverActivators.add(rpa);
    registry = rpa.getRegistryPlugin();

    PersistencePluginActivator ppa = new PersistencePluginActivator();
    ppa.start(sbc);
    serverActivators.add(ppa);

    if (localInUse) {
      serverXmlRpcActivator = new SAXmlRpcPluginActivator(localInUse);
    } else {
      serverXmlRpcActivator = new SAXmlRpcPluginActivator(serverPort);
    }
    serverXmlRpcActivator.start(sbc);
    serverActivators.add(serverXmlRpcActivator);

    //start SAC plugin
    Properties props = new Properties();
    //fake properties to allow the SAC to load
    props.setProperty(Const.SAC_WS_URL, "localhost:11111");
    props.setProperty(Const.SAC_ID, "55");
    //props.setProperty(Const.MFW_WS_PORT, ""+PortManager.next());
    props.setProperty(Const.MFW_WS_URL, "http://localhost:22222/");
    SACPluginActivator spc = new SACPluginActivator(props);
    spc.start(sbc);
    sac = spc.getSac();
    serverActivators.add(spc);

    EventPluginActivator epa = new EventPluginActivator();
    epa.start(sbc);
    serverActivators.add(epa);
  }

  protected void createCustomServerBundles() throws Exception {
  }


  protected void startProbeAgent() throws Exception {
    startProbeAgent(serverPort);
  }

  protected void startProbeAgent(int serverPort) throws Exception {
//    pbc = new MockBundleContext();
    if (!localInUse) {
      //we can only have on blackboard, and if local is in use the server initialization already created oned
      BlackboardActivator ba = new BlackboardActivator();
      ba.start(pbc);
      probeActivators.add(ba);
    }

    probePort = PortManager.next();
    if (localInUse) {
      //this makes the probe-agent use a local connection
      probePort = -1;
    }
    ProbeAgentConfig config = new ProbeAgentConfig(probePort, "http://localhost:" + serverPort, keepAliveInterval, retryDelay, subscriptionCheckInterval);
    customizeProbeAgentConfig(config);
    PAXmlRpcPluginActivator probeXmlRpcActivator = new PAXmlRpcPluginActivator(config);
    probeXmlRpcActivator.start(pbc);
    probeAgent = probeXmlRpcActivator.getProbeAgent();
    probeActivators.add(probeXmlRpcActivator);

    MeasurementPluginActivator mpa = new MeasurementPluginActivator(threadPoolSize, taskTimeOut);
    mpa.start(pbc);
    probeActivators.add(mpa);

    CommunicationPluginActivator cpa = new CommunicationPluginActivator();
    cpa.start(pbc);
    probeActivators.add(cpa);
  }

  protected void customizeProbeAgentConfig(ProbeAgentConfig config) {
  }

  protected void startTestProbes() throws Exception {
    Properties props = new Properties();
    TestProbeActivator tpa = new TestProbeActivator(props);
    tpa.start(pbc);
    probeActivators.add(tpa);
  }

}
