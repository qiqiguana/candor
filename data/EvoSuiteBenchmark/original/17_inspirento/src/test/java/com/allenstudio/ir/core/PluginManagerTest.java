/*
 * @(#)PluginManagerTest.java
 * Created on 2005-8-30
 * Inspirento, Copyright AllenStudio, All Rights Reserved
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package com.allenstudio.ir.core;

import junit.framework.TestCase;
public class PluginManagerTest extends TestCase {

    /*
     * Test method for 'com.allenstudio.ir.core.PluginManager.getAllPluginNames()'
     */
    public void testGetAllPluginNames() {
        String[] expected = {"Common", "Birthday"};
        assertTrue(expected[1].equals(PluginManager.getInstance().getAllPluginNames()[1])
                && expected[0].equals(PluginManager.getInstance().getAllPluginNames()[0]));
    }

}
