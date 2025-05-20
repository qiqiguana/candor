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
 * Copyright 2001-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: GarbageCollectionService.java,v 1.2 2005/08/30 05:09:45 tanderson Exp $
 */
package org.exolab.jms.gc;

import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.GarbageCollectionConfiguration;
import org.exolab.jms.events.BasicEventManager;
import org.exolab.jms.events.Event;
import org.exolab.jms.events.EventHandler;
import org.exolab.jms.events.IllegalEventDefinedException;
import org.exolab.jms.events.EventManager;
import org.exolab.jms.service.Service;
import org.exolab.jms.service.ServiceException;


/**
 * The garbage collection service is responsible for managing all transient
 * garbage collection for OpenJMS, which includes messages, destinations,
 * endpoints etc. It does not deal with persistent data, which is handled
 * through the database service. Other services or managers can register
 * themselves with GarbageCollectionService if they implement the {@link
 * GarbageCollectable} interface.
 * <p/>
 * Gargabe collection will be initiated when the amount of free memory falls
 * below a low water mark, which is calculated as a percentage of total memory.
 * By default garbage collection will run when free memory falls below 20% of
 * total memory, this can be changed through the configuration file.
 * <p/>
 * The service will check the memory usage every 30 seconds by default. but this
 * can also be modified through the configuration file.
 * <p/>
 * In addition the garbage collection service can also be configured to execute
 * at regular intervals regardless the amount of residual free memory. This
 * option can be employed to ease the burden of performing wholesale garbage
 * collection when memory falls below the low water mark threshold. The default
 * value for this is 300 seconds. Setting this value to 0 will disable this
 * capability.
 * <p/>
 * This service makes use of the {@link BasicEventManager} to register events
 * for garbage collection.
 *
 * @author <a href="mailto:jima@intalio.com">Jim Alateras</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 05:09:45 $
 */
public class GarbageCollectionService
        extends Service
        implements EventHandler {

    /**
     * This is the value of the transient garbage collection event that is used
     * to register with the {@link BasicEventManager}. When this event is
     * received the memory utilization is checked to determine whether we need
     * to perform some garbage collection.
     */
    private final static int CHECK_FREE_MEMORY_EVENT = 1;

    /**
     * This event is used to unconditionally trigger garbage collection.
     */
    private final static int GARBAGE_COLLECT_EVENT = 2;

    /**
     * The default low water threshold value before GC is initiated. This is
     * specified as a percentage with valid values ranging from 10-50.
     */
    private int _gcLowWaterThreshold = 20;

    /**
     * The default interval, in seconds, that memory is checked for the low
     * water threshold. The default is 30 seconds.
     */
    private int _memoryCheckInterval = 30 * 1000;

    /**
     * The default interval, in seconds, between successive executions of the
     * garbage collector. This will execute regardless the amount of free memory
     * left in the VM.
     */
    private int _gcInterval = 300 * 1000;

    /**
     * This is the priority of that the GC thread uses to collect garbage.
     * Changing it effects how aggressive GC is performed. The default value is
     * 5.
     */
    private int _gcThreadPriority = 5;

    /**
     * This is used to serialize access to the _collectingGarbage flag
     */
    private final Object _gcGuard = new Object();

    /**
     * This flag indicates whether garabage collection is in progress
     */
    private boolean _collectingGarbage = false;

    /**
     * Maintains a list of all GarbageCollectable instances
     */
    private LinkedList _gcList = new LinkedList();

    /**
     * The event manager.
     */
    private final EventManager _eventMgr;

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(GarbageCollectionService.class);


    /**
     * Create an instance of a garbage collection service. It uses the
     * configuration manager to extract the service parameters.
     *
     * @param config   the configuration to use
     * @param eventMgr the event manager
     */
    public GarbageCollectionService(Configuration config,
                                    EventManager eventMgr) {
        super("GarbageCollectionService");
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        if (eventMgr == null) {
            throw new IllegalArgumentException("Argument 'eventMgr' is null");
        }

        GarbageCollectionConfiguration gcConfig =
                config.getGarbageCollectionConfiguration();

        // read the value and ensure that it is within
        // the specified limits
        int low = gcConfig.getLowWaterThreshold();
        if (low < 10) {
            low = 10;
        }

        if (low > 50) {
            low = 50;
        }
        _gcLowWaterThreshold = low;

        // read the memory check interval and fix it if it falls
        // outside the constraints
        int mem_interval = gcConfig.getMemoryCheckInterval();
        if ((mem_interval > 0) &&
                (mem_interval < 5)) {
            mem_interval = 5;
        }
        _memoryCheckInterval = mem_interval * 1000;

        // read the gc interval, which is optional
        int gc_interval = gcConfig.getGarbageCollectionInterval();
        if (gc_interval <= 0) {
            gc_interval = 0;
        }
        _gcInterval = gc_interval * 1000;

        // read the gc thread priority
        int gc_priority = gcConfig.getGarbageCollectionThreadPriority();
        if (gc_priority < Thread.MIN_PRIORITY) {
            gc_priority = Thread.MIN_PRIORITY;
        }

        if (gc_priority > Thread.MAX_PRIORITY) {
            gc_priority = Thread.MAX_PRIORITY;
        }
        _gcThreadPriority = gc_priority;

        _eventMgr = eventMgr;
    }

    /**
     * Check whether the low water threshold has been reached.
     *
     * @return boolean - true if it has; false otherwise
     */
    public boolean belowLowWaterThreshold() {
        boolean result = false;
        long threshold = (long) ((Runtime.getRuntime().totalMemory() / 100) *
                _gcLowWaterThreshold);
        long free = Runtime.getRuntime().freeMemory();

        if (_log.isDebugEnabled()) {
            _log.debug("GC Threshold=" + threshold + " Free=" + free);
        }
        if (threshold > free) {
            result = true;
        }

        return result;
    }

    /**
     * Register an entity that wishes to participate in the garbage collection
     * process. This entity will be added to the list of other registered
     * entities and will be called when GC is triggered.
     *
     * @param entry the entry to add to list
     */
    public void register(GarbageCollectable entry) {
        if (entry != null) {
            synchronized (_gcList) {
                _gcList.add(entry);
            }
        }
    }

    /**
     * Unregister the specified entry from the list of garbge collectable
     * entities
     *
     * @param entry - entry to remove
     */
    public void unregister(GarbageCollectable entry) {
        if (entry != null) {
            synchronized (_gcList) {
                _gcList.remove(entry);
            }
        }
    }

    public void doStart()
            throws ServiceException {

        // register an event with the event manager
        if (_memoryCheckInterval > 0) {
            _log.info("Registering Garbage Collection every " +
                      _memoryCheckInterval + " for memory.");
            registerEvent(CHECK_FREE_MEMORY_EVENT, _memoryCheckInterval);
        }

        // optionally start garbage collection
        if (_gcInterval > 0) {
            _log.info("Registering Garbage Collection every " +
                      _gcInterval + " for other resources.");
            registerEvent(GARBAGE_COLLECT_EVENT, _gcInterval);
        }
    }

    // implementation of EventHandler.handleEvent
    public void handleEvent(int event, Object callback, long time) {
        boolean valid_event = false;

        try {
            if (event == CHECK_FREE_MEMORY_EVENT) {
                valid_event = true;
                try {
                    // collect garbage only below threshold
                    if (belowLowWaterThreshold()) {
                        _log.info("GC Collecting Garbage Free Heap below "
                                  + _gcLowWaterThreshold);
                        collectGarbage(true);
                    }
                } catch (Exception exception) {
                    _log.error("Error in GC Service [CHECK_FREE_MEMORY_EVENT]",
                               exception);
                }
            } else if (event == GARBAGE_COLLECT_EVENT) {
                valid_event = true;
                try {
                    // collect garbage now
                    collectGarbage(false);
                } catch (Exception exception) {
                    _log.error("Error in GC Service [GARBAGE_COLLECT_EVENT]",
                               exception);
                }
            }
        } finally {
            if (valid_event) {
                try {
                    registerEvent(event, ((Long) callback).longValue());
                } catch (Exception exception) {
                    _log.error("Error in GC Service", exception);
                }
            }
        }
    }

    /**
     * Iterate through the list of registered {@link GarbageCollectable}s and
     * call collectGarbage on all of them.
     *
     * @param aggressive - true ofr aggressive garbage collection
     */
    private void collectGarbage(boolean aggressive) {
        synchronized (_gcGuard) {
            if (_collectingGarbage) {
                // if we are in the middle of collecting garbage then
                // we can ignore this request safely.
                return;
            } else {
                _collectingGarbage = true;
            }
        }

        // if we get this far then we are the only thread that will
        // trigger garbage collection. First we must set the priority
        // of this thread
        int oldPriority = Thread.currentThread().getPriority();
        try {
            Thread.currentThread().setPriority(_gcThreadPriority);
            Object[] list = _gcList.toArray();
            for (int index = 0; index < list.length; index++) {
                try {
                    GarbageCollectable collectable =
                            (GarbageCollectable) list[index];
                    collectable.collectGarbage(aggressive);
                } catch (Exception exception) {
                    _log.error("Error while collecting garbage", exception);
                }
            }
        } finally {
            Thread.currentThread().setPriority(oldPriority);
        }

        // we have finished collecting garbage
        synchronized (_gcGuard) {
            _collectingGarbage = false;
        }
    }

    /**
     * Register the specified event with the corresponding time with the {@link
     * BasicEventManager}. It will throw an exception if it cannot contact the
     * event manager or register the event.
     *
     * @param event the event to register
     * @param time  the associated time
     * @throws GarbageCollectionServiceException
     *
     */
    private void registerEvent(int event, long time)
            throws GarbageCollectionServiceException {
        try {
            _eventMgr.registerEventRelative(
                    new Event(event, this, new Long(time)), time);
        } catch (IllegalEventDefinedException exception) {
            // rethrow as a more relevant exception
            throw new GarbageCollectionServiceException(
                    "Failed to register event " + exception);
        }
    }

}
