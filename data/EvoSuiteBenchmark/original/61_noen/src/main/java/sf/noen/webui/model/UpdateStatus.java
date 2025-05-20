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

package sf.noen.webui.model;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.Failure;

import java.util.*;
import java.io.Serializable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Holds a collection of updates that have not yet been processed (shown) by the UI manager.
 * 
 * @author Teemu Kanstrén
 */
public class UpdateStatus extends RunListener implements Serializable {
  private Collection<Update> updates = new Vector<Update>();
  private Map<String, String> failedTests = new HashMap<String, String>();

  public Collection<Update> getUpdates() {
    ArrayList<Update> updatesClone = new ArrayList<Update>();
    updatesClone.addAll(updates);
    return updatesClone;
  }

  public void processed(Update update) {
    updates.remove(update);
  }

  @Override
  public void testRunStarted(Description description) throws Exception {
    update("Suite", "Started", "Tests being executed", UpdateType.SUITE_STARTED);
  }

  @Override
  public void testRunFinished(Result result) throws Exception {
    update("Suite", "Finished", "All tests done", UpdateType.SUITE_FINISHED);
  }

  @Override
  public void testStarted(Description description) throws Exception {
    update(description.getDisplayName(), "Started", "Test started", UpdateType.TEST_STARTED);
  }

  @Override
  public void testFinished(Description description) throws Exception {
    String name = description.getDisplayName();
    if (failedTests.get(name) != null) {
      update(name, "Failed", failedTests.get(name), UpdateType.TEST_FAILED);
    } else {
      update(name, "OK", "Test passed", UpdateType.TEST_FINISHED);
    }
  }

  @Override
  public void testFailure(Failure failure) throws Exception {
    String name = failure.getDescription().getDisplayName();
//    update(name, "Failed:"+failure.getMessage(), UpdateType.TEST_FAILED);
    String msg = getStackTrace(failure.getException());
    failedTests.put(name, msg);
  }

  @Override
  public void testAssumptionFailure(Failure failure) {
    update(failure.getDescription().getDisplayName(), "Assumption failure:", failure.getMessage(), UpdateType.ASSUMPTION_FAILED);
  }

  @Override
  public void testIgnored(Description description) throws Exception {
    update(description.getDisplayName(), "Ignored", "Test not executed", UpdateType.TEST_IGNORED);
  }


  private void update(String name, String status, String description, UpdateType type) {
    Update update = new Update(name, status, description, type, type.css);
    updates.add(update);
  }


  public static String getStackTrace(Throwable aThrowable) {
    final Writer result = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(result);
    aThrowable.printStackTrace(printWriter);
    return result.toString();
  }
}
