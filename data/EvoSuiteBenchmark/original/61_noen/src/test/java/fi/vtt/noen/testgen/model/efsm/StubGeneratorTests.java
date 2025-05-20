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

package fi.vtt.noen.testgen.model.efsm;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.io.IOException;

import static fi.vtt.noen.testgen.TestUtils.*;

/**
 * @author Teemu Kanstrén
 */
public class StubGeneratorTests {
  private String ln = "\n";

  @Test
  public void methodGeneration() throws Exception {
    Class clazz = TestInterface.class;
    Method method = clazz.getMethod("TestMethod1");
    String expected =
        "  public void TestMethod1() {"+ln+
        "    msgReceived(\"TestMethod1\");"+ln+
        "  }"+ln;
    StubGenerator generator = new StubGenerator();
    String actual = generator.stubFor(method);
    assertEquals("Generated method stub", expected, actual);
  }

  @Test
  public void methodGenerationWithParametersAndReturnValue() throws Exception {
    Class clazz = TestInterface.class;
    Method method = clazz.getMethod("TestMethod2", Collection.class, Map.class, int.class);
    String expected =
        "  public java.lang.String TestMethod2(java.util.Collection p1, java.util.Map p2, int p3) {"+ln+
        "    msgReceived(\"TestMethod2\");"+ln+
        "    return null;"+ln+
        "  }"+ln;
    StubGenerator generator = new StubGenerator();
    String actual = generator.stubFor(method);
    assertEquals("Generated method stub", expected, actual);
  }

  @Test
  public void methodGenerationWithThrows() throws Exception {
    Class clazz = TestInterface.class;
    Method method = clazz.getMethod("TestMethod3", double.class);
    String expected =
        "  public int[] TestMethod3(double p1) throws java.io.IOException {"+ln+
        "    msgReceived(\"TestMethod3\");"+ln+
        "    return null;"+ln+
        "  }"+ln;
    StubGenerator generator = new StubGenerator();
    String actual = generator.stubFor(method);
    assertEquals("Generated method stub", expected, actual);
  }

  @Test
  public void variableTest() throws Exception {
    StubGenerator generator = new StubGenerator();

    Class clazz = TestInterface.class;
    Method method = clazz.getMethod("TestMethod1");
    generator.stubFor(method);
    method = clazz.getMethod("TestMethod2", Collection.class, Map.class, int.class);
    generator.stubFor(method);
    method = clazz.getMethod("TestMethod3", double.class);
    generator.stubFor(method);

    String actual = generator.variables();
    String expected =
        "  private Map<String, Integer> messages = new HashMap<String, Integer>();"+ln+ln+
        "  public void reset() {"+ln+
        "    messages.clear();"+ln+
        "  }"+ln+ln+
        "  private void msgReceived(String msg) {"+ln+
        "    Integer count = messages.get(msg);"+ln+
        "    if (count == null) {"+ln+
        "      count = 0;"+ln+
        "    }"+ln+
        "    count++;"+ln+
        "    messages.put(msg, count);"+ln+
        "  }"+ln+ln+
        "  public int countFor(String msg) {"+ln+
        "    return messages.get(msg);"+ln+
        "  }"+ln;
    assertEquals("Generated variables", expected, actual);
  }

  @Test
  public void fullTest() throws Exception {
    String expected = getFileContents(this, "expectedStub.txt");
    String actual = new StubGenerator().stubFor(TestInterface.class, "fi.vtt.package", "TestInterfaceImpl");
    assertEquals("Generated stub code", expected, actual);
  }

  private static interface TestInterface {
    public void TestMethod1();
    public String TestMethod2(Collection c, Map m, int i);
    public int[] TestMethod3(double d) throws IOException;
  }
}
