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

import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.XmlRpcServerClient;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.XmlRpcServerServer;
import fi.vtt.noen.mfw.unittests.PortManager;
import fi.vtt.noen.mfw.unittests.system.TestServerAgent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class ServerAgentTests {
  @Test
  public void fourMeasures() throws Exception {
    TestServerAgent testServer = new TestServerAgent();
    int port = PortManager.next();
    XmlRpcServerServer serverServer = new XmlRpcServerServer(port, testServer);
    serverServer.start();
    XmlRpcServerClient serverClient = new XmlRpcServerClient(PortManager.testUrlFor(port));
    long time = System.currentTimeMillis();
    serverClient.measurement(time, "measureURI1", 1, "1", -1);
    serverClient.measurement(time, "measureURI1", 1, "22.3", -1);
    serverClient.measurement(time, "measureURI3", 1, "226.3", -1);
    serverClient.measurement(time, "measureURI5", 1, "hello", -1);
    assertEquals(2, testServer.getReceivedStrings("measureURI1"));
    assertEquals(1, testServer.getReceivedStrings("measureURI5"));
    assertEquals(0, testServer.getReceivedDoubles("measureURI2"));
    assertEquals(4, testServer.getTotalReceivedMeasures());
    serverServer.stop();
  }

  @Test
  public void twoProbes() throws Exception {
    TestServerAgent testServer = new TestServerAgent();
    int port = PortManager.next();
    XmlRpcServerServer serverServer = new XmlRpcServerServer(port, testServer);
    serverServer.start();
    XmlRpcServerClient serverClient = new XmlRpcServerClient(PortManager.testUrlFor(port));
    XmlRpcServerClient serverClient2 = new XmlRpcServerClient(PortManager.testUrlFor(port));
    long time = System.currentTimeMillis();
    serverClient.measurement(time, "measureURI1", 1, "1", -1);
    serverClient.measurement(time, "measureURI1", 1, "22.3d", -1);
    serverClient.measurement(time, "measureURI1", 1, "226.3d", -1);
    serverClient.measurement(time, "measureURI5", 1, "hello", -1);
    serverClient2.measurement(time, "measureURI2-1", 1, "hello", -1);
    serverClient2.measurement(time, "measureURI2-1", 1, "4", -1);
    serverClient2.measurement(time, "measureURI2-1", 1, "true", -1);
    assertEquals(3, testServer.getReceivedStrings("measureURI2-1"));
    assertEquals(3, testServer.getReceivedStrings("measureURI1"));
    assertEquals(1, testServer.getReceivedStrings("measureURI5"));
    assertEquals(0, testServer.getReceivedStrings("measureURI2"));
    assertEquals(7, testServer.getTotalReceivedMeasures());
    serverServer.stop();
  }

}
