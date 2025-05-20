/*
 * Copyright (C) 2010-2011 VTT Technical Research Centre of Finland.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package fi.vtt.noen.mfw.mbt.tests;

import fi.vtt.noen.mfw.mbt.log.StringWriter;
import fi.vtt.noen.mfw.mbt.log.TestCase;
import fi.vtt.noen.mfw.mbt.log.TestStep;
import fi.vtt.noen.mfw.mbt.log.TestSuite;
import org.junit.Test;

import static fi.vtt.noen.mfw.mbt.TestUtils.getResource;
import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class LoggerTests {
  @Test
  public void validSuite() {
    StringWriter writer = new StringWriter();
    TestSuite suite = new TestSuite("TestTestSuite", writer);
    suite.start();
    TestCase test1 = suite.startTest("test1");
    test1.startStep("step1-1");
    test1.endStep("step1-1");
    test1.startStep("step1-2");
    test1.startStep("step1-3");
    test1.endStep("step1-3");
    test1.endStep("step1-2");
    test1.end();

    TestCase test2 = suite.startTest("test2");
    test2.startStep("step2-1");
    test2.endStep("step2-1");
    test2.startStep("step2-2");
    test2.endStep("step2-2");
    test2.startStep("step2-3");
    test2.endStep("step2-3");
    test2.end();

    suite.end();

    String expected = getResource(getClass(), "ExpectedLog.xml");
    String actual = writer.toString();
    assertEquals("Generated XML log file", expected, actual);
  }

  @Test
  public void validSuiteWithActions() {
    StringWriter writer = new StringWriter();
    TestSuite suite = new TestSuite("TestTestSuite", writer);
    suite.start();
    TestCase test1 = suite.startTest("test1");
    TestStep step = test1.startStep("step1-1");
    step.action("Having fun").result("Hangover").end();
    test1.endStep("step1-1");
    test1.startStep("step1-2");
    step = test1.startStep("step1-3");
    step.action("Having more fun").result("FooBar").end();
    test1.endStep("step1-3");
    test1.endStep("step1-2");
    test1.end();

    TestCase test2 = suite.startTest("test2");
    step = test2.startStep("step2-1");
    step.action("s2 action").result("s2 result").end();
    step.action("s2 second action").result("s2 second result").end();
    test2.endStep("step2-1");
    test2.startStep("step2-2");
    test2.endStep("step2-2");
    test2.startStep("step2-3");
    test2.endStep("step2-3");
    test2.end();

    suite.end();

    String expected = getResource(getClass(), "ExpectedLog2.xml");
    String actual = writer.toString();
    assertEquals("Generated XML log file", expected, actual);
  }

  @Test
  public void actionInWrongStep() {
    StringWriter writer = new StringWriter();
    TestSuite suite = new TestSuite("TestTestSuite", writer);
    suite.start();
    TestCase test1 = suite.startTest("test1");
    TestStep step = test1.startStep("step1-1");
    step.action("Having fun").result("Hangover").end();
    test1.endStep("step1-1");
    try {
      step.action("Should fail");
      fail("Creating an action on an inactive (ended) step should fail.");
    } catch (IllegalStateException e) {
      //expected
    }
    step = test1.startStep("step1-2");
    test1.startStep("extra step");
    try {
      step.action("Should fail");
      fail("Creating an action on a previous (currently not active) step should fail.");
    } catch (IllegalStateException e) {
      //expected
    }
  }

  @Test
  public void invalidStepOrder() {
    TestSuite suite =  new TestSuite("TestTestSuite");
    suite.start();
    TestCase test1 = suite.startTest("test1");
    test1.startStep("step1-1");
    test1.startStep("step1-2");
    try {
      test1.endStep("step1-1");
      fail("Ending tests in wrong order should throw exception.");
    } catch (IllegalStateException e) {
      //expected
    }
  }

  @Test
  public void testNotEnded() {
    TestSuite suite =  new TestSuite("TestTestSuite");
    suite.start();
    TestCase test1 = suite.startTest("test1");
    test1.startStep("step1-1");
    test1.endStep("step1-1");
    test1.startStep("step1-2");
    test1.startStep("step1-3");
    test1.endStep("step1-3");
    test1.endStep("step1-2");

    try {
      TestCase test2 = suite.startTest("test2");
      fail("Starting a test case before closing previous one should throw exception.");
    } catch (IllegalStateException e) {
      //expected
    }
  }

  @Test
  public void suiteNotStarted() {
    TestSuite suite =  new TestSuite("TestTestSuite");
    try {
      TestCase test1 = suite.startTest("test1");
      fail("Starting a test case before starting a test suite should throw exception.");
    } catch (IllegalStateException e) {
      //expected
    }
  }

  @Test
  public void stepNotStarted() {
    TestSuite suite =  new TestSuite("TestTestSuite");
    suite.start();
    TestCase test1 = suite.startTest("test1");
    try {
      test1.endStep("step1-1");
      fail("Ending a step before starting it should throw exception.");
    } catch (IllegalStateException e) {
      //expected
    }
  }

  @Test
  public void stepNotEnded() {
    TestSuite suite =  new TestSuite("TestTestSuite");
    suite.start();
    TestCase test1 = suite.startTest("test1");
    test1.startStep("step1-1");
    try {
      test1.end();
    } catch (IllegalStateException e) {
      //expected
    }
  }

  @Test
  public void actionNotEnded() {

  }

  @Test
  public void actionNotStarted() {

  }

  @Test
  public void actionBeforeStep() {

  }
}
