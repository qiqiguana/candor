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

import fi.vtt.noen.mfw.bundle.common.DataObject;
import fi.vtt.noen.mfw.bundle.common.KnowledgeSource;
import fi.vtt.noen.mfw.bundle.common.ProbeConfiguration;
import fi.vtt.noen.mfw.bundle.server.plugins.xmlrpc.ServerPlugin;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeDescription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Teemu Kanstren
 */
public class InMemoryServerPlugin implements ServerPlugin, KnowledgeSource {
  private Collection<String> subscriptions = new ArrayList<String>();

  @Override
  public Set<Class> getCommands() {
    return null;
  }

  @Override
  public void process(DataObject data) {
  }

  @Override
  public String getName() {
    return null;
  }

  public boolean isSubscribed(int bmId, long subscriptionId) {
    return subscriptions.contains("R:"+bmId+"/"+subscriptionId);
  }

  @Override
  public void requestBM(long bmId, long subscriptionId) {
    subscriptions.add("R:"+bmId+"/"+subscriptionId);
  }

  @Override
  public void subscribeToBM(long bmId, long interval, long subscriptionId) {
    throw new IllegalStateException("subscriptions not supported in this test model");
  }

  @Override
  public void unSubscribeToBM(long bmId, long subscriptionId) {
    subscriptions.remove(bmId+"/"+subscriptionId);
  }

  @Override
  public boolean setProbeConfiguration(long probeId, Map<String, String> configuration) {
    throw new UnsupportedOperationException("probe configuration not supported in this test model");
  }

  @Override
  public Collection<ProbeConfiguration> requestProbeConfigurationParameters(long probeId) {
    throw new UnsupportedOperationException("probe configuration not supported in this test model");
  }

  @Override
  public void unSubscribeToBM(ProbeDescription probeDescription, Long subscriptionId) {
    throw new UnsupportedOperationException("unsubscribe through probe id not supported in this test model");
  }

  @Override
  public void setReference(long subscriptionId, String reference) {
    throw new UnsupportedOperationException("setting reference not supported in this test model");
  }
}
