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

package fi.vtt.noen.mfw.unittests.agent.probe;

import fi.vtt.noen.mfw.bundle.blackboard.Blackboard;
import fi.vtt.noen.mfw.bundle.blackboard.BlackboardActivator;
import fi.vtt.noen.mfw.bundle.blackboard.BlackboardImpl;
import fi.vtt.noen.mfw.bundle.common.Const;
import fi.vtt.noen.mfw.bundle.common.KnowledgeSource;
import fi.vtt.noen.mfw.bundle.probe.plugins.communication.CommunicationPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.measurement.MeasurementPluginActivator;
import fi.vtt.noen.mfw.bundle.probe.plugins.measurement.MeasurementProvider;
import fi.vtt.noen.mfw.bundle.probe.plugins.xmlrpc.ProbeAgentConfig;
import fi.vtt.noen.mfw.bundle.probe.shared.BaseMeasure;
import fi.vtt.noen.mfw.bundle.probe.shared.MeasurementRequest;
import fi.vtt.noen.mfw.bundle.probe.shared.MeasurementResponse;
import fi.vtt.noen.mfw.bundle.probe.shared.ProbeInformation;
import fi.vtt.noen.mfw.probes.tester.TestProbe;
import fi.vtt.noen.mfw.unittests.EmptyBundleContext;
import fi.vtt.noen.mfw.unittests.MeasurementResponseTestPlugin;
import fi.vtt.noen.mfw.unittests.MockBundleContext;
import fi.vtt.noen.mfw.unittests.system.TestServerAgent;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class MeasurementRequestTests {
  //this is used as an empty stub to avoid nullpointers while checking nothing
  private BundleContext bc = new EmptyBundleContext();
  private static final String targetName1 = "target1";
  private static final String targetType1 = "targetType1";
  private static final String targetName2 = "target2";
  private static final String targetType2 = "targetType2";
  private static final String bmName1 = "bmName1";
  private static final String bmClass1 = "bmClass1";
  private static final String bmName2 = "bmName2";
  private static final String bmClass2 = "bmClass2";
  private static String measureURI1 = null;
  private static String measureURI2 = null;

  @BeforeClass
  public static void setup() {
    measureURI1 = Const.createMeasureURI(targetType1, targetName1, bmClass1, bmName1);
    measureURI2 = Const.createMeasureURI(targetType2, targetName2, bmClass2, bmName2);
  }

  /**
   * Requests a single measure from a measurementproviderthread and checks that the correct measure
   * is queried from the given probe and posted to the blackboard.
   *
   * @throws Exception
   */
  @Test
  public void requestSingleMeasure() throws Exception {
    Blackboard mockBb = createMock(Blackboard.class);
    mockBb.register((KnowledgeSource) anyObject());
    ProbeInformation probeInfo = new ProbeInformation(targetName1, targetType1, bmClass1, bmName1, null, null, 1, null);
    mockBb.process(new MeasurementResponse(EasyMock.<BaseMeasure>anyObject(), null, probeInfo, 1, -1));
    replay(mockBb);

    MeasurementResponseTestPlugin testPlugin = new MeasurementResponseTestPlugin();
    mockBb.register(testPlugin);
    MeasurementProvider provider = new MeasurementProvider(bc, mockBb);
    provider.start();

    TestProbe testProbe = new TestProbe(targetName1, targetType1, bmClass1, bmName1, "bm desc", "probe desc", "hello_world", 1);
    MeasurementRequest req = new MeasurementRequest(null, measureURI1, testProbe);
    provider.addSamplingRequest(req);
    //this is for letting the measurement provider thread provide the measure, and for the watchdog to remove the old subscription
    Thread.sleep(1500);
    verify(mockBb);
    assertEquals(0, provider.getSubscriptions().size());
    provider.stop();
  }

  /**
   * This test requests for sampling of measurements every 100 milliseconds, and checks the correct amount is received in 400 millis.
   */
  @Test
  public void requestSamplingMeasures() throws Exception {
    Blackboard bb = new BlackboardImpl();
    MeasurementResponseTestPlugin testPlugin = new MeasurementResponseTestPlugin();
    bb.register(testPlugin);
    MeasurementProvider provider = new MeasurementProvider(bc, bb);
    provider.start();
    TestProbe testProbe = new TestProbe(targetName1, targetType1, bmClass1, bmName1, "say hello", "hello_world", "result", 1);
    MeasurementRequest req = new MeasurementRequest(null, measureURI1, testProbe, 100);
    provider.addSamplingRequest(req);
    //this is for letting the measurement provider thread provide the measure
    Thread.sleep(500);
    int responses = testPlugin.getResponses().size();
    //allow for 3 or 4 responses in this time to account for timing non-determinism
    assertTrue("Number of measurement responses should be 4-7, is "+responses, responses >= 4 && responses <= 7);
    provider.stop();
  }

  /**
   * Makes a request through an actual blackboard, uses actual bundle activators to tie it all together.
   * Checks that correct type of responses are received for requests.
   *
   * @throws Exception
   */
  @Test
  public void requestThroughBlackboard() throws Exception {
    MockBundleContext bc = new MockBundleContext();
    BlackboardActivator ba = new BlackboardActivator();
    ba.start(bc);
    Blackboard bb = ba.getBlackboard();

    MeasurementPluginActivator mpa = new MeasurementPluginActivator(5, 5);
    mpa.start(bc);
    CommunicationPluginActivator cpa = new CommunicationPluginActivator();
    cpa.start(bc);

    TestProbe testProbe1 = new TestProbe(targetName1, targetType1, bmClass1, bmName1, "bm desc", "probe desc", "hello_world", 1);
    TestProbe testProbe2 = new TestProbe(targetName2, targetType2, bmClass2, bmName2, "bm desc 2", "probe desc 2", "11", 1);
    TestServerAgent testServer = new TestServerAgent();

    MeasurementRequest req = new MeasurementRequest(testServer, measureURI1, testProbe1);
    bb.process(req);
    testServer.waitForMeasure(measureURI1, 500);
    assertEquals(1, testServer.getReceivedStrings(measureURI1));
    assertEquals(0, testServer.getReceivedBooleans(measureURI1));
    assertEquals(0, testServer.getReceivedDoubles(measureURI1));

    req = new MeasurementRequest(testServer, measureURI2, testProbe2);
    bb.process(req);
    testServer.waitForMeasure(measureURI2, 500);
    assertEquals(1, testServer.getReceivedStrings(measureURI2));
    assertEquals(0, testServer.getReceivedBooleans(measureURI2));
    assertEquals(0, testServer.getReceivedDoubles(measureURI2));

    ba.stop(bc);
    mpa.stop(bc);
    cpa.stop(bc);
  }
}
