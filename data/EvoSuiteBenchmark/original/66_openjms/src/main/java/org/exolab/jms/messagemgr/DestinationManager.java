package org.exolab.jms.messagemgr;

import java.util.List;
import java.util.Map;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.gc.GarbageCollectable;


/**
 * <code>DestinationManager</code> is responsible for creating and managing the
 * lifecycle of {@link DestinationCache} objects. The destination manager is
 * also responsible for managing messages that are received by the message
 * manager, which do not have any registered {@link DestinationCache}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/11/12 10:58:40 $
 */
public interface DestinationManager
        extends MessageManagerEventListener, GarbageCollectable {

    /**
     * Returns the cache for the supplied destination.
     * <p/>
     * If the cache doesn't exist, it will be created, and any registered {@link
     * DestinationEventListener}s will be notified.
     *
     * @param destination the destination of the cache to return
     * @return the cache associated with <code>destination</code>
     * @throws InvalidDestinationException if <code>destination</code> doesn't
     *                                     exist
     * @throws JMSException                if the cache can't be created
     */
    DestinationCache getDestinationCache(JmsDestination destination)
            throws JMSException;

    /**
     * Returns a destination given its name.
     *
     * @param name the name of the destination
     * @return the destination corresponding to <code>name</code> or
     *         <code>null</code> if none exists
     */
    JmsDestination getDestination(String name);

    /**
     * Create a destination.
     * <p/>
     * Any registered {@link DestinationEventListener}s will be notified.
     *
     * @param destination the destination to create
     * @throws InvalidDestinationException if the destination already exists or
     *                                     is a wildcard destination
     * @throws JMSException                if the destination can't be created
     */
    void createDestination(JmsDestination destination) throws JMSException;

    /**
     * Remove a destination.
     * <p/>
     * All messages and durable consumers will be removed. Any registered {@link
     * DestinationEventListener}s will be notified.
     *
     * @param destination the destination to remove
     * @throws InvalidDestinationException if the destination is invalid.
     * @throws JMSException                if the destination can't be removed
     */
    void removeDestination(JmsDestination destination) throws JMSException;

    /**
     * Returns all destinations.
     *
     * @return a list of JmsDestination instances.
     * @throws JMSException for any JMS error
     */
    List getDestinations() throws JMSException;

    /**
     * Returns a map of all destinations that match the specified topic.
     * <p/>
     * If the topic represents a wildcard then it may match none, one or more
     * destinations.
     *
     * @param topic the topic
     * @return a map of topics to DestinationCache instances
     */
    Map getTopicDestinationCaches(JmsTopic topic);

    /**
     * Register an event listener to be notified when destinations are created
     * and destroyed.
     *
     * @param listener the listener to add
     */
    void addDestinationEventListener(DestinationEventListener listener);

    /**
     * Remove an event listener.
     *
     * @param listener the listener to remove
     */
    void removeDestinationEventListener(DestinationEventListener listener);

}
