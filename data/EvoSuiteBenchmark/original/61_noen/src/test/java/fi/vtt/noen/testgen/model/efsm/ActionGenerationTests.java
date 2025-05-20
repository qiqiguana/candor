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

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static fi.vtt.noen.testgen.TestUtils.*;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Method;

import fi.vtt.noen.testgen.TestInputInterface;
import fi.vtt.noen.testgen.TestObject;
import fi.vtt.noen.testgen.TestOutputInterface;
import fi.vtt.noen.testgen.model.fsm.FSMState;
import fi.vtt.noen.testgen.model.daikon.DaikonState;
import fi.vtt.noen.testgen.model.daikon.DaikonModel;
import fi.vtt.noen.testgen.model.daikon.DaikonTransition;
import fi.vtt.noen.testgen.model.daikon.constraints.GreaterOrEqualConstraint;
import fi.vtt.noen.testgen.model.daikon.constraints.ConstantSizeConstraint;

/**
 * @author Teemu Kanstrén
 */
public class ActionGenerationTests {
  private String ln = "\n";

  @Test
  public void actionGenerationWithoutInvariants() {
    Collection<Class> inputs = new ArrayList<Class>();
    inputs.add(TestInputInterface.class);
    Collection<Class> outputs = new ArrayList<Class>();
    outputs.add(TestOutputInterface.class);
    DaikonModel dm = new DaikonModel();
    DaikonTransition transition = new DaikonTransition("testMethod1,outputState");
    transition.addConstraint(new ConstantSizeConstraint("size(clients?g[])", "2"));
    dm.add(transition);
    EFSMGenerator generator = new EFSMGenerator(TestObject.class, null, dm, inputs, outputs);
    FSMState state1 = new FSMState("testMethod1");
    String actual = generator.actionFor(state1.id());
    String expected =
        "  @Action"+ln+
        "  public void testMethod1() throws Exception {"+ln+
        "    this.state = \"TestMethod1\";"+ln+
        "    System.out.println(\"TESTMETHOD1\");"+ln+
        "    replay(mockTestOutputInterface);"+ln+
        "    testObject.testMethod1();"+ln+
        "    verify(mockTestOutputInterface);"+ln+
        "    EasyMock.reset(mockTestOutputInterface);"+ln+
        "  }"+ln;
    assertEquals("Generated @Action transition method", expected, actual);
  }

  //@Test
  public void actionGenerationWithInvariants() {
    DaikonModel dm = new DaikonModel();
    EFSMGenerator generator = new EFSMGenerator(TestObject.class, null, dm, inputs(), outputs());
    FSMState state1 = new FSMState("TestMethod1");
    String actual = generator.actionFor(state1.id());
    String expected =
        "  @Action"+ln+
        "  public void TestMethod1() {"+ln+
        "    //TODO: call method TestMethod1()"+ln+
        "    if ("+ln+
        "    this.state = States.TestMethod1;"+ln+
        "  }"+ln;
    assertEquals("Generated @Action transition method", expected, actual);
  }

  @Test
  public void mockInit() {
    String expected = "mockTestOutputInterface = createMock(TestOutputInterface.class);"+ln;
    assertEquals(expected, MockGenerator.mockInit(TestOutputInterface.class.getSimpleName()));
  }

  @Test
  public void replayFor() {
    String expected = "replay(mockTestOutputInterface);"+ln;
    MockGenerator mocker = new MockGenerator(TestOutputInterface.class);
    assertEquals(expected, mocker.replay());
  }

  @Test
  public void mockCallFor() throws Exception {
    String expected = "expect(mockTestOutputInterface.outputState()).andReturn(ok);"+ln;
    MockGenerator mocker = new MockGenerator(TestOutputInterface.class);
    Class outputInterfaceClass = TestOutputInterface.class;
    Method method = outputInterfaceClass.getMethod("outputState");
    assertEquals(expected, mocker.callFor(method, "ok"));
  }

  @Test
  public void verifyFor() {
    String expected = "verify(mockTestOutputInterface);"+ln+"EasyMock.reset(mockTestOutputInterface);"+ln;
    MockGenerator mocker = new MockGenerator(TestOutputInterface.class);
    assertEquals(expected, mocker.verify());
  }

  @Test
  public void methodCallForSUT() {
    DaikonModel dm = new DaikonModel();
    EFSMGenerator generator = new EFSMGenerator(TestObject.class, null, dm, inputs(), outputs());
    String expected = "testObject.testMethod1();"+ln;
    assertEquals(expected, generator.sutCall("testMethod1"));
  }
}
