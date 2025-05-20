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

package fi.vtt.noen.testgen.model.invariants;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map;

/**
 * @author Teemu Kanstrén
 */
public class GuardAnalyserTests {
  private GuardAnalyser guardAnalyser;

  @Before public void setup() {
    guardAnalyser = new GuardAnalyser();
  }

  @Test public void alwaysInArrayWithOneAttribute() {
    createEvent("Request", "id", "keijo");
    createEvent("Request", "id", "matti");
    createEvent("Request", "id", "keijo");
    Map<InvariantType, Collection<Invariant>> invariants = guardAnalyser.getValidInvariants("Request");
    Collection<Invariant> actual = invariants.get(InvariantType.ALWAYS_IN);
    assertNotNull(actual);
    assertEquals(1, actual.size());
    InvariantAlwaysIn invariant = (InvariantAlwaysIn) actual.iterator().next();
    assertEquals("ids", invariant.name());
    Collection<String> variables = invariant.getVariables();
    assertEquals(1, variables.size());
    assertTrue(variables.contains("id"));

    createEvent("Request", "id2", "keijo");
    assertEquals(0, variables.size());
    
  }

  @Test public void alwaysInArrayWithTwoAttributesIn() {
    createEvent("Request", "id", "keijo", "id2", "teppo");
    createEvent("Request", "id", "matti", "id2", "teppo");
    createEvent("Request", "id", "keijo", "id2", "teppo");
    Map<InvariantType, Collection<Invariant>> invariants = guardAnalyser.getValidInvariants("Request");
//    assertEquals(1, invariants.size());
    Collection<Invariant> actual = invariants.get(InvariantType.ALWAYS_IN);
    assertNotNull(actual);
    assertEquals(1, actual.size());
    InvariantAlwaysIn invariant = (InvariantAlwaysIn) actual.iterator().next();
    assertEquals("ids", invariant.name());
    Collection<String> variables = invariant.getVariables();
    assertEquals(2, variables.size());
    assertTrue(variables.contains("id"));
    assertTrue(variables.contains("id2"));

    createEvent("Request", "id2", "keijo");
    assertEquals(1, variables.size());
    assertTrue(variables.contains("id2"));

  }

  @Test public void SizeLargerThan1() {
    createEvent(4);
    createEvent(1);
    createEvent(5);
    createEvent(2);

    Map<InvariantType, Collection<Invariant>> invariants = guardAnalyser.getValidInvariants("keijo");
//    assertEquals(1, invariants.size());
    Collection<Invariant> actual = invariants.get(InvariantType.LARGER_THAN);
    InvariantLargerThan invariant = (InvariantLargerThan) actual.iterator().next();
    assertEquals(1, actual.size());
    assertEquals(1, invariant.getLimit());

    createEvent(0);
    assertEquals(0, invariant.getLimit());
    assertEquals(0, actual.size());
  }

  @Test public void valueRange0to5() {
    createStringEvent(1);

    Map<InvariantType, Collection<Invariant>> invariants = guardAnalyser.getValidInvariants("paavo");
//    assertEquals(1, invariants.size());
    Collection<Invariant> actual = invariants.get(InvariantType.VALUE_RANGE);
    InvariantValueRange invariant = (InvariantValueRange) actual.iterator().next();
    assertEquals(1, actual.size());
    assertEquals(1, invariant.getMin());
    assertEquals(1, invariant.getMax());

    createStringEvent(0);
    assertEquals(0, invariant.getMin());
    assertEquals(1, invariant.getMax());

    createStringEvent(5);
    assertEquals(0, invariant.getMin());
    assertEquals(5, invariant.getMax());
  }

  private void createStringEvent(int n) {
    Event event = new Event("paavo");
    event.add(new StringAttribute("huuh", ""+n));
    guardAnalyser.process(event);
  }

  private void createEvent(int n) {
    Event event = new Event("keijo");
    addListSizeOf(event, n);
    guardAnalyser.process(event);
  }

  //testit:

  private void createEvent(String name, String id, String value, String id2, String value2) {
    Event event = new Event(name);
    addGlobalAttributes(event);
    event.add(new StringAttribute(id, value));
    event.add(new StringAttribute(id2, value2));
    guardAnalyser.process(event);
  }

  private void createEvent(String name, String id, String value) {
    Event event = new Event(name);
    addGlobalAttributes(event);
    event.add(new StringAttribute(id, value));
    guardAnalyser.process(event);
  }

  private void addGlobalAttributes(Event event) {
    CollectionAttribute ids = new CollectionAttribute("ids");
    ids.addValue("matti");
    ids.addValue("teppo");
    ids.addValue("keijo");
    event.add(ids);
  }

  private void addListSizeOf(Event event, int n) {
    CollectionAttribute attributes = new CollectionAttribute("laalaa");
    for (int i = 0 ; i < n ; i++) {
      attributes.addValue("afdgdfg");
    }
    event.add(attributes);
  }
}
