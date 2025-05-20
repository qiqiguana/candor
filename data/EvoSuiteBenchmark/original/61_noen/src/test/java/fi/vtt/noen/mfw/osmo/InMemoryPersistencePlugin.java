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

package fi.vtt.noen.mfw.osmo;

import fi.vtt.noen.mfw.bundle.common.Const;
import fi.vtt.noen.mfw.bundle.common.DataObject;
import fi.vtt.noen.mfw.bundle.common.KnowledgeSource;
import fi.vtt.noen.mfw.bundle.server.plugins.persistence.PersistencePlugin;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ServerEvent;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.TargetDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import org.hibernate.annotations.Target;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Teemu Kanstren
 */
public class InMemoryPersistencePlugin implements PersistencePlugin, KnowledgeSource {
  @Override
  public List<ServerEvent> getEvents(int first, int count, ServerEvent.SortKey sortKey, boolean ascending) {
    return null;
  }

  @Override
  public int getEventCount() {
    return 0;
  }

  @Override
  public List<Value> getValues(int first, int count, Value.SortKey sortKey, boolean ascending) {
    return null;
  }

  @Override
  public List<Value> getValues(long start, long end, Long[] bmids, Value.SortKey sort, boolean asc) {
    return null;
  }

  @Override
  public int getValueCount() {
    return 0;
  }

  @Override
  public ProbeDescription createProbeDescription(Map<String, String> properties) {
    TargetDescription target = createTargetDescription(properties);
    BMDescription bm = createBMDescription(properties);
    return new ProbeDescription(properties, target, bm);
  }

  @Override
  public BMDescription createBMDescription(Map<String, String> properties) {
    TargetDescription target = createTargetDescription(properties);
    String bmClass = properties.get(Const.PROBE_BM_CLASS);
    String bmName = properties.get(Const.PROBE_BM_NAME);
    String bmDesc = properties.get(Const.PROBE_BM_DESCRIPTION);
    return new BMDescription(target, bmClass, bmName, bmDesc);
  }

  @Override
  public TargetDescription createTargetDescription(Map<String, String> properties) {
    String type = properties.get(Const.PROBE_TARGET_TYPE);
    String name = properties.get(Const.PROBE_TARGET_NAME);
    return new TargetDescription(type, name);
  }

  @Override
  public Set<Class> getCommands() {
    return null;
  }

  @Override
  public void process(DataObject data) {
  }

  @Override
  public String getName() {
    return getClass().getName();
  }
}
