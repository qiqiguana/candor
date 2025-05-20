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
 * $Id: MultiplexInputStream.java,v 1.2 2005/04/02 13:23:12 tanderson Exp $
 */
package org.exolab.jms.net.multiplexer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * An <code>InputStream</code> which reads multiplexed data over a shared
 * physical connection, managed by a {@link Multiplexer}.
 * <p/>
 * <em>NOTE:</em> the <code>InputStream</code> methods of this class are not
 * thread safe
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $
 * @see Multiplexer
 */
class MultiplexInputStream extends InputStream implements Constants {

    /**
     * The channel identifier.
     */
    private final int _channelId;

    /**
     * The multiplexer.
     */
    private Multiplexer _multiplexer;

    /**
     * The local data buffer.
     */
    private byte[] _data;

    /**
     * Temporary buffer for single byte reads.
     */
    private byte[] _byte = new byte[1];

    /**
     * The index into <code>_data</code> where data starts.
     */
    private int _index = 0;

    /**
     * The number of available bytes in <code>_data</code>.
     */
    private int _available = 0;

    /**
     * Indicates if the underyling connection has been closed.
     */
    private boolean _disconnected = false;

    /**
     * The no. of bytes to read before notifying the remote endpoint.
     */
    private final int _lowWaterMark;

    /**
     * The number of bytes read from this stream since the last.
     * <code>notifyRead()</code> call
     */
    private int _read = 0;

    /**
     * Synchronization helper.
     */
    private final Object _lock = new Object();

    /**
     * The logger.
     */
    private final Log _log = LogFactory.getLog(MultiplexInputStream.class);


    /**
     * Construct a new <code>MultiplexInputStream</code>.
     *
     * @param channelId   the channel identifier
     * @param multiplexer the multiplexer
     * @param size        the size of the local data buffer
     */
    public MultiplexInputStream(int channelId, Multiplexer multiplexer,
                                int size) {
        _channelId = channelId;
        _multiplexer = multiplexer;
        _data = new byte[size];
        _lowWaterMark = size / 2;
    }

    /**
     * This implementation is a no-op, as the stream is re-used.
     */
    public void close() {
    }

    /**
     * Closes this input stream and releases any resources associated with it.
     *
     * @throws IOException if an I/O error occurs
     */
    public void destroy() throws IOException {
        // notify the endpoint iff it hasn't notified this of disconnection
        synchronized (_lock) {
            if (!_disconnected) {
                //_multiplexer.closed(this);
            }
        }
        _multiplexer = null;
        _data = null;
    }

    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     *
     * @return the next byte of data, or <code>-1</code> if the end of the
     *         stream is reached.
     * @throws IOException if an I/O error occurs.
     */
    public int read() throws IOException {
        final int mask = 0xFF;
        int count = read(_byte, 0, 1);
        return (count == 1) ? _byte[0] & mask : -1;
    }

    /**
     * Reads up to <code>length</code> bytes of data from the input stream into
     * an array of bytes.  An attempt is made to read as many as
     * <code>length</code> bytes, but a smaller number may be read, possibly
     * zero. The number of bytes actually read is returned as an integer.
     * <p/>
     * <p> If the first byte cannot be read for any reason other than end of
     * file, then an <code>IOException</code> is thrown. In particular, an
     * <code>IOException</code> is thrown if the input stream has been closed.
     *
     * @param buffer the buffer into which the data is read
     * @param offset the start offset in array <code>buffer</code> at which the
     *               data is written
     * @param length the maximum number of bytes to read
     * @return the total number of bytes read into the buffer, or
     *         <code>-1</code> if there is no more data because the end of the
     *         stream has been reached.
     * @throws IOException               if an I/O error occurs.
     * @throws IndexOutOfBoundsException if <code>offset</code> is negative, or
     *                                   <code>length</code> is negative, or
     *                                   <code>offset+length</code> is greater
     *                                   than the length of the array
     * @throws NullPointerException      if <code>buffer</code> is null
     */
    public int read(byte[] buffer, int offset, int length) throws IOException {
        int count = 0;
        if (length > 0) {
            synchronized (_lock) {
                count = (length <= _available) ? length : _available;
                if (_log.isDebugEnabled()) {
                    _log.debug("read(length=" + length + ") [channelId="
                               + _channelId
                               + ", available=" + _available + "]");
                }

                if (count > 0) {
                    // copy the available data into the buffer
                    copy(buffer, offset, count);
                }

                if (count < length) {
                    // wait for more data to become available
                    int more = length - count;
                    while ((_available < more) && !_disconnected) {
                        if (_log.isDebugEnabled()) {
                            _log.debug("read() waiting on data [channelId="
                                       + _channelId
                                       + ", available=" + _available
                                       + ", requested=" + more + "]");
                        }

                        try {
                            _lock.wait();
                        } catch (InterruptedException ignore) {
                        }
                    }

                    if (_available > 0) {
                        // more data available, so copy it
                        more = (more <= _available) ? more : _available;
                        offset += count;
                        copy(buffer, offset, more);
                        count += more;
                    }
                }

                if ((count == 0) && _disconnected) {
                    // no data was read, and we were disconnected. Indicate
                    // end of stream to user
                    count = -1;
                }
            }
        }
        return count;
    }

    /**
     * Returns the number of bytes that can be read (or skipped over) from this
     * input stream without blocking by the next caller of a method for this
     * input stream.
     *
     * @return the number of bytes that can be read from this input stream
     *         without blocking.
     */
    public int available() {
        int result;
        synchronized (_lock) {
            result = _available;
        }
        return result;
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
        return "MultiplexInputStream[available=" + _available + "]";
    }

    /**
     * Invoked by {@link Multiplexer} when data is available for this stream.
     *
     * @param input  the stream to read data from
     * @param length the number of bytes to read
     * @throws IOException if an I/O error occurs
     */
    protected void receive(DataInputStream input, int length)
            throws IOException {

        synchronized (_lock) {
            int space = _data.length - _available;
            if (length > space) {
                throw new IOException("Buffer overflow: buffer size="
                                      + _data.length
                                      + ", space available=" + space
                                      + ", requested size=" + length);
            }

            int freeAtEnd = _data.length - (_index + _available);
            if (length > freeAtEnd) {
                // make space at the end of the buffer, by shuffling data
                // to the start
                System.arraycopy(_data, _index, _data, 0, _available);
                _index = 0;
            }
            input.readFully(_data, _index + _available, length);

            if (_log.isDebugEnabled()) {
                _log.debug("receive(length=" + length
                           + ") [channelId=" + _channelId
                           + ", available=" + _available
                           + ", space=" + (_data.length - _available) + "]");

/*
                StringBuffer buf = new StringBuffer();
                for (int i = 0; i < length; ++i) {
                    if (i > 0) {
                      buf.append(", ");
                    }
                    final int mask = 0xff;
                    int value = _data[_index + i + _available] & mask;
                    buf.append(Integer.toHexString(value));
                }
                _log.debug("receive[channelId=" + _channelId
                           + "], length=" + length + ", data=" + buf);
*/
            }

            _available += length;

            _lock.notifyAll();
        }
    }

    /**
     * Helper to copy data to a user buffer, notifying the remote endpoint if
     * more data should be sent.
     *
     * @param buffer the buffer into which the data is read
     * @param offset the start offset in array <code>buffer</code> at which the
     *               data is written
     * @param length the maximum number of bytes to read
     * @throws IOException               if an I/O error occurs.
     * @throws IndexOutOfBoundsException if <code>offset</code> is negative, or
     *                                   <code>length</code> is negative, or
     *                                   <code>offset+length</code> is greater
     *                                   than the length of the array
     * @throws NullPointerException      if <code>buffer</code> is null
     */
    private void copy(byte[] buffer, int offset, int length)
            throws IOException {

        System.arraycopy(_data, _index, buffer, offset, length);
        _index += length;
        _available -= length;
        _read += length;
        if (_read >= _lowWaterMark) {
            notifyRead();
        }
    }

    /**
     * Notify the remote endpoint of the current no. of bytes read.
     *
     * @throws IOException if the notification fails
     */
    private void notifyRead() throws IOException {
        if (_log.isDebugEnabled()) {
            _log.debug("notifyRead() [channelId=" + _channelId
                       + ", read=" + _read + "]");
        }
        _multiplexer.send(FLOW_READ, _channelId, _read);
        _read = 0;
    }

}
