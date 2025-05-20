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

package fi.vtt.noen.mfw.unittests;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Teemu Kanstren
 */
public class PortManager {
  private static int next = 10000;

  public static int next() {
    return next++;
  }

  public static URL testUrlFor(int port) {
    try {
      return new URL(testUrlStringFor(port));
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public static String testUrlStringFor(int port) {
    return "http://localhost:"+port+"/xmlrpc";
  }
}
