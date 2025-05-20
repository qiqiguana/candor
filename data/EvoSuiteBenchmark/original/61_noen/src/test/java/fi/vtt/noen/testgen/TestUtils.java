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

package fi.vtt.noen.testgen;

import static org.junit.Assert.*;
import static fi.vtt.noen.testgen.StringUtils.*;
import org.junit.Test;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.ArrayList;

/**
 * @author Teemu Kanstrén
 */
public class TestUtils {
  public static String getFileContents(Object o, String fileName) throws IOException {
    InputStream in = o.getClass().getResourceAsStream(fileName);
    String text = stringForStream(in);
    text = unifyLineSeparators(text);
    in.close();
    return text;
  }

  public static String unifyLineSeparators(String text) {
    // Windows->Unix
    text = text.replaceAll("\r\n", "\n");
    // Mac->Unix
    text = text.replaceAll("\r", "\n");
    return text;
  }

  public static Collection<Class> inputs() {
    Collection<Class> inputs = new ArrayList<Class>();
    inputs.add(TestInputInterface.class);
    inputs.add(TestInputInterface2.class);
    return inputs;
  }

  public static Collection<Class> outputs() {
    Collection<Class> outputs = new ArrayList<Class>();
    outputs.add(TestOutputInterface.class);
    return outputs;
  }

  @Test
  public void numberGenerationValidation() {
    for (int i = 0 ; i < 100 ; i++) {
      int test = cInt(22, 44);
      assertTrue("expected >= 22, actual "+test, test >= 22);
      assertTrue("expected <= 44, actual "+test, test <= 44);
    }
  }

   public int cInt(int min, int max) {
     double rnd = Math.random();
     rnd *= (max-min);
     rnd += min;
     return (int) Math.round(rnd);
   }
}
