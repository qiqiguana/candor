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
 * Copyright 2001-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsConnectionFactoryBuilder.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;


/**
 * Implementation of the {@link ObjectFactory} interface that creates {@link
 * JmsConnectionFactory} instances given a corresponding {@link Reference}
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 * @see JmsConnectionFactory
 * @see JmsXAConnectionFactory
 */
public class JmsConnectionFactoryBuilder
        implements ObjectFactory {

    /**
     * Creates an object using the location or reference information specified.
     * This only constructs
     *
     * @param object      the object containing location or reference
     *                    information that can be used in creating an object.
     *                    May be <code>null</code>
     * @param name        the name of this object relative to <code>context</code>,
     *                    or <code>null</code> if no name is specified.
     * @param context     the context relative to which the <code>name</code>
     *                    parameter is specified, or <code>null</code> if
     *                    <code>name</code> is relative to the default initial
     *                    context.
     * @param environment the environment used in creating the object. May be
     *                    <code>null</code>
     * @return the object created oird <code>null</code> if an object can't be
     *         created.
     * @throws Exception if this object factory encountered an exception while
     *                   attempting to create an object, and no other object
     *                   factories are to be tried.
     */
    public Object getObjectInstance(Object object, Name name, Context context,
                                    Hashtable environment) throws Exception {
        Object result = null;

        if (object instanceof Reference) {
            Reference ref = (Reference) object;
            String clazz = ref.getClassName();

            if (clazz.equals(JmsConnectionFactory.class.getName())
                    || clazz.equals(JmsXAConnectionFactory.class.getName())) {

                StringRefAddr serverClass =
                        (StringRefAddr) ref.get("serverClass");
                String serverClassName = (String) serverClass.getContent();
                

                // get a list of string properties and store them in a
                // hashtable
                HashMap properties = new HashMap();
                Enumeration iter = ref.getAll();
                while (iter.hasMoreElements()) {
                    StringRefAddr addr = (StringRefAddr) iter.nextElement();
                    properties.put(addr.getType(), addr.getContent());
                }

                // create the factory
                if (clazz.equals(JmsConnectionFactory.class.getName())) {
                    result = new JmsConnectionFactory(serverClassName,
                                                      properties, environment);
                } else {
                    result = new JmsXAConnectionFactory(serverClassName,
                                                        properties,
                                                        environment);
                }
            }
        }
        return result;
    }
}

