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
 * $Id: EventManager.java,v 1.2 2005/08/30 05:08:53 tanderson Exp $
 */
package org.exolab.jms.events;


/**
 * The EventManager manages {@link Event} objects.
 * <p/>
 * An event is defined to occur at sometime in the future, as specified either
 * by an absolute time through {@link #registerEvent} or as relative time
 * through {@link #registerEventRelative}. An event must have an associated
 * event type and may have an attached <code>Serializable</code> object, which
 * is used when the EventManager makes a callback to the registered handler when
 * the event fires.
 * <p/>
 * The register methids will return an event identifier which can subsequently
 * be used to unregister the event through the {@link #unregisterEvent} event.
 * This is the only means of unregister an event.
 * <p/>
 * If the {@link Event} object is incorrectly specified then the {@link
 * IllegalEventDefinedException} exception is raised.
 * <p/>
 * When an event fires the EventManager is responsible for ensuring that the
 * event handler is notified. If the event handler has since been removed then
 * the EventManager must gracefully abort the delivery and continue processing
 * the next event.
 * <p/>
 * This class is also <code>Serviceable</code>, which implies that it can be
 * added and controlled by a <code>ServiceManager</code>.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 05:08:53 $
 */
public interface EventManager {

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
     * @param absolute the absolute time, in ms, that the event must fire
     * @return String          unique event identifier
     * @throws IllegalEventDefinedException
     */
    String registerEvent(Event event, long absolute)
            throws IllegalEventDefinedException;

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
     * @param relative the  relative time in ms (currently no reference to
     *                 locale).
     * @return String          unique event identifier,
     * @throws IllegalEventDefinedException
     */
    String registerEventRelative(Event event, long relative)
            throws IllegalEventDefinedException;

    /**
     * Unregister the event specified by the event identifier. If the event does
     * not exist then fail silently.
     *
     * @param id unique event identifier.
     */
    void unregisterEvent(String id);
}
