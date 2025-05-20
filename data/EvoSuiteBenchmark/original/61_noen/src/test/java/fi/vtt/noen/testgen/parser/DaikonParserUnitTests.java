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
import java.util.Iterator;
import java.util.NoSuchElementException;

import fi.vtt.noen.testgen.model.daikon.constraints.*;

/**
 * @author Teemu Kanstrén
 */
public class DaikonParserUnitTests {
  @Test
  public void validMiddleNextItem() {
    String text = "[14:50:03]: Finished reading atlas-log.dtrace\n" +
        "===========================================================================\n" +
        "first\n" +
        "===========================================================================\n" +
        "second1\n" +
        "second2\n" +
        "===========================================================================\n";
    DaikonParser parser = new DaikonParser(text);
    String item = parser.nextBlock();
    assertEquals("first\n", item);
    item = parser.nextBlock();
    assertEquals("second1\nsecond2\n", item);
  }

  @Test
  public void validMiddleNextItemCRLF() {
    String text = "[14:50:03]: Finished reading atlas-log.dtrace\r\n" +
        "===========================================================================\r\n" +
        "first\r\n" +
        "===========================================================================\r\n" +
        "second1\r\n" +
        "second2\r\n" +
        "===========================================================================\r\n";
    DaikonParser parser = new DaikonParser(text);
    String item = parser.nextBlock();
    assertEquals("first\r\n", item);
    item = parser.nextBlock();
    assertEquals("second1\r\nsecond2\r\n", item);
  }

  @Test
  public void validEndNextItem() {
    String text = "[14:50:03]: Finished reading atlas-log.dtrace\n" +
        "===========================================================================\n" +
        "first\n" +
        "===========================================================================\n" +
        "second1\n" +
        "second2\n" +
        "===========================================================================\n"+
        "third1\n" +
        "third3\n" +
        "Exiting Daikon.\n";
    DaikonParser parser = new DaikonParser(text);
    String item = parser.nextBlock();
    assertEquals("first\n", item);
    item = parser.nextBlock();
    assertEquals("second1\nsecond2\n", item);
    item = parser.nextBlock();
    assertEquals("third1\nthird3\n", item);
  }

  @Test
  public void parseItemFromBlock() {
    String block = "first\nsecond\nthird\n";
    Collection items = DaikonParser.items(block);
    Iterator i = items.iterator();
    assertEquals("first", i.next());
    assertEquals("second", i.next());
    assertEquals("third", i.next());
    try {
      assertEquals("fourth", i.next());
      assertTrue("Should have trown exception here", false);
    } catch (NoSuchElementException e) {

    }
  }

  @Test
  public void equalItemParse() {
    String text = "test_state?1 == test_value?0\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("conversion from string to constraint", EqualsConstraint.class, constraint.getClass());
    assertEquals("reference:test_state == reference:test_value", constraint.toString());
  }

  @Test
  public void nonEqualItemParse() {
    String text = "size(Subscriptions?g[])-1 != 0";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("conversion from string to constraint", NonEqualConstraint.class, constraint.getClass());
    assertEquals("sizeof(reference:Subscriptions)-1 != 0.0", constraint.toString());
  }

  @Test
  public void oneOfItemParse() {
    String text = "test_state?0 one of {1,2,3,4}\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("conversion from string to constraint", OneOfConstraint.class, constraint.getClass());
    assertEquals("reference:test_state one of {1.0,2.0,3.0,4.0}", constraint.toString());

    text = "test_state?1 one of { 0, 1 }\n";
    constraint = DaikonParser.constraint(text);
    assertEquals("reference:test_state one of {0.0,1.0}", constraint.toString());
  }

  @Test
  public void oneOfArrayParse() {
    String text = "Clients?g[] one of { [], [myclient] }\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("conversion from string to constraint", OneOfConstraint.class, constraint.getClass());
    assertEquals("reference:Clients one of {[],[myclient]}", constraint.toString());
  }

  @Test
  public void oneOfElementsParse() {
    String text = "Messages?g[] elements one of { \"29959477\", \"30411188\" }\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("conversion from string to constraint", OneOfConstraint.class, constraint.getClass());
    assertEquals("reference:Messages elements one of {\"29959477\",\"30411188\"}", constraint.toString());
  }

  @Test
  public void arrayContentsParse() {
    String text = "Clients?g[] == []\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", ArrayContentsConstraint.class, constraint.getClass());
    assertEquals("reference:Clients == []", constraint.toString());

    text = "Clients?g[] == [item1]\n";
    constraint = DaikonParser.constraint(text);
    assertEquals("reference:Clients == [item1]", constraint.toString());

    text = "Clients?g[] == [item1, item2, item3]\n";
    constraint = DaikonParser.constraint(text);
    assertEquals("reference:Clients == [item1, item2, item3]", constraint.toString());

    ArrayContentsConstraint ai = (ArrayContentsConstraint) constraint;
    Collection<String> values = ai.getValues();
    assertEquals("number of values in array", 3, values.size());
    int i = 1;
    for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
      String value = iterator.next();
      assertEquals("Value in array", "item"+i, value);
      i++;
    }
  }

  @Test
  public void sizeOfParse() {
    String text = "Messages?0 == size(Clients?g[])\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", SizeOfConstraint.class, constraint.getClass());
    assertEquals("reference:Messages == sizeof(reference:Clients)", constraint.toString());

    text = "Messages?0 == size(Clients?g[])-1\n";
    constraint = DaikonParser.constraint(text);
    assertEquals("reference:Messages == sizeof(reference:Clients)-1", constraint.toString());

    text = "Messages?0 == size(Clients?g[])+1\n";
    constraint = DaikonParser.constraint(text);
    assertEquals("reference:Messages == sizeof(reference:Clients)+1", constraint.toString());
  }

  @Test
  public void arrayStaticParse() {
    String text = "Clients?g[] elements == \"myclient\"\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", ArrayElementsConstraint.class, constraint.getClass());
    assertEquals("reference:Clients elements == \"myclient\"", constraint.toString());
  }

  @Test
  public void constantSizeParse() {
    String text = "size(Clients?g[]) == 1\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", ConstantSizeConstraint.class, constraint.getClass());
    assertEquals("sizeof(reference:Clients) == 1.0", constraint.toString());
  }

  @Test
  public void constantSizeReferenceParse() {
    String text = "size(Clients?g[])-1 == size(Subscriptions?g[])\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", ConstantSizeConstraint.class, constraint.getClass());
    assertEquals("sizeof(reference:Clients)-1 == sizeof(reference:Subscriptions)", constraint.toString());
  }

  @Test
  public void constantSizeOneOfParse() {
    String text = "size(Clients?g[]) one of { 0, 1 }\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", ConstantSizeConstraint.class, constraint.getClass());
    assertEquals("sizeof(reference:Clients) one of {0.0,1.0}", constraint.toString());
  }

  @Test
  public void greaterOrEqualsSizeParse() {
    String text = "size(Clients?g[]) >= size(Subscriptions?g[])\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", GreaterOrEqualConstraint.class, constraint.getClass());
    assertEquals("sizeof(reference:Clients) >= sizeof(reference:Subscriptions)", constraint.toString());
  }

  @Test
  public void greaterOrEqualsSizeWithAdditionParse() {
    String text = "size(Clients?g[]) >= size(Messages?g[])-1\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", GreaterOrEqualConstraint.class, constraint.getClass());
    assertEquals("sizeof(reference:Clients) >= sizeof(reference:Messages)-1", constraint.toString());
  }

  @Test
  public void lesserOrEqualsSizeWithAdditionParse() {
    String text = "size(Clients?g[])-1 <= size(Subscriptions?g[])\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", LesserOrEqualConstraint.class, constraint.getClass());
    assertEquals("sizeof(reference:Clients)-1 <= sizeof(reference:Subscriptions)", constraint.toString());
  }

  @Test
  public void alwaysInArrayParse() {
    String text = "ClientName?0 in Clients?g[]\n";
    Object constraint = DaikonParser.constraint(text);
    assertEquals("Conversion from string to constraint", AlwaysInArrayConstraint.class, constraint.getClass());
    assertEquals("reference:ClientName always in reference:Clients", constraint.toString());
  }

  @Test
  public void eventNameParse() {
    String text = "CONNECT:::ENTER";
    String name = DaikonParser.parseEventName(text);
    assertEquals("CONNECT", name);
  }

  @Test
  public void parseStringValueObject() {
    String value = "\"test-value\"";
    Object obj = DaikonParser.parseValueObject(value);
    assertEquals("test-value", obj);
  }

  @Test
  public void parseNumericValueObject() {
    String value = "11";
    Object obj = DaikonParser.parseValueObject(value);
    assertEquals(11.0d, obj);

    value = "11.01";
    obj = DaikonParser.parseValueObject(value);
    assertEquals(11.01d, obj);

    value = "1.22424664139E12";
    obj = DaikonParser.parseValueObject(value);
    assertEquals(1.22424664139E12, obj);
    Double d = (Double)obj;
    //explicit conversion to long needed to get number like this.
    assertEquals(1224246641390L, d.longValue());
  }

  @Test
  public void parseReferenceValueObject() {
    String value = "AnotherVariable?1";
    Object obj = DaikonParser.parseValueObject(value);
    ReferenceValue ref = (ReferenceValue) obj;
    assertEquals("AnotherVariable", ref.getReferredVariable());
    assertEquals(1, ref.getIndex());
  }

  @Test
  public void parseGlobalReferenceValueObject() {
    String value = "GlobalVariable?g";
    Object obj = DaikonParser.parseValueObject(value);
    ReferenceValue ref = (ReferenceValue) obj;
    assertEquals("GlobalVariable", ref.getReferredVariable());
    assertEquals(-1, ref.getIndex());
  }

  @Test
  public void parseReturnValue() {
    String value = "ReturnStatus?r";
    Object obj = DaikonParser.parseValueObject(value);
    ReferenceValue ref = (ReferenceValue) obj;
    assertEquals("ReturnStatus", ref.getReferredVariable());
    assertEquals(-1, ref.getIndex());
  }
}
