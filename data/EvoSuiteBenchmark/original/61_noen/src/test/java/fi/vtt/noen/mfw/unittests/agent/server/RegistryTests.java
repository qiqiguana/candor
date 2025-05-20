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

package fi.vtt.noen.mfw.unittests.agent.server;

import fi.vtt.noen.mfw.bundle.server.plugins.registry.RegistryPluginImpl;
import fi.vtt.noen.mfw.unittests.MockBundleContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class RegistryTests {
  @Test
  public void testTargetParsingFromMeasureURI() {
    String measureURI = "MFW://target_type/target_name/bm_class/bm_name";
    RegistryPluginImpl registry = new RegistryPluginImpl(new MockBundleContext(), 0, 0);
    assertEquals("target_type", registry.parseTargetType(measureURI));
    assertEquals("target_name", registry.parseTargetName(measureURI));
  }
}
