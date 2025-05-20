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
 * Copyright 2001,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsDestinationFactory.java,v 1.1 2004/11/26 01:50:40 tanderson Exp $
 *
 * Date         Author  Changes
 * 06/14/2001   jima    Created
 */
package org.exolab.jms.client;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;


/**
 * This factory object implements the ObjectFactory interface and is used
 * to resolve a reference to an administered destination. This is part
 * of our JNDI support.
 * <p>
 * All {@link JmsDestination} objects support the java.naming.Referenceable
 * interface and make explicit reference to this factory
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:40 $
 * @author      <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @see         JmsTopic
 * @see         JmsQueue
 */
public class JmsDestinationFactory
    implements ObjectFactory {

    // implementation of ObjectFactory.getObjectInstance
    public Object getObjectInstance(Object object, Name name, Context context,
                                    Hashtable env) throws Exception {

        Object result = null;

        if (object instanceof Reference) {
            Reference ref = (Reference) object;
            String className = ref.getClassName();
            StringRefAddr nameref = (StringRefAddr) ref.get("name");
            StringRefAddr persistref = (StringRefAddr) ref.get("persistent");

            if (nameref != null && persistref != null) {
                String destination = (String) nameref.getContent();
                String persist = (String) persistref.getContent();

                if (className.equals(JmsQueue.class.getName())) {
                    JmsQueue queue = new JmsQueue(destination);
                    queue.setPersistent(new Boolean(persist).booleanValue());
                    result = queue;
                } else if (className.equals(JmsTopic.class.getName())) {
                    JmsTopic topic = new JmsTopic(destination);
                    topic.setPersistent(new Boolean(persist).booleanValue());
                    result = topic;
                } else {
                    throw new Exception(
                        "This factory cannot create objects of type "
                        + className);
                }
            }
        }

        return result;
    }
}
