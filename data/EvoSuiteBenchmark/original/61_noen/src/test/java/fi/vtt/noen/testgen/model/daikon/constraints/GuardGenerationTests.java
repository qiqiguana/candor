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

package fi.vtt.noen.testgen.model.daikon.constraints;

import org.junit.Test;

import static org.junit.Assert.*;

import fi.vtt.noen.testgen.model.daikon.constraints.*;

/**
 * @author Teemu Kanstr�n
 */
public class GuardGenerationTests {
  private static final String ln = "\n";

  @Test
  public void alwaysInArrayTest() {
    AlwaysInArrayConstraint constraint = new AlwaysInArrayConstraint("testobj?1", "testarray?g[]");
    String expected =
        "    if (testarray.contains(testobj)) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void arrayContentsTest() {
    ArrayContentsConstraint constraint = new ArrayContentsConstraint("testarray?1", "testobj1, testobj2, true]");
    String expected =
        "    Collection requiredValues = new ArrayList();"+ln+
        "    requiredValues.add(\"testobj1\");"+ln+
        "    requiredValues.add(\"testobj2\");"+ln+
        "    requiredValues.add(\"true\");"+ln+
        "    if (testarray.equals(requiredValues)) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void arrayElementsTest() {
    ArrayElementsConstraint constraint = new ArrayElementsConstraint("testarray?1", "testobj?0");
    String expected =
        "    Object expected = testobj;"+ln+
        "    for (Object o : testarray) {"+ln+
        "      if (expected.equals(o)) {"+ln+
        "        return false;"+ln+
        "      }"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void constantSizeTest() {
    ConstantSizeConstraint constraint = new ConstantSizeConstraint("size(testarray?g[])", "2");
    String expected =
        "    if (testarray.size() == 2.0) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void constantSizeArrayTest() {
    ConstantSizeConstraint constraint = new ConstantSizeConstraint("size(testarray?g[])", "1, 2, 3 }");
    String expected =
        "    Collection validSizes = new HashSet();"+ln+
        "    validSizes.add(1.0);"+ln+
        "    validSizes.add(2.0);"+ln+
        "    validSizes.add(3.0);"+ln+
        "    if (validSizes.contains(new Double(testarray.size()))) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void equalsTest() {
    EqualsConstraint constraint = new EqualsConstraint("testobj1?0", "testobj2?1");
    String expected =
        "    if (testobj1.equals(testobj2)) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void greaterTest() {
    GreaterConstraint constraint = new GreaterConstraint("testobj?0", "5");
    String expected =
        "    if (testobj > 5.0) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);

    constraint = new GreaterConstraint("Clients?g[]", "Subscriptions?g[] (lexically)");
    assertLexicalJava(constraint);
    assertEquals(expected, actual);

    constraint = new GreaterConstraint("MessageID?0", "Repeat?0");
    expected =
        "    if (MessageID > Repeat) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  private void assertLexicalJava(DaikonConstraint constraint) {
    if (constraint.isEnabled()) {
      fail("Lexical constraints should be disabled. "+constraint+" was not.");
    }
    try {
      constraint.toJava();
      fail("Lexical constraints should not allow generation of code but "+constraint+" does.");
    } catch (IllegalArgumentException e) {
      //expected
    }
  }

  @Test
  public void greaterOrEqualTest() {
    GreaterOrEqualConstraint constraint = new GreaterOrEqualConstraint("testobj?0", "5");
    String expected =
        "    if (testobj >= 5.0) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void lesserTest() {
    LesserConstraint constraint = new LesserConstraint("testobj?0", "5");
    String expected =
        "    if (testobj < 5.0) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);

    constraint = new LesserConstraint("Repeat?0", "UserId?0");
    expected =
        "    if (Repeat < UserId) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    actual = constraint.toJava();
    assertEquals(expected, actual);

    constraint = new LesserConstraint("Repeat?0", "size(Subscriptions?g[])-1");
    expected =
        "    if (Repeat < Subscriptions.size()-1) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void lesserOrEqualsTest() {
    LesserOrEqualConstraint constraint = new LesserOrEqualConstraint("testobj?0", "5");
    String expected =
        "    if (testobj <= 5.0) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);

    constraint = new LesserOrEqualConstraint("Repeat?0", "UserId?0");
    expected =
        "    if (Repeat <= UserId) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    actual = constraint.toJava();
    assertEquals(expected, actual);

    constraint = new LesserOrEqualConstraint("Repeat?0", "size(Subscriptions?g[])-1");
    expected =
        "    if (Repeat <= Subscriptions.size()-1) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    actual = constraint.toJava();
    assertEquals(expected, actual);

    constraint = new LesserOrEqualConstraint("Clients?g[]", "Subscriptions?g[] (lexically)");
    assertFalse(constraint.isEnabled());
  }

  @Test
  public void nonEqualTest() {
    NonEqualConstraint constraint = new NonEqualConstraint("Repeat?0", "UserId?0");
    String expected =
        "    if (Repeat != UserId) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);

    constraint = new NonEqualConstraint("Repeat?0", "size(Subscriptions?g[])-1");
    expected =
        "    if (Repeat != Subscriptions.size()-1) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    actual = constraint.toJava();
    assertEquals(expected, actual);

    constraint = new NonEqualConstraint("size(Subscriptions?g[])-1", "0");
    expected =
        "    if (Subscriptions.size()-1 != 0.0) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void oneOfTest() {
    OneOfConstraint constraint = new OneOfConstraint("testobj?0", "bob?1,bill?2,billybob?3)");
    String expected =
        "    HashSet validValues = new HashSet();"+ln+
        "    validValues.add(bob);"+ln+
        "    validValues.add(bill);"+ln+
        "    validValues.add(billybob);"+ln+
        "    if (validValues.contains(testobj)) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  @Test
  public void oneOfElementsTest() {
    OneOfConstraint constraint = new OneOfConstraint("testobj?0 elements", "1,2)");
    String expected =
        "    HashSet validValues = new HashSet();"+ln+
        "    validValues.add(1.0);"+ln+
        "    validValues.add(2.0);"+ln+
        "    for (Object o : testobj) {"+ln+
        "    if (validValues.contains(o)) {"+ln+
        "      return false;"+ln+
        "    }"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }

  /* This is commented out since it is not currently in the run, optimized out for case studies
  @Test
  public void oneOfSizeZeroTest() {
    ConstantSizeConstraint constraint = new ConstantSizeConstraint("size(testarray?g[])", "0, 1, 2 }");
    String expected = "";
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }
  */

  @Test
  public void sizeOfTest() {
    SizeOfConstraint constraint = new SizeOfConstraint("testobj?0", "testarray?1[])");
    String expected =
        "    if (testarray.size() == testobj) {"+ln+
        "      return false;"+ln+
        "    }"+ln;
    String actual = constraint.toJava();
    assertEquals(expected, actual);
  }
}
