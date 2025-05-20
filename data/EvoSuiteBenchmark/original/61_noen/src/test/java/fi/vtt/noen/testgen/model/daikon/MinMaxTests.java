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
public class MinMaxTests {
  private static final String ln = "\n";

  @Test
  public void alwaysInArrayTest() {
    AlwaysInArrayConstraint constraint = new AlwaysInArrayConstraint("testobj?1", "testarray?g[]");
    assertNoMinMax(constraint);
  }

  private void assertNoMinMax(DaikonConstraint constraint) {
    assertNoMin(constraint);
    assertNoMax(constraint);
  }

  private void assertNoMin(DaikonConstraint constraint) {
    double min = constraint.min();
    assertEquals(Integer.MAX_VALUE, min, 0);
  }

  private void assertNoMax(DaikonConstraint constraint) {
    double max = constraint.max();
    assertEquals(Integer.MIN_VALUE, max, 0);
  }

  @Test
  public void arrayContentsTest() {
    ArrayContentsConstraint constraint = new ArrayContentsConstraint("testarray?1", "testobj1, testobj2, true]");
    assertNoMinMax(constraint);
  }

  @Test
  public void arrayElementsTest() {
    ArrayElementsConstraint constraint = new ArrayElementsConstraint("testarray?1", "testobj?0");
    assertNoMinMax(constraint);
  }

  @Test
  public void constantSizeTest() {
    ConstantSizeConstraint constraint = new ConstantSizeConstraint("size(testarray?g[])", "2");
    assertNoMinMax(constraint);
  }

  @Test
  public void constantSizeArrayTest() {
    ConstantSizeConstraint constraint = new ConstantSizeConstraint("size(testarray?g[])", "1, 2, 3 }");
    assertNoMinMax(constraint);
  }

  @Test
  public void equalsTest() {
    EqualsConstraint constraint = new EqualsConstraint("testobj1?0", "testobj2?1");
    assertNoMinMax(constraint);

    constraint = new EqualsConstraint("testobj1?0", "5");
    assertEquals(5, constraint.min(), 0);
    assertEquals(5, constraint.max(), 0);
  }

  @Test
  public void greaterTest() {
    GreaterConstraint constraint = new GreaterConstraint("testobj?0", "testbob?1");
    assertNoMinMax(constraint);

    constraint = new GreaterConstraint("testobj?0", "11.5");
    assertEquals(11.5, constraint.min(), 0);
    assertNoMax(constraint);

    constraint = new GreaterConstraint("testobj?0", "-4511.5");
    assertEquals(-4511.5, constraint.min(), 0);
    assertNoMax(constraint);
  }

  @Test
  public void greaterOrEqualTest() {
    GreaterOrEqualConstraint constraint = new GreaterOrEqualConstraint("testobj?0", "5");
    assertEquals(5, constraint.min(), 0);
    assertNoMax(constraint);
  }

  @Test
  public void lesserTest() {
    LesserConstraint constraint = new LesserConstraint("testobj?0", "testbob?1");
    assertNoMinMax(constraint);

    constraint = new LesserConstraint("testobj?0", "100.1");
    assertNoMin(constraint);
    assertEquals(100.1, constraint.max(), 0);
  }

  @Test
  public void lesserOrEqualsTest() {
    LesserOrEqualConstraint constraint = new LesserOrEqualConstraint("testobj?0", "testbob?1");
    assertNoMinMax(constraint);

    //TODO: these OrEqual constraints do not actually consider if there is "equal" part or no
    constraint = new LesserOrEqualConstraint("testobj?0", "55");
    assertNoMin(constraint);
    assertEquals(55, constraint.max(), 0);
  }

  @Test
  public void nonEqualTest() {
    NonEqualConstraint constraint = new NonEqualConstraint("testobj?0", "testbob?1");
    assertNoMinMax(constraint);
  }

  @Test
  public void oneOfTest() {
    OneOfConstraint constraint = new OneOfConstraint("testobj?0", "bob?1,bill?2,billybob?3)");
    assertNoMinMax(constraint);
    
    constraint = new OneOfConstraint("testobj?0", "1,2,billybob?3)");
    assertEquals(2, constraint.max(), 0);
    assertEquals(1, constraint.min(), 0);

    constraint = new OneOfConstraint("testobj?0", "1,255.3,-10.2)");
    assertEquals(255.3, constraint.max(), 0);
    assertEquals(-10.2, constraint.min(), 0);
  }

  @Test
  public void oneOfElementsTest() {
    OneOfConstraint constraint = new OneOfConstraint("testobj?0 elements", "1,2)");
    //this is not exactly supposed to be so, as with elements its a collection but then again
    //in this case it should not be generating number parameters anyway so lets leave it..
    assertEquals(2, constraint.max(), 0);
    assertEquals(1, constraint.min(), 0);
  }

  @Test
  public void sizeOfTest() {
    SizeOfConstraint constraint = new SizeOfConstraint("testobj?0", "testarray?1[])");
    assertNoMinMax(constraint);
  }
}
