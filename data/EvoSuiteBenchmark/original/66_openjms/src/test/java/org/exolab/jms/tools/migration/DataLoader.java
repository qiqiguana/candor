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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: DataLoader.java,v 1.2 2005/11/12 12:52:06 tanderson Exp $
 */
package org.exolab.jms.tools.migration;

import java.io.InputStream;
import java.sql.Connection;
import javax.jms.JMSException;
import javax.jms.DeliveryMode;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.ConfigurationReader;
import org.exolab.jms.message.*;
import org.exolab.jms.messagemgr.MessageHandle;
import org.exolab.jms.messagemgr.PersistentMessageHandle;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceAdapter;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.service.ServiceException;
import org.exolab.jms.tools.db.DBTool;
import org.exolab.jms.authentication.User;


/**
 * Loads up the master database with data, for testing purposes.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/12 12:52:06 $
 * @see ExportImportTest
 */
public class DataLoader {

    /**
     * The database service.
     */
    private final DatabaseService _service;
    
    /**
     * The persistence adapter.
     */
    private final PersistenceAdapter _adapter;

    /**
     * The connection to use.
     */
    private final Connection _connection;

    /**
     * JMSTimestamp seed.
     */
    private long _timestampSeed = 0;

    /**
     * JMSPriority seed.
     */
    private int _prioritySeed = 0;

    private final JmsTopic _topic1 = new JmsTopic("topic1");
    private final JmsQueue _queue1 = new JmsQueue("queue1");


    /**
     * Construct a new <code>DataLoader</code>.
     *
     * @param config the configuration to use
     * @throws ServiceException for any error
     */
    public DataLoader(Configuration config) throws ServiceException {
        DBTool tool = new DBTool(config);
        tool.drop();
        tool.create();

        _service = new DatabaseService(config);
        _service.start();
        _adapter = _service.getAdapter();
        _connection = _service.getConnection();
        _topic1.setPersistent(true);
        _queue1.setPersistent(true);
    }

    /**
     * Load the database with data.
     *
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     * @throws ServiceException     for any service error
     */
    public void load() throws JMSException, PersistenceException,
                              ServiceException{
        addDestination(_queue1);
        addDestination(_topic1);

        final String body = "Hello world!";
        BytesMessageImpl bytes = new BytesMessageImpl();
        bytes.writeUTF(body);

        MapMessageImpl map = new MapMessageImpl();
        map.setString("key", body);

        MessageImpl message = new MessageImpl();

        ObjectMessageImpl object = new ObjectMessageImpl();
        object.setObject(body);

        StreamMessageImpl stream = new StreamMessageImpl();
        stream.writeString(body);

        TextMessageImpl text = new TextMessageImpl();
        text.setText(body);

        MessageImpl[] messages = new MessageImpl[]{
            bytes, map, message, object,
            stream, text
        };
        _adapter.addDurableConsumer(_connection, _topic1.getName(), "sub1");


        for (int i = 0; i < messages.length; ++i) {
            MessageImpl msg = messages[i];
            addMessage(msg, _queue1);
            MessageHandle handle = new PersistentMessageHandle(msg,
                                                               _queue1.getName());
            _adapter.addMessageHandle(_connection, handle);

            addMessage(msg, _topic1);
            handle = new PersistentMessageHandle(msg, "sub1");
            _adapter.addMessageHandle(_connection, handle);
        }

        addUser("admin", "openjms");
        addUser("user1", "secret");

        _service.commit();
        _service.stop();
    }

    public static void main(String[] args) throws Exception {
        InputStream stream = DataLoader.class.getResourceAsStream(
                "/openjmstest.xml");
        Configuration config = ConfigurationReader.read(stream);
        DataLoader loader = new DataLoader(config);
        loader.load();
    }

    /**
     * Add a destination to the database.
     *
     * @param destination the destination to add
     * @throws PersistenceException for any error
     */
    private void addDestination(JmsDestination destination)
            throws PersistenceException {
        boolean queue = false;
        if (destination instanceof JmsQueue) {
            queue = true;
        }
        _adapter.addDestination(_connection, destination.getName(), queue);
    }

    /**
     * Add a message to the database.
     *
     * @param message     the message to add
     * @param destination the message's destination
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    private void addMessage(MessageImpl message, JmsDestination destination)
            throws JMSException, PersistenceException {
        message.setJMSMessageID(MessageId.create());
        message.setJMSDestination(destination);
        message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
        message.setJMSTimestamp(_timestampSeed);
        message.setJMSPriority(_prioritySeed);
        _adapter.addMessage(_connection, message);

        ++_timestampSeed;
        _prioritySeed = (_prioritySeed + 1) % 10;
    }

    /**
     * Add an user to the database.
     *
     * @param user the user name
     * @param password the user's password
     * @throws PersistenceException for any error
     */
    private void addUser(String user, String password)
            throws PersistenceException {
        _adapter.addUser(_connection, new User(user, password));
    }
}
