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
 * Copyright 2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ThreadPool.java,v 1.2 2006/02/23 11:07:05 tanderson Exp $
 */
package org.exolab.jms.common.threads;


import EDU.oswego.cs.dl.util.concurrent.Channel;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;
import EDU.oswego.cs.dl.util.concurrent.SynchronousChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.common.threads.ThreadFactory;
import org.exolab.jms.common.threads.ThreadListener;


/**
 * Thread pool.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2006/02/23 11:07:05 $
 */
public class ThreadPool extends PooledExecutor {

    /**
     * The listener. May be <code>null</code>.
     */
    private ThreadListener _listener;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(ThreadPool.class);


    /**
     * Construct a new <code>ThreadPool</code>.
     *
     * @param name        the name to assign the thread group and worker
     *                    threads
     * @param maxPoolSize the maximum no. of threads to use
     */
    public ThreadPool(String name, int maxPoolSize) {
        this(name, maxPoolSize, false);
    }

    /**
     * Construct a new <code>ThreadPool</code>.
     *
     * @param name        the name to assign the thread group and worker
     *                    threads
     * @param channel     the channel for queueing
     * @param maxPoolSize the maximum no. of threads to use
     */
    public ThreadPool(String name, Channel channel, int maxPoolSize) {
        this(name, channel, maxPoolSize, false);
    }

    /**
     * Construct a new <code>ThreadPool</code>.
     *
     * @param name        the name of the pool
     * @param maxPoolSize the maximum no. of threads to use
     * @param daemon      if <code>true</code> all threads will be daemon
     *                    threads
     */
    public ThreadPool(String name, int maxPoolSize, boolean daemon) {
        this(new ThreadGroup(name), name, new SynchronousChannel(), maxPoolSize,
                daemon);
    }

    /**
     * Construct a new <code>ThreadPool</code>.
     *
     * @param name        the name of the pool
     * @param channel     the channel for queueing
     * @param maxPoolSize the maximum no. of threads to use
     * @param daemon      if <code>true</code> all threads will be daemon
     *                    threads
     */
    public ThreadPool(String name, Channel channel, int maxPoolSize,
                      boolean daemon) {
        this(new ThreadGroup(name), name, channel, maxPoolSize, daemon);
    }

    /**
     * Construct a new <code>ThreadPool</code>.
     *
     * @param group       the thread group. May be <code>null</code>
     * @param name        the name to assign worker threads
     * @param maxPoolSize the maximum no. of threads to use
     */
    public ThreadPool(ThreadGroup group, String name, int maxPoolSize) {
        this(group, name, new SynchronousChannel(), maxPoolSize, false);
    }

    /**
     * Construct a new <code>ThreadPool</code>.
     *
     * @param group       the thread group. May be <code>null</code>
     * @param name        the name to assign worker threads
     * @param channel     the channel for queueing
     * @param maxPoolSize the maximum no. of threads to use
     * @param daemon      if <code>true</code> all threads will be daemon
     *                    threads
     */
    public ThreadPool(ThreadGroup group, String name, Channel channel,
                      int maxPoolSize, boolean daemon) {
        super(channel, maxPoolSize);
        setThreadFactory(new ThreadFactory(group, name + "-Worker-", daemon));
    }

    /**
     * Sets a listener to be notified when a thread processes a command.
     *
     * @param listener the listener
     */
    public void setThreadListener(ThreadListener listener) {
        _listener = listener;
    }

    /**
     * Arrange for the given command to be executed by a thread in this pool.
     * The method normally returns when the command has been handed off for
     * (possibly later) execution.
     */
    public void execute(Runnable command) throws InterruptedException {
        ThreadListener listener = _listener;
        if (listener != null) {
            super.execute(new NotifyingRunnable(command, listener));
        } else {
            super.execute(command);
        }
    }

    private static class NotifyingRunnable implements Runnable {

        /**
         * The command to execute.
         */
        private final Runnable _command;

        /**
         * The listener.
         */
        private final ThreadListener _listener;


        /**
         * Construct a new <code>NotifyingRunnable</code>.
         *
         * @param command  the command to execute
         * @param listener the listener
         */
        public NotifyingRunnable(Runnable command, ThreadListener listener) {
            _command = command;
            _listener = listener;
        }

        /**
         * Run the command, notifying the listener.
         *
         * @see Thread#run()
         */
        public void run() {
            try {
                _listener.begin(_command);
            } catch (Throwable exception) {
                _log.error(exception, exception);
            }
            _command.run();
            try {
                _listener.end(_command);
            } catch (Throwable exception) {
                _log.error(exception, exception);
            }
        }
    }

}
