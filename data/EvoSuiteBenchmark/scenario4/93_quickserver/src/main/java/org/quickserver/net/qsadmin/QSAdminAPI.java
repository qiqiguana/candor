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

    private static final Logger logger = Logger.getLogger(QSAdminAPI.class.getName());

    private String username = "Admin";

    private String password = "QsAdm1n";

    private String host = "localhost";

    private int port = 9877;

    private Socket socket;

    private InputStream in;

    private OutputStream out;

    private BufferedReader br;

    private BufferedWriter bw;

    /**
     * Creates QSAdminAPI object that will communicate with the
     * passed host and port.
     */
    public QSAdminAPI(String host, int port) {
    }

    /**
     * Will attempt to connect and logon to the remote QsAdminServer.
     */
    public boolean logon() throws IOException;

    /**
     * Will attempt to connect and logon to the remote QsAdminServer.
     */
    public boolean logon(String username, String password) throws IOException;

    /**
     * Sends the given command to QSAdmin and gives the response back.
     */
    public String sendCommand(String data) throws IOException;

    private String readResponse() throws IOException;

    /**
     * Logoff the QSAdminServer and closed the socket associated.
     */
    public void logoff() throws IOException;

    /**
     * Closes the socket associated.
     */
    public void close() throws IOException;

    public static void main(String[] args) throws Exception;
}
