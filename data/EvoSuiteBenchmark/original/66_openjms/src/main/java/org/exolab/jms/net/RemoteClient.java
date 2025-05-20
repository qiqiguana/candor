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
 * $Id: RemoteClient.java,v 1.1 2008/01/07 13:00:17 tanderson Exp $
 */

package org.exolab.jms.net;

import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.ORBFactory;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.registry.Registry;
import org.exolab.jms.net.util.SSLUtil;

import java.lang.reflect.Constructor;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;


/**
 * Helper to instantiate an Object given its class name, passing a Proxy
 * instance obtained from the ORB registry for the specified URI.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $LastChangedDate: 2006-05-02 05:16:31Z $
 */
public class RemoteClient {

    /**
     * Creates a new <tt>RemoteClient</tt>.
     *
     * @param uri    the URI to connect to
     * @param name   the service name
     * @param client the client class
     * @throws Exception for any error
     */
    public RemoteClient(String uri, String name, Class client)
            throws RemoteException, NotBoundException, Exception {
        ORB orb = ORBFactory.createORB(uri);
        Map properties = new HashMap();
        properties.put(ORB.PROVIDER_URI, uri);
        if (uri.startsWith("tcps")) {
            properties.putAll(
                    SSLUtil.getTCPSProperties("test.keystore", "secret"));
        } else if (uri.startsWith("https")) {
            properties.putAll(
                    SSLUtil.getHTTPSProperties("test.keystore", "secret"));
        }
        Registry registry = orb.getRegistry(properties);
        Proxy proxy = registry.lookup(name);
        Constructor ctor = client.getConstructor(new Class[]{Proxy.class});
        ctor.newInstance(new Object[]{proxy});
    }

    /**
     * Main line to start the client.
     *
     * @param args the command line arguments
     * @throws Exception if the server can't be created
     */
    public static void main(String args[]) throws Exception {
        String uri = args[0];
        String name = args[1];
        String className = args[2];
        System.out.println("Starting RemoteClient, uri=" + uri
                + ", name=" + name + ", className=" + className);

        Class client = Class.forName(className);
        new RemoteClient(uri, name, client);
    }

}
