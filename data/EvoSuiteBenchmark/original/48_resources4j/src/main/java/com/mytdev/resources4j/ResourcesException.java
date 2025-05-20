/*
 * ResourcesException.java
 *
 *  Copyright 2010 Yann D'Isanto.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.mytdev.resources4j;

/**
 *
 * @author Yann D'Isanto
 */
public class ResourcesException extends Exception {

    /**
     * Creates a new instance of <code>ResourcesException</code> without detail
     * message.
     */
    public ResourcesException() {
    }

    /**
     * Constructs an instance of <code>ResourcesException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public ResourcesException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>ResourcesException</code> with the specified
     * detail message and cause.  <p>Note that the detail message associated
     * with <code>cause</code> is <i>not</i> automatically incorporated in
     * this exception's detail message.
     * @param msg the detail message.
     * @param cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public ResourcesException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs an instance of <code>ResourcesException</code> with the specified
     * cause and a detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of <tt>cause</tt>).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public ResourcesException(Throwable cause) {
        super(cause);
    }

}
