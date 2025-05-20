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

package fi.vtt.noen.mfw.osmo;

import fi.vtt.noen.mfw.bundle.common.ProbeConfiguration;
import fi.vtt.noen.mfw.bundle.probe.shared.BaseMeasure;
import fi.vtt.noen.mfw.bundle.probe.shared.Probe;
import fi.vtt.noen.mfw.bundle.probe.shared.ProbeInformation;
import fi.vtt.noen.mfw.bundle.server.plugins.webui.availabilitypage.BMDesc;
import fi.vtt.noen.mfw.bundle.server.plugins.webui.probeparameterpage.ProbeDesc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * @author Teemu Kanstren
 */
public class OsmoTestProbe implements Probe {
  private final String CONFIG_KEY = "test";
  private String configValue = "";
  private int bmCount = 0;
  private static int probeCount = 1;

  @Override
  public ProbeInformation getInformation() {
    String me = getClass().getName();
    String targetName = me+" target name "+probeCount;
    String targetType = me+" target type "+probeCount;
    String bmClass = me+" bm class "+probeCount;
    String bmName = me+" bm name "+probeCount;
    String bmDesc = me+" bm description "+probeCount;
    String probeDesc = me+" probe description "+probeCount;
    probeCount++;
    String url = "http://localhost:6666/xmlrpc";
    return new ProbeInformation(targetName, targetType, bmClass, bmName, bmDesc, probeDesc, 1, url);
  }

  @Override
  public BaseMeasure measure() {
    String value = getClass().getName() + " measure " + bmCount;
    bmCount++;
    return new BaseMeasure(value);
  }

  @Override
  public void startProbe() {
  }

  @Override
  public void stopProbe() {
  }

  @Override
  public void setConfiguration(Map<String, String> configuration) {
    configValue = configuration.get(CONFIG_KEY);
  }

  @Override
  public Collection<ProbeConfiguration> getConfigurationParameters() {
    Collection<ProbeConfiguration> config = new ArrayList<ProbeConfiguration>();
    ProbeConfiguration item = new ProbeConfiguration(CONFIG_KEY, "test item", true, configValue);
    config.add(item);
    return config;
  }

  @Override
  public void init(Properties properties) {
  }
}
