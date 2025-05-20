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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsTemporaryTopic.java,v 1.2 2005/12/20 20:36:43 tanderson Exp $
 */
package org.exolab.jms.client;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.jms.JMSException;
import javax.jms.TemporaryTopic;
import javax.naming.Reference;
import javax.naming.Referenceable;

import org.exolab.jms.common.uuid.UUID;


/**
 * A temporary topic is created by a client through a session and has
 * a lifetime of the session's connection.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/12/20 20:36:43 $
 * @author      <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 */
public class JmsTemporaryTopic
    extends JmsTopic
    implements TemporaryTopic, JmsTemporaryDestination, Externalizable, Referenceable {

    /**
     * Used for serialization.
     */
    static final long serialVersionUID = 2;

    /**
     * Contains the id to the connection that created this temporary
     * destination. Note that the actual connection object is not
     * streamed across, only the identifier.
     */
    private long _connectionId;

    /**
     * Reference to the connection that owns this deztination.
     */
    transient private JmsConnection _connection = null;


    /**
     * Constructor provided for serialization.
     */
    public JmsTemporaryTopic() {
    }

    /**
     * Construct a new <code>JmsTemporaryQueue</code>.
     *
     * @param connection the owning connection
     */
    private JmsTemporaryTopic(JmsConnection connection) {
        super(TEMP_TOPIC_PREFIX + UUID.next());
        _connection = connection;
        _connectionId = _connection.getConnectionId();
    }

    // implementation of TemporaryTopic.delete
    public void delete()
        throws JMSException {
        // unregisterthe temporary topic from the owning destination
        _connection.deleteTemporaryDestination(this);
    }

    // implementation of JmsTemporaryDestination.getOwningConnection
    public JmsConnection getOwningConnection() {
        return _connection;
    }

    // implementation of JmsTemporaryDestination.getConnectionId
    public long getConnectionId() {
        return _connectionId;
    }

    // implementation of JmsTemporaryDestination.validForConnection
    public boolean validForConnection(JmsConnection connection) {
        boolean result = false;

        if (connection != null
            && connection.getConnectionId() == _connectionId) {
            result = true;
        }

        return result;
    }

    // implementation of Referenceable.getReference
    public Reference getReference() {
        // should never be called for temp destination
        return null;
    }

    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput stream)
        throws IOException {
        stream.writeLong(serialVersionUID);
        stream.writeLong(_connectionId);
        super.writeExternal(stream);
    }

    // implementation of Externalizable.writeExternal
    public void readExternal(ObjectInput stream)
        throws IOException, ClassNotFoundException {
        long version = stream.readLong();
        if (version == serialVersionUID) {
            _connectionId = stream.readLong();
            super.readExternal(stream);
        } else {
            throw new IOException("JmsTemporaryTopic with version " +
                version + " is not supported.");
        }
    }

    /**
     * Construct a new temporary topic.
     *
     * @param connection the connection owns that the topic
     * @return a new temporary topic
     */
    public static TemporaryTopic create(JmsConnection connection) {
        return new JmsTemporaryTopic(connection);
    }

}
