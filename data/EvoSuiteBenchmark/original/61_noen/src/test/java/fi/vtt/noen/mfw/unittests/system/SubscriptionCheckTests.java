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

import java.util.List;

import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import org.junit.Test;
import static org.junit.Assert.*;

import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeDescription;
import fi.vtt.noen.mfw.probes.tester.TestProbe;
import fi.vtt.noen.mfw.unittests.system.testprobes.ConfigurableTestProbeActivator;
import fi.vtt.noen.mfw.unittests.system.testprobes.MockSAC;
import org.junit.After;

public class SubscriptionCheckTests extends BaseSystemTest{
  @Test
  public void newSubscriptionOnServer() throws Exception {
    TestProbe probe = new TestProbe("target name", "target type", "bm class", "subscription check test 1", "bm description", "probe description", "test value 1", 0);
    ConfigurableTestProbeActivator cta = new ConfigurableTestProbeActivator(probe);
    cta.start(pbc);
    Thread.sleep(500);
    
    MockSAC mockSac = new MockSAC(-1);
    sac.register(-1, mockSac);
    Thread.sleep(500);
    List<ProbeDescription> probes = sac.getProbeList();
    assertEquals("Should have one probe registered", 1, probes.size());    
    List<BMDescription> bmList = sac.getAvailableBMList();
    BMDescription bm = bmList.get(0);
    long bmId = bm.getBmId();
    assertEquals("subscription check test 1", bm.getBmName());   
    ProbeDescription pd = probes.get(0);
    long probeId = pd.getProbeId();
    assertEquals("Should have 0 value", 0, mockSac.getNumberOfValuesReceived());
    registry.addMeasurementRequest(-1, bm, probeId);
    //wait for subscription checker to synchronize subscriptions (add subscription to probe)
    Thread.sleep(4300);
    assertEquals("Should have 1 value", 1, mockSac.getNumberOfValuesReceived());
    assertEquals("Should have received 'test value 1'", "test value 1", mockSac.values.get(bmId).get(0).getString());
  }
  
  @Test
  public void subscriptionRemovedFromServer() throws Exception {
    TestProbe probe = new TestProbe("target name", "target type", "bm class", "subscription check test 2", "bm description", "probe description", "test value 2", 0);
    ConfigurableTestProbeActivator cta = new ConfigurableTestProbeActivator(probe);
    cta.start(pbc);
    Thread.sleep(500);
    
    MockSAC mockSac = new MockSAC(-1);
    sac.register(-1, mockSac);    
    Thread.sleep(500);    
    List<ProbeDescription> probes = sac.getProbeList();
    assertEquals("Should have one probe registered", 1, probes.size());    
    List<BMDescription> bmList = sac.getAvailableBMList();
    BMDescription bm = bmList.get(0);
    assertEquals("subscription check test 2", bm.getBmName());
    long bmId = bm.getBmId();
    assertEquals("Should have 0 value", 0, mockSac.getNumberOfValuesReceived());
    sac.subscribeToBM(-1, bmId, 1000);
    Thread.sleep(500);
    assertEquals("Should have 1 value", 1, mockSac.getNumberOfValuesReceived());
    assertEquals("Should have received 'test value 2'", "test value 2", mockSac.values.get(bmId).get(0).getString());
    Thread.sleep(1000);
    assertEquals("Should have 2 values", 2, mockSac.getNumberOfValuesReceived());
    
    long subscriptionId = registry.getIdForSubscription(-1, bmId);
    registry.removeSubscription(-1, subscriptionId);
    //wait for subscription checker to synchronize subscriptions (remove subscription from probe)
    Thread.sleep(4300);
    assertTrue("Should have less than 6 values", mockSac.getNumberOfValuesReceived() < 6);
  }
  
  @Test
  public void betterProbeRegistered() throws Exception {
    TestProbe probe = new TestProbe("target name", "target type", "bm class", "subscription check test 3", "bm description", "probe 1", "test value 3", 0);
    ConfigurableTestProbeActivator cta = new ConfigurableTestProbeActivator(probe);
    cta.start(pbc);
    Thread.sleep(500);
    
    MockSAC mockSac = new MockSAC(-1);
    sac.register(-1, mockSac);
    Thread.sleep(500);
    List<ProbeDescription> probes = sac.getProbeList();
    assertEquals("Should have one probe registered", 1, probes.size());    
    List<BMDescription> bmList = sac.getAvailableBMList();
    BMDescription bm = bmList.get(0);
    assertEquals("subscription check test 3", bm.getBmName());
    assertEquals("Should have 0 value", 0, mockSac.getNumberOfValuesReceived());
    long bmId = bm.getBmId();
    sac.subscribeToBM(-1, bmId, 1000);
    Thread.sleep(500);
    assertEquals("Should have 1 value", 1, mockSac.getNumberOfValuesReceived());
    assertEquals("Should have received 'test value 3'", "test value 3", mockSac.values.get(bmId).get(0).getString());
    
    TestProbe probe2 = new TestProbe("target name", "target type", "bm class", "subscription check test 3", "bm description", "probe 2", "test value 4", 1);
    ConfigurableTestProbeActivator cta2 = new ConfigurableTestProbeActivator(probe2);
    cta2.start(pbc);
    Thread.sleep(1000);   
    probes = sac.getProbeList();
    assertEquals("Should have 2 probes registered", 2, probes.size());
    //wait for subscription checker to change better probe for subscription
    Thread.sleep(4000);
    int values = mockSac.getNumberOfValuesReceived();
    assertTrue("Should have 4 -7values, has "+values, values >= 4 && values <= 7);
    List<Value> values1 = mockSac.values.get(bmId);
    assertEquals("The last value received should be 'test value 4'", "test value 4", values1.get(values1.size()-1).getString());
  }

}
