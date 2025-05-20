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

package sf.noen.webui;

import org.junit.Test;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.Failure;
import org.junit.runner.Description;
import org.junit.runner.Result;
import sf.noen.webui.server.Tester;
import sf.noen.webui.model.Configuration;
import static org.junit.Assert.*;
import org.apache.wicket.util.tester.WicketTester;

import java.util.Collection;
import java.util.ArrayList;

public class TestExecutionTest {
  @Test
  public void testNotificationsAreReceived() throws Exception {
    FakeTest1.timeouts = false;

    Collection<String> expectedTests = new ArrayList<String>();
    expectedTests.add("test1_1(sf.noen.webui.FakeTest1)");
    expectedTests.add("test1_2(sf.noen.webui.FakeTest1)");
    expectedTests.add("test1_3(sf.noen.webui.FakeTest1)");
    expectedTests.add("test1_4(sf.noen.webui.FakeTest1)");
    expectedTests.add("test1_5(sf.noen.webui.FakeTest1)");

    Collection<String> expectedFailures = new ArrayList<String>();
    expectedFailures.add("test1_4(sf.noen.webui.FakeTest1)");
    expectedFailures.add("test1_5(sf.noen.webui.FakeTest1)");

    Collection<String> expectedIgnores = new ArrayList<String>();
    expectedIgnores.add("test1_6(sf.noen.webui.FakeTest1)");

    TestListener listener = new TestListener();
    Tester tester = new Tester(FakeTest1.class, listener);
    tester.executeTests();
    tester.getThread().join();
    assertEquals("started tests", expectedTests, listener.startedTests);
    assertEquals("finished tests", expectedTests, listener.finishedTests);
    assertEquals("failed tests", expectedFailures, listener.failedTests);
    assertEquals("ignored tests", expectedIgnores, listener.ignoredTests);

    assertTrue("test suite started", listener.started);
    assertTrue("test suite finished", listener.finished);

    //test that correct notifications are received
    //also test with a test suite but this time only chosen subset
  }

  @Test
  public void wicketTest() throws Exception {
    //test with the wicket test tools the actual page handling
    WicketTester tester = new WicketTester();
	tester.startPage(StartPageUpdatingRows.class);
	tester.assertRenderedPage(StartPageUpdatingRows.class);
	tester.assertContains("teemu");
  }

  @Test
  public void testLoadProperties() throws Exception {
	assertEquals("configured test class", "sf.noen.webui.FakeSuite", Configuration.getTestClassName());
  }

  private class TestListener extends RunListener {
    public boolean started = false;
    public boolean finished = true;
    public Collection<String> startedTests = new ArrayList<String>();
    public Collection<String> finishedTests = new ArrayList<String>();
    public Collection<String> failedTests = new ArrayList<String>();
    public Collection<String> failedAssumptions = new ArrayList<String>();
    public Collection<String> ignoredTests = new ArrayList<String>();

    @Override
    public void testRunStarted(Description description) throws Exception {
      started = true;
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
      finished = true;
    }

    @Override
    public void testStarted(Description description) throws Exception {
      startedTests.add(description.getDisplayName());
    }

    @Override
    public void testFinished(Description description) throws Exception {
      finishedTests.add(description.getDisplayName());
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
      failedTests.add(failure.getDescription().getDisplayName());
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
      failedAssumptions.add(failure.getDescription().getDisplayName());
    }

    @Override
    public void testIgnored(Description description) throws Exception {
      ignoredTests.add(description.getDisplayName());
    }
  }
}
