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
 * Copyright 2001-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: DefaultMessageCache.java,v 1.5 2007/01/24 12:00:28 tanderson Exp $
 *
 * Date         Author  Changes
 * 3/1/2001     jima    Created
 */
package org.exolab.jms.messagemgr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceAdapter;
import org.exolab.jms.persistence.PersistenceException;

import javax.jms.JMSException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


/**
 * Default implementation of the {@link MessageCache} interface.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2007/01/24 12:00:28 $
 */
final class DefaultMessageCache implements MessageCache {

    /**
     * Maintains the pool of transient messages.
     */
    private final Map _transient = new HashMap(1023);

    /**
     * Maintains the pool of persistent messages.
     */
    private final Map _persistent = new HashMap(1023);

    /**
     * Maintains the references to messages.
     */
    private final Map _references = new HashMap(1023);

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(
            DefaultMessageCache.class);


    /**
     * Add a reference and its corresponding message to the cache.
     *
     * @param reference the reference to the message
     * @param message   the message
     */
    public synchronized void addMessage(MessageRef reference,
                                        MessageImpl message) {
        String messageId = reference.getMessageId();
        if (reference.isPersistent()) {
            _persistent.put(messageId, message);
        } else {
            _transient.put(messageId, message);
        }
        addMessageRef(messageId, reference);
    }

    /**
     * Adds a message reference to the cache.
     *
     * @param reference the message reference to add
     */
    public synchronized void addMessageRef(MessageRef reference) {
        addMessageRef(reference.getMessageId(), reference);
    }

    /**
     * Returns a message reference, given its identifier.
     *
     * @param messageId the message identifier
     * @return the message reference associated with <code>messageId</code>, or
     *         <code>null</code>  if none exists
     */
    public synchronized MessageRef getMessageRef(String messageId) {
        return (MessageRef) _references.get(messageId);
    }

    /**
     * Returns the message corresponding to the specified reference.
     *
     * @param reference the message reference
     * @return the associated message, or <code>null</code> if none exists
     * @throws JMSException for any error
     */
    public synchronized MessageImpl getMessage(MessageRef reference)
            throws JMSException {
        MessageImpl message;
        final String messageId = reference.getMessageId();

        if (reference.isPersistent()) {
            message = (MessageImpl) _persistent.get(messageId);

            // if the message is not cached then try and retrieve it from the
            // database and cache it.
            if (message == null) {
                // fault in at least the next message from the database
                try {
                    DatabaseService service = DatabaseService.getInstance();
                    PersistenceAdapter adapter = service.getAdapter();
                    Connection connection = service.getConnection();
                    message = adapter.getMessage(connection, messageId);
                } catch (PersistenceException exception) {
                    final String msg = "Failed to retrieve message";
                    _log.error(msg, exception);
                    throw new JMSException(msg + ": " + exception.getMessage());
                }
                // add the message to the persistent cache once it has been
                // retrieved from the datastore
                if (message != null) {
                    _persistent.put(messageId, message);
                }
            }
        } else {
            message = (MessageImpl) _transient.get(messageId);
        }

        if (message != null && !message.getReadOnly()) {
            // mark the message as read-only
            message.setReadOnly(true);
        }

        return message;
    }

    /**
     * Destroys the message corresponding to the reference.
     *
     * @throws JMSException for any error
     */
    public synchronized void destroy(MessageRef reference) throws JMSException {
        final String messageId = reference.getMessageId();
        if (_references.remove(messageId) != null) {
            if (reference.isPersistent()) {
                try {
                    DatabaseService service = DatabaseService.getInstance();
                    Connection connection = service.getConnection();
                    PersistenceAdapter adapter = service.getAdapter();
                    adapter.removeMessage(connection, messageId);
                } catch (Exception exception) {
                    _log.error("Failed to remove message", exception);
                    throw new JMSException("Failed to remove message: "
                            + exception.getMessage());
                }
                _persistent.remove(messageId);
            } else {
                _transient.remove(messageId);
            }
        }
    }

    /**
     * Clear the persistent and non-persistent message cache.
     */
    public synchronized void clear() {
        _transient.clear();
        _persistent.clear();
        _references.clear();
    }

    /**
     * Clear only the persistent messages in the cache.
     */
    public synchronized void clearPersistentMessages() {
        _persistent.clear();
    }

    /**
     * Return the number of messages in the transient cache.
     *
     * @return the number of messages in the transient cache
     */
    public synchronized int getTransientCount() {
        return _transient.size();
    }

    /**
     * Return the number of messages in the persistent cache.
     *
     * @return the number of messages in the persistent cache
     */
    public synchronized int getPersistentCount() {
        return _persistent.size();
    }

    /**
     * Return the number of message references in the cache.
     *
     * @return the number of message references in the cache
     */
    public synchronized int getMessageCount() {
        return _references.size();
    }

    /**
     * Add a message reference to the cache.
     *
     * @param messageId the message identifier
     * @param reference the message reference
     */
    private void addMessageRef(String messageId, MessageRef reference) {
        _references.put(messageId, reference);
    }

}

