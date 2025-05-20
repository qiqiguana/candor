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
 * This class is the base class for classes adapting simple Java types.
 * This is necessary to:
 * <ul>
 * <li>reduce the number of types that the selector has to deal with.
 *     Expressions only evaluate to boolean, numeric and string types.
 * </li>
 * <li>Simplify operations (comparison, type checking etc.) on these types.
 * </li>
 * </ul>
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         Expression
 * @see         SObjectFactory
 */
abstract class SObject {

    /**
     * Returns the underlying object
     *
     * @return the underlying object
     */
    public abstract Object getObject();

    /**
     * Determines if this is equal to another object.
     *
     * @param obj the object to compare
     * @return <code>null</code> if the comparison is undefined,
     * <code>SBool.TRUE</code> if <code>this = obj</code>, otherwise
     * <code>SBool.FALSE</code> if <code>this &lt;&gt; obj</code>
     */
    public SBool equal(final SObject obj) {
        SBool result = SBool.FALSE;
        if (getObject().equals(obj.getObject())) {
            result = SBool.TRUE;
        }
        return result;
    }

    /**
     * Determines if this is not equal to another object
     *
     * @param obj the object to compare
     * @return <code>null</code> if the comparison is undefined,
     * <code>SBool.TRUE</code> if <code>this &lt;&gt; obj</code>, otherwise
     * <code>SBool.FALSE</code> if <code>this = obj</code>
     */
    public SBool notEqual(final SObject obj) {
        SBool result = equal(obj);
        if (result != null) {
            result = result.not();
        }
        return result;
    }

    /**
     * Determines if this is less than another object.
     *
     * @param obj the object to compare
     * @return <code>null</code> if the comparison is undefined,
     * <code>SBool.TRUE</code> if <code>this &lt; obj</code>, otherwise
     * <code>SBool.FALSE</code> if <code>this &gt;= obj</code>
     */
    public SBool less(final SObject obj) {
        return null;
    }

    /**
     * Determines if this is greater than another object.
     *
     * @param obj the object to compare
     * @return <code>null</code> if the comparison is undefined,
     * <code>SBool.TRUE</code> if <code>this &gt; obj</code>, otherwise
     * <code>SBool.FALSE</code> if <code>this &lt;= obj</code>
     */
    public SBool greater(final SObject obj) {
        return null;
    }

    /**
     * Determines if this is less than or equal to another object.
     *
     * @param obj the object to compare
     * @return <code>null</code> if the comparison is undefined,
     * <code>SBool.TRUE</code> if <code>this &lt;= obj</code>, otherwise
     * <code>SBool.FALSE</code> if <code>this &gt; obj</code>
     */
    public SBool lessEqual(final SObject obj) {
        SBool result = less(obj);
        if (result != null && !result.value()) {
            result = equal(obj);
        }
        return result;
    }

    /**
     * Determines if this is greater than or equal to another object.
     *
     * @param obj the object to compare
     * @return <code>null</code> if the comparison is undefined,
     * <code>SBool.TRUE</code> if <code>this &gt;= obj</code>, otherwise
     * <code>SBool.FALSE</code> if <code>this &lt; obj</code>
     */
    public SBool greaterEqual(final SObject obj) {
        SBool result = greater(obj);
        if (result != null && !result.value()) {
            result = equal(obj);
        }
        return result;
    }

    /**
     * Returns a string representation of this
     *
     * @return a string representation of this
     */
    public String toString() {
        return getObject().toString();
    }

    /**
     * Determines the type of this
     *
     * @return the type of this
     */
    public abstract Type type();

} //-- SObject
