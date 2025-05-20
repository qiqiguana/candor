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

import fi.vtt.noen.mfw.mbt.generators.IntegerGenerator;
import fi.vtt.noen.mfw.mbt.generators.NameGenerator;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * TODO: Log all generated data, all tested oracles
 * @author Teemu Kanstren
 */
public class NameGeneratorTests {
  private NameGenerator generator = null;

  @Test
  public void testFailureHandling() {

  }

  @Test
  public void testGenerateName() {
    generator = new NameGenerator();
    IntegerGenerator intGenerator = new IntegerGenerator();
    intGenerator.addBoundary(0);
    for (int v : intGenerator) {
      generator.setNameLength(v);
      assertName(v);
    }
  }

  public void assertName(int expectedLength) {
    //tähän myös boundary
    if (expectedLength <= 0) {
      try {
        generator.generateName();
        fail("Should throw exception on value <= 0");
      } catch (Exception e) {
        //expected
        return;
      }
    }
    String name = generator.generateName();
    assertEquals("Name length", expectedLength, name.length());
    assertTrue("Name should be capitalized", Character.isUpperCase(name.charAt(0)));
    if (name.length() > 1) {
      assertTrue("Name ending should be lowercase", Character.isLowerCase(name.charAt(1)));
    }
  }
}
