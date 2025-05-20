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

import fi.vtt.noen.mfw.bundle.common.DataObject;
import fi.vtt.noen.mfw.bundle.common.KnowledgeSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Teemu Kanstren
 */
public class MockKnowledgeSource implements KnowledgeSource {
  private Class clazz;
  public int count = 0;
  private List<DataObject> received = new ArrayList<DataObject>();

  public MockKnowledgeSource(Class clazz) {
    this.clazz = clazz;
  }

  public String getName() {
    return getClass().getName();
  }

  public Set<Class> getCommands() {
    Set<Class> commands = new HashSet<Class>();
    commands.add(clazz);
    return commands;
  }

  public void process(DataObject data) {
    count++;
    received.add(data);
  }

  public List<DataObject> getReceived() {
    return received;
  }
}
