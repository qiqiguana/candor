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

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Collection;

/**
 * @author Teemu Kanstrén
 */
public class MainTests {
  @Test
  public void testClassLoading() throws Exception {
    Collection<Class> inputs = Main.inputs();
    assertEquals(2, inputs.size());
    assertTrue(inputs.contains(TestInputInterface.class));
    assertTrue(inputs.contains(TestInputInterface2.class));

    Collection<Class> outputs = Main.outputs();
    assertEquals(1, outputs.size());
    assertTrue(outputs.contains(TestOutputInterface.class));
  }
}
