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

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Was intended to provide means to stop execution of long test suites in the middle.
 * However, not used right now since it is hard to use with other runners such as the
 * basic test suite runner..
 *
 * @author Teemu Kanstrén
 */
public class JUnitRunner extends BlockJUnit4ClassRunner {
  private RunNotifier notifier = null;

  public JUnitRunner(Class<?> aClass) throws InitializationError {
    super(aClass);
  }

  @Override
  public void run(RunNotifier runNotifier) {
    this.notifier = runNotifier;
    super.run(runNotifier);
  }

  public void stop() {
    notifier.pleaseStop();
  }

  //requires @RunWith(sf.noen.webui.server.JUnitRunner in the test class to use the "stop" functionality
}
