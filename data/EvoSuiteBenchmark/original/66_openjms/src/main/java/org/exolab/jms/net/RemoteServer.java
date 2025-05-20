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
 * Copyright 2007 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: RemoteServer.java,v 1.1 2008/01/07 13:00:17 tanderson Exp $
 */

package org.exolab.jms.net;

import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.ORBFactory;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.util.SSLUtil;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;


/**
 * Binds a service in an ORB.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2008/01/07 13:00:17 $
 */
public class RemoteServer {

    /**
     * Creates a new <tt>RemoteServer</tt>.
     *
     * @param name     the name to bind the service under
     * @param service  the service
     * @param uri      the export URI
     * @param routeURI the route URI. May be <tt>null</tt>
     * @throws RemoteException       if the server can't be created
     * @throws AlreadyBoundException if the service can't be bound in the
     *                               registry
     */
    public RemoteServer(String name, Object service, String uri,
                        String routeURI)
            throws AlreadyBoundException, IOException, RemoteException {
        Map properties = new HashMap();
        properties.put(ORB.PROVIDER_URI, uri);
        if (uri.startsWith("tcps")) {
            properties.putAll(
                    SSLUtil.getTCPSProperties("test.keystore", "secret"));
        } else if (uri.startsWith("https")) {
            properties.putAll(
                    SSLUtil.getHTTPSProperties("test.keystore", "secret"));
        }

        ORB orb = ORBFactory.createORB(properties);
        if (routeURI != null) {
            orb.addRoute(uri, routeURI);
        }
        Proxy proxy = orb.exportObject(service);
        orb.getRegistry().bind(name, proxy);
    }

    /**
     * Main line to start the server
     *
     * @param args the command line arguments
     * @throws Exception if the server can't be created
     */
    public static void main(String args[]) throws Exception {
        String name = args[0];
        String className = args[1];
        String uri = args[2];
        String routeURI = (args.length == 4) ? args[3] : null;

        System.out.println("Starting RemoteServer, uri=" + uri
                + ", name=" + name + ", className=" + className
                + ", routeURI=" + routeURI);

        Class clazz = Class.forName(className);
        Object service = clazz.newInstance();
        new RemoteServer(name, service, uri, routeURI);
    }
}
