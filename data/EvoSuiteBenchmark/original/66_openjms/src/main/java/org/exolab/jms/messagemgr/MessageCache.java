package org.exolab.jms.messagemgr;

import javax.jms.JMSException;

import org.exolab.jms.message.MessageImpl;


/**
 * MessageCache is responsible for managing a collection of messages. Messages
 * in the cache are referenced via {@link MessageRef} instances
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/08/30 07:26:49 $
 */
public interface MessageCache {

    /**
     * Add a reference and its corresponding message to the cache
     *
     * @param reference the reference to the message
     * @param message   the message
     */
    void addMessage(MessageRef reference, MessageImpl message);

    /**
     * Adds a message reference to the cache
     *
     * @param reference the message reference to add
     */
    void addMessageRef(MessageRef reference);

    /**
     * Returns a message reference, given its identifier
     *
     * @param messageId the message identifier
     * @return the message reference associated with <code>messageId</code>, or
     *         <code>null</code>  if none exists
     */
    MessageRef getMessageRef(String messageId);

    /**
     * Returns the message corresponding to the specified reference
     *
     * @param reference the message reference
     * @return the associated message, or <code>null</code> if none exists
     * @throws JMSException for any error
     */
    MessageImpl getMessage(MessageRef reference)
            throws JMSException;

    /**
     * Destroys the message corresponding to the reference
     *
     * @throws JMSException for any error
     */
    void destroy(MessageRef reference) throws JMSException;

}
