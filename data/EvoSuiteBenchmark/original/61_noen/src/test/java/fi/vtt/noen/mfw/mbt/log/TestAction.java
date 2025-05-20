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

/**
 * @author Teemu Kanstren
 */
public class TestAction extends LogComponent {
  private final String name;
  private final LogWriter writer;

  public TestAction(String name, int depth, LogWriter writer) {
    this.name = name;
    this.depth = depth;
    this.start = "      ";
    this.writer = writer;
  }

  public TestAction start() {
    writer.writeln(indent()+"<action name='"+name+"'>");
    return this;
  }

  public TestAction result(Object result) {
    writer.writeln(indent()+"  <result value='"+result+"'/>");
    return this;
  }

  public TestAction end() {
    writer.writeln(indent()+"</action>");
    return this;
  }
}
