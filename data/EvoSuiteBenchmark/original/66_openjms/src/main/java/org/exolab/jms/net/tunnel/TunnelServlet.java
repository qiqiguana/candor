/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2004-2006 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: TunnelServlet.java,v 1.3 2007/03/10 12:42:13 tanderson Exp $
 */
package org.exolab.jms.net.tunnel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * HTTP Tunnel servlet.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2007/03/10 12:42:13 $
 */
public class TunnelServlet extends HttpServlet {

    /**
     * The host that the server is running on.
     */
    private String _host;

    /**
     * The port that the connector is listening on.
     */
    private int _port;

    /**
     * The socket timeout, in milliseconds. A value of <code>0</code> indicates
     * to block indefinitely.
     */
    private int _timeout;

    /**
     * The connection manager.
     */
    private static final SocketManager _manager = new SocketManager();

    /**
     * Initialisation property name for the host that the server is running on.
     */
    private static final String SERVER_HOST = "host";

    /**
     * Initialisation property name for the port that the connector is listening
     * on.
     */
    private static final String SERVER_PORT = "port";

    /**
     * Initialisation property name for the maximum time a connection will
     * block waiting for data.
     */
    private static final String READ_TIMEOUT = "readTimeout";

    /**
     * The default read timeout in seconds.
     */
    private static final int DEFAULT_READ_TIMEOUT = 30;

    /**
     * Initialisation property name for the time a connection must be idle for,
     * before it may be reaped.
     */
    private static final String IDLE_PERIOD = "idlePeriod";

    /**
     * The default idle period in seconds.
     */
    private static final int DEFAULT_IDLE_PERIOD = 60 * 5;


    /**
     * Initialise the servlet.
     *
     * @throws ServletException if the servlet can't be initialised
     */
    public void init() throws ServletException {
        _host = getString(SERVER_HOST);
        _port = getInt(SERVER_PORT);

        int readTimeout = getInt(READ_TIMEOUT, DEFAULT_READ_TIMEOUT);
        int idlePeriod = getInt(IDLE_PERIOD, DEFAULT_IDLE_PERIOD);

        _timeout = readTimeout * 1000;
        _manager.setIdlePeriod(idlePeriod);

        log("OpenJMS tunnel accepting requests (timeout=" + readTimeout
                + ", idle=" + idlePeriod);
    }

    /**
     * Handle a GET request. This method always sets the response code to
     * <code>HttpServletResponse.SC_BAD_REQUEST</code>.
     *
     * @param request  the client request
     * @param response the response to the request
     * @throws IOException for any I/O error
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Handle a POST request.
     *
     * @param request  the client request
     * @param response the response to the request
     * @throws IOException for any I/O error
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        String action = request.getHeader("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                               "Invalid action");
        } else if (action.equals("open")) {
            open(response);
        } else {
            String id = request.getHeader("id");
            if (id == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                                   "Invalid connection");
            } else if (action.equals("read")) {
                read(id, response);
            } else if (action.equals("write")) {
                write(id, request, response);
            } else if (action.equals("close")) {
                close(id, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                                   "Invalid action");
            }
        }
    }

    /**
     * Handle an open request. A connection is established to the server and the
     * identifier written to the client.
     *
     * @param response the response to the request
     * @throws IOException for any I/O error
     */
    private void open(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = new PrintWriter(response.getWriter());

        try {
            String id = _manager.open(_host, _port);
            out.println("OPEN " + id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception exception) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                               exception.getMessage());
            log("open failed", exception);
        }
    }

    /**
     * Handles a read request. Data is read from the endpoint and written to the
     * client.
     *
     * @param id       the endpoint identifier
     * @param response the response to the client
     * @throws IOException for any I/O error
     */
    private void read(String id, HttpServletResponse response)
            throws IOException {
        Socket socket = _manager.getSocket(id);
        if (socket == null) {
            log("Connection not found, id=" + id);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                               "Connection not found");
        } else {
            byte[] data = new byte[1024];
            try {
                socket.setSoTimeout(_timeout);
                InputStream in = socket.getInputStream();
                int count = 0;
                try {
                    count = in.read(data);
                } catch (InterruptedIOException ignore) {
                }
                // log("read(id=" + id + "), [length=" + count + "]");
                if (count != -1) {
                    response.setContentLength(count);
                    response.setStatus(HttpServletResponse.SC_OK);
                    OutputStream out = response.getOutputStream();
                    out.write(data, 0, count);
                    out.flush();
                } else {
                    remove(id);
                    response.setStatus(
                            HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } catch (IOException exception) {
                log("read failed", exception);
                remove(id);
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        exception.getMessage());
            }
        }
    }

    /**
     * Handles a write request. Data from the client is written to the endpoint
     *
     * @param id       the endpoint identifier
     * @param request  the client request
     * @param response the response to the client
     * @throws IOException for any I/O error
     */
    private void write(String id, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Socket endpoint = _manager.getSocket(id);
        if (endpoint == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                               "Connection not found");
        } else {
            try {
                // log("write(id=" + id + "), [length="
                //    + request.getContentLength()
                //    + "]");
                InputStream in = request.getInputStream();
                OutputStream out = endpoint.getOutputStream();
                byte[] data = new byte[1024];
                int count = 0;
                while (count != -1) {
                    count = in.read(data);
                    if (count > 0) {
                        out.write(data, 0, count);
                    }
                }
                in.close();
                out.flush();
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (IOException exception) {
                log("write failed", exception);
                remove(id);
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        exception.getMessage());
            }
        }
    }

    /**
     * Handle a close request.
     *
     * @param id       the endpoint identifier
     * @param response the response to the client
     * @throws IOException for any I/O error
     */
    private void close(String id, HttpServletResponse response)
            throws IOException {

        try {
            log("close(id=" + id + ")");
            _manager.close(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException exception) {
            log("close failed", exception);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                               exception.getMessage());
        }
    }

    /**
     * Removes a socket.
     *
     * @param id the socket identifier
     */
    private void remove(String id) {
        try {
            _manager.close(id);
        } catch (IOException ignore) {
        }
    }

    /**
     * Helper to get an initialisation property.
     *
     * @param name the property name
     * @return the value corresponding to <code>name</code>
     * @throws ServletException if the property doesn't exist
     */
    private String getString(String name) throws ServletException {
        String value = getInitParameter(name);
        if (value == null) {
            throw new ServletException("Property not defined: " + name);
        }
        return value;
    }

    /**
     * Helper to get an initialisation property.
     *
     * @param name the property name
     * @return the value corresponding to <code>name</code>
     * @throws ServletException if the property doesn't exist
     */
    private int getInt(String name) throws ServletException {
        int result;
        String value = getString(name);
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new ServletException("Invalid " + name + ": " + value);
        }
        return result;
    }

    /**
     * Helper to get an initialisation property.
     *
     * @param name         the property name
     * @param defaultValue the default value to use
     * @return the value corresponding to <code>name</code>
     * @throws ServletException if the value is invalid
     */
    private int getInt(String name, int defaultValue) throws ServletException {
        int result;
        String value = getInitParameter(name);
        if (value == null) {
            result = defaultValue;
        } else {
            try {
                result = Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                throw new ServletException("Invalid " + name + ": " + value);
            }
        }
        return result;
    }
}
