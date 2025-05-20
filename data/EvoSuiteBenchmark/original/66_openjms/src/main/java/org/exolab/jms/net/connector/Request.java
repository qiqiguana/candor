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
 * $Id: Request.java,v 1.3 2005/04/14 15:08:06 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.server.ObjID;

import org.exolab.jms.net.util.SerializationHelper;


/**
 * A <code>Request</code> wraps all of the information needed to invoke a method
 * on a remote object.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/04/14 15:08:06 $
 * @see Response
 * @see Connection
 */
public class Request implements Serializable {

    /**
     * The URI of the remote server.
     */
    private transient String _uri;

    /**
     * The identifier of the object to invoke the method on.
     */
    private ObjID _objID;

    /**
     * The method to invoke.
     */
    private transient Method _method;

    /**
     * The serialized method arguments.
     */
    private transient ObjectInput _argStream;

    /**
     * The arguments to pass to the method. Note that if the method doesn't
     * take any arguments, this may be <code>null</code>, to avoid the
     * overhead of serializing an empty Object[].
     */
    private Object[] _args;

    /**
     * The unique identifier of the method to invoke.
     */
    private long _methodID;


    /**
     * Construct a new <code>Request</code>.
     *
     * @param objID    the object to invoke the method on
     * @param method   the method to invoke
     * @param args     the arguments to pass to the method.
     *                 May be <code>null</code>.
     * @param methodID the unique identifier of the method
     */
    public Request(ObjID objID, Method method, Object[] args, long methodID) {
        _objID = objID;
        _method = method;
        _args = args;
        _methodID = methodID;
    }

    /**
     * Construct a new <code>Request</code>.
     *
     * @param uri       the URI of the remote server
     * @param objID     the object to invoke the method on
     * @param methodID  a unique identifier for the method
     * @param argStream the serialized arguments to pass to the method
     * @see {@link #getArgs}
     * @see {@link #readArgs}
     */
    private Request(String uri, ObjID objID, long methodID,
                    ObjectInput argStream) {
        _uri = uri;
        _objID = objID;
        _argStream = argStream;
        _methodID = methodID;
    }

    /**
     * Returns the URI of the remote server.
     *
     * @return the URI of the remote server
     */
    public String getURI() {
        return _uri;
    }

    /**
     * Returns the object identifier.
     *
     * @return the object identifier
     */
    public ObjID getObjID() {
        return _objID;
    }

    /**
     * Returns the method to invoke.
     *
     * @return the method to invoke
     */
    public Method getMethod() {
        return _method;
    }

    /**
     * Returns the arguments to pass to the method.
     *
     * @return the arguments to pass to the method, or <code>null</code>
     * if the method doesn't take any arguments, or the arguments haven't yet
     * been read via {@link #readArgs}
     */
    public Object[] getArgs() {
        return _args;
    }

    /**
     * Reads the serialized arguments, using the supplied method to determine
     * the argument types.
     *
     * @param method the method
     * @return the deserialized arguments
     * @throws ClassNotFoundException if an argument can't be deserialized
     * @throws IOException            for any I/O error
     * @see #read
     */
    public Object[] readArgs(Method method)
            throws ClassNotFoundException, IOException {
        Class[] types = method.getParameterTypes();
        _args = new Object[types.length];
        _method = method;
        for (int i = 0; i < types.length; ++i) {
            _args[i] = SerializationHelper.read(types[i], _argStream);
        }
        if (_argStream != null) {
            _argStream.close();
            _argStream = null;
        }
        return _args;
    }

    /**
     * Returns the unique identifier of the method to invoke.
     *
     * @return the identifier of the method to invoke
     */
    public long getMethodID() {
        return _methodID;
    }

    /**
     * Write this request to a stream.
     *
     * @param out the stream to write to
     * @throws IOException for any I/O error
     */
    public void write(ObjectOutput out) throws IOException {
        _objID.write(out);
        out.writeLong(_methodID);

        // write arguments
        Class[] types = _method.getParameterTypes();
        for (int i = 0; i < types.length; ++i) {
            SerializationHelper.write(types[i], _args[i], out);
        }
    }

    /**
     * Read a request from a stream.
     * <p/>
     * This method doesn't completely deserialize the request. On return from
     * this, the caller is responsible for invoking {@link #readArgs} with the
     * method corresponding to that returned by {@link #getMethodID}.
     *
     * @param in the stream to read from. This is responsible for its closure.
     * @return the deserialized request
     * @throws IOException for any I/O error
     */
    public static Request read(ObjectInput in) throws IOException {
        ObjID objID = ObjID.read(in);
        long methodID = in.readLong();
        return new Request(null, objID, methodID, in);
    }

}
