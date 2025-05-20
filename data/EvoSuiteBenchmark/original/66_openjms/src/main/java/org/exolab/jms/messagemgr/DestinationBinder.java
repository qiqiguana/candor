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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: DestinationBinder.java,v 1.2 2005/11/12 10:49:48 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.server.NameService;
import org.exolab.jms.service.Service;
import org.exolab.jms.service.ServiceException;


/**
 * Binds persistent destinations in JNDI.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/12 10:49:48 $
 */
public class DestinationBinder extends Service
        implements DestinationEventListener {

    /**
     * The destination manager.
     */
    private final DestinationManager _destinations;

    /**
     * The name service.
     */
    private final NameService _names;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(DestinationBinder.class);


    /**
     * Construct a new <code>DestinationBinder</code>.
     *
     * @param destinations the destination manager
     * @param names        the name service
     */
    public DestinationBinder(DestinationManager destinations,
                             NameService names) {
        if (destinations == null) {
            throw new IllegalArgumentException(
                    "Argument 'destinations' is null");
        }
        if (names == null) {
            throw new IllegalArgumentException("Argument 'names' is null");
        }
        _destinations = destinations;
        _names = names;
    }

    /**
     * Invoked when a destination is created.
     *
     * @param destination the destination that was added
     */
    public void destinationAdded(JmsDestination destination)
            throws JMSException {
        if (destination.getPersistent()) {
            try {
                Context context = _names.getInitialContext();
                ContextHelper.rebind(context, destination.getName(),
                                     destination);
            } catch (NamingException exception) {
                final String msg = "Failed to add destination "
                        + destination.getName() + " to JNDI context";
                _log.error(msg, exception);
                throw new JMSException(msg + ": " + exception.getMessage());
            }
        }
    }

    /**
     * Invoked when a destination is removed.
     *
     * @param destination the destination that was removed
     */
    public void destinationRemoved(JmsDestination destination) {
        if (destination.getPersistent()) {
            try {
                Context context = _names.getInitialContext();
                context.unbind(destination.getName());
            } catch (NamingException error) {
                _log.error("Failed to remove destination "
                           + destination.getName() + " from JNDI", error);
            }
        }
    }

    /**
     * Invoked when a message cache is created.
     *
     * @param destination the destination that messages are being cached for
     * @param cache       the corresponding cache
     */
    public void cacheAdded(JmsDestination destination, DestinationCache cache) {
        // no-op
    }

    /**
     * Invoked when a message cache is removed.
     *
     * @param destination the destination that messages are no longer being
     *                    cached for
     * @param cache       the corresponding cache
     */
    public void cacheRemoved(JmsDestination destination,
                             DestinationCache cache) {
        // no-op
    }

    /**
     * Start the service.
     *
     * @throws ServiceException if the service fails to start
     */
    protected void doStart() throws ServiceException {
        // bind each persistent destination in JNDI
        Context context;
        try {
            context = _names.getInitialContext();
        } catch (NamingException exception) {
            throw new ServiceException("Failed to get initial JNDI context",
                                       exception);
        }

        List destinations;
        try {
            destinations = _destinations.getDestinations();
        } catch (JMSException exception) {
            throw new ServiceException("Failed to get destinations",
                                       exception);
        }

        for (Iterator i = destinations.iterator(); i.hasNext();) {
            JmsDestination destination = (JmsDestination) i.next();
            if (destination.getPersistent()) {
                try {
                    ContextHelper.rebind(context, destination.getName(),
                                         destination);
                } catch (NamingException exception) {
                    throw new ServiceException("Failed to add destination "
                                               + destination.getName()
                                               + " to JNDI", exception);
                }
            }
        }

        // register for updates
        _destinations.addDestinationEventListener(this);
    }

    /**
     * Stop the service.
     *
     * @throws ServiceException if the service fails to stop
     */
    protected void doStop() throws ServiceException {
        _destinations.removeDestinationEventListener(this);
    }

}
