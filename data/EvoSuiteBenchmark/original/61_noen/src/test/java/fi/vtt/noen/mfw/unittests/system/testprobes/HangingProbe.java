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

package fi.vtt.noen.mfw.unittests.system.testprobes;

import fi.vtt.noen.mfw.bundle.common.ProbeConfiguration;
import fi.vtt.noen.mfw.bundle.probe.shared.BaseMeasure;
import fi.vtt.noen.mfw.bundle.probe.shared.Probe;
import fi.vtt.noen.mfw.bundle.probe.shared.ProbeInformation;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * @author Teemu Kanstren
 */
public class HangingProbe implements Probe {
  public ProbeInformation getInformation() {
    return new ProbeInformation("target1", "type1", "hanging bm", "bmname1", "bmdescription1", "probedescription1", 1, null);
  }

  public BaseMeasure measure() {
    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
    }
    return null;
  }

  public void startProbe() {
  }

  public void stopProbe() {
  }

  public void setConfiguration(Map<String, String> configuration) {
  }

  public Collection<ProbeConfiguration> getConfigurationParameters() {
    return null;
  }

  public void init(Properties properties) {
  }
}
