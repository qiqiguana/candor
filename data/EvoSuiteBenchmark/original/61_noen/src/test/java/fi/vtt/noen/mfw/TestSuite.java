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

package fi.vtt.noen.mfw;

import fi.vtt.noen.mfw.unittests.agent.probe.MeasurementRequestTests;
import fi.vtt.noen.mfw.unittests.agent.probe.ProbeConfigurationTests;
import fi.vtt.noen.mfw.unittests.agent.probe.ProbeRegistrationTests;
import fi.vtt.noen.mfw.unittests.agent.probe.SSHProbeTests;
import fi.vtt.noen.mfw.unittests.agent.server.BlackBoardTests;
import fi.vtt.noen.mfw.unittests.agent.server.DMTests;
import fi.vtt.noen.mfw.unittests.system.ProbeInvocationTests;
import fi.vtt.noen.mfw.unittests.system.ProbeServerRegistrationTests;
import fi.vtt.noen.mfw.unittests.system.ReConnectionTests;
import fi.vtt.noen.mfw.unittests.system.SACToTestPluginTests;
import fi.vtt.noen.mfw.unittests.xmlrpctesting.ProbeAgentTests;
import fi.vtt.noen.mfw.unittests.xmlrpctesting.ServerAgentTests;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import sf.net.sinve.trace.Trace;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teemu Kanstren
 */
public class TestSuite {
  public static void main(String[] args) {
    List<Class> tests = new ArrayList<Class>();
    tests.add(MeasurementRequestTests.class);
    tests.add(ProbeConfigurationTests.class);
    tests.add(ProbeRegistrationTests.class);
    tests.add(SSHProbeTests.class);
    tests.add(BlackBoardTests.class);
    tests.add(DMTests.class);
    tests.add(ProbeInvocationTests.class);
    tests.add(ProbeServerRegistrationTests.class);
    tests.add(ReConnectionTests.class);
    tests.add(SACToTestPluginTests.class);
    tests.add(ProbeAgentTests.class);
    tests.add(ServerAgentTests.class);

    Class[] makeMeHappy = new Class[tests.size()];
    tests.toArray(makeMeHappy);
    JUnitCore junit = new JUnitCore();
    junit.addListener(new TraceTestListener());
    junit.run(makeMeHappy);
//    junit.run(MeasurementRequestTests.class);
//    junit.run(ProbeConfigurationTests.class);
  }

  private static class TraceTestListener extends RunListener {

    @Override
    public void testStarted(Description description) throws Exception {
      super.testStarted(description);
      String testName = description.getDisplayName();
      try {
        Trace.startSequence(testName);
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("STARTING TEST "+testName);
    }

    @Override
    public void testFinished(Description description) throws Exception {
      super.testFinished(description);
      try {
        Trace.endSequence();
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("ENDING TEST");
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
      super.testRunFinished(result);
      System.out.println("SUITE OVER");
      Trace.closeTrace();
    }

    @Override
    public void testRunStarted(Description description) throws Exception {
      super.testRunStarted(description);
      System.out.println("SUITE STARTED");
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
      super.testFailure(failure);
      System.out.println("TEST FAILED");
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
      super.testAssumptionFailure(failure);
      System.out.println("TEST ASSUMPTION FAILED");
    }

    @Override
    public void testIgnored(Description description) throws Exception {
      super.testIgnored(description);
      System.out.println("TEST IGNORED");
    }
  }
}
