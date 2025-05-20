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
 * $Id: LeaseManager.java,v 1.4 2005/12/26 04:45:30 tanderson Exp $
 */
package org.exolab.jms.lease;


import org.exolab.jms.service.BasicService;
import org.exolab.jms.service.ServiceException;
import org.exolab.jms.common.util.OrderedQueue;


/**
 * The LeaseManager is responsible for creating and managing the lease objects.
 * The Leasemanager is a singleton. When a BaseLease object is created it is
 * added to the queue according to the duration (i.e. leases with shorter
 * durations are placed at the top of the queue.
 * <p>
 * When the lease expires the LeeaseManager calls the leasee's associated
 * listener(s).
 *
 * @version     $Revision: 1.4 $ $Date: 2005/12/26 04:45:30 $
 * @author      <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 */
public class LeaseManager extends BasicService {

    /**
     * An ordered list of leases.
     */
    private OrderedQueue _queue = null;

    /**
     * Helper for waiting for leases to expire.
     */
    private final Object _waiter = new Object();


    /**
     * Create a new sorted tree set using the lease comparator as the
     * sorting functor.
     */
    public LeaseManager() {
        super("LeaseManager");
        _queue = new OrderedQueue(new LeaseComparator());
    }

    /**
     * Add a lease.
     *
     * @param lease the lease to add
     */
    public void addLease(BaseLease lease) {
        synchronized (_queue) {
            _queue.add(lease);
            if (_queue.firstElement() == lease) {
                // inserted before the first element, so reset scan
                synchronized (_waiter) {
                    _waiter.notify();
                }
            }
        }
    }

    /**
     * Remove a lease.
     *
     * @param       lease           lease to remove
     * @return      boolean         true if successful; false otherwise
     */
    public boolean removeLease(BaseLease lease) {
        boolean result = false;

        synchronized (_queue) {
            result = _queue.remove(lease);
        }
        if (result) {
            synchronized (_waiter) {
                _waiter.notify();
            }
        }

        return result;
    }

    /**
     * Renew the lease on the specified object
     *
     * @param lease the lease to renew
     * @param duration the new duration of the lease in ms
     */
    public BaseLease renewLease(BaseLease lease, long duration) {
        BaseLease newlease = null;

        if ((lease != null) && (duration > 0)) {
            synchronized (_queue) {
                // check that the lease hasn't expired yet.
                if (_queue.remove(lease)) {
                    lease.setDuration(duration);
                    _queue.add(lease);
                    newlease = lease;
                    synchronized (_waiter) {
                        _waiter.notify();
                    }
                }
            }
        }

        return newlease;
    }

    /**
     * Remove all the leases from the queue. Do not expire any of them
     */
    public void removeAll() {
        synchronized (_queue) {
            _queue.clear();
        }
    }

    /**
     * The run method will search for expired leases, remove them from the
     * list and notify listeners
     */
    public void run() {
        while (!Thread.interrupted()) {
            expire();

            // wait until a lease is available, or the service is terminated
            synchronized (_waiter) {
                try {
                    _waiter.wait();
                } catch (InterruptedException terminate) {
                    break;
                }
            }
        }
    }

    /**
     * Stop the service.
     *
     * @throws ServiceException if the service fails to stop
     */
    public void doStop() throws ServiceException {
        synchronized (_waiter) {
            _waiter.notifyAll();
        }
    }

    /**
     * Expires active leases
     */
    protected void expire() {
        while (_queue.size() > 0) {
            BaseLease lease = null;
            boolean expired = false;
            synchronized (_queue) {
                lease = (BaseLease) _queue.firstElement();
                if (lease == null) {
                    continue;
                }
                
                if (lease.getExpiryTime() <= System.currentTimeMillis()) {
                    // remove from the list and notify listeners
                    _queue.removeFirstElement();
                    expired = true;
                }
            }

            if (expired) {
                lease.notifyLeaseExpired();
            } else {
                // wait until the first element in the list is
                // ready to expire
                long time = lease.getExpiryTime() - 
                    System.currentTimeMillis();
                
                if (time > 0) {
                    try {
                        synchronized (_waiter) {
                            _waiter.wait(time);
                        }
                    } catch (InterruptedException ignore) {
                    }
                }
            }
        }
    }

}
