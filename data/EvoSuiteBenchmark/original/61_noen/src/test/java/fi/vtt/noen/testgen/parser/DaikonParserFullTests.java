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
import static org.junit.Assert.*;
import static fi.vtt.noen.testgen.TestUtils.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import fi.vtt.noen.testgen.model.daikon.DaikonState;
import fi.vtt.noen.testgen.model.daikon.DaikonModel;
import fi.vtt.noen.testgen.model.daikon.DaikonModelElement;
import fi.vtt.noen.testgen.model.daikon.DaikonTransition;
import fi.vtt.noen.testgen.model.daikon.constraints.DaikonConstraint;

/**
 * @author Teemu Kanstrén
 */
public class DaikonParserFullTests {
  private DaikonModel dm;
  private DaikonState state;
  private Iterator<DaikonConstraint> constraintIterator;

  private void state(String id) {
    state = dm.getState(id);
    constraintIterator = state.getConstraints().iterator();
  }

  private void assertName(String expected) {
    assertEquals(expected, state.getFullName());
  }

  private void constraint(String expected) {
    Object constraint = constraintIterator.next();
    assertEquals(expected, constraint.toString());
  }

  private void finalConstraint(String expected) {
    constraint(expected);
    try {
      constraintIterator.next();
      fail("Should have no more constraints");
    } catch (NoSuchElementException nee) {
      //expected
    }
  }

  private void transition(String source, String target) {
    DaikonTransition transition = dm.getTransition(source, target);
    constraintIterator = transition.getConstraints().iterator();
  }

  @Test
  public void parseAll() throws Exception {
    String text = getFileContents(this, "daikon-test-output.txt");
    DaikonParser parser = new DaikonParser(text);
    dm = parser.parseAll();
//    dm.printStates();

    //TODO add more asserts for assert- and guard generation
    state("Cconnect");
    assertName("Cconnect");
    constraint("reference:Clients elements one of {\"3Dvis\",\"myclient\",\"vis\"}");
    constraint("reference:Subscriptions == []");
    constraint("reference:Messages elements one of {\"11155366\",\"29959477\"}");
    constraint("sizeof(reference:Clients) one of {0.0,1.0,2.0}");
    finalConstraint("sizeof(reference:Messages) one of {0.0,1.0}");
//    constraint("sizeof(reference:Clients) >= sizeof(reference:Subscriptions)");
//    constraint("sizeof(reference:Clients) >= sizeof(reference:Messages)-1");
//    constraint("sizeof(reference:Clients)-1 >= sizeof(reference:Subscriptions)-1");
//    constraint("sizeof(reference:Subscriptions) <= sizeof(reference:Messages)");
//    constraint("sizeof(reference:Subscriptions) >= sizeof(reference:Messages)-1");
//    finalConstraint("sizeof(reference:Subscriptions)-1 <= sizeof(reference:Messages)-1");

    DaikonState shouldNotBe = dm.getState("AISin,AISin:::ENTER");
    assertNull("Transitions should not be available as state objects", shouldNotBe);

    state("Cdisconnect");
    constraint("reference:clientName == myclient");
    constraint("reference:Clients elements == \"myclient\"");
    constraint("reference:Clients one of {[],[myclient]}");
    constraint("reference:Subscriptions == []");
    finalConstraint("sizeof(reference:Clients) one of {0.0,1.0}");

    state("Cdispose");
//    constraint("sizeof(reference:Subscriptions) == sizeof(reference:Messages)");
    constraint("reference:Clients == [vis, 3Dvis, mon]");
    constraint("reference:Clients elements one of {\"3Dvis\",\"mon\",\"vis\"}");
    finalConstraint("sizeof(reference:Clients) == 3.0");
//    constraint("reference:arg0 > sizeof(reference:Clients)");
//    constraint("reference:arg0 > sizeof(reference:Subscriptions)");
//    finalConstraint("sizeof(reference:Clients) < sizeof(reference:Subscriptions)-1");

    state("Cnew");
    constraint("reference:MessageID one of {1.0,3.0,5.0}");
    constraint("reference:Time one of {1.180656000201E12,1.231660195578E12}");
    constraint("reference:Repeat == 0.0");
    constraint("reference:UserId >= 1.0");
    constraint("reference:Clients one of {[myclient],[vis, 3Dvis, mon]}");
    finalConstraint("sizeof(reference:Clients) one of {1.0,3.0}");
//    constraint("reference:MessageID > reference:Repeat");
//    constraint("reference:MessageID <= reference:UserId");
//    constraint("reference:MessageID != sizeof(reference:Clients)-1");
//    constraint("reference:Repeat < reference:UserId");
//    constraint("reference:Repeat <= sizeof(reference:Clients)-1");
//    constraint("reference:Repeat <= sizeof(reference:Subscriptions)");
//    constraint("reference:Repeat <= sizeof(reference:Messages)");
//    constraint("reference:UserId >= sizeof(reference:Clients)");
//    constraint("reference:UserId != sizeof(reference:Subscriptions)");
//    constraint("reference:UserId != sizeof(reference:Subscriptions)-1");
//    constraint("reference:UserId != sizeof(reference:Messages)-1");
//    constraint("sizeof(reference:Subscriptions) >= sizeof(reference:Messages)-1");
//    finalConstraint("sizeof(reference:Subscriptions)-1 <= sizeof(reference:Messages)");

    transition("AISin", "AISin");
//    constraint("sizeof(reference:Subscriptions) == sizeof(reference:Messages)");
    constraint("reference:MessageID one of {1.0,3.0,5.0}");
    constraint("reference:Time == 1.180656000209E12");
    constraint("reference:Repeat == 0.0");
    constraint("reference:Clients == [vis, 3Dvis, mon]");
    constraint("reference:Clients elements one of {\"3Dvis\",\"mon\",\"vis\"}");
    finalConstraint("sizeof(reference:Clients) == 3.0");
//    constraint("reference:MessageID > reference:Repeat");
//    constraint("reference:MessageID < reference:UserId");
//    constraint("reference:MessageID != sizeof(reference:Clients)-1");
//    constraint("reference:MessageID <= sizeof(reference:Subscriptions)-1");
//    constraint("reference:Repeat < reference:UserId");
//    constraint("reference:Repeat < sizeof(reference:Subscriptions)-1");
//    constraint("reference:UserId > sizeof(reference:Clients)");
//    constraint("reference:UserId > sizeof(reference:Subscriptions)");
//    finalConstraint("sizeof(reference:Clients)-1 <= sizeof(reference:Subscriptions)");

/*      state = dm.getState("Crequest");
    sa = new ModelElementAssert(state);
    sa.assertName("Crequest");
    sa.assertNextContraint("reference:ShipId == reference:AISType");

    state = dm.getState("Csubscribe");
    sa = new ModelElementAssert(state);
    sa.assertName("Csubscribe");

    state = dm.getState("Unsubscribe_OK");
    sa = new ModelElementAssert(state);
    sa.assertName("Unsubscribe_OK");

    transition("Cconnect", "Cunsubscribe");
    assertName("Cconnect,Cunsubscribe");
    constraint("reference:Messages == sizeof(reference:Clients)");
    finalContraint("reference:ClientName always in reference:Clients");

    assertEquals("Number of states", 15, dm.numberOfStates());
    assertEquals("Number of transitions", 2, dm.numberOfTransitions());*/
  }
}
