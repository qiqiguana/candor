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

package fi.vtt.noen.mfw.mbt.log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teemu Kanstren
 */
public class TestSuite {
  private final String suiteName;
  private final LogWriter writer;
  private final List<String> tests = new ArrayList<String>();
  private boolean started = false;

  public TestSuite(String suiteName) {
    this.suiteName = suiteName;
    writer = new FileWriter();
  }

  public TestSuite(String suiteName, LogWriter writer) {
    this.suiteName = suiteName;
    this.writer = writer;
  }

  public void start() {
//    PrintStream old = System.out;
//    System.setOut(new PrintStream(new DualLogStream(old)));
    writer.writeln("<testsuite name='"+suiteName+"'>");
    started = true;
  }

  public void end() {
    writer.writeln("</testsuite>");
  }

  public TestCase startTest(String testName) {
    //TODO: contracts would be nice..
    if (!started) {
      throw new IllegalStateException("Cannot start tests from a suite before the suite is started.");
    }
    if (tests.size() > 0) {
      throw new IllegalStateException("Only one test can be active at a time. Already active:"+tests);
    }
    TestCase test = new TestCase(this, testName, writer);
    test.start();
    tests.add(testName);
    return test;
  }

  public void endTest(String testName) {
    String latest = tests.get(tests.size()-1);
    if (!latest.equals(testName)) {
      throw new IllegalStateException("Only the last active test can be closed. Currently last one is "+latest+" attempted:"+testName);
    }
    tests.remove(tests.size()-1);
  }

}
