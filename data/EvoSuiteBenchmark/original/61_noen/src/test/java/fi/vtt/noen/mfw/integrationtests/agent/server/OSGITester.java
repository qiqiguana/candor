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

package fi.vtt.noen.mfw.integrationtests.agent.server;

/**
 * Interface for the OSGI tester class that implements integration tests inside the OSGI container.
 * 
 *
 * @author Teemu Kanstren
 */
public interface OSGITester {
  /**
   * Initializes the tester with all required services. Needs to be called after all the services have been
   * registered in order to find them. Constructor does not work since bundles are autoloaded in unspecific order.
   * */
  public void init();
  /** Tests that all plugins are correctly registered to the Blackboard. */
  public void testPluginsRegistered();
  public void servicesStarted() throws Exception;
}
