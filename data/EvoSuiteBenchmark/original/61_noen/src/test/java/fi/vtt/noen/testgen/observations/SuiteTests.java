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

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.custommonkey.xmlunit.XMLAssert.*;
import static fi.vtt.noen.testgen.StringUtils.*;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import fi.vtt.noen.testgen.observations.formatter.PromFormatter;
import fi.vtt.noen.testgen.observations.formatter.BasicFormatter;
import fi.vtt.noen.testgen.observations.formatter.DaikonFormatter;
import fi.vtt.noen.testgen.observations.formatter.NoenFormatter;
import fi.vtt.noen.testgen.observations.data.ProgramRun;
import fi.vtt.noen.testgen.observations.data.ProgramRunSuite;
import fi.vtt.noen.testgen.observations.data.Event;
import fi.vtt.noen.testgen.TestUtils;

/**
 * @author Teemu Kanstrén
 */
public class SuiteTests extends TestUtils {
  private final String fileName = "TestData";

  @Before
  public void setUp() {
    PromFormatter.reset();
  }

  @After
  public void tearDown() {
    File file = new File(fileName);
    file.delete();
  }

  @Test
  public void basicSuite() throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PromFormatter prom = new PromFormatter(out);
    List<BasicFormatter> formatters = new ArrayList<BasicFormatter>();
    formatters.add(prom);
    ProgramRunSuite suite = new ProgramRunSuite(formatters);

    ProgramRun test1 = new ProgramRun("test1");
    ProgramRun test2 = new ProgramRun("test2");

    suite.addTest(test1);
    Event event1 = new Event("rabbit in");
    Event event2 = new Event("rabbit out");
    Event event3 = new Event("drink beer");
    event3.addAttribute("beer", "heineken");
    test1.add(event1);
    test1.add(event2);
    test1.add(event3);

    suite.addTest(test2);
    Event event4 = new Event("dwagon sails");
    Event event4exit = new Event("dwagon sails", true);
    Event event5 = new Event("dwagon eats");
    Event event6 = new Event("dwagon drinks beer");
    test2.add(event4);
    test2.add(event4exit);
    test2.add(event5);
    test2.add(event6);

    suite.close();

    String actual = new String(out.toByteArray());
    InputStream in = getClass().getResourceAsStream("expectedPromSuiteFormat.txt");
    String expected = stringForStream(in);
    assertXMLEqual("Test suite as formatted", expected, actual);
  }

  @Test
  public void fileOutputSuite() throws Exception {
    ProgramRunSuite suite = new ProgramRunSuite(fileName, false);
    ProgramRun test1 = new ProgramRun("test1");
    ProgramRun test2 = new ProgramRun("test2");

    suite.addTest(test1);
    Event event1_1 = new Event("rabbit in");
    Event event1_2 = new Event("rabbit out");
    Event event1_3 = new Event("drink beer");
    event1_3.addAttribute("beer", "heineken");
    Event event1_4 = new Event("pass out");
    test1.add(event1_1);
    test1.add(event1_2);
    test1.add(event1_3);
    test1.add(event1_4);

    suite.addTest(test2);
    Event event2_1 = new Event("dwagon sails");
    Event event2_1exit = new Event("dwagon sails", true);
    event2_1exit.addAttribute("result", "ok");
    Event event2_2 = new Event("dwagon eats");
    Collection sixpack = new ArrayList();
    sixpack.add("lapinkulta");
    sixpack.add("karhu");
    sixpack.add("olvi");
    sixpack.add("heineken");
    sixpack.add("hertog");
    sixpack.add("bavaria");
    event2_2.addAttribute("sixpack", sixpack);
    Event event2_3 = new Event("dwagon drinks beer");
    test2.add(event2_1);
    test2.add(event2_1exit);
    test2.add(event2_2);
    test2.add(event2_3);
    suite.close();

    String promFileExtension = new PromFormatter("").fileNameExtension();
    InputStream in = new FileInputStream(fileName + "." + promFileExtension);
    String actual = stringForStream(in);

    assertTrue("PROM output in file is correct filesize", actual.length() > 1000);

    String daikonFileExtension = new DaikonFormatter("", false).fileNameExtension();
    in = new FileInputStream(fileName + "." + daikonFileExtension);
    actual = stringForStream(in);
    InputStream expectedIn = getClass().getResourceAsStream("expectedDaikonSuiteFormat.txt");
    String expected = stringForStream(expectedIn);
    expected = unifyLineSeparators(expected);
    assertEquals("Daikon formatter output", expected, actual);

    String noenFileExtension = new NoenFormatter("").fileNameExtension();
    in = new FileInputStream(fileName + "." + noenFileExtension);
    actual = stringForStream(in);
    expectedIn = getClass().getResourceAsStream("expectedNoenSuiteFormat.xml");
    expected = stringForStream(expectedIn);
    //System.out.println("actual:"+actual);
    //expected = unifyLineSeparators(expected);
    assertXMLEqual("Noen formatter output", expected, actual);
  }

  @Test
  public void simpleFileOutputSuite() throws Exception {
    ProgramRunSuite suite = new ProgramRunSuite(fileName, true);
    ProgramRun test1 = new ProgramRun("test1");
    ProgramRun test2 = new ProgramRun("test2");

    suite.addTest(test1);
    Event event1_1 = new Event("rabbit in a hat", false);
    Event event1_2 = new Event("shake hat", false);
    Event event1_3 = new Event("drink beer", false);
    Event event1_4 = new Event("drink beer", true);
    Event event1_5 = new Event("shake hat", true);
    Event event1_6 = new Event("rabbit in a hat", true);
    event1_3.addAttribute("beer", "heineken");
    //daikon seems to require entry and exit have same paramters..
    //it also seems to assume strict hierarchy in ordering.. perhaps run with -nohierarchy?
    event1_4.addAttribute("beer", "heineken");
    test1.add(event1_1);
    test1.add(event1_2);
    test1.add(event1_3);
    test1.add(event1_4);
    test1.add(event1_5);
    test1.add(event1_6);

    suite.addTest(test2);
    Event event2_1 = new Event("dwagon sails", false);
    Event event2_1exit = new Event("dwagon sails", true);
    event2_1exit.addAttribute("result", "ok");
    Event event2_2 = new Event("dwagon eats", false);
    Collection sixpack = new ArrayList();
    sixpack.add("lapinkulta");
    sixpack.add("karhu");
    sixpack.add("olvi");
    sixpack.add("heineken");
    sixpack.add("hertog");
    sixpack.add("bavaria");
    event2_2.addAttribute("sixpack", sixpack);
    Event event2_3 = new Event("dwagon drinks beer", false);
    Event event2_4 = new Event("dwagon eats", true);
    event2_4.addAttribute("sixpack", sixpack);
    Event event2_5 = new Event("dwagon drinks beer", true);
    test2.add(event2_1);
    test2.add(event2_1exit);
    test2.add(event2_2);
    test2.add(event2_4);
    test2.add(event2_3);
    test2.add(event2_5);
    suite.close();

    String promFileExtension = new PromFormatter("").fileNameExtension();
    InputStream in = new FileInputStream(fileName + "." + promFileExtension);
    String actual = stringForStream(in);

    assertTrue("PROM output in file is correct filesize", actual.length() > 1000);

    String daikonFileExtension = new DaikonFormatter("", false).fileNameExtension();
    in = new FileInputStream(fileName + "." + daikonFileExtension);
    actual = stringForStream(in);
    InputStream expectedIn = getClass().getResourceAsStream("expectedDaikonSuiteFormat.txt");
    String expected = stringForStream(expectedIn);
    expected = unifyLineSeparators(expected);
    assertEquals("Daikon formatter output", expected, actual);
  }
}
