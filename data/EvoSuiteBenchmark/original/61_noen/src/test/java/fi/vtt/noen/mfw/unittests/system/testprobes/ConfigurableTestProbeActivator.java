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

import fi.vtt.noen.mfw.bundle.common.Logger;
import fi.vtt.noen.mfw.bundle.probe.shared.BaseProbeAgentActivator;
import fi.vtt.noen.mfw.bundle.probe.shared.Probe;
import org.osgi.framework.BundleContext;

import java.util.Properties;

/**
 * @author Teemu Kanstren
 */
public class ConfigurableTestProbeActivator extends BaseProbeAgentActivator {
  private final static Logger log = new Logger(ConfigurableTestProbeActivator.class);
  private final Probe probe;

  public ConfigurableTestProbeActivator(Probe probe) {
    super(log, new Properties(), "not-used");
    this.probe = probe;
  }

  public void start(BundleContext bc) throws Exception {
    register(bc, probe, 1);
  }

  public void stop(BundleContext bc) throws Exception {
  }
}
