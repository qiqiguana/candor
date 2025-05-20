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

import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.XmlRpcServerClient;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.XmlRpcServerServer;
import fi.vtt.noen.mfw.unittests.PortManager;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class ServerInvocationTests {
  private XmlRpcServerServer server;

  @After
  public void tearDown() {
    server.stop();
  }

  @Test
  public void sendMeasure() throws Exception {
    TestServerAgent testServer = new TestServerAgent();
    int port = PortManager.next();
    server = new XmlRpcServerServer(port, testServer);
    server.start();
    XmlRpcServerClient client = new XmlRpcServerClient(PortManager.testUrlFor(port));
    //since only doubles are now in the interface, this will also invoke a double
    long time = System.currentTimeMillis();
    client.measurement(time, "measureURI1", 1, "1", -1);
    client.measurement(time, "measureURI1", 1, "22.3d", -1);
    client.measurement(time, "measureURI1", 1, "226.3d", -1);
    client.measurement(time, "measureURI5", 1, "hello", -1);
    assertEquals(1, testServer.getReceivedStrings("measureURI5"));
    assertEquals(3, testServer.getReceivedStrings("measureURI1"));
    assertEquals(4, testServer.getTotalReceivedMeasures());
  }
}
