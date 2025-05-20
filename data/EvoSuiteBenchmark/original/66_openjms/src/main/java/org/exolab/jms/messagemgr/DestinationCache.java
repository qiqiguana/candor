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
 * $Id: DestinationCache.java,v 1.3 2005/08/30 07:26:49 tanderson Exp $
 */

package org.exolab.jms.messagemgr;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.gc.GarbageCollectable;


/**
 * A DestinationCache is used to cache messages for a particular destination.
 * <p/>
 * It implements {@link MessageManagerEventListener} in order to be notified of
 * messages being added to the {@link MessageManager}.
 * <p/>
 * A {@link ConsumerEndpoint} registers with a {@link DestinationCache} to
 * receive messages for a particular destination.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/08/30 07:26:49 $
 */
public interface DestinationCache extends MessageManagerEventListener,
        GarbageCollectable {

    /**
     * Returns the destination that messages are being cached for
     *
     * @return the destination that messages are being cached for
     */
    JmsDestination getDestination();

    /**
     * Register a consumer with this cache.
     *
     * @param consumer the message consumer for this destination
     * @return <code>true</code> if registered; otherwise <code>false</code>
     */
    boolean addConsumer(ConsumerEndpoint consumer);

    /**
     * Remove the consumer for the list of registered consumers.
     *
     * @param consumer the consumer to remove
     */
    void removeConsumer(ConsumerEndpoint consumer);

    /**
     * Determines if the cache has any consumers.
     *
     * @return <code>true</code> if the cache has consumers;
     * otherwise <code>false</code>
     */
    boolean hasConsumers();

    /**
     * Return a message handle back to the cache, to recover unsent
     * or unacknowledged messages.
     *
     * @param handle the message handle to return
     */
    void returnMessageHandle(MessageHandle handle);

    /**
     * Returns the number of messages in the cache.
     *
     * @return the number of messages in the cache
     */
    int getMessageCount();

    /**
     * Determines if this cache can be destroyed.
     *
     * @return <code>true</code> if the cache can be destroyed, otherwise
     *         <code>false</code>
     */
    boolean canDestroy();

    /**
     * Destroy this cache.
     */
    void destroy();

}
