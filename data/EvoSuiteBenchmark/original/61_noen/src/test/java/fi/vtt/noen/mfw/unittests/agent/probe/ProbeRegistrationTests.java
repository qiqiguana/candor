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

package fi.vtt.noen.mfw.unittests.agent.probe;

import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.PAXmlRpcPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.ProbeAgentConfig;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.ProbeAgentImpl;
import fi.vtt.noen.mfw.bundle.probe.shared.Probe;
import fi.vtt.noen.mfw.probes.tester.TestProbeActivator;
import fi.vtt.noen.mfw.unittests.MockBundleContext;
import fi.vtt.noen.mfw.unittests.PortManager;
import fi.vtt.noen.mfw.unittests.system.TestServerAgent;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class ProbeRegistrationTests {
  @Test
  public void registerProbeBeforeAgent() throws Exception {
    MockBundleContext mbc = new MockBundleContext();
    //this properties file is here to stop trying to read it from file unnecessarily
    Properties configuration = new Properties();
    TestProbeActivator tpa = new TestProbeActivator(configuration);
    tpa.start(mbc);

    TestServerAgent server = new TestServerAgent();

    ProbeAgentConfig pac = new ProbeAgentConfig(PortManager.next(), server, 1000, 100, 20000);
    PAXmlRpcPluginActivator xpa = new PAXmlRpcPluginActivator(pac);
    xpa.start(mbc);

    Thread.sleep(500);

    ProbeAgentImpl pai = xpa.getProbeAgent();
    Map<Long,Probe> probes = pai.getProbes();
    assertEquals("Number of registered probes", 6, probes.size());
  }

  @Test
  public void registerProbeAfterAgent() throws Exception {
    MockBundleContext mbc = new MockBundleContext();

    TestServerAgent server = new TestServerAgent();
    ProbeAgentConfig pac = new ProbeAgentConfig(PortManager.next(), server, 1000, 100, 20000);
    PAXmlRpcPluginActivator xpa = new PAXmlRpcPluginActivator(pac);
    xpa.start(mbc);
    ProbeAgentImpl pai = xpa.getProbeAgent();

    //this properties file is here to stop trying to read it from file unnecessarily
    Properties configuration = new Properties();
    TestProbeActivator tpa = new TestProbeActivator(configuration);
    tpa.start(mbc);

    Thread.sleep(500);

    Map<Long,Probe> probes = pai.getProbes();
    assertEquals("Number of registered probes", 6, probes.size());
  }
}
