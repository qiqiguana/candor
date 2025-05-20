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

import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.probes.tester.TestProbeActivator;
import org.junit.Test;

import java.util.Collection;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class ProbeServerRegistrationTests extends BaseSystemTest {
  @Test
  public void registerProbe() throws Exception {
    TestProbeActivator tpa = new TestProbeActivator(new Properties());
    tpa.start(pbc);
    probeActivators.add(tpa);

    Thread.sleep(2000);

    Collection<BMDescription> bms = registry.getAvailableBM();
    assertEquals("Registration of probes to server failed", 5, bms.size());
  }
}
