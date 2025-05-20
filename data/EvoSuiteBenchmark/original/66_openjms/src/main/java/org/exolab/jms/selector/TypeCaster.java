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
 * Copyright 2000-2001,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 */

package org.exolab.jms.selector;


/**
 * Utility class for casting from an SObject to a derived class,
 * raising a TypeMismatchException if the operation is invalid.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:45 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         Expression
 * @see         SBool
 * @see         SNumber
 * @see         SString
 * @see         TypeMismatchException
 */
final class TypeCaster {

    /**
     * Private constructor
     */
    private TypeCaster() {
    }

    /**
     * Cast an object to boolean
     *
     * @param obj the object to cast
     * @param context the context where the object was created
     * @return the cast object
     * @throws TypeMismatchException if <code>obj</code> can't be cast
     */
    public static SBool castToBool(final SObject obj, final String context)
        throws TypeMismatchException {

        SBool result = null;
        if (obj instanceof SBool) {
            result = (SBool) obj;
        } else if (obj != null) {
            typeMismatch(Type.BOOLEAN, obj, context);
        }
        return result;
    }

    /**
     * Cast an object to numeric
     *
     * @param obj the object to cast
     * @param context the context where the object was created
     * @return the cast object
     * @throws TypeMismatchException if <code>obj</code> can't be cast
     */
    public static SNumber castToNumber(final SObject obj, final String context)
        throws TypeMismatchException {

        SNumber result = null;
        if (obj instanceof SNumber) {
            result = (SNumber) obj;
        } else if (obj != null) {
            typeMismatch(Type.NUMERIC, obj, context);
        }
        return result;
    }

    /**
     * Cast an object to string
     *
     * @param obj the object to cast
     * @param context the context where the object was created
     * @return the cast object
     * @throws TypeMismatchException if <code>obj</code> can't be cast
     */
    public static SString castToString(final SObject obj, final String context)
        throws TypeMismatchException {

        SString result = null;
        if (obj instanceof SString) {
            result = (SString) obj;
        } else if (obj != null) {
            typeMismatch(Type.STRING, obj, context);
        }
        return result;
    }

    /**
     * Raises a type mismatch exception
     *
     * @param expected the expected type
     * @param value the actual value
     * @param context the context where <code>value</code> was created
     * @throws TypeMismatchException corresponding to the expected and actual
     * values
     */
    private static void typeMismatch(final Type expected, final SObject value,
                                     final String context)
        throws TypeMismatchException {

        StringBuffer msg = new StringBuffer();
        msg.append("expecting a ");
        msg.append(expected);
        msg.append(" expression");
        if (context != null) {
            msg.append(" for ");
            msg.append(context);
        }
        msg.append(", found a ");
        msg.append(value.type());
        throw new TypeMismatchException(msg.toString());
    }

} //-- TypeCaster
