/*
 * Copyright (C) 2009 VTT Technical Research Centre of Finland.
 *
 * This file is part of NOEN framework.
 *
 * NOEN framework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2.
 *
 * NOEN framework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package fi.vtt.noen.testgen.model.efsm;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import net.sourceforge.czt.modeljunit.Action;
import net.sourceforge.czt.modeljunit.FsmModel;
import net.sourceforge.czt.modeljunit.Tester;
import net.sourceforge.czt.modeljunit.RandomTester;
import net.sourceforge.czt.modeljunit.GraphListener;
import net.sourceforge.czt.modeljunit.coverage.CoverageMetric;
import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage;

import fi.vtt.noen.testgen.TestInputInterface;
import fi.vtt.noen.testgen.TestInputInterface2;
import fi.vtt.noen.testgen.TestObject;
import fi.vtt.noen.testgen.TestOutputInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class EFSMTestExpectedOutput implements FsmModel {
  private Collection connections = new ArrayList();
  private Collection clients = new ArrayList();
  private String state = "";
  private TestObject testObject;
  private TestOutputInterface mockTestOutputInterface;

  @Before
  public void setup() {
    mockTestOutputInterface = createMock(TestOutputInterface.class);
    testObject = createTestObject(mockTestOutputInterface);
  }

  @Test
  @org.junit.Ignore
  public void modelJUnitTest() throws Exception {
    Tester tester = new RandomTester(new EFSMTestExpectedOutput());
    GraphListener listener = tester.buildGraph();
    listener.printGraphDot("EFSMTestExpectedOutput.dot");
    CoverageMetric trCoverage = new TransitionCoverage();
    tester.addListener(trCoverage);
    tester.addListener("verbose");
    tester.generate(20);
    tester.getModel().printMessage(trCoverage.getName() + " was " + trCoverage.toString());
  }

  public void reset(boolean b) {
    connections.clear();
    clients.clear();
    EasyMock.reset(mockTestOutputInterface);
  }

  public Object getState() {
    return state;
  }

  @Action
  public void testMethod1() {
    this.state = "TestMethod1";
    testObject.testMethod1();
  }

  public boolean testMethod1Guard() {
    return true;
  }

  @Action
  public void testMethod2() {
    this.state = "TestMethod2";
    if (clients.size() == 2.0) {
      this.state += "->OutputState2";
      mockTestOutputInterface.outputState2(null, 0);
      replay(mockTestOutputInterface);
      String rv1 = testObject.testMethod2(testMethod2_p1(), testMethod2_p2(), testMethod2_p3());
      verify(mockTestOutputInterface);
      assertEquals("testing", rv1);
      return;
    }
    testObject.testMethod2(testMethod2_p1(), testMethod2_p2(), testMethod2_p3());
  }

  public boolean testMethod2Guard() {
    if (!(connections.size() == 1.0)) {
      return false;
    }
    return true;
  }

  //---------- TODO IMPLEMENT METHODS IN THIS SECTION TO GENERATE OBJECTS ------------
  private TestObject createTestObject(TestOutputInterface mockTestOutputInterface) {
    return null;
  }

  private Collection testMethod2_p1() {
    return null;
  }

  private Map testMethod2_p2() {
    return null;
  }

  private int testMethod2_p3() {
    return 0;
  }
}
