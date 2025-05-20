package org.quickserver.net.qsadmin;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * QSAdminAPI class to communicate to QsAdmin from java applications.
 * <p>
 *  Eg:
 * <code><BLOCKQUOTE><pre>
 * 	QSAdminAPI qsAdminApi = new QSAdminAPI("127.0.0.1", 9080);
 * 	if(qsAdminApi.logon()) {
 * 		System.out.println("Logged in");
 * 		String info = qsAdminApi.sendCommand("info server");
 * 		System.out.println("Info on Server :\n"+info);
 * 		qsAdminApi.logoff();
 * 	} else {
 * 		System.out.println("Bad Login");
 * 		qsAdminApi.close();
 * 	}
 * </pre></BLOCKQUOTE></code></p>
 *
 * @see QSAdminServer
 * @since 1.4
 * @author Akshathkumar Shetty
 */
public class QSAdminAPI {

    /**
     * Will attempt to connect and logon to the remote QsAdminServer.
     */
    public boolean logon() throws IOException {
        return logon(username, password);
    }
}
