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
 * Copyright 2001-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: Executor.java,v 1.1 2004/11/26 01:51:10 tanderson Exp $
 */
package org.exolab.jms.net.jvm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This class enables commands to be executed, with the output being
 * captured, or echoed to System.out and System.err
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:10 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class Executor {

    /**
     * The command to execute
     */
    private final String _command;

    /**
     * The stream to direct standard out to
     */
    private final OutputStream _out;

    /**
     * The stream to direct standard err to
     */
    private final OutputStream _err;

    /**
     * The thread for _out
     */
    private Thread _outThread;

    /**
     * The thread for _err
     */
    private Thread _errThread;

    /**
     * The executing process
     */
    private volatile Process _process;

    /**
     * The logger
     */
    private static final Log _log = LogFactory.getLog(Executor.class);


    /**
     * Constructor to execute a command, with output going to System.out and 
     * System.err
     *
     * @param command the command to execute
     */
    public Executor(String command) {
        this(command, System.out, System.err);
    }

    /**
     * Constructor to execute a command, with all output going to a stream
     *
     * @param command the command to execute
     * @param log the stream to log output to
     */
    public Executor(String command, OutputStream log) {
        this(command, log, log);
    }

    /**
     * Constructor to execute a command with standard output and error output
     * going to two separate streams
     *
     * @param command the command to execute
     * @param out the stream to direct standard output to
     * @param err the stream to direct standard error to
     */
    public Executor(String command, OutputStream out, OutputStream err) {
        if (command == null) {
            throw new IllegalArgumentException("Argument 'command' is null");
        }
        if (out == null) {
            throw new IllegalArgumentException("Argument 'out' is null");
        }
        if (err == null) {
            throw new IllegalArgumentException("Argument 'err' is null");
        }
        _command = command;
        _out = out;
        _err = err;
    }

    /**
     * Start the command
     *
     * @throws IOException if the command cannot be started
     */
    public void start() throws IOException {
        _log.debug("Starting " + _command);
        _process = Runtime.getRuntime().exec(_command);
        Reader out = new Reader(_process.getInputStream(), _out);
        Reader err = new Reader(_process.getErrorStream(), _err);
        _outThread = new Thread(out, "StdOut<" + _command + ">");
        _errThread = new Thread(err, "StdErr<" + _command + ">");
        _outThread.start();
        _errThread.start();
        
    }

    /**
     * Wait for the command to finish
     *
     * @return the command exit status
     */
    public int waitFor() {
        boolean done = false;
        int status = 1;

        while (!done) {
            try {
                status = _process.waitFor();
                done = true;
            } catch (InterruptedException ignore) {
                // do nothing
            }
        }

        while (true) {
            try {
                _outThread.join();
                break;
            } catch (InterruptedException ignore) {
                // do nothing
            }
        }

        while (true) {
            try {
                _errThread.join();
                break;
            } catch (InterruptedException ignore) {
                // do nothing
            }
        }

        _outThread = null;
        _errThread = null;
        _process = null;

        return status;        
    }

    /**
     * Execute the command and wait for it to complete
     *
     * @return the command exit status
     * @throws IOException if the command cannot be executed
     */
    public int run() throws IOException {
        start();
        return waitFor();        
    }

    /**
     * Stop the process
     */
    public void stop() {
        Process process = _process;
        if (process != null) {
            _log.debug("Stopping " + _command);
            process.destroy();
        }
    }

    /**
     * Helper class that reads from one stream and writes to another, in a
     * separate thread.
     */
    class Reader implements Runnable {

        /**
         * The thread executing the reader instance
         */
        private volatile Thread _thread = null;

        /**
         * The input stream
         */
        private BufferedReader _input = null;

        /**
         * The output stream
         */
        private PrintStream _output = null;

        /**
         * The exception, if an exception was generated while performing I/O
         */
        private Exception _exception = null;

        /**
         * Construct a reader, reading from the input stream and writing to 
         * the output stream
         *
         * @param input the input stream
         * @param output the output stream
         */
        public Reader(InputStream input, OutputStream output) {
            _input = new BufferedReader(new InputStreamReader(input));
            _output = new PrintStream(output);
        }

        /**
         * Run the reader
         */
        public void run() {
            _thread = Thread.currentThread();
            String line;
            try {
                while (_thread != null && (line = _input.readLine()) != null) {
                    _output.println(line);
                }
            } catch (IOException exception) {
                // terminate this on an I/O error.
                _exception = exception;
                _thread = null;
            }
            _input = null;
            _output = null;
        }    

        /**
         * Stop the reader
         */
        public void stop() {
            if (_thread != null) {
                Thread interrupter = _thread;
                _thread = null;
                interrupter.interrupt();
            }
        }

        /**
         * Returns the exception if one was generated. If {@link #stop} was
         * invoked then an exception is likely to have been generated.
         */
        public Exception getException() {
            return _exception;
        }
    }

}
