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
import fi.vtt.noen.mfw.bundle.common.ProbeConfiguration;
import fi.vtt.noen.mfw.bundle.probe.plugins.communication.CommunicationPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.measurement.MeasurementPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.PAXmlRpcPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.ProbeAgentConfig;
import fi.vtt.noen.mfw.bundle.server.plugins.persistence.PersistencePluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.registry.RegistryPluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.registry.RegistryPluginImpl;
import fi.vtt.noen.mfw.bundle.server.plugins.sac.SACPlugin;
import fi.vtt.noen.mfw.bundle.server.plugins.sac.SACPluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.SAXmlRpcPluginActivator;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.TargetDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import fi.vtt.noen.mfw.probes.tester.TestProbe;
import fi.vtt.noen.mfw.probes.tester.TestProbe1;
import fi.vtt.noen.mfw.probes.tester.TestProbe2;
import fi.vtt.noen.mfw.probes.tester.TestProbe3;
import fi.vtt.noen.mfw.probes.tester.TestProbe4;
import fi.vtt.noen.mfw.probes.tester.TestProbeActivator;
import fi.vtt.noen.mfw.unittests.MockBundleContext;
import fi.vtt.noen.mfw.unittests.PortManager;
import fi.vtt.noen.mfw.unittests.system.testprobes.MockSAC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class SACToTestPluginTests {
  private int serverPort;
  private SACPlugin sac;
  private RegistryPluginImpl registry;
  private long probe1id;
  private long probe2id;
  private long probe3id;
  private long probe4id;
  private TestProbe testProbe1;
  public BlackboardActivator serverBA;
  public SACPluginActivator spa;
  public SAXmlRpcPluginActivator serverActivator;
  public RegistryPluginActivator rpa;
  public BlackboardActivator probeBA;
  public PAXmlRpcPluginActivator probeActivator;
  public CommunicationPluginActivator cpa;
  public MeasurementPluginActivator mpa;
  public TestProbeActivator tpa;
  public MockBundleContext probeBC;
  public MockBundleContext serverBC;

  @Before
  public void setup() throws Exception {
    startServerAgent();
    startProbeAgent();
    Thread.sleep(1000);
    captureTestProbes();
  }

  @After
  public void teardown() throws Exception {
    serverBA.stop(serverBC);
    spa.stop(serverBC);
    rpa.stop(serverBC);
    serverActivator.stop(serverBC);

    probeBA.stop(probeBC);
    probeActivator.stop(probeBC);
    cpa.stop(probeBC);
    mpa.stop(probeBC);
    tpa.stop(probeBC);
  }

  @Test
  public void requestConfiguration() throws Exception {
    Collection<ProbeConfiguration> configuration = sac.getProbeConfigurationParameters(probe1id);
    assertEquals(2, configuration.size());
    Iterator<ProbeConfiguration> iterator = configuration.iterator();
    ProbeConfiguration height = iterator.next();
    ProbeConfiguration width = iterator.next();
    if (!height.getName().equals("height")) {
      ProbeConfiguration temp = height;
      height = width;
      width = temp;
    }
    assertEquals("height", height.getName());
    assertEquals("10 meters", height.getValue());
    assertEquals("width", width.getName());
    assertEquals("3 meters", width.getValue());
  }

  @Test
  public void setConfiguration() throws Exception {
    requestConfiguration();
    HashMap<String, String> configuration = new HashMap<String, String>();
    configuration.put("height", "5 meters");
    configuration.put("width", "1 meter");
//    configuration.put("boredom", "dominant");
    sac.setProbeConfiguration(probe1id, configuration);

    Collection<ProbeConfiguration> actual = testProbe1.getConfigurationParameters();
    assertEquals("Number of parameters", 2, actual.size());
//    assertEquals("Set configuration height", "5 meters", actual.get("height"));
//    assertEquals("Set configuration width", "1 meter", actual.get("width"));
//    assertEquals("Set configuration domination", "dominant", actual.get("boredom"));
  }

  @Test
  public void requestBM() throws Exception {
    MockSAC mockSAC = new MockSAC(-1);
    sac.register(-1, mockSAC);
    List<BMDescription> bms = sac.getAvailableBMList();   
    BMDescription bm = find(TestProbe1.TARGET_NAME, bms);
    sac.requestBM(-1, bm.getBmId());
    //wait for the value to be provided
    Thread.sleep(2000);
    assertEquals("Number of BM values received", 1, mockSAC.getNumberOfValuesReceived());
    assertFalse("Should receive no invalid measurement event", mockSAC.isInvalidBMEventReceived());
    Value actual = mockSAC.values.values().iterator().next().get(0);
    assertEquals("Should have provided testprobe1v2 value", "test probe1v2 measure 0", actual.getString());
  }

  @Test
  public void requestBMWithTwoSAC() throws Exception {
    MockSAC mockSAC1 = new MockSAC(1);
    MockSAC mockSAC2 = new MockSAC(2);
    sac.register(1, mockSAC1);
    sac.register(2, mockSAC2);
    Thread.sleep(500);
    List<BMDescription> bms = sac.getAvailableBMList();   
    BMDescription bm1 = find(TestProbe1.TARGET_NAME, bms);
    sac.requestBM(1, bm1.getBmId());
    BMDescription bm2 = find(TestProbe2.TARGET_NAME, bms);
    sac.requestBM(2, bm2.getBmId());
    //wait for the value to be provided
    Thread.sleep(5000);
    assertEquals("Number of BM values received", 1, mockSAC1.getNumberOfValuesReceived());
    assertFalse("Test should not produce a measurement error event to the SAC", mockSAC1.isInvalidBMEventReceived());
    Value actual1 = mockSAC1.values.values().iterator().next().get(0);
    assertEquals("Should have provided testprobe1v2 value", "test probe1v2 measure 0", actual1.getString());

    assertEquals("Number of BM values received", 1, mockSAC2.getNumberOfValuesReceived());
    assertFalse("Test should not produce a measurement error event to the SAC", mockSAC2.isInvalidBMEventReceived());
    Value actual2 = mockSAC2.values.values().iterator().next().get(0);
    assertEquals("Should have provided testprobe1v2 value", "0", actual2.getString());
  }
  
  private BMDescription find(String targetName, List<BMDescription> bms) {
    for (BMDescription bm : bms) {
      //we check with "contains" the measureURI as it is embedded with more information such as bm class etc.
      if (bm.getMeasureURI().contains(targetName)) {
        return bm;
      }
    }
    throw new IllegalStateException("No suitable BM found for:" + targetName+"\nhad:"+bms);
  }

  @Test
  public void requestBMSampling() throws Exception {
    MockSAC mockSAC = new MockSAC(-1);
    sac.register(-1, mockSAC);
    List<BMDescription> bms = sac.getAvailableBMList();
    BMDescription bm = find(TestProbe1.TARGET_NAME, bms);

    assertEquals("BM received should be none before request", 0, mockSAC.values.size());
    assertEquals("Events received should be none before request", 0, mockSAC.events.size());

    //this has to be 100 because anything less seems to fail as too accurate for the java timer
    sac.subscribeToBM(-1, bm.getBmId(), 100);
    Thread.sleep(1000);
    int numberOfValues = mockSAC.getNumberOfValuesReceived();
    assertTrue("Number of BM values received should be 5-15, was "+numberOfValues, numberOfValues >= 5 && numberOfValues <= 15);
    assertFalse("Test should not produce a measurement error event to the SAC", mockSAC.isInvalidBMEventReceived());
    Value actual = mockSAC.values.values().iterator().next().get(0);
    assertEquals("Should have provided testprobe1v2 value", "test probe1v2 measure 0", actual.getString());
  }

  @Test
  public void requestBMSamplingWithTwoSAC() throws Exception {
    MockSAC mockSAC1 = new MockSAC(1);
    MockSAC mockSAC2 = new MockSAC(2);
    sac.register(1, mockSAC1);
    sac.register(2, mockSAC2);
    List<BMDescription> bms = sac.getAvailableBMList();
    BMDescription bm = find(TestProbe1.TARGET_NAME, bms);

    assertEquals("BM received should be none before request", 0, mockSAC1.values.size());
    assertEquals("Events received should be none before request", 0, mockSAC1.events.size());
    assertEquals("BM received should be none before request", 0, mockSAC2.values.size());
    assertEquals("Events received should be none before request", 0, mockSAC2.events.size());

    //this has to be 100 because anything less seems to fail as too accurate for the java timer
    sac.subscribeToBM(1, bm.getBmId(), 100);
    sac.subscribeToBM(2, bm.getBmId(), 100);
    Thread.sleep(1000);
    int numberOfValues = mockSAC1.getNumberOfValuesReceived();
    assertTrue("Number of BM values received should be 5-12, was "+numberOfValues, numberOfValues >= 5 && numberOfValues <= 12);
    assertFalse("Test should not produce a measurement error event to the SAC", mockSAC1.isInvalidBMEventReceived());
    Value actual = mockSAC1.values.values().iterator().next().get(0);
    assertEquals("Should have provided testprobe1v2 value", "test probe1v2 measure 0", actual.getString());
    numberOfValues = mockSAC2.getNumberOfValuesReceived();
    assertTrue("Number of BM values received should be 5-12, was "+numberOfValues, numberOfValues >= 5 && numberOfValues <= 12);
    assertFalse("Test should not produce a measurement error event to the SAC", mockSAC2.isInvalidBMEventReceived());
    actual = mockSAC2.values.values().iterator().next().get(0);
    assertTrue("Should have provided testprobe1v2 value", actual.getString().startsWith("test probe1v2 measure"));
  }

  @Test
  public void availableBMsAndProbesAndDevices() throws Exception {
    List<BMDescription> bms = sac.getAvailableBMList();
    assertEquals("Number of available base measures from test probes", 5, bms.size());

    List<ProbeDescription> probes = sac.getProbeList();
    assertEquals("Number of available probes from test probes", 6, probes.size());

    Collection<TargetDescription> devices = sac.getTargetList();
    assertEquals("Number of available devices from test probes", 5, devices.size());
    assertTarget(devices, 1, TestProbe1.TARGET_NAME);
    assertTarget(devices, 2, TestProbe2.TARGET_NAME);
    assertTarget(devices, 3, TestProbe3.TARGET_NAME);
    assertTarget(devices, 4, TestProbe4.TARGET_NAME);
  }

  private void assertTarget(Collection<TargetDescription> devices, int probe, String targetName) {
    for (TargetDescription device : devices) {
      if (device.getTargetName().equals(targetName)) {
        return;
      }
    }
    fail("Should have testprobe" + probe + " target but this was not found");
  }

  @Test
  public void notifyFailure() throws Exception {
    sac.requestBM(-1, Long.MAX_VALUE);
  }

  private void captureTestProbes() {
    List<ProbeDescription> probes = registry.getProbes();
    for (ProbeDescription probe : probes) {
      if (probe.getProbeName().equals(TestProbe1.PROBE_DESCRIPTION)) {
        probe1id = probe.getProbeId();
      }
      if (probe.getProbeName().equals(TestProbe2.PROBE_DESCRIPTION)) {
        probe2id = probe.getProbeId();
      }
      if (probe.getProbeName().equals(TestProbe3.PROBE_DESCRIPTION)) {
        probe3id = probe.getProbeId();
      }
      if (probe.getProbeName().equals(TestProbe4.PROBE_DESCRIPTION)) {
        probe4id = probe.getProbeId();
      }
    }
  }

  private void startServerAgent() throws Exception {
    serverBC = new MockBundleContext();

    //start blackboard
    serverBA = new BlackboardActivator();
    serverBA.start(serverBC);

    //start registry plugin to keep track of registered plugins
    rpa = new RegistryPluginActivator();
    rpa.start(serverBC);
    registry = rpa.getRegistryPlugin();

    PersistencePluginActivator ppa = new PersistencePluginActivator();
    ppa.start(serverBC);

    //start SAC plugin
    Properties props = new Properties();
    //fake properties to allow the SAC to load
    props.setProperty(Const.SAC_WS_URL, "localhost:11111");
    //props.setProperty(Const.MFW_WS_PORT, ""+PortManager.next());
    props.setProperty(Const.MFW_WS_URL, "http://localhost:22222/");
    spa = new SACPluginActivator(props);
    spa.start(serverBC);
    sac = spa.getSac();

    //start server agent plugin to communicate with probe-agents
    serverPort = PortManager.next();
    serverActivator = new SAXmlRpcPluginActivator(serverPort);
    serverActivator.start(serverBC);

  }

  private void startProbeAgent() throws Exception {
    probeBC = new MockBundleContext();

    //start blackboard
    probeBA = new BlackboardActivator();
    probeBA.start(probeBC);

    int probePort = PortManager.next();
    System.out.println("probeport:"+probePort);
    ProbeAgentConfig probeConfig = new ProbeAgentConfig(probePort, PortManager.testUrlStringFor(serverPort), 1000, 100, 20000);
    probeActivator = new PAXmlRpcPluginActivator(probeConfig);
    probeActivator.start(probeBC);

    cpa = new CommunicationPluginActivator();
    cpa.start(probeBC);

    mpa = new MeasurementPluginActivator(5, 5);
    mpa.start(probeBC);

    //this properties file is here to stop trying to read it from file unnecessarily
    Properties configuration = new Properties();
    tpa = new TestProbeActivator(configuration);
    tpa.start(probeBC);
    testProbe1 = tpa.getTestProbe1();

    Thread.sleep(500);
  }
}
