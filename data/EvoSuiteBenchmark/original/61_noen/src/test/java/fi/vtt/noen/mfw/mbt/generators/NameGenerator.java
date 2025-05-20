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

package fi.vtt.noen.mfw.mbt.generators;

import java.util.Iterator;

import static fi.vtt.noen.mfw.mbt.tests.TestUtils.*;

/**
 * @author Teemu Kanstren
 */
public class NameGenerator implements Iterator<String> {
  private char separator = ' ';
  private int names = 0;
  private int nameLength = 0;

  public NameGenerator() {
  }

  public char getSeparator() {
    return separator;
  }

  public void setSeparator(char separator) {
    this.separator = separator;
  }

  public int getNames() {
    return names;
  }

  public void setNames(int names) {
    this.names = names;
  }

  public int getNameLength() {
    return nameLength;
  }

  public void setNameLength(int nameLength) {
    this.nameLength = nameLength;
  }

  private void checkConstraints() {
    if (separator != ' ' && separator != '_') {
      throw new IllegalArgumentException("Separator must be a valid letter or digit. Was '"+separator+"'.");
    }
    if (nameLength < 1) {
      throw new IllegalArgumentException("Name length must be 1 or more. Was "+nameLength+".");
    }
  }

  public String generateName() {
    checkConstraints();
    String name = "";
    name += upperCaseLetter();
    for (int i = 0 ; i < nameLength-1 ; i++) {
      name += lowerCaseLetter();
    }
    return name;
  }

  public boolean hasNext() {
    return false;
  }

  public String next() {
    return null;
  }

  public void remove() {
  }
}
