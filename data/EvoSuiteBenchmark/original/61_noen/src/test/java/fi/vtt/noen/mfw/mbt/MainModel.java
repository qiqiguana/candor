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

package fi.vtt.noen.mfw.mbt;

import fi.vtt.noen.mfw.bundle.server.plugins.registry.RegistryPlugin;
import fi.vtt.noen.mfw.bundle.server.plugins.sac.SACPlugin;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import fi.vtt.noen.mfw.mbt.log.TestCase;
import fi.vtt.noen.mfw.mbt.log.TestStep;
import fi.vtt.noen.mfw.mbt.log.TestSuite;
import fi.vtt.noen.mfw.probes.tester.TestProbe;
import fi.vtt.noen.mfw.unittests.system.testprobes.MockSAC;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.coverage.CoverageHistory;
import nz.ac.waikato.modeljunit.coverage.CoverageMetric;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;

import java.util.ArrayList;
import java.util.List;

import static fi.vtt.noen.mfw.mbt.TestUtils.*;
import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class MainModel implements FsmModel {
  private SystemSetup setup = new SystemSetup();
  private List<TestProbe> probes = new ArrayList<TestProbe>();
  private RegistryPlugin registry = null;
  private SACPlugin server = null;
  private MockSAC mockSac = null;
  private TestSuite suiteLogger = new TestSuite("MFW-Test-Suite");
  private TestCase test = null;
  private int testIndex = 1;

  public MainModel() throws Exception {
    setup.init();
    suiteLogger.start();
  }

  public void shutdown() throws Exception {
    test.end();
    suiteLogger.end();
    setup.teardown();
    System.out.println("info:"+getThreadInfo());
    System.exit(0);
  }

  public Object getState() {
    int probeState = 0;
    if (probes.size() > 0) {
      probeState = 1;
    }
    if (probes.size() == 5) {
      probeState = 2;
    }
    return ""+probeState;
  }

  public void reset(boolean b) {
    if (test != null) {
      test.end();
    }
    test = suiteLogger.startTest("TEST "+testIndex);
    testIndex++;
    System.out.println("----------RESET-------------------------------");
    try {
      setup.teardown();
      probes.clear();
//      Thread.sleep(1000);
      setup = new SystemSetup();
      setup.init();
      registry = setup.getRegistry();
      server = setup.getSACPlugin();
      mockSac = setup.getMockSAC();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean registerProbeGuard() {
    //we limit the max number of registrations to contain state-explosion for now
    return probes.size() < 5;
  }

  //todo: register probes with same bm?
  @Action
  public void registerProbe() {
    test.startStep("REGISTER PROBE");
    try {
      TestProbe probe = setup.addProbe();
      //let registrationthread do its job
      Thread.sleep(2000);
      probes.add(probe);
      //todo: fix, modeljunit swallows stacktrace in failure reporting if not printed by self
      assertEquals(probes.size(), setup.getRegistry().getProbes().size());
      assertEquals(probes.size(), mockSac.probes.size());
      List<BMDescription> bms = server.getAvailableBMList();
      assertEquals(probes.size(), bms.size());
    } catch (Exception e) {
      test.endStep("REGISTER PROBE");
      e.printStackTrace();
    } catch (Error e) {
      test.endStep("REGISTER PROBE");
      e.printStackTrace();
    }
    test.endStep("REGISTER PROBE");
  }

  public boolean requestBMGuard() {
//    return false;
    return probes.size() > 0;
  }

  //useampaan osamalliin jako, tyyliin yksi tekee useita subscribeja, toinen samoilla bm:lla, toinen eri, nama erikseen ajoon..

  @Action
  public void requestBM() {
    TestStep step = test.startStep("REQUEST BM");
    try {
      List<BMDescription> bms = server.getAvailableBMList();
      step.action("Requested available BM").result(bms).end();
      BMDescription bm = oneOf(bms);
      long bmId = bm.getBmId();
      List<Value> values = mockSac.values.get(bmId);
      int previous = values.size();
      boolean ok = server.requestBM(1, bmId);
      assertTrue(ok);
      Thread.sleep(100);
      values = mockSac.values.get(bmId);
      assertTrue("Should have received a new value", values.size() == previous + 1);
    } catch (Exception e) {
      e.printStackTrace();
      //TODO: differentiate success from failure
      test.endStep("REQUEST BM");
      fail("action failure (see trace)");
    }
    test.endStep("REQUEST BM");
  }

  //requestbm
  //requestbmsampling
  //requestprobeconfiguration
  //setprobeconfiguration
  //getprobes
  //gettargets

  public static void main(String args[]) throws Exception {
    // create our model and a test generation algorithm
    MainModel mainModel = new MainModel();
    Tester tester = new RandomTester(mainModel);

    // build the complete FSM graph for our model, just to ensure
    // that we get accurate model coverage metrics.
    tester.buildGraph();

    // set up our favourite coverage metric
    CoverageMetric trCoverage = new TransitionCoverage();
    tester.addCoverageMetric(trCoverage);
    CoverageHistory hist = new CoverageHistory(new TransitionCoverage(), 1);
    tester.addCoverageMetric(hist);

    // ask to print the generated tests
    tester.addListener("verbose");

//    while (hist.getPercentage() < 99.0)
//      tester.generate();

    // generate a small test suite of 20 steps (covers 4/5 transitions)
    tester.generate(20);

    tester.getModel().printMessage(trCoverage.getName() + " was " + trCoverage.toString());

    mainModel.shutdown();
  }

}
