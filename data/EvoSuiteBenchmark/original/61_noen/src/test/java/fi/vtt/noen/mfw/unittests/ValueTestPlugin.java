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

import fi.vtt.noen.mfw.bundle.common.BasePlugin;
import fi.vtt.noen.mfw.bundle.common.DataObject;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import org.osgi.framework.BundleContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * @author Teemu Kanstren
 */
public class ValueTestPlugin extends BasePlugin {
  private Collection<Value> values = new ArrayList<Value>();

  public ValueTestPlugin(BundleContext bc) {
    super(bc, null);
  }

  public Set getCommands() {
    return createCommandSet(Value.class);
  }

  public void process(DataObject data) {
    values.add((Value) data);
  }

  public Collection<Value> getValues() {
    return values;
  }
}
