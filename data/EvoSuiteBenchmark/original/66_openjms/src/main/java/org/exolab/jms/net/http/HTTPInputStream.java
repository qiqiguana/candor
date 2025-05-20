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
 * Copyright 2004-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: HTTPInputStream.java,v 1.3 2005/04/04 15:08:52 tanderson Exp $
 */
package org.exolab.jms.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Reads and buffers input from an {@link java.net.URLConnection}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/04/04 15:08:52 $
 */
class HTTPInputStream extends InputStream {

    /**
     * The connection identifier.
     */
    private final String _id;

    /**
     * The tunnel servlet URL.
     */
    private final URL _url;

    /**
     * The connection information.
     */
    private final HTTPRequestInfo _info;

    /**
     * The input stream from the servlet.
     */
    private InputStream _in;

    /**
     * The local data buffer.
     */
    private byte[] _data = new byte[1024];

    /**
     * Temporary buffer for single byte reads.
     */
    private final byte[] _byte = new byte[1];

    /**
     * The index into <code>_data</code> where data starts.
     */
    private int _index = 0;

    /**
     * The number of available bytes in <code>_data</code>.
     */
    private int _available = 0;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(HTTPInputStream.class);

    /**
     * Construct a new <code>HTTPInputStream</code>.
     *
     * @param id   the connection identifier
     * @param url  the URL to connect to
     * @param info the connection information
     */
    public HTTPInputStream(String id, URL url, HTTPRequestInfo info) {
        _id = id;
        _url = url;
        _info = info;
    }

    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     * <p/>
     * <p> A subclass must provide an implementation of this method.
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
        if (offset < 0 || length < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (length > 0) {
            if (_available == 0) {
                try {
                    doRead();
                } catch (IOException exception) {
                    _log.debug(exception, exception);
                    throw exception;
                }
            }
            count = (length <= _available) ? length : _available;
            if (_log.isDebugEnabled()) {
                _log.debug("read(length=" + length + "), [id=" + _id
                           + ", available=" + _available + "]");
            }

            if (count > 0) {
                // copy the available data into the buffer
                System.arraycopy(_data, _index, buffer, offset, count);
                _index += count;
                _available -= count;
            }
        }
        return count;
    }

    /**
     * Read from the connection and cache locally.
     *
     * @return the total number of bytes read into the buffer, or
     *         <code>-1</code> if there is no more data because the end of the
     *         stream has been reached.
     * @throws IOException if the read fails
     */
    protected int doRead() throws IOException {
        int count = 0;
        boolean done = false;
        while (!done) {
            if (_in == null) {
                connect();
                done = true;
            }
            count = _in.read(_data);
            if (count != -1) {
                _available = count;
                _index = 0;
                done = true;
            } else {
                _in.close();
                _in = null;
            }
        }
        return count;
    }

    /**
     * Connect to the tunnel servlet and get the input stream.
     *
     * @throws IOException for any I/O error
     */
    private void connect() throws IOException {
        int length = 0;
        HttpURLConnection connection = null;
        while (length == 0) {
            // poll the servlet until data is available, or an error
            // occurs
            connection = TunnelHelper.connect(_url, _id, "read", _info);
            length = connection.getContentLength();
            if (length == -1) {
                // throw new IOException("Content length not specified");
                // NOTE: above is commented out as it appears that the content
                // length may not always be set
            } else if (length == 0) {
                try {
                    // delay
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                }
            }
        }
        _in = connection.getInputStream();
        if (_log.isDebugEnabled()) {
            _log.debug("connect(), [id=" + _id
                       + ", contentLength=" + length + "]");
        }
    }
}
