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

package fi.vtt.noen.mfw.webui;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import net.sourceforge.jwebunit.junit.WebTester;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * @author Teemu Kanstren
 */
public class WebTestUtils {
  public static final String PROBES_TABLE = "List of connected probes";
  public static final String MEASUREMENTS_TABLE = "Latest measurements";
  public static final String PROBE_INFO_TABLE = "Information for selected probe";
  public static final String AVAILABLE_BM_TABLE = "List of available base measures";
  public static final String EVENTS_TABLE = "List of MFW events";

  private static Process serverProcess = null;
  private static Process testProbeProcess = null;

  public static void invokeRestShutdown(String port) {
    if (serverProcess != null) {
      serverProcess.destroy();
    }
    Client client = Client.create();
    try {
      WebResource wr = client.resource("http://localhost:"+port+"/rest/shutdown");
      String response = wr.accept(MediaType.TEXT_PLAIN_TYPE).get(String.class);
    } catch (Exception e) {
      //this should fail since the remote VM should be shut down now..
    }
  }

  public static void startRemoteServer() throws Exception {
    serverProcess = startRemoteNode("server");
  }

  public static void startRemoteSuperNode() throws Exception {
    testProbeProcess = startRemoteNode("super");
  }

  public static Process startRemoteNode(String name) throws Exception {
    //get the working directory
    String workingDirectory = System.getProperty("user.dir");
    String slash = System.getProperty("file.separator");
    String serverBundleDir = workingDirectory+slash+"distro"+slash+name;
    String osName = System.getProperty("os.name");
    Runtime runtime = Runtime.getRuntime();
    if (osName.startsWith("Windows")) {
      String command = "cmd /c " + serverBundleDir + slash + "start-mfw.bat";
      System.out.println("Running windows server:"+command);
      Process process = runtime.exec(command, null, new File(serverBundleDir));
      Thread t1 = createReaderThread(process.getInputStream(), System.out);
      Thread t2 = createReaderThread(process.getErrorStream(), System.err);
      Thread.sleep(10000);
      return process;
    }
    if (osName.startsWith("Linux")) {
      String command = "bash start-mfw.sh";
      Process process = runtime.exec(command, null, new File(serverBundleDir));
      System.out.println("Running linux server:"+command);
      Thread t1 = createReaderThread(process.getInputStream(), System.out);
      Thread t2 = createReaderThread(process.getErrorStream(), System.err);
      Thread.sleep(10000);
      return process;
    }
    throw new IllegalStateException("This test only runs on Windows* and Linux* systems. Found:"+osName);
  }

  public static void goToHistoryFrame(WebTester t) {
    goToFrame(t, "history_link");
  }

  public static void goToProbeFrame(WebTester t) {
    goToFrame(t, "probes_link");
  }

  public static void goToEventFrame(WebTester t) {
    goToFrame(t, "events_link");
  }

  public static void goToFrame(WebTester t, String link) {
    t.beginAt("/");
    t.gotoFrame("body");
    t.gotoFrame("left");
    t.clickLink(link);

    t.gotoRootWindow();
    t.gotoFrame("body");
    t.gotoFrame("right");
  }

  public static Thread createReaderThread(final InputStream in, final PrintStream out) {
    Runnable run = new Runnable() {
      public void run() {
        InputStreamReader r = new InputStreamReader(in);
        BufferedReader bin = new BufferedReader(r);

        String line = null;
        try {
          while ((line = bin.readLine()) != null) {
            out.println(line);
          }
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    Thread t = new Thread(run);
    t.setDaemon(true);
    t.start();
    return t;
  }
}
