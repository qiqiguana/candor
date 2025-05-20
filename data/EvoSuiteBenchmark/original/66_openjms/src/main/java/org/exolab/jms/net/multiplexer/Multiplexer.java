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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: Multiplexer.java,v 1.9 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.multiplexer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.common.security.BasicPrincipal;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.SecurityException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * This class multiplexes data over a physical connection.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.9 $ $Date: 2006/12/16 12:37:17 $
 */
public class Multiplexer implements Constants, Runnable {

    /**
     * The listener to notify.
     */
    private MultiplexerListener _listener;

    /**
     * If <code>true</code>, indicates that the multiplexer has been closed.
     */
    private volatile boolean _closed;

    /**
     * The endpoint.
     */
    private Endpoint _endpoint;

    /**
     * The endpoint's output stream.
     */
    private DataOutputStream _out;

    /**
     * The endpoint's input stream.
     */
    private DataInputStream _in;

    /**
     * The set of channels managed by this, keyed on channel identifier.
     */
    private final HashMap _channels = new HashMap();

    /**
     * The set of free channels, keyed on channel identifier.
     */
    private final LinkedList _free = new LinkedList();

    /**
     * If <code>true</code>, indicates that the physical connection was opened
     * (client), rather than accepted (server). This is used in channel
     * identifier generation
     */
    private boolean _client = false;

    /**
     * The channel identifier seed.
     */
    private int _seed = 0;

    /**
     * The principal that owns the connection, or <code>null</code>,
     * if this is an unauthenticated connection.
     */
    private Principal _principal;

    /**
     * The sending and receiving buffer size, in bytes.
     */
    private static final int BUFFER_SIZE = 2048;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(Multiplexer.class);

    /**
     * Construct a new client-side <code>Multiplexer</code>.
     *
     * @param listener  the multiplexer listener
     * @param endpoint  the endpoint to multiplex messages over
     * @param principal the security principal
     * @throws IOException       if an I/O error occurs
     * @throws SecurityException if connection is refused by the server
     */
    public Multiplexer(MultiplexerListener listener, Endpoint endpoint,
                       Principal principal)
            throws IOException, SecurityException {
        initialise(listener, endpoint, true);
        authenticate(principal);
    }

    /**
     * Construct a new server-side <code>Multiplexer</code>.
     *
     * @param listener      the multiplexer listener
     * @param endpoint      the endpoint to multiplex messages over
     * @param authenticator the connection authenticator
     * @throws IOException       if an I/O error occurs
     * @throws ResourceException if the authenticator cannot authenticate
     */
    public Multiplexer(MultiplexerListener listener, Endpoint endpoint,
                       Authenticator authenticator)
            throws IOException, ResourceException {
        initialise(listener, endpoint, false);
        authenticate(authenticator);
    }

    /**
     * Construct a new <code>Multiplexer</code>.
     * <p/>
     * This constructor is provided for subclasses that must perform setup
     * work prior to invoking {@link #initialise}
     */
    protected Multiplexer() {
    }

    /**
     * Start multiplexing.
     */
    public void run() {
        while (!_closed) {
            multiplex();
        }
    }

    /**
     * Returns a free channel from the pool, opening a new one if none are
     * available.
     *
     * @return a free channel
     * @throws IOException if an I/O error occurs
     */
    public Channel getChannel() throws IOException {
        Channel channel = null;

        synchronized (_free) {
            if (!_free.isEmpty()) {
                channel = (Channel) _free.removeFirst();
            }
        }

        if (channel == null) {
            channel = open();
        }

        return channel;
    }

    /**
     * Releases a channel back to the pool.
     *
     * @param channel the channel to release
     */
    public void release(Channel channel) {
        synchronized (_free) {
            _free.add(channel);
        }
    }

    /**
     * Close a channel.
     *
     * @param channel the channel to close
     * @throws IOException if an I/O error occurs
     */
    public void close(Channel channel) throws IOException {
        int channelId = channel.getId();
        synchronized (_channels) {
            _channels.remove(new Integer(channelId));
        }

        send(CLOSE, channelId);
    }

    /**
     * Send a message.
     *
     * @param type the packet type
     * @throws IOException if an I/O error occurs
     */
    public void send(byte type) throws IOException {
        synchronized (_out) {
            _out.writeByte(type);
            _out.flush();
            if (_log.isDebugEnabled()) {
                _log.debug("send(type=0x" + Integer.toHexString(type) + ")");
            }
        }
    }

    /**
     * Send a message.
     *
     * @param type      the packet type
     * @param channelId the identifier of the channel sending the message
     * @throws IOException if an I/O error occurs
     */
    public void send(byte type, int channelId) throws IOException {
        synchronized (_out) {
            _out.writeByte(type);
            _out.writeShort(channelId);
            _out.flush();
            if (_log.isDebugEnabled()) {
                _log.debug("send(type=0x" + Integer.toHexString(type)
                        + ", channel=" + channelId + ")");
            }
        }
    }

    /**
     * Send a message.
     *
     * @param type      the packet type
     * @param channelId the identifier of the channel sending the message
     * @param data      the data to send
     * @throws IOException if an I/O error occurs
     */
    public void send(byte type, int channelId, int data) throws IOException {
        synchronized (_out) {
            _out.writeByte(type);
            _out.writeShort(channelId);
            _out.writeInt(data);
            _out.flush();
            if (_log.isDebugEnabled()) {
                _log.debug("send(type=" + type + ", channel=" + channelId
                        + ", data=" + Integer.toHexString(data) + ")");
            }
        }
    }

    /**
     * Send a message.
     *
     * @param type      the packet type
     * @param channelId the identifier of the channel sending the message
     * @param data      the data to send
     * @param offset    the offset into the data
     * @param length    the length of data
     * @throws IOException if an I/O error occurs
     */
    public void send(byte type, int channelId, byte[] data, int offset,
                     int length) throws IOException {
        synchronized (_out) {
            _out.writeByte(type);
            _out.writeShort(channelId);
            _out.writeInt(length);
            _out.write(data, offset, length);
            _out.flush();
        }
    }

    /**
     * Ping the connection.
     *
     * @param token the token to be returned in the reply
     * @throws IOException if an I/O error occurs
     */
    public void ping(int token) throws IOException {
        synchronized (_out) {
            _out.writeByte(PING_REQUEST);
            _out.writeInt(token);
            _out.flush();
            if (_log.isDebugEnabled()) {
                _log.debug("ping(token=" + token + ")");
            }
        }
    }

    /**
     * Close the multiplexer, releasing any resources. This closes the socket
     * and waits for the thread to terminate.
     */
    public void close() {
        if (!_closed) {
            _closed = true;
            try {
                send(SHUTDOWN);
            } catch (IOException exception) {
                _log.debug(exception);
            }
            try {
                _endpoint.close();
            } catch (IOException exception) {
                _log.debug(exception);
            }
            // _pool.shutdownAfterProcessingCurrentlyQueuedTasks();
            // @todo - as the pool is shared, need to block for
            // tasks queued by this
        }
    }

    /**
     * Determines if the multiplexer is closed.
     *
     * @return <code>true</code> if the multiplexer is closed
     */
    public boolean isClosed() {
        return _closed;
    }

    /**
     * Determines if this is a client-side instance.
     *
     * @return <code>true</code> if this is a client-side instance,
     *         <code>false</code> if it is a server=side instance
     */
    public boolean isClient() {
        return _client;
    }

    /**
     * Returns the principal that owns the connection.
     *
     * @return the principal that owns the connection, or <code>null<code>
     *         if this is an unauthenticated connection
     */
    public Principal getPrincipal() {
        return _principal;
    }

    /**
     * Initialise the multiplexer.
     *
     * @param listener the multiplexer listener
     * @param endpoint the endpoint to multiplex messages over
     * @param client   determines if this is a client-side or server-side
     *                 instance
     * @throws IOException if an I/O error occurs
     */
    protected void initialise(MultiplexerListener listener, Endpoint endpoint,
                              boolean client)
            throws IOException {


        if (listener == null) {
            throw new IllegalArgumentException("Argument 'listener' is null");
        }
        if (endpoint == null) {
            throw new IllegalArgumentException("Argument 'endpoint' is null");
        }
        if (_log.isDebugEnabled()) {
            _log.debug("Multiplexer(uri=" + endpoint.getURI()
                    + ", client=" + client);
        }
        _listener = listener;
        _endpoint = endpoint;
        _out = new DataOutputStream(endpoint.getOutputStream());
        _in = new DataInputStream(endpoint.getInputStream());
        _client = client;
        handshake(_out, _in);
    }

    /**
     * Perform handshaking on initial connection, to verify protocol. Subclasses
     * may extend this behaviour.
     *
     * @param out the endpoint's output stream
     * @param in  the endpoint's input stream
     * @throws IOException for any I/O error
     */
    protected void handshake(DataOutputStream out, DataInputStream in)
            throws IOException {
        out.writeInt(MAGIC);
        out.writeInt(VERSION);
        out.flush();

        int magic = in.readInt();
        if (magic != MAGIC) {
            throw new ProtocolException("Expected protocol magic=" + MAGIC
                    + ", but received=" + magic);
        }
        int version = in.readInt();
        if (version != VERSION) {
            throw new ProtocolException("Expected protocol version=" + VERSION
                    + ", but received=" + version);
        }
    }

    /**
     * Perform authentication on initial connection.
     *
     * @param principal the security principal. May be <code>null</code>
     * @throws IOException       for any I/O error
     * @throws java.lang.SecurityException if connection is refused by the server
     */
    protected void authenticate(Principal principal)
            throws IOException, SecurityException {
        try {
            if (principal != null && !(principal instanceof BasicPrincipal)) {
                throw new IOException(
                        "Cannot authenticate with principal of type "
                                + principal.getClass().getName());
            }
            if (principal != null) {
                BasicPrincipal basic = (BasicPrincipal) principal;
                _out.writeByte(AUTH_BASIC);
                _out.writeUTF(basic.getName());
                _out.writeUTF(basic.getPassword());
            } else {
                _out.writeByte(AUTH_NONE);
            }
            _out.flush();
            if (_in.readByte() != AUTH_OK) {
                throw new SecurityException("Connection refused");
            }
        } catch (IOException exception) {
            // terminate the connection
            _endpoint.close();
            throw exception;
        }
        _principal = principal;
    }

    /**
     * Performs authentication on initial connection.
     *
     * @param authenticator the authenticator
     * @throws IOException       for any I/O error
     * @throws ResourceException if the authenticator cannot authenticate
     */
    protected void authenticate(Authenticator authenticator)
            throws IOException, ResourceException {

        try {
            Principal principal = null;
            byte type = _in.readByte();

            switch (type) {
                case AUTH_BASIC:
                    String name = _in.readUTF();
                    String password = _in.readUTF();
                    principal = new BasicPrincipal(name, password);
                    break;
                case AUTH_NONE:
                    break;
                default:
                    throw new IOException("Invalid packet type: " + type);
            }
            if (authenticator.authenticate(principal)) {
                _out.writeByte(AUTH_OK);
                _out.flush();
            } else {
                _out.writeByte(AUTH_DENIED);
                _out.flush();
                throw new SecurityException("User " + principal
                        + " unauthorised");
            }
            _principal = principal;
        } catch (IOException exception) {
            // terminate the connection
            _endpoint.close();
            throw exception;
        } catch (ResourceException exception) {
            // terminate the connection
            _endpoint.close();
            throw exception;
        }
    }

    /**
     * Opens a new channel.
     *
     * @return a new channel
     * @throws IOException if a channel can't be opened
     */
    protected Channel open() throws IOException {
        Channel channel;
        int channelId;
        synchronized (_channels) {
            channelId = getNextChannelId();
            channel = addChannel(channelId);
        }

        send(OPEN, channelId);
        return channel;
    }

    /**
     * Read a packet from the endpoint.
     */
    private void multiplex() {
        try {
            byte type = _in.readByte();
            switch (type) {
                case OPEN:
                    handleOpen();
                    break;
                case CLOSE:
                    handleClose();
                    break;
                case REQUEST:
                    handleRequest();
                    break;
                case RESPONSE:
                    handleResponse();
                    break;
                case DATA:
                    handleData();
                    break;
                case PING_REQUEST:
                    handlePingRequest();
                    break;
                case PING_RESPONSE:
                    handlePingResponse();
                    break;
                case FLOW_READ:
                    handleFlowRead();
                    break;
                case SHUTDOWN:
                    handleShutdown();
                    break;
                default:
                    throw new IOException("Unrecognised message type: "
                            + type);
            }
        } catch (Exception exception) {
            boolean closed = _closed;
            shutdown();
            if (!closed) {
                _log.debug("Multiplexer shutting down on error", exception);
                // error notify the listener
                _listener.error(exception);
            }
        }
    }

    /**
     * Shuts down the multiplexer.
     */
    private void shutdown() {
        // mark this as closed
        _closed = true;

        // notify the channels
        Channel[] channels;
        synchronized (_channels) {
            channels = (Channel[]) _channels.values().toArray(new Channel[0]);
        }
        for (int i = 0; i < channels.length; ++i) {
            channels[i].disconnected();
        }
        try {
            _endpoint.close();
        } catch (IOException exception) {
            _log.debug(exception);
        }
    }

    /**
     * Open a new channel.
     *
     * @throws IOException for any error
     */
    private void handleOpen() throws IOException {
        int channelId = _in.readUnsignedShort();
        Integer key = new Integer(channelId);

        synchronized (_channels) {
            if (_channels.get(key) != null) {
                throw new IOException(
                        "A channel already exists with identifier: " + key);
            }
            addChannel(channelId);
        }
    }

    /**
     * Close a channel.
     *
     * @throws IOException for any error
     */
    private void handleClose() throws IOException {
        int channelId = _in.readUnsignedShort();
        Integer key = new Integer(channelId);

        synchronized (_channels) {
            Channel channel = (Channel) _channels.remove(key);
            if (channel == null) {
                throw new IOException(
                        "No channel exists with identifier: " + key);
            }
            channel.close();
        }
    }

    /**
     * Handle a <code>REQUEST</code> packet.
     *
     * @throws IOException if an I/O error occurs, or no channel exists matching
     *                     that read from the packet
     */
    private void handleRequest() throws IOException {
        final Channel channel = handleData();
        if (_log.isDebugEnabled()) {
            _log.debug("handleRequest() [channel=" + channel.getId() + "]");
        }
        // todo - need to handle closed()
        _listener.request(channel);

        if (_log.isDebugEnabled()) {
            _log.debug("handleRequest() [channel=" + channel.getId()
                    + "] - end");
        }
    }

    /**
     * Handle a <code>RESPONSE</code> packet.
     *
     * @throws IOException if an I/O error occurs, or no channel exists matching
     *                     that read from the packet
     */
    private void handleResponse() throws IOException {
        handleData();
    }

    /**
     * Handle a <code>PING_REQUEST</code> packet.
     *
     * @throws IOException if an I/O error occurs
     */
    private void handlePingRequest() throws IOException {
        int token = _in.readInt();
        synchronized (_out) {
            _out.writeByte(PING_RESPONSE);
            _out.writeInt(token);
            _out.flush();
            if (_log.isDebugEnabled()) {
                _log.debug("pinged(token=" + token + ")");
            }
        }
    }

    /**
     * Handle a <code>PING_RESPONSE</code> packet.
     *
     * @throws IOException if an I/O error occurs
     */
    private void handlePingResponse() throws IOException {
        int token = _in.readInt();
        _listener.pinged(token);
    }

    /**
     * Handle a <code>DATA</code> packet.
     *
     * @return the channel to handle the packet
     * @throws IOException if an I/O error occurs, or no channel exists matching
     *                     that read from the packet
     */
    private Channel handleData() throws IOException {
        Channel channel = readChannel();
        int length = _in.readInt();
        channel.getMultiplexInputStream().receive(_in, length);
        return channel;
    }

    /**
     * Handle a <code>FLOW_READ</code> packet.
     *
     * @throws IOException if an I/O error occurs
     */
    private void handleFlowRead() throws IOException {
        Channel channel = readChannel();
        int read = _in.readInt();
        channel.getMultiplexOutputStream().notifyRead(read);
    }

    /**
     * Handle a <code>SHUTDOWN</code> packet.
     */
    private void handleShutdown() {
        shutdown();
        _listener.closed();
    }

    /**
     * Adds a new channel.
     * <p/>
     * NOTE: Must be invoked with <code>_channels</code> synchronized
     *
     * @param channelId the channel identifier
     * @return the new channel
     */
    private Channel addChannel(int channelId) {
        int size = BUFFER_SIZE;
        MultiplexOutputStream out =
                new MultiplexOutputStream(channelId, this, size, size);
        MultiplexInputStream in =
                new MultiplexInputStream(channelId, this, size);
        Channel channel = new Channel(channelId, this, in, out);
        _channels.put(new Integer(channelId), channel);
        return channel;
    }

    /**
     * Reads the channel identifier from the stream and returns the
     * corresponding channel.
     *
     * @return the channel corresponding to the read channel identifier
     * @throws IOException for any I/O error, or if there is no corresponding
     *                     channel
     */
    private Channel readChannel() throws IOException {
        int channelId = _in.readUnsignedShort();
        return getChannel(channelId);
    }

    /**
     * Returns a channel given its identifier.
     *
     * @param channelId the channel identifier
     * @return the channel corresponding to <code>channelId</code>
     * @throws IOException if there is no corresponding channel
     */
    private Channel getChannel(int channelId) throws IOException {
        Channel channel;
        Integer key = new Integer(channelId);
        synchronized (_channels) {
            channel = (Channel) _channels.get(key);
            if (channel == null) {
                throw new IOException(
                        "No channel exists with identifier: " + channelId);
            }
        }
        return channel;
    }

    /**
     * Returns the next available channel identifier. Channel identifiers
     * generated on the client side are in the range 0x0..0x7FFF, on the server
     * side, 0x8000-0xFFFF
     * <p/>
     * NOTE: Must be invoked with <code>_channels</code> synchronized
     *
     * @return the next channel identifier
     * @throws IOException if the connection is closed
     */
    private int getNextChannelId() throws IOException {
        final int mask = 0x7fff;
        final int serverIdBase = 0x8000;
        int channelId = 0;
        while (!_closed) {
            _seed = (_seed + 1) & mask;
            channelId = (_client) ? _seed : _seed + serverIdBase;
            if (!_channels.containsKey(new Integer(channelId))) {
                break;
            }
        }
        if (_closed) {
            throw new IOException("Connection has been closed");
        }
        return channelId;
    }

}
