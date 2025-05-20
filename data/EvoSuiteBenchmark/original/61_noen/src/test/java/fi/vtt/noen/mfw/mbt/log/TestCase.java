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
public class TestCase {
  private final TestSuite suite;
  private final String testName;
  private final LogWriter writer;
  private List<TestStep> steps = new ArrayList<TestStep>();

  public TestCase(TestSuite suite, String testName, LogWriter writer) {
    this.suite = suite;
    this.testName = testName;
    this.writer = writer;
  }

  /**
   * Call this when a test case is started.
   */
  protected void start() {
    writer.writeln("  <test name='"+testName+"'>");
  }

  /**
   * Call this when a test case is finished.
   */
  public void end() {
    if (steps.size() > 0) {
      throw new IllegalStateException("Cannot end a test while steps are active. Active steps:"+steps);
    }
    writer.writeln("  </test>");
    suite.endTest(testName);
  }

  //TODO: These loggings could be automated based on the model annotations
  public TestStep startStep(String name) {
    TestStep step = new TestStep(this, steps.size(), name, writer);
    steps.add(step);
    step.start();
    return step;
  }

  public void endStep(String name) {
    //todo: these checks should be changed to contracts if they were somehow supported..
    if (steps.size() == 0) {
      throw new IllegalStateException("No steps are started, still tried to close:"+name);
    }
    TestStep step = steps.get(steps.size()-1);
    if (!step.getName().equals(name)) {
      throw new IllegalStateException("Only the last active step can be closed. Currently last one is:"+step.getName()+" attempted:"+name);
    }
    step.end();
    steps.remove(step);
  }

  public List<TestStep> getSteps() {
    return steps;
  }
}
