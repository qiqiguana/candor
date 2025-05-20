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

package sf.noen.webui;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class FakeTest1 {
  public static boolean timeouts = true;

  @Test
  public void test1_1() throws Exception {
    if (!timeouts) return;
    Thread.sleep(1000);
  }

  @Test
  public void test1_2() throws Exception {
    if (!timeouts) return;
    Thread.sleep(4000);
  }

  @Test
  public void test1_3() throws Exception {
    if (!timeouts) return;
    Thread.sleep(2000);
  }

  @Test
  public void test1_4() throws Exception {
    assertTrue("Failure test", false);
  }

  @Test
  public void test1_5() throws Exception {
    throw new Exception("Error test");
  }

  @Ignore @Test
  public void test1_6() throws Exception {
    fail("this should never be executed");
  }
}
