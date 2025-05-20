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
 * Copyright 2001-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: MessageQueue.java,v 1.3 2005/12/20 20:31:59 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * <code>MessageQueue</code> implements a synchronized queue of
 * {@link MessageHandle} instances.
 * .<p/>
 * Message handles are ordered using {@link MessageHandleComparator}
 *
 * @version     $Revision: 1.3 $ $Date: 2005/12/20 20:31:59 $
 * @author      <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class MessageQueue {

    /**
     * The message handle queue.
     */
    private final SortedMap _queue = new TreeMap(new MessageHandleComparator());

    /**
     * The set of handles, keyed on message identifier.
     */
    private final Map _handles = new HashMap();


    /**
     * Add a message handle.
     *
     * @param handle the message handle
     * @return <code>true</code> if queue set did not already contain the handle
     */
    public synchronized boolean add(MessageHandle handle) {
        boolean added = false;
        if (_queue.put(handle, handle) == null) {
            _handles.put(handle.getMessageId(), handle);
            added = true;
        }
        return added;
    }

    /**
     * Determines if a message handle exists.
     *
     * @param handle the message handle
     * @return <code>true</code> if it exists
     */
    public synchronized boolean contains(MessageHandle handle) {
        return _queue.containsKey(handle);
    }

    /**
     * Return all elements in the queue.
     *
     * @return a list of message handles
     */
    public synchronized MessageHandle[] toArray() {
        return (MessageHandle[]) _queue.keySet().toArray(new MessageHandle[0]);
    }

    /**
     * Removes a message handle from the queue.
     *
     * @param handle the message handle to remove
     * @return the removed handle, or <code>null</code> if it wasn't present
     */
    public synchronized MessageHandle remove(MessageHandle handle) {
        MessageHandle result = (MessageHandle) _queue.remove(handle);
        if (result != null) {
            _handles.remove(handle.getMessageId());
        }
        return result;
    }

    /**
     * Removes a message handle from the queue.
     *
     * @param messageId the message identifier of the handle to remove
     * @return the removed handle, or <code>null</code> if it wasn't present
     */
    public synchronized MessageHandle remove(String messageId) {
        MessageHandle result = (MessageHandle) _handles.remove(messageId);
        if (result != null) {
            _queue.remove(result);
        }
        return result;
    }

    /**
     * Removes all the elements from the queue.
     */
    public synchronized void clear() {
        _queue.clear();
        _handles.clear();
    }

    /**
     * Returns the number of message handles in the queue.
     *
     * @return the number message handles in the queue
     */
    public synchronized int size() {
        return _queue.size();
    }

    /**
     * Removes and returns the first message handle in the queue.
     *
     * @return the first message handle in the queue, or <code>null</code>,
     * if the queue is empty
     */
    public synchronized MessageHandle removeFirst() {
        MessageHandle first = null;
        if (_queue.size() > 0) {
            first = (MessageHandle) _queue.firstKey();
            _queue.remove(first);
            _handles.remove(first.getMessageId());
        }
        return first;
    }

}
