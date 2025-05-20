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
 * Copyright 2000-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsDestination.java,v 1.1 2004/11/26 01:50:40 tanderson Exp $
 *
 * Date         Author  Changes
 * 3/21/2000    jima    Created
 */
package org.exolab.jms.client;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.naming.Referenceable;

import org.exolab.jms.message.DestinationImpl;


/**
 * This is the base class for all destinations.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:40 $
 * @author      <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 */
abstract public class JmsDestination
    extends DestinationImpl
    implements Destination, Externalizable, Referenceable {

    /**
     * Need a default constructor for the serialization
     */
    public JmsDestination() {
    }

    /**
     * Instantiate an instance of this object with the specified string
     *
     * @param       name            name of the destination
     */
    protected JmsDestination(String name) {
        super(name);
    }

    /**
     * Return the name of the destination
     *
     * @return      String
     */
    public String getName() {
        return getDestination();
    }

    /**
     * Determine whether the destination is persistent or not
     *
     * @param       flag            true for persistent
     */
    public void setPersistent(boolean flag) {
        persistent_ = flag;
    }

    /**
     * Return the persistent state of this destination
     *
     * @return      boolean
     */
    public boolean getPersistent() {
        return persistent_;
    }

    // implementation of Object.toString()
    public String toString() {
        return getDestination() + "-" + persistent_;
    }

    // implementation of Object.hashCode
    public int hashCode() {
        return getName().hashCode();
    }

    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput stream)
        throws IOException {
        stream.writeLong(serialVersionUID);
        stream.writeBoolean(persistent_);
        super.writeExternal(stream);
    }

    // implementation of Externalizable.writeExternal
    public void readExternal(ObjectInput stream)
        throws IOException, ClassNotFoundException {
        long version = stream.readLong();
        if (version == serialVersionUID) {
            persistent_ = stream.readBoolean();
            super.readExternal(stream);
        } else {
            throw new IOException("JmsDestination with version " +
                version + " is not supported.");
        }
    }

    /**
     * This static method determines whether a particular string
     * refers to a temporary destination.
     *
     * @param       destination         destination to test
     * @return      boolean             true if it is
     */
    public boolean isTemporaryDestination() {
        boolean result = false;

        if ((getDestination().startsWith(TEMP_QUEUE_PREFIX)) ||
            (getDestination().startsWith(TEMP_TOPIC_PREFIX))) {
            result = true;
        }

        return result;
    }

    /**
     * This static method determines whether a particular DestinationImpl
     * instance refers to a temporary destination.
     *
     * @param       destination         destination to test
     * @return      boolean             true if it is
     */
    public static boolean isTemporaryDestination(DestinationImpl destination) {
        boolean result = false;

        if ((destination.getDestination().startsWith(TEMP_QUEUE_PREFIX)) ||
            (destination.getDestination().startsWith(TEMP_TOPIC_PREFIX))) {
            result = true;
        }

        return result;
    }


    /**
     * This flag determines whether or not the destination is persistent or
     * not
     */
    private boolean persistent_ = false;

    /**
     * This is the prefix used by the temporary queues
     */
    final static String TEMP_QUEUE_PREFIX = "tempqueue:";

    /**
     * This is the prefix used by the temporary topics
     */
    final static String TEMP_TOPIC_PREFIX = "temptopic:";

    /**
     * Used for serialization
     */
    static final long serialVersionUID = 1;
}

