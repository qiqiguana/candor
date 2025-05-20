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

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcSunHttpTransportFactory;
import org.apache.xmlrpc.client.util.ClientFactory;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.junit.Test;

import java.net.URL;

/**
 * @author Teemu Kanstren
 */
public class ClientServerTest {
  @Test
  public void connect() throws Exception {
    startServer();
    runClient();
  }

  private void startServer() throws Exception {
    WebServer webServer = new WebServer(7999);

    XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

    PropertyHandlerMapping phm = new PropertyHandlerMapping();

    phm.addHandler("Calculator", Calculator.class);
    phm.addHandler(ICalculator.class.getName(), Calculator.class);
//    phm.addHandler(org.apache.xmlrpc.demo.proxy.Adder.class.getName(), org.apache.xmlrpc.demo.proxy.AdderImpl.class);
    xmlRpcServer.setHandlerMapping(phm);

    //"fi.vtt.noen.mfw.unittests.xmlrpctesting.ICalculator";
    XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
    serverConfig.setEnabledForExtensions(true);
    serverConfig.setContentLengthOptional(false);

    webServer.start();
  }

  public void runClient() throws Exception {
    // create configuration
    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
    config.setServerURL(new URL("http://127.0.0.1:7999/xmlrpc"));
    config.setEnabledForExtensions(true);
    config.setConnectionTimeout(60 * 1000);
    config.setReplyTimeout(60 * 1000);

    XmlRpcClient client = new XmlRpcClient();

    client.setTransportFactory(new XmlRpcSunHttpTransportFactory(client));
    client.setConfig(config);

    // make the a regular call
    Object[] params = new Object[]{new Integer(2), new Integer(3)};
    Integer result = (Integer) client.execute("Calculator.add", params);
    System.out.println("2 + 3 = " + result);

    // make a call using dynamic proxy
    ClientFactory factory = new ClientFactory(client);
    ICalculator calc = (ICalculator) factory.newInstance(ICalculator.class);
    int sum = calc.add(2, 4);
    System.out.println("2 + 4 = " + sum);
//    Adder adder = (Adder) factory.newInstance(Adder.class);
//    int sum = adder.add(2, 4);
//    System.out.println("2 + 4 = " + sum);
  }
}
