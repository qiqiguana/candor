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

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;

import fi.vtt.noen.testgen.TestInputInterface;
import fi.vtt.noen.testgen.TestInputInterface2;

/**
 * @author Teemu Kanstrén
 */
public class InterfaceParserTests {
  @Test
  public void methodNameParse() {
    Collection<String> actual = InterfaceParser.methodNames(TestInputInterface.class);
    Collection<String> expected = new ArrayList<String>();
    expected.add("testMethod1");
    expected.add("testMethod2");
    expected.add("testMethod3");
    //if expected or actual is not a set or a list, this can fail as in that case
    //the comparison is not necessarily about content but reference
    assertEquals("Parsed method names", expected, actual);
  }

  @Test
  public void methodNameParseForMultipleInterfaces() {
    Collection<Class> interfaces = new HashSet<Class>();
    interfaces.add(TestInputInterface.class);
    interfaces.add(TestInputInterface2.class);
    Collection<String> actual = new HashSet<String>(InterfaceParser.methodNames(interfaces));
    Collection<String> expected = new HashSet<String>();
    expected.add("testMethod1");
    expected.add("testMethod2");
    expected.add("testMethod3");
    expected.add("testMethod4");
    expected.add("testMethod5");
    //if expected or actual is not a set or a list, this can fail as in that case
    //the comparison is not necessarily about content but reference
    assertEquals("Parsed method names", expected, actual);
  }

}
