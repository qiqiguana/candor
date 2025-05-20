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

package fi.vtt.noen.mfw.unittests.xmlrpctesting;

import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.XmlRpcProbeServer;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.XmlRpcProbeClient;
import fi.vtt.noen.mfw.unittests.PortManager;
import fi.vtt.noen.mfw.unittests.system.TestProbeAgent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class ProbeAgentTests {
  @Test
  public void startProbe() throws Exception {
    //TODO: move port number creation to configuration object
    TestProbeAgent testProbe = new TestProbeAgent(null);
    int port = PortManager.next();
    XmlRpcProbeServer probeServer = new XmlRpcProbeServer(testProbe, port);
    probeServer.start();
    XmlRpcProbeClient probeClient = new XmlRpcProbeClient(PortManager.testUrlFor(port));
    probeClient.startProbe(1);
    probeClient.startProbe(2);
    probeClient.startProbe(3);
    assertEquals(3, testProbe.getStartCount());
  }
}
