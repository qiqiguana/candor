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
 */
package org.exolab.jms.messagemgr;

import java.sql.Connection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.service.Service;
import org.exolab.jms.service.ServiceException;


/**
 * This is the active message handling component within the JMS server. Messages
 * are passed in and added to the appropriate dispatchers for delivery to the
 * clients.
 *
 * @author <a href="mailto:mourikis@intalio.com">Jim Mourikis</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2005/11/12 12:27:40 $
 */
public class MessageMgr extends Service implements MessageManager {

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The destination manager.
     */
    private DestinationManager _destinations;

    /**
     * A map of <code>MessageHanagerEventListener instances, keyed on
     * <code>JmsDestination</ocde>.
     */
    private Map _listeners = Collections.synchronizedMap(new HashMap(1023));

    /**
     * The seed to allocate to messages to differentiate messages arriving at
     * the same time.
     */
    private long _sequenceNoSeed = 0;

    /**
     * Lock for accessing _sequenceNoSeed.
     */
    private final Object _lock = new Object();

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(MessageMgr.class);


    /**
     * Construct a new <code>MessageMgr</code>.
     *
     * @param database the database service
     */
    public MessageMgr(DatabaseService database) {
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        _database = database;
    }

    /**
     * Sets the destination manager.
     *
     * @param manager the destination manager
     */
    public void setDestinationManager(DestinationManager manager) {
        _destinations = manager;
    }

    /**
     * Prepares a message prior to it being passed through the system.
     *
     * @param message the message
     * @throws JMSException if the message is invalid or cannot be prep'ed
     */
    public void prepare(MessageImpl message)
            throws JMSException {
        if (message == null) {
            throw new JMSException("Null message");
        }
        Destination destination = message.getJMSDestination();
        if (destination == null) {
            throw new InvalidDestinationException("Message has no destination");
        }
        if (!(destination instanceof JmsDestination)) {
            throw new InvalidDestinationException(
                    "Destination not a JmsDestination");
        }

        // mark the message as accepted and attach a sequence number
        message.setAcceptedTime((new Date()).getTime());
        message.setSequenceNumber(getNextSequenceNumber());
        message.setReadOnly(true);
    }

    /**
     * Add a message.
     *
     * @param message the message to add
     * @throws JMSException if the message cannot be added
     */
    public void add(MessageImpl message) throws JMSException {
        prepare(message);

        JmsDestination destination =
                (JmsDestination) message.getJMSDestination();
        final JmsDestination existing
                = _destinations.getDestination(destination.getName());
        final boolean persistent = (existing != null)
                ? existing.getPersistent() : false;

        try {
            _database.begin(); // need a transaction for any database access

            // if the message's delivery mode is PERSISTENT, and the destination
            // is also persistent, then then process it accordingly, otherwise use
            // the non-persistent quality of service
            if (message.getJMSDeliveryMode() == DeliveryMode.PERSISTENT
                    && persistent) {
                addPersistentMessage(message);
            } else {
                addNonPersistentMessage(message);
            }
            _database.commit();
        } catch (Exception exception) {
            final String msg = "Failed to process message";
            _log.error(msg, exception);
            try {
                if (_database.isTransacted()) {
                    _database.rollback();
                }
            } catch (PersistenceException error) {
                _log.error(error, error);
            }
            if (exception instanceof JMSException) {
                throw (JMSException) exception;
            }
            throw new JMSException(msg + ": " + exception.getMessage());
        }
    }

    /**
     * Register a listener for a specific destination, to be notified when
     * messages for the destination arrive.
     *
     * @param destination the destination to register the listener for
     * @param listener    the listener to notify
     */
    public void addEventListener(JmsDestination destination,
                                 MessageManagerEventListener listener) {
        _listeners.put(destination, listener);
    }

    /**
     * Remove the listener for the specified destination.
     *
     * @param destination the destination to remove the listener for
     */
    public void removeEventListener(JmsDestination destination) {
        _listeners.remove(destination);
    }

    /**
     * Start the service.
     *
     * @throws ServiceException if the service fails to start
     */
    protected void doStart() throws ServiceException {
        if (_destinations == null) {
            throw new ServiceException(
                    "Cannot start service: DestinationManager not initialised");
        }
    }

    /**
     * Processes a non-persistent message.
     *
     * @param message the message to add
     * @throws JMSException if the message cannot be processed
     */
    private void addNonPersistentMessage(MessageImpl message)
            throws JMSException {

        // notify the listener for the destination
        JmsDestination destination
                = (JmsDestination) message.getJMSDestination();

        MessageManagerEventListener listener = getEventListener(destination);
        listener.messageAdded(destination, message);
    }

    /**
     * Stop the service.
     *
     * @throws ServiceException if the service fails to stop
     */
    protected void doStop() throws ServiceException {
        _listeners.clear();
    }

    /**
     * Add a persistent message.
     *
     * @param message the message to add
     * @throws JMSException if the message cannot be processed
     * @throws PersistenceException for any persistence error
     */
    private void addPersistentMessage(MessageImpl message)
            throws JMSException, PersistenceException {
        JmsDestination destination =
                (JmsDestination) message.getJMSDestination();

        Connection connection = _database.getConnection();

            // add the message to the database
        _database.getAdapter().addMessage(connection, message);

        // notify the listener that a persistent message has arrived
        MessageManagerEventListener listener = getEventListener(destination);
        listener.persistentMessageAdded(destination, message);
    }

    /**
     * Returns the event listener for the specified destination.
     * <p/>
     * If no event listener is registered, it falls back to the destination
     * manager.
     *
     * @param destination the destination
     * @return the event listener registgered for <code>destination</code>, or
     *         the destination manager if no is registered
     */
    private MessageManagerEventListener getEventListener(
            JmsDestination destination) {
        MessageManagerEventListener listener =
                (MessageManagerEventListener) _listeners.get(destination);

        if (listener == null) {
            // no registered destination cache, so let the destination manager
            // handle it
            listener = _destinations;
        }
        return listener;
    }

    /**
     * Returns the next seed value to be allocated to a new message.
     *
     * @return a unique identifier for a message
     */
    private long getNextSequenceNumber() {
        synchronized (_lock) {
            return ++_sequenceNoSeed;
        }
    }

}
