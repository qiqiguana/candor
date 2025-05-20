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
 * $Id: BasicEventManager.java,v 1.4 2006/02/23 11:17:38 tanderson Exp $
 */
package org.exolab.jms.events;

import java.util.Comparator;
import java.util.HashMap;

import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;
import org.exolab.jms.common.threads.ThreadPoolFactory;
import org.exolab.jms.common.util.OrderedQueue;
import org.exolab.jms.service.BasicService;


/**
 * The EventManager manages {@link Event} objects. It has methods to register
 * and unregister events. It also extends {@link Runnable} interface which
 * defines the thread responsible for dispatching events.
 * <p/>
 * An event is defined to occur at sometime in the future, as specified either
 * by an absolute time through {@link #registerEvent} or as relative time
 * through {@link #registerEventRelative}. An event must have an associated
 * event type and may have an attached <code>Serializable</code>, which is used
 * when the EventManager makes a callback to the registered handler when the
 * event fires.
 * <p/>
 * The register methids will return an event identifier which can subsequently
 * be used to unregister the event through the {@link #unregisterEvent} event.
 * This is the only means of unregister an event.
 * <p/>
 * If the {@link Event} object is incorrectly specified then the {@link
 * IllegalEventDefinedException} exception is raised.
 * <p/>
 * When an event fires the {@link EventManager} is responsible for ensuring that
 * the event handler is notified. If the event handler has since been removed
 * then the EventManager must gracefully abort the delivery and continue
 * processing the next event.
 * <p/>
 * Objects of type {@link Event} need to survive subsequent {@link EventManager}
 * restarts, as such they must be persisted, which implies that the {@link
 * EventHandler} needs to also be persisted. The ability to store the {@link
 * EventHandler} as a <code>HandleIfc</code> object which can later be resolved
 * to an object will be required.
 *
 * @author <a href="mailto:wood@intalio.com">Chris Wood</a>
 * @version $Revision: 1.4 $ $Date: 2006/02/23 11:17:38 $
 */
public class BasicEventManager
        extends BasicService
        implements EventManager {

    // The max number of threads for this pool.
    private static final int MAX_THREADS = 5;

    /**
     * Maps ids to events.
     */
    private final HashMap _events = new HashMap();

    /**
     * Thread pool.
     */
    private PooledExecutor _pool;

    /**
     * Synchonization for the following two collections.
     */
    private final Object _queueSync = new Object();

    /**
     * Event queue.
     */
    private final OrderedQueue _queue = new OrderedQueue(_queueComparator);

    /**
     * Used to generate unique queue entry ids.
     */
    private long _seed;


    /**
     * Construct a new <code>BasicEventManager</code>.
     */
    public BasicEventManager(ThreadPoolFactory factory) {
        super("BasicEventManager");

        _pool = factory.create(getName(), MAX_THREADS);
    }

    /**
     * Register an event to be fired once and only once at the specified
     * abolsute time. The event object must be Serializable so that it can be
     * persisted and restored across EventManager restarts.
     * <p/>
     * If the specified event is ill-defined then the IllegalEventDefined-
     * Exception exception is thrown.
     * <p/>
     * Similarly, if the abolsute time has already passed then the exception
     * IllegalEventDefinedException is raised.
     * <p/>
     * The method returns an unique event identifier, which can subsequently be
     * used to deregister the event.
     *
     * @param event    information about the event
     * @param absolute the abolsute time, in ms, that the event must fire
     * @return String  unique event identifier
     * @throws IllegalEventDefinedException
     */
    public String registerEvent(Event event, long absolute)
            throws IllegalEventDefinedException {
        synchronized (_queueSync) {
            QueueEntry entry = new QueueEntry(event, absolute, generateId());

            // add entry to the queue.
            _queue.add(entry);
            _events.put(entry.id, entry);

            // notify the event thread.
            _queueSync.notifyAll();
            return entry.id;
        }
    }

    /**
     * Register an event to be fired once and only once at a time relative to
     * now. The event object must be Serializable so that it can be persisted
     * and restored across EventManager restarts.
     * <p/>
     * If the specified event is ill-defined then the IllegalEventDefined-
     * Exception exception is thrown.
     * <p/>
     * The method returns an unique event identifier, which can subsequently be
     * used to deregister the event.
     *
     * @param event    information about the event
     * @param relative the relative time in ms (currently no reference to
     *                 locale).
     * @return String  unique event identifier,
     * @throws IllegalEventDefinedException
     */
    public String registerEventRelative(Event event, long relative)
            throws IllegalEventDefinedException {
        return registerEvent(event, System.currentTimeMillis() + relative);
    }

    /**
     * Unregister the event specified by the event identifier. If the event does
     * not exist then fail silently.
     *
     * @param id unique event identifier.
     */
    public void unregisterEvent(String id) {
        synchronized (_queueSync) {
            // remove from the events list
            Object obj = _events.remove(id);
            if (obj == null) {
                return;
            }
            // remove from the queue.
            _queue.remove(obj);
        }
    }

    // implementation of BasicService.run
    public void run() {
        synchronized (_queueSync) {
            QueueEntry entry;
            long currentTime;
            while (!Thread.interrupted()) {
                currentTime = System.currentTimeMillis();
                try {
                    entry = (QueueEntry) _queue.firstElement();
                } catch (java.util.NoSuchElementException ex) {
                    // queue is empty.
                    try {
                        _queueSync.wait();
                    } catch (InterruptedException ex1) {
                        break;
                    }
                    continue;
                }

                if (entry.absolute <= currentTime) {
                    // trigger any expired events
                    try {
                        _pool.execute(entry);
                    } catch (InterruptedException ex) {
                        break;
                    }
                    _queue.removeFirstElement();
                    _events.remove(entry.id);
                } else {
                    // wait for either the next event to expire or an element to be
                    // added to the queue.
                    try {
                        _queueSync.wait(entry.absolute - currentTime);
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Generate unique queued object identifier.
     */
    private synchronized String generateId() {
        return Long.toString(++_seed);
    }

    /**
     * Compare queue entries on expiration times
     */
    private static final Comparator _queueComparator =
            new Comparator() {

                public int compare(Object obj1, Object obj2) {
                    QueueEntry qe1 = (QueueEntry) obj1;
                    QueueEntry qe2 = (QueueEntry) obj2;

                    if (qe1.absolute < qe2.absolute) {
                        return -1;
                    }
                    if (qe1.absolute > qe2.absolute) {
                        return 1;
                    }
                    return 0;
                }

                public boolean equals(Object that) {
                    return (this == that);
                }
            };

    /**
     * Entry on the task queue.
     */
    class QueueEntry implements Runnable {

        QueueEntry(Event event, long absolute, String id) {
            this.absolute = absolute;
            this.event = event;
            this.id = id;
        }

        private long absolute;
        private Event event;
        private String id;

        public void run() {
            event.getEventListener().handleEvent(event.getEventType(),
                    event.getCallbackObject(),
                    System.currentTimeMillis());
        }
    }

}
