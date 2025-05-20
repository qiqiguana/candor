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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: MarshalledInvocation.java,v 1.2 2005/07/22 23:40:36 tanderson Exp $
 */

package org.exolab.jms.net.connector;

import java.io.IOException;
import java.rmi.MarshalledObject;

import EDU.oswego.cs.dl.util.concurrent.Latch;


/**
 * An <code>Invocation</code> where the {@link Request} is wrapped
 * in an {@link MarshalledObject}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/07/22 23:40:36 $
 */
public class MarshalledInvocation implements Invocation {

    /**
     * The marshalled {@link Request} instance.
     */
    private final MarshalledObject _request;

    /**
     * The result of the invocation.
     */
    private Response _response;

    /**
     * The caller performing the invocation.
     */
    private final Caller _caller;

    /**
     * Latch to force clients to wait until the invocation is complete.
     */
    private final Latch _latch = new Latch();


    /**
     * Construct a new <code>MarshalledInvocation</code>.
     *
     * @param request the marshalled {@link Request} instance
     * @param caller  the caller performing the invocation
     */
    public MarshalledInvocation(MarshalledObject request, Caller caller) {
        _request = request;
        _caller = caller;
    }

    /**
     * Returns the request.
     *
     * @return the request
     * @throws Throwable for any error
     */
    public Request getRequest() throws Throwable {
        return (Request) _request.get();
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
     * Sets the result of the invocation.
     *
     * @param response the result
     */
    public void setResponse(Response response) {
        _response = response;
        _latch.release();
    }

    /**
     * Returns the result of the invocation.
     *
     * @return the result of the invocation.
     * @throws InterruptedException if interrupted
     */
    public Response getResponse() throws InterruptedException {
        _latch.acquire();
        return _response;
    }

    /**
     * Returns the result of the invocation, wrapped in a {@link
     * MarshalledObject}.
     *
     * @return the result of the invocation.
     * @throws IOException if the response can't be marshalld
     * @throws InterruptedException if interrupted
     */
    public MarshalledObject getMarshalledResponse()
            throws IOException, InterruptedException {
        return new MarshalledObject(getResponse());
    }

}
