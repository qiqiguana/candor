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
 * $Id: ChannelInvocation.java,v 1.1 2005/06/04 14:34:45 tanderson Exp $
 */
package org.exolab.jms.net.multiplexer;

import java.io.IOException;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.connector.Invocation;
import org.exolab.jms.net.connector.Request;
import org.exolab.jms.net.connector.Response;
import org.exolab.jms.net.connector.InvocationHandler;


/**
 * <code>ChannelInvocation</code> is used by
 * {@link MultiplexedManagedConnection} to pass remote method invocations
 * on a {@link Channel} to an {@link InvocationHandler}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/06/04 14:34:45 $
 */
class ChannelInvocation implements Invocation {

    /**
     * The channel to use.
     */
    private final Channel _channel;

    /**
     * The caller peforming the invocation.
     */
    private final Caller _caller;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(ChannelInvocation.class);


    /**
     * Construct a new <code>ChannelInvocation</code>.
     *
     * @param channel the channel to use
     * @param caller  the caller performing the invocation
     */
    public ChannelInvocation(Channel channel, Caller caller) {
        _channel = channel;
        _caller = caller;
    }

    /**
     * Reads the request from the channel.
     *
     * @return the request
     * @throws IOException if the request can't be read
     */
    public Request getRequest() throws IOException {
        return _channel.readRequest();
    }

    /**
     * Returns the caller performing the invocation.
     *
     * @return the caller
     */
    public Caller getCaller() {
        return _caller;
    }

    /**
     * Writes the response to the channel.
     *
     * @param response the response to write
     */
    public void setResponse(Response response) {
        try {
            _channel.writeResponse(response);
        } catch (Exception exception) {
            _log.debug(exception, exception);
        }
    }

}
