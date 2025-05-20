package org.exolab.jms.messagemgr;

import javax.jms.JMSException;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.message.MessageImpl;


/**
 * Enter description here.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/08/30 07:26:49 $
 */
public interface MessageManager {

    /**
     * Prepares a message prior to it being passed through the system.
     *
     * @param message the message
     * @throws JMSException if the message is invalid or cannot be prep'ed
     */
    void prepare(MessageImpl message) throws JMSException;

    /**
     * Add a message.
     *
     * @param message the message to add
     * @throws JMSException if the message cannot be added
     */
    void add(MessageImpl message) throws JMSException;

    /**
     * Register a listener for a specific destination, to be notified when
     * messages for the destination arrive.
     *
     * @param destination the destination to register the listener for
     * @param listener    the listener to notify
     */
    void addEventListener(JmsDestination destination,
                          MessageManagerEventListener listener);

    /**
     * Remove the listener for the specified destination.
     *
     * @param destination the destination to remove the listener for
     */
    void removeEventListener(JmsDestination destination);

}
