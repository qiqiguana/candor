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

package fi.vtt.noen.testgen.parser;

import org.junit.Test;
import org.junit.Before;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Collection;

import fi.vtt.noen.testgen.model.fsm.FSMModel;
import fi.vtt.noen.testgen.model.fsm.FSMState;
import fi.vtt.noen.testgen.model.fsm.FSMTransition;

import static org.junit.Assert.*;

/**
 * @author Teemu Kanstrén
 */
public class PromParserTests {
  private InputStream in;
  private PromParser parser;
  private FSMModel fsm;

  @Before
  public void setup() {
    in = getClass().getResourceAsStream("prom-test-output.txt");
    parser = new PromParser();
    fsm = parser.parse(in);
  }

  @Test
  public void correctNumberOfStatesAndTransitions() {
    assertEquals("Number of states", 15, fsm.numberOfStates());
    assertEquals("Number of transitions", 30, fsm.numberOfTransitions());//XPROM change reduced by 1?
  }

  @Test
  public void transitionsForEachState() {
    //this is initial state (no longer in XPROM update?)
//    String[] transitions = new String[] { "Crequest", "Csubscribe", "Cdisconnect", "Cunsubscribe", "Cconnect", "Message received"};
//    assertStateTransitions("", transitions);

    String[] transitions = new String[] { "Error: Ship Not Found", "Error: Not Connected", "Request OK"};
    assertStateTransitions("Crequest", transitions);

    //XPROM update removed 1 state (self-loop)
    transitions = new String[] { "Error: Already Connected", "Csubscribe", "Cnew", "Cdisconnect", "Cunsubscribe", "Crequest"};
    assertStateTransitions("Cconnect", transitions);

    //add more if needed..

  }

  private void assertStateTransitions(String sourceId, String[] targetIds) {
    //we do NOT want this since it screws up combination of daikon+prom
//    sourceId = "[["+sourceId+"]]";
    FSMState state = fsm.getState(sourceId);
    Collection transitions = fsm.getTransitionsFor(state);
    System.out.println("transitions:"+transitions);
    assertNotNull("State "+sourceId+" should exist", state);
    assertEquals("Number of transitions for "+sourceId, targetIds.length, transitions.size());
    for (int i = 0; i < targetIds.length; i++) {
      String transition = targetIds[i];
//      transition = "[["+transition+"]]";
      assertStateHasTransitionTo(state, transition);
    }
  }

  private void assertStateHasTransitionTo(FSMState state, String expectedTarget) {
    boolean found = false;
    Collection transitions = fsm.getTransitionsFor(state);
    for (Iterator<FSMTransition> i = transitions.iterator(); i.hasNext();) {
      FSMTransition transition = i.next();
      FSMState source = transition.getSource();
      FSMState target = transition.getTarget();
      assertEquals("Source should always be state itself", state, source);
      if (target.toString().equals(expectedTarget)) {
        found = true;
        break;
      }
    }
    assertTrue("State "+state+" should have transition to "+expectedTarget, found);
  }
}
