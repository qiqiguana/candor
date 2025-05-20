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
import fi.vtt.noen.testgen.model.fsm.FSMState;
import fi.vtt.noen.testgen.model.fsm.FSMTransition;
import fi.vtt.noen.testgen.model.fsm.FSMModel;
import fi.vtt.noen.testgen.model.daikon.DaikonState;
import fi.vtt.noen.testgen.model.daikon.DaikonModel;
import fi.vtt.noen.testgen.model.daikon.DaikonTransition;
import fi.vtt.noen.testgen.model.daikon.constraints.ConstantSizeConstraint;
import fi.vtt.noen.testgen.model.daikon.constraints.EqualsConstraint;
import fi.vtt.noen.testgen.model.daikon.constraints.GreaterOrEqualConstraint;
import fi.vtt.noen.testgen.model.daikon.constraints.LesserConstraint;
import static fi.vtt.noen.testgen.TestUtils.*;
import fi.vtt.noen.testgen.TestObject;


import java.util.*;

/**
 * @author Teemu Kanstrén
 */
public class EFSMGeneratorFullTest {
  @Test
  public void efsmGeneration() throws Exception {
    String expected = getFileContents(this, "EFSMTestExpectedOutput.java.txt");
    TreeMap<String, FSMState> fsmStates = new TreeMap<String, FSMState>();
    FSMState testMethod1State = new FSMState("testMethod1");
    FSMState testMethod2State = new FSMState("testMethod2");
    fsmStates.put("testMethod1", testMethod1State);
    fsmStates.put("testMethod2", testMethod2State);

    ConstantSizeConstraint constraint = new ConstantSizeConstraint("size(connections?g[])", "1");
    GreaterOrEqualConstraint geConstraint = new GreaterOrEqualConstraint("Bob?2", "511.12");
    LesserConstraint lessConstraint = new LesserConstraint("Bob?2", "1001.55");

    DaikonModel dm = new DaikonModel();
    DaikonState daikonState = new DaikonState("testMethod1");
    dm.add(daikonState);
    daikonState = new DaikonState("testMethod2");
    daikonState.addConstraint(constraint);
    daikonState.addConstraint(geConstraint);
    daikonState.addConstraint(lessConstraint);
    dm.add(daikonState);
    DaikonTransition transition = new DaikonTransition("testMethod2,outputState2");
    transition.addConstraint(new ConstantSizeConstraint("size(clients?g[])", "2"));
    dm.add(transition);

    EqualsConstraint eq = new EqualsConstraint("testitem?r", "\"testing\"");
    daikonState = new DaikonState("testMethod2_EXIT");
    daikonState.addConstraint(eq);
    dm.add(daikonState);

    eq = new EqualsConstraint("ReturnStatus?r", "\"jeejee\"");
    daikonState = new DaikonState("outputState2_EXIT");
    daikonState.addConstraint(eq);
    dm.add(daikonState);

    Set<FSMTransition> transitions = new HashSet<FSMTransition>();
    FSMModel fsmModel = new FSMModel(fsmStates, transitions);
    EFSMGenerator generator = new EFSMGenerator(TestObject.class, fsmModel, dm, inputs(), outputs());
    String actual = generator.generateEFSM("fi.vtt.noen.testgen.model.efsm", "EFSMTestExpectedOutput");
    assertEquals(expected, actual);
  }
}
