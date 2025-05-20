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

package fi.vtt.noen.testgen.model.daikon;

import org.junit.Test;
import static org.junit.Assert.*;
import fi.vtt.noen.testgen.model.daikon.constraints.*;

/**
 * @author Teemu Kanstrén
 */
public class AssertTests {
  private static final String ln = "\n";

  @Test
  public void alwaysInArrayTest() {
    AlwaysInArrayConstraint constraint = new AlwaysInArrayConstraint("testobj?1", "testarray?g[]");
    String assertion = constraint.asAssert("rv1");
    assertEquals("assertTrue(testarray.contains(rv1));"+ln, assertion);
/*    try {
      String assertion = constraint.asAssert();
      fail("AlwaysInArrayConstraint constraints is not intended to support assert generation.");
    } catch (UnsupportedOperationException e) {
      //this is expected
    }*/
  }

  @Test
  public void arrayContentsTest() {
    ArrayContentsConstraint constraint = new ArrayContentsConstraint("testarray?r", "testobj1, testobj2, true]");
    String assertion = constraint.asAssert("rv1");
    String expected =
        "Collection requiredValues = new ArrayList();"+ln+
        "requiredValues.add(\"testobj1\");"+ln+
        "requiredValues.add(\"testobj2\");"+ln+
        "requiredValues.add(\"true\");"+ln+
        "assertEquals(rv1, requiredValues);"+ln;
    assertEquals(expected, assertion);
  }

  @Test
  public void arrayElementsTest() {
    ArrayElementsConstraint constraint = new ArrayElementsConstraint("testarray?r", "\"testobj\"");
    String assertion = constraint.asAssert("rv1");
    String expected =
        "for (Object o : rv1) {"+ln+
        "  assertEquals(\"testobj\", o);"+ln+
        "}"+ln;
    assertEquals(expected, assertion);
  }

  @Test
  public void constantSizeTest() {
    ConstantSizeConstraint constraint = new ConstantSizeConstraint("size(testarray?r[])", "2");
    String assertion = constraint.asAssert("rv1");
    assertEquals("assertEquals(2.0, rv1.size());"+ln, assertion);
  }

  @Test
  public void constantSizeArrayTest() {
    ConstantSizeConstraint constraint = new ConstantSizeConstraint("size(testarray?r[])", "1, 2, 3 }");
    String expected =
        "Collection validSizes = new HashSet();"+ln+
        "validSizes.add(1.0);"+ln+
        "validSizes.add(2.0);"+ln+
        "validSizes.add(3.0);"+ln+
        "assertTrue(validSizes.contains(new Double(rv1.size())));"+ln;
    String assertion = constraint.asAssert("rv1");
    assertEquals(expected, assertion);
  }

  @Test
  public void equalsTest() {
    EqualsConstraint constraint = new EqualsConstraint("testobj1?r", "1");
    String assertion = constraint.asAssert("rv1");
    assertEquals("assertEquals(1.0, rv1);"+ln, assertion);
  }

  @Test
  public void greaterOrEqualTest() {
    GreaterOrEqualConstraint constraint = new GreaterOrEqualConstraint("testobj?0", "5");
    String assertion = constraint.asAssert("rv1");
    assertEquals("assertTrue(rv1 >= 5.0);"+ln, assertion);
  }

  @Test
  public void nonEqualTest() {
    NonEqualConstraint constraint = new NonEqualConstraint("size(Subscriptions?g[])-1", "0");
    String assertion = constraint.asAssert("rv1");
    assertEquals("assertTrue(rv1.size()-1 != 0.0);"+ln, assertion);
  }

  @Test
  public void oneOfTest() {
    OneOfConstraint constraint = new OneOfConstraint("testobj?0", "bob?1,bill?2,billybob?3)");
    String assertion = constraint.asAssert("rv1");
    String expected =
        "HashSet validValues = new HashSet();"+ln+
        "validValues.add(bob);"+ln+
        "validValues.add(bill);"+ln+
        "validValues.add(billybob);"+ln+
        "assertTrue(validValues.contains(rv1));"+ln;
    assertEquals(expected, assertion);
  }

  @Test
  public void oneOfElementsTest() {
    OneOfConstraint constraint = new OneOfConstraint("testobj?0 elements", "1,2)");
    String assertion = constraint.asAssert("rv1");
    String expected =
        "HashSet validValues = new HashSet();"+ln+
        "validValues.add(1.0);"+ln+
        "validValues.add(2.0);"+ln+
        "for (Object o : rv1) {"+ln+
        "  assertTrue(validValues.contains(o));"+ln+
        "}"+ln;
    assertEquals(expected, assertion);
  }

  @Test
  public void sizeOfTest() {
    //test nothing for now since this constraint is not supported assert
//    SizeOfConstraint constraint = new SizeOfConstraint("2", "testarray?1[])");
//    String assertion = constraint.asAssert("rv1");
//    assertEquals("    assertEquals(testarray.size(), rv1);"+ln, assertion);
  }
}
