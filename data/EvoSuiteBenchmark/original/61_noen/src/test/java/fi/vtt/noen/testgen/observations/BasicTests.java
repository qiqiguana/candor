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

package fi.vtt.noen.testgen.observations;

import fi.vtt.noen.testgen.observations.data.EventAttribute;
import org.junit.Test;

import static junit.framework.Assert.*;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;

import fi.vtt.noen.testgen.observations.data.ProgramRun;
import fi.vtt.noen.testgen.observations.data.Event;
import fi.vtt.noen.testgen.observations.data.ArrayAttribute;

/**
 * @author Teemu Kanstrén
 */
public class BasicTests {
  @Test
  public void eventOrder() {
    ProgramRun test = new ProgramRun("testing");
    Event event1 = new Event("event1");
    Event event2 = new Event("event2");
    Event event3 = new Event("event3");
    test.add(event1);
    test.add(event2);
    test.add(event3);
    Iterator<Event> iterator = test.iterator();
    assertEquals("Event 1 first", "event1", iterator.next().getName());
    assertEquals("Event 2 second", "event2", iterator.next().getName());
    assertEquals("Event 3 third", "event3", iterator.next().getName());
  }

  @Test
  public void arrayAttributeFormatting() {
    Collection data = new ArrayList();
    data.add("test1");
    data.add("test2");
    data.add("test3");
    String expected = "[\"test1\" \"test2\" \"test3\"]";
    String actual = ArrayAttribute.format(data);
    assertEquals("Formatted array", expected, actual);
  }

  @Test
  public void eventFromObject() {
    int x = 1;
    EventAttribute ea = new EventAttribute("x", x);
    assertEquals("int", ea.getType());
    assertEquals("1", ea.getValue());

    double y = 1.1;
    ea = new EventAttribute("y", y);
    assertEquals("double", ea.getType());
    assertEquals("1.1", ea.getValue());

    boolean b = true;
    ea = new EventAttribute("b", b);
    assertEquals("boolean", ea.getType());
    assertEquals("1", ea.getValue());

    String str = "hello";
    ea = new EventAttribute("str", str);
    assertEquals("java.lang.String", ea.getType());
    assertEquals("hello", ea.getValue());
  }
}
