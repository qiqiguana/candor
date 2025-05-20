/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.common.util;

/**
 * Run a delayed {@link System#exit} with configurable delay and exit value.
 * It is possible to abort the exit by calling {@link #abortExit()}. 
 */
public class DeferredSystemExit implements Runnable {
    /** Default shutdown delay in milliseconds. */
    public static final int DEFAULT_DELAY = 5000;
    /** Exit code to exit with. */
    private int exitCode;
    /** The actual delay. */
    private int delay;
    /** Private thread, which when started calls the exit. */
    private Thread thread;

    /**
     * Run {@code System.exit (exitCode)} after {@code delay} ms.
     * @param exitCode The system exit code.
     * @param delay Number of milli seconds to wait before calling
     *              {@link System#exit}.
     */
    public DeferredSystemExit(int exitCode, int delay) {
        this.exitCode = exitCode;
        this.delay = delay;
        startExit();
    }

    /**
     * Run {@code System.exit (exitCode)} after {@link #DEFAULT_DELAY} ms
     * @param exitCode The system exit code.
     */
    public DeferredSystemExit(int exitCode) {
        this (exitCode, DEFAULT_DELAY);
    }

    /**
     * Abort the scheduled system exit.
     */
    public void abortExit() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    /**
     * Do not call the method externally. It is only meant to be internally called by {@code DefferedSystemExit}.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            // Abort the System.exit. Someone probably called abortExit()
            return;
        }
        try {
            System.exit (exitCode);
        } catch (SecurityException e) {
            System.err.println("Enable to perform a system.exit(" + exitCode + ") as the SecurityManager did not "
                               + "permit this operation. This JVM instance might not terminate by itself");
        }
    }

    /**
     * Starts a exit thread.
     */
    private void startExit() {
        thread = new Thread(this, "DeferredSystemExit Thread");
        // Allow the JVM to exit even though thread is active
        thread.setDaemon(true);
        thread.start();
    }
}
