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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FakeTest2 {
  public static boolean timeouts = true;

  @Test
  public void test2_1() throws Exception {
    if (!timeouts) return;
    Thread.sleep(1000);
  }

  @Test
  public void test2_2() throws Exception {
    if (!timeouts) return;
    Thread.sleep(4000);
  }

  @Test
  public void test2_3() throws Exception {
    if (!timeouts) return;
    Thread.sleep(2000);
  }

  @Test
  public void test2_4() throws Exception {
    if (!timeouts) return;
    assertTrue("Failure test", false);
  }

  @Test
  public void test2_5() throws Exception {
    if (!timeouts) return;
    throw new Exception("Error test");
  }
}
