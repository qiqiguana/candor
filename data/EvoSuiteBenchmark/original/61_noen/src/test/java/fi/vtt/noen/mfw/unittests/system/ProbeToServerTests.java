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

import fi.vtt.noen.mfw.bundle.common.Const;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.XmlRpcProbeServer;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.XmlRpcServerClient;
import fi.vtt.noen.mfw.bundle.probe.shared.ProbeAgent;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.XmlRpcProbeClient;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.XmlRpcServerServer;
import fi.vtt.noen.mfw.bundle.server.shared.ServerAgent;
import fi.vtt.noen.mfw.unittests.PortManager;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class ProbeToServerTests {
  protected String serverUrl = null;
  protected XmlRpcServerServer serverServer = null;
  private XmlRpcProbeServer probeServer;

  @After
  public void teardown() {
    if (serverServer != null) {
      serverServer.stop();
    }
    if (probeServer != null) {
      probeServer.stop();
    }
  }

  @Test
  public void startAndMeasure() throws Exception {
    TestServerAgent testServer = new TestServerAgent();
    ProbeAgent testProbe = new TestProbeAgent(testServer);
    startProbe(testProbe, 1);
    startServer(testServer);
    String measureURI = Const.createMeasureURI("targetType1", "target1", "bmClass1", "measure1");
    testProbe.requestMeasure(measureURI, -1);
    waitForStringMeasure(testServer, measureURI);
  }

  protected XmlRpcProbeClient startProbe(ProbeAgent testProbe, int probeId) throws Exception {
    int port = PortManager.next();
    probeServer = new XmlRpcProbeServer(testProbe, port);
    probeServer.start();
    XmlRpcProbeClient probeClient = new XmlRpcProbeClient(PortManager.testUrlFor(port));
    probeClient.startProbe(probeId);
    return probeClient;
  }

  protected XmlRpcServerClient startServer(ServerAgent testServer) throws Exception {
    int port = PortManager.next();
    serverServer = new XmlRpcServerServer(port, testServer);
    serverServer.start();
    serverUrl = PortManager.testUrlStringFor(port);
    XmlRpcServerClient serverClient = new XmlRpcServerClient(PortManager.testUrlFor(port));
    return serverClient;
  }

  protected void waitForStringMeasure(TestServerAgent testServer, String measureURI) {
    synchronized (testServer) {
      testServer.waitForMeasure(measureURI, 1000);
    }
    assertEquals(0, testServer.getReceivedBooleans(measureURI));
    assertEquals(0, testServer.getReceivedDoubles(measureURI));
    assertEquals(1, testServer.getReceivedStrings(measureURI));
  }
}
