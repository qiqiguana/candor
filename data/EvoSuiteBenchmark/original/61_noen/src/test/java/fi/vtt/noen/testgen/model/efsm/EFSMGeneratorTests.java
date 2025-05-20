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
import org.junit.Before;
import fi.vtt.noen.testgen.model.fsm.FSMState;
import fi.vtt.noen.testgen.model.daikon.DaikonState;
import fi.vtt.noen.testgen.model.daikon.DaikonModel;
import fi.vtt.noen.testgen.model.daikon.DaikonTransition;
import fi.vtt.noen.testgen.model.daikon.constraints.DaikonConstraint;
import fi.vtt.noen.testgen.model.daikon.constraints.AlwaysInArrayConstraint;
import fi.vtt.noen.testgen.model.daikon.constraints.GreaterOrEqualConstraint;
import fi.vtt.noen.testgen.model.efsm.EFSMGenerator;
import fi.vtt.noen.testgen.TestObject;
import fi.vtt.noen.testgen.TestOutputInterface;
import fi.vtt.noen.testgen.TestInputInterface;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Teemu Kanstr�n
 */
public class EFSMGeneratorTests {
  private String ln = "\n";
  private EFSMGenerator generator;

  @Before
  public void setup() {
    generator = new EFSMGenerator(TestObject.class, null, null);
  }

  @Test
  public void enumGeneration() {
    Collection<FSMState> states = new ArrayList<FSMState>();
    states.add(new FSMState("State1"));
    states.add(new FSMState("state2"));
    states.add(new FSMState("state3"));

    String expected =
        "  private enum States {"+ln+
        "    Init,"+ln+
        "    State1,"+ln+
        "    State2,"+ln+
        "    State3"+ln+
        "  }"+ln+ln+
        "  public Object getState() {"+ln+
        "    return state;"+ln+
        "  }"+ln;
    String actual = generator.stateEnumFor(states);
    assertEquals("Generated State enumeration", expected, actual);
  }

  @Test
  public void stateGeneration() {
    String expected =
        "  public Object getState() {"+ln+
        "    return state;"+ln+
        "  }"+ln;
    String actual = generator.stateMethod();
    assertEquals("Generated State enumeration", expected, actual);
  }

  @Test
  public void headerGeneration() {
    String expected =
        "package fi.vtt.noen.testgen.generated;" + ln + ln +
        "import static org.junit.Assert.*;" + ln +
        "import static org.easymock.EasyMock.*;" + ln +ln+
        "import org.easymock.EasyMock;" + ln+
        "import org.junit.Before;" + ln+
        "import org.junit.Test;" + ln+
        "import net.sourceforge.czt.modeljunit.Action;"+ln+
        "import net.sourceforge.czt.modeljunit.FsmModel;"+ln+
        "import net.sourceforge.czt.modeljunit.Tester;"+ln+
        "import net.sourceforge.czt.modeljunit.RandomTester;"+ln+
        "import net.sourceforge.czt.modeljunit.GraphListener;"+ln+
        "import net.sourceforge.czt.modeljunit.coverage.CoverageMetric;"+ln+
        "import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage;"+ln+ln+
        "import net.sourceforge.czt.modeljunit.Action;"+ln+
        "import java.util.Iterator;"+ln+
        "import java.util.HashSet;"+ln+
        "import java.util.List;" +ln+
        "import java.util.Random;" +ln+
        "import fi.vtt.noen.testgen.TestObject;"+ln+
        "import java.util.ArrayList;" + ln +
        "import java.util.Collection;" + ln + ln +
        "public class GenMBTModel implements FsmModel {" + ln+
        "  private int testIndex = 1;"+ln;
    String actual = generator.header("fi.vtt.noen.testgen.generated", "GenMBTModel");
    assertEquals(expected, actual);
  }

  @Test
  public void footerGeneration() {
    String expected = "}"+ln;
    String actual = generator.footer();
    assertEquals("Footer", expected, actual);
  }

  @Test
  public void guardGeneration() {
    DaikonState state = new DaikonState("testMethod1");
    DaikonConstraint constraint = new AlwaysInArrayConstraint("name?g", "names?g[]");
    state.addConstraint(constraint);
    constraint = new GreaterOrEqualConstraint("weight?g", "100");
    state.addConstraint(constraint);
    constraint = new GreaterOrEqualConstraint("weight2?0", "155");
    state.addConstraint(constraint);

    DaikonModel dm = new DaikonModel();
    dm.add(state);
    Collection<Class> inputs = new ArrayList<Class>();
    inputs.add(TestInputInterface.class);
    EFSMGenerator generator = new EFSMGenerator(TestObject.class, null, dm, inputs, Collections.EMPTY_LIST);

    String actual = generator.guardFor(state.getFullName());
    String expected =
        "  public boolean testMethod1Guard() {" +ln+
        "    if(weight.size() < 100.0) return false;"+ln+
        "    return true;"+ln+
        "  }"+ln;
    assertEquals(expected, actual);
  }

  @Test
  public void testMethodGeneration() {
    String expected =
        "  @Test"+ln+
        "  public void modelJUnitTest() throws Exception {"+ln+
        "    mockTestOutputInterface = createMock(TestOutputInterface.class);"+ln+
        "    Tester tester = new RandomTester(this);"+ln+
        "    GraphListener listener = tester.buildGraph();"+ln+
        "    listener.printGraphDot(\"MyTestClass.dot\");"+ln+
        "    CoverageMetric trCoverage = new TransitionCoverage();"+ln+
        "    tester.addListener(trCoverage);"+ln+
        "    tester.addListener(\"verbose\");"+ln+
        "    tester.generate(20);"+ln+
        "    tester.getModel().printMessage(trCoverage.getName() + \" was \" + trCoverage.toString());"+ln+
        "  }"+ln+ln;
    Collection<Class> outputs = new ArrayList<Class>();
    outputs.add(TestOutputInterface.class);
    generator = new EFSMGenerator(TestObject.class, null, (DaikonModel)null, Collections.EMPTY_LIST, outputs);
    String actual = generator.testMethod("MyTestClass");
    assertEquals(expected, actual);
  }

  @Test
  public void resetGeneration() {
    String expected =
        "  public void reset(boolean b) {"+ln+
        "    state = \"\";"+ln+
        "    System.out.println(\"------------------- STARTING TEST \"+testIndex+\"--------------------------\");"+ln+
        "    testIndex++;"+ln+
        "    array1.clear();"+ln+
        "    array2.clear();"+ln+
        "    EasyMock.reset(mockTestOutputInterface);"+ln+
        "    try {"+ln+
        "      testObject = createTestObject();"+ln+
        "    } catch (Exception e) {"+ln+
        "      throw new RuntimeException(e);"+ln+
        "    }"+ln+
        "  }"+ln+ln;
    Collection<String> arrayNames = new ArrayList<String>();
    arrayNames.add("array1");
    arrayNames.add("array2");
    Collection<Class> mocks = new ArrayList<Class>();
    mocks.add(TestOutputInterface.class);
    String actual = generator.resetMethod(arrayNames, mocks);
    assertEquals(expected, actual);
  }

  //TODO t�m� kuuluu daikonmodelin testeihin, ei t�nne
  @Test
  public void followingTestParser() {
    DaikonModel dm = new DaikonModel();
    DaikonState state1 = new DaikonState("state1");
    DaikonState state2 = new DaikonState("state2");
    dm.add(state1);
    dm.add(state2);
    dm.add(new DaikonTransition("state1,state2"));
    Collection<DaikonTransition> transitions = dm.getTransitions("state1");
    assertEquals(1, transitions.size());
    DaikonTransition transition = transitions.iterator().next();
    assertEquals("state1", transition.getSource());
    assertEquals("state2", transition.getTarget());
  }

  @Test
  public void parameterIndexParsing() {
    String parameter = "client?1";
    String[] parts = parameter.split("\\?");
    assertEquals("client", parts[0]);
    assertEquals("1", parts[1]);
  }

  @Test
  public void returnValueGeneration() {
    String expected = "    ";
  }

}
