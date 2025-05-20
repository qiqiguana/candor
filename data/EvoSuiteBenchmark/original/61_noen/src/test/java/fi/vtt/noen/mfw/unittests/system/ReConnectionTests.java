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

import fi.vtt.noen.mfw.bundle.probe.shared.Probe;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeDescription;
import fi.vtt.noen.mfw.unittests.PortManager;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.*;

/**
 * Test cases to verify the functionality for re-connecting when a probe is connected, the connection is lost
 * and needs to be re-connected after a new connection is available.
 *
 * @author Teemu Kanstren
 */
public class ReConnectionTests extends BaseSystemTest {

  @Override
  public void customizeProperties() {
    keepAliveInterval = 100;
  }

  @Test
  public void reconnectAfterServerConnectionLostAndGained() throws Exception {
    assertEquals("Should have no probes registered when starting test", 0, registry.getProbes().size());

    startTestProbes();

    Thread.sleep(2000);

    //testprobeactivator registers 5 test probes
    assertEquals("Number of probes registered", 6, registry.getProbes().size());

    serverXmlRpcActivator.stop(sbc);
    Thread.sleep(500);
    //the content of "probes" keeps changing, so we need to redo the query all the time
    List<ProbeDescription> probes = registry.getProbes();
    double average = 0;
    for (ProbeDescription probe : probes) {
      int delay = probe.getDelay();
      average += delay;
      assertTrue("Probe delay should be updated to reflect disconnection: probe=" + probe + " delay:" + delay, delay > 0 && delay < 900);
    }
    average /= probes.size();
    serverXmlRpcActivator.start(sbc);

    Thread.sleep(500);
    probes = registry.getProbes();
    for (ProbeDescription probe : probes) {
      assertTrue("Probe delay should be updated to reflect disconnection, now " + probe.getDelay() + " average was " + average, probe.getDelay() < average);
    }
  }

  //test case for when probe is started before the server, showing the probe can connect even if first try fails
  @Test
  public void startProbeFirst() throws Exception {
    stopProbeAgent();
    stopServerAgent();
    int serverPort = PortManager.next();
    startProbeAgent(serverPort);
    System.out.println("serverport:" + serverPort);
    startTestProbes();
    //we sleep to allow PA to start
    Thread.sleep(500);
    startServerAgent(serverPort);
    //we sleep to allow the PA to register with the SA
    Thread.sleep(5000);
    assertEquals(6, registry.getProbes().size());
  }

  //test case for when a connected probe node is shut down, showing the server upholding probe/bm identifiers
  @Test
  public void restartConnectedProbe() throws Exception {
    Map<Long, Probe> probes = probeAgent.getProbes();
    Set<Long> probeIds = probes.keySet();
    System.out.println("test1");
    stopProbeAgent();
    System.out.println("test2");
//    startServerAgent();

    probes = probeAgent.getProbes();
    Set<Long> probeIds2 = probes.keySet();
    assertEquals("Probes should be registered with the same identifiers after restart", probeIds, probeIds2);
    System.out.println("test3");
    startProbeAgent();
  }

  //test case for when the server node is shut down, showing the server upholding probe/bm identifiers
  @Test
  public void restartServerWithProbes() throws Exception {
    startTestProbes();
    Thread.sleep(2000);

    Map<Long, Probe> probes = probeAgent.getProbes();
    Set<Long> probeIds = probes.keySet();
    stopServerAgent();
    Thread.sleep(1000);
    startServerAgent(serverPort);

    Thread.sleep(5000);

    probes = probeAgent.getProbes();
    List<ProbeDescription> registryProbes = registry.getProbes();
    Collection<Long> probeIds3 = new HashSet<Long>();
    for (ProbeDescription registryProbe : registryProbes) {
      probeIds3.add(registryProbe.getProbeId());
    }

    Set<Long> probeIds2 = probes.keySet();
    System.out.println("pi:" + probeIds + " pi2:" + probeIds2);

    assertEquals("Probes should be registered with the same identifiers after restart", probeIds, probeIds2);
    assertEquals("Probes should be registered with the same identifiers after restart", probeIds, probeIds3);
  }

}
