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

import fi.vtt.noen.mfw.bundle.common.ProbeConfiguration;
import fi.vtt.noen.mfw.bundle.probe.shared.Probe;
import fi.vtt.noen.mfw.bundle.probe.shared.ProbeAgent;
import fi.vtt.noen.mfw.bundle.server.shared.ServerAgent;

import java.util.List;
import java.util.Map;

public class TestProbeAgent implements ProbeAgent {
  private int counter = 0;
  private final int replyValue;
  private final ServerAgent server;

  public TestProbeAgent(ServerAgent server) {
    this.server = server;
    this.replyValue = -1;
  }

  public TestProbeAgent(ServerAgent server, int replyValue) {
    this.server = server;
    this.replyValue = replyValue;
  }

  public int getStartCount() {
    return counter;
  }

  public void startProbe(long probeId) {
    System.out.println("start called..");
    counter++;
  }

  public void stopProbe(long probeId) {

  }

  public void requestMeasure(String measureURI, long subscriptionId) {
    if (replyValue < 0) {
      server.measurement(System.currentTimeMillis(), measureURI, 1, "true", subscriptionId);
    } else {
      server.measurement(System.currentTimeMillis(), measureURI, 1, ""+replyValue, subscriptionId);
    }
  }

  public void subscribe(String measureURI, long interval, long subscriptionId) {
    // TODO Auto-generated method stub
  }

  public void unSubscribe(String measureURI, long subscriptionId) {
    // TODO Auto-generated method stub
  }

  public List<ProbeConfiguration> getConfigurationParameters(long probeId) {
    // TODO Auto-generated method stub
    return null;
  }

  public void setConfiguration(long probeId, Map<String, String> configuration) {

  }

  public Map<Long, Probe> getProbes() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setReference(long subscriptionId, String reference) {
    // TODO Auto-generated method stub
    
  }
}