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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Teemu Kanstren
 */
public class FileWriter implements LogWriter {
  private final String fileName = "tests.xml";
  private final OutputStream out;
  private final byte[] lnb;

  public FileWriter() {
    try {
      out = new FileOutputStream(fileName);
      lnb = "\n".getBytes("UTF-8");
    } catch (Exception e) {
      throw new RuntimeException("Failed to open file for writing test log output", e);
    }
  }

  public void writeln(String line) {
    try {
      out.write(line.getBytes("UTF-8"));
      out.write(lnb);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write line to file.", e);
    }
  }
}
