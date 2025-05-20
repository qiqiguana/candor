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
 * $Id: JmsQueueBrowser.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Client implementation of the <code>javax.jms.QueueBrowser</code> interface.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 * @author      <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class JmsQueueBrowser
    extends JmsMessageConsumer
    implements QueueBrowser, Enumeration {

    /**
     * Caches a collection of messages, which are used during enumeration.
     */
    private LinkedList _messages = new LinkedList();

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(JmsQueueBrowser.class);


    /**
     * Construct a new <code>QueueBrowser</code>.
     *
     * @param session the session that created this instance
     * @param consumerId  the identity of this consumer
     * @param queue the queue to browse
     * @param selector the message selector. May be <code>null</code>
     */
    public JmsQueueBrowser(JmsSession session, long consumerId,
                           Queue queue, String selector) {
        super(session, consumerId, queue, selector);
    }

    /**
     * Returns the queue associated with this browser.
     *
     * @return the queue associated with this browser
     */
    public Queue getQueue() {
        return (Queue) getDestination();
    }

    /**
     * Returns an enumeration for browsing the current queue messages in the
     * order they would be received.
     *
     * @return an enumeration for browsing the messages
     */
    public Enumeration getEnumeration() {
        return this;
    }

    /**
     * Close this browser.
     *
     * @throws JMSException if the browser can't be closed
     */
    public void close() throws JMSException {
        super.close();
        if (_messages != null) {
            _messages.clear();
            _messages = null;
        }
    }

    /**
     * Handle asynchronous messages. It is invalid to call this method -
     * doing so results in a <code>RuntimeException</code>
     *
     * @param message the message received
     */
    public void onMessage(Message message) {
        throw new RuntimeException(
            "JmsQueueBrowsder.onMessage() has been called");
    }

    /**
     * Determines if there are more messages to browse.
     *
     * @return <code>true</code> if there are more messages to browse
     */
    public boolean hasMoreElements() {
        return !isEmpty();
    }

    /**
     * Returns the next message.
     *
     * @return the next message
     */
    public synchronized Object nextElement() {
        if (!isEmpty()) {
            return _messages.removeFirst();
        }

        return null;
    }

    /**
     * If there are no more messages on the server, bring across another
     * batch of them. If there are no more then return false.
     * <p>
     * Return a max of 20 at a time..although we should make it configurable
     *
     * @return <code>true</code> is empty; <code>false</code> oherwise
     */
    private boolean isEmpty() {
        final int count = 20;
        // check that the local cache is not empty first
        if (!_messages.isEmpty()) {
            return false;
        }

        // now try and retrieve a batch of messages from the server. If there
        // are no messages in place then return true otherwise retrieve the
        // messages, place them in the local cache and return not empty.
        List messages = null;
        try {
            messages = getSession().browse(getConsumerId(), count);
        } catch (JMSException exception) {
            _log.error("Error in JmsQueueBrowser.isEmpty", exception);
        }

        if (messages != null) {
            _messages.addAll(messages);
        }

        return _messages.isEmpty();
    }

}
