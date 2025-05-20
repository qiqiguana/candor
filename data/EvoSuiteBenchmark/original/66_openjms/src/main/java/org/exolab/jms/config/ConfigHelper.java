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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ConfigHelper.java,v 1.8 2005/12/01 13:53:23 tanderson Exp $
 */
package org.exolab.jms.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.exolab.jms.config.types.SchemeType;


/**
 * Helper class for interrogating the configuration.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.8 $ $Date: 2005/12/01 13:53:23 $
 */
public class ConfigHelper {

    /**
     * Returns the server URL for the specified scheme.
     *
     * @param scheme the connector scheme
     * @param config the configuration to use
     * @return the server URL for the specified scheme
     */
    public static String getServerURL(SchemeType scheme,
                                      Configuration config) {
        String url = null;
        ServerConfiguration server = config.getServerConfiguration();

        if (scheme.equals(SchemeType.TCP)) {
            url = getServerURL(scheme, server.getHost(),
                               config.getTcpConfiguration());
        } else if (scheme.equals(SchemeType.TCPS)) {
            url = getServerURL(scheme, server.getHost(),
                               config.getTcpsConfiguration());
        } else if (scheme.equals(SchemeType.RMI)) {
            RmiConfiguration rmi = config.getRmiConfiguration();
            if (rmi.getEmbeddedRegistry()) {
                // if the registry is embedded within the OpenJMS server,
                // use the server host
                url = getServerURL(scheme, server.getHost(), rmi);
            } else {
                url = getServerURL(scheme, rmi.getRegistryHost(), rmi);
            }
        } else if (scheme.equals(SchemeType.HTTP)) {
            url = getServerURL(scheme, config.getHttpConfiguration());
        } else if (scheme.equals(SchemeType.HTTPS)) {
            url = getServerURL(scheme, config.getHttpsConfiguration());
        } else if (scheme.equals(SchemeType.EMBEDDED)) {
            url = "vm:openjms";
        }
        return url;
    }

    /**
     * Returns the embedded JNDI URL for the specified scheme.
     *
     * @param scheme the connector scheme
     * @param config the configuration to use
     * @return embedded JNDI URL for the specified scheme
     */
    public static String getJndiURL(SchemeType scheme, Configuration config) {
        String url = null;
        ServerConfiguration server = config.getServerConfiguration();

        if (scheme.equals(SchemeType.TCP)) {
            url = getJndiURL(scheme, server.getHost(),
                             config.getTcpConfiguration());
        } else if (scheme.equals(SchemeType.TCPS)) {
            url = getJndiURL(scheme, server.getHost(),
                             config.getTcpsConfiguration());
        } else if (scheme.equals(SchemeType.HTTP)) {
            url = getJndiURL(scheme, config.getHttpConfiguration());
        } else if (scheme.equals(SchemeType.HTTPS)) {
            url = getJndiURL(scheme, config.getHttpsConfiguration());
        } else if (scheme.equals(SchemeType.RMI)) {
            RmiConfiguration rmi = config.getRmiConfiguration();
            if (rmi.getEmbeddedRegistry()) {
                // if the registry is embedded within the OpenJMS server,
                // use the server host
                url = getJndiURL(scheme, server.getHost(), rmi);
            } else {
                url = getJndiURL(scheme, rmi.getRegistryHost(), rmi);
            }
        } else if (scheme.equals(SchemeType.EMBEDDED)) {
            url = "vm:openjms";
        }
        return url;
    }

    /**
     * Returns the server administration URL for the specified scheme.
     *
     * @param scheme the connector scheme
     * @param config the configuration to use
     * @return the server administration URL for the specified scheme
     */
    public static String getAdminURL(SchemeType scheme, Configuration config) {
        String url = null;
        ServerConfiguration server = config.getServerConfiguration();

        if (scheme.equals(SchemeType.TCP)) {
            url = getAdminURL(scheme, server.getHost(),
                              config.getTcpConfiguration());
        } else if (scheme.equals(SchemeType.TCPS)) {
            url = getAdminURL(scheme, server.getHost(),
                              config.getTcpsConfiguration());
        } else if (scheme.equals(SchemeType.RMI)) {
            RmiConfiguration rmi = config.getRmiConfiguration();
            if (rmi.getEmbeddedRegistry()) {
                // if the registry is embedded within the OpenJMS server,
                // use the server host
                url = getAdminURL(scheme, server.getHost(), rmi);
            } else {
                url = getAdminURL(scheme, rmi.getRegistryHost(), rmi);
            }
        } else if (scheme.equals(SchemeType.HTTP)) {
            url = getAdminURL(scheme, config.getHttpConfiguration());
        } else if (scheme.equals(SchemeType.HTTPS)) {
            url = getAdminURL(scheme, config.getHttpsConfiguration());
        } else if (scheme.equals(SchemeType.EMBEDDED)) {
            url = "vm:openjms";
        }
        return url;
    }

    /**
     * Returns the server URL for the TCP/TCPS connector.
     *
     * @param scheme the connector scheme
     * @param host   the server host
     * @param config the TCP/TCPS configuration
     * @return the server URL for the TCP/TCPS connector
     */
    private static String getServerURL(SchemeType scheme, String host,
                                       TcpConfigurationType config) {
        return getURL(scheme, host, config.getInternalHost(), config.getPort());
    }

    /**
     * Returns the server URL for the RMI connector.
     *
     * @param scheme the connector scheme
     * @param host   the server host
     * @param config the RMI configuration
     * @return the server URL for the RMI connector
     */
    private static String getServerURL(SchemeType scheme, String host,
                                       RmiConfiguration config) {
        return getURL(scheme, host, config.getRegistryPort());
    }

    /**
     * Returns the server URL for the HTTP/HTTPS connector.
     *
     * @param scheme the connector scheme
     * @param config the HTTP/HTTPS configuration
     * @return the server URL for the HTTP/HTTPS connector
     */
    private static String getServerURL(SchemeType scheme,
                                       HttpConfigurationType config) {
        return getURL(scheme, config.getWebServerHost(),
                      config.getWebServerPort(), config.getServlet());
    }

    /**
     * Returns the embedded JNDI URL for the TCP/TCPS connector.
     *
     * @return the embedded JNDI URL for the TCP/TCPS connector
     */
    private static String getJndiURL(SchemeType scheme, String host,
                                     TcpConfigurationType config) {
        int port = config.getJndiPort();
        if (port == 0) {
            port = config.getPort();
        }
        return getURL(scheme, host, config.getInternalHost(), port);
    }

    /**
     * Returns the embedded JNDI URL for the RMI connector.
     *
     * @return the embedded JNDI URL for the RMI connector
     */
    private static String getJndiURL(SchemeType scheme, String host,
                                     RmiConfiguration config) {
        return getURL(scheme, host, config.getRegistryPort());
    }

    /**
     * Returns the embedded JNDI URL for the HTTP/HTTPS connector.
     *
     * @return the embedded JNDI URL for the HTTP/HTTPS connector
     */
    private static String getJndiURL(SchemeType scheme,
                                     HttpConfigurationType config) {
        return getURL(scheme, config.getWebServerHost(),
                      config.getWebServerPort(), config.getServlet());
    }

    /**
     * Returns the admin URL for the TCP/TCPS connector.
     *
     * @return the admin URL for the TCP/TCPS connector
     */
    private static String getAdminURL(SchemeType scheme, String host,
                                      TcpConfigurationType config) {
        int port = config.getAdminPort();
        if (port == 0) {
            port = config.getPort();
        }
        return getURL(scheme, host, config.getInternalHost(), port);
    }

    /**
     * Returns the admin URL for the RMI connector.
     *
     * @return the admin URL for the RMI connector
     */
    private static String getAdminURL(SchemeType scheme, String host,
                                      RmiConfiguration config) {
        return getURL(scheme, host, config.getRegistryPort());
    }

    /**
     * Returns the admin URL for the HTTP/HTTPS connector.
     *
     * @return the admin URL for the HTTP/HTTPS connector
     */
    private static String getAdminURL(SchemeType scheme,
                                      HttpConfigurationType config) {
        return getURL(scheme, config.getWebServerHost(),
                      config.getWebServerPort(), config.getServlet());
    }

    /**
     * Constructs a URL with no path.
     *
     * @param scheme the connector scheme
     * @param host   the host
     * @param port   the port
     * @return a URL formed from the concatenation of the arguments
     */
    private static String getURL(SchemeType scheme, String host, int port) {
        return getURL(scheme, host, port, "");
    }

    /**
     * Constructs a URL with a path.
     *
     * @param scheme the connector scheme
     * @param host   the host
     * @param port   the port
     * @param path   the path
     * @return a URL formed from the concatenation of the arguments
     */
    private static String getURL(SchemeType scheme, String host, int port,
                                 String path) {
        return getURL(scheme.toString(), host, port, path);
    }

    /**
     * Constructs a URL with a path.
     *
     * @param scheme the connector scheme
     * @param host   the host
     * @param port   the port
     * @param path   the path
     * @return a URL formed from the concatenation of the arguments
     */
    private static String getURL(String scheme, String host, int port,
                                 String path) {
        String result = scheme + "://" + getHost(host) + ":" + port;
        if (!path.startsWith("/")) {
            result += "/" + path;
        } else {
            result += path;
        }
        return result;
    }

    /**
     * Returns a URL with an alternative host encoded.
     *
     * @param scheme the connector scheme
     * @param host   the server host
     * @return the server URL for the TCP/TCPS connector
     * @param altHost the alternative host. May be <code>null</code>
     * @param port   the port
     * @return the URL
     */
    private static String getURL(SchemeType scheme, String host,
                                 String altHost, int port) {
        String url = getURL(scheme, host, port);
        if (altHost != null) {
            url += "?alt=" + altHost;
        }
        return url;
    }

    /**
     * Returns the host address, if the supplied host is localhost, else returns
     * it, unchanged.
     */
    private static String getHost(String host) {
        if (host.equals("localhost")) {
            try {
                host = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ignore) {
            }
        }
        return host;
    }

}
