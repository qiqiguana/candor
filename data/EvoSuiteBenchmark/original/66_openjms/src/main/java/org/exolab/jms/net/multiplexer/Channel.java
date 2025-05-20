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
 * Copyright 2003-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: Channel.java,v 1.4 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.multiplexer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.net.connector.Request;
import org.exolab.jms.net.connector.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.MarshalException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;


/**
 * A <code>Channel</code> represents a single-threaded virtual connection over a
 * physical connection.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2006/12/16 12:37:17 $
 */
class Channel implements Constants {

    /**
     * The channel identifier
     */
    private int _id;

    /**
     * The multiplexer
     */
    private Multiplexer _multiplexer;

    /**
     * The input stream
     */
    private MultiplexInputStream _in;

    /**
     * The output stream
     */
    private MultiplexOutputStream _out;

    /**
     * The logger
     */
    private static final Log _log = LogFactory.getLog(Channel.class);


    /**
     * Construct a new <code>Channel</code>
     *
     * @param id          the identifier for this channel
     * @param multiplexer the multiplexer
     * @param in          the stream to receive data on
     * @param out         the stream to send data on
     */
    public Channel(int id, Multiplexer multiplexer,
                   MultiplexInputStream in, MultiplexOutputStream out) {
        _id = id;
        _multiplexer = multiplexer;
        _in = in;
        _out = out;
    }

    /**
     * Returns the channel identifier
     *
     * @return the channel identifier
     */
    public int getId() {
        return _id;
    }

    /**
     * Invoke a method on a remote object.
     *
     * @param request the request
     * @return the result of the invocation
     * @throws RemoteException if the distributed call cannot be made
     */
    public Response invoke(Request request) throws RemoteException {
        if (_log.isDebugEnabled()) {
            _log.debug("invoke() [channel=" + _id + "]");
        }
        Response response;
        ObjectOutputStream out = null;
        try {
            // set the packet type
            _out.setType(REQUEST);

            // write the request
            out = new ObjectOutputStream(_out);
            request.write(out);
        } catch (IOException exception) {
            throw new MarshalException("Failed to marshal call", exception);
        } catch (Exception exception) {
            throw new MarshalException("Failed to marshal call", exception);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore) {
                    // no-op
                }
            }
        }

        // read the response
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(_in);
            response = Response.read(in, request.getMethod());
        } catch (ClassNotFoundException exception) {
            throw new UnmarshalException("Failed to unmarshal response",
                                         exception);
        } catch (IOException exception) {
            throw new UnmarshalException("Failed to unmarshal response",
                                         exception);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignore) {
                // no-ops
            }
        }
        if (_log.isDebugEnabled()) {
            _log.debug("invoke() [channel=" + _id + "] - end");
        }
        return response;
    }

    /**
     * Read a request from the channel.
     * todo synchronization required due to scheduling in Multiplexer?
     *
     * @return the request
     * @throws IOException if the request can't be read
     */
    public synchronized Request readRequest() throws IOException {
        Request request;
        ObjectInputStream in = new ObjectInputStream(_in);
        request = Request.read(in);
        return request;
    }

    /**
     * Write a response to a request.
     * todo synchronization required due to scheduling in Multiplexer?
     *
     * @param response the response to write
     * @throws IOException for any I/O error
     */
    public synchronized void writeResponse(Response response)
            throws IOException {
        // set the packet type
        _out.setType(RESPONSE);

        // write the response
        ObjectOutputStream out = new ObjectOutputStream(_out);
        try {
            response.write(out);
        } finally {
            out.close();
        }
    }

    /**
     * Invoked when the underlying physical connection is closed.
     */
    public void disconnected() {
        if (_log.isDebugEnabled()) {
            _log.debug("disconnected [channel=" + _id + "]");
        }
        _in.disconnected();
        _out.disconnected();
    }

    /**
     * Returns the underlying input stream.
     *
     * @return the underlying input stream
     */
    public MultiplexInputStream getMultiplexInputStream() {
        return _in;
    }

    /**
     * Returns the underlying output stream.
     *
     * @return the underlying output stream
     */
    public MultiplexOutputStream getMultiplexOutputStream() {
        return _out;
    }

    /**
     * Releases this channel for re-use.
     */
    public void release() {
        _multiplexer.release(this);
    }

    /**
     * Closes this channel.
     *
     * @throws IOException for any I/O error
     */
    public void close() throws IOException {
        if (_multiplexer != null) {
            try {
                _multiplexer.close(this);
            } finally {
                _multiplexer = null;

                try {
                    _in.destroy();
                } catch (IOException ignore) {
                    // no need to propagate
                }
                try {
                    _out.close();
                } catch (IOException ignore) {
                    // no need to propagate
                }
            }
        }
    }

    /**
     * Destroy this channel.
     */
    public void destroy() {
        try {
            close();
        } catch (IOException exception) {
            _log.debug("close() failed", exception);
        }
    }

    /**
     * Returns a string representation of this.
     *
     * @return a string representation of this
     */
    public String toString() {
        return "Channel[id=" + _id + ", out=" + _out + ", in=" + _in + " ]";
    }

}
