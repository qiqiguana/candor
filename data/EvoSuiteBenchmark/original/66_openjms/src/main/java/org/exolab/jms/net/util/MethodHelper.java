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
 * $Id: MethodHelper.java,v 1.2 2005/04/02 13:47:16 tanderson Exp $
 */
package org.exolab.jms.net.util;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Helper class for performing reflection.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/04/02 13:47:16 $
 */
public final class MethodHelper {

    /**
     * Prevent construction of helper class.
     */
    private MethodHelper() {
    }

    /**
     * Returns all the interface-declared methods of a class.
     * This includes those implemented by superclasses.
     *
     * @param clazz the class
     * @return a list of the interface-declared methods for <code>clazz</code>
     */
    public static Method[] getAllInterfaceMethods(Class clazz) {
        final int size = 10;
        ArrayList result = new ArrayList(size);
        getInterfaceMethods(getAllInterfaces(clazz), result);
        return (Method[]) result.toArray(new Method[0]);
    }

    /**
     * Returns the interface-declared methods of a class.
     *
     * @param clazz the class
     * @return a list of the interface-declared methods for <code>clazz</code>
     */
    public static Method[] getInterfaceMethods(Class clazz) {
        final int size = 10;
        ArrayList result = new ArrayList(size);
        getInterfaceMethods(clazz.getInterfaces(), result);
        return (Method[]) result.toArray(new Method[0]);
    }

    /**
     * Calculates a unique identifier for a method. The identifier is unique
     * within the declaring class.
     *
     * @param method the method
     * @return a unique identifier for <code>method</code>
     */
    public static long getMethodID(Method method) {
        final int shift = 32;
        long hash = method.getDeclaringClass().getName().hashCode();
        hash ^= method.getName().hashCode();
        hash ^= method.getReturnType().getName().hashCode();
        Class[] args = method.getParameterTypes();
        for (int i = 0; i < args.length; ++i) {
            hash ^= ((long) args[i].getName().hashCode()) << shift;
        }
        return hash;
    }

    /**
     * Determines all of the interface-declared methods from a set of interfaces
     * and their super-interfaces.
     *
     * @param interfaces the interfaces
     * @param result     the array to contain the result
     */
    private static void getInterfaceMethods(Class[] interfaces,
                                            ArrayList result) {
        for (int i = 0; i < interfaces.length; ++i) {
            Class iface = interfaces[i];
            getInterfaceMethods(iface.getInterfaces(), result);
            Method[] methods = iface.getMethods();
            for (int j = 0; j < methods.length; ++j) {
                if (methods[j].getDeclaringClass() == interfaces[i]) {
                    result.add(methods[j]);
                }
            }
        }
    }

    /**
     * Returns all of the interfaces implemented by a class.
     *
     * @param clazz the class
     * @return a list of the interfaces implemented by the class.
     */
    public static Class[] getAllInterfaces(Class clazz) {
        ArrayList result = new ArrayList();
        getAllInterfaces(clazz, result);
        return (Class[]) result.toArray(new Class[0]);
    }

    /**
     * Returns all of the interfaces inplemented by a class.

     * @param clazz the class
     * @param result     the array to contain the result
     */
    private static void getAllInterfaces(Class clazz, ArrayList result) {
        Class[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            if (!result.contains(interfaces[i])) {
                result.add(interfaces[i]);
            }
        }
        Class superClass = clazz.getSuperclass();
        if (superClass != null) {
            getAllInterfaces(superClass, result);
        }
    }
}
