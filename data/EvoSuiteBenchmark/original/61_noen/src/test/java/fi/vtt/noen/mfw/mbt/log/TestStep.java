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

import java.util.List;

/**
 * @author Teemu Kanstren
 */
public class TestStep extends LogComponent {
  private final String name;
  private LogWriter writer;
  private TestCase parent;

  public TestStep(TestCase parent, int depth, String name, LogWriter writer) {
    this.parent = parent;
    this.depth = depth;
    this.start = "    ";
    this.name = name;
    this.writer = writer;
  }

  public String getName() {
    return name;
  }

  public TestAction action(String name) {
    List<TestStep> steps = parent.getSteps();
    int size = steps.size();
    if (size == 0) {
      throw new IllegalStateException("You can not start an action on an inactive step (ended the step before?).");
    }
    if (!steps.get(size-1).equals(this)) {
      throw new IllegalStateException("Your can only start an action on the currently active test step.");
    }
    TestAction action = new TestAction(name, depth+1, writer);
    action.start();
    return action;
  }

  public void start() {
    writer.writeln(indent()+"<step name='"+name+"'>");
  }

  public void end() {
    writer.writeln(indent()+"</step>");
  }

  @Override
  public String toString() {
    return "TestStep:"+name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TestStep testStep = (TestStep) o;

    if (name != null ? !name.equals(testStep.name) : testStep.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}
