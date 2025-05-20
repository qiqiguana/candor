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
 * Copyright 2000,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: Event.java,v 1.1 2004/11/26 01:50:41 tanderson Exp $
 *
 * Date         Author  Changes
 * 07/27/00	    jima    Created
 */
package org.exolab.jms.events;

import java.io.Serializable;


/**
 * This class specifies the object that is used by the {@link EventManager}.
 * It defines the event type, the event listener and the callback object.
 * <p>
 * The event type is an integral value that identifies the type of event to
 * fire. It is transparent to the {@link EventManager}.
 * <p>
 * The event listener must implement the {@link EventHandler} interface.
 * <p>
 * The callback object must be {@link Serializable} and is passed back to the
 * {@link EventHandler} when the event fires.
 * <p>
 * Most importantly this class is {@link Serializable} so that it can be
 * stored, if required.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:41 $
 * @author      <a href="mailto:jima@intalio.com">Jim Alateras</a>
 */
public class Event
    implements Serializable {

    /**
     * The event type.
     */
    private int _eventType;

    /**
     * The _eventHandler is the object that is notified when the event fires.
     * It must be Serializable so that it can be persisted.
     */
    private EventHandler _eventHandler = null;

    /**
     * The _callbackObject is optionally specified and is passed back to the
     * _eventHandler during notification.
     */
    private Object _callbackObject = null;


    /**
     * The constructor instantiates an instance of this class with the
     * specified parameters. The event and listener must be non-null and
     * valid but the callback object may be null.
     *
     * @param       event           the type of event to fire
     * @param       listener        the object that will receive the event
     * @param       callback        a callback object, that is Serializable
     * @throws      IllegalEventDefinedException
     */
    public Event(int event, EventHandler listener, Object callback)
        throws IllegalEventDefinedException {
        if ((event != 0) &&
            (listener != null)) {
            _eventType = event;
            _eventHandler = listener;

            // if the callback object has been specified then ensure that
            // it is Serializabe. If the object isnot Serializable then throw
            // an exception.
            if (callback != null) {
                if (callback instanceof Serializable) {
                    _callbackObject = callback;
                } else {
                    throw new IllegalEventDefinedException(
                        "The callback object is not Serializable");
                }
            }
        } else {
            // event not-well specified
            throw new IllegalEventDefinedException(
                "event is 0 or listener is null");
        }
    }

    /**
     * Return the event type.
     *
     * @return      int
     */
    public int getEventType() {
        return _eventType;
    }

    /**
     * Return the event listener
     *
     * @return      EventHandler
     */
    public EventHandler getEventListener() {
        return _eventHandler;
    }

    /**
     * Return a reference to the callbacl object. The returned object may be
     * null
     *
     * @return      Object
     */
    public Object getCallbackObject() {
        return _callbackObject;
    }
}


