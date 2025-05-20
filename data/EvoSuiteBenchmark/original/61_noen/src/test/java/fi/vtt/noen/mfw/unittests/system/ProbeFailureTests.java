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

import fi.vtt.noen.mfw.bundle.probe.shared.BaseMeasure;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.probes.tester.TestProbe;
import fi.vtt.noen.mfw.unittests.system.testprobes.ConfigurableTestProbeActivator;
import fi.vtt.noen.mfw.unittests.system.testprobes.MockSAC;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class ProbeFailureTests extends BaseSystemTest {
  @Test
  public void nullBMInformation() throws Exception {
    TestProbe probe = new TestProbe("target-name", "target type", null, null, null, "probe description", 0);
    ConfigurableTestProbeActivator cta = new ConfigurableTestProbeActivator(probe);
    cta.start(pbc);

    assertEquals("Probe registration should fail with missing BM information", 0, probeAgent.getProbes().size());
    assertEquals("No probe should be registered with missing BM information", 0, registry.getProbes().size());
    assertEquals("No BM should be registered with missing BM information", 0, registry.getAvailableBM().size());
  }

  @Test
  public void nullTargetInformation() throws Exception {
    TestProbe probe = new TestProbe(null, null, "bm class", "bm name", "bm description", "probe description", 0);
    ConfigurableTestProbeActivator cta = new ConfigurableTestProbeActivator(probe);
    cta.start(pbc);

    assertEquals("Probe registration should fail with missing BM information", 0, probeAgent.getProbes().size());
    assertEquals("No probe should be registered with missing target information", 0, registry.getProbes().size());
    assertEquals("No BM should be registered with missing target information", 0, registry.getAvailableBM().size());
  }

  @Test
  public void nullBMValue() throws Exception {
    TestProbe probe = new TestProbe("target name", "target type", "bm class", "bm name", "bm description", "probe description", null, 0);
    ConfigurableTestProbeActivator cta = new ConfigurableTestProbeActivator(probe);
    cta.start(pbc);

    Thread.sleep(500);

    List<BMDescription> bms = registry.getAvailableBM();
    assertEquals("Should have one probe registered", 1, bms.size());

    MockSAC mockSAC = new MockSAC(-1);
    sac.register(-1, mockSAC);
    boolean ok = sac.requestBM(-1, bms.get(0).getBmId());
    assertEquals("request for measure should be ok", true, ok);
    Thread.sleep(500);

    assertEquals("null value should not produce a bmresponse to the SAC", 0, mockSAC.values.size());
    assertTrue("null value should produce a measurement error event to the SAC", mockSAC.isInvalidBMEventReceived());
  }

  @Test
  public void nullBMResult() throws Exception {
    TestProbe probe = new TestProbe("target name", "target type", "bm class", "bm name", "bm description", "probe description", 0) {
      @Override
      public BaseMeasure measure() {
        return null;
      }
    };
    ConfigurableTestProbeActivator cta = new ConfigurableTestProbeActivator(probe);
    cta.start(pbc);

    Thread.sleep(500);

    List<BMDescription> bms = registry.getAvailableBM();
    assertEquals("Should have one probe registered", 1, bms.size());

    MockSAC mockSAC = new MockSAC(55);
    sac.register(55, mockSAC);
    boolean ok = sac.requestBM(55, bms.get(0).getBmId());
    assertEquals("request for measure should be ok", true, ok);
    Thread.sleep(500);

    assertEquals("null value should not produce a bmresponse to the SAC", 0, mockSAC.values.size());
    assertTrue("null value should produce a measurement error event to the SAC", mockSAC.isInvalidBMEventReceived());
  }

  @Test
  public void disconnectedProbeRequest() {
    
  }
}
