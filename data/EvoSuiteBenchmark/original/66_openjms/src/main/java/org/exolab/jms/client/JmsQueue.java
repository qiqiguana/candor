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
 * Copyright 2000-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsQueue.java,v 1.1 2004/11/26 01:50:40 tanderson Exp $
 */
package org.exolab.jms.client;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.Reference;
import javax.naming.StringRefAddr;


/**
 * This object represents a queue, which is a type of destination. A queue
 * is identified by name and two queues with the same name refer to the
 * same object.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:40 $
 * @author      <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 */
public class JmsQueue
    extends JmsDestination
    implements Queue, Externalizable {

    /**
     * Used for serialization
     */
    static final long serialVersionUID = 1;


    /**
     * Need a default constructor for the serialization
     */
    public JmsQueue() {
    }

    /**
     * Instantiate an instance of a queue name
     *
     * @param       name        queue name
     */
    public JmsQueue(String name) {
        super(name);
    }

    /**
     * Return the name of the queue
     *
     * @return      name        name of  the queue
     * @exception   JMSException
     */
    public String getQueueName()
        throws JMSException {
        return getName();
    }


    // implementation of Object.equals(Object)
    public boolean equals(Object object) {
        boolean result = false;

        if ((object instanceof JmsQueue)
            && (((JmsQueue) object).getName().equals(this.getName()))) {
            result = true;
        }

        return result;
    }

    // implementation of Object.hashCode
    public int hashCode() {
        return getName().hashCode();
    }

    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput stream)
        throws IOException {
        stream.writeLong(serialVersionUID);
        super.writeExternal(stream);
    }

    // implementation of Externalizable.writeExternal
    public void readExternal(ObjectInput stream)
        throws IOException, ClassNotFoundException {
        long version = stream.readLong();
        if (version == serialVersionUID) {
            super.readExternal(stream);
        } else {
            throw new IOException("JmsQueue with version "
                                  + version + " is not supported.");
        }
    }

    /**
     * Retrieves the <code>Reference</code> of this object
     *
     * @return the non-null <code>Reference</code> of this object
     */
    public Reference getReference() {
        Reference reference = new Reference(
            JmsQueue.class.getName(), new StringRefAddr("name", getName()),
            JmsDestinationFactory.class.getName(), null);

        // add the persistence attribute
        reference.add(new StringRefAddr("persistent",
                                        (getPersistent() ? "true" : "false")));

        return reference;
    }
}
