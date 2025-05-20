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
 */
package org.exolab.jms.scheduler;

import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;
import org.exolab.jms.common.threads.ThreadPoolFactory;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.SchedulerConfiguration;
import org.exolab.jms.service.Service;


/**
 * The scheduler is responsible for executing {@link Runnable} objects using a
 * thread pool. Clients can add these objects to the scheduler and the scheduler
 * will, in fifo order, execute them. If there are no threads currently
 * available, the runnable will wait for one to become available.
 * <p/>
 * A client can add or remove {@link Runnable} objects.
 *
 * @author <a href="mailto:mourikis@intalio.com">Jim Mourikis</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @version $Revision: 1.3 $ $Date: 2006/02/23 11:17:39 $
 */
public class Scheduler extends Service {

    /**
     * The thread pool used by the scheduler.
     */
    private final PooledExecutor _threads;

    /**
     * This is the minimum number of threads that can be used to configure the
     * scheduler. If a lower nmber is specified then it defaults to this value
     */
    private final static int MIN_THREAD_COUNT = 2;

    /**
     * Unique name identifyting this sevice
     */
    private static final String SCHEDULER_NAME = "Scheduler";

    /**
     * Construct a new <code>Scheduler</code>.
     *
     * @param config  the configuration
     * @param factory the thread pool factory
     */
    public Scheduler(Configuration config, ThreadPoolFactory factory) {
        super(SCHEDULER_NAME);

        SchedulerConfiguration schedConfig =
                config.getSchedulerConfiguration();

        int count = schedConfig.getMaxThreads();
        if (count < MIN_THREAD_COUNT) {
            count = MIN_THREAD_COUNT;
        }

        // create the thread pool
        _threads = factory.create(SCHEDULER_NAME, new LinkedQueue(), count);
        _threads.setMinimumPoolSize(count);
        _threads.setKeepAliveTime(-1); // live forever
    }

    /**
     * Add a Runnable object to the scheduler queue. When a thread becomes
     * available, it will be executed.
     *
     * @param task the task to execute
     */
    public void execute(Runnable task) throws InterruptedException {
        _threads.execute(task);
    }

    /**
     * Stop the service.
     */
    protected void doStop() {
        _threads.shutdownAfterProcessingCurrentlyQueuedTasks();
    }

}
