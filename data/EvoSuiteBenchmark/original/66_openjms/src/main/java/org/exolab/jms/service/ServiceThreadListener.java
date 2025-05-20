package org.exolab.jms.service;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import org.exolab.jms.common.threads.ThreadListener;


/**
 * A <code>ThreadListener</code> that notifies child listeners of thread events..
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $LastChangedDate$
 */
public class ServiceThreadListener implements ThreadListener {

    /**
     * The thread listeners.
     */
    private List _listeners = Collections.synchronizedList(new ArrayList());


    /**
     * Add a listener.
     *
     * @param listener the listener to add
     */
    public void addThreadListener(ThreadListener listener) {
        _listeners.add(listener);
    }

    /**
     * Remove a listener.
     *
     * @param listener the listener tp remove
     */
    public void removeThreadListener(ThreadListener listener) {
        _listeners.remove(listener);
    }

    /**
     * Invoked when a command is to be executed.
     *
     * @param command the command
     */
    public void begin(Runnable command) {
        ThreadListener[] listeners
                = (ThreadListener[]) _listeners.toArray(new ThreadListener[0]);
        for (int i = 0; i < listeners.length; ++i) {
            listeners[i].begin(command);
        }
    }

    /**
     * Invoked when a command completes execution.
     *
     * @param command the command.
     */
    public void end(Runnable command) {
        ThreadListener[] listeners
                = (ThreadListener[]) _listeners.toArray(new ThreadListener[0]);
        for (int i = 0; i < listeners.length; ++i) {
            listeners[i].end(command);
        }
    }
}
