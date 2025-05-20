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

import fi.vtt.noen.mfw.bundle.server.plugins.sac.SAC;
import fi.vtt.noen.mfw.bundle.server.plugins.sac.sacclient.Availability;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeDisabled;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeRegistered;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Teemu Kanstren
 */
public class MockSAC implements SAC {
  public Collection<String> events = new ArrayList<String>();
  public Map<Long, List<Value>> values = new HashMap<Long, List<Value>>();
  public Collection<ProbeDescription> probes = new ArrayList<ProbeDescription>();
  private int numberOfValuesReceived = 0;
  private boolean invalidBMEventReceived = false;
  private final int sacId;

  public MockSAC(int sacId) {
    this.sacId = sacId;
  }

  public void event(String message) {
    System.out.println("MockSAC:"+sacId+" received event:"+message);
    events.add(message);
    if (message.startsWith("No valid measurement")) {
      invalidBMEventReceived = true;
    }
  }

  public boolean isInvalidBMEventReceived() {
    return invalidBMEventReceived;
  }

  public void bmResult(Value value) {
    numberOfValuesReceived++;
    System.out.println("mocksac:"+sacId+" value:"+value);
    long bmId = value.getBm().getBmId();
    List<Value> bmValues = values.get(bmId);
    if (bmValues == null) {
      bmValues = new ArrayList<Value>();
      values.put(bmId, bmValues);
    }
    bmValues.add(value);
  }

  public int getNumberOfValuesReceived() {
    return numberOfValuesReceived;
  }

  public void probeRegistered(ProbeDescription probe) {
    probes.add(probe);
  }

  public void probeDisabled(ProbeDescription probe) {
    probes.remove(probe);
  }

  public boolean initAvailability() {
    return false;
  }

  public void probeRegistered(ProbeRegistered pr) {
    probes.add(pr.getProbeDescription());
  }

  public void probeDisabled(ProbeDisabled pd) {
    probes.remove(pd.getProbeDescription());
  }

  public void setAvailability(Availability availability) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String toString() {
    return "MockSAC{" +
            "sacId=" + sacId +
            '}';
  }
}
