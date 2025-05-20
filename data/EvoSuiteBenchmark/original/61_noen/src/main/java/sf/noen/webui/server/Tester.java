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

package sf.noen.webui.server;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

/**
 * Takes a test (suite) class and executes with JUnit.
 *
 * @author Teemu Kanstrén
 */
public class Tester implements Runnable {
  private final Class testClass;
  private RunListener testListener;
  private Thread thread = null;

  public Tester(Class testClass, RunListener testListener) {
    this.testListener = testListener;
    this.testClass = testClass;
  }

  public void executeTests() {
    Thread thread = new Thread(this);
    this.thread = thread;
    thread.start();
  }

  public Thread getThread() {
    return thread;
  }

  //TODO give error if unable to create test class, in fact give useful errors in general

  public void run() {
    JUnitCore junit = new JUnitCore();
    junit.addListener(testListener);
    Result result = junit.run(testClass);
  }
}
