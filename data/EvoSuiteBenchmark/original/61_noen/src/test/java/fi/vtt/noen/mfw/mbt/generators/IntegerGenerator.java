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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Teemu Kanstren
 */
public class IntegerGenerator implements Iterable<Integer> {
  private Collection<Integer> boundaries = new ArrayList<Integer>();

  public Iterator<Integer> iterator() {
    Collection<Integer> values = new ArrayList<Integer>();
    for (int boundary : boundaries) {
      values.add(boundary);
      values.add(boundary-1);
      values.add(boundary+1);
      values.add(boundary-5);
      values.add(boundary+5);
    }
    return values.iterator();
  }

  public void addBoundary(int boundary) {
    boundaries.add(boundary);
  }
}
