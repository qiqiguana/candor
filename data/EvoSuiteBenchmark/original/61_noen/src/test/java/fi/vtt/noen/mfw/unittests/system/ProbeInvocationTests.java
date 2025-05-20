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

import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.XmlRpcProbeServer;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.XmlRpcProbeClient;
import fi.vtt.noen.mfw.unittests.PortManager;
import org.junit.After;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class ProbeInvocationTests {
  private XmlRpcProbeServer server;

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void startProbe() throws Exception {
    TestProbeAgent testProbe = new TestProbeAgent(new TestServerAgent());
    int port = PortManager.next();
    server = new XmlRpcProbeServer(testProbe, port);
    server.start();
    URL testUrl = PortManager.testUrlFor(port);
    XmlRpcProbeClient client = new XmlRpcProbeClient(testUrl);
    client.startProbe(1);
    client.startProbe(2);
    client.startProbe(3);
    assertEquals(3, testProbe.getStartCount());
  }

}
