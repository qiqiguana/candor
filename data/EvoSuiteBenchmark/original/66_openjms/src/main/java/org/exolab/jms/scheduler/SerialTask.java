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
 * $Id: SerialTask.java,v 1.1 2005/08/30 05:38:56 tanderson Exp $
 */
package org.exolab.jms.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * A {@link Runnable} implementation which may only be executed serially by the
 * {@link Scheduler}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/08/30 05:38:56 $
 */
public class SerialTask implements Runnable {

    /**
     * The task to execute.
     */
    private Runnable _task;

    /**
     * The scheduler.
     */
    private final Scheduler _scheduler;

    /**
     * Determines if the task is currently active.
     */
    private boolean _active = false;

    /**
     * Determines if the task should stop.
     */
    private boolean _stop = false;

    /**
     * Determines if the task is scheduled to run.
     */
    private boolean _scheduled = false;

    /**
     * Determines if the task needs to be rescheduled.
     */
    private boolean _reschedule = false;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(SerialTask.class);


    /**
     * Construct a new <code>SerialTask</code>.
     *
     * @param task      the task to execute
     * @param scheduler the scheduler
     */
    public SerialTask(Runnable task, Scheduler scheduler) {
        _task = task;
        _scheduler = scheduler;
    }

    /**
     * Schedule this to run.
     * <p/>
     * If the task is currently running, it will be scheduled after it has
     * completed.
     *
     * @return <code>true</code> if the task was scheduled; <code>false</code>
     *         if the task is already scheduled, or is in the process of
     *         stopping
     * @throws InterruptedException if the task can't be scheduled
     */
    public synchronized boolean schedule() throws InterruptedException {
        if (_log.isDebugEnabled()) {
            _log.debug("schedule() " + this);
        }
        boolean result = false;
        if (!_stop && !_scheduled) {
            if (_active) {
                _reschedule = true;
            } else {
                _scheduled = true;
                try {
                    _scheduler.execute(this);
                } catch (InterruptedException exception) {
                    _scheduled = false;
                    throw exception;
                }
            }
            result = true;
        }
        return result;
    }

    /**
     * Run the task.
     */
    public void run() {
        synchronized (this) {
            if (_log.isDebugEnabled()) {
                _log.debug("run() " + this);
            }
            if (_stop) {
                if (_log.isDebugEnabled()) {
                    _log.debug("Scheduled task cancelled");
                }
                return;
            }
            if (_active) {
                if (_log.isDebugEnabled()) {
                    _log.debug("Serial task already running, aborting");
                }
                throw new IllegalStateException("SerialTask already running");
            }
            _active = true;
            _scheduled = false;
        }
        try {
            _task.run();
        } finally {
            synchronized (this) {
                _active = false;
                if (_reschedule) {
                    try {
                        _reschedule = false;
                        _scheduled = true;
                        _scheduler.execute(this);
                    } catch (InterruptedException exception) {
                        _scheduled = false;
                    }
                } else {
                    _scheduled = false;
                }
                notify();
            }
        }
    }

    /**
     * Stop the task.
     * <p/>
     * This will wait for the task to complete before returning. If the task
     * has been scheduled to run again, it will be cancelled.
     */
    public synchronized void stop() {
        if (_log.isDebugEnabled()) {
            _log.debug("stop() " + this);
        }
        _stop = true;
        _reschedule = false;
        _scheduled = false;
        while (_active) {
            try {
                wait();
            } catch (InterruptedException ignore) {
            }
        }
        _reschedule = false;
        _stop = false;
    }

    /**
     * Returns a stringified form of this, for debugging purposes.
     *
     * @return a stringified form of this
     */
    public synchronized String toString() {
        return "[stop=" + _stop + ", active=" + _active
                + ", reschedule=" + _reschedule + ", scheduled=" + _scheduled
                + "]";
    }

}
