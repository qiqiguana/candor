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

package fi.vtt.noen.mfw.unittests;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This is taken from ganymede SSH2 examples to try out how it works
 *
 * @author Teemu Kanstren
 */
public class SSHTesting {
  public static void main(String[] args) throws Exception {
    String hostname = "put_your_hostname_here";
    String username = "put_your_username_here";
    String password = "put_your_password_here";

    /* Create a connection instance */
    Connection conn = new Connection(hostname);

    /* Now connect */
    conn.connect();

    /* Authenticate.
     * If you get an IOException saying something like
     * "Authentication method password not supported by the server at this stage."
     * then please check the FAQ.
     */
    boolean isAuthenticated = conn.authenticateWithPassword(username, password);

    if (isAuthenticated == false)
      throw new IOException("Authentication failed.");

    /* Create a session */
    Session sess = conn.openSession();
    sess.execCommand("uname -a && date && uptime && who");
//    sess.execCommand("ls -l");
    System.out.println("Here is some information about the remote host:");
    /*
     * This basic example does not handle stderr, which is sometimes dangerous
     * (please read the FAQ).
     */
    InputStream stdout = new StreamGobbler(sess.getStdout());
    BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
    while (true) {
      String line = br.readLine();
      if (line == null)
        break;
      System.out.println(line);
    }

    /* Show exit status, if available (otherwise "null") */
    System.out.println("ExitCode: " + sess.getExitStatus());
    /* Close this session */
    sess.close();
    /* Close the connection */
    conn.close();
  }
}
