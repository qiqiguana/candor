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
 * $Id: InvokeTestCase.java,v 1.5 2008/01/07 13:00:17 tanderson Exp $
 */
package org.exolab.jms.net.invoke;

import org.exolab.jms.net.EchoService;
import org.exolab.jms.net.EchoServiceImpl;
import org.exolab.jms.net.registry.Registry;

import java.util.Arrays;
import java.util.Map;


/**
 * Tests remote method invocation.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2008/01/07 13:00:17 $
 */
public abstract class InvokeTestCase extends RemoteServerTestCase {

    /**
     * Tracks errors during {@link #testConcurrency}.
     */
    private Throwable _failure;

    /**
     * The service name.
     */
    private static final String SERVICE_NAME = "echo";

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     */
    public InvokeTestCase(String name, String uri, boolean embeddedService) {
        this(name, uri, embeddedService, null);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     * @param properties      connection properties. May be <code>null</code>
     */
    public InvokeTestCase(String name, String uri, boolean embeddedService,
                          Map properties) {
        this(name, uri, null, embeddedService, properties);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     */
    public InvokeTestCase(String name, String uri, String routeURI,
                          boolean embeddedService) {
        this(name, uri, routeURI, embeddedService, null);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     * @param properties      connection properties. May be <code>null</code>
     */
    public InvokeTestCase(String name, String uri, String routeURI,
                          boolean embeddedService, Map properties) {
        this(name, uri, routeURI, embeddedService, properties, properties);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     * @param connectionProps connection properties. May be <code>null</code>
     * @param acceptorProps   acceptor properites. May be <code>null</code>
     */
    public InvokeTestCase(String name, String uri, String routeURI,
                          boolean embeddedService,
                          Map connectionProps, Map acceptorProps) {
        super(name, uri, routeURI, embeddedService, connectionProps,
              acceptorProps);
    }

    /**
     * Tests remote method invocation for primitives.
     *
     * @throws Exception for any error
     */
    public void testPrimitives() throws Exception {
        Registry registry = getORB().getRegistry(getConnectionProperties());
        EchoService echo = (EchoService) registry.lookup(SERVICE_NAME);

        assertEquals(true, echo.echoBoolean(true));
        assertEquals(false, echo.echoBoolean(false));

        assertEquals(Byte.MIN_VALUE, echo.echoByte(Byte.MIN_VALUE));
        assertEquals(Byte.MAX_VALUE, echo.echoByte(Byte.MAX_VALUE));

        assertEquals(Character.MIN_VALUE, echo.echoChar(Character.MIN_VALUE));
        assertEquals(Character.MAX_VALUE, echo.echoChar(Character.MAX_VALUE));

        assertEquals(Short.MIN_VALUE, echo.echoShort(Short.MIN_VALUE));
        assertEquals(Short.MAX_VALUE, echo.echoShort(Short.MAX_VALUE));

        assertEquals(Integer.MIN_VALUE, echo.echoInt(Integer.MIN_VALUE));
        assertEquals(Integer.MAX_VALUE, echo.echoInt(Integer.MAX_VALUE));

        assertEquals(Long.MIN_VALUE, echo.echoLong(Long.MIN_VALUE));
        assertEquals(Long.MAX_VALUE, echo.echoLong(Long.MAX_VALUE));

        assertEquals(Float.MIN_VALUE, echo.echoFloat(Float.MIN_VALUE), 0.0f);
        assertEquals(Float.MAX_VALUE, echo.echoFloat(Float.MAX_VALUE), 0.0f);

        assertEquals(Double.MIN_VALUE, echo.echoDouble(Double.MIN_VALUE), 0.0);
        assertEquals(Double.MAX_VALUE, echo.echoDouble(Double.MAX_VALUE), 0.0);
    }

    /**
     * Tests remote method invocation for primitive arrays.
     *
     * @throws Exception for any error
     */
    public void testPrimitiveArrays() throws Exception {
        final int size = 4096;

        Registry registry = getORB().getRegistry(getConnectionProperties());
        EchoService echo = (EchoService) registry.lookup(SERVICE_NAME);

        // byte arrays
        byte[] bytes = new byte[size];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) i;
        }
        byte[] bytesResult = (byte[]) echo.echoObject(bytes);
        assertTrue(Arrays.equals(bytes, bytesResult));

        // int arrays
        int[] ints = new int[size];
        for (int i = 0; i < ints.length; ++i) {
            ints[i] = i;
        }
        int[] intsResult = (int[]) echo.echoObject(ints);
        assertTrue(Arrays.equals(ints, intsResult));

        // float arrays
        float[] floats = new float[size];
        for (int i = 0; i < floats.length; ++i) {
            floats[i] = i;
        }
        float[] floatsResult = (float[]) echo.echoObject(floats);
        assertTrue(Arrays.equals(floats, floatsResult));
    }

    /**
     * Verifies invocations can be made concurrently.
     *
     * @throws Exception for any error
     */
    public void testConcurrency() throws Exception {
        Thread[] threads = new Thread[10];

        Registry registry = getORB().getRegistry(getConnectionProperties());
        EchoService echo = (EchoService) registry.lookup(SERVICE_NAME);

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(new IntInvoker(echo, i, 1000));
        }

        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException ignore) {
            }
        }
        if (_failure != null) {
            if (_failure instanceof Error) {
                throw (Error) _failure;
            } else if (_failure instanceof Exception) {
                throw (Exception) _failure;
            } else {
                throw new Exception("testConcurrency failed: "
                        + _failure.getMessage());
            }
        }
    }

    /**
     * Sets up the test case.
     *
     * @throws Exception for any error
     */
    protected void setUp() throws Exception {
        super.setUp();
        startServer(EchoServiceImpl.class, SERVICE_NAME);
    }

    /**
     * Helper classed used in concurrency tests.
     */
    protected class IntInvoker implements Runnable {

        /**
         * The echo service.
         */
        private final EchoService _echo;

        /**
         * The value to invoke the service with.
         */
        private final int _value;

        /**
         * No. of invocations to perform.
         */
        private final int _count;


        /**
         * Construct a new <tt>IntInvoker</tt>.
         *
         * @param echo  the echo service
         * @param value the value to invoke the echo service with
         * @param count the no. of invocations to perform
         */
        public IntInvoker(EchoService echo, int value, int count) {
            _echo = echo;
            _value = value;
            _count = count;
        }

        /**
         * Run the invoker.
         */
        public void run() {
            try {
                for (int i = 0; i < _count; ++i) {
                    assertEquals(_value, _echo.echoInt(_value));
                }
            } catch (Throwable exception) {
                _failure = exception;
            }
        }
    }

}
