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
 * $Id: HTTPOutputStream.java,v 1.2 2005/04/04 15:08:53 tanderson Exp $
 */
package org.exolab.jms.net.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Writes to an {@link URLConnection}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/04/04 15:08:53 $
 */
class HTTPOutputStream extends OutputStream {

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
     * The local data buffer.
     */
    private final byte[] _data;

    /**
     * The current index into <code>_data</code>.
     */
    private int _index;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(HTTPOutputStream.class);


    /**
     * Construct a new <code>HTTPOutputStream</code>.
     *
     * @param id   the connection identifier
     * @param url  the URL to connect to
     * @param size the size of the buffer
     * @param info the connection information
     */
    public HTTPOutputStream(String id, URL url, int size, HTTPRequestInfo info) {
        _id = id;
        _url = url;
        _data = new byte[size];
        _info = info;
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be
     * written out.
     *
     * @throws IOException if an I/O error occurs
     */
    public void flush() throws IOException {
        while (_index > 0) {
            doWrite();
        }
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
            doWrite(buffer, offset, length);
        }
    }

    /**
     * Writes the specified byte to this output stream.
     *
     * @param value the byte value
     * @throws IOException if an I/O error occurs
     */
    public void write(int value) throws IOException {
        while (_index >= _data.length) {
            flush();
        }
        _data[_index++] = (byte) value;
    }

    /**
     * Writes from the local data buffer to the underlying connection.
     *
     * @throws IOException if an I/O error occurs
     */
    private void doWrite() throws IOException {
        try {
            doWrite(_data, 0, _index);
            _index = 0;
        } catch (IOException exception) {
            _log.debug(exception, exception);
            throw exception;
        }
    }

    /**
     * Writes length bytes from the specified byte array starting at offset to
     * the underlying connection.
     *
     * @param buffer the data to write
     * @param offset the start offset in the data
     * @param length the number of bytes to write
     * @throws IOException if an I/O error occurs
     */
    private void doWrite(byte[] buffer, int offset, int length)
            throws IOException {

        HttpURLConnection connection =
                TunnelHelper.create(_url, _id, "write", _info);
        connection.setDoOutput(true);
        OutputStream out = connection.getOutputStream();
        out.write(buffer, offset, length);
        out.close();
        if (_log.isDebugEnabled()) {
            _log.debug("doWrite(length=" + length + "), [id=" + _id + "]");
        }

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException(connection.getResponseCode() + " "
                                  + connection.getResponseMessage());
        }

    }

}
