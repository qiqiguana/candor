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
 * $Id: OrderedQueue.java,v 1.1 2004/11/26 01:50:35 tanderson Exp $
 */
package org.exolab.jms.common.util;

import java.util.Comparator;
import java.util.Vector;


/**
 * The OrderedQueue is responsible for managing the expiration of the leases.
 * The LeaseComparator is used to determine where they are inserted and the
 * lease with the shortest duration is removed from the queue first. It is
 * implemented suing a Vector but this could be changed to improve performance.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @version $Revision: 1.1 $ $Date: 2004/11/26 01:50:35 $
 */
public class OrderedQueue {

    /***
     * The queue
     */
    private Vector _queue = null;

    /**
     * The comparator for ordering the queue
     */
    private Comparator _comparator = null;

    /**
     * Construct an instance of this class with the comparator to order the
     * elements in the queue. Elements with the same order value are placed
     * after each other.
     *
     * @param comparator used for ordering
     */
    public OrderedQueue(Comparator comparator) {
        _comparator = comparator;
        _queue = new Vector();
    }

    /**
     * Add this element to the queue in the required order. It uses a binary
     * search to locate the correct position
     *
     * @param object object to add
     */
    public synchronized void add(Object object) {

        if (_queue.size() == 0) {
            // no elements then simply add it here
            _queue.addElement(object);
        } else {
            int start = 0;
            int end = _queue.size() - 1;

            if (_comparator.compare(object,
                                    _queue.firstElement()) < 0) {
                // it need to go before the first element
                _queue.insertElementAt(object, 0);
            } else if (_comparator.compare(object,
                                           _queue.lastElement()) > 0) {
                // add to the end of the queue
                _queue.addElement(object);
            } else {
                // somewhere in the middle
                while (true) {
                    int midpoint = start + (end - start) / 2;
                    if (((end - start) % 2) != 0) {
                        midpoint++;
                    }

                    int result = _comparator.compare(
                            object, _queue.elementAt(midpoint));

                    if (result == 0) {
                        _queue.insertElementAt(object, midpoint);
                        break;
                    } else if ((start + 1) == end) {
                        // if the start and end are next to each other then
                        // insert after at the end
                        _queue.insertElementAt(object, end);
                        break;
                    } else {
                        if (result > 0) {
                            // musty be in the upper half
                            start = midpoint;
                        } else {
                            // must be in the lower half
                            end = midpoint;
                        }
                    }
                }
            }
        }
    }

    /**
     * Remove the object from the queue
     *
     * @param object object to remove
     * @return <code>true</code> if the object was removed
     */
    public synchronized boolean remove(Object object) {
        return _queue.remove(object);
    }

    /**
     * Remove all the elements from the queue
     */
    public synchronized void clear() {
        _queue.clear();
    }

    /**
     * Return the number elements in the queue
     *
     * @return int         size of the queue
     */
    public int size() {
        return _queue.size();
    }

    /**
     * Return the first element on the queue
     *
     * @return Object
     */
    public Object firstElement() {
        return _queue.firstElement();
    }

    /**
     * Remove the first element from the queue or null if there are no elements
     * on the queue.
     *
     * @return Object
     */
    public synchronized Object removeFirstElement() {
        return _queue.remove(0);
    }

}

