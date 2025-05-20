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

import fi.vtt.noen.mfw.bundle.blackboard.Blackboard;
import fi.vtt.noen.mfw.bundle.blackboard.BlackboardActivator;
import fi.vtt.noen.mfw.bundle.blackboard.BlackboardImpl;
import fi.vtt.noen.mfw.bundle.common.EventType;
import fi.vtt.noen.mfw.bundle.common.KnowledgeSource;
import fi.vtt.noen.mfw.bundle.probe.plugins.communication.CommunicationPluginActivator;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ServerEvent;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import fi.vtt.noen.mfw.unittests.MockBundleContext;
import fi.vtt.noen.mfw.unittests.system.TestKnowledgeSource;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class BlackBoardTests {
  private Blackboard bb = null;
  private MockKnowledgeSource eventPlugin;
  private MockKnowledgeSource valuePlugin;

  @Before
  public void setup() {
    bb = new BlackboardImpl();
    eventPlugin = new MockKnowledgeSource(ServerEvent.class);
    valuePlugin = new MockKnowledgeSource(Value.class);
    bb.register(eventPlugin);
    bb.register(valuePlugin);
  }

  private ServerEvent createEvent(String msg) {
    return new ServerEvent(System.currentTimeMillis(), EventType.GENERAL_ERROR, "BlackboardTests", msg);
  }

  @Test
  public void eventProcessing() {
    ServerEvent event = createEvent("test-event");
    bb.process(event);
    assertEquals(1, eventPlugin.count);
    assertEquals(0, valuePlugin.count);
  }

  @Test
  public void registerMultipleTimes() {
    bb.register(eventPlugin);
    bb.register(valuePlugin);
    ServerEvent event = createEvent("test-event");
    bb.process(event);
    assertEquals(1, eventPlugin.count);
    assertEquals(0, valuePlugin.count);
  }

  @Test
  public void registerUnregisterRegister() {
    bb.unregister(eventPlugin);
    bb.unregister(valuePlugin);
    ServerEvent event = createEvent("test-event");
    bb.process(event);
    assertEquals(0, eventPlugin.count);
    assertEquals(0, valuePlugin.count);

    bb.register(eventPlugin);
    bb.register(valuePlugin);
    bb.process(event);
    assertEquals(1, eventPlugin.count);
    assertEquals(0, valuePlugin.count);
  }

  @Test
  public void startBlackboardWithKnowledgeSources() throws Exception {
    BlackboardActivator ba = new BlackboardActivator();
    MockBundleContext mbc = new MockBundleContext();
    TestKnowledgeSource tks = new TestKnowledgeSource(ServerEvent.class);
    mbc.registerService(KnowledgeSource.class.getName(), tks, null);
    ba.start(mbc);
    Blackboard bb = ba.getBlackboard();
    ServerEvent event = createEvent("hello");
    bb.process(event);
    assertEquals("Received messages", 1, tks.getMessages().size());
  }

  @Test
  public void useStoppedBlackboard() throws Exception {
    BlackboardActivator ba = new BlackboardActivator();
    MockBundleContext mbc = new MockBundleContext();
    ba.start(mbc);
    Blackboard bb = ba.getBlackboard();
    TestKnowledgeSource tks = new TestKnowledgeSource();
    Collection<KnowledgeSource> plugins = bb.getPlugins();
    assertEquals("Empty set of plugins should be created", 0, plugins.size());
    bb.register(tks);
    plugins = bb.getPlugins();
    assertEquals("A plugin should be registered", 1, plugins.size());
    ba.stop(mbc);
    plugins = bb.getPlugins();
    assertEquals("Stop should clear the set of installed plugins", 0, plugins.size());
    bb.register(tks);
    plugins = bb.getPlugins();
    assertEquals("Register should do nothing after the blackboard is stopped", 0, plugins.size());
  }

  /**
   * Test with a KS that subscribes to no commands.
   * Test with a KS that throws an Exception while processing a message.
   * ..In order to check the BB will keep working despite issues.
   * (this does not check if a KS hangs.. Would maybe require a thread per KS?)
   *
   * @throws Exception
   */
  @Test
  public void exceptionalKnowledgeSources() throws Exception {
    BlackboardActivator ba = new BlackboardActivator();
    MockBundleContext mbc = new MockBundleContext();
    ba.start(mbc);
    Blackboard bb = ba.getBlackboard();
    TestKnowledgeSource eventPlugin = new TestKnowledgeSource(ServerEvent.class);
    mbc.registerService(KnowledgeSource.class.getName(), eventPlugin, null);
    ServerEvent event = createEvent("test-event");
    bb.process(event);
    assertEquals("Should have one event received", 1, eventPlugin.getMessages().size());
    TestKnowledgeSource nullPlugin = new TestKnowledgeSource(mbc, null);
    mbc.registerService(KnowledgeSource.class.getName(), nullPlugin, null);
    event = createEvent("test-event");
    bb.process(event);
    assertEquals("Should have two events received", 2, eventPlugin.getMessages().size());
    KnowledgeSource mockPlugin = createMock(KnowledgeSource.class);
    Set mockCommands = new HashSet();
    mockCommands.add(ServerEvent.class);
    expect(mockPlugin.getCommands()).andReturn(mockCommands);
    ServerEvent testEvent = createEvent("test-event");
    mockPlugin.process(testEvent);
    expectLastCall().andThrow(new RuntimeException("Expected"));
    replay(mockPlugin);
    bb.register(mockPlugin);
    bb.process(testEvent);
    bb.unregister(mockPlugin);
    verify(mockPlugin);
    bb.process(testEvent);
    verify(mockPlugin);
    assertEquals("Should have three events received", 4, eventPlugin.getMessages().size());
  }

  @Test
  public void blackboardRegisterAfterPlugin() throws Exception {
    MockBundleContext mbc = new MockBundleContext();
    CommunicationPluginActivator cpa = new CommunicationPluginActivator();
    cpa.start(mbc);

    BlackboardActivator ba = new BlackboardActivator();
    ba.start(mbc);
    Blackboard bb = ba.getBlackboard();

    TestKnowledgeSource eventPlugin = new TestKnowledgeSource(ServerEvent.class);
    mbc.registerService(KnowledgeSource.class.getName(), eventPlugin, null);
    ServerEvent event = createEvent("test-event");
    bb.process(event);
    assertEquals("Should have one event received", 1, eventPlugin.getMessages().size());
  }

}
