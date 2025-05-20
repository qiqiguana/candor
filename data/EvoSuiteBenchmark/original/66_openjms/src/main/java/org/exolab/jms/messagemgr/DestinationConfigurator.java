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
 * $Id: DestinationConfigurator.java,v 1.1 2005/08/30 07:26:49 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.config.AdministeredDestinations;
import org.exolab.jms.config.AdministeredQueue;
import org.exolab.jms.config.AdministeredTopic;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.Subscriber;
import org.exolab.jms.service.Service;
import org.exolab.jms.service.ServiceException;


/**
 * Pre-configures {@link DestinationManager} with destinations and and
 * {@link ConsumerManager} with subscribers from an {@link Configuration}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/08/30 07:26:49 $
 */
public class DestinationConfigurator extends Service {

    /**
     * The configuration to use.
     */
    private final Configuration _config;

    /**
     * The destination manager.
     */
    private final DestinationManager _destinations;

    /**
     * The consumer manager.
     */
    private final ConsumerManager _consumers;

    /**
     * The logger.
     */
    private static final Log _log
            = LogFactory.getLog(DestinationConfigurator.class);


    /**
     * Create a new <code>DestinationConfigurator</code>.
     *
     * @param config       the configuration to use
     * @param destinations the destination manager
     * @param consumers    the consumer manaager
     */
    public DestinationConfigurator(Configuration config,
                                   DestinationManager destinations,
                                   ConsumerManager consumers) {
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        if (destinations == null) {
            throw new IllegalArgumentException(
                    "Argument 'destinations' is null");
        }
        if (consumers == null) {
            throw new IllegalArgumentException("Argument 'consumers' is null");
        }
        _config = config;
        _destinations = destinations;
        _consumers = consumers;
    }


    /**
     * Start the service.
     *
     * @throws ServiceException if the service fails to start
     */
    protected void doStart() throws ServiceException {
        AdministeredDestinations destinations
                = _config.getAdministeredDestinations();
        if (destinations != null) {
            configureTopics(destinations.getAdministeredTopic());
            configureQueues(destinations.getAdministeredQueue());
        }

    }

    /**
     * Configure topics.
     *
     * @param topics the topics to configure
     */
    protected void configureTopics(AdministeredTopic[] topics) {
        for (int i = 0; i < topics.length; ++i) {
            final AdministeredTopic topic = topics[i];
            final String name = topics[i].getName();

            if (_destinations.getDestination(name) == null) {
                final JmsTopic destination = new JmsTopic(name);
                destination.setPersistent(true);
                try {
                    _destinations.createDestination(destination);

                    // register the subscribers for each topic.
                    int scount = topic.getSubscriberCount();
                    for (int sindex = 0; sindex < scount; sindex++) {
                        Subscriber subscriber = topic.getSubscriber(sindex);
                        _consumers.subscribe(destination,
                                             subscriber.getName(), null);
                    }
                } catch (JMSException exception) {
                    _log.error("Failed to register persistent topic " + name,
                               exception);
                }
            }
        }
    }

    /**
     * Configure queues.
     *
     * @param queues the queues to configure
     */
    protected void configureQueues(AdministeredQueue[] queues) {
        for (int i = 0; i < queues.length; ++i) {
            final AdministeredQueue queue = queues[i];
            final String name = queue.getName();

            if (_destinations.getDestination(name) == null) {
                JmsQueue destination = new JmsQueue(name);
                destination.setPersistent(true);
                try {
                    _destinations.createDestination(destination);
                } catch (JMSException exception) {
                    _log.error("Failed to register persistent queue " + name,
                               exception);
                }
            }
        }
    }

}
