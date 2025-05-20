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
 * $Id: Constants.java,v 1.3 2005/05/30 13:34:49 tanderson Exp $
 */
package org.exolab.jms.net.multiplexer;


/**
 * Protocol constants for the multiplexer
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/05/30 13:34:49 $
 */
interface Constants {

    /**
     * Magic no. used in stream verification.
     */
    int MAGIC = 0xBADDECAF;

    /**
     * Indicates that the packet contains protocol version.
     */
    int VERSION = 0x00000001;

    /**
     * Indicates that a packet is a request to open a new channel.
     */
    byte OPEN = 0x20;

    /**
     * Indicates that a packet is a request to close an existing channel.
     */
    byte CLOSE = 0x21;

    /**
     * Indicates the start of an invocation request.
     */
    byte REQUEST = 0x30;

    /**
     * Indicates the start of an invocation return.
     */
    byte RESPONSE = 0x31;

    /**
     * Indicates the continuation of a stream of data packets.
     */
    byte DATA = 0x32;

    /**
     * Indicates that the client is supplying user/password authentication
     * details.
     */
    byte AUTH_BASIC = 0x40;

    /**
     * Indicates that a client is supplying no authentication details.
     */
    byte AUTH_NONE = 0x41;

    /**
     * Indicates that connection has been accepted.
     */
    byte AUTH_OK = 0x4E;

    /**
     * Indicates that connection has been refused.
     */
    byte AUTH_DENIED = 0x4F;

    /**
     * Indicates that a packet is a ping request.
     */
    byte PING_REQUEST = 0x50;

    /**
     * Indicates that a packet is a ping response.
     */
    byte PING_RESPONSE = 0x51;

    /**
     * Indicates the no. of bytes read from the stream.
     */
    byte FLOW_READ = 0x60;

    /**
     * Indicates to close the connection.
     */
    byte SHUTDOWN = 0x70;

}
