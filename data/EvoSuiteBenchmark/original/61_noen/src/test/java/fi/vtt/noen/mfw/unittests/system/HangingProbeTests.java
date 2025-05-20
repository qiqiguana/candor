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

import fi.vtt.noen.mfw.bundle.common.Const;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.ProbeAgentConfig;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import fi.vtt.noen.mfw.probes.tester.TestProbeActivator;
import fi.vtt.noen.mfw.unittests.system.testprobes.HangingProbeActivator;
import fi.vtt.noen.mfw.unittests.system.testprobes.MockSAC;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class HangingProbeTests extends BaseSystemTest {
  @Override
  public void customizeProperties() {
    localInUse = true;
    taskTimeOut = 1;
  }

  @Test
  public void oneTimeProbeHangs() throws Exception {
    Properties props = new Properties();
    props.put(Const.THREAD_POOL_SIZE, 5);
    props.put(Const.TASK_TIMEOUT, 1000);
    HangingProbeActivator hpa = new HangingProbeActivator(props);
    hpa.start(pbc);

    MockSAC mockSac = new MockSAC(-1);
    sac.register(-1, mockSac);

    Thread.sleep(1000);
    List<BMDescription> bmList = sac.getAvailableBMList();
    BMDescription bm = bmList.get(0);
    assertEquals("hanging bm", bm.getBmClass());

    long bmId = bm.getBmId();
    sac.requestBM(-1, bmId);
    Thread.sleep(3000);
    assertEquals("Should have 1 value", 1, mockSac.values.size());
    List<Value> values = mockSac.values.get(bmId);
    assertEquals("Should have 1 value", 1, values.size());
    Value value = values.get(0);
    assertEquals("Hung probe should report error", true, value.isError());
  }

  @Test
  public void subscriptionProbeHangs() throws Exception {
    Properties props = new Properties();
    props.put(Const.THREAD_POOL_SIZE, 5);
    props.put(Const.TASK_TIMEOUT, 1000);
    HangingProbeActivator hpa = new HangingProbeActivator(props);
    hpa.start(pbc);

    MockSAC mockSac = new MockSAC(-1);
    sac.register(-1, mockSac);

    Thread.sleep(1000);
    List<BMDescription> bmList = sac.getAvailableBMList();
    BMDescription bm = bmList.get(0);
    assertEquals("hanging bm", bm.getBmClass());

    long bmId = bm.getBmId();
    sac.subscribeToBM(-1, bmId, 1000);
    Thread.sleep(3000);
    assertEquals("Should have 1 value", 1, mockSac.values.size());
    List<Value> values = mockSac.values.get(bmId);
    assertEquals("Should have 1 value", 1, values.size());
    Value value = values.get(0);
    assertEquals("Hung probe should report error", true, value.isError());
  }

  @Test
  public void oneOfSeveralProbesHangsWithMultipleThreads() throws Exception {
    Properties props = new Properties();
    props.put(Const.THREAD_POOL_SIZE, 2);
    props.put(Const.TASK_TIMEOUT, 1000);

    HangingProbeActivator hpa = new HangingProbeActivator(props);
    hpa.start(pbc);

    TestProbeActivator tpa = new TestProbeActivator(props);
    tpa.start(pbc);

    MockSAC mockSac = new MockSAC(-1);
    sac.register(-1, mockSac);

    Thread.sleep(1000);
    List<BMDescription> bmList = sac.getAvailableBMList();
    BMDescription bm = null;
    for (BMDescription bmDescription : bmList) {
      if (bmDescription.getBmClass().equals("hanging bm")) {
        bm = bmDescription;
        break;
      }
    }
    assertNotNull("Should have haging bm listed", bm);
    assertEquals("hanging bm", bm.getBmClass());

    long bmId = bm.getBmId();
    sac.subscribeToBM(-1, bmId, 1000);
    Thread.sleep(3000);
    assertEquals("Should have 1 value", 1, mockSac.values.size());
    List<Value> values = mockSac.values.get(bmId);
    assertEquals("Should have 1 value", 1, values.size());
    Value value = values.get(0);
    assertEquals("Hung probe should report error", true, value.isError());
  }

  public void oneOfSeveralProbesHangsWithSingleThread() {

  }
}
