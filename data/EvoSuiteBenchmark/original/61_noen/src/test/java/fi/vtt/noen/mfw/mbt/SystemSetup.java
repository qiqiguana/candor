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

package fi.vtt.noen.mfw.mbt;

import fi.vtt.noen.mfw.bundle.server.plugins.registry.RegistryPlugin;
import fi.vtt.noen.mfw.bundle.server.plugins.sac.SACPlugin;
import fi.vtt.noen.mfw.probes.tester.TestProbe;
import fi.vtt.noen.mfw.unittests.system.BaseSystemTest;
import fi.vtt.noen.mfw.unittests.system.testprobes.ConfigurableTestProbeActivator;
import fi.vtt.noen.mfw.unittests.system.testprobes.MockSAC;

/**
 * @author Teemu Kanstren
 */
public class SystemSetup extends BaseSystemTest {
  //number of generated string
  private int n = 0;
  private MockSAC mockSAC;

  public void init() throws Exception {
    localInUse = true;
    super.setup();
    mockSAC = new MockSAC(-1);
    sac.register(-1, mockSAC);
  }

  public void teardown() throws Exception {
    super.teardown();
  }

  public RegistryPlugin getRegistry() {
    return registry;
  }

  public SACPlugin getSACPlugin() {
    return sac;
  }

  public MockSAC getMockSAC() {
    return mockSAC;
  }

  public TestProbe addProbe() throws Exception {
    TestProbe probe = new TestProbe("target name"+n, "target type"+n, "bm class"+n, "bm name"+n, "bm description"+n, "probe description"+n, 1);
    n++;
    ConfigurableTestProbeActivator cpa = new ConfigurableTestProbeActivator(probe);
    cpa.start(pbc);
    probeActivators.add(cpa);
    return probe;
  }
}
