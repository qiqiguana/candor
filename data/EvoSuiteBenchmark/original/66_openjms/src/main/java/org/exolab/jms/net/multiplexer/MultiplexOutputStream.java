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
 * $Id: MultiplexOutputStream.java,v 1.2 2005/04/02 13:23:12 tanderson Exp $
 */
package org.exolab.jms.net.multiplexer;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * An <code>OutputStream</code> which multiplexes data over a shared physical
 * connection, managed by a {@link Multiplexer}.
 * <p/>
 * <em>NOTE:</em> the <code>OutputStream</code> methods of this class are not
 * thread safe
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/04/02 13:23:12 $
 * @see Multiplexer
 */
class MultiplexOutputStream extends OutputStream implements Constants {

    /**
     * The channel identifier, used to associate packets with a channel.
     */
    private final int _channelId;

    /**
     * The packet type.
     */
    private byte _type;

    /**
     * The multiplexer which handles this stream's output.
     */
    private Multiplexer _multiplexer;

    /**
     * The local data buffer.
     */
    private byte[] _data;

    /**
     * The current index into <code>_data</code>.
     */
    private int _index;

    /**
     * The no. of bytes that the remote endpoint can currently accept.
     */
    private int _remoteSpace;

    /**
     * The maximum no. of bytes that the remote endpoint can accept.
     */
    private final int _maxRemoteSpace;

    /**
     * Indicates if the underlying connection has been closed.
     */
    private boolean _disconnected;

    /**
     * Synchronization helper.
     */
    private final Object _lock = new Object();

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(MultiplexOutputStream.class);


    /**
     * Construct a new <code>MultiplexOutputStream</code>.
     *
     * @param channelId   the channel identifier
     * @param multiplexer the multiplexer which handles this stream's output
     * @param size        the size of the local data buffer
     * @param remoteSize  the size of the remote endpoint's data buffer
     */
    public MultiplexOutputStream(int channelId, Multiplexer multiplexer,
                                 int size, int remoteSize) {
        _channelId = channelId;
        _multiplexer = multiplexer;
        _data = new byte[size];
        _maxRemoteSpace = remoteSize;
        _remoteSpace = remoteSize;
    }

    /**
     * Set the packet type.
     *
     * @param type the packet type
     */
    public void setType(byte type) {
        _type = type;
    }

    /**
     * This implementation flushes the stream, rather than closing it, as the
     * stream is re-used.
     *
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException {
        flush();
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be
     * written out.
     *
     * @throws IOException if an I/O error occurs
     */
    public void flush() throws IOException {
        int offset = 0;
        int length = _index;
        while (offset < _index) {
            int available = waitForSpace();
            int size = (length <= available) ? length : available;

            send(_data, offset, size);
            offset += size;
            length -= size;
        }
        _index = 0;
    }

    /**
     * Writes length bytes from the specified byte array starting at offset to
     * this output stream.
     *
     * @param buffer the data to write
     * @param offset the start offset in the data
     * @param length the number of bytes to write
     * @throws IOException if an I/O error occurs
     */
    public void write(byte[] buffer, int offset, int length)
            throws IOException {

        int space = _data.length - _index;
        if (space >= length) {
            // got enough space, so copy it to the buffer
            System.arraycopy(buffer, offset, _data, _index, length);
            _index += length;
        } else {
            flush();
            int size = length;
            // send the buffer, when the endpoint has enough free space
            while (size > 0) {
                int available = waitForSpace();
                int count = (size <= available) ? size : available;
                send(buffer, offset, count);
                offset += count;
                size -= count;
            }
        }
    }

    /**
     * Writes the specified byte to this output stream.
     *
     * @param value the byte value
     * @throws IOException if an I/O error occurs
     */
    public void write(int value) throws IOException {
        if (_index >= _data.length) {
            flush();
        }
        _data[_index++] = (byte) value;
    }

    /**
     * Notify this of the no. of bytes read by the remote endpoint.
     *
     * @param read the number of bytes read
     * @throws IOException if the no. of bytes exceeds that expected
     */
    public void notifyRead(int read) throws IOException {
        synchronized (_lock) {
            int space = _remoteSpace + read;
            if (space > _maxRemoteSpace) {
                throw new IOException("Remote space=" + space
                                      + " exceeds expected space="
                                      + _maxRemoteSpace);
            }
            _remoteSpace = space;

            if (_log.isDebugEnabled()) {
                _log.debug("notifyRead(read=" + read
                           + ") [channelId=" + _channelId
                           + ", remoteSpace=" + _remoteSpace
                           + "]");
            }
            _lock.notifyAll();
        }
    }

    /**
     * Invoked when the underlying physical connection is closed.
     */
    public void disconnected() {
        synchronized (_lock) {
            _disconnected = true;
            _lock.notifyAll();
        }
    }

    /**
     * Returns a string representation of this.
     *
     * @return a string representation of this
     */
    public String toString() {
        return "MultiplexOutputStream[index=" + _index + "]";
    }

    /**
     * Sends length bytes from the specified byte array starting at offset to
     * the endpoint.
     *
     * @param buffer the data to write
     * @param offset the start offset in the data
     * @param length the number of bytes to write
     * @throws IOException if an I/O error occurs
     */
    private void send(byte[] buffer, int offset, int length)
            throws IOException {
        if (_log.isDebugEnabled()) {
            _log.debug("send(length=" + length + ") [channelId=" + _channelId
                       + ", remoteSpace=" + _remoteSpace
                       + "]");
        }
        synchronized (_lock) {
            _multiplexer.send(_type, _channelId, buffer, offset, length);
            _type = DATA;

            _remoteSpace -= length;

/*
            if (_log.isDebugEnabled()) {
                StringBuffer buf = new StringBuffer();
                for (int i = 0; i < length; ++i) {
                    if (i > 0) {
                        buf.append(", ");
                    }
                    final int mask = 0xff;
                    int value = buffer[offset + i] & mask;
                    buf.append(Integer.toHexString(value));
                }
                _log.debug("send[channelId=" + _channelId + "], length="
                           + length + ", data=" + buf);
            }
*/
        }
    }

    /**
     * Returns immediately if the endpoint can receive data, otherwise blocks,
     * waiting for the endpoint to have space available.
     *
     * @return the number of bytes that the endpoint can accept
     * @throws IOException if the connection is closed while blocking
     */
    private int waitForSpace() throws IOException {
        int available = 0;
        while (!_disconnected) {
            synchronized (_lock) {
                if (_log.isDebugEnabled()) {
                    _log.debug("waitForSpace() [channelId=" + _channelId
                               + ", remoteSpace=" + _remoteSpace
                               + "]");
                }

                if (_remoteSpace > 0) {
                    available = _remoteSpace;
                    break;
                } else {
                    try {
                        _lock.wait();
                    } catch (InterruptedException ignore) {
                    }
                }
            }
        }
        if (_disconnected) {
            throw new IOException("Connection has been closed");
        }

        return available;
    }

}
