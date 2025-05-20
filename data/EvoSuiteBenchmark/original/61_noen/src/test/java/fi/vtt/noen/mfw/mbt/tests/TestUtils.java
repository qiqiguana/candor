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

import org.junit.Test;

import java.util.Collection;
import java.util.Random;
import java.util.TreeSet;

import static junit.framework.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class TestUtils {
  private static Random random = new Random();

  public static char lowerCaseLetter() {
    //a-z = 97-122 in the ASCII table
    int c = random.nextInt(123-97)+97;
    return (char)c;
  }

  public static char upperCaseLetter() {
    //A-Z = 65-90 in the ASCII table
    int c = random.nextInt(91-65)+65;
    return (char)c;
  }

  @Test
  public void checkLowerCaseGenerator() {
    Collection<Character> generated = new TreeSet<Character>();
    String expected = "abcdefghijklmnopqrstuvwxyz";
    for (int i = 0 ; i < 1000 ; i++) {
      generated.add(lowerCaseLetter());
    }
    assertEquals("Lower case generator should produce all characters from a-z. Was: "+generated, expected.length(), generated.size());
  }

  @Test
  public void checkUpperCaseGenerator() {
    Collection<Character> generated = new TreeSet<Character>();
    String expected = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for (int i = 0 ; i < 1000 ; i++) {
      generated.add(upperCaseLetter());
    }
    assertEquals("Upper case generator should produce all characters from A-Z. Was: "+generated, expected.length(), generated.size());
  }
}
